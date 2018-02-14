package com.seel.apod.service;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seel.apod.SpringTest;
import com.seel.apod.web.nasa.entity.Apod;

public class ApodServiceTest extends SpringTest {

	public static Apod testApod;

	@Autowired ApodService service;

	@BeforeClass
	public static void beforeClass() {
		testApod = new Apod();
		testApod.setDate("2018-02-10");
		testApod.setExplanation("Don't panic. It's just a spacesuited mannequin named Starman. As the sunlit crescent of planet Earth recedes in the background, Starman is comfortably seated at the wheel of a Tesla Roadster in this final image of the payload launched by a Falcon Heavy rocket on February 6. Internationally designated 2018-017A, roadster and Starman are headed for space beyond the orbit of Mars. The successful Falcon Heavy rocket has now become the most powerful rocket in operation and the roadster one of four electric cars launched from planet Earth. The other three were launched to the Moon by historically more powerful (but not reusable) Saturn V rockets. Still, Starman's roadster is probably the only one that would be considered street legal.");
		testApod.setHdurl("https://apod.nasa.gov/apod/image/1802/Starman_SpaceX.jpg");
		testApod.setMediaType("image");
		testApod.setServiceVersion("v1");
		testApod.setTitle("Roadster, Starman, Planet Earth");
		testApod.setUrl("https://apod.nasa.gov/apod/image/1802/Starman_SpaceX1067.jpg");
	}

	@Test
	public void determineUrl() {
		String urlString = service.determineUrl(testApod);
		assert(urlString.equals(testApod.getHdurl()));
	}

	@Test
	public void determineName() {
		String name = service.determineName(testApod.getHdurl());
		assert(name.equals("Starman_SpaceX.jpg"));
	}

	/**
	 * This is an integrated test of the service.  It is only used during development.
	 * Better integrated test should made elsewhere.
	 */
	@Test
	public void download() {

		Pair<String,Apod> pair = service.download("2018-01-10");
		assert(pair != null);
	}

	@Test
	public void download2() {

		Pair<String,Apod> pair = service.download("22-Feb-15");
		assert(pair != null);
	}

	//TODO: many tests are needed for null/argument/exception/condition branching (if hdurl empty for instance)

}
