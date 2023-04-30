package com.example.demo.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

	Lecturer findByLid(String lid);
	
	@Query(value = "SELECT * FROM lecturer WHERE tax_band = ?1", nativeQuery = true)
    List<Lecturer> findByTaxBandNativeQuery(String taxBand);
}