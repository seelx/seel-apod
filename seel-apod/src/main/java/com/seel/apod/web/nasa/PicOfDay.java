package com.seel.apod.web.nasa;



import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.seel.apod.web.nasa.entity.Apod;
import com.seel.apod.web.util.TemplateErrorHandler;

@Service
@Scope("prototype")
public class PicOfDay {
	public static Logger logger = Logger.getLogger(PicOfDay.class);
	public static DateTime EARLIEST_DATE = new DateTime(1995,06,16,0,0);

	@Autowired TemplateErrorHandler errorHandler;

	private String url;
	public void setUrl(String url) {this.url = url;}
	public String getUrl() {return url;}

	@Autowired
	public PicOfDay(@Value("${nasa.apod.url}") String url) {
		this.url = url;
	}

	/**
	 * Ex. https://api.nasa.gov/planetary/apod?date=2018-02-10&hd=true&api_key=DEMO_KEY
	 * @return
	 */
	public Apod getPicOfDay(String date, boolean hd, String apiKey) {

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				                                           .queryParam("date", date)
				                                           .queryParam("hd", hd)
				                                           .queryParam("api_key", apiKey);

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(errorHandler);
		restTemplate.getMessageConverters().add(converter);
		logger.info(builder.toUriString());
		Apod apod = restTemplate.getForObject(builder.toUriString(), Apod.class);
		return apod;
	}

	public Apod getPicOfDay(DateTime dateTime, Boolean hd, String apiKey) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		String date = dateTime.toString(dtf);
		return getPicOfDay(date,hd,apiKey);
	}

	public Apod getPicOfDay(DateTime dateTime) {
		return getPicOfDay(dateTime,true,"DEMO_KEY");
	}

	public Apod getPicOfDay(String date) {
		return getPicOfDay(date,true,"DEMO_KEY");
	}
}

