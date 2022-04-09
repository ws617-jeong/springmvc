package com.test.tasks;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
<pre>
1. get 말고는 405 리턴
2. get에서 requestParameter값은 full or short 2가지 외에는 400
3. short : 200 -> {status : ok}
4. full : 200 -> {currentTime : ISO_DATETIME, status: ok}
5. all response are contain content type header value : application/json
</pre>
 *
 */

@RestController
public class HealthcheckController {
	
	enum ParameterName {
		FORMAT;
		
		static Set<String> validNames() {
			return Arrays.stream(values()).map(e -> e.name()).map(String::toLowerCase).collect(Collectors.toSet());
		}
	}
	
	enum ParameterValue {
		FULL, SHORT;
		
		static Set<String> validValues() {
			return Arrays.stream(values()).map(e -> e.name()).map(String::toLowerCase).collect(Collectors.toSet());
		}
	}
	
	@Autowired
	private ObjectMapper mapper;
	
    @GetMapping(value = "/healthcheck")
    public String healthcheck(@RequestParam("format") String format ) {
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put("status", "OK");
    	
    	if(format.equals(ParameterValue.FULL.name().toLowerCase())) {
    		String formatTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    		jsonMap.put("currentTime", formatTime);
    	}
    	
    	String responseBody = null;
    	try {
    		responseBody = mapper.writeValueAsString(jsonMap);
    	} catch (JsonProcessingException e) {
    		e.printStackTrace();
    	}
    	
        return responseBody;
    }

    @PutMapping(value = "/healthcheck")
    public String healthcheckPut() {
        return null;
    }

    @PostMapping(value = "/healthcheck")
    public String healthcheckPost() {
        return null;
    }


    @DeleteMapping(value = "/healthcheck")
    public String healthcheckDelete() {
    	return null;
    }

}


