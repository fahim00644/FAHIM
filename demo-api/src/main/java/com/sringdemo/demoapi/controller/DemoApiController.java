package com.sringdemo.demoapi.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class DemoApiController {

	@GetMapping("/getCheck")
	public ResponseEntity<Map<String,String>> getCheck() {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("status", "OK");
		return new ResponseEntity<Map<String,String>>(resultMap, HttpStatus.OK);
	}
	
	@GetMapping("/getCheck/{name}")
	public ResponseEntity<Map<String,String>> getCheck(@PathVariable String name) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("status", "OK");
		resultMap.put("name", name);
		resultMap.put("Current Time", String.valueOf(LocalDateTime.now()));
		return new ResponseEntity<Map<String,String>>(resultMap, HttpStatus.OK);
	}
	
	@GetMapping("/getParamCheck")
	public ResponseEntity<Map<String,String>> getParamCheck(@RequestParam(name = "name") String urlParam) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("status", "OK");
		resultMap.put("name", urlParam);
		resultMap.put("Current Time", String.valueOf(LocalDateTime.now()));
		return new ResponseEntity<Map<String,String>>(resultMap, HttpStatus.OK);
	}
	
	@GetMapping("/getDataFromApi")
	public ResponseEntity<Map<String,String>> getDataFromExtApi() {
		RestTemplate template = new RestTemplate();
		String data = template.getForObject(URI.create("http://localhost:8000/api/v1/getData"), String.class);
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("status", "OK");
		resultMap.put("name", data);
		resultMap.put("Current Time", String.valueOf(LocalDateTime.now()));
		return new ResponseEntity<Map<String,String>>(resultMap, HttpStatus.OK);
	}
	@GetMapping("/getDataFromApiWithErrorCheck/{name}")
	public ResponseEntity<Map<String,String>> getDataFromExtApiErrCheck(@PathVariable("name") String name) {
		RestTemplate template = new RestTemplate();
		Map<String,String> urlParam = new HashMap<String, String>();
		Map<String,String> resultMap = new HashMap<String,String>();
		urlParam.put("name", name);
		ResponseEntity<Object> data = template.getForEntity("http://localhost:8000/api/v1/checkError?name={name}",
				Object.class,urlParam);
		resultMap.put("status", "OK");
		resultMap.put("name", String.valueOf(data.getBody()));
		resultMap.put("Current Time", String.valueOf(LocalDateTime.now()));
		return new ResponseEntity<Map<String,String>>(resultMap, HttpStatus.OK);
	}
}
