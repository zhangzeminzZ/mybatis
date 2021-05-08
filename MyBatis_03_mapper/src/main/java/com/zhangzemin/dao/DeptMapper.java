package com.zhangzemin.dao;

import com.zhangzemin.bean.Dept;

public interface DeptMapper {
    Dept getDeptById(Integer id);

    Dept getDeptByIdPlus(Integer id);

    Dept getDeptByIdStep(Integer id);
}
