package org.perscholas.casestudy_staffhub.controller;


import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.database.dao.DepartmentDAO;
import org.perscholas.casestudy_staffhub.database.dao.UserDAO;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.database.entity.User;
import org.perscholas.casestudy_staffhub.formbean.UserFormBean;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.perscholas.casestudy_staffhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDao;

    @Autowired
    private DepartmentDAO departmentDao;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/staff/create")
    public ModelAndView createUser(){
        ModelAndView response = new ModelAndView("staff/create");
        log.info("In create employee with no args");

        // Fetch all departments
        List<Department> departments = departmentService.getAllDepartments();

        // Add departments to the model
        response.addObject("departments", departments);

        return response;
    }

    @PostMapping("/staff/createSubmit")
    public ModelAndView createUserSubmit(@Valid @ModelAttribute UserFormBean form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("########## In create staff submit");
            ModelAndView response = new ModelAndView("staff/create");

            for(ObjectError error : bindingResult.getAllErrors()){
                log.info("error: " + error.getArguments());
            }
            response.addObject("form", form);
            response.addObject("errors", bindingResult);

            // Fetch all departments
            List<Department> departments = departmentService.getAllDepartments();

            // Add departments to the model
            response.addObject("departments", departments);

            return response;
        }

        Department department = departmentDao.findById(form.getDepartmentId());

        User u = userService.createUser(form);

        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/staff/edit/" + u.getId() + "?success=Staff Saved Successfully");

        return response;
    }

    @GetMapping("/staff/edit/{userId}")
    public ModelAndView editUser(@PathVariable Integer userId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("staff/create");

        User user = userDao.findById(userId);


        if (!StringUtils.isEmpty(success)) {
            response.addObject("success", success);
        }

        UserFormBean form = new UserFormBean();

        // Add departments to the model
        List<Department> departments = departmentService.getAllDepartments();
        response.addObject("departments", departments);

        // get each department
        Department department = departmentDao.findById(form.getDepartmentId());

        if (user != null) {
            form.setId(user.getId());
            department = user.getDepartment();
            form.setDepartmentId(department != null ? department.getId() : null);
            form.setFirstName(user.getFirstName());
            form.setLastName(user.getLastName());
            form.setEmail(user.getEmail());
            form.setPassword(user.getPassword());
            form.setJobTitle(user.getJobTitle());
            form.setAddress(user.getAddress());
            form.setOffice_Id(user.getOffice_Id());
            form.setImageUrl(user.getImageUrl());
        } else {
            log.warn("User with id " + userId + " was not found");
            log.warn("Department with id " +  department.getId() + " was not found");
        }

        response.addObject("form", form);
        response.addObject("departments", departments);

        return response;
    }
}
