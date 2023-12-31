package org.perscholas.casestudy_staffhub.database.dao;


import org.perscholas.casestudy_staffhub.database.entity.UserTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrainingDAO extends JpaRepository<UserTraining, Long> {

    @Query("select ut FROM UserTraining ut WHERE ut.user.id = :userId")
    List<UserTraining> findByUserId(Integer userId);

    @Query("select ut FROM UserTraining ut WHERE ut.user.id = :userId AND ut.training.id= :trainingId")
    UserTraining findByUserIdAndTrainingId(Integer userId, Integer trainingId);

    @Query("SELECT count(ut) FROM UserTraining ut WHERE ut.user.id = :userId AND ut.training.id = :trainingId")
    Integer countByUserIdAndTrainingId(@Param("userId") Integer userId, @Param("trainingId") Integer trainingId);

}
