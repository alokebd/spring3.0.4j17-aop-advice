package com.vision.aop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vision.aop.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
