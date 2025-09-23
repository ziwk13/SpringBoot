package org.shark.boot06;

import org.junit.jupiter.api.Test;
import org.shark.boot06.user.dto.UserDTO;
import org.shark.boot06.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
  
  @Autowired
  private UserServiceImpl userService;
  
  
	@Test
	void contextLoads() {
	  
	  userService.createUser(new UserDTO(null, "ert", "dfg", "cvb", null));
	  
	}

}
