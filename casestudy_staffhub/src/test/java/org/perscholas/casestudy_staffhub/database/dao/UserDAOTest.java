package org.perscholas.casestudy_staffhub.database.dao;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.junit.jupiter.api.*;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.database.entity.User;
import org.perscholas.casestudy_staffhub.database.entity.UserRole;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private DepartmentService departmentService;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleDAO userRoleDao;


    @Test
    @Order(1)
    public void createUserTest() {

        // given
        User user = new User();
        user.setFirstName("Senai");
        user.setLastName("Jhon");
        user.setEmail("senai@staff.com");
        user.setPassword(passwordEncoder.encode("Password123456"));
        user.setCreateDate(new Date());

        List<Department> departments = departmentService.getAllDepartments();

        Department department = null;

        for(Department dept : departments){
            if(dept != null){
                department = dept;
                break;
            }
        }

        if(department != null){
            user.setDepartment(department);
        }
        user.setJobTitle("Software Engineer");
        user.setAddress("ATL");
        user.setOffice_Id(303);
        user.setImageUrl("image url");
        user.setUserType("USER");

        // when save it to the db
        user = userDao.save(user);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleName(user.getUserType());

        userRoleDao.save(userRole);

        // then
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("Senai", user.getFirstName());
        Assertions.assertEquals("Jhon", user.getLastName());
        Assertions.assertEquals("senai@staff.com", user.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("Password123456", user.getPassword()));
        Assertions.assertEquals(LocalDate.now().toString(), user.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        Assertions.assertEquals(department, user.getDepartment());
        Assertions.assertEquals("Software Engineer", user.getJobTitle());
        Assertions.assertEquals("ATL", user.getAddress());
        Assertions.assertEquals(303, user.getOffice_Id());
        Assertions.assertEquals("image url", user.getImageUrl());
        Assertions.assertEquals("USER", user.getUserType());
        Assertions.assertEquals(userRole.getRoleName(), user.getUserType());

    }

    @Test
    @Order(2)
    public void findByFirstNameOrLastName(){
        // given
        String firstName = "Senai";

        // when
        List<Department> departments = departmentService.getAllDepartments();
        List<User> users = userDao.findUserByFirstNameOrLastName(firstName, null);

        Department department = null;

        for(Department dept : departments){
            if(dept != null){
                department = dept;
                break;
            }
        }

        // then
        Assertions.assertEquals(1, users.size());

        User user = users.get(0);
        if(department != null){
            user.setDepartment(department);
        }

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleName(user.getUserType());

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("Senai", user.getFirstName());
        Assertions.assertEquals("Jhon", user.getLastName());
        Assertions.assertEquals("senai@staff.com", user.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("Password123456", user.getPassword()));
        Assertions.assertEquals(LocalDate.now().toString(), user.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        Assertions.assertEquals(department, user.getDepartment());
        Assertions.assertEquals("Software Engineer", user.getJobTitle());
        Assertions.assertEquals("ATL", user.getAddress());
        Assertions.assertEquals(303, user.getOffice_Id());
        Assertions.assertEquals("image url", user.getImageUrl());
        Assertions.assertEquals("USER", user.getUserType());
        Assertions.assertEquals(userRole.getRoleName(), user.getUserType());

    }

    @Test
    @Order(3)
    public void deleteUserTest() {
        // given
        String firstName = "Senai";
        List<User> users = userDao.findUserByFirstNameOrLastName(firstName, null);

        Integer userId = null;
        for(User user : users){
            userId = user.getId();
        }

        // when
        List<UserRole> userRoles = userRoleDao.findByUserId(userId);
        int deletedFromUserRole = 0;
        if (userId != null) {
            deletedFromUserRole = userRoleDao.deleteByUserId(userId);
        }
        int deleted = userDao.deleteByFirstName(firstName);

        // then
        Assertions.assertEquals(1, deleted);
    }

    @Test
    @Order(4)
    public void shouldNotExistTest() {
        // given
        String firstName = "Senai";

        // when
        List<User> users = userDao.findUserByFirstNameOrLastName(firstName, null);

        // then
        Assertions.assertEquals(0, users.size());
    }
}

