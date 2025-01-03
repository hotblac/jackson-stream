package org.dontpanic.jackson_stream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.dontpanic.jackson_stream.model.Model;
import org.apache.commons.lang3.RandomStringUtils;
import org.dontpanic.jackson_stream.model.SuperModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MapperController {

    private final int MODEL_COUNT = 16 * 1024;
    private final int DATA_LENGTH = 1024;

    private final ObjectMapper objectMapper;

    public MapperController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/valueAsString")
    public String valueAsString() throws IOException {
        SuperModel largeModel = buildLargeModel();
        return objectMapper.writeValueAsString(largeModel);
    }

    @GetMapping("/valueAsOutputStream")
    public void valueAsOutputStream(HttpServletResponse response) throws IOException {
        SuperModel largeModel = buildLargeModel();
        objectMapper.writeValue(response.getOutputStream(), largeModel);

    }

    private SuperModel buildLargeModel() {
        SuperModel superModel = new SuperModel();
        for (int i=0; i<MODEL_COUNT; i++) {
            Model model = new Model();
            model.setId(1L);
            model.setName("Model 1");
            model.setData(RandomStringUtils.randomAlphanumeric(DATA_LENGTH));
            superModel.addModel(model);
        }
        return superModel;
    }
}
