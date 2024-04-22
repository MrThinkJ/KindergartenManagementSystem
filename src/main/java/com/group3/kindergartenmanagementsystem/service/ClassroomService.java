package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.ClassroomDTO;

import java.util.List;

public interface ClassroomService {
    List<ClassroomDTO> getAllClassroom();
    ClassroomDTO getClassroomById(Integer id);
    ClassroomDTO createNewClassroom(ClassroomDTO classroomDTO);
    ClassroomDTO updateClassroomById(Integer id, ClassroomDTO classroomDTO);
    String deleteClassroomById(Integer id);
}
