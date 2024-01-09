package org.perscholas.casestudy_staffhub.controller;


import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.perscholas.casestudy_staffhub.database.dao.*;
import org.perscholas.casestudy_staffhub.database.entity.*;
import org.perscholas.casestudy_staffhub.formbean.*;
import org.perscholas.casestudy_staffhub.security.AuthenticatedUserService;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.perscholas.casestudy_staffhub.service.TrainingService;
import org.perscholas.casestudy_staffhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
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

    @Autowired
    private TrainingService trainingService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private static final String DATE_FORMAT = "yyyy-MM-dd";

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

    @GetMapping("/admin/createDepartmentSubmit")
    public ModelAndView createDepartment(@Valid @ModelAttribute DepartmentFormBean form, BindingResult bindingResult ) {
        if(bindingResult.hasErrors()){
            log.info("########## In create department submit");
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


    @GetMapping("/admin/editDepartment/{departmentId}")
    public ModelAndView edit(@PathVariable Integer departmentId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("admin/createDepartment");

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
            department.setDepartmentDetail(form.getDepartmentDetail());
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

    @GetMapping("/admin/addTraining")
    public ModelAndView showAddTrainingForm(@RequestParam Integer userId) {
        ModelAndView response = new ModelAndView("admin/addTraining");

        List<Training> trainingList = trainingDao.findAll();

        response.addObject("userId", userId);
        response.addObject("trainingList", trainingList);

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

        // check if user has the training in its List or not
        if(userTrainingDao.countByUserIdAndTrainingId(userId, trainingId) > 0){
            response.setViewName("staff/addTraining");
            response.addObject("errorMessage", "Training already added");
            return response;
        }

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

    @PostMapping("/admin/{userId}/editTraining/{trainingId}")
    public ModelAndView editTraining(@PathVariable Integer userId,
                                     @PathVariable Integer trainingId,
                                     @RequestParam(required = false) String enrollmentDate,
                                     @RequestParam(required = false) String completionDate,
                                     @RequestParam String status,
                                     RedirectAttributes redirectAttributes) throws ParseException {
        ModelAndView response = new ModelAndView("admin/editTraining");
        log.debug("Editing training for userId: " + userId);

        // Fetch single user by id
        User user = authenticatedUserService.loadCurrentUser();
        //User user = userDao.findById(userId);
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

    @GetMapping("/admin/{userId}/editTraining/{trainingId}")
    public ModelAndView showEditTrainingForm(@PathVariable Integer userId, @PathVariable Integer trainingId) {
        ModelAndView response = new ModelAndView("admin/editTraining");

        UserTraining userTraining = userTrainingDao.findByUserIdAndTrainingId(userId, trainingId);
        List<Training> trainingList = trainingDao.findAll();

        response.addObject("userId", userId);
        response.addObject("trainingId", trainingId);
        response.addObject("userTraining", userTraining);
        response.addObject("trainingList", trainingList);

        return response;
    }

    @GetMapping("/admin/showAllUser")
    public ModelAndView showAllStaff(@RequestParam(required = false) Integer departmentId) {
        ModelAndView response = new ModelAndView("admin/showAllUser");
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

    @GetMapping("/admin/showProfile")
    public ModelAndView userProfile(){
        ModelAndView response = new ModelAndView("admin/showProfile");
        log.debug("In the user show profile controller method firstName");

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

    @PostMapping("/admin/showProfileSubmit")
    public String userProfileSubmit(@PathVariable Integer userId, RedirectAttributes redirectAttributes) {

        UserProfileDTO userProfile = userService.getUserProfileById(userId);
        if (userProfile == null) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        redirectAttributes.addFlashAttribute("success", "Staff Saved Successfully");
        return "redirect:/admin/showProfile/" + userId;
    }


    @PostMapping("/admin/editShowProfile/{userId}")
    public ModelAndView editUserProfile(@PathVariable Integer userId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("admin/showProfile");

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
        userProfileDTO.setDepartmentDetail(department.getDepartmentDetail());
    }

    private void setDefaultDepartmentValues(UserProfileDTO userProfileDTO) {
        userProfileDTO.setDepartmentId(null);
        userProfileDTO.setDepartmentName("N/A");
        userProfileDTO.setDescription("N/A");
        userProfileDTO.setDepartmentDetail("N/A");
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

    @GetMapping("/admin/createTraining")
    public ModelAndView createTraining() {
        ModelAndView response = new ModelAndView("admin/createTraining");
        log.info("In create training with no args");

        return response;
    }

    @PostMapping("/admin/createTrainingSubmit")
    public ModelAndView createTrainingSubmit(@Valid @ModelAttribute TrainingFormBean form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("########## In create training submit");
            ModelAndView response = new ModelAndView("admin/createTraining");

            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error: " + error.getDefaultMessage());
            }
            response.addObject("form", form);
            response.addObject("errors", bindingResult);

            return response;
        }

        Training t = trainingService.createTraining(form);

        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/admin/editTraining/" + t.getId() + "?success=Training Saved Successfully");

        return response;
    }

    @GetMapping("/admin/editTraining/{trainingId}")
    public ModelAndView editTraining(@PathVariable Integer trainingId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("admin/createTraining");

        Training training = trainingDao.findById(trainingId);

        if (!StringUtils.isEmpty(success)) {
            response.addObject("success", success);
        }

        TrainingFormBean form = new TrainingFormBean();

        if (training != null) {
            form.setId(training.getId());
            form.setTrainingName(training.getTrainingName());
            form.setDatePosted(training.getDatePosted() != null ? training.getDatePosted().toString() : null);
            form.setDescription(training.getDescription());
            form.setPrerequisite(training.getPrerequisite());
            form.setImageUrl(training.getImageUrl());
            form.setTrainingDetail(training.getTrainingDetail());
        } else {
            log.info("Training with id " + trainingId + " was not found");
        }

        response.addObject("form", form);

        return response;
    }

    @GetMapping("/admin/searchTraining")
    public ModelAndView searchTraining(@RequestParam(required = false) String trainingName){
        ModelAndView response = new ModelAndView("admin/searchTraining");
        log.info("In the training search controller method trainingName: " + trainingName);

        if(!StringUtils.isEmpty(trainingName)){
            response.addObject("trainingName", trainingName);

            if(!StringUtils.isEmpty(trainingName)){
                trainingName = "%" + trainingName + "%";
            }

            List<Training> trainings = trainingDao.findTrainingByName(trainingName);
            response.addObject("trainingVar", trainings);

            for(Training training : trainings){
                log.info("training: id= " + training.getId() + "training Name = " + training.getTrainingName());
                log.info("training: id= " + training.getId() + "description = " + training.getDescription());
            }
        }
        return response;
    }

    @GetMapping("/admin/detailTraining")
    public ModelAndView trainingDetail(@RequestParam(required = false) Integer id) {
        ModelAndView response = new ModelAndView("admin/detailTraining");
        log.debug("In the training detail controller method id: " + id);

        // Fetch single training by id
        Training training = trainingDao.findById(id);

        if (training != null) {
            response.addObject("training", training);
        } else {
            log.warn("Training with id " + id + " not found");
        }

        return response;
    }

    @GetMapping("/admin/fileuploadTraining")
    public ModelAndView fileUploadTraining(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("admin/fileuploadTraining");

        Training training = trainingDao.findById(id);
        response.addObject("training", training);

        log.info(" In fileupload with no Args");
        return response;
    }

    @PostMapping("/admin/fileUploadTrainingSubmit")
    public ModelAndView fileUploadTrainingSubmit(@RequestParam("file") MultipartFile file,
                                         @RequestParam Integer id) {
        ModelAndView response = new ModelAndView("redirect:/admin/detailTraining?id=" + id);

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

        Training training = trainingDao.findById(id);
        training.setImageUrl("/pub/images/" + file.getOriginalFilename());
        trainingDao.save(training);

        return response;
    }

    @GetMapping("/admin/showAllTraining")
    public ModelAndView showAllTraining() {
        ModelAndView response = new ModelAndView("admin/showAllTraining");
        log.info("In the training showAllTraining controller method");

        // Fetch all trainings
        List<Training> trainings = trainingService.getAllTrainings();

        // Add trainings to the model
        response.addObject("trainingVar", trainings);

        for(Training training : trainings){
            log.info("training: id= " + training.getId() + "Training Name = " + training.getTrainingName());
        }

        return response;
    }

}
