package com.mballem.curso.jasper.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mballem.curso.jasper.spring.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	@Query("select distinct  e.uf from Endereco e order by e.uf asc")
	List<String> findUfs();

}
