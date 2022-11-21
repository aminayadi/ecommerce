package com.ecommerce.product.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import feign.Headers;
import com.ecommerce.product.service.dto.ClientDTO;







//@FeignClient(name = "Carsdb",url = "http://localhost:8081")

@FeignClient(name = "127.0.0.1:8083")

@Headers({

    "Accept: application/json",

    "Content-Type: application/json"

})





public interface ClientFeignClient {





	@GetMapping("/api/clients")
    public List<ClientDTO> getAllClients();
	
	@GetMapping("/api/clients/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable("id") String id);
	    

}