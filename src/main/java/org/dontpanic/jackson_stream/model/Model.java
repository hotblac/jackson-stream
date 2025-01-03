package org.dontpanic.jackson_stream.model;

/**
 * Object to be mapped to JSON
 */
public class Model {
    private long id;
    private String name;
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
