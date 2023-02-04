package com.ecommerce.product.client;


import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Headers;
import com.ecommerce.product.service.dto.FileDTO;


@FeignClient(name = "127.0.0.1:8091")

@Headers({

	"Accept: application/json",

	"Content-Type: application/json"

})

public interface PhotoFeignClient {

	@PostMapping("/api/files")
	public ResponseEntity<FileDTO> createFile(@Valid @RequestBody FileDTO fileDTO) throws URISyntaxException ;

	@PutMapping("/api/files/{id}")
	public ResponseEntity<FileDTO> updateFile(
			@PathVariable(value = "id", required = false) final String id,
			@Valid @RequestBody FileDTO fileDTO
			) throws URISyntaxException;

    @PatchMapping(value = "/api/files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileDTO> partialUpdateFile(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody FileDTO fileDTO
    ) throws URISyntaxException;
    
    @GetMapping("/api/files")
    public List<FileDTO> getAllFiles();
    
    
   @GetMapping("/api/filesofproduct/{idproduct}")
   public List<FileDTO> getAllFilesOfProduct(@PathVariable("idproduct") String idproduct) ;    

   @GetMapping("/api/files/{id}")
   public ResponseEntity<FileDTO> getFile(@PathVariable("id") String id);
}

