package com.artbrain.service;

import com.artbrain.entity.Token;

/**
 * Created by hongyu on 2017/4/30.
 */
public interface TokenService {
    /**
     * 查重token
     * 查看token字段是否重复
     *
     * @param token
     * @return
     */
    Boolean isDuplicateToken(Token token);

    /**
     * 新增token
     *
     * @param token
     * @return
     */
    Boolean tokenAdd(Token token);

    /**
     * 查找token所有信息
     * 根据token ID查找该token所有信息
     *
     * @param token
     * @return
     */
    Token tokenFindById(Token token);

    /**
     * 查找token所有信息
     * 根据token token查找该token所有信息
     *
     * @param token
     * @return
     */
    Token tokenFindByToken(Token token);

    /**
     * 删除所有过期token
     *
     * @return
     */
    Boolean tokenExpiredDelete();

    /**
     * 删除所有过期token
     *
     * @param token
     * @return
     */
    Boolean tokenDeleteById(Token token);
}
