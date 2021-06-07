package com.prime.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.prime.manager.dto.PrimeWorkerDetailsDTO;
import com.prime.manager.dto.RangeDTO;

@RestController
@RequestMapping(value = "/primemanager")
public class PrimeManagerController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/getrange", method= RequestMethod.GET)
	public RangeDTO getRange(@RequestParam int workerID){
		RangeDTO range = getRangeSynchronized(workerID);
		return range;		
	}
	synchronized private RangeDTO getRangeSynchronized(int workerID) {
		RangeDTO range = new RangeDTO();
		PrimeWorkerDetailsDTO primeWorkerDetailsTemp = new PrimeWorkerDetailsDTO();
		String url = "http://localhost:8084/repository/primeworker";
        
		ResponseEntity<PrimeWorkerDetailsDTO[]> responseEntity = restTemplate.getForEntity(url,PrimeWorkerDetailsDTO[].class);
		PrimeWorkerDetailsDTO[] primeWorkerDetailsArray  = responseEntity.getBody();
		
		int endIndex = 0;
		if(primeWorkerDetailsArray != null) {
			int len = primeWorkerDetailsArray.length;
			if(len>0) {
			primeWorkerDetailsTemp = primeWorkerDetailsArray[len-1];
			endIndex= primeWorkerDetailsTemp.getEndIndex();
			}
		}
		if(endIndex>=1000000) {
			range.setEndIndex(0);
			range.setStartIndex(0);
			return range;
		}
		range.setStartIndex(endIndex+1);
		range.setEndIndex(endIndex+1000);
		
		PrimeWorkerDetailsDTO primeWorkerDetails = new PrimeWorkerDetailsDTO();
        primeWorkerDetails.setStartIndex(endIndex+1);
        primeWorkerDetails.setEndIndex(endIndex+1000);
        primeWorkerDetails.setWorkerID(workerID);
        primeWorkerDetails.setStatus("Allotted");
        
        //Call Repository AddWorkerdetailsAPI to create worker details.
        String url2 = "http://localhost:8084/repository/primeworker";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PrimeWorkerDetailsDTO> httpEntity2 = new HttpEntity<>(primeWorkerDetails, headers);
        ResponseEntity<Integer> repositoryResponse = restTemplate.postForEntity(url2, httpEntity2, Integer.class);
        int id = repositoryResponse.getBody();
		range.setId(id);
		return range;		
	}
}
