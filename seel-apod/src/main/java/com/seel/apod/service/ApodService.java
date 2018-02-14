package com.seel.apod.service;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.seel.apod.file.ApodImageWriter;
import com.seel.apod.util.validation.DateValidator;
import com.seel.apod.web.nasa.PicOfDay;
import com.seel.apod.web.nasa.entity.Apod;
import com.seel.apod.web.util.ImageDownloader;

@Service
public class ApodService {
	public static Logger logger = Logger.getLogger(ApodService.class);

	public static enum ValidDateFormat {
		  yyyy_MM_dd("yyyy-MM-dd")
		, d_MMM_yyyy("d-MMM-yyyy")
		, d_MMM_yy("d-MMM-yy");

		private String format;
		public String getFormat() {return format;}

		private ValidDateFormat(String format) {
			  this.format = format;
		}
	};

	@Autowired PicOfDay picOfDay;
	@Autowired ImageDownloader downloader;
	@Autowired ApodImageWriter writer;
	@Autowired DateValidator dateValidator;

	private String imageFolder;
	public void setImageFolder(String imageFolder) {this.imageFolder = imageFolder;}
	public String getImageFolder() {return imageFolder; }

	@Autowired
	public ApodService(@Value("${image.folder}") String imageFolder) {
		this.imageFolder = imageFolder;
	}

	public Pair<String,Apod> download(String date) {
		BufferedImage image = null;
		Pair<String,Apod> pair = null;

		try {
			String dateString = determineDate(date);
			Apod apod = picOfDay.getPicOfDay(dateString);
			String fileName = null;

			//TODO: clean/refactor these nested if statements
			if (apod == null) {
				logger.warn("The pic of the day could not be downloaded");
			}
			else {
				String urlString = determineUrl(apod);
				image = downloader.download(urlString);

				if (image == null) logger.warn("The image could not be downloaded");
				else {
					String name = determineName(urlString);
					fileName = writer.write(imageFolder,name,image);
				}
			}

			if (apod != null && !StringUtils.isEmpty(fileName)) pair = new ImmutablePair<String,Apod>(fileName,apod);
		}
		catch (Exception exc) {
			logger.error(exc.getMessage(),exc);
		}

		return pair;
	}

	public String determineUrl(Apod apod) {
		if (apod == null) return null;

		String urlString = null;
		if (!StringUtils.isEmpty(apod.getHdurl())) urlString = apod.getHdurl();
		else if (!StringUtils.isEmpty(apod.getUrl())) urlString = apod.getUrl();

		return urlString;
	}

	public String determineName(String urlString) {
		if (StringUtils.isEmpty(urlString)) return null;

		String parsed[] = urlString.split("/");
		String name = parsed[parsed.length -1];

		return name;
	}

	public boolean isValidDate(String inDate) {
		if (StringUtils.isEmpty(inDate)) return false;
		boolean isValid = false;

		for (ValidDateFormat validDateFormat: ValidDateFormat.values()) {
			isValid = dateValidator.isValid(inDate,validDateFormat.getFormat());
			if (isValid) break;
		}

		return isValid;
	}

	/**
	 * If a date is before the PicOfDay earliest day, it will be set to the earliest date
	 * If a date is after PicOfDay is after today, the NASA web site will default to today
	 * @param inDate
	 * @return
	 */
	public String determineDate(String inDate) {
		String outDate = null;

		try
		{
			for (ValidDateFormat validDateFormat: ValidDateFormat.values()) {
				try
				{
					DateTimeFormatter dtf = DateTimeFormat.forPattern(validDateFormat.getFormat());
					DateTime datetime = dtf.parseDateTime(inDate);
					if (datetime.isBefore(PicOfDay.EARLIEST_DATE)) datetime = PicOfDay.EARLIEST_DATE;
					outDate = datetime.toString(ValidDateFormat.yyyy_MM_dd.getFormat());
				}
				catch (IllegalArgumentException exc) {
					//do nothing, try next format
				}
			}
		}
		catch (Exception exc) {
			logger.error(exc.getMessage(),exc);
		}

		return outDate;
	}

}
