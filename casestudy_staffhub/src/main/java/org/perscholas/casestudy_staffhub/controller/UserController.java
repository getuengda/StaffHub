package org.perscholas.casestudy_staffhub.controller;


import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.formbean.UserFormBean;
import org.perscholas.casestudy_staffhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;



@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/staff/create")
    public ModelAndView createUser(){
        ModelAndView response = new ModelAndView("staff/create");
        log.info("In create employee with no args");

        return response;
    }

    @PostMapping("/staff/createSubmit")
    public ModelAndView createEmployeeSubmit(@ModelAttribute UserFormBean form) {

        log.info("########## In create employee submit");
        ModelAndView response = new ModelAndView("staff/create");

        response.addObject("form", form);

        userService.createUser(form);

        return response;
    }
}
