package com.anaplan.anaplancrud.controller;

import com.anaplan.anaplancrud.dto.EmployeeDto;
import com.anaplan.anaplancrud.service.EmployeeService;
import com.anaplan.anaplancrud.utility.UrlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping(value = UrlConstants.ADD_EMPLOYEE)
    public ResponseEntity<Object> addEmployee(@RequestBody EmployeeDto employeeDto) {
        logger.info("Request for addEmployee of EmployeeController :{}", employeeDto);
        return new ResponseEntity<>(employeeService.addEmployee(employeeDto), HttpStatus.OK);
    }

    @PutMapping(value = UrlConstants.UPDATE_EMPLOYEE)
    public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        logger.info("Request for updateEmployee of EmployeeController :{}", employeeDto);
        return new ResponseEntity<>(employeeService.updateEmployee(employeeDto), HttpStatus.OK);
    }

    @GetMapping(UrlConstants.GET_ALL_EMPLOYEES)
    public ResponseEntity<Object> getAllEmployee() {
        logger.info("Request for getAllEmployee of EmployeeController");
        return new ResponseEntity<>(employeeService.getAllEmployess(), HttpStatus.OK);
    }

    @GetMapping(UrlConstants.GET_EMPLOYEE_BY_ID)
    public ResponseEntity<Object> getEmployeeBYId(@RequestParam Long employeeId) {
        logger.info("Request for getEmployeeBYId of EmployeeController");
        return new ResponseEntity<>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
    }

    @DeleteMapping(UrlConstants.DELETE_EMPLOYEE)
    public ResponseEntity<Object> deleteEmployeeBYId(@RequestParam Long employeeId) {
        logger.info("Request for deleteEmployeeBYId of EmployeeController");
        return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
    }

}
