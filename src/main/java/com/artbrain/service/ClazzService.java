package com.artbrain.service;

import com.artbrain.entity.Clazz;

import java.util.List;

/**
 * Created by hongyu on 2017/1/15.
 */
public interface ClazzService {
    /**
     * 查找班级所有信息
     * 根据班级ID查找该班级所有信息
     *
     * @param clazz
     * @return
     */
    Clazz clazzFindById(Clazz clazz);

    /**
     * 新增班级
     *
     * @param clazz
     * @return
     */
    Boolean clazzAdd(Clazz clazz);

    /**
     * 修改班级
     *
     * @param clazz
     * @return
     */
    Boolean clazzUpdate(Clazz clazz);

    /**
     * 查找所有班级
     *
     * @param page
     * @param rows
     * @return
     */
    List<Clazz> clazzFindAll(int page, int rows);
}