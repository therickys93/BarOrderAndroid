package it.therickys93.javabarorderapi;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BarOrder {

	private static final String APPLICATION_JSON = "application/json; charset=utf-8";
	String server;
	OkHttpClient client;
	private String token;

	public BarOrder(String server, String token){
		this.server = server;
		this.client = new OkHttpClient();
		this.token = token;
	}
	
	public String execute(Sendable request) throws IOException {
		if(request.method() == "GET"){
			return this.getMethod(request);
		} else if(request.method() == "POST") {
			return this.postMethod(request);
		}
		return null;
	}
	
	public String getMethod(Sendable request) throws IOException {
		Request requestAdd = new Request.Builder().url(this.server + request.endpoint()).addHeader("Authorization", this.token).build();
		Response response = client.newCall(requestAdd).execute();
		return response.body().string();
	}
	
	public String postMethod(Sendable request) throws IOException {
		final MediaType JSON = MediaType.parse(APPLICATION_JSON);
		RequestBody body = null;
		if(request.toJson() == null){
			body = RequestBody.create(JSON, "");
		} else {
			body = RequestBody.create(JSON, request.toJson().toString());
		}
		Request requestAdd = new Request.Builder().url(this.server + request.endpoint()).addHeader("Authorization", this.token).post(body).build();
		Response response = client.newCall(requestAdd).execute();
		return response.body().string();
	}
	
}
