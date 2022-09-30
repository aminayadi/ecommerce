package com.ecommerce.favoris.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import feign.Headers;

import com.ecommerce.favoris.service.dto.ProductDTO;
import com.ecommerce.favoris.service.dto.ClientDTO;







//@FeignClient(name = "Carsdb",url = "http://localhost:8081")

@FeignClient(name = "127.0.0.1:8083")

@Headers({

    "Accept: application/json",

    "Content-Type: application/json"

})





public interface ProductFeignClient {





	@GetMapping("/products")
    public List<ProductDTO> getAllProducts() ; 
	
	 @GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable String id) ;
	    

}
