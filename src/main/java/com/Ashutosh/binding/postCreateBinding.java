package com.Ashutosh.binding;

import jakarta.persistence.Lob;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class postCreateBinding {
	    private Integer postId;
        private String title;
        private String description;
         @Lob
        private String content;
}
