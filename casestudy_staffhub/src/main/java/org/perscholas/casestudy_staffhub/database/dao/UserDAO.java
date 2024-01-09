package org.perscholas.casestudy_staffhub.database.dao;

import jakarta.transaction.Transactional;
import org.perscholas.casestudy_staffhub.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    public User findById(Integer id);

    public User findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE :firstName or u.lastName LIKE :lastName")
    List<User> findUserByFirstNameOrLastName(String firstName, String lastName);

    @Modifying
    @Transactional
    int deleteByFirstName(String lastName);

    @Query("SELECT count(u) FROM User u WHERE u.email = :email")
    Integer countByEmail(@Param("email") String email);
}
