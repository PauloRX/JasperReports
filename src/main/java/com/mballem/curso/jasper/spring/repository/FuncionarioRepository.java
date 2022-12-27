package com.mballem.curso.jasper.spring.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mballem.curso.jasper.spring.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	@Query("select f.id as id, f.nome as nome from Funcionario f where f.nome like %:nome% and f.dataDemissao is not null and f.nivel.id = 1")
	public List<Tuple> findFuncionarioPorNome(String nome);

}
