package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Classroom;
import com.group3.kindergartenmanagementsystem.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update Child c set c.classroom=null, c.teacher=null where c.classroom.id = ?1")
    void setClassAndTeacherToNull(int classId);
    List<Child> findAllByTeacher(User teacher);
    List<Child> findAllByClassroom(Classroom classroom);
    Child findByParent(User parent);
    Child findByTeacher(User teacher);
}
