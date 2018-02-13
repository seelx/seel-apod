package com.seel.apod.service;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.seel.apod.file.ApodImageWriter;
import com.seel.apod.web.nasa.PicOfDay;
import com.seel.apod.web.nasa.entity.Apod;
import com.seel.apod.web.util.ImageDownloader;

@Service
public class ApodService {
	public static Logger logger = Logger.getLogger(ApodService.class);

	@Autowired PicOfDay picOfDay;
	@Autowired ImageDownloader downloader;
	@Autowired ApodImageWriter writer;

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

		//TODO: verify date string format
		try {
			Apod apod = picOfDay.getPicOfDay(date);
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

			System.out.println(fileName);
			System.out.println(apod);
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

}
