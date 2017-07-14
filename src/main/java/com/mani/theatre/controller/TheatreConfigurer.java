package com.mani.theatre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mani.theatre.dao.TheatreConfigurerDAO;

@Controller
@RequestMapping("/configure")
public class TheatreConfigurer {
	@RequestMapping(method=RequestMethod.GET)
	public String configurer() {
		return "configureTheatre";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView configureTheatre(@RequestParam("screens") int screens,@RequestParam int rows, @RequestParam int seats )
	{
		System.out.println("Screens :::: "+screens+"Rows :: "+rows+"Seats ::: "+seats);
		ModelAndView model=new ModelAndView();
		TheatreConfigurerDAO dao=new TheatreConfigurerDAO();
		if(dao.configureTheatre(screens, rows, seats))
			model.addObject("success", "Theatre Configured Successfully");
		else
			model.addObject("error", "Theatre Configured already. If you see any issues contact tech-support");
		model.setViewName("configureTheatre");
		return  model;
	}
}
