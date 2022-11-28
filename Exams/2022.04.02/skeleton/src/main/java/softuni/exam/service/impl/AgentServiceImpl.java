package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImortDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper mapper;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, ValidationUtils validationUtils, Gson gson, ModelMapper mapper) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.mapper = mapper;
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
        StringBuilder builder = new StringBuilder();

        final List<AgentImortDTO> imortDTOS =
                Arrays.stream(this.gson.fromJson(readAgentsFromFile(), AgentImortDTO[].class)).toList();

        for (AgentImortDTO dto : imortDTOS) {
            boolean isValid = this.validationUtils.isValid(dto);

            if (isValid) {
                Optional<Agent> optFirstName = this.agentRepository.findByFirstName(dto.getFirstName());

                if (optFirstName.isPresent()) {
                    builder.append("Invalid agent").append(System.lineSeparator());

                } else {
                    Agent agentToSave = this.mapper.map(dto, Agent.class);
                    Optional<Town> town = this.townRepository.findByTownName(dto.getTown());

                    agentToSave.setFirstName(dto.getFirstName());
                    agentToSave.setLastName(dto.getLastName());
                    agentToSave.setEmail(dto.getEmail());
                    agentToSave.setTown(town.get());

                    this.agentRepository.save(agentToSave);

                    builder.append(String.format("Successfully imported agent - %s %s",
                                    dto.getFirstName(),
                                    dto.getLastName()))
                            .append(System.lineSeparator());
                }
            } else {
                builder.append("Invalid agent").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
