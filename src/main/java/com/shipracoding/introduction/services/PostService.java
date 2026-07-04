package com.shipracoding.introduction.services;

import com.shipracoding.introduction.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {
    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePostById(PostDTO inputPost, Long postId);
}
