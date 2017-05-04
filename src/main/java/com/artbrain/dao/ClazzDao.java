package com.artbrain.dao;


import com.artbrain.entity.Clazz;

import java.util.List;


/**
 * Created by hongyu on 2017/1/15.
 */
public interface ClazzDao {
    /**
     * 查找班级所有信息
     * 根据班级ID查找该班级所有信息
     *
     * @param clazz
     * @return
     */
    Clazz selectClazzById(Clazz clazz);

    /**
     * 新增班级
     *
     * @param clazz
     * @return
     */
    int addClazz(Clazz clazz);

    /**
     * 修改班级
     *
     * @param clazz
     * @return
     */
    int updateClazzById(Clazz clazz);

    /**
     * 查找全部班级
     *
     * @return
     */
    List<Clazz> selectAllClazz();
}
