package org.perscholas.casestudy_staffhub.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.perscholas.casestudy_staffhub.database.dao.TrainingDAO;
import org.perscholas.casestudy_staffhub.database.entity.Training;
import org.perscholas.casestudy_staffhub.formbean.TrainingFormBean;
import org.perscholas.casestudy_staffhub.service.TrainingService;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class TrainingController {

    @Autowired
    TrainingService trainingService;

    @Autowired
    UserService userService;

    @Autowired
    TrainingDAO trainingDao;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/training/create")
    public ModelAndView createTraining() {
        ModelAndView response = new ModelAndView("training/create");
        log.info("In create training with no args");

        return response;
    }

    @PostMapping("/training/createSubmit")
    public ModelAndView createTrainingSubmit(@Valid @ModelAttribute TrainingFormBean form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("########## In create training submit");
            ModelAndView response = new ModelAndView("training/create");

            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error: " + error.getDefaultMessage());
            }
            response.addObject("form", form);
            response.addObject("errors", bindingResult);

            return response;
        }

        Training t = trainingService.createTraining(form);

        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/training/edit/" + t.getId() + "?success=Training Saved Successfully");

        return response;
    }

    @GetMapping("/training/edit/{trainingId}")
    public ModelAndView editTraining(@PathVariable Integer trainingId, @RequestParam(required = false) String success) {
        ModelAndView response = new ModelAndView("training/create");

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
        } else {
            log.info("Training with id " + trainingId + " was not found");
        }

        response.addObject("form", form);

        return response;
    }

    @GetMapping("training/search")
    public ModelAndView search(@RequestParam(required = false) String trainingName){
        ModelAndView response = new ModelAndView("training/search");
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

    @GetMapping("/training/detail")
    public ModelAndView trainingDetail(@RequestParam(required = false) Integer id) {
        ModelAndView response = new ModelAndView("training/detail");
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

    @GetMapping("/training/fileupload")
    public ModelAndView fileUpload(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("training/fileupload");

        Training training = trainingDao.findById(id);
        response.addObject("training", training);

        log.info(" In fileupload with no Args");
        return response;
    }

    @PostMapping("/training/fileUploadSubmit")
    public ModelAndView fileUploadSubmit(@RequestParam("file") MultipartFile file,
                                         @RequestParam Integer id) {
        ModelAndView response = new ModelAndView("redirect:/training/detail?id=" + id);

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

    @GetMapping("/training/showAll")
    public ModelAndView showAllTraining() {
        ModelAndView response = new ModelAndView("training/showAll");
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
