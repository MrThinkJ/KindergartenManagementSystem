package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.ClassroomRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.ChildService;
import com.group3.kindergartenmanagementsystem.service.TeacherService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    ChildRepository childRepository;
    ClassroomRepository classroomRepository;
    UserRepository userRepository;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public String addTeacherToClass(Integer teacherId, Integer classroomId) {
        String className = classroomRepository.findById(classroomId).orElseThrow(()-> new ResourceNotFoundException("Classroom", "id", classroomId)).getName();
        List<Child> children = childRepository.findAllByClassroomId(classroomId);
        User teacher = userRepository.findById(teacherId).orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", teacherId));
        children.forEach(child->{
            child.setTeacher(teacher);
            childRepository.save(child);
        });
        return String.format("Add teacher %s to %s", teacher.getFullName(), className);
    }
}
