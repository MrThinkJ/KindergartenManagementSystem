package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.*;
import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.ClassroomRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.ChildService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildServiceImpl implements ChildService {
    ChildRepository childRepository;
    UserRepository userRepository;
    ClassroomRepository classroomRepository;
    ModelMapper modelMapper;

    public ChildServiceImpl(ChildRepository childRepository, UserRepository userRepository, ClassroomRepository classroomRepository, ModelMapper modelMapper) {
        this.childRepository = childRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ChildDTO getChildById(Integer id) {
        Child child = childRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Child", "id", id));
        return mapToDTO(child);
    }

    @Override
    public List<ChildDTO> getAllChildByClassroom(Integer classroomId) {
        List<Child> children = childRepository.findAllByClassroomId(classroomId);
        return children.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ChildDTO> getAllChildByTeacher(Integer teacherId) {
        List<Child> children = childRepository.findAllByTeacherId(teacherId);
        return children.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ChildDTO addNewChild(ChildDTO childDTO) {
        Child child = mapAddToEntity(childDTO);
        User parent = userRepository.findById(childDTO.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Parent", "id", childDTO.getParentId()));
        User teacher = userRepository.findById(childDTO.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", childDTO.getTeacherId()));
        Classroom classroom = classroomRepository.findById(childDTO.getClassroomId()).orElseThrow(()-> new ResourceNotFoundException("Class","id", childDTO.getClassroomId()));
        child.setParent(parent);
        child.setTeacher(teacher);
        child.setClassroom(classroom);
        List<Album> albums = new ArrayList<>();
        List<CommentForChild> commentsForChild = new ArrayList<>();
        List<MedicineReminder> medicineReminders = new ArrayList<>();
        child.setComments(commentsForChild);
        child.setMedicineReminders(medicineReminders);
        child.setAlbums(albums);
        Child newChild = childRepository.save(child);
        return mapToDTO(newChild);
    }

    private Child mapToEntity(ChildDTO childDTO){
        return modelMapper.map(childDTO, Child.class);
    }

    private ChildDTO mapToDTO(Child child){
        ChildDTO childDTO = modelMapper.map(child, ChildDTO.class);
        childDTO.setParentId(child.getParent().getId());
        childDTO.setTeacherId(child.getTeacher().getId());
        childDTO.setClassroomId(child.getClassroom().getId());
        return childDTO;
    }

    private Child mapAddToEntity(ChildDTO childDTO){
        return Child.builder()
                .id(childDTO.getId())
                .fullName(childDTO.getFullName())
                .age(childDTO.getAge())
                .birthDay(childDTO.getBirthDay())
                .height(childDTO.getHeight())
                .weight(childDTO.getWeight())
                .gender(childDTO.getGender())
                .hobby(childDTO.getHobby())
                .build();
    }
}
