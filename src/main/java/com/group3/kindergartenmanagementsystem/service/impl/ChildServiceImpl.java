package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.APIException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.*;
import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.ClassroomRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.ChildService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChildServiceImpl implements ChildService {
    ChildRepository childRepository;
    UserRepository userRepository;
    ClassroomRepository classroomRepository;
    RoleRepository roleRepository;
    ModelMapper modelMapper;

    @Override
    public ChildDTO getChildById(Integer id) {
        Child child = childRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Child", "id", id));
        return mapToDTO(child);
    }

    @Override
    public ChildDTO getChildByParentId(Integer parentId) {
        Child child = childRepository.findByParentId(parentId);
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
    public ChildDTO createNewChild(ChildDTO childDTO) {
        Child child = mapAddToEntity(childDTO);
        User parent = userRepository.findById(childDTO.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Parent", "id", childDTO.getParentId()));
        User teacher = userRepository.findById(childDTO.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", childDTO.getTeacherId()));
        Classroom classroom = classroomRepository.findById(childDTO.getClassroomId()).orElseThrow(()-> new ResourceNotFoundException("Classroom","id", childDTO.getClassroomId()));
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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<ChildDTO> addChildToClassroom(List<Integer> childIds, Integer classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(()-> new ResourceNotFoundException("Classroom", "id", classroomId));
        return childIds.stream().map(
                childId->{
                    Child child = childRepository.findById(childId).orElseThrow(()-> new ResourceNotFoundException("Child", "id", childId));
                    child.setClassroom(classroom);
                    Child updatedChild = childRepository.save(child);
                    return mapToDTO(updatedChild);
                }
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<ChildDTO> addChildToTeacher(List<Integer> childIds, Integer teacherId) {
        User teacher = userRepository.findById(teacherId).orElseThrow(()-> new ResourceNotFoundException("User", "id", teacherId));
        Role teacherRole = roleRepository.findByRoleName("ROLE_TEACHER");
        if (!teacher.getRoles().contains(teacherRole))
            throw new APIException(HttpStatus.BAD_REQUEST, "This user doesn't have teacher role");
        return childIds.stream().map(
                childId ->{
                    Child child = childRepository.findById(childId).orElseThrow(()-> new ResourceNotFoundException("Child", "id", childId));
                    child.setTeacher(teacher);
                    Child updatedChild = childRepository.save(child);
                    return mapToDTO(updatedChild);
                }
        ).collect(Collectors.toList());
    }

    @Override
    public ChildDTO updateChildById(Integer id, ChildDTO childDTO) {
        Child child = childRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Child", "id", id));
        User parent = userRepository.findById(childDTO.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Parent", "id", childDTO.getParentId()));
        User teacher = userRepository.findById(childDTO.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", childDTO.getTeacherId()));
        Classroom classroom = classroomRepository.findById(childDTO.getClassroomId()).orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", childDTO.getClassroomId()));
        child.setParent(parent);
        child.setTeacher(teacher);
        child.setClassroom(classroom);
        Child updatedChild = childRepository.save(child);
        return mapToDTO(updatedChild);
    }

    @Override
    public String deleteChildById(Integer id) {
        Child child = childRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Child", "id", id));
        childRepository.delete(child);
        return "Child deleted successfully !";
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
