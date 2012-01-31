package com.oracle.model;

import java.math.BigDecimal;

import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "HRFacade", mappedName = "HR-BusinessObjects-HRFacade")
public class HRFacadeBean implements HRFacade, HRFacadeLocal {
    @PersistenceContext(unitName = "BusinessObjects")
    private EntityManager em;

    public HRFacadeBean() {
    }

    public Object queryByRange(String jpqlStmt, int firstResult,
                               int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public Departments persistDepartments(Departments departments) {
        em.persist(departments);
        return departments;
    }

    public Departments mergeDepartments(Departments departments) {
        return em.merge(departments);
    }

    public void removeDepartments(Departments departments) {
        departments =
                em.find(Departments.class, departments.getDepartmentId());
        em.remove(departments);
    }

    /** <code>select o from Departments o</code> */
    public List<Departments> getDepartmentsFindAll() {
        return em.createNamedQuery("Departments.findAll").getResultList();
    }

    public Employees persistEmployees(Employees employees) {
        em.persist(employees);
        return employees;
    }

    public Employees mergeEmployees(Employees employees) {
        return em.merge(employees);
    }

    public void removeEmployees(Employees employees) {
        employees = em.find(Employees.class, employees.getEmployeeId());
        em.remove(employees);
    }

    /** <code>select o from Employees o</code> */
    public List<Employees> getEmployeesFindAll() {
        return em.createNamedQuery("Employees.findAll").getResultList();
    }


    /** <code>select o from Employees o where o.salary > :_salary</code> */
    public List<Employees> getEmployeesFindBySal(String _salary) {
        String salaryValue = _salary;
        if (_salary == null)
            salaryValue = "0";
        return em.createNamedQuery("Employees.findBySal").setParameter("_salary",
                                                                       Double.valueOf(salaryValue)).getResultList();
    }
}
