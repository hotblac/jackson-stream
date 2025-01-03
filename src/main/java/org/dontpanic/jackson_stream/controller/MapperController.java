package org.dontpanic.jackson_stream.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dontpanic.jackson_stream.model.Model;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapperController {

    private final ObjectMapper objectMapper;

    public MapperController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/valueAsString")
    public String valueAsString() throws JsonProcessingException {
        Model model = new Model();
        model.setId(1L);
        model.setName("Model 1");
        model.setData(RandomStringUtils.randomAlphanumeric(128));

        return objectMapper.writeValueAsString(model);
    }
}
