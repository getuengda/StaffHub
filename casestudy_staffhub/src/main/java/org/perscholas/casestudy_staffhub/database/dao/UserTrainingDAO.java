package org.perscholas.casestudy_staffhub.database.dao;


import org.perscholas.casestudy_staffhub.database.entity.UserTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrainingDAO extends JpaRepository<UserTraining, Long> {

    @Query("select ut FROM UserTraining ut WHERE ut.user.id = :userId")
    List<UserTraining> findByUserId(Integer userId);

}
