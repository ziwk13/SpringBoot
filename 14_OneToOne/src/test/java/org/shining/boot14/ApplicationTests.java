package org.shining.boot14;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.shining.boot14.commom.util.JpaUtil;
import org.shining.boot14.member.entity.Locker;
import org.shining.boot14.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
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
	@DisplayName("1:1 연관관계 저장 테스트")
	void createOneToOneRelationshipTest() {
	  
	  Member member = Member.createMember("김이름", "010-1111-1111");
	  Locker locker = Locker.createLocker("B1");
	  member.assignLocker(locker);  // 연관 관계 설정되는 코드
	  
	  EntityTransaction tx = em.getTransaction();
	  tx.begin();
	  
	  try {
      em.persist(member);
      em.flush();
      
      em.clear();
      
      Long id = member.getId();
      Member foundMember = em.find(Member.class, id);  // SELECT 쿼리 실행
                                                       // fetch=FetchType.EAGER : Member와 연관된 Locker 모두 조회 (join 발생)
                                                       // fetch=FetchType.LAZY  : Member만 조회
      log.info("Member's name: {}", member.getName());
      
      Locker foundLocker = foundMember.getLocker(); // fetch=FetchType.LAZY : Locker를 조회
      String location = foundLocker.getLocation();  
      log.info("Locker's location : {}", location);
      
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	}
	@Test
	@DisplayName("양방향 매핑 테스트")
	void biDirectionMappingTest() {
	  
	  Member member = Member.createMember("뉴멤버", "010-2222-1111");
	  Locker locker = Locker.createLocker("B3");
	  member.assignLocker(locker);
	  
	  EntityTransaction tx = em.getTransaction();
	  tx.begin();
	  
	  try {
      em.persist(member);
      em.flush();
      
      Long locker_id = locker.getId();
      
      em.clear();
      
      Locker foundLocker = em.find(Locker.class, locker_id);
      log.info("foundLocker: {}", foundLocker);
      
      // foundLocker를 이용하는 Member 조회
      Member foundMember = foundLocker.getMember();
      log.info("{}", foundMember.getName());
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
	}
}
