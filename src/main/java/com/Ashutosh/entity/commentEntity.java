package com.Ashutosh.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Comment_Table")
public class commentEntity {
	 
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer comment_id;
	@Lob
	private String content;
	private String name;
	private String email;
	 @CreationTimestamp
	private LocalDate createdOn;
	 
	  @ManyToOne
	  @JoinColumn(name = "post_id")
	 private postEntity post;
	

}
