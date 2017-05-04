package com.artbrain.dao.mapper;

import com.artbrain.dao.BulletinDao;
import com.artbrain.entity.Bulletin;
import com.artbrain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hongyu on 2017/1/15.
 */
@Mapper
public interface BulletinMapper extends BulletinDao {

    /**
     * 查找bulletin所有信息
     * 根据bulletin ID查找该bulletin所有信息
     *
     * @param bulletin
     * @return
     */
    @Override
    Bulletin selectBulletinById(@Param("bulletin") Bulletin bulletin);

    /**
     * 查找bulletin所有信息
     * 根据stu id查找该bulletin所有信息s
     *
     * @param user
     * @return
     */
    @Override
    Bulletin selectBulletinByStuId(@Param("user") User user);

    /**
     * 查找tea对应所有bulletin所有信息
     * 根据tea id查找
     *
     * @param user
     * @return
     */
    @Override
    List<Bulletin> selectBulletinByTeaId(@Param("user") User user);

    /**
     * 查找所有bulletin所有信息
     *
     * @return
     */
    @Override
    List<Bulletin> selectBulletinAll();

    /**
     * 新增bulletin
     *
     * @param bulletin
     * @return
     */
    @Override
    int bulletinAdd(@Param("bulletin") Bulletin bulletin);
}
