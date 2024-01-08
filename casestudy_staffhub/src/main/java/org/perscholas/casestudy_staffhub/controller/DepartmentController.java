package org.perscholas.casestudy_staffhub.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.perscholas.casestudy_staffhub.database.dao.DepartmentDAO;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.perscholas.casestudy_staffhub.formbean.DepartmentFormBean;
import org.perscholas.casestudy_staffhub.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
            log.info("########## In create user submit");
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
            form.setDepartmentDetail(department.getDepartmentDetail());
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

    @GetMapping("/department/detail")
    public ModelAndView departmentDetail(@RequestParam(required = false) Integer id) {
        ModelAndView response = new ModelAndView("department/detail");
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

    @GetMapping("/department/fileupload")
    public ModelAndView fileUpload(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("department/fileupload");

        Department department = departmentDao.findById(id);
        response.addObject("department", department);

        log.info(" In fileupload with no Args");
        return response;
    }

    @PostMapping("/department/fileUploadSubmit")
    public ModelAndView fileUploadSubmit(@RequestParam("file") MultipartFile file,
                                         @RequestParam Integer id) {
        ModelAndView response = new ModelAndView("redirect:/department/detail?id=" + id);

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

    @GetMapping("/department/showAll")
    public ModelAndView showAllDepartment() {
        ModelAndView response = new ModelAndView("department/showAll");
        log.info("In the department showAllDepartment controller method firstName");

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
}
