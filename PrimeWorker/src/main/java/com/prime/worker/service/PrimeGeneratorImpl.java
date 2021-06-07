package com.prime.worker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class PrimeGeneratorImpl implements PrimeGenerator {
	
	@Override
	public List<Integer> generatePrimeNumbers(int startIndex, int endIndex) {
		List<Integer> primeList = new ArrayList<>();
		for(int i= startIndex;i<=endIndex;i++) {
			if(isPrime(i))
				primeList.add(i);			
		}
		return primeList;
	}
	
	private boolean isPrime(int n) {
		if(n<=1) {
			return false;
		}
		if(n==2||n==3) {
			return true;
		}
		for(int i=2;i<=n/2;i++) {
			if(n%i==0)
				return false;
		}
		return true;
	}
}
