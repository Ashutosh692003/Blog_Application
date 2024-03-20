package com.Ashutosh.service;

import java.util.List;

import com.Ashutosh.binding.commentBinding;
import com.Ashutosh.binding.loginBinding;
import com.Ashutosh.binding.postCreateBinding;
import com.Ashutosh.binding.registerBinding;
import com.Ashutosh.binding.searchBinding;
import com.Ashutosh.entity.commentEntity;
import com.Ashutosh.entity.postEntity;

public interface serviceInterface {
	 public String register(registerBinding regis);
	 
	 public String login(loginBinding log);
	 
	 public boolean createPost(postCreateBinding post,Integer userId);
	 
	 public List<postEntity>  dashboardData(Integer userId);
	 
	 public postCreateBinding edit(Integer pId);
	 
	 public boolean delete (Integer id);
	 
	 public List<postEntity> search(searchBinding search);
	 
	 public String addComment(commentBinding comment,Integer postId);
	 
	 public List<postEntity>  searchInside(searchBinding search,Integer userId);
	 
	 public List<commentEntity> getComments(Integer userId);
	 
	 public boolean commentDelete(Integer id);
} 
