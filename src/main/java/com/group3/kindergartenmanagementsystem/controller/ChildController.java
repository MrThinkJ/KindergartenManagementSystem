package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.service.ChildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/children")
public class ChildController {
    ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildDTO> getChildById(@PathVariable Integer id){
        return ResponseEntity.ok(childService.getChildById(id));
    }

    @GetMapping("/parent/{id}")
    public ResponseEntity<ChildDTO> getChildByParentId(@PathVariable Integer id){
        return ResponseEntity.ok(childService.getChildByParentId(id));
    }

    @GetMapping("/classroom/{id}")
    public ResponseEntity<List<ChildDTO>> getAllChildByClassroomId(@PathVariable Integer id){
        return ResponseEntity.ok(childService.getAllChildByClassroom(id));
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<ChildDTO>> getAllChildByTeacherId(@PathVariable Integer id){
        return ResponseEntity.ok(childService.getAllChildByTeacher(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildDTO> updateChildById(@PathVariable Integer id,
                                                    @RequestBody ChildDTO childDTO){
        return ResponseEntity.ok(childService.updateChildById(id, childDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChildById(@PathVariable Integer id){
        return ResponseEntity.ok(childService.deleteChildById(id));
    }
}
