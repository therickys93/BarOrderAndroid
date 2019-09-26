package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public interface Sendable {

	public String endpoint();
	public JsonObject toJson();
	public String method();
	
}
