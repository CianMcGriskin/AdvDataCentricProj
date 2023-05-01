package com.example.demo.interfaces;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.demo.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s JOIN FETCH s.modules m JOIN FETCH m.lecturer")
    List<Student> findAllWithModulesAndLecturers();
    Optional<Student> findBySid(String sid);

}
