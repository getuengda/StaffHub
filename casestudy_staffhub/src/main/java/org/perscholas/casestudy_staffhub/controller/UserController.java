package org.perscholas.casestudy_staffhub.controller;


import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.perscholas.casestudy_staffhub.database.dao.DepartmentDAO;
import org.perscholas.casestudy_staffhub.database.dao.TrainingDAO;
import org.perscholas.casestudy_staffhub.database.dao.UserDAO;
import org.perscholas.casestudy_staffhub.database.dao.UserTrainingDAO;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.database.entity.Training;
import org.perscholas.casestudy_staffhub.database.entity.User;
import org.perscholas.casestudy_staffhub.database.entity.UserTraining;
import org.perscholas.casestudy_staffhub.formbean.UserFormBean;
import org.perscholas.casestudy_staffhub.formbean.UserProfileDTO;
import org.perscholas.casestudy_staffhub.security.AuthenticatedUserService;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.perscholas.casestudy_staffhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private DepartmentDAO departmentDao;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TrainingDAO trainingDao;

    @Autowired
    private UserTrainingDAO userTrainingDao;

    @Autowired
    AuthenticatedUserService authenticatedUserService;


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

    @GetMapping("/staff/myUser")
    public void myStaff() {
        ModelAndView response = new ModelAndView("staff/create");
        log.info("######### In my staff ##############");

        //Use the authenticated user service to find the logged-in user
        User user = authenticatedUserService.loadCurrentUser();

        List<User> users = (List<User>) userDao.findById(user.getId());

        for ( User user1 : users ) {
            log.debug("user: id = " + user1.getId() + " last name = " + user1.getLastName());
        }
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

    @GetMapping("staff/search")
    public ModelAndView  search(@RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName){
        ModelAndView response = new ModelAndView("staff/search");
        log.info("In the user search controller method firstName: " + firstName);
        log.info("In the user search controller method lastName: " + lastName);

        if(!StringUtils.isEmpty(firstName) || !StringUtils.isEmpty(lastName)){
            response.addObject("firstName", firstName);
            response.addObject("lastName",lastName);

            if(!StringUtils.isEmpty(firstName)){
                firstName = "%" + firstName + "%";
            }
            if(!StringUtils.isEmpty(lastName)){
                lastName = "%" + lastName + "%";
            }

            List<User> users = userDao.findUserByFirstNameOrLastName(firstName, lastName);
            response.addObject("userVar", users);

            for(User user : users){
                log.info("user: id= " + user.getId() + " first name = " + user.getFirstName());
                log.info("user: id= " + user.getId() + " last name = " + user.getLastName());
            }

        }
        return response;
    }

    @GetMapping("/staff/fileupload")
    public ModelAndView fileUpload(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("staff/fileupload");

        User user = userDao.findById(id);
        response.addObject("user", user);

        log.info(" In fileupload with no Args");
        return response;
    }

    @PostMapping("/staff/fileUploadSubmit")
    public ModelAndView fileUploadSubmit(@RequestParam("file") MultipartFile file,
                                         @RequestParam Integer id) {
        ModelAndView response = new ModelAndView("redirect:/staff/detail?id=" + id);

        log.info("Filename = " + file.getOriginalFilename());
        log.info("Size     = " + file.getSize());
        log.info("Type     = " + file.getContentType());

        // Get the file and save it somewhere
        File f = new File("./src/main/webapp/pub/images/" + file.getOriginalFilename());
        try (OutputStream outputStream = new FileOutputStream(f.getAbsolutePath())) {
            IOUtils.copy(file.getInputStream(), outputStream);
        } catch (Exception e) {

            e.printStackTrace();
        }

        User user = userDao.findById(id);
        user.setImageUrl("/pub/images/" + file.getOriginalFilename());
        userDao.save(user);

        return response;
    }

    @GetMapping("/staff/detail")
    public ModelAndView userDetail(@RequestParam(required = false) Integer id) {
        ModelAndView response = new ModelAndView("staff/detail");
        log.debug("In the user detail controller method id: " + id);

        // Fetch single user by id
        User user = userDao.findById(id);

        if (user != null) {
            response.addObject("user", user);
        } else {
            log.warn("User with id " + id + " not found");
        }

        return response;
    }

    @GetMapping("/staff/{userId}/detail")
    public ModelAndView showUserDetail(@PathVariable Integer userId) {
        ModelAndView response = new ModelAndView("staff/detail");

        User user = userDao.findById(userId);
        response.addObject("user", user);

        return response;
    }

    @PostMapping("/staff/{userId}/addTraining")
    public ModelAndView addTraining(@PathVariable Integer userId,
                                    @RequestParam Integer trainingId,
                                    @RequestParam String enrollmentDate,
                                    @RequestParam String status,
                                    RedirectAttributes redirectAttributes) throws ParseException {
        ModelAndView response = new ModelAndView("staff/addTraining");
        log.debug("Adding training for userId: " + userId);

        User user = userDao.findById(userId);
        Training training = trainingDao.findById(trainingId);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormatter.parse(enrollmentDate);

        UserTraining userTraining = new UserTraining();
        userTraining.setEnrollmentDate(date);
        userTraining.setUser(user);
        userTraining.setTraining(training);
        userTraining.setStatus(status);
        userTrainingDao.save(userTraining);

        response.addObject("user", user);
        response.setViewName("redirect:/staff/" + userId + "/detail");

        return response;
    }


    @GetMapping("/staff/addTraining")
    public ModelAndView showAddTrainingForm(@RequestParam Integer userId) {
        ModelAndView response = new ModelAndView("staff/addTraining");

        List<Training> trainingList = trainingDao.findAll();

        response.addObject("userId", userId);
        response.addObject("trainingList", trainingList);

        return response;
    }

    @GetMapping("/staff/showAll")
    public ModelAndView showAllStaff() {
        ModelAndView response = new ModelAndView("staff/showAll");
        log.debug("In the user showAllStaff controller method firstName");

        // Fetch all staff
        List<User> users = userService.getAllUsers();

        // Add staffs to the model
        response.addObject("userVar", users);

        for(User user : users){
            log.debug("user: id= " + user.getId() + "first name = " + user.getFirstName());
            log.debug("user: id= " + user.getId() + "last name = " + user.getLastName());
        }

        return response;
    }

    @GetMapping("/staff/{userId}/profile")
    public String showUserProfile(@PathVariable Integer userId) {

        ModelAndView response = new ModelAndView("staff/profile");

        UserProfileDTO userProfile = userService.getUserProfileById(userId);

        if (userProfile == null) {
            return "redirect:/error";
        }

        response.addObject("userProfile", userProfile);
        return String.valueOf(response);
    }

    @GetMapping("staff/profile")
    public String userProfile(@RequestParam("id") Integer userId, Model model) {
        log.debug("In the user profile controller method id: " + userId);

        UserProfileDTO userProfile = userService.getUserProfileById(userId);

        if (userProfile == null) {
            return "redirect:/error";
        }

        model.addAttribute("userProfile", userProfile);

        return "staff/profile";
    }

}
