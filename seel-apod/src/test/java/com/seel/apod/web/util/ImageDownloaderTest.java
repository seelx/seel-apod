package com.seel.apod.web.util;

import java.awt.image.BufferedImage;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seel.apod.SpringTest;

public class ImageDownloaderTest extends SpringTest {

	@Autowired ImageDownloader downloader;

	@Test
	public void download() {
		String url = "https://apod.nasa.gov/apod/image/1802/Starman_SpaceX.jpg";

		BufferedImage image = downloader.download(url);
		assert(image != null);
	}

	@Ignore
	public void downloadNull() {
		//TODO: test null url
	}

}
