package com.everis.retomicrostarter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import retostarter.Temperatura;

@RestController
public class IndexController {
	
	private Counter counterMostrarTemperatura,counterConvertirTemperatura;

	@Autowired
	private Temperatura temperatura;
	
	@Value("${some.unidadGrados}")
	private String unidadGrados;
	
	public IndexController(MeterRegistry registry) {
		this.counterMostrarTemperatura = Counter.builder("invocaciones.mostrarTemperatura").description("Invocaciones totales mostrarTemperatura").register(registry);
		this.counterConvertirTemperatura = Counter.builder("invocaciones.convertirTemperatura").description("Invocaciones totales convertirTemperatura").register(registry);
	}
	
	@GetMapping("/mostrarTemperatura")
	public ResponseEntity<String> mostrarTemperatura(@RequestParam float temperaturaIntroducida){
		
		
		return new ResponseEntity<String>(HttpStatus.OK).ok(temperatura.mostrarTemperatura(temperaturaIntroducida,unidadGrados));
			
	}
	
	@GetMapping("/convertirCelsius")
	public ResponseEntity<String> convertirCelsius(@RequestParam float temperaturaCelsius){
		if(unidadGrados.equals("Celsius")) {
			return new ResponseEntity<String>(HttpStatus.OK).ok(temperatura.convertirCelsiusFaren(temperaturaCelsius));
		}else {
			return new ResponseEntity<String>(HttpStatus.OK).ok("No se pueden convertir los grados por que ya estan en Fahrenheit");
		}
		
	}
}
