package org.perscholas.casestudy_staffhub.database.dao;

import jakarta.transaction.Transactional;
import org.perscholas.casestudy_staffhub.database.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDAO extends JpaRepository<UserRole, Long> {

    public List<UserRole> findByUserId(Integer userId);

    @Modifying
    @Transactional
    int deleteByUserId(Integer userId);
}
