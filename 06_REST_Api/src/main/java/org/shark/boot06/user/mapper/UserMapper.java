package org.shark.boot06.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.shark.boot06.user.dto.UserDTO;

@Mapper
public interface UserMapper {

  int selectUserCount();
  List<UserDTO> seletUserList(Map<String, Object> map);
  UserDTO selectUserById(Long uid);
  int insertUser(UserDTO user);
  int updateUser(UserDTO user);
  int deleteUser(Long uid);
}
