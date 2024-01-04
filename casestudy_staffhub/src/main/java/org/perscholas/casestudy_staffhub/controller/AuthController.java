package org.perscholas.casestudy_staffhub.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.database.dao.UserDAO;
import org.perscholas.casestudy_staffhub.database.dao.UserRoleDAO;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.database.entity.User;
import org.perscholas.casestudy_staffhub.database.entity.UserRole;
import org.perscholas.casestudy_staffhub.formbean.RegisterUserFormBean;
import org.perscholas.casestudy_staffhub.security.AuthenticatedUserService;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.perscholas.casestudy_staffhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Slf4j
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private UserRoleDAO userRoleDao;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("/auth/login")
    public ModelAndView login() {
        ModelAndView response = new ModelAndView();
        response.setViewName("auth/login");
        return response;
    }

    @GetMapping("/auth/register")
    public ModelAndView register(){
        ModelAndView response = new ModelAndView();
        // Fetch all departments
        List<Department> departments = departmentService.getAllDepartments();

        // Add departments to the model
        response.addObject("departments", departments);

        response.setViewName("auth/register");
        return response;
    }

    @GetMapping("/auth/registerSubmit")
    public ModelAndView registerSubmit(@Valid RegisterUserFormBean form, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            log.info("####### In register user - has errors ######");
            ModelAndView response = new ModelAndView("auth/register");

            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error: " + error.getDefaultMessage());
            }

            response.addObject("form", form);
            response.addObject("errors", bindingResult);
            return response;
        }

        log.info("##### In register user - no error found #######");

        User u = userService.createNewUser(form);

        authenticatedUserService.authenticateNewUser(session, u.getEmail(), form.getPassword());

        UserRole userRole = new UserRole();
        userRole.setUserId(u.getId());
        userRole.setRoleName(form.getUserType());
        userRoleDao.save(userRole);

        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/");

        return response;
    }

}
