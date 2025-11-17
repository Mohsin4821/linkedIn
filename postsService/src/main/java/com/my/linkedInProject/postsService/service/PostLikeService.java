package com.my.linkedInProject.postsService.service;

import com.my.linkedInProject.postsService.entity.PostLike;
import com.my.linkedInProject.postsService.exception.BadRequestException;
import com.my.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.my.linkedInProject.postsService.repository.PostLikeRepository;
import com.my.linkedInProject.postsService.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {
    
    private static final Logger log = LoggerFactory.getLogger(PostLikeService.class);
    
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    
    public PostLikeService(PostLikeRepository postLikeRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void likePost(Long postId) {
        Long userId = 1L;
        log.info("User with ID: {} liking the post with ID: {}", userId, postId);

        postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post not found with ID: "+postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(hasAlreadyLiked) throw new BadRequestException("You cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);

//        TODO: send notification to the owner of the post

    }


    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L;
        log.info("User with ID: {} unliking the post with ID: {}", userId, postId);

        postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post not found with ID: "+postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if(!hasAlreadyLiked) throw new BadRequestException("You cannot unlike the post that you have not liked yet");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
