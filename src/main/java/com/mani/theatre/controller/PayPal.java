package com.mani.theatre.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mani.theatre.beans.PayPalBean;
import com.mani.theatre.dao.BookTicketsDAO;
import com.mani.theatre.dao.PayPalDAO;

@Controller
public class PayPal {

	@RequestMapping("/paypal")
	public String registerPaypal() {
		return "paypal";
	}

	@RequestMapping("/registerPaypal")
	public ModelAndView register(PayPalBean bean) {
		System.out.println("Balance::::" + bean.getBalance());
		ModelAndView view = new ModelAndView("paypal");
		PayPalDAO dao = new PayPalDAO();
		if (dao.registerPayPal(bean))
			view.addObject("success", "PayPal Account created");
		else
			view.addObject("error", "PayPal creation failed.. try different User Name");
		return view;
	}

	@RequestMapping("/processPaypal")
	public synchronized ModelAndView process(HttpServletRequest req, HttpServletResponse res, int reservation_id,
			int screen, PayPalBean bean) throws IOException {

		ModelAndView model = new ModelAndView("showSeats");
		PayPalDAO dao = new PayPalDAO();
		BookTicketsDAO dao_book = new BookTicketsDAO();
		float price = dao_book.getCostReservation(reservation_id);
		model.addObject("screen", screen);
		model.addObject("seats",dao_book.getAllSeats(screen));
		System.out.println("reservationid ::: " + reservation_id);
		System.out.println("price:::::" + price);
		if (price != -1) {
			bean.setBalance(dao_book.getCostReservation(reservation_id));
			if (dao.validatePayPalAndPay(bean)) {
				List<Integer> seats=(List<Integer>)req.getSession().getAttribute("seats");
				if ( seats == null) {
					model.addObject("error", "Session time-out");
				} else {
					if (dao_book.finalizeTransaction(seats,reservation_id,screen)) {
						model.addObject("success", "Tickets Booked Successfully");
					}
					else
					{
						model.addObject("error", "Ticket book failed ..");
					}
				}
			} else {
				model.addObject("error", "PayPal Authorization Failed");
			}
		} else {
			model.addObject("error", "Something Wrong with the reservation");
		}

		req.getSession().setAttribute("model", model);
		res.sendRedirect("tickets.html");
		return model;
	}
}
