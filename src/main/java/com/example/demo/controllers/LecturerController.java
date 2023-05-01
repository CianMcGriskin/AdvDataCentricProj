package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.interfaces.LecturerRepository;
import com.example.demo.models.Lecturer;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LecturerController {

    @Autowired
    private LecturerRepository lecturerRepository;

    @GetMapping("/lecturers")
    public ResponseEntity<List<Map<String, Object>>> getAllLecturers() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Lecturer lecturer : lecturers) {
            Map<String, Object> lecturerMap = new LinkedHashMap<>();
            lecturerMap.put("id", lecturer.getId());
            lecturerMap.put("lid", lecturer.getLid());
            lecturerMap.put("name", lecturer.getName());
            lecturerMap.put("taxBand", lecturer.getTaxBand());
            lecturerMap.put("salaryScale", lecturer.getSalaryScale());
            result.add(lecturerMap);
        }
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/lecturer")
    public ResponseEntity<Object> createLecturer(@RequestBody Lecturer lecturer) {
        try {
            // Ensure the Lecturer object does not contain an id
            if (lecturer.getId() != null) {
                throw new IllegalArgumentException("Cannot create Lecturer with existing id");
            }
            
            // Ensure the Lecturer object contains a lid and name
            if (lecturer.getLid() == null || lecturer.getName() == null) {
                throw new IllegalArgumentException("Lecturer must have a lid and name");
            }
            
            // Check if a lecturer with the same lid already exists
            Lecturer existingLecturer = lecturerRepository.findByLid(lecturer.getLid());
            if (existingLecturer != null) {
                String errorMessage = "Lecturer: " + lecturer.getLid() + " already exists";
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
            }
            
            // Save the Lecturer object to the database
            Lecturer createdLecturer = lecturerRepository.save(lecturer);
            return ResponseEntity.ok(createdLecturer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @PutMapping("/lecturer/{lid}")
    public ResponseEntity<Lecturer> putLecturer(@PathVariable String lid, @RequestBody Lecturer updatedLecturer) {
        Optional<Lecturer> lecturerOptional = Optional.of(lecturerRepository.findByLid(lid));
        if (!lecturerOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Lecturer lecturer = lecturerOptional.get();

        lecturer.setName(updatedLecturer.getName());
        lecturer.setTaxBand(updatedLecturer.getTaxBand());
        lecturer.setSalaryScale(updatedLecturer.getSalaryScale());

        lecturerRepository.save(lecturer);

        return ResponseEntity.ok(lecturer);
    }
    
    @GetMapping("lecturer/search")
    public List<Lecturer> getLecturersByTaxBand(@RequestParam("taxBand") String taxBand) {
        return lecturerRepository.findByTaxBandNativeQuery(taxBand);
    }
}

