package org.shining.boot12;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shining.boot12.commom.util.JpaUtil;
import org.shining.boot12.user.entity.AccessLog;
import org.shining.boot12.user.entity.User;
import org.shining.boot12.user.enums.Gender;
import org.shining.boot12.user.enums.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS) // @BeforeAll, @BeforeEach, @AfterEach, @AfterAll 메소드에 static 처리를 안 할 수 있도록 처리 하는 Annotation
class CRUDTests {
  
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
  @DisplayName("User 엔티티 등록하기")
  void createUserTest() {
    
    // User 엔티티 생성
    User user = User.createUser("나얌", "ime@example.com", Gender.FEMALE);
    
    // 트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      // 엔티티를 영속성 컨텍스트(Persistence Context)에 저장
      em.persist(user);
      log.info("after persist()");
      
      // 트랜잭션 커밋
      tx.commit();
      log.info("after commit()");
      
    } catch (Exception e) {
      // 트랜잭션 롤백
      tx.rollback();
      throw e;
    }
    assertNotNull(user.getId());
    assertNotNull(user.getCreatedAt());
  }
  /*
   * 실행 순서 정리
   * 1. insert into
   * 2. after persist()
   * 3. after commit()
   * 
   * 실행 순서 이유
   * 영속성 컨텍스트에 엔티티를 저장하기 위해서는 반드시 엔티티에 ID가 필요하다
   * User 엔티티는 AUTO INCREMENT 전략을 사용하므로, INSERT 쿼리를 실행 해야만 ID를 알 수 있다.
   * 따라서, 영속성 컨텍스트에 엔티티를 저장하는 persist() 호출 시 곧바로 DB INSERT 쿼리가 실행된다.
   */

  @Test
  @DisplayName("AccessLog 엔티티 등록하기")
  void createAccessLogTest() {
    
    // AccessLog 엔티티 생성
    AccessLog accessLog = AccessLog.createAccessLog("USER_LOGIN", "사용자 로그인 성공", LogLevel.INFO, "192.168.100.5", "Mozilla/5.0");
    
    // 트랜잭션 시작
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      // AccessLog 엔티티를 영속성 컨텍스트에 저장
      em.persist(accessLog);
      log.info("after persist()");
      
      // 트랜잭션 커밋
      tx.commit();
      log.info("after commit()");
    } catch (Exception e) {
      // 예외 발생
      tx.rollback();
      throw e;
    }
    assertNotNull(accessLog.getId());
    assertNotNull(accessLog.getCreatedAt());
  }
   /*
   * 실행 순서 정리
   * 1. insert into
   * 2. after persist()
   * 3. after commit()
   * 
   * 실행 순서 이유
   * 영속성 컨텍스트에 엔티티를 저장하기 위해서는 반드시 엔티티에 ID가 필요하다
   * AccessLog 엔티티는 access_log_seq 테이블로부터 ID를 받아서 사용한다.
   * 따라서, AccessLog 엔티티를 DB에 INSERT 하지 않아도 엔티티의 ID를 알 수 있으므로, persist() 메소드 호출 시 INSERT 쿼리가 동작 안한다.
   */
  
  @Test
  @DisplayName("User Entity Select")
  void findUserTest() {
    
    // 엔티티를 조회 할 땐 ID를 이용해서 조회한다.
    
    Long id = 1L;

    // 엔티티 매니저를 이용한 엔티티 조회 원리
    // 1. find() 호출 시 영속성 컨텍스트에서 엔티티를 조회 한다
    // 2. 없으면 DB에서 조회 한다.
    // 3. 그래도 없으면 null 반환
    // 4. 조회한 엔티티는 영속성 컨텍스트에서 관리 한다 (find() 호출 결과 엔티티는 영속성 컨텍스트에 저장 된다.)
    User foundUser = em.find(User.class, id);
    
    assertNotNull(foundUser);
    
  }
  
  @Test
  @DisplayName("AccessLog 엔티티 조회하기")
  void findAccessLogTest() {
    Long id = 1L;
    AccessLog accessLog = em.find(AccessLog.class, id);
    
    assertNotNull(accessLog);
  }
  
  @Test
  @DisplayName("User 엔티티 삭제하기")
  void deleteUserTest() {
    
    Long id = 1L;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      // 삭제할 엔티티를 조회해서 영속성 컨텍스트에 저장
      User foundUser = em.find(User.class, id);
      
      // 삭제 예정 상태로 엔티티 변경
      em.remove(foundUser);
      
      // 실제 삭제를 하기 위해 반영
      tx.commit();
      
      assertNull(em.find(User.class, id));
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
  }
  @Test
  @DisplayName("AccessLog 삭제하기")
  void deleteAccessLog() {
    Long id = 1L;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      AccessLog foundAccessLog = em.find(AccessLog.class, id); // DB에서 AccessLog 엔티티를 조회해 영속성 컨텍스트에 저장한다.
                                                               // AccessLog 엔티티를 영속성 컨텍스트에서 제거하고 삭제 상태로 바꾼다.
      em.remove(foundAccessLog);                               // 삭제 상태의 AccessLog 엔티티를 영속성 컨텍스트에 다시 저장 한다.
                                                               // 영속성 컨텍스트에 여전히 AccessLog가 저장 되어 있으므로 아무런 변화가 없다.
      tx.commit();
      
      assertNull(em.find(AccessLog.class, id));
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
  }
  @Test
  @DisplayName("User 엔티티를 수정하기")
  void updateUserTest() {
    Long id = 1L;
    String username = "이걸로수정";
    Gender gender = Gender.FEMALE;
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
      
      // 수정할 엔티티를 조회해서 영속성 컨텍스트에 저장
      User foundUser = em.find(User.class, id);
      
      // 영속성 컨텍스트에 저장된 엔티티를 수정
      foundUser.setUsername(username);
      foundUser.setGender(gender);
      
      tx.commit();
      
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
    assertEquals(username, em.find(User.class, id).getUsername());
  }
}
