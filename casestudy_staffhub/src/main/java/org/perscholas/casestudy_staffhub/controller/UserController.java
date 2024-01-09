package org.perscholas.casestudy_staffhub.controller;


import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.perscholas.casestudy_staffhub.database.dao.*;
import org.perscholas.casestudy_staffhub.database.entity.*;
import org.perscholas.casestudy_staffhub.formbean.UserFormBean;
import org.perscholas.casestudy_staffhub.formbean.UserProfileDTO;
import org.perscholas.casestudy_staffhub.formbean.UserTrainingFormBean;
import org.perscholas.casestudy_staffhub.security.AuthenticatedUserService;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.perscholas.casestudy_staffhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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

    @Autowired
    UserRoleDAO userRoleDao;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    // Added to resolve Unparseable date: "", when I'm editing userTraining information and submit dateCompletion empty
    // because that might happen like status could be In Progress
    // This method will be called before each request is processed by the controller’s handler methods
    @InitBinder
    public void allowEmptyDateBinding( WebDataBinder binder ) {
        //Custom Date Editor
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
        simpleDateFormat.setLenient(false);
        binder.registerCustomEditor( Date.class, new CustomDateEditor( simpleDateFormat,true));
    }

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

        UserRole userRole = new UserRole();
        userRole.setUserId(u.getId());
        userRole.setRoleName(form.getUserType());
        userRoleDao.save(userRole);

        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/staff/edit/" + u.getId() + "?success=Staff Saved Successfully");

        return response;
    }

    @GetMapping("/staff/myUser")
    public ModelAndView myStaff() {
        ModelAndView response = new ModelAndView("staff/create");
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

    @GetMapping("/staff/addTraining")
    public ModelAndView showAddTrainingForm(@RequestParam Integer userId) {
        ModelAndView response = new ModelAndView("staff/addTraining");

        List<Training> trainingList = trainingDao.findAll();

        response.addObject("userId", userId);
        response.addObject("trainingList", trainingList);

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

        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = dateFormatter.parse(enrollmentDate);

        // check if user has the training in its List or not
        if(userTrainingDao.countByUserIdAndTrainingId(userId, trainingId) > 0){
            response.setViewName("staff/addTraining");
            response.addObject("errorMessage", "Training already added");
            return response;
        }

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

    @PostMapping("/staff/{userId}/editTraining/{trainingId}")
    public ModelAndView editTraining(@PathVariable Integer userId,
                                     @PathVariable Integer trainingId,
                                     @RequestParam(required = false) String enrollmentDate,
                                     @RequestParam(required = false) String completionDate,
                                     @RequestParam String status,
                                     RedirectAttributes redirectAttributes) throws ParseException {
        ModelAndView response = new ModelAndView("staff/editTraining");
        log.debug("Editing training for userId: " + userId);

        // Fetch single user by id
        User user = authenticatedUserService.loadCurrentUser();

        userId = user.getId();
        Training training = trainingDao.findById(trainingId);

        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = dateFormatter.parse(enrollmentDate);
        Date date1 = dateFormatter.parse(completionDate);

        UserTraining userTraining = userTrainingDao.findByUserIdAndTrainingId(userId, trainingId);
        userTraining.setEnrollmentDate(date);
        userTraining.setCompletionDate(date1);
        userTraining.setUser(user);
        userTraining.setTraining(training);
        userTraining.setStatus(status);
        userTrainingDao.save(userTraining);

        response.addObject("user", user);
        //response.setViewName("redirect:/staff/" + userId + "/profile");

        return response;
    }

    @GetMapping("/staff/{userId}/editTraining/{trainingId}")
    public ModelAndView showEditTrainingForm(@PathVariable Integer userId, @PathVariable Integer trainingId) {
        ModelAndView response = new ModelAndView("staff/editTraining");

        UserTraining userTraining = userTrainingDao.findByUserIdAndTrainingId(userId, trainingId);
        List<Training> trainingList = trainingDao.findAll();

        response.addObject("userId", userId);
        response.addObject("trainingId", trainingId);
        response.addObject("userTraining", userTraining);
        response.addObject("trainingList", trainingList);

        return response;
    }

    @GetMapping("/staff/showAll")
    public ModelAndView showAllStaff(@RequestParam(required = false) Integer departmentId) {
        ModelAndView response = new ModelAndView("staff/showAll");
        log.debug("In the user showAllStaff or Sort By Department controller method firstName");

        List<User> users;

        List<Department> departments = departmentService.getAllDepartments();

        if(departmentId != null){
            users = userDao.sortByDepartment(departmentId);
        } else {
            users = userService.getAllUsers();
        }

        // Add users and departments to the model
        response.addObject("userVar", users);
        response.addObject("departmentVar", departments);

        for(User user : users){
            log.debug("user: id= " + user.getId() + "first name = " + user.getFirstName());
            log.debug("user: id= " + user.getId() + "last name = " + user.getLastName());
        }

        return response;
    }

    @RequestMapping(value = "/staff/showUser", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView singleStaff(@RequestParam(required = false) Integer id) {
        ModelAndView response = new ModelAndView("staff/showUser");
        log.debug("In the user showSingleStaff controller method firstName");

        // Fetch single user by id
        User user = authenticatedUserService.loadCurrentUser();

        // Find the user in the database
        Optional<User> optionalUser = Optional.ofNullable(userDao.findById(user.getId()));

        if (user != null) {
            response.addObject("user", user);
        } else {
            log.warn("User with id " + id + " not found");
        }

        return response;
    }

    @RequestMapping(value = "/staff/{userId}/showUser", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView showSingleUser(@PathVariable Integer userId) {
        ModelAndView response = new ModelAndView("staff/showUser");

        User user = userDao.findById(userId);
        response.addObject("user", user);

        return response;
    }


    @GetMapping("/staff/profile")
    public ModelAndView userProfile(){
        ModelAndView response = new ModelAndView("staff/profile");
        log.debug("In the user staff profile controller method firstName");

        // Fetch single user by id
        User user = authenticatedUserService.loadCurrentUser();

        UserProfileDTO userProfile = userService.getUserProfileById(user.getId());

        if (userProfile == null) {
            throw new ResourceNotFoundException("User not found with id " + user.getId());
        } else{
            response.addObject("userProfile", userProfile);
            response.addObject("user", user);
        }
        return response;
    }

    @PostMapping("/staff/profileSubmit")
    public String userProfileSubmit(@PathVariable Integer userId, RedirectAttributes redirectAttributes) {

        UserProfileDTO userProfile = userService.getUserProfileById(userId);
        if (userProfile == null) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        redirectAttributes.addFlashAttribute("success", "Staff Saved Successfully");
        return "redirect:/staff/profile/" + userId;
    }


    @PostMapping("/staff/editProfile/{userId}")
    public ModelAndView editUserProfile(@PathVariable Integer userId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("staff/profile");

        User user = userDao.findById(userId);


        if (!StringUtils.isEmpty(success)) {
            response.addObject("success", success);
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setId(user.getId());
        userProfileDTO.setFirstName(user.getFirstName());
        userProfileDTO.setLastName(user.getLastName());
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setJobTitle(user.getJobTitle());
        userProfileDTO.setOffice_Id(user.getOffice_Id());
        userProfileDTO.setAddress(user.getAddress());
        userProfileDTO.setImageUrl(user.getImageUrl());

        // Checking if user and department are not null before accessing their attributes
        if (user.getDepartment() != null) {
            populateDepartmentDetails(userProfileDTO, user.getDepartment());
        } else {
            setDefaultDepartmentValues(userProfileDTO);
        }


        List<UserTraining> userTrainings = userTrainingDao.findByUserId(user.getId());

        List<UserTrainingFormBean> userTrainingBeans = new ArrayList<>();
        for (UserTraining userTraining : userTrainings) {
            UserTrainingFormBean userTrainingBean = populateTrainingDetails(userTraining);
            userTrainingBeans.add(userTrainingBean);
        }

        userProfileDTO.setUserTrainings(userTrainingBeans);

        response.addObject("userProfile", userProfileDTO);
        response.addObject("user", user);

        return response;
    }

    private void populateDepartmentDetails(UserProfileDTO userProfileDTO, Department department) {
        userProfileDTO.setDepartmentId(department.getId());
        userProfileDTO.setDepartmentName(department.getDepartmentName());
        userProfileDTO.setDescription(department.getDescription());
    }

    private void setDefaultDepartmentValues(UserProfileDTO userProfileDTO) {
        userProfileDTO.setDepartmentId(null);
        userProfileDTO.setDepartmentName("N/A");
        userProfileDTO.setDescription("N/A");
    }

    private UserTrainingFormBean populateTrainingDetails(UserTraining userTraining) {
        UserTrainingFormBean userTrainingBean = new UserTrainingFormBean();
        userTrainingBean.setId(userTraining.getId());
        userTrainingBean.setUser(userTraining.getUser());
        userTrainingBean.setTraining(userTraining.getTraining());
        userTrainingBean.setEnrollmentDate(userTraining.getEnrollmentDate());
        userTrainingBean.setCompletionDate(userTraining.getCompletionDate());
        userTrainingBean.setStatus(userTraining.getStatus());
        return userTrainingBean;
    }


}
