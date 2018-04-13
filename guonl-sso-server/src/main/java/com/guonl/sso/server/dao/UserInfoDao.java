package com.guonl.sso.server.dao;

import com.guonl.sso.server.core.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by guonl
 * Date 2018/4/12 下午5:31
 * Description:
 */
@Mapper
public interface UserInfoDao {

    int insert(@Param("userInfo") UserInfo userInfo);

    int delete(@Param("id") int id);

    int update(@Param("userInfo") UserInfo userInfo);

    List<UserInfo> findAll();

    UserInfo loadById(@Param("id") int id);

    UserInfo findByUsername(@Param("username") String username);

}
