package ir.beheshti.bpms;

import ir.beheshti.bpms.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BpmsApplication {
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(BpmsApplication.class, args);
    }
}
