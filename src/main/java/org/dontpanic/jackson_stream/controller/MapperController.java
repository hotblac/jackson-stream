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

    // Maximum number of models before OutOfMemoryError when I run with -Xmx32M
    private static final int VALUE_AS_STRING_MODEL_COUNT = 2 * 1024;
    private static final int VALUE_AS_OUTPUT_STREAM_MODEL_COUNT = 16 * 1024;
    private static final int VALUES_ARRAY_AS_OUTPUT_STREAM_MODEL_COUNT = 128 * 1024; // The highest I've tried. We can possibly do more

    private static final int DATA_LENGTH = 1024;

    private final ObjectMapper objectMapper;

    public MapperController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/valueAsString")
    public String valueAsString() throws IOException {
        SuperModel largeModel = buildLargeModel(VALUE_AS_STRING_MODEL_COUNT);
        return objectMapper.writeValueAsString(largeModel);
    }

    @GetMapping("/valueAsOutputStream")
    public void valueAsOutputStream(HttpServletResponse response) throws IOException {
        SuperModel largeModel = buildLargeModel(VALUE_AS_OUTPUT_STREAM_MODEL_COUNT);
        objectMapper.writeValue(response.getOutputStream(), largeModel);
    }

    @GetMapping("/valuesArrayAsOutputStream")
    public void valuesArrayAsOutputStream(HttpServletResponse response) throws IOException {

        // Create a SequenceWriter to write Models one by one.
        try (SequenceWriter sequenceWriter = objectMapper.writer().writeValues(response.getOutputStream())) {
            sequenceWriter.init(true);

            for (int i = 0; i < VALUES_ARRAY_AS_OUTPUT_STREAM_MODEL_COUNT; i++) {
                Model model = buildModel(i);
                sequenceWriter.write(model);
            }
        }

    }

    private SuperModel buildLargeModel(int count) {
        SuperModel superModel = new SuperModel();
        for (int i=0; i<count; i++) {
            Model model = buildModel(i);
            superModel.addModel(model);
        }
        return superModel;
    }

    private Model buildModel(int i) {
        Model model = new Model();
        model.setId(i);
        model.setName("Model " + i);
        model.setData(RandomStringUtils.randomAlphanumeric(DATA_LENGTH));
        return model;
    }
}
