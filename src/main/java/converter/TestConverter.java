package converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

@RequiredArgsConstructor
public class TestConverter implements Converter<String, TestDto> {

    private final ObjectMapper objectMapper;

    @Override
    public TestDto convert(String text) {
        try {
            return objectMapper.readValue(text, TestDto.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
