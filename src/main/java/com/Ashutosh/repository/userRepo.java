package com.Ashutosh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ashutosh.entity.userEntity;

public interface userRepo extends JpaRepository<userEntity,Integer> {
	
	public userEntity findByEmail(String email);
      
}
