package com.example.weather.rest;

import javax.inject.Named;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

@Named
public class RestClient {

	public Response getCall(String url) {

		Response response = null;

		try {

			Builder builder = getBuilder(url);

			if (builder != null) {
				response = builder.get();
			}

			return response;
		} catch (ProcessingException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	protected Builder getBuilder(String url) {
		WebTarget webTarget = ClientBuilder.newClient().target(url);

		return buildTarget(webTarget);
	}

	protected Builder buildTarget(WebTarget target) {

		return target.request(MediaType.APPLICATION_JSON).property(ClientProperties.CONNECT_TIMEOUT, "5000")
				.property(ClientProperties.READ_TIMEOUT, "5000");
	}

}
