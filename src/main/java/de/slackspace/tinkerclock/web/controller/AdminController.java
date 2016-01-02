package de.slackspace.tinkerclock.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

	@RequestMapping("/admin")
    public String greeting(Model model) {
        return "admin";
    }
	
}
