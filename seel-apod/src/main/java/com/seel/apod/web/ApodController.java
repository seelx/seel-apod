package com.seel.apod.web;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seel.apod.service.ApodService;
import com.seel.apod.web.entity.SeelApod;
import com.seel.apod.web.nasa.entity.Apod;

//TODO: build Aspect to measure performance

@RestController
@RequestMapping("/seel")
public class ApodController {
	public static Logger logger = Logger.getLogger(ApodController.class);

	@Autowired ApodService service;

    @RequestMapping(value = "/apod", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeelApod> testId(@RequestParam(value="date") String date) {
    	//TODO: default date = today
    	//TODO: implement @Constraint @Valid for ApodDate to check for valid date format (replace simple exception below)
    	//TODO: build proper exception handler-- throw new MethodArgumentNotValidException();
    	//				(then I can do away with the ResponseEntity and the rest of the messy code here)
    	//Note that the service could have returned a SeelApod instead of a Pair
    	//	done this way to demonstrate separating the web entity from the service entitities
    	//	changes in the service won't affect the API (unless that is desired)
    	ResponseEntity<SeelApod> responseEntity = null;
    	if (service.isValidDate(date)) {
    		Pair<String,Apod> pair = service.download(date);
    		if (pair != null) {
				SeelApod seelApod = new SeelApod(pair);
    			responseEntity = ResponseEntity.ok().body(seelApod);
    		}
    		else
    			{
    				SeelApod seelApod = new SeelApod();
					seelApod.setMessage("An internal error has occurred.");
    				responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(seelApod);
    			}
    	}
    	else {
			SeelApod seelApod = new SeelApod();
			seelApod.setMessage("The date request paremeter is not a valid format.");
    		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(seelApod);
    	}

		return responseEntity;
    }

    //TODO: build rest service to except file with list of dates


}
