package com.seel.apod.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ApodImageWriter {
	public static Logger logger = Logger.getLogger(ApodImageWriter.class);

	public ApodImageWriter() {}

	public String write(String imageFolder, String imageName, BufferedImage image) {
		if (!StringUtils.isEmpty(imageFolder) && !StringUtils.isEmpty(imageName) && image == null) return null;
		String fileName = null;

		try {
		    File directory = new File(imageFolder);
		    if (! directory.exists()){
		        directory.mkdir();
		    }

		    fileName = imageFolder + File.separator + imageName;
		    File imageFile = new File(fileName);
			Files.deleteIfExists(imageFile.toPath()); //remove any image that may already be there

		    ImageIO.write(image, "jpg", imageFile);
		} catch (IOException | IllegalArgumentException exc) {
		    logger.error(exc.getMessage(),exc);
		    fileName = null;
		}

		return fileName;
	}
}
