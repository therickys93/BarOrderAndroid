package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class GetOrder implements Sendable {

    private String endpoint;
    private String method;

    public GetOrder(int id){
        this.method = "GET";
        this.endpoint = "/v1/order/" + id;
    }

    @Override
    public String endpoint() {
        return this.endpoint;
    }

    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public String method() {
        return this.method;
    }
}
