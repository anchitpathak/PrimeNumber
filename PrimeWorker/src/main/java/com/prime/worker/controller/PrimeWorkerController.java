package com.prime.worker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.prime.worker.service.PrimeWorkerJob;

@RestController
@RequestMapping(value = "/primeworker")
public class PrimeWorkerController {
	
	@Autowired
	private PrimeWorkerJob primeWorker;
	
	@RequestMapping(value="/health", method= RequestMethod.GET)
	@ResponseBody
	public String healthCheck(){
		return "OK";
	}
	@RequestMapping(value="/init", method = RequestMethod.POST)
	public String initialize(@RequestParam int workerID) {
		primeWorker.initializeWorker(workerID);
		return "Active";
	}
	@RequestMapping(value="/exist", method= RequestMethod.GET)
	public String isExist(@RequestParam int n) {
		return primeWorker.isNumberAvailable(n);
		
	}
	
	
}
