package org.perscholas.casestudy_staffhub.database.dao;

import org.perscholas.casestudy_staffhub.database.entity.UserTraining;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrainingDAO extends JpaRepository<UserTraining, Long> {
}
