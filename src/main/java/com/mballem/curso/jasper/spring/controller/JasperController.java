package com.mballem.curso.jasper.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mballem.curso.jasper.spring.repository.EnderecoRepository;
import com.mballem.curso.jasper.spring.repository.FuncionarioRepository;
import com.mballem.curso.jasper.spring.repository.NivelRepository;
import com.mballem.curso.jasper.spring.service.JasperService;

import net.sf.jasperreports.engine.JRException;

@Controller
public class JasperController {

	@Autowired
	private JasperService jasperService;

	@Autowired
	private NivelRepository nivelRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@GetMapping("/reports")
	public String abrir() {
		return "reports";
	}

	@GetMapping("/relatorio/pdf/jr01")
	public void exibirRelatorio01(@RequestParam String code, @RequestParam String acao, HttpServletResponse response)
			throws IOException {

		byte[] bytes = jasperService.exportarPDF(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		if ("v".equals(acao)) {
			response.setHeader("Content-disposition", "inline; filename=relatorio-" + code + ".pdf");
		} else {
			response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
		}
		response.getOutputStream().write(bytes);
	}

	@GetMapping("/relatorio/pdf/jr09/{code}")
	public void exibirRelatorio09(@PathVariable String code,
			@RequestParam(name = "nivel", required = false) String nivel,
			@RequestParam(name = "uf", required = false) String uf, HttpServletResponse response) throws IOException {
		jasperService.addParams("NIVEL_DESC", nivel.isEmpty() ? null : nivel);
		jasperService.addParams("UF", uf.isEmpty() ? null : uf);
		byte[] bytes = jasperService.exportarPDF(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader("Content-disposition", "inline; filename=relatorio-" + code + ".pdf");
		response.getOutputStream().write(bytes);
	}
	
	@GetMapping("/buscar/funcionarios")
	public ModelAndView buscarFuncionarioPorNome(@RequestParam String nome) {
		return new ModelAndView("reports", "funcionarios", funcionarioRepository.findFuncionarioPorNome(nome)); 
	}
	
	@GetMapping("/relatorio/pdf/jr19/{code}")
	public void exibirRelatorio19(@PathVariable String code,
			@RequestParam(name = "idf", required = false) Long id, HttpServletResponse response) throws IOException {
		jasperService.addParams("ID_FUNCIONARIO", id);  
		byte[] bytes = jasperService.exportarPDF(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader("Content-disposition", "inline; filename=relatorio-" + code + ".pdf");
		response.getOutputStream().write(bytes);
	}
	
	@GetMapping("/relatorio/html/jr19/{code}")
	public void exibirRelatorio19Html(@PathVariable String code,
			@RequestParam(name = "idf", required = false) Long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, JRException {
		
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		jasperService.addParams("ID_FUNCIONARIO", id);
		jasperService.exportarHTML(code, request,response).exportReport();
		
	}

	@ModelAttribute("niveis")
	public List<String> getNiveis() {
		return nivelRepository.findNiveis();
	}

	@ModelAttribute("ufs")
	public List<String> getUfs() {
		return enderecoRepository.findUfs();
	}

}
