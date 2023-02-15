package com.ecommerce.photomanager.web.rest;

import com.ecommerce.photomanager.domain.FileInfo;
import com.ecommerce.photomanager.message.ResponseMessage;
import com.ecommerce.photomanager.service.FileService;
import com.ecommerce.photomanager.service.FilesStorageService;
import com.ecommerce.photomanager.service.FolderService;
import com.ecommerce.photomanager.service.dto.FileDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("/api/fmanager")
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    @Autowired
    FileService fileService;

    private final Logger log = LoggerFactory.getLogger(FolderService.class);

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays
                .asList(files)
                .stream()
                .forEach(file -> {
                    storageService.save(file);
                    fileNames.add(file.getOriginalFilename());
                });

            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService
            .loadAll()
            .map(path -> {
                String filename = path.getFileName().toString();
                String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString())
                    .build()
                    .toString();

                return new FileInfo(filename, url);
            })
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/filesbyidproduct/{idproduct}")
    public ResponseEntity<List<FileInfo>> getListFilesByIdProduct(@PathVariable String idproduct) {
        //Extract list of files
        List<FileDTO> faip = fileService.findAllByIdProduct(idproduct);

        List<FileInfo> fileInfos = storageService
            .loadAll()
            .map(path -> {
                String filename = path.getFileName().toString();
                String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString())
                    .build()
                    .toString();

                return new FileInfo(filename, url);
            })
            .collect(Collectors.toList());

        for (int j = 0; j < fileInfos.size(); j++) {
            int i;
            for (i = 0; i < faip.size(); i++) {
                if (faip.get(i).getName().contains(fileInfos.get(j).getName())) break;
                log.debug("Request to save Folder : {} ----------", fileInfos.get(j).getName());
                log.debug("Request to save Folder : {} +++++++++++", faip.get(i));
            }
            if (i < faip.size()) fileInfos.remove(i);
        }
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
    }
}
