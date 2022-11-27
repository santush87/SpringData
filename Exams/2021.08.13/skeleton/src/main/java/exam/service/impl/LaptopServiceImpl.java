package exam.service.impl;

import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/laptops.json"));
    }

    @Override
    public String importLaptops() throws IOException {
        return null;
    }

    @Override
    public String exportBestLaptops() {
        return null;
    }
}
