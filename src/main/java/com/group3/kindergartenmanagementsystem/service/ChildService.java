package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.ChildDTO;

import java.util.List;

public interface ChildService {
    ChildDTO getChildById(Integer id);
    List<ChildDTO> getAllChildByClassroom(Integer classroomId);
    List<ChildDTO> getAllChildByTeacher(Integer teacherId);
    ChildDTO addNewChild(ChildDTO childDTO);
}
