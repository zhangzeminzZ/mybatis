package com.zhangzemin.dao;

import com.zhangzemin.bean.Employee;

import java.util.List;

public interface EmployeeMapperPlus {
    Employee getEmpById(Integer id);

    Employee getEmpAndDept(Integer id);

    Employee getEmpByIdStep(Integer id);

    List<Employee> getEmpsByDeptId(Integer deptId);

    Employee getEmpByIdDis(Integer id);
}
