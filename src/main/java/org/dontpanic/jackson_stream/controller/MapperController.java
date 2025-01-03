package org.dontpanic.jackson_stream.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dontpanic.jackson_stream.model.Model;
import org.apache.commons.lang3.RandomStringUtils;
import org.dontpanic.jackson_stream.model.SuperModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapperController {

    private final int MODEL_COUNT = 16 * 1024;
    private final int DATA_LENGTH = 1024;

    private final ObjectMapper objectMapper;

    public MapperController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/valueAsString")
    public String valueAsString() throws JsonProcessingException {
        SuperModel superModel = new SuperModel();
        for (int i=0; i<MODEL_COUNT; i++) {
            Model model = new Model();
            model.setId(1L);
            model.setName("Model 1");
            model.setData(RandomStringUtils.randomAlphanumeric(DATA_LENGTH));
            superModel.addModel(model);
        }

        return objectMapper.writeValueAsString(superModel);
    }
}
