package com.prime.repository.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prime.repository.dto.NumbersDTO;
import com.prime.repository.dto.PrimeWorkerDetailsDTO;
import com.prime.repository.service.RepositoryService;

@RestController
@RequestMapping(value = "/repository")
public class PrimeRepositoryController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping(value="/primenumber/exist", method= RequestMethod.GET)
	public String findNumber(@RequestParam int n){
		return (repositoryService.existPrimeNumber(n)? "YES": "NO");
	}	
	@RequestMapping(value = "/primenumber/addnumbers", method= RequestMethod.POST)
	public void addPrimeNumber(@RequestBody NumbersDTO numbers){
		for(int i: numbers.getPrimeNumbers())
			repositoryService.addPrimeNumber(i);
	}
	@RequestMapping(value = "/primeworker", method= RequestMethod.POST)
	public int postWorkerDetails(@RequestBody PrimeWorkerDetailsDTO primeWorkerDetailsDTO){
			return repositoryService.addPrimeWorkerDetails(primeWorkerDetailsDTO);
			
	}
	@RequestMapping(value = "/primeworker", method= RequestMethod.PUT)
	public void putWorkerDetails(@RequestBody PrimeWorkerDetailsDTO primeWorkerDetailsDTO){	
		repositoryService.updatePrimeWorkerDetails(primeWorkerDetailsDTO);
	}
	@RequestMapping(value = "/primeworker", method= RequestMethod.GET)
	public List<PrimeWorkerDetailsDTO> getWorkerDetails(){
			return repositoryService.getPrimeWorkerDetails();
	}
	//test_Anchit
}
