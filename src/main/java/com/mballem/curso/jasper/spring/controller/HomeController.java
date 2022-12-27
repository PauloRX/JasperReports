package com.mballem.curso.jasper.spring.controller;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mballem.curso.jasper.spring.entity.Funcionario;
import com.mballem.curso.jasper.spring.repository.FuncionarioRepository;

@Controller
public class HomeController {

	@Autowired
    private Connection connection;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@GetMapping("/")
    public String index() {
        return "index";
    }
    
	@GetMapping("/conn")
    public String myConn(Model model) {
    	model.addAttribute("conn", connection != null ? "Conexao OK!" : "Moio");
    	return "index";
    }
	
	@GetMapping("/certificado")
    public String certificadoValidador(@RequestParam("cid") Long cid, Model model) {
		Funcionario funcionario = funcionarioRepository.findById(cid).get();
    	model.addAttribute("mensagem", "Confirmada a veracidade da certificaçao de "
    			+ funcionario.getNome()
    			+ " expedido em "
    			+ DateTimeFormatter.ofPattern("dd/MM/yyyy").format(funcionario.getDataDemissao())
    			+".");
    	return "index";
    }

}
