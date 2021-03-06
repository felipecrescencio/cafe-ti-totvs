package com.felipecrescencio.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.felipecrescencio.app.CoffeeITApplication;
import com.felipecrescencio.entity.WeeklyCoffee;
import com.felipecrescencio.service.WeeklyCoffeeService;

@Controller
public class WeeklyCoffeeController {
    @Autowired
    WeeklyCoffeeService weeklyCoffeeService;

    @RequestMapping("/")
	String index() {
    	CoffeeITApplication.cttb.weeklyCoffeeService = weeklyCoffeeService;
		return "index";
	}

	@RequestMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String dayOfWeek, @RequestParam String whoBroughtName) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(dayOfWeek));

			WeeklyCoffee n = new WeeklyCoffee(name, c, whoBroughtName);
			weeklyCoffeeService.create(n);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Saved";
	}

	@RequestMapping(path="/update") // Map ONLY GET Requests
	public @ResponseBody String updateUser (@RequestParam String id, @RequestParam String name, @RequestParam String dayOfWeek, @RequestParam String whoBroughtName) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		try {
			Long idl = Long.valueOf(id);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(dayOfWeek));

			WeeklyCoffee n = new WeeklyCoffee(name, c, whoBroughtName);
			n.setId(idl);
			weeklyCoffeeService.update(n);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Saved";
	}
	
	@RequestMapping(path="/all")
	public @ResponseBody Iterable<String> getAllWeeklyCoffees() {
		List<String> l1 = new ArrayList<String>();
		for(WeeklyCoffee w : weeklyCoffeeService.findAll()) {
			l1.add(w.toString());
		}
		return l1;
	}

	@RequestMapping(path="/allraw")
	public @ResponseBody Iterable<WeeklyCoffee> getAllWeeklyCoffeesRaw() {
		// This returns a JSON or XML with the users
		return weeklyCoffeeService.findAll();
	}

	@RequestMapping(path="/delete") // Map ONLY GET Requests
	public @ResponseBody String delete (@RequestParam String id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		try {
			weeklyCoffeeService.delete(Long.parseLong(id));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Deleted";
	}
}
