package org.perscholas.casestudy_staffhub.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.database.dao.TrainingDAO;
import org.perscholas.casestudy_staffhub.database.entity.Training;
import org.perscholas.casestudy_staffhub.formbean.TrainingFormBean;
import org.perscholas.casestudy_staffhub.service.TrainingService;
import org.perscholas.casestudy_staffhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class TrainingController {

    @Autowired
    TrainingService trainingService;

    @Autowired
    UserService userService;

    @Autowired
    TrainingDAO trainingDao;

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
            log.warn("Training with id " + trainingId + " was not found");
        }

        response.addObject("form", form);

        return response;
    }

}
