package org.perscholas.casestudy_staffhub.service;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.casestudy_staffhub.database.dao.TrainingDAO;
import org.perscholas.casestudy_staffhub.database.entity.Training;
import org.perscholas.casestudy_staffhub.formbean.TrainingFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TrainingService {

    @Autowired
    private TrainingDAO trainingDao;


    public Training createTraining(TrainingFormBean form) {
        log.info("trainingName: " + form.getTrainingName());
        log.info("datePosted: " + form.getDatePosted());
        log.info("description: " + form.getDescription());
        log.info("prerequisite: " + form.getPrerequisite());
        log.info("imageUrl" +  form.getImageUrl());

        Training training = trainingDao.findById(form.getId());

        if(training == null ){
            training = new Training();
        }

        training.setTrainingName(form.getTrainingName());
        training.setDatePosted(new Date());
        training.setDescription(form.getDescription());
        training.setPrerequisite(form.getPrerequisite());
        training.setImageUrl(form.getImageUrl());
        training.setTrainingDetail(form.getTrainingDetail());

        return trainingDao.save(training);
    }

    public List<Training> getAllTrainings(){
        return trainingDao.findAll();
    }
}
