package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.UserDTO;

import java.util.List;

public interface TeacherService {
    String addTeacherToClass(Integer teacherId, Integer classroomId);
    List<UserDTO> getAllTeacher();
}
