package com.example.safraha.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.safraha.region.entity.Region;
import com.example.safraha.region.repository.RegionRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final RegionRepository regionRepository;

    public DataLoader(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public void run(String... args) {

        createIfNotExists("SOUTH_INDIA");
        createIfNotExists("NORTH_EAST");
        createIfNotExists("UAE");
        createIfNotExists("METRO");
    }

    private void createIfNotExists(String name) {
        regionRepository.findByName(name)
                .orElseGet(() -> regionRepository.save(new Region(null, name)));
    }
}
