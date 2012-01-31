package com.oracle.model;

import java.math.BigDecimal;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface HRFacade {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    Departments persistDepartments(Departments departments);

    Departments mergeDepartments(Departments departments);

    void removeDepartments(Departments departments);

    List<Departments> getDepartmentsFindAll();

    Employees persistEmployees(Employees employees);

    Employees mergeEmployees(Employees employees);

    void removeEmployees(Employees employees);

    List<Employees> getEmployeesFindAll();

    List<Employees> getEmployeesFindBySal(String _salary);


}
