package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;

    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of("D:\\SoftUni\\SpringData\\Exams\\2022.04.02\\skeleton\\src\\main\\resources\\files\\json\\towns.json"));
    }

    @Override
    public String importTowns() throws IOException {
        return null;
    }
}
