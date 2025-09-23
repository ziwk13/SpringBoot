package org.shining.practice.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.shining.practice.user.dto.UserDTO;

@Mapper
public interface UserMapper {

  long selectUserCount();
  List<UserDTO> selectUserList(@Param(value = "offset") long offset, @Param(value = "size") int size, @Param(value = "sort") String sort);
  UserDTO selectUserById(long uid);
  int insertUser(long uid);
  int updateUser(long uid);
  int deleteUser(long uid);
  
}
