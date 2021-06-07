package com.prime.repository.service;

import java.util.List;

import com.prime.repository.dto.PrimeWorkerDetailsDTO;

public interface RepositoryService {

	void addPrimeNumber(int num);
	boolean existPrimeNumber(int num);
	int addPrimeWorkerDetails(PrimeWorkerDetailsDTO primeWorkerDetailsDTO);
	void updatePrimeWorkerDetails(PrimeWorkerDetailsDTO primeWorkerDetailsDTO);
	List<PrimeWorkerDetailsDTO> getPrimeWorkerDetails();
}
