package org.perscholas.casestudy_staffhub.service;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.database.dao.DepartmentDAO;
import org.perscholas.casestudy_staffhub.database.dao.UserDAO;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.database.entity.User;
import org.perscholas.casestudy_staffhub.formbean.UserFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // if the employee is null then we know that this is a create and we have to make a new object
        if ( user == null ) {
            user = new User();
        }

        user.setDepartment(department);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setJobTitle(form.getJobTitle());
        user.setAddress(form.getAddress());
        user.setOffice_Id(form.getOffice_Id());
        user.setImageUrl(form.getImageUrl());

        return userDao.save(user);
    }

}
