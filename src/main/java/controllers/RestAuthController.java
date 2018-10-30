package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Faculty;
import entity.University;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class RestAuthController {

    private final ObjectMapper objectMapper;

    @GetMapping(value = "/universities", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getUniversities() throws JsonProcessingException {
        Faculty faculty = new Faculty();
        faculty.setId(2L);
        faculty.setName("ФКТИ");

        University university = new University();
        university.setId(1L);
        university.setName("Универ1");
        university.setFaculties(Collections.singletonList(faculty));
        return objectMapper.writeValueAsString(Collections.singleton(university));
    }

}
