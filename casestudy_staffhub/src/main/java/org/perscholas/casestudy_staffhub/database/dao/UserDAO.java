package org.perscholas.casestudy_staffhub.database.dao;

import org.perscholas.casestudy_staffhub.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    public User findById(Integer id);

    public User findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE :firstName and u.lastName LIKE :lastName")
    List<User> findByUserFirstNameAndLastName(String firstName, String lastName);

}
