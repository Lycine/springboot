package com.artbrain.service.impl;

import com.artbrain.dao.ClazzDao;
import com.artbrain.entity.Clazz;
import com.artbrain.service.ClazzService;
import com.github.pagehelper.PageHelper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hongyu on 2017/1/15.
 */
@CommonsLog
@Service("ClazzService")
public class ClazzServiceImpl implements ClazzService {
    private ClazzDao clazzDao;

    @Autowired
    public ClazzServiceImpl(ClazzDao clazzDao) {
        this.clazzDao = clazzDao;
    }

    @Override
    public Clazz clazzFindById(Clazz clazz) {
        clazz = clazzDao.selectClazzById(clazz);
        return clazz;
    }

    @Override
    public Boolean clazzAdd(Clazz clazz) {
        int effect_row = clazzDao.addClazz(clazz);
        return effect_row > 0;
    }

    @Override
    public Boolean clazzUpdate(Clazz clazz) {
        int effect_row = clazzDao.updateClazzById(clazz);
        return effect_row > 0;
    }

    @Override
    public List<Clazz> clazzFindAll(int page, int rows) {
        PageHelper.startPage(page, rows);
        return clazzDao.selectAllClazz();
    }
}
