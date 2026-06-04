package com.example.service.impl;

import com.example.mapper.ClazzMapper;
import com.example.pojo.Clazz;
import com.example.pojo.ClazzQueryParam;
import com.example.pojo.PageResult;
import com.example.pojo.Result;
import com.example.service.ClazzService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ClazzServiceimpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        log.info("分页查询班级数据");
        Integer page = clazzQueryParam.getPage() != null ? clazzQueryParam.getPage() : 1;
        Integer pageSize = clazzQueryParam.getPageSize() != null ? clazzQueryParam.getPageSize() : 10;

        PageHelper.startPage(page, pageSize);
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);
        //clazzList 添加状态
        /*注意：班级状态，显示为：未开班、已结课、在读中 这三种。
        如果：
        - 当前时间 > 结课时间：状态未 已结课。
        - 当前时间 < 开课时间：状态未 未开班。
        - 否则，就是 在读中。
         */
        clazzList.forEach(clazz -> {
            if(clazz.getEndDate().isBefore(LocalDate.now())){
                clazz.setStatus("已结课");
            }
            else if(clazz.getBeginDate().isAfter(LocalDate.now())){
                clazz.setStatus("未开班");
            }
            else{
                clazz.setStatus("在读中");
            }
        });

        Page<Clazz> p = (Page<Clazz>) clazzList;

        return new PageResult<Clazz> (p.getTotal(), p.getResult());
    }


    @Override
    public void delete(Integer id) {
        clazzMapper.deleteByIds(id);
    }

    @Override
    public void save(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz get(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

    @Override
    public List<Clazz> listAllClazzs() {
        return clazzMapper.listAllClazzs();
    }

}
