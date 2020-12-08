package com.example.weather.config;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.example.weather.error.UnauthorizedException;
import com.example.weather.error.WeatherError;

/**
 * This filter verify the access permissions for a user based on user name and
 * password provided in request
 */
@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "apiKey";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		Method method = resourceInfo.getResourceMethod();
		/** Allow All */
		if (!method.isAnnotationPresent(PermitAll.class)) {
			/** Deny All */
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(ACCESS_FORBIDDEN);
				throw new UnauthorizedException(new WeatherError("No Request being accepted now.", Status.UNAUTHORIZED.toString()));
			}

			/** Get Authorization header */
			MultivaluedMap<String, String> authorization = requestContext.getUriInfo().getQueryParameters();
			List<String> value = authorization.get(AUTHORIZATION_PROPERTY);

			/** Filtering based on Auth */
			if (value == null || value.isEmpty()) {
				requestContext.abortWith(ACCESS_DENIED);
				throw new UnauthorizedException(new WeatherError("Missing API key.", Status.UNAUTHORIZED.toString()));
			}

			/** Filtering from LinkedList */
			final String encodedUserPassword = value.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			if (!("YWRtaW46cGFzc3dvcmQ=".equalsIgnoreCase(encodedUserPassword))) {
				requestContext.abortWith(ACCESS_DENIED);
				throw new UnauthorizedException(new WeatherError("Invalid API key.", Status.UNAUTHORIZED.toString()));
			}

			/** Verifying RolesAllowed */
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

				if (!rolesSet.contains("INTERNAL_USER")) {
					requestContext.abortWith(ACCESS_DENIED);
					throw new UnauthorizedException(new WeatherError("Roles not allowed.", Status.UNAUTHORIZED.toString()));
				}

			}
		}
	}
}