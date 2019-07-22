package com.niu.springbootjwt.mapper;

import com.niu.springbootjwt.model.UserAuthority;
import com.niu.springbootjwt.model.UserAuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserAuthorityMapper {

  int countByExample(UserAuthorityExample example);

  int deleteByExample(UserAuthorityExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(UserAuthority record);

  int insertSelective(UserAuthority record);

  List<UserAuthority> selectByExampleWithRowbounds(UserAuthorityExample example,
      RowBounds rowBounds);

  List<UserAuthority> selectByExample(UserAuthorityExample example);

  UserAuthority selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") UserAuthority record,
      @Param("example") UserAuthorityExample example);

  int updateByExample(@Param("record") UserAuthority record,
      @Param("example") UserAuthorityExample example);

  int updateByPrimaryKeySelective(UserAuthority record);

  int updateByPrimaryKey(UserAuthority record);

  Long sumByExample(UserAuthorityExample example);

  void batchInsert(@Param("items") List<UserAuthority> items);
}