package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.CommentForTeacherDTO;
import com.group3.kindergartenmanagementsystem.service.CommentForTeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacherComment")
public class CommentForTeacherController {
    CommentForTeacherService commentForTeacherService;

    public CommentForTeacherController(CommentForTeacherService commentForTeacherService) {
        this.commentForTeacherService = commentForTeacherService;
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<CommentForTeacherDTO>> getAllCommentByTeacherId(@PathVariable Integer id){
        return ResponseEntity.ok(commentForTeacherService.getAllCommentByTeacherId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentForTeacherDTO> getCommentById(@PathVariable Integer id){
        return ResponseEntity.ok(commentForTeacherService.getCommentById(id));
    }

    @PostMapping
    public ResponseEntity<CommentForTeacherDTO> createNewComment(@RequestBody CommentForTeacherDTO comment){
        return new ResponseEntity<>(commentForTeacherService.createNewComment(comment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentForTeacherDTO> updateCommentById(@PathVariable Integer id,
                                                                  @RequestBody CommentForTeacherDTO comment){
        return ResponseEntity.ok(commentForTeacherService.updateCommentById(id, comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable Integer id){
        return ResponseEntity.ok(commentForTeacherService.deleteCommendById(id));
    }
}
