package com.Ashutosh.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.Ashutosh.service.service;

import jakarta.servlet.http.HttpSession;

@Controller
public class controller {
	                                          @Autowired
	                                          service s;
	                                          
	                                          @Autowired
	                                          HttpSession session;
	                                          
	                                          @Autowired
	                                          postRepo pRepo;
	                                          
	                                          
	                                          
	                                          
	                      //Related to logout functionality
	                                @GetMapping("/logout")
	                     public String logout(Model m) {
	                    	     session.invalidate();
	                    	     return "redirect:/";
	                    	     
	                     }
	                                          
	                                          
	                                          
	                                          
	                                          
	   // index page                                       
	                                          
	   @GetMapping("/")
	 public String index(Model m) {
	List<postEntity> posts=  s.findAll();
	      commentBinding comment= new commentBinding();
	      m.addAttribute("posts",posts);
	      m.addAttribute("comment", comment);
	      m.addAttribute("search", new searchBinding());
	                    
		 return "index";
	 }
	   
	   
	        // Related to Signup functionality
	    @GetMapping("/register")
	   public String registration(Model m) {
	    	registerBinding regis = new registerBinding();
	    	m.addAttribute("register", regis);
		   return "register";
	   }
	    
	    @PostMapping("/register")
		   public String registrationDb(@ModelAttribute("register")registerBinding bind,Model m) {
		  String msg=           s.register(bind);
		       
		         if(msg.contains("success")) {
		        	  m.addAttribute("succMsg", "Registration SucessFull !!!!!!!");
		         }
		         else if(msg.contains("Email")) {
		        	  m.addAttribute("emailMsg","Email Already Registered !!!!!!!");
		         }
		         else { 
		        	 m.addAttribute("errMsg", "Something Went Wrong !!!!!!!");
		         }
		   
			   return "register";
		   }
	     
	    
	    //Related to login functionality
	     @GetMapping("/login")
	    public String login(Model m) {
	    	 
	    	  loginBinding log = new loginBinding();
	    	   m.addAttribute("log", log);
	    	return "login";
	    }
	     
	      @PostMapping("/login")
	     public String loginDB(loginBinding log,Model m) {
		String msg= 	  s.login(log);
		System.out.println(msg);
		      if(msg.contains("incorrect")) {
		    	   m.addAttribute("errMsg", msg);
		    	   m.addAttribute("log", log);
		    	   System.out.println("Inside login controller");
		      
		    	    return "login";}
		    	  
		      System.out.println("outside login controller");
	    	  
	    	  return "redirect:/dashboard";
	    	 
	     }
	      
	       // Related to dashboard view
	      
	       @GetMapping("/dashboard")
	      public String dashBoard(Model m) {
	    	       Integer userId = (Integer) session.getAttribute("userId");
	    	List<postEntity>    posts =           s.dashboardData(userId);
	    	 m.addAttribute("posts", posts);
	    	 searchBinding search = new searchBinding();
	    	 m.addAttribute("search", search);
	    	         
	    	   return "dashboard";
	      } 
	       
	       
	       //  Related to Post Creation
	       
	       @GetMapping("/blogCreate")
	       public String createBlog(Model m) {
	    	   postCreateBinding post = new postCreateBinding();
	    	    m.addAttribute("post", post);
	    	    return "blogCreate";
	       }
	    	    
	        @PostMapping("/blogCreate")
	       public String createBlogDb(@ModelAttribute("post")postCreateBinding post,Model m) {
	        	Integer userId = (Integer) session.getAttribute("userId");
	        	System.out.println(userId);
	    	boolean b =        s.createPost(post, userId);
	    	   if(b) {
	    		          m.addAttribute("succMsg", "Post Uploaded Successfully!!!!!!!!");
	    	   }
	    	   else {
	    		      m.addAttribute("errMsg","Something Went Wrong!!!!!!!");
	    	   }
	    	   
	    	    return "blogCreate";
	       }
	     
	     // Related to Update Post
	         @GetMapping("/edit")
	        public String edit(@RequestParam("id") Integer pId ,Model m) {
	        	      
	        	  postCreateBinding post = s.edit(pId);
	        	  m.addAttribute("post", post);
	        	  
	        	 
	        	 return "blogCreate";
	        }
	         
	         // Related to Delete functionality
	               @GetMapping("/delete")
	         public String delete(@RequestParam("id") Integer id,Model m) {
	        	        
	        	                s.delete(id);
	        	                Integer userId = (Integer) session.getAttribute("userId");
	        	    	    	List<postEntity>    posts =           s.dashboardData(userId);
	        	    	    	 
	        	    	    	 m.addAttribute("posts", posts);
	        	    	    	 m.addAttribute("deleteMsg","Post Deleted Successfully");
	        	    	    	 searchBinding search = new searchBinding();
	        	    	    	 m.addAttribute("search", search);
	        	                
	        	 return "dashboard";
	        	 
	         }
	               
	                
	                // related to  add comments
	                @PostMapping("/comment")
	                public String comment(Model m, commentBinding comments, @RequestParam("id") Integer postId) {
	                    String comMsg = s.addComment(comments, postId);

	                    // Redirect to the display controller with postId as a parameter
	                    return "redirect:/display?id=" + postId;
	                }

	               
	              // search post through Index 
	                @PostMapping("/searchIndex")
	                   public String searchIndex(searchBinding search,Model m) {
	                	               if(search.getTitle()=="") {
	                	            	   search.setTitle(null);
	                	               }
	                	   List<postEntity> posts = s.search(search);
	                	  
	                	  
	            	       m.addAttribute("posts", posts);
	            	       m.addAttribute("search", new searchBinding());
	            	       
	                	    return "index";
	                   }
	                
	                
	                //search after login user specific
	                @PostMapping("/searchInside")
	                public String searchInside(@ModelAttribute("search")searchBinding search,Model m) {
	                	 if(search.getTitle()=="") {
      	            	   search.setTitle(null);
      	               }
	                	 Integer userId = (Integer) session.getAttribute("userId");
	               List<postEntity> posts=	     s.searchInside(search, userId);
	                	 
	                	 m.addAttribute("posts", posts);
	                	 
	                	 return "dashboard";
	                	
	                }
	                
	                //realted view user-specific comment after login
	                @GetMapping("comment")
	                public String comment(Model m) {
	                	Integer userId = (Integer) session.getAttribute("userId");
	           List<commentEntity>    comments = 	         s.getComments(userId);
	                	m.addAttribute("comments", comments);
	                	           
	                	return "comment";
	                	
	                }
	                
	                // related to delete comment
	                @GetMapping("/deleteComment")
	             public String deleteComment(@RequestParam("id")Integer id,Model m) {
	                	 
	       boolean b =          	 s.commentDelete(id);
	       m.addAttribute("deleteMsg", "Comment Deleted Sucessfully!!!");
	       
	        
	       Integer userId = (Integer) session.getAttribute("userId");
           List<commentEntity>    comments = 	         s.getComments(userId);
                	m.addAttribute("comments", comments);
	                	 
                	return "comment";
	                }
	                
	                // related to content display
	                @GetMapping("/display")
	                public String display(Model m,@RequestParam("id") Integer id) {
	                	 Optional<postEntity>   p =       pRepo.findById(id);
	                	 postEntity post = p.get();
	                	 m.addAttribute("post", post);
	                	 commentBinding comment= new commentBinding();
	                	 m.addAttribute("comment", comment); 
	                	 return "contentDisplay";
	                }
	               

}
