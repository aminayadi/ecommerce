package com.ecommerce.category.client;


import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.ecommerce.category.service.dto.ProductDTO;

import feign.Headers;

@FeignClient(name = "127.0.0.1:8085")
@Headers({
    "Accept: application/json",
    "Content-Type: application/json"
})

public interface ProductFeignClient {

    @GetMapping("api/productscategory/{idcategory}")
    public List<ProductDTO> getAllProductsByIdCategory(@PathVariable("idcategory") String idcategory);
	
}
