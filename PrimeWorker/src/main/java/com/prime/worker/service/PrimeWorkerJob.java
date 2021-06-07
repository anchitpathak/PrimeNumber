package com.prime.worker.service;

public interface PrimeWorkerJob {
	
	void initializeWorker(int workerID);
	String isNumberAvailable(int n);
}
