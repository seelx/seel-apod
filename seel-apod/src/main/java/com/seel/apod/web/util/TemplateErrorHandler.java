package com.seel.apod.web.util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Service
@Scope("prototype")
public class TemplateErrorHandler extends DefaultResponseErrorHandler {
	public static Logger logger = Logger.getLogger(TemplateErrorHandler.class);

	public TemplateErrorHandler() {}

	//TODO: improve exception handling / better logging / statistics
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		logger.error(response.getStatusText());
	}
}