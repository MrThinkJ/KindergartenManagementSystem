package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.APIException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Role;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.UserDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.ClassroomRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.ChildService;
import com.group3.kindergartenmanagementsystem.service.TeacherService;
import com.group3.kindergartenmanagementsystem.utils.ReceivedRole;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    ChildRepository childRepository;
    ClassroomRepository classroomRepository;
    RoleRepository roleRepository;
    UserRepository userRepository;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public String addTeacherToClass(Integer teacherId, Integer classroomId) {
        User teacher = userRepository.findById(teacherId).orElseThrow(
                ()-> new ResourceNotFoundException("Teacher", "id", teacherId)
        );
        String className = classroomRepository.findById(classroomId).orElseThrow(()-> new ResourceNotFoundException("Classroom", "id", classroomId)).getName();
        List<Child> children = childRepository.findAllByTeacher(teacher);
        if(!teacher.getRoles().contains(roleRepository.findByRoleName(ReceivedRole.getRoleName(ReceivedRole.Teacher))))
            throw new APIException(HttpStatus.BAD_REQUEST, "This id is not belong to teacher");
        children.forEach(child->{
            child.setTeacher(teacher);
            childRepository.save(child);
        });
        return String.format("Add teacher %s to %s", teacher.getFullName(), className);
    }

    @Override
    public List<UserDTO> getAllTeacher() {
        Set<Role> roleSet = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_TEACHER");
        roleSet.add(role);
        List<User> users = userRepository.findByRoles(roleSet);
        return users.stream().map(UserServiceImpl::mapToDTO).collect(Collectors.toList());
    }
}
