package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.CommentForTeacher;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.CommentForTeacherDTO;
import com.group3.kindergartenmanagementsystem.repository.CommentForTeacherRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.CommentForTeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentForTeacherServiceImpl implements CommentForTeacherService {
    CommentForTeacherRepository commentForTeacherRepository;
    UserRepository userRepository;
    ModelMapper mapper;

    public CommentForTeacherServiceImpl(CommentForTeacherRepository commentForTeacherRepository, UserRepository userRepository, ModelMapper mapper) {
        this.commentForTeacherRepository = commentForTeacherRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CommentForTeacherDTO> getAllCommentByTeacherId(Integer teacherId) {
        List<CommentForTeacher> comments = commentForTeacherRepository.findAllByTeacherId(teacherId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentForTeacherDTO getCommentById(Integer id) {
        CommentForTeacher comment = commentForTeacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment for teacher", "id", id));
        return mapToDTO(comment);
    }

    @Override
    public CommentForTeacherDTO createNewComment(CommentForTeacherDTO commentForTeacherDTO) {
        CommentForTeacher comment = mapToEntity(commentForTeacherDTO);
        User teacher = userRepository.findById(commentForTeacherDTO.getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", commentForTeacherDTO.getTeacherId()));
        comment.setTeacher(teacher);
        CommentForTeacher newComment = commentForTeacherRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public CommentForTeacherDTO updateCommentById(Integer id, CommentForTeacherDTO commentForTeacherDTO) {
        CommentForTeacher comment = commentForTeacherRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment for teacher", "id", id));
        User teacher = userRepository.findById(commentForTeacherDTO.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", commentForTeacherDTO.getTeacherId()));
        comment.setComment(commentForTeacherDTO.getComment());
        comment.setAttitudeScore(commentForTeacherDTO.getAttitudeScore());
        comment.setCreativeScore(commentForTeacherDTO.getCreativeScore());
        comment.setTeacher(teacher);
        CommentForTeacher updatedComment = commentForTeacherRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public String deleteCommendById(Integer id) {
        CommentForTeacher comment = commentForTeacherRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment for teacher", "id", id));
        commentForTeacherRepository.delete(comment);
        return "Delete comment successfully";
    }

    private CommentForTeacherDTO mapToDTO(CommentForTeacher comment){
        CommentForTeacherDTO commentForTeacherDTO = mapper.map(comment, CommentForTeacherDTO.class);
        commentForTeacherDTO.setTeacherId(comment.getTeacher().getId());
        return commentForTeacherDTO;
    }

    private CommentForTeacher mapToEntity(CommentForTeacherDTO comment){
        User teacher = userRepository.findById(comment.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", comment.getTeacherId()));
        CommentForTeacher commentForTeacher = mapper.map(comment, CommentForTeacher.class);
        commentForTeacher.setTeacher(teacher);
        return commentForTeacher;
    }
}
