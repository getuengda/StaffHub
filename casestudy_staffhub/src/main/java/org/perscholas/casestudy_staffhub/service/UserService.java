package org.perscholas.casestudy_staffhub.service;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.database.dao.UserDAO;
import org.perscholas.casestudy_staffhub.database.entity.User;
import org.perscholas.casestudy_staffhub.formbean.UserFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService {

    @Autowired
    UserDAO userDao;

    public User createUser(UserFormBean form) {
        log.info("firstName: " + form.getFirstName());
        log.info("lastName: " + form.getLastName());
        log.info("email: " + form.getEmail());
        log.info("password: " + form.getPassword());
        log.info("job_title: " + form.getJobTitle());
        log.info("office_Id: " + form.getOffice_Id());
        log.info("address: " + form.getAddress());
        log.info("imageUrl: " + form.getImageUrl());

        // if the form.id is null then this is a create - if it is not null then it is an edit
        // first we attempt to load it from the database ( basically we assume that it is going to be an edit )
        // if it was found in the database we know the incoming id was valid so we can edit it
        User user = userDao.findById(form.getId());



        // if the employee is null then we know that this is a create and we have to make a new object
        if ( user == null ) {
            user = new User();
        }

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
