package org.perscholas.casestudy_staffhub.controller;


import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.perscholas.casestudy_staffhub.database.dao.*;
import org.perscholas.casestudy_staffhub.database.entity.*;
import org.perscholas.casestudy_staffhub.formbean.DepartmentFormBean;
import org.perscholas.casestudy_staffhub.formbean.UserFormBean;
import org.perscholas.casestudy_staffhub.formbean.UserProfileDTO;
import org.perscholas.casestudy_staffhub.security.AuthenticatedUserService;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.perscholas.casestudy_staffhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import java.util.Optional;

@Slf4j
@Controller
@Component
public class AdminSystemController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentDAO departmentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private TrainingDAO trainingDao;

    @Autowired
    private UserTrainingDAO userTrainingDao;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Autowired
    private UserRoleDAO userRoleDao;


    @GetMapping("/admin/createDepartment")
    public ModelAndView create(){
        ModelAndView response = new ModelAndView("admin/createDepartment");
        log.info("In create Department with no args");

        // Fetch all departments
        List<Department> departments = departmentService.getAllDepartments();

        // Add departments to the model
        response.addObject("departments", departments);

        return response;
    }

    @GetMapping("/admin/createSubmitDepartment")
    public ModelAndView createDepartment(@Valid @ModelAttribute DepartmentFormBean form, BindingResult bindingResult ) {
        if(bindingResult.hasErrors()){
            log.info("########## In create user submit");
            ModelAndView response = new ModelAndView("admin/createDepartment");

            for(ObjectError error : bindingResult.getAllErrors()){
                log.info("error: " + error.getArguments());
            }
            response.addObject("form", form);
            response.addObject("errors", bindingResult);

            return response;
        }

        Department d = departmentService.createDepartment(form);

        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/admin/editDepartment/" + d.getId() + "?success=Department Saved Successfully");

        return response;
    }


    @GetMapping("/admin/edit/{departmentId}")
    public ModelAndView edit(@PathVariable Integer departmentId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("/admin/createDepartment");

        Department department = departmentDao.findById(departmentId);


        if (!StringUtils.isEmpty(success)) {
            response.addObject("success", success);
        }

        DepartmentFormBean form = new DepartmentFormBean();

        // Add departments to the model
        List<Department> departments = departmentService.getAllDepartments();
        response.addObject("departments", departments);

        if (department != null) {
            form.setId(department.getId());
            form.setDepartmentName(department.getDepartmentName());
            form.setDescription(department.getDescription());
            form.setImageUrl(department.getImageUrl());
        } else {
            log.info("Department with id " +  department.getId() + " was not found");
        }

        response.addObject("form", form);

        return response;
    }


    @RequestMapping(value = "/admin/searchDepartment", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView search(@RequestParam(required = false) String departmentName){
        ModelAndView response = new ModelAndView("admin/searchDepartment");
        log.debug("In the department search controller method departmentName: " + departmentName);

        if(!StringUtils.isEmpty(departmentName)){
            departmentName = "%" + departmentName + "%";
        }

        List<Department> departments = departmentDao.findDepartmentByName(departmentName);
        response.addObject("departmentVar", departments);

        for(Department department : departments){
            log.info("department: id= " + department.getId() + "department Name = " + department.getDepartmentName());
            log.info("department: id= " + department.getId() + "description = " + department.getDescription());
        }
        return response;
    }

    @GetMapping("/admin/detailDepartment")
    public ModelAndView departmentDetail(@RequestParam(required = false) Integer id) {
        ModelAndView response = new ModelAndView("admin/detailDepartment");
        log.debug("In the department detail controller method id: " + id);

        // Fetch single department by id
        Department department = departmentDao.findById(id);

        if (department != null) {
            response.addObject("department", department);
        } else {
            log.warn("Department with id " + id + " not found");
        }

        return response;
    }

    @GetMapping("/admin/fileuploadDepartment")
    public ModelAndView fileUpload(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("admin/fileuploadDepartment");

        Department department = departmentDao.findById(id);
        response.addObject("department", department);

        log.info(" In fileupload with no Args");
        return response;
    }

    @PostMapping("/admin/fileUploadSubmitDepartment")
    public ModelAndView fileUploadSubmit(@RequestParam("file") MultipartFile file,
                                         @RequestParam Integer id) {
        ModelAndView response = new ModelAndView("redirect:/admin/detailDepartment?id=" + id);

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

        Department department = departmentDao.findById(id);
        department.setImageUrl("/pub/images/" + file.getOriginalFilename());
        departmentDao.save(department);

        return response;
    }

    @GetMapping("/admin/showAllDepartment")
    public ModelAndView showAllDepartment() {
        ModelAndView response = new ModelAndView("admin/showAllDepartment");
        log.info("In admin department showAllDepartment controller method firstName");

        // Fetch all departments
        List<Department> departments = departmentService.getAllDepartments();

        // Add departments to the model
        response.addObject("departmentVar", departments);

        for(Department department : departments){
            log.info("department: id= " + department.getId() + "Department Name = " + department.getDepartmentName());
            log.info("department: id= " + department.getId() + "Description = " + department.getDescription());
        }

        return response;
    }

        // User Controller section below

    @GetMapping("/admin/createUser")
    public ModelAndView createUser(){
        ModelAndView response = new ModelAndView("admin/createUser");
        log.info("In create User with no args");

        // Fetch all departments
        List<Department> departments = departmentService.getAllDepartments();

        // Add departments to the model
        response.addObject("departments", departments);

        return response;
    }

    @PostMapping("/admin/createSubmitUser")
    public ModelAndView createUserSubmit(@Valid @ModelAttribute UserFormBean form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("########## In create staff submit");
            ModelAndView response = new ModelAndView("admin/createUser");

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

        UserRole userRole = new UserRole();
        userRole.setUserId(u.getId());
        userRole.setRoleName(form.getUserType());
        userRoleDao.save(userRole);

        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/admin/editUser/" + u.getId() + "?success=Staff Saved Successfully");

        return response;
    }

    @GetMapping("/admin/myUser")
    public ModelAndView myStaff() {
        ModelAndView response = new ModelAndView("admin/createUser");
        log.info("######### In my staff ##############");

        // Use the authenticated user service to find the logged-in user
        User user = authenticatedUserService.loadCurrentUser();

        // Find the user in the database
        Optional<User> optionalUser = Optional.ofNullable(userDao.findById(user.getId()));

        if (optionalUser.isPresent()) {
            User userFromDb = optionalUser.get();
            log.debug("user: id = " + userFromDb.getId() + " last name = " + userFromDb.getLastName());
        } else {
            log.debug("User not found in the database");
        }

        return response;
    }

    @GetMapping("/admin/editUser/{userId}")
    public ModelAndView editUser(@PathVariable Integer userId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("admin/createUser");

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

    @GetMapping("admin/searchUser")
    public ModelAndView  search(@RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName){
        ModelAndView response = new ModelAndView("admin/searchUser");
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

    @GetMapping("/admin/fileuploadForUser")
    public ModelAndView userFileUpload(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("admin/fileuploadForUser");

        User user = userDao.findById(id);
        response.addObject("user", user);

        log.info(" In fileupload with no Args");
        return response;
    }

    @PostMapping("/admin/fileUploadForUserSubmit")
    public ModelAndView userFileUploadSubmit(@RequestParam("file") MultipartFile file,
                                         @RequestParam Integer id) {
        ModelAndView response = new ModelAndView("redirect:/admin/showUserDetail?id=" + id);

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

    @GetMapping("/admin/showUserDetail")
    public ModelAndView userDetail(@RequestParam(required = false) Integer id) {
        ModelAndView response = new ModelAndView("admin/showUserDetail");
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

    @GetMapping("/admin/{userId}/showUserDetail")
    public ModelAndView showUserDetail(@PathVariable Integer userId) {
        ModelAndView response = new ModelAndView("admin/showUserDetail");

        User user = userDao.findById(userId);
        response.addObject("user", user);

        return response;
    }

    @PostMapping("/admin/{userId}/addTraining")
    public ModelAndView addTraining(@PathVariable Integer userId,
                                    @RequestParam Integer trainingId,
                                    @RequestParam String enrollmentDate,
                                    @RequestParam String status,
                                    RedirectAttributes redirectAttributes) throws ParseException {
        ModelAndView response = new ModelAndView("admin/addTraining");
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
        response.setViewName("redirect:/admin/" + userId + "/showUserDetail");

        return response;
    }


    @GetMapping("/admin/addTraining")
    public ModelAndView showAddTrainingForm(@RequestParam Integer userId) {
        ModelAndView response = new ModelAndView("admin/addTraining");

        List<Training> trainingList = trainingDao.findAll();

        response.addObject("userId", userId);
        response.addObject("trainingList", trainingList);

        return response;
    }

    @GetMapping("/admin/showAllUser")
    public ModelAndView showAllStaff() {
        ModelAndView response = new ModelAndView("admin/showAllUser");
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

    @GetMapping("/admin/showLoggedUser")
    public ModelAndView singleStaff() {
        ModelAndView response = new ModelAndView("admin/showLoggedUser");
        log.debug("In the user showSingleStaff controller method firstName");

        // Fetch current user logged
        User user = authenticatedUserService.loadCurrentUser();

        if (user != null) {
            response.addObject("userId", user.getId());
            response.addObject("user", user);
        }  else {
            log.warn("User not found");
        }

        return response;
    }

    @GetMapping("/admin/{userId}/showLoggedUser")
    public ModelAndView showSingleUser(@PathVariable Integer userId) {
        ModelAndView response = new ModelAndView("admin/showLoggedUser");

        User user = userDao.findById(userId);
        response.addObject("user", user);

        return response;
    }

    @GetMapping("/admin/{userId}/showUserProfile")
    public String showUserProfile(@PathVariable Integer userId) {

        ModelAndView response = new ModelAndView("admin/showUserProfile");

        UserProfileDTO userProfile = userService.getUserProfileById(userId);

        if (userProfile == null) {
            return "redirect:/error";
        }

        response.addObject("userProfile", userProfile);
        return String.valueOf(response);
    }

    @GetMapping("admin/showUserProfile")
    public String userProfile(@RequestParam("id") Integer userId, Model model) {
        log.debug("In the user profile controller method id: " + userId);

        UserProfileDTO userProfile = userService.getUserProfileById(userId);

        if (userProfile == null) {
            return "redirect:/error";
        }

        model.addAttribute("userProfile", userProfile);

        return "admin/showUserProfile";
    }
}
