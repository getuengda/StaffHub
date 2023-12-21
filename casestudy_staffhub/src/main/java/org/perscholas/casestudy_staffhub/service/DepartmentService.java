package org.perscholas.casestudy_staffhub.service;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.database.dao.DepartmentDAO;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.formbean.DepartmentFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DepartmentService {

    @Autowired
    private DepartmentDAO departmentDao;


    public Department createDepartment(DepartmentFormBean form){

        log.info("id " + form.getId());
        log.info("departmentName " + form.getDepartmentName());
        log.info("description " + form.getDescription());
        log.info("imageUrl " + form.getImageUrl());

        Department department = departmentDao.findById(form.getId());

        if(department == null){
            department = new Department();
        }

        department.setDepartmentName(form.getDepartmentName());
        department.setDescription(form.getDescription());
        department.setImageUrl(form.getImageUrl());

        return departmentDao.save(department);
    }

    public List<Department> getAllDepartments() {

        return departmentDao.findAll();
    }
}
