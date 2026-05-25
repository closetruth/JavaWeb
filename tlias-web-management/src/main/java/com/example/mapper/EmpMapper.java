package com.example.mapper;

import com.example.pojo.Emp;
import com.example.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
    /**
     * 原始查询
     */

//    /**
//     * 查询总记录数
//     * @return
//     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long count();
//
//    /**
//     * 分页查询员工数据
//     * @param start
//     * @param pageSize
//     * @return
//     */
//    @Select("select e.*, d.name as deptName from emp e left join dept d on e.dept_id = d.id" +
//            "order by e.update_time desc limit #{start}, #{pageSize}")
//    public List<Emp> page(Integer start, Integer pageSize);

    @Select("select * from emp e left join dept d on e.dept_id = d.id")
    public Long count();

//    @Select("select e.*, d.name as deptName from emp e left join dept d on e.dept_id = d.id " +
//            "order by e.update_time desc")
    public List<Emp> list(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values(#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getById(Integer id);
}
