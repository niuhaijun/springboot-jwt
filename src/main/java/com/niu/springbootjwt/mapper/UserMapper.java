package com.niu.springbootjwt.mapper;

import com.niu.springbootjwt.model.User;
import com.niu.springbootjwt.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserMapper {

  int countByExample(UserExample example);

  int deleteByExample(UserExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(User record);

  int insertSelective(User record);

  List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

  List<User> selectByExample(UserExample example);

  User selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

  int updateByExample(@Param("record") User record, @Param("example") UserExample example);

  int updateByPrimaryKeySelective(User record);

  int updateByPrimaryKey(User record);

  Long sumByExample(UserExample example);

  void batchInsert(@Param("items") List<User> items);
}