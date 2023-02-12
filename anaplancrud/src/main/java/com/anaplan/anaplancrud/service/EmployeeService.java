package com.anaplan.anaplancrud.service;

import com.anaplan.anaplancrud.dto.EmployeeDto;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    public Object addEmployee(EmployeeDto employeeDto);

    public Object updateEmployee(EmployeeDto employeeDto);

    public Object getAllEmployess();

    public Object deleteEmployee(Long employeeId);

    public Object getEmployeeById(Long employeeId);

}
