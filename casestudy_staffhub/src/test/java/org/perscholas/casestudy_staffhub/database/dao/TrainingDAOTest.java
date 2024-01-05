package org.perscholas.casestudy_staffhub.database.dao;

import org.junit.jupiter.api.*;
import org.perscholas.casestudy_staffhub.database.entity.Training;
import org.perscholas.casestudy_staffhub.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrainingDAOTest {

    @Autowired
    private TrainingDAO trainingDao;

    @Autowired
    private TrainingService trainingService;

    @Test
    @Order(1)
    public void createTrainingTest(){

        // given
        Training training = new Training();
        training.setTrainingName("Java First");
        training.setDescription("Core Language");
        training.setPrerequisite("Interest and IT proficiency");
        training.setDatePosted(new Date());
        training.setImageUrl("image url");

        // when
        trainingDao.save(training);

        // then
        Assertions.assertNotNull(training.getId());
        Assertions.assertEquals("Java First", training.getTrainingName());
        Assertions.assertEquals("Core Language", training.getDescription());
        Assertions.assertEquals("Interest and IT proficiency", training.getPrerequisite());
        Assertions.assertEquals("Interest and IT proficiency", training.getPrerequisite());
        Assertions.assertEquals(LocalDate.now().toString(), training.getDatePosted().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        Assertions.assertEquals("image url", training.getImageUrl());
    }

    @Test
    @Order(2)
    public void findByTrainingName(){

        // given
        String trainingName = "Java First";

        // when
        List<Training> trainings = trainingService.getAllTrainings();


        // then
        int lastIndex = trainings.size() - 1;
        Training training = trainings.get(lastIndex);

        Assertions.assertNotNull(training.getId());
        Assertions.assertEquals("Java First", training.getTrainingName());
        Assertions.assertEquals("Core Language", training.getDescription());
        Assertions.assertEquals("Interest and IT proficiency", training.getPrerequisite());
        Assertions.assertEquals("Interest and IT proficiency", training.getPrerequisite());
        Assertions.assertEquals(LocalDate.now().toString(), training.getDatePosted().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        Assertions.assertEquals("image url", training.getImageUrl());

    }

    @Test
    @Order(3)
    public void deleteTrainingTest(){

        // given
        String trainingName = "Java First";
        List<Training> trainings = trainingDao.findTrainingByName(trainingName);

        // When
        int deleted = trainingDao.deleteByTrainingName(trainingName);

        // then
        Assertions.assertEquals(1, deleted);
    }

    @Test
    @Order(4)
    public void shouldNotExistTest(){

        // given
        String trainingName = "Java First";

        // when
        List<Training> trainings = trainingDao.findTrainingByName(trainingName);

        // then
        Assertions.assertEquals(0, trainings.size());
    }
}
