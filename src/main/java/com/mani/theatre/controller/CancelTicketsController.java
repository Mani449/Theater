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
public class CancelTicketsController {

	@RequestMapping("/cancel")
	public ModelAndView cancelSeats() {
		BookTicketsDAO dao = new BookTicketsDAO();
		ModelAndView model = new ModelAndView("cancelscreen");
		List<Screen> screens = dao.getCancelScreens();
		if (screens.size() != 0)
			model.addObject("screens", screens);
		else
			model.addObject("error", "No Tickets Booked at all");
		return model;
	}
	
	@RequestMapping("/cancelTickets")
	public ModelAndView cancelSeatsForScreen(@RequestParam int screen) {
		ModelAndView model = new ModelAndView("cancelseats");
		BookTicketsDAO dao = new BookTicketsDAO();
		model.addObject("seats",dao.getAllSeats(screen));
		model.addObject("screen",screen);
		return model;
	}
	
	@RequestMapping("/cancelSeats")
	public ModelAndView bookSeats(HttpServletRequest req, HttpServletResponse res,@RequestParam("screen") int screen,@RequestParam("seats") List<Integer> seats)
	{
		System.out.println("seats"+seats);
		ModelAndView model = new ModelAndView("cancelseats");
		if(seats.size()==0){
			model.addObject("error","Please Select atleast one seat");
			return model;
		}
		BookTicketsDAO dao = new BookTicketsDAO();
		System.out.println("Seats:::::"+seats);
		model.addObject("screen",screen);
		
		if(dao.cancelSeats(seats, screen))
		{
			model.addObject("seats",dao.getAllSeats(screen));
			model.addObject("success", "Tickets Cancelled Successfully");
		}
		else
		{
			model.addObject("seats",dao.getAllSeats(screen));
			model.addObject("error","Tickets Cancel Failed");
		}
		return model;
	}
	
}
