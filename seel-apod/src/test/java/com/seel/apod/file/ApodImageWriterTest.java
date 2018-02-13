package com.seel.apod.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.seel.apod.SpringTest;
import com.seel.apod.web.util.ImageDownloader;

public class ApodImageWriterTest extends SpringTest {

	@Autowired ApplicationContext appContext;
	@Autowired ApodImageWriter writer;
	@Autowired ImageDownloader downloader;

	@Test
	public void write() {
		String testDir = "c:\\apod";
		String testFile = "test.jpg";

		try {
	    	Resource resource = appContext.getResource("classpath:test.jpg");
	    	BufferedImage image = ImageIO.read(resource.getURL());

			writer.write(testDir, testFile, image);

			File file = new File(testDir + File.separator + testFile);
			BufferedImage newImage = ImageIO.read(file);
			assert(newImage != null);
		}
		catch (IOException exc) {
			System.out.println(exc.getMessage());
		}
	}

	@Ignore
	public void writeNull() {
		//TODO: test null condition
	}
}
