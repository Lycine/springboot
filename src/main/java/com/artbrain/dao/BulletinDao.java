package com.artbrain.dao;

import com.artbrain.entity.Bulletin;
import com.artbrain.entity.User;

import java.util.List;

/**
 * Created by hongyu on 2017/4/30.
 */
public interface BulletinDao {
    /**
     * 查找bulletin所有信息
     * 根据bulletin ID查找该bulletin所有信息
     *
     * @param bulletin
     * @return
     */
    Bulletin selectBulletinById(Bulletin bulletin);

    /**
     * 查找bulletin所有信息
     * 根据stu id查找该bulletin所有信息
     *
     * @param user
     * @return
     */
    Bulletin selectBulletinByStuId(User user);

    /**
     * 查找tea对应所有bulletin所有信息
     * 根据tea id查找
     *
     * @param user
     * @return
     */
    List<Bulletin> selectBulletinByTeaId(User user);

    /**
     * 查找所有bulletin所有信息
     *
     * @return
     */
    List<Bulletin> selectBulletinAll();

    /**
     * 新增bulletin
     *
     * @param bulletin
     * @return
     */
    int bulletinAdd(Bulletin bulletin);
}
