package com.niu.springbootjwt.mapper;

import com.niu.springbootjwt.model.Authority;
import com.niu.springbootjwt.model.AuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AuthorityMapper {

  int countByExample(AuthorityExample example);

  int deleteByExample(AuthorityExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(Authority record);

  int insertSelective(Authority record);

  List<Authority> selectByExampleWithRowbounds(AuthorityExample example, RowBounds rowBounds);

  List<Authority> selectByExample(AuthorityExample example);

  Authority selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") Authority record,
      @Param("example") AuthorityExample example);

  int updateByExample(@Param("record") Authority record,
      @Param("example") AuthorityExample example);

  int updateByPrimaryKeySelective(Authority record);

  int updateByPrimaryKey(Authority record);

  Long sumByExample(AuthorityExample example);

  void batchInsert(@Param("items") List<Authority> items);
}