package converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

@RequiredArgsConstructor
public class TestConverter implements Converter<String, Test> {

    private final ObjectMapper objectMapper;

    @Override
    public Test convert(String text) {
        try {
            return objectMapper.readValue(text, Test.class);
        } catch (IOException e) {
            return null;
        }
    }

}
