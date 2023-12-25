package org.perscholas.casestudy_staffhub.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.database.dao.DepartmentDAO;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.formbean.DepartmentFormBean;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentDAO departmentDao;

    @GetMapping("/department/create")
    public ModelAndView create(){
        ModelAndView response = new ModelAndView("department/create");
        log.info("In create Department with no args");

        // Fetch all departments
        List<Department> departments = departmentService.getAllDepartments();

        // Add departments to the model
        response.addObject("departments", departments);

        return response;
    }

    @GetMapping("/department/createSubmit")
    public ModelAndView createDepartment(@Valid @ModelAttribute DepartmentFormBean form, BindingResult bindingResult ) {
        if(bindingResult.hasErrors()){
            log.info("########## In create employee submit");
            ModelAndView response = new ModelAndView("department/create");

            for(ObjectError error : bindingResult.getAllErrors()){
                log.info("error: " + error.getArguments());
            }
            response.addObject("form", form);
            response.addObject("errors", bindingResult);

            return response;
        }

        Department d = departmentService.createDepartment(form);

        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/department/edit/" + d.getId() + "?success=Department Saved Successfully");

        return response;
    }


    @GetMapping("/department/edit/{departmentId}")
    public ModelAndView edit(@PathVariable Integer departmentId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("department/create");

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

    @GetMapping("/department/search")
    public ModelAndView search(@RequestParam(required = false) String departmentName){
        ModelAndView response = new ModelAndView("department/search");
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
}
