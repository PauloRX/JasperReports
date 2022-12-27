package com.mballem.curso.jasper.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mballem.curso.jasper.spring.entity.Nivel;

public interface NivelRepository extends JpaRepository<Nivel, Long> {
	
	@Query("select n.nivel from Nivel n")
	List<String> findNiveis ();

}
