package com.artbrain.service.impl;

import com.artbrain.dao.TokenDao;
import com.artbrain.entity.Token;
import com.artbrain.service.TokenService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hongyu on 2017/4/30.
 */
@CommonsLog
@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    private TokenDao tokenDao;

    @Autowired
    public TokenServiceImpl(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    public Boolean isDuplicateToken(Token token) {
        token = tokenDao.selectTokenByToken(token);
        return null != token;
    }

    @Override
    public Boolean tokenAdd(Token token) {
        int effect_row = tokenDao.tokenAdd(token);
        return effect_row > 0;
    }

    @Override
    public Token tokenFindById(Token token) {
        return tokenDao.selectTokenById(token);
    }

    @Override
    public Token tokenFindByToken(Token token) {
        return tokenDao.selectTokenByToken(token);
    }

    @Override
    public Boolean tokenExpiredDelete() {
        int effect_row = tokenDao.deleteTokenByExpireTime();
        return effect_row > 0;
    }

    @Override
    public Boolean tokenDeleteById(Token token) {
        int effect_row = tokenDao.deleteTokenById(token);
        return effect_row > 0;
    }


}
