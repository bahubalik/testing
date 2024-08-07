package com.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
private long id;
@NotEmpty
@Size(min = 2, message = "post title should have at least 2 characters" )
private String title;
@NotEmpty
@Size(min = 5, message = "post description should have at least 5 characters")
private String description;
@NotEmpty
private String content;
}
