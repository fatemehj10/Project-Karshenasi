package ir.beheshti.bpms.controller;

import ir.beheshti.bpms.model.diagrams.DirectoryFlowGram;

import ir.beheshti.bpms.service.EventLogFileService;
import ir.beheshti.bpms.service.custom_exception.StorageFileNotFoundException;
import ir.beheshti.bpms.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
public class FileController {
    private final EventLogFileService eventLogFileService;
    private final StorageService storageService;
    private int index = 0;

    @Autowired
    public FileController(StorageService storageService, EventLogFileService eventLogFileService) {
        this.storageService = storageService;
        this.eventLogFileService = eventLogFileService;
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/get-log", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity getEventLog(@RequestParam("file") MultipartFile file) throws IOException {
        storageService.store(file, index);
        //read csv file
        String[] fileName = file.getOriginalFilename().split("[.]");
        Path path = storageService.load(fileName[0] + index++ + "." + fileName[1]);
        eventLogFileService.read(path.toString());
        ///////////////
        return ResponseEntity.ok().build();
    }

    @GetMapping("/show-log")
    public DirectoryFlowGram getFlowChart() {
        eventLogFileService.solveDirectoryFlowGram();
        return eventLogFileService.getDirectoryFlowGram();
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
