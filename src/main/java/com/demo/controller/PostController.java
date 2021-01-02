package com.demo.controller;

import com.demo.dao.PostRepository;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.Comment;
import com.demo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
/*post http://localhost:8080/posts?page=0&size=2&sort=createdAt,desc
{
	"title": "p3",
	"description":"p3 description",
	"content":"p3 content"
}
*/
//get http://localhost:8080/posts?page=0&size=2&sort=createdAt,desc

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     *
     * http://localhost:8080/lazy/posts/1
     *
     * **/
    @GetMapping("/lazy/posts/{postId}")
    public List<Comment> getPostById(@PathVariable (value = "postId") Long postId) {
        Optional<Post>  post=postRepository.findById(postId);
        List<Comment> comment=post.map(p->{return p.getComments();}).orElse( new ArrayList<Comment>());
        Comment c1=comment.get(1);
        String c1text=c1.getText();
        Date createAt1= c1.getCreatedAt();
        Comment c2= comment.get(2);
        String c2text=c1.getText();
        Date createAt2= c1.getCreatedAt();
        return comment;

    }

    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/posts/{postId}")
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId).map(post -> {
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setContent(postRequest.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }


    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
}
