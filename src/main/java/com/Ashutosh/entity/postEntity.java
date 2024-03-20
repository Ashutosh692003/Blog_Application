package com.Ashutosh.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Post_Table")
@SQLDelete(sql = "UPDATE Post_Table SET deleted = true where post_id=?")
@FilterDef(name="deltedPostFilter" ,parameters = @ParamDef(name="isDeleted",type = boolean.class))
@Filter (name = "deletedPostFilter",condition="deleted=:isDeleted")
public class postEntity {
	
	       @Id
	       @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer postId;
      private String title;
      private String description;
      @Lob
      @Column(length = 65555)
      private String content;
      
       @CreationTimestamp
       @Column( nullable = false, updatable = false)
      private LocalDate createdOn;
       @UpdateTimestamp
       private LocalDate updateOn;
       
       @ManyToOne
       @JoinColumn(name = "user_id")
       private userEntity user;
       
      @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE) 
       private List<commentEntity> comments;
       
       private boolean deleted = Boolean.FALSE;
}
