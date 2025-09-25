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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS) // @BeforeAll, @BeforeEach, @AfterEach, @AfterAll 메소드에 static 처리를 안 할 수 있도록 처리 하는 Annotation
class LifeCycleTests {
  
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
	@DisplayName("비영속 엔티티 점검")
	void transientTest() {
	  
	  // User 엔티티 생성
	  User user = User.createUser("나얌", "ime@example.com", Gender.FEMALE);

	  // 영속성 컨텍스트에 저장되어 있는지 확인
	  assertTrue(em.contains(user));
	}
	@Test
	@DisplayName("영속 엔티티 점검")
	void mamagedTest() {
	  
	  // 영속 엔티티(Managed Entity)가 되는 방법
	  // 1. persist()
	  // 2. find()
	  // 3. JPQL
	  // 4. merge()
	  
	  // User 엔티티 생성
	  User user = User.createUser("나얌", "ime@example.com", Gender.FEMALE);
	  
	  // persist()
	  em.persist(user);
	  
	  // AccessLog 엔티티 조회
	  AccessLog accessLog = em.find(AccessLog.class, 1L);
	  
	  // 영속성 컨텍스트에 저장되어 있는지 확인
	  assertTrue(em.contains(user));
	  assertTrue(em.contains(accessLog));
	}
	@Test
	@DisplayName("준영속 엔티티 detach() 테스트")
	void detachTest() {
	  
	  // 준영속 엔티티
	  // 1. 영속성 컨텍스트에서 관리하던 엔티티가 영속성 컨텍스트에서 분리된 상태 이다
	  // 2. 비영속 엔티티와의 가장 큰 차이점은 식별자(PK) 보유 여부 (ID가 있으면 준영속, 없으면 비영속)
	  
	  // 비영속 엔티티 만들기
	  User user = User.createUser("영속이", "bepersist@example.com", Gender.MALE);
	  
	  EntityTransaction tx = em.getTransaction();
	  tx.begin();
	  
	  try {
	    
	    // 비영속 -> 영속 엔티티로 바꾸기
	    em.persist(user);
	    
	    // Dirty Checking
	    user.setUsername("비영속");
	    user.setEmail("psersist@example.com");
	    user.setGender(Gender.FEMALE);
	    
	    // 영속 -> 준영속 엔티티로 바꾸기
	    // 준영속 엔티티는 Dirty Checking이 동작하지 않는다.
	    // 오직 영속 엔티티만 Dirty Checking 동작 한다.
	    em.detach(user);
	    
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	  assertEquals("비영속", em.find(User.class, 1L).getUsername());
	}
	@Test
	@DisplayName("준영속 엔티티 clear() 테스트")
	void clearTest() {
	  User user = User.createUser("영속이", "bepersist@example.com", Gender.MALE);
	  EntityTransaction tx = em.getTransaction();
	  tx.begin();
	  try {
	    em.persist(user);  // user 엔티티를 영속성 컨텍스트에 저장
	    em.flush();  // INSERT 쿼리 실행
	    em.find(User.class, 1L);  // 영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 성공
	    em.find(User.class, 1L);  // 영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 성공
      em.clear();  // 영속성 컨텍스트에 있는 모든 엔티티를 준영속 상태로 변경 (영속성
      em.find(User.class, 1L);  // 영속성 컨텍스트에서 ID가 1인 User 엔티티 조회 : 실패 (DB로 SELECT
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	}
	@Test
	@DisplayName("준영속 엔티티 merge() 테스트")
	void mergeTest() {
	  
	  // merge()
	  // 1. 준영속 상태의 엔티티를 영속성 컨텍스트에 반환 한다.
	  // 2. merge() 동작
	  //   1) 준영속 엔티티를 merge()에 전달한다.
	  //   2) 전달 받은 준영속 엔티티의 ID를 이용해 영속성 컨텍스트의 1차 캐시에서 엔티티를 조회한다.
	  //    2-1) 1차 캐시에 엔티티가 있으면 준영속 엔티티의 모든 필드 값을 1차 캐시에 있는 영속 엔티티에 덮어쓰기 한다.
	  //    2-2) 1차 캐시에 없으면 DB에서 조회해서 1차 캐시에 저장한 뒤 준영속 엔티티의 모든 필드 값을 덮어쓰기 한다.
	  //   3) 그렇게 만든 새로운 영속 엔티티를 반환 한다.
	  
    User user = User.createUser("영속이", "bepersist@example.com", Gender.MALE);
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      em.persist(user);  // user 엔티티를 영속성 컨텍스트에 저장
      em.flush();  // INSERT 쿼리 실행
      
      em.detach(user);  // user 엔티티를 준영속 상태로 변경 (1차 캐시에서 user 엔티티 제거)
      
      User mergedUser = em.merge(user);  // 1차 캐시에서 user 엔티티의 ID를 가진 엔티티를 찾지만, 없기 때문에 DB에서 SELECT 해서 가져온다.
      User mergedUser2 = em.merge(user);
      
      assertTrue(em.contains(mergedUser));  // merge()가 반환한 엔티티는 영속 엔티티
      assertTrue(em.contains(mergedUser2));
      // assertTrue(em.contains(user));  // merge() 이후에도 준영속 엔티티는 여전히 그대로 이다.
      
      
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	}
	@Test
	@DisplayName("준영속 엔티티 merge()와 Dirty Checking 테스트")
	void mergeDirtyCheckingTest() {
	  User user = User.createUser("인텔리", "intel@example.com", Gender.FEMALE);
	  EntityTransaction tx = em.getTransaction();
	  tx.begin();
	  
	  try {
      em.persist(user);
      em.flush();
      
      em.detach(user);
      
      user.setUsername("제이");
      user.setEmail("j@example.com");
      
      User mergedUser = em.merge(user);
      
      log.info("{}", mergedUser);
      
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	}
}
