package org.dontpanic.jackson_stream.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of objects to be mapped to JSON
 */
public class SuperModel {

    private List<Model> models = new ArrayList<>();

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public void addModel(Model model) {
        models.add(model);
    }
}
