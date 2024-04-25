package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.ClassroomDTO;
import com.group3.kindergartenmanagementsystem.service.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classroom")
public class ClassroomController {
    ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<ClassroomDTO>> getAllClassroom(){
        return ResponseEntity.ok(classroomService.getAllClassroom());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable Integer id){
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @PostMapping
    public ResponseEntity<ClassroomDTO> createNewClassroom(@RequestBody ClassroomDTO classroomDTO){
        return new ResponseEntity<>(classroomService.createNewClassroom(classroomDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDTO> updateClassroomById(@PathVariable Integer id,
                                                            @RequestBody ClassroomDTO classroomDTO){
        return ResponseEntity.ok(classroomService.updateClassroomById(id, classroomDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassroomById(@PathVariable Integer id){
        return ResponseEntity.ok(classroomService.deleteClassroomById(id));
    }
}
