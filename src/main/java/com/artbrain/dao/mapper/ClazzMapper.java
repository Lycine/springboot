package com.artbrain.dao.mapper;

import com.artbrain.dao.ClazzDao;
import com.artbrain.entity.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hongyu on 2017/1/15.
 */
@Mapper
public interface ClazzMapper extends ClazzDao {
    /**
     * 查找班级所有信息
     * 根据班级ID查找该班级所有信息
     *
     * @param clazz
     * @return
     */
    @Override
    Clazz selectClazzById(@Param("clazz") Clazz clazz);

    /**
     * 新增班级
     *
     * @param clazz
     * @return
     */
    @Override
    int addClazz(@Param("clazz") Clazz clazz);

    /**
     * 查找全部班级
     *
     * @return
     */
    @Override
    List<Clazz> selectAllClazz();

    /**
     * 修改班级
     *
     * @param clazz
     * @return
     */
    @Override
    int updateClazzById(@Param("clazz") Clazz clazz);
}
