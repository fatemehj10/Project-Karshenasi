package ir.beheshti.bpms.controller;

import ir.beheshti.bpms.model.DTO.DirectoryFlowGramDto;
import ir.beheshti.bpms.model.diagrams.DirectoryFlowGram;

import ir.beheshti.bpms.model.event_log.EventLogHeader;
import ir.beheshti.bpms.service.DirectoryFlowGramService;
import ir.beheshti.bpms.service.EventLogFileService;
import ir.beheshti.bpms.service.custom_exception.StorageFileNotFoundException;
import ir.beheshti.bpms.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {
    private final DirectoryFlowGramService directoryFlowGramService;
    private final EventLogFileService eventLogFileService;
    private final StorageService storageService;
    private int index = 0;

    @Autowired
    public FileController(DirectoryFlowGramService directoryFlowGramService, StorageService storageService, EventLogFileService eventLogFileService) {
        this.directoryFlowGramService = directoryFlowGramService;
        this.storageService = storageService;
        this.eventLogFileService = eventLogFileService;
    }

    @PostMapping(value = "/get-log", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity getEventLog(@RequestParam("file") MultipartFile file){
        storageService.store(file, index);
        //read csv file
        String[] fileName = file.getOriginalFilename().split("[.]");
        Path path = storageService.load(fileName[0] + index++ + "." + fileName[1]);
        eventLogFileService.setPath(path.toString());
        ///////////////
        return ResponseEntity.ok().build();
    }

    @GetMapping("/show-log")
    public DirectoryFlowGramDto getFlowChart() {
        eventLogFileService.solveDirectoryFlowGram();
        directoryFlowGramService.covertToDto(eventLogFileService.getNodesFreq(), eventLogFileService.getDirectoryFlowGram());
        return directoryFlowGramService.getDirectoryFlowGramDto();
    }

    @GetMapping("/choose-header")
    public EventLogHeader getHeaderNames() {
        return eventLogFileService.getHeadersName();
    }

    @PostMapping("/select-header")
    public ResponseEntity setHeaderNames(@RequestBody() String[] headers) {
        System.out.println(headers);
        eventLogFileService.read(headers);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
