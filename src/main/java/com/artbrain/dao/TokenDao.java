package com.artbrain.dao;

import com.artbrain.entity.Token;

/**
 * Created by hongyu on 2017/4/30.
 */
public interface TokenDao {
    /**
     * 查找token所有信息
     * 根据token ID查找该token所有信息
     *
     * @param token
     * @return
     */
    Token selectTokenById(Token token);

    /**
     * 新增token
     *
     * @param token
     * @return
     */
    int tokenAdd(Token token);

    /**
     * 删除token（id）
     *
     * @param token
     * @return
     */
    int deleteTokenById(Token token);

    /**
     * 删除token（超时）
     *
     * @return
     */
    int deleteTokenByExpireTime();

    /**
     * 查重token
     * 查看数据库中token是否已存在
     *
     * @param token
     * @return
     */
    Token selectTokenByToken(Token token);
}
