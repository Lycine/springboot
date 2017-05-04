package com.artbrain.service;

import com.artbrain.entity.Bulletin;
import com.artbrain.entity.User;

import java.util.List;

/**
 * Created by hongyu on 2017/4/30.
 */
public interface BulletinService {
    /**
     * 新增bulletin
     *
     * @param bulletin
     * @return
     */
    Boolean bulletinAdd(Bulletin bulletin);

    /**
     * 查找bulletin所有信息
     * 根据学生 ID查找最新一条bulletin
     *
     * @param bulletin
     * @return
     */
    Bulletin bulletinFindById(Bulletin bulletin);

    /**
     * 查找所有bulletin
     *
     * @return
     */
    List<Bulletin> bulletinFindAll();

    /**
     * 查找bulletin所有信息
     * 根据bulletin bulletin查找该teacher对应所有信息
     *
     * @param user
     * @return
     */
    List<Bulletin> bulletinFindByTea(User user);

    /**
     * 查找bulletin所有信息
     * 根据bulletin bulletin查找该student最新的公告
     *
     * @param user
     * @return
     */
    Bulletin bulletinLastFindByStuId(User user);
}
