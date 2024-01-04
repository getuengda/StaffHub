package org.perscholas.casestudy_staffhub.database.dao;

import jakarta.transaction.Transactional;
import org.perscholas.casestudy_staffhub.database.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentDAO extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE d.id = :id")
    public Department findById(Integer id);

    @Query("SELECT d FROM Department d WHERE d.departmentName LIKE :departmentName")
    List<Department> findDepartmentByName(String departmentName);

    @Modifying
    @Transactional
    int deleteDepartmentByDepartmentName(String departmentName);

}
