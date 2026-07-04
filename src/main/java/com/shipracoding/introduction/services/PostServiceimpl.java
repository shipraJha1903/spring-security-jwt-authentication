package com.shipracoding.introduction.services;

import com.shipracoding.introduction.dto.PostDTO;
import com.shipracoding.introduction.entities.PostEntity;
import com.shipracoding.introduction.entities.UserEntity;
import com.shipracoding.introduction.exceptions.ResourceNotFoundException;
import com.shipracoding.introduction.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceimpl implements PostService {
    private static final Logger log = LoggerFactory.getLogger(PostServiceimpl.class);
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
      PostEntity postEntity = modelMapper.map(inputPost,PostEntity.class);
      PostEntity newSavedPost = postRepository.save(postEntity);
      return modelMapper.map(newSavedPost, PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {

        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user {}",user);
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePostById(PostDTO inputPost, Long postId) {
       PostEntity existingPost = postRepository.findById(postId).orElseThrow();
       inputPost.setId(postId);
       modelMapper.map(inputPost,existingPost);
       PostEntity existingPostUpdated = postRepository.save(existingPost);
       return modelMapper.map(existingPostUpdated, PostDTO.class);
    }
// input post jo le rahe woh basically chije hain jo meko update krni hain

}
