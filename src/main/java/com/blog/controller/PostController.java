package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponce;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
     private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponce getAllPost(@RequestParam(value = "pageNo", defaultValue = "0",required = false)int pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = "5",required = false)int pageSize,
                                   @RequestParam(value = "sortBy", defaultValue = "id",required = false)String sortBy,
                                   @RequestParam(value = "sortDir", defaultValue = "0",required = false)String sortDir){
        PostResponce responce=postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return responce;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto=postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        PostDto dto=postService.updatePost(id,postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted", HttpStatus.OK);

    }
}
