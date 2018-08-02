package com.youyuan.paystrategy.repository;

import com.youyuan.paystrategy.bean.Hello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/*
 */
public interface HelloRepository extends JpaRepository<Hello, Long> ,JpaSpecificationExecutor<Hello> {
 
}