package com.prime.repository.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.prime.repository.dto.PrimeWorkerDetailsDTO;

@Service
public class RespositoryServiceImpl implements RepositoryService {

	Set<Integer> hs = new HashSet<>();
	List<PrimeWorkerDetailsDTO> workerDetailsList = new ArrayList<PrimeWorkerDetailsDTO>();
	
	@Override
	public void addPrimeNumber(int num) {
		hs.add(num);
	}
	
	public boolean existPrimeNumber(int num) {
		return hs.contains(num);
	}
	
	public int addPrimeWorkerDetails(PrimeWorkerDetailsDTO primeWorkerDetailsDTO) {
		int id=workerDetailsList.size() + 1;
		primeWorkerDetailsDTO.setId(id);
		workerDetailsList.add(primeWorkerDetailsDTO);
		return id;
	}
	
	public void updatePrimeWorkerDetails(PrimeWorkerDetailsDTO primeWorkerDetailsDTO) {
		workerDetailsList.set(primeWorkerDetailsDTO.getId()-1,primeWorkerDetailsDTO);
	}
	
	public List<PrimeWorkerDetailsDTO> getPrimeWorkerDetails(){
		return workerDetailsList;
	}

}
