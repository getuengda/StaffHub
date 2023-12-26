package org.perscholas.casestudy_staffhub.database.dao;

import org.perscholas.casestudy_staffhub.database.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingDAO extends JpaRepository<Training, Long> {

    public Training findById(Integer id);

    List<Training> findAll();

    @Query("SELECT t FROM Training t WHERE t.id = :userId")
    List<Training> findByUserId(Integer userId);

    @Query("SELECT t FROM Training t WHERE t.trainingName LIKE :trainingName")
    List<Training> findTrainingByName(String trainingName);
}
