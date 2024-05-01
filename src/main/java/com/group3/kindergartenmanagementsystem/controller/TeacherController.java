package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController {
    TeacherService teacherService;
    @PutMapping("/{teacherId}/addToClass/{classroomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addTeacherToClass(@PathVariable Integer teacherId,
                                                    @PathVariable Integer classroomId){
        return ResponseEntity.ok(teacherService.addTeacherToClass(teacherId, classroomId));
    }
}
