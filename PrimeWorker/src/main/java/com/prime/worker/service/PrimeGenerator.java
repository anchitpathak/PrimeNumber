package com.prime.worker.service;

import java.util.List;

public interface PrimeGenerator {
	
	List<Integer> generatePrimeNumbers(int startIndex, int endIndex);
}
