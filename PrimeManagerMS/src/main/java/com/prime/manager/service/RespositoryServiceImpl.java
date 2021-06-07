package com.prime.manager.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.prime.manager.dto.NumbersDTO;

@Service
public class RespositoryServiceImpl implements RepositoryService {

	@Autowired
	private RestTemplate restTemplate;
	Set<Integer> hs = new HashSet<>();
	@Override
	public void addPrimeNumber(int num) {
		
		String url = "http://localhost:8084/repository/primenumber/addnumbers";
        NumbersDTO numbers= new NumbersDTO();
        List<Integer> al = new ArrayList<Integer>();
        al.add(num);
        numbers.setPrimeNumbers(al);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NumbersDTO> httpEntity = new HttpEntity<>(numbers, headers);

        restTemplate.postForEntity(url, httpEntity, null);
	}

}
