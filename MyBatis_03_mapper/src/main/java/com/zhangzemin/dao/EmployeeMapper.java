package com.zhangzemin.dao;

import com.zhangzemin.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    Employee getEmpById(Integer id);

    Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);

    Employee getEmpByMap(Map<String, Object> map);

    Employee getEmpByPojo(Employee employee);

    List<Employee> getEmpsByLastNameLike(String lastName);

    //返回一条记录的map；key就是列名，值就是对应的值
    Map<String, Object> getEmpByIdReturnMap(Integer id);

    //多条记录封装一个map：Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javaBean
    //@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("id")
    Map<String, Employee> getEmpByLastNameLikeReturnMap(String lastName);

    Long addEmp(Employee employee);

    boolean updateEmp(Employee employee);

    void deleteEmpById(Integer id);
}
