package com.guonl.sso.core.store;

import com.guonl.sso.core.conf.Conf;
import com.guonl.sso.core.user.SsoUser;
import com.guonl.sso.core.util.JedisUtil;

/**
 * Created by guonl
 * Date 2018/4/12 下午2:12
 * Description: local login store
 */
public class SsoLoginStore {

    /**
     * 根据sessionID获取redis中的user信息
     * @param sessionId
     * @return
     */
    public static SsoUser get(String sessionId){
        String redisKey = redisKey(sessionId);
        Object objectValue = JedisUtil.getObjectValue(redisKey);
        if(objectValue != null){
            SsoUser user = (SsoUser) objectValue;
            return user;
        }
        return null;
    }

    /**
     * 删除redis中的user
     * @param sessionId
     */
    public static void remove(String sessionId){
        String redisKey = redisKey(sessionId);
        JedisUtil.del(redisKey);
    }

    /**
     * 保存用户的信息到redis
     * @param sessionId
     * @param user
     */
    public static void put(String sessionId,SsoUser user){
        String redisKey = redisKey(sessionId);
        JedisUtil.setObjectValue(redisKey,user);

    }


    /**
     * 获取redis的key
     * @param sessionId
     * @return
     */
    private static String redisKey(String sessionId){
        return Conf.SSO_SESSIONID.concat(":").concat(sessionId);
    }


}
