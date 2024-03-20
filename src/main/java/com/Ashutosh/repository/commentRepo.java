package com.Ashutosh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ashutosh.entity.commentEntity;

public interface commentRepo extends JpaRepository<commentEntity,Integer> {

}
