package com.test.tasks;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.tasks.HealthcheckController.ParameterName;
import com.test.tasks.HealthcheckController.ParameterValue;

@Component
public class HealthcheckInterceptor implements HandlerInterceptor {

	private EnumSet<HttpMethod> allowedHttpMethods = EnumSet.of(HttpMethod.GET);
	
	@Autowired
	private ObjectMapper jsonMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// only get method
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Map<String, String> jsonMap = new HashMap<>();
		
		HttpMethod httpMethod = Optional.of(HttpMethod.resolve(request.getMethod())).orElse(HttpMethod.HEAD);
		if(!allowedHttpMethods.contains(httpMethod)) {
			jsonMap.put("status", "Method Not Allowed");
			String message = jsonMapper.writeValueAsString(jsonMap);
			response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value(), message);
			return false;
		}
		
		// get parameter name is allowed 'format'
		// get parameter value is allowed full or short
		Set<String> parameterNames = request.getParameterMap().keySet();
		boolean isInvalidParameterName = parameterNames.stream().anyMatch(name -> Objects.isNull(name) || name.isEmpty() || !ParameterName.validNames().contains(name));
		boolean isInValidParameterValue = ParameterName.validNames().stream()
		.map(request::getParameter)
		.anyMatch(value -> Objects.isNull(value) || value.isEmpty() || !ParameterValue.validValues().contains(value));
		
		if(isInvalidParameterName || isInValidParameterValue) {
			jsonMap.put("status", "Bad Request");
			String message = jsonMapper.writeValueAsString(jsonMap);
			response.sendError(HttpStatus.BAD_REQUEST.value(), message);
			return false;
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	}
}
