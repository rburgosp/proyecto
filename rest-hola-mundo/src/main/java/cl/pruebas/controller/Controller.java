package cl.pruebas.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(path="/servicio/v1")
public class Controller {
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@Autowired ObjectMapper mapper;
	
	private static final String HEADER_NAME="detalle_ejecucion";
	private static final String HEADER_VALUE="exitosa";
	private static final String MSJ_ERROR="Error al obtener detalle cotizacion ";

	
	@GetMapping(path="/server",produces=MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<String> server() throws JsonProcessingException {
	
		String jsonOutput = null;
		HttpStatus statusResponse=HttpStatus.OK;
		String headerName=HEADER_NAME;
		String headerValue=HEADER_VALUE;
	
		try {			
			
			List<String> nombre=new ArrayList<String>();
			nombre.add("Servicio Iniciado OK...");
			//Pretty Print JSON
			jsonOutput = mapper.writeValueAsString(nombre);

		}catch(Exception e) {
			statusResponse= HttpStatus.INTERNAL_SERVER_ERROR;
			headerValue=e.getMessage();
			ApiError appiError=new ApiError();
			appiError.setStatus(statusResponse);
			appiError.setMessage(e.getMessage());	
			//Pretty Print JSON
			jsonOutput = mapper.writeValueAsString(appiError);

			logger.error(MSJ_ERROR +e);
			for(StackTraceElement st:e.getStackTrace()) {
				logger.error(MSJ_ERROR +st);
			}

		}

		return ResponseEntity.status(statusResponse).header(headerName,headerValue).body(jsonOutput); 
		
	}
	

}
