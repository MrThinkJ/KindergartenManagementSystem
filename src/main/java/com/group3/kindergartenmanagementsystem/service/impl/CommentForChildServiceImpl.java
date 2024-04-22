package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.CommentForChild;
import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.payload.CommentForChildDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.CommentForChildRepository;
import com.group3.kindergartenmanagementsystem.service.CommentForChildService;
import com.group3.kindergartenmanagementsystem.service.CommentForTeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentForChildServiceImpl implements CommentForChildService {
    CommentForChildRepository commentForChildRepository;
    ChildRepository childRepository;
    ModelMapper mapper;

    public CommentForChildServiceImpl(CommentForChildRepository commentForChildRepository, ChildRepository childRepository, ModelMapper mapper) {
        this.commentForChildRepository = commentForChildRepository;
        this.childRepository = childRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CommentForChildDTO> getAllCommentByChildId(Integer childId) {
        List<CommentForChild> comments = commentForChildRepository.findAllByChildId(childId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentForChildDTO getCommentById(Integer id) {
        CommentForChild comment = commentForChildRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment for child", "id", id));
        return mapToDTO(comment);
    }

    @Override
    public CommentForChildDTO createNewComment(CommentForChildDTO commentForChildDTO) {
        Child child = childRepository.findById(commentForChildDTO.getChildId()).orElseThrow(
                () -> new ResourceNotFoundException("Child", "id", commentForChildDTO.getChildId()));
        CommentForChild comment = mapToEntity(commentForChildDTO);
        comment.setChild(child);
        return null;
    }

    @Override
    public CommentForChildDTO updateCommentById(Integer id, CommentForChildDTO commentForChildDTO) {
        CommentForChild comment = commentForChildRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment for child", "id", id));
        Child child = childRepository.findById(commentForChildDTO.getChildId()).orElseThrow(() -> new ResourceNotFoundException("Child", "id", id));
        comment.setComment(comment.getComment());
        comment.setPostMonth(commentForChildDTO.getPostMonth());
        comment.setChild(child);
        CommentForChild newComment = commentForChildRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public String deleteCommentById(Integer id) {
        CommentForChild comment = commentForChildRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment for child", "id", id));
        commentForChildRepository.delete(comment);
        return "Comment deleted successfully";
    }

    private CommentForChildDTO mapToDTO(CommentForChild comment){
        return CommentForChildDTO.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .childId(comment.getChild().getId())
                .build();
    }

    private CommentForChild mapToEntity(CommentForChildDTO commentForChildDTO){
        return CommentForChild.builder()
                .id(commentForChildDTO.getId())
                .comment(commentForChildDTO.getComment())
                .build();
    }
}
