package com.zhangzemin.test;

import com.zhangzemin.bean.Dept;
import com.zhangzemin.bean.Employee;
import com.zhangzemin.dao.DeptMapper;
import com.zhangzemin.dao.EmployeeMapper;
import com.zhangzemin.dao.EmployeeMapperAnnotation;
import com.zhangzemin.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1、接口式编程
 * 	原生：		Dao		====>  DaoImpl
 * 	mybatis：	Mapper	====>  xxMapper.xml
 *
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * 		（将接口和xml进行绑定）
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息：
 * 					将sql抽取出来。
 */
public class MybatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
     * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码：
     * 		1）、根据全局配置文件得到SqlSessionFactory；
     * 		2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 			一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 		3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        //获取sqlSession实例，能直接执行已经映射的sql语句
        //sql的唯一标识（一般用namespace+id）：statement Unique identifier matching the statement to use.
        //执行sql要用的参数：parameter A parameter object to pass to the statement.
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Employee employee = openSession.selectOne("com.zhangzemin.mybatis.EmployeeMapper.getEmpById", 1);
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    /**
     * xml配置文件方式查询数据
     * @throws IOException
     */
    @Test
    public void test02() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            // 3、获取接口的实现类对象
            //mybatis会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        }finally {
            openSession.close();
        }
    }

    /**
     * 注解方式查询数据
     * @throws IOException
     */
    @Test
    public void test03() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
            Employee empById = mapper.getEmpById(1);
            System.out.println(empById);
        }finally{
            openSession.close();
        }
    }

    /**
     * 测试增删改
     * 1、mybatis允许增删改直接定义以下类型返回值
     * 		Integer、Long、Boolean、void
     * 2、我们需要手动提交数据
     * 		sqlSessionFactory.openSession();===》手动提交
     * 		sqlSessionFactory.openSession(true);===》自动提交
     * @throws IOException
     */
    @Test
    public void test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            //添加
            Employee employee = new Employee(null, "jerry", null, "0");
            Long addEmp = mapper.addEmp(employee);
            System.out.println(addEmp);
            System.out.println(employee.getId());

            //修改
            /*boolean updateEmp = mapper.updateEmp(new Employee(1, "jerry", "jerry@qq.com", "0"));
            System.out.println(updateEmp);*/

            //删除
            /*mapper.deleteEmpById(1);*/

            openSession.commit();
        }finally {
            openSession.close();
        }
    }


    /**
     * 测试查询
     * @throws IOException
     */
    @Test
    public void test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            /*Employee employee = mapper.getEmpByIdAndLastName(2, "zhangzemin");
            System.out.println(employee);*/

            /*Map<String,Object> map = new HashMap<>();
            map.put("id",2);
            map.put("lastName","zhangzemin");
            map.put("tableName","tbl_employee");
            Employee employee = mapper.getEmpByMap(map);
            System.out.println(employee);*/

            /*Employee employee = mapper.getEmpByPojo(new Employee(2, "zhangzemin", null, null));
            System.out.println(employee);*/

            /*List<Employee> employeeList = mapper.getEmpsByLastNameLike("%e%");
            for (Employee e: employeeList) {
                System.out.println(e);
            }*/

            /*Map<String, Object> map = mapper.getEmpByIdReturnMap(2);
            System.out.println(map);*/

            Map<String, Employee> map = mapper.getEmpByLastNameLikeReturnMap("%e%");
            System.out.println(map);

        }finally {
            openSession.close();
        }
    }

    @Test
    public void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
            /*Employee employee = mapper.getEmpById(2);
            System.out.println(employee);*/

            /*Employee empAndDept = mapper.getEmpAndDept(2);
            System.out.println(empAndDept);
            System.out.println(empAndDept.getDept());*/

            /*Employee empByIdStep = mapper.getEmpByIdStep(2);
            System.out.println(empByIdStep);
            System.out.println(empByIdStep.getDept());*/

            /*Employee empByIdStep = mapper.getEmpByIdStep(2);
            System.out.println(empByIdStep.getLastName());
            System.out.println(empByIdStep.getDept());*/

            Employee empByIdStep = mapper.getEmpByIdDis(3);
            System.out.println(empByIdStep);
            System.out.println(empByIdStep.getDept());
        }finally {
            openSession.close();
        }
    }

    @Test
    public void test07() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            DeptMapper mapper = openSession.getMapper(DeptMapper.class);

            /*Dept dept = mapper.getDeptByIdPlus(1);
            System.out.println(dept);
            System.out.println(dept.getEmps());*/

            Dept dept = mapper.getDeptByIdStep(1);
            System.out.println(dept.getDeptName());
            System.out.println(dept.getEmps());
        }finally {
            openSession.close();
        }

    }
}
