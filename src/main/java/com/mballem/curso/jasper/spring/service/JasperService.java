package com.mballem.curso.jasper.spring.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.HtmlResourceHandler;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

@Service
public class JasperService {
	
	private static final String JASPER_DIRETORIO = "classpath:jasper/";
	private static final String JASPER_PREFIXO = "funcionarios-";
	private static final String JASPER_SUFIXO = ".jasper";
	
	
	
	public JasperService() {
		this.params.put("IMAGEM_DIRETORIO", JASPER_DIRETORIO);
		this.params.put("SUB_REPORT_DIR", JASPER_DIRETORIO);
	}

	private Map<String, Object> params = new HashMap<>();
	
	@Autowired
	private Connection connection;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public void addParams(String key, Object value) {
		this.params.put(key, value);
	}
	
	public byte[] exportarPDF(String code) {
		byte[] bytes = null;
		try {
			File file = ResourceUtils.getFile(JASPER_DIRETORIO.concat(JASPER_PREFIXO).concat(code).concat(JASPER_SUFIXO));
			JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), params, connection);
			bytes = JasperExportManager.exportReportToPdf(print);
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
		} 
		return bytes;
	}

	public HtmlExporter exportarHTML(String code, HttpServletRequest request, HttpServletResponse response) {
		HtmlExporter exporter = null;
		try {
			Resource resource = resourceLoader.getResource(JASPER_DIRETORIO.concat(JASPER_PREFIXO).concat(code).concat(JASPER_SUFIXO));
			InputStream is = resource.getInputStream();
			JasperPrint print = JasperFillManager.fillReport(is, params, connection);
			exporter = new HtmlExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			
			SimpleHtmlExporterOutput htmlExporterOutput = new SimpleHtmlExporterOutput(response.getWriter());
			
			HtmlResourceHandler resourceHandler = new WebHtmlResourceHandler(request.getContextPath() + "/image/servlet?image={0}");
			htmlExporterOutput.setImageHandler(resourceHandler);
			exporter.setExporterOutput(htmlExporterOutput);
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print);
			
		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exporter;
	}
	

}
