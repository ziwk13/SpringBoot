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
import org.shining.boot14.company.entity.Address;
import org.shining.boot14.company.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class CompanyTests {
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
  @DisplayName("Company 1:1 연관관계 저장 테스트")
  void createCompanyRelationshipTest() {
    Employee employee = Employee.createEmployee("영업부");
    Address address = Address.createAddress("07152", "seoul", "dosinro 161");
    employee.assignAddress(address);
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      em.persist(employee);
      em.flush();
      Long id = employee.getId();
      Employee foundEmployee = em.find(Employee.class, id);
      log.info("employee : {}", employee.getName());
      
      Address foundAddress = foundEmployee.getAddress();
      String city = foundAddress.getCity();
      log.info("address : {}", city);
      
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
    }
  }
}
