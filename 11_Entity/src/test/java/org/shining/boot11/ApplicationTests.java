package org.shining.boot11;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shining.boot11.commom.util.JpaUtil;
import org.shining.boot11.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS) // @BeforeAll, @BeforeEach, @AfterEach, @AfterAll 메소드에 static 처리를 안 할 수 있도록 처리 하는 Annotation
class ApplicationTests {
  
  @Autowired
  private JpaUtil jpaUtil;
  
  private EntityManager em;
  
  // 전체 테스트 시작 전 : 엔티티 매니저 팩토리 생성
  @BeforeAll
  void setUpBeforeClass() {
    jpaUtil.initFactory();
    log.info("엔티티 매니저 팩토리 생성");
  }
  
  // 각 테스트 시작 전 : 엔티티 매니저 생성
  @BeforeEach
  void setUp() {
    em = jpaUtil.getEntityManager();
    log.info("엔티티 매니저 생성");
  }
  // 각 테스트 종료 후 : 엔티티 매니저 소멸
  @AfterEach
  void tearDown() {
    if(em != null && em.isOpen()) {
      em.close();
      log.info("엔티티 매니저 소멸");
    }
  }
  // 전체 테스트 종료 후 : 엔티티 매니저 팩토리 소멸
  @AfterAll
  void tearDownAfterClass() {
    jpaUtil.closeFactory();
    log.info("엔티티 매니저 팩토리 소멸");
  }
	@Test
	void contextLoads() {
	  assertNotNull(em);
	  log.info("contextLoads");
	}
	@Test
	@DisplayName("Product Entity 생성이 테이블의 데이터 입력으로 이어지는지 테스트")
	void createProductTest() {
	  Product product = new Product("P-001", "아이폰17", 100L, 120, LocalDate.of(2026, 12, 31));
	  log.info("{}", product);
	  // 테스트 결과 : 엔티티 생성이 INSERT 쿼리로 이어지지 않는다
	}

}
