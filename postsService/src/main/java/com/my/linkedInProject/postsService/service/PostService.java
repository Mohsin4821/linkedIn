package com.my.linkedInProject.postsService.service;

import com.my.linkedInProject.postsService.dto.PostCreateRequestDto;
import com.my.linkedInProject.postsService.dto.PostDto;
import com.my.linkedInProject.postsService.entity.Post;
import com.my.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.my.linkedInProject.postsService.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    
    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId){
        log.info("creating post for user with id: {}",userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.info("Getting the post with ID: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found " +
                "with ID: "+postId));
        return modelMapper.map(post, PostDto.class);
    }

//    public PostDto getPostById(Long postId) {
//        log.info("Getting the post with ID: {}", postId);
//
//        Long userId = AuthContextHolder.getCurrentUserId();
//
////        TODO: Remove in future
////        Call the Connections Service from the Posts Service and pass the userId inside the headers
//
//        List<PersonDto> personDtoList = connectionsServiceClient.getFirstDegreeConnections(userId);
//
//        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found " +
//                "with ID: "+postId));
//        return modelMapper.map(post, PostDto.class);
//    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all the posts of a user with ID: {}", userId);
        List<Post> postList = postRepository.findByUserId(userId);

        return postList
                .stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .collect(Collectors.toList());
    }
}
