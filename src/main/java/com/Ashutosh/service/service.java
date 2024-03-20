package com.Ashutosh.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.Ashutosh.binding.commentBinding;
import com.Ashutosh.binding.loginBinding;
import com.Ashutosh.binding.postCreateBinding;
import com.Ashutosh.binding.registerBinding;
import com.Ashutosh.binding.searchBinding;
import com.Ashutosh.entity.commentEntity;
import com.Ashutosh.entity.postEntity;
import com.Ashutosh.entity.userEntity;
import com.Ashutosh.repository.commentRepo;
import com.Ashutosh.repository.postRepo;
import com.Ashutosh.repository.userRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Service
public class service implements serviceInterface {
	 @Autowired
	 userRepo uRepo;
	 
	  @Autowired
	 HttpSession session;
	  
	  
	   @Autowired
	   postRepo pRepo;
	   
	   @PersistenceContext
	   private EntityManager entityManager;
	   
	   @Autowired
	   commentRepo cRepo;
	  
	              //Method related to register
	 public String register(registerBinding regis) {
	userEntity user1 = 	         uRepo.findByEmail(regis.getEmail());
	   if(user1!=null) {
		    return "Email Already Registered";
	   }
		  
		  userEntity user = new userEntity();
		  System.out.println("inside service");
		   BeanUtils.copyProperties(regis,user);
		   
		    uRepo.save(user);
		    
		    return "success";
		      
	 }
	 
	       // Method related to login
	  public String login(loginBinding log) {
	userEntity user  =	       uRepo.findByEmail(log.getEmail());
	          if(user==null) {
	        	   return "incorrect Email";
	          }
	                  String userPwd = user.getPwd();
	                  System.out.println(userPwd);
	                  System.out.println(log.getPwd());
	          if(log.getPwd().equals(userPwd)) {
	        	  System.out.println("inside if");
	        	   session.setAttribute("userId", user.getUser_id());
	        	   return "success"; 
	          }
		  
		     return "incorrect password";
	  }
	    // Method to insert post data to db
	  
	  public boolean createPost(postCreateBinding post,Integer userId) {
		  
		              Optional<userEntity>   u =              uRepo.findById(userId);
		                                          userEntity user= u.get();
		      postEntity blog = new postEntity();
		                         
		   BeanUtils.copyProperties(post, blog);
		            blog.setUser(user);
		            
		           pRepo.save(blog);
		           
		           return true;
		      
		   
		          
	  }
	   
	   //Method related to dashboard data
	  public List<postEntity> dashboardData(Integer userId){
		 Optional<userEntity>    u =               uRepo.findById(userId);
		  userEntity user = u.get();
		  
		   List <postEntity > posts =               pRepo.getUser(user);
		                   
		  return posts;
	  }
	  
	  
	  // Method related to editing
	  public postCreateBinding edit(Integer pId) {
		  Optional<postEntity>   p=           pRepo.findById(pId);
		         postEntity post = p.get();
		       postCreateBinding   binding = new postCreateBinding() ;
		        BeanUtils.copyProperties(post, binding);
		        
		        
		         
		  return binding;
	  }
	  
	  // Method to control fetching data  want to fetch delted or non deleted
	  
	  public List<postEntity> findAll() {
		    System.out.println("Inside Service");
		List<postEntity>    posts = pRepo.getAllPost();
		 return posts;
		}

	  
	  // method to call overridden delete method
	            public boolean delete (Integer  id) {
	            Optional<postEntity>	 p =            pRepo.findById(id);
	               postEntity post  = p.get();
	               
	                pRepo.delete(post);
	                   return true;
	            }

	       // Method related to  Index search functonality 
	            public List<postEntity> search(searchBinding search){
	            	      postEntity post = new postEntity();
	            	      post.setTitle(search.getTitle());
	            	      System.out.println(post.getTitle());
	            	       
	   List<postEntity> posts =       	       pRepo.findAll(Example.of(post));
	            	    
	            	   
	            	 return posts;
	            }
	            
	            // Method to add comment
	            public String addComment(commentBinding comment,Integer postId) {
	            	  
	            	
	            	  System.out.println(postId);
	            	  Optional<postEntity> p  = pRepo.findById(postId);
	            	    postEntity post = p.get();
	            	    
	            	 commentEntity commentDb = new commentEntity() ;
	            	 
	            	 BeanUtils.copyProperties(comment, commentDb);
	            	    
	            	commentDb.setPost(post);
	            	 cRepo.save(commentDb);
	            	
	            	
	            	return "Comment Added SuccessFully !!!! Kindly Explore More .......";
	            }
	            
	            
	            //Method realted to user-specific search after log-in
	            public List<postEntity>  searchInside(searchBinding search,Integer userId)
	            {
	            	    Optional<userEntity>    u =                 uRepo.findById(userId);
	            	     userEntity user = u.get();
	            	     
	            	     postEntity post = new postEntity();
	            	     BeanUtils.copyProperties(search, post);
	            	     post.setUser(user);
	            	     
	            	              
	            List<postEntity>	posts=            pRepo.findAll(Example.of(post));
	            	
	            	
	            	return posts;
	            }
	            
	            //Mehtod related to fetching all user-specific comments  after login
	            
	            public List<commentEntity> getComments(Integer userId){
	            Optional<userEntity>	 u =                  uRepo.findById(userId);
	            userEntity user = u.get();
	            
	                                    List<postEntity>  posts = pRepo.getUser(user);
	                                    List<commentEntity> comments = new ArrayList<>();
	                                     
	                                    
	                                    for(postEntity post : posts) {
	                                    	      for(commentEntity e : post.getComments()) {
	                                    	    	        comments.add(e);
	                                    	      }
	                                    }
	                                    
	                                    return comments;
	            
	            	
	            }
	            
	            // Method related to comment delete
	            public boolean commentDelete(Integer id) {
	            	
	          	  cRepo.deleteById(id);
	          	   return true;
	            }
                
}
