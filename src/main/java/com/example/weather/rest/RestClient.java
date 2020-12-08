package com.example.weather.rest;

import static net.logstash.logback.argument.StructuredArguments.kv;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.weather.error.ErrorService;

@Named
public class RestClient {

	@Inject
	ErrorService errorService;

	private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
	private static final String CONNECTION_ERROR = "java.net.ConnectException: Connection refused: connect";
	private static final String TIMEOUT_ERROR = "java.net.SocketTimeoutException:";
	private static final String PROCESSING_EXCEPTION = "Processing Exception";

	public Response getCall(String url) {

		logger.info("Fetching Data from endpoint: " + url);
		Response response = null;

		try {

			Builder builder = getBuilder(url);

			if (builder != null) {
				response = builder.get();
				logger.info("Fetching Data from endpoint: " + url, kv("GETEndpointStatus: ", response.getStatus()));
			}

			return response;
		} catch (ProcessingException processingException) {
			return processingExceptionResponse(processingException);
		} catch (Exception exception) {
			return errorResposne(exception);
		}
	}

	protected Response processingExceptionResponse(ProcessingException processingException) {
		if (CONNECTION_ERROR.equalsIgnoreCase(processingException.getMessage())) {

			logger.error(PROCESSING_EXCEPTION, kv("Endpoint is unreachable", processingException.getMessage()));
			errorService.addError("Endpoint is unreachable", Status.SERVICE_UNAVAILABLE.toString());
			return Response.status(Status.SERVICE_UNAVAILABLE).entity(processingException.getMessage()).build();
		} else if (TIMEOUT_ERROR.equalsIgnoreCase(processingException.getMessage())) {

			logger.error(PROCESSING_EXCEPTION,
					kv("Time out while processing request", processingException.getMessage()));
			errorService.addError("Time out while processing request", Status.GATEWAY_TIMEOUT.toString());
			return Response.status(Status.GATEWAY_TIMEOUT).entity(processingException.getMessage()).build();
		} else {

			logger.error(PROCESSING_EXCEPTION,
					kv("Error while processing the request", processingException.getMessage()));
			errorService.addError("Error while processing the request", Status.INTERNAL_SERVER_ERROR.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(processingException.getMessage()).build();
		}
	}

	protected Response errorResposne(Exception exception) {

		logger.error("Error while fetching data from the endpoint");
		errorService.addError("Error while fetching data from the endpoint", Status.INTERNAL_SERVER_ERROR.toString());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
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
