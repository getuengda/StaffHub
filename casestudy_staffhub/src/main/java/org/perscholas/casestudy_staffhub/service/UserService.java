package org.perscholas.casestudy_staffhub.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.perscholas.casestudy_staffhub.database.dao.DepartmentDAO;
import org.perscholas.casestudy_staffhub.database.dao.TrainingDAO;
import org.perscholas.casestudy_staffhub.database.dao.UserDAO;
import org.perscholas.casestudy_staffhub.database.dao.UserTrainingDAO;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.database.entity.Training;
import org.perscholas.casestudy_staffhub.database.entity.User;
import org.perscholas.casestudy_staffhub.database.entity.UserTraining;
import org.perscholas.casestudy_staffhub.formbean.RegisterUserFormBean;
import org.perscholas.casestudy_staffhub.formbean.UserFormBean;

import org.perscholas.casestudy_staffhub.formbean.UserProfileDTO;
import org.perscholas.casestudy_staffhub.formbean.UserTrainingFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Slf4j
@Service
public class UserService {

    @Autowired
    UserDAO userDao;

    @Autowired
    private DepartmentDAO departmentDao;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TrainingDAO trainingDao;

    @Autowired
    private UserTrainingDAO userTrainingDao;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(RegisterUserFormBean form) {
        User user = new User();

        user.setEmail(form.getEmail());

        String encoded = passwordEncoder.encode(form.getPassword());
        log.debug("Encoded password: " + encoded);
        user.setPassword(encoded);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        // this will create a date in  the database with the current time (right now)
        user.setJobTitle(form.getJobTitle());
        user.setAddress(form.getAddress());
        user.setOffice_Id(form.getOffice_Id());
        user.setImageUrl(form.getImageUrl());
        user.setCreateDate(new Date());

        return userDao.save(user);
    }

    public User createUser(UserFormBean form) {
        log.info("firstName: " + form.getFirstName());
        log.info("lastName: " + form.getLastName());
        log.info("email: " + form.getEmail());
        log.info("password: " + form.getPassword());
        log.info("job_title: " + form.getJobTitle());
        log.info("office_Id: " + form.getOffice_Id());
        log.info("address: " + form.getAddress());
        log.info("imageUrl: " + form.getImageUrl());


        User user = userDao.findById(form.getId());

        //Add departments to the model
        List<Department> departments = departmentService.getAllDepartments();

        Department department = null;
        Integer formId = form.getDepartmentId();

        for(Department dept : departments){
            if(dept.getId().equals(formId)){
                department = dept;
                break;
            }
        }

        if(department != null){
            department.getId();
        }else {
            log.info("Department not found");
        }

        // if the staff is null then we know that this is a create and we have to make a new object
        if ( user == null ) {
            user = new User();
        }

        user.setDepartment(department);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());

        String encoded = passwordEncoder.encode(form.getPassword());
        log.debug("Encoded password: " + encoded);
        user.setPassword(encoded);
        user.setJobTitle(form.getJobTitle());
        user.setAddress(form.getAddress());
        user.setOffice_Id(form.getOffice_Id());
        user.setCreateDate(new Date());
        user.setImageUrl(form.getImageUrl());

        return userDao.save(user);
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    public void addTraining(Integer userId, Integer trainingId, String enrollmentDate, String status) throws ParseException {

        Training training = trainingDao.findById(trainingId);
        User user = userDao.findById(userId);


        // if (employee.getTrainings().stream().noneMatch(et -> et.getTraining().equals(training))) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormatter.parse(enrollmentDate);

        UserTraining userTraining = new UserTraining();
        // employeeTraining.setEnrollmentDate(new Date());
        userTraining.setEnrollmentDate(date);
        userTraining.setUser(user);
        userTraining.setTraining(training);
        userTraining.setStatus(status);
        userTrainingDao.save(userTraining);
        //}
    }


        public UserProfileDTO getUserProfileById(Integer userId) {
        User user = userDao.findById(userId);

        if (user == null) {
            return null;
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


        return userProfileDTO;
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

    private void setDefaultTrainingValues(UserTrainingFormBean userTrainingBean) {
        userTrainingBean.setId(null);
        userTrainingBean.setUser(null);
        userTrainingBean.setTraining(null);
        userTrainingBean.setEnrollmentDate(new Date("N/A"));
        userTrainingBean.setCompletionDate(new Date("N/A"));
        userTrainingBean.setStatus("N/A");
    }
}
