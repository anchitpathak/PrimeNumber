package com.prime.worker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.prime.worker.dto.NumbersDTO;
import com.prime.worker.dto.PrimeWorkerDetailsDTO;
import com.prime.worker.dto.RangeDTO;

@Service
@Component
public class PrimeWorkerJobImpl implements PrimeWorkerJob {
	
	@Autowired
	PrimeGenerator primeGenerator;	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public void initializeWorker(int workerID) {
		try{
			do {	
			RangeDTO range = getRange(workerID);
			if(range.getEndIndex()==0)
				return;
			generatePrimeInRange(range, workerID);
		}while(true);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	private void generatePrimeInRange(RangeDTO range, int workerID) {
		
		NumbersDTO numbers = new NumbersDTO();
		List<Integer> primeList = new ArrayList<>();
		int startIndex= range.getStartIndex();
		int endIndex= range.getEndIndex();
		
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
		PrimeWorkerDetailsDTO primeWorkerDetails = new PrimeWorkerDetailsDTO();
        primeWorkerDetails.setStartIndex(startIndex);
        primeWorkerDetails.setEndIndex(endIndex);
        primeWorkerDetails.setWorkerID(workerID);
        primeWorkerDetails.setStatus("InProgress");
        primeWorkerDetails.setId(range.getId());
               
        String url2 = "http://localhost:8084/repository/primeworker";
        HttpEntity<PrimeWorkerDetailsDTO> httpEntity1 = new HttpEntity<>(primeWorkerDetails, headers);
        restTemplate.put(url2, httpEntity1);
        
        //Generating Prime Numbers
		primeList = primeGenerator.generatePrimeNumbers(startIndex, endIndex);		
		numbers.setPrimeNumbers(primeList);
		
		//Call Repository PostPrimeNumberAPI 
		String url1 = "http://localhost:8084/repository/primenumber/addnumbers";        	        
        HttpEntity<NumbersDTO> httpEntity2 = new HttpEntity<>(numbers, headers);
        restTemplate.postForEntity(url1, httpEntity2, null);
        
        //Call to Update worker details in repository.
        primeWorkerDetails.setStatus("COMPLETED");       
        String url3 = "http://localhost:8084/repository/primeworker";
        HttpEntity<PrimeWorkerDetailsDTO> httpEntity3 = new HttpEntity<>(primeWorkerDetails, headers);
        restTemplate.put(url3, httpEntity3);
		
	}
	private RangeDTO getRange(int workerID) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
		
		//Call Manager GetRangeAPI to set startIndex and endIndex.
		String url = "http://localhost:8085/primemanager/getrange?workerID=" + workerID;     
		ResponseEntity<RangeDTO> responseEntity = restTemplate.getForEntity(url,RangeDTO.class);
		RangeDTO range  = responseEntity.getBody();
		return range;
	}
	
	public String isNumberAvailable(int n) {
		String url = "http://localhost:8084/repository/primenumber/exist?n=" + n;
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,String.class);
		return responseEntity.getBody();
	}
}
