package com.artbrain.dao.mapper;

import com.artbrain.dao.TokenDao;
import com.artbrain.entity.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by hongyu on 2017/1/15.
 */
@Mapper
public interface TokenMapper extends TokenDao {

    /**
     * 查找token所有信息
     * 根据token ID查找该token所有信息
     *
     * @param token
     * @return
     */
    @Override
    Token selectTokenById(@Param("token") Token token);

    /**
     * 新增token
     *
     * @param token
     * @return
     */
    @Override
    int tokenAdd(@Param("token") Token token);

    /**
     * 查重token
     * 查看数据库中token是否已存在
     *
     * @param token
     * @return
     */
    @Override
    Token selectTokenByToken(@Param("token") Token token);

    /**
     * 删除token（id）
     *
     * @param token
     * @return
     */
    @Override
    int deleteTokenById(@Param("token") Token token);

    /**
     * 删除token（超时）
     *
     * @return
     */
    @Override
    int deleteTokenByExpireTime();
}
