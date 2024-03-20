package com.Ashutosh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Ashutosh.entity.postEntity;
import com.Ashutosh.entity.userEntity;

public interface postRepo extends JpaRepository<postEntity,Integer> {
	@Query("SELECT p FROM postEntity p WHERE p.user = :user AND p.deleted = false") // Assuming deleted = false for active posts

	 public List<postEntity> getUser(userEntity user);
       
	 @Query("SELECT p FROM postEntity p WHERE  p.deleted = false")
	 public List<postEntity> getAllPost();
}
