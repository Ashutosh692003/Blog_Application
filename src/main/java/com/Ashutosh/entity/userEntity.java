package com.Ashutosh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User_Dtls")
@Getter
@Setter
public class userEntity {
	
	
	      @Id
	     @GeneratedValue(strategy = GenerationType.IDENTITY)
          private Integer user_id;
	        private String firstName;
	        private String lastName;
	        private String email;
	        private String pwd;
}
