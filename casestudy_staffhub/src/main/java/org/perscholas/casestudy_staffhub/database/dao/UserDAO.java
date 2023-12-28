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

    @Query("SELECT u FROM User u WHERE u.firstName LIKE :firstName or u.lastName LIKE :lastName")
    List<User> findUserByFirstNameOrLastName(String firstName, String lastName);

//    @Query("SELECT new com.example.dto.UserProfileDTO(u.id, u.firstName, u.lastName, u.email, u.jobTitle, u.imageUrl, u.department.id, u.department.name, u.description, u.userTrainings) FROM User u WHERE u.id = :userId")
//    UserProfileDTO getUserProfileById(@Param("userId") Integer userId);
//    @Query("SELECT u.id AS id, u.firstName AS firstName, u.lastName AS lastName, u.email AS email, " +
//            "u.jobTitle AS jobTitle, u.imageUrl AS imageUrl, u.department.id AS departmentId, " +
//            "u.department.name AS departmentName, u.description AS description, u.userTrainings AS userTrainings " +
//            "FROM User u WHERE u.id = :userId")
//    UserProfileProjection getUserProfileById(@Param("userId") Integer userId);

}
