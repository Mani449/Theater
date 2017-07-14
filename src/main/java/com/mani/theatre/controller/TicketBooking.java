package com.mani.theatre.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mani.theatre.beans.Screen;
import com.mani.theatre.dao.BookTicketsDAO;

@Controller
public class TicketBooking {

	@RequestMapping("/book")
	public ModelAndView bookTickets() {
		ModelAndView model = new ModelAndView();
		model.setViewName("Book");
		BookTicketsDAO dao = new BookTicketsDAO();
		List<Screen> screens = dao.getScreens();
		if (screens.size() == 0) {
			model.addObject("noScreen", true);
			model.addObject("error", "Configure the Theater");
		} else
			model.addObject("screens", screens);
		return model;
	}

	@RequestMapping("/checkTickets")
	public ModelAndView checkTickets(@RequestParam int screen, @RequestParam int tickets) {
		ModelAndView model = new ModelAndView("Book");
		BookTicketsDAO dao = new BookTicketsDAO();
		int count = dao.availableSeats(screen);
		if (count < tickets) {
			model.addObject("error", "Only " + count + " Tickets are Available");
		} else {
			System.out.println(dao.getAllSeats(screen));
			model.addObject("seats",dao.getAllSeats(screen));
			model.addObject("screen",screen);
			model.setViewName("showSeats");
		}
		return model;
	}
	
	@RequestMapping("/bookSeats")
	public ModelAndView bookSeats(HttpServletRequest req, HttpServletResponse res,@RequestParam("screen") int screen,@RequestParam("seats") List<Integer> seats)
	{
		System.out.println("SEssion Id::::::::"+req.getRequestedSessionId());
		System.out.println("seats"+seats);
		ModelAndView model = new ModelAndView("showSeats");
		if(seats.size()==0){
			model.addObject("error","Please Select atleast one seat");
			return model;
		}
		BookTicketsDAO dao = new BookTicketsDAO();
		System.out.println("Seats:::::"+seats);
		model.addObject("screen",screen);
		int reservationId=dao.bookSeats(seats.size(), screen);
		if(reservationId!=-1)
		{
			/*model.addObject("seats",dao.getAllSeats(screen));
			model.addObject("success", "Tickets Booked Successfully");*/
			req.getSession().setAttribute("seats", seats);
			model.setViewName("paypalPayment");
			model.addObject("reservation_id", reservationId);
		}
		else
		{
			model.addObject("seats",dao.getAllSeats(screen));
			model.addObject("error","Tickets Booking Failed");
		}
		return model;
	}
	
	@RequestMapping("/tickets")
	public ModelAndView redirect(HttpServletRequest req) {
		ModelAndView model=(ModelAndView)req.getSession().getAttribute("model");
		BookTicketsDAO dao=new BookTicketsDAO();
		model.addObject("seats", dao.getAllSeats((int)model.getModel().get("screen")));
		return model;
	}
}