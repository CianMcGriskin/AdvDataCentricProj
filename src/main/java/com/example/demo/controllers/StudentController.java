package com.example.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.interfaces.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.models.Lecturer;
import com.example.demo.models.Module;
import com.example.demo.models.Student;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class StudentController {
	
	 @Autowired
	 private StudentRepository studentRepository;
	 
	 @GetMapping("/students")
	 public ResponseEntity<List<Map<String, Object>>> getAllStudents() {
	     List<Student> students = studentRepository.findAll();
	     List<Map<String, Object>> result = new ArrayList<>();
	     
	     for (Student student : students) {
	         Map<String, Object> studentMap = new LinkedHashMap<>();
	         studentMap.put("id", student.getId());
	         studentMap.put("sid", student.getSid());
	         studentMap.put("name", student.getName());

	         List<Map<String, Object>> moduleList = new ArrayList<>();
	         for (Module module : student.getModules()) {
	             Map<String, Object> moduleMap = new LinkedHashMap<>();
	             moduleMap.put("id", module.getId());
	             moduleMap.put("mid", module.getMid());
	             moduleMap.put("name", module.getName());
	             moduleMap.put("credits", module.getCredits());
	             moduleMap.put("level", module.getLevel());

	             Map<String, Object> lecturerMap = new LinkedHashMap<>();
	             Lecturer lecturer = module.getLecturer();
	             lecturerMap.put("id", lecturer.getId());
	             lecturerMap.put("name", lecturer.getName());
	             lecturerMap.put("taxBand", lecturer.getTaxBand());
	             lecturerMap.put("salaryScale", lecturer.getSalaryScale());
	             moduleMap.put("lecturer", lecturerMap);

	             moduleList.add(moduleMap);
	         }
	         studentMap.put("modules", moduleList);

	         result.add(studentMap);
	     }
	     
	     return ResponseEntity.ok(result);
	 }
	 
	 @DeleteMapping("/students/{sid}")
	 public ResponseEntity<?> deleteStudent(@PathVariable String sid) {
	     Optional<Student> studentOptional = studentRepository.findBySid(sid);
	     
	     if (studentOptional.isPresent()) {
	         Student student = studentOptional.get();
	         if (!student.getModules().isEmpty()) {
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                 .body("Cannot delete student because they have associated modules.");
	         } else {
	             studentRepository.delete(student);
	             return ResponseEntity.ok().build();
	         }
	     } else {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	             .body(String.format("Student %s does not exist", sid));
	     }
	 }




	 
}
