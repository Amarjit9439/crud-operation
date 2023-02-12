package com.anaplan.anaplancrud.repository;

import com.anaplan.anaplancrud.modal.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public Employee findByEmail(String employeeEmail);
}
