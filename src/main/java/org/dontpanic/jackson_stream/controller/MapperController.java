package org.dontpanic.jackson_stream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
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

    @GetMapping("/valuesArrayAsOutputStream")
    public void valuesArrayAsOutputStream(HttpServletResponse response) throws IOException {

        // Create a SequenceWriter to write Models one by one.
        try (SequenceWriter sequenceWriter = objectMapper.writer().writeValues(response.getOutputStream())) {
            sequenceWriter.init(true);

            for (int i = 0; i < MODEL_COUNT; i++) {
                Model model = new Model();
                model.setId(i);
                model.setName("Model " + i);
                model.setData(RandomStringUtils.randomAlphanumeric(DATA_LENGTH));
                sequenceWriter.write(model);
            }
        }

    }

    private SuperModel buildLargeModel() {
        SuperModel superModel = new SuperModel();
        for (int i=0; i<MODEL_COUNT; i++) {
            Model model = new Model();
            model.setId(i);
            model.setName("Model " + i);
            model.setData(RandomStringUtils.randomAlphanumeric(DATA_LENGTH));
            superModel.addModel(model);
        }
        return superModel;
    }
}
