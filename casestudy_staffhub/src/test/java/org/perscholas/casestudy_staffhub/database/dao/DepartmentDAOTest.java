package org.perscholas.casestudy_staffhub.database.dao;

import org.junit.jupiter.api.*;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentDAOTest {

    @Autowired
    private DepartmentDAO departmentDao;

    @Autowired
    private DepartmentService departmentService;


    @Test
    @Order(1)
    public void createDepartmentTest(){

        //given
        Department department = new Department();
        department.setDepartmentName("First Department");
        department.setDescription("Programing will be re-engineered here");
        department.setImageUrl("image url");

        // when save it to the db
        departmentDao.save(department);

        // then
        Assertions.assertNotNull(department.getId());
        Assertions.assertEquals("First Department", department.getDepartmentName());
        Assertions.assertEquals("Programing will be re-engineered here", department.getDescription());
        Assertions.assertEquals("image url", department.getImageUrl());

    }

    @Test
    @Order(2)
    public void findByDepartmentName(){
        // given
        String departmentName = "First Department";

        // when
        List<Department> departments = departmentDao.findDepartmentByName(departmentName);

        // then
        Department department = departments.get(0);

        Assertions.assertNotNull(department.getId());
        Assertions.assertEquals("First Department", department.getDepartmentName());
        Assertions.assertEquals("Programing will be re-engineered here", department.getDescription());
        Assertions.assertEquals("image url", department.getImageUrl());
    }

    @Test
    @Order(3)
    public void deleteDepartmentTest(){

        // given
        String departmentName = "First Department";
        List<Department> departments = departmentDao.findDepartmentByName(departmentName);

        // when
        int deleted = departmentDao.deleteDepartmentByDepartmentName(departmentName);

        // then
        Assertions.assertEquals(1, deleted);
    }

    @Test
    @Order(4)
    public void shouldNotExistTest(){

        // given
        String departmentName = "First Department";

        // when
        List<Department> departments = departmentDao.findDepartmentByName(departmentName);

        // then
        Assertions.assertEquals(0, departments.size());
    }
}
