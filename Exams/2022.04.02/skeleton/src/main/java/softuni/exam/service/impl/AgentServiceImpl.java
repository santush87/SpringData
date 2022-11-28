package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.AgentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of("D:\\SoftUni\\SpringData\\Exams\\2022.04.02\\skeleton\\src\\main\\resources\\files\\json\\agents.json"));
    }

    @Override
    public String importAgents() throws IOException {
        return null;
    }
}
