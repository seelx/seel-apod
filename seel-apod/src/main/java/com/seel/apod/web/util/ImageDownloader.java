package com.seel.apod.web.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ImageDownloader {
	public static Logger logger = Logger.getLogger(ImageDownloader.class);

	public ImageDownloader() {}

	public BufferedImage download(String urlString) {
		if (StringUtils.isEmpty(urlString)) return null;

		BufferedImage image = null;
		try {
		    URL url = new URL(urlString);
		    image = ImageIO.read(url);
		} catch (IOException exc) {
			logger.error(exc.getMessage(),exc);
		}

		return image;
	}

}