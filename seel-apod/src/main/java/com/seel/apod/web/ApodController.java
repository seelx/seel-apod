package com.seel.apod.web;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seel.apod.service.ApodService;
import com.seel.apod.web.nasa.entity.Apod;

@RestController
@RequestMapping("/seel")
public class ApodController {
	public static Logger logger = Logger.getLogger(ApodController.class);

	@Autowired ApodService service;

    @RequestMapping(value = "/apod", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Pair<String,Apod> testId(@RequestParam(value="date") String date) {
    	//TODO: default date = today
    	//TODO: TemplateErorrHandler should pick up the exceptions (need to test it)
    	System.out.println(date);

    	Pair<String,Apod> pair = service.download(date);
    	System.out.println(pair);
		return pair;
    }
}
