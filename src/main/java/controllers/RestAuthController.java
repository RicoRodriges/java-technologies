package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UniversityDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestAuthController {

    private final ObjectMapper objectMapper;
    private final UniversityDAO universityDAO;

    @GetMapping(value = "/universities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getUniversities() throws JsonProcessingException {
        return objectMapper.writeValueAsString(universityDAO.findAll());
    }

}
