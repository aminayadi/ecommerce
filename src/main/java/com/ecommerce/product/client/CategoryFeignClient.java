package com.ecommerce.product.client;
import java.util.Collection;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import feign.Headers;
import com.ecommerce.product.service.dto.CategoryDTO;







//@FeignClient(name = "Carsdb",url = "http://localhost:8081")

@FeignClient(name = "127.0.0.1:8081")

@Headers({

    "Accept: application/json",

    "Content-Type: application/json"

})





public interface CategoryFeignClient {





	 @GetMapping("api/categories")
	    public List<CategoryDTO> getAllCategories() ; 
	    
	 @GetMapping("api/categories/{id}")
	    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") String id);
	   
	    

}