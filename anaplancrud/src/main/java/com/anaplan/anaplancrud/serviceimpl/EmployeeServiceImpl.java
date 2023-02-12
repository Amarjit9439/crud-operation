package com.anaplan.anaplancrud.serviceimpl;

import com.anaplan.anaplancrud.dto.EmployeeDto;
import com.anaplan.anaplancrud.dto.Response;
import com.anaplan.anaplancrud.exception.EmployeeException;
import com.anaplan.anaplancrud.modal.Employee;
import com.anaplan.anaplancrud.repository.EmployeeRepository;
import com.anaplan.anaplancrud.service.EmployeeService;
import com.anaplan.anaplancrud.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    Environment env;

    @Override
    public Object addEmployee(EmployeeDto employeeDto) {
        try {
            logger.info("Inside addEmployee of EmployeeServiceImpl");
            if (addEmployeeValidation(employeeDto)) {
                logger.info("Getting Invalid data in request");
                throw new EmployeeException(env.getProperty(Constants.INVALID_DATA));
            }
            if (employeeExistByEmail(employeeDto.getEmail())) {
                logger.info("Employee already exist");
                throw new EmployeeException(env.getProperty(Constants.EMPLOYEE_ALREADY_EXIST));
            }
            Employee employee = new Employee();
            employee.setDepartment(employeeDto.getDepartment());
            employee.setEmail(employeeDto.getEmail());
            employee.setName(employeeDto.getName());
            employeeRepository.save(employee);
            return new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEE_SAVED_SUCCESSFULLY));

        } catch (Exception e) {
            String errorMessage = null;
            if (e instanceof EmployeeException) {
                errorMessage = ((EmployeeException) e).getMessage();
            } else {
                errorMessage = MessageFormat.format("Exception caught in addEmployee of EmployeeServiceImpl:{0}", e.getMessage());
            }
            throw new EmployeeException(errorMessage);
        }
    }

    @Override
    public Object updateEmployee(EmployeeDto employeeDto) {
        try {
            logger.info("Inside updateEmployee of EmployeeServiceImpl");
            if (employeeDto.getEmployeeId() == null) {
                throw new EmployeeException(env.getProperty(Constants.KINDLY_PROVIDE_EMPLOYEE_ID));
            }
            Optional<Employee> existedEmployee = employeeExistById(employeeDto.getEmployeeId());
            if (existedEmployee.isEmpty()) {
                throw new EmployeeException(env.getProperty(Constants.EMPLOYEE_DOES_NOT_EXIST));
            }
            Employee employee = existedEmployee.get();
            employee.setEmployeeId(employeeDto.getEmployeeId());
            employee.setDepartment(employeeDto.getDepartment()!=null?employeeDto.getDepartment():employee.getDepartment());
            employee.setEmail(employeeDto.getEmail()!=null?employeeDto.getEmail():employeeDto.getEmail());
            employee.setName(employeeDto.getName()!=null?employeeDto.getName():null);
            employeeRepository.save(employee);
            return new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEE_UPDATED_SUCCESSFULLY));

        } catch (Exception e) {
            String errorMessage = null;
            if (e instanceof EmployeeException) {
                errorMessage = ((EmployeeException) e).getMessage();
            } else {
                errorMessage = MessageFormat.format("Exception caught in updateEmployee of EmployeeServiceImpl:{0}", e.getMessage());
            }
            throw new EmployeeException(errorMessage);
        }
    }

    @Override
    public Object getAllEmployess() {
        try {
            logger.info("Inside getAllEmployess of EmployeeServiceImpl");
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            List<Employee> employeeList = employeeRepository.findAll();
            for (Employee employee : employeeList) {
                employeeDtoList.add(populateEmployeeData(employee));
            }
            return new Response<>(employeeDtoList, env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEES_FETCHED_SUCCESSFULLY));

        } catch (Exception e) {
            String errorMessage = null;
            if (e instanceof EmployeeException) {
                errorMessage = ((EmployeeException) e).getMessage();
            } else {
                errorMessage = MessageFormat.format("Exception caught in getAllEmployees of EmployeeServiceImpl:{0}", e.getMessage());
            }
            throw new EmployeeException(errorMessage);
        }
    }

    @Override
    public Object deleteEmployee(Long employeeId) {
        try {
            logger.info("Inside deleteEmployee of EmployeeServiceImpl");
            if (employeeId == null) {
                throw new EmployeeException(env.getProperty(Constants.KINDLY_PROVIDE_EMPLOYEE_ID));
            }
            Optional<Employee> existedEmployee = employeeExistById(employeeId);
            if (existedEmployee.isEmpty()) {
                throw new EmployeeException(env.getProperty(Constants.EMPLOYEE_DOES_NOT_EXIST));
            }
            employeeRepository.deleteById(employeeId);
            return new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEE_DELETED_SUCCESSFULLY));

        } catch (Exception e) {
            String errorMessage = null;
            if (e instanceof EmployeeException) {
                errorMessage = ((EmployeeException) e).getMessage();
            } else {
                errorMessage = MessageFormat.format("Exception caught in delete of EmployeeServiceImpl:{0}", e.getMessage());
            }
            throw new EmployeeException(errorMessage);
        }
    }

    @Override
    public Object getEmployeeById(Long employeeId) {
        try {
            logger.info("Inside getEmployeeById of EmployeeServiceImpl");
            if (employeeId == null) {
                throw new EmployeeException(env.getProperty(Constants.KINDLY_PROVIDE_EMPLOYEE_ID));
            }
            Optional<Employee> existedEmployee = employeeExistById(employeeId);
            if (existedEmployee.isEmpty()) {
                throw new EmployeeException(env.getProperty(Constants.EMPLOYEE_DOES_NOT_EXIST));
            }
            return new Response<>(populateEmployeeData(existedEmployee.get()), env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEE_FETCHED_SUCCESSFULLY));
        } catch (Exception e) {
            String errorMessage = null;
            if (e instanceof EmployeeException) {
                errorMessage = ((EmployeeException) e).getMessage();
            } else {
                errorMessage = MessageFormat.format("Exception caught in getEmployeeById of EmployeeServiceImpl:{0}", e.getMessage());
            }
            throw new EmployeeException(errorMessage);
        }
    }

    public boolean addEmployeeValidation(EmployeeDto employeeDto) {
        logger.info("Inside addEmployeeValidation");
        boolean flag = false;
        if (employeeDto.getDepartment() == null || employeeDto.getName() == null || employeeDto.getName() == null || employeeDto.getEmail() == null ||
        employeeDto.getDepartment().isEmpty() || employeeDto.getEmail().isBlank() || employeeDto.getName().isBlank()){
            flag = true;
        }
        return flag;
    }


    public boolean employeeExistByEmail(String employeeEmail) throws Exception {
        logger.info("Inside employeeExistByEmail Of EmployeeServiceImpl");
        boolean flag = false;
        Employee employee = employeeRepository.findByEmail(employeeEmail);
        if (employee != null) {
            flag = true;
        }
        return flag;
    }

    public Optional<Employee> employeeExistById(Long employeeId) throws Exception {
        logger.info("Inside employeeExistById of EmployeeServiceImpl");
        return employeeRepository.findById(employeeId);
    }

    public EmployeeDto populateEmployeeData(Employee employee) throws Exception {
        logger.info("Inside populateEmployeeData of EmployeeServiceImpl");
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setDepartment(employee.getDepartment());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        return employeeDto;
    }
}
