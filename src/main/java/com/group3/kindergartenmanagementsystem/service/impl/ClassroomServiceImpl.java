package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Classroom;
import com.group3.kindergartenmanagementsystem.payload.ClassroomDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.ClassroomRepository;
import com.group3.kindergartenmanagementsystem.service.ClassroomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    ClassroomRepository classroomRepository;
    ChildRepository childRepository;
    ModelMapper mapper;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, ChildRepository childRepository, ModelMapper mapper) {
        this.childRepository = childRepository;
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ClassroomDTO> getAllClassroom() {
        List<Classroom> classrooms = classroomRepository.findAll();
        return classrooms.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ClassroomDTO getClassroomById(Integer id) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", id));
        return mapToDTO(classroom);
    }

    @Override
    public ClassroomDTO createNewClassroom(ClassroomDTO classroomDTO) {
        Set<Child> children = new HashSet<>();
        Classroom classroom = new Classroom(classroomDTO.getId(), classroomDTO.getName(), children);
        Classroom newClassroom = classroomRepository.save(classroom);
        return mapToDTO(newClassroom);
    }

    @Override
    public ClassroomDTO updateClassroomById(Integer id, ClassroomDTO classroomDTO) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", id));
        Set<Child> children = classroomDTO.getChildIds().stream().map(
                childId -> childRepository.findById(childId).orElseThrow(()-> new ResourceNotFoundException("Child", "id", childId))).collect(Collectors.toSet());
        classroom.setChildren(children);
        classroom.setName(classroomDTO.getName());
        Classroom updatedClass = classroomRepository.save(classroom);
        return mapToDTO(updatedClass);
    }

    @Override
    public String deleteClassroomById(Integer id) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", id));
        classroomRepository.delete(classroom);
        return "Delete classroom successfully";
    }

    private ClassroomDTO mapToDTO(Classroom classroom){
        Set<Integer> childIds = classroom.getChildren().stream().map(Child::getId).collect(Collectors.toSet());
        return ClassroomDTO.builder()
                .id(classroom.getId())
                .name(classroom.getName())
                .childIds(childIds)
                .build();
    }

    private Classroom mapToEntity(ClassroomDTO classroomDTO){
        return mapper.map(classroomDTO, Classroom.class);
    }
}
