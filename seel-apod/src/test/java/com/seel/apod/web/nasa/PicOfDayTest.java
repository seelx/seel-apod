package com.seel.apod.web.nasa;


import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seel.apod.SpringTest;
import com.seel.apod.web.nasa.entity.Apod;

public class PicOfDayTest extends SpringTest {

	public static Apod testApod;

	@Autowired PicOfDay picOfDay;

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
	public void getPicOfDay() {
		String date = "2018-02-10";
		Boolean hd = true;
		String apiKey = "DEMO_KEY";

		Apod apod = picOfDay.getPicOfDay(date,hd,apiKey);
		assert(apod.equals(testApod));
		assert(StringUtils.equals(apod.getExplanation(),testApod.getExplanation()));
		assert(StringUtils.equals(apod.getHdurl(),testApod.getHdurl()));
		assert(StringUtils.equals(apod.getMediaType(),testApod.getMediaType()));
		assert(StringUtils.equals(apod.getServiceVersion(),testApod.getServiceVersion()));
		assert(StringUtils.equals(apod.getTitle(),testApod.getTitle()));
		assert(StringUtils.equals(apod.getUrl(),testApod.getUrl()));
	}

	@Test
	public void getPicOfDay2() {
		DateTime date = new DateTime(2018,2,10,0,0);
		Boolean hd = true;
		String apiKey = "DEMO_KEY";

		Apod apod = picOfDay.getPicOfDay(date,hd,apiKey);
		assert(apod.equals(testApod));
		assert(StringUtils.equals(apod.getExplanation(),testApod.getExplanation()));
		assert(StringUtils.equals(apod.getHdurl(),testApod.getHdurl()));
		assert(StringUtils.equals(apod.getMediaType(),testApod.getMediaType()));
		assert(StringUtils.equals(apod.getServiceVersion(),testApod.getServiceVersion()));
		assert(StringUtils.equals(apod.getTitle(),testApod.getTitle()));
		assert(StringUtils.equals(apod.getUrl(),testApod.getUrl()));
	}

	@Test
	public void getPicOfDay3() {
		String date = "2018-02-10";

		Apod apod = picOfDay.getPicOfDay(date);
		assert(apod.equals(testApod));
		assert(StringUtils.equals(apod.getExplanation(),testApod.getExplanation()));
		assert(StringUtils.equals(apod.getHdurl(),testApod.getHdurl()));
		assert(StringUtils.equals(apod.getMediaType(),testApod.getMediaType()));
		assert(StringUtils.equals(apod.getServiceVersion(),testApod.getServiceVersion()));
		assert(StringUtils.equals(apod.getTitle(),testApod.getTitle()));
		assert(StringUtils.equals(apod.getUrl(),testApod.getUrl()));
	}

	@Ignore
	public void getPicOfDayNull() {
		String date = null;
		Boolean hd = true;
		String apiKey = "DEMO_KEY";

		Apod apod = picOfDay.getPicOfDay(date,hd,apiKey);
		assert(apod != null);

		//TODO: null date defaults to today from NASA API
	}

	@Ignore
	public void getPicOfDayFuture() {
		//TODO: assert that log is written when date is out of range for NASA API (past and future)
	}

	@Ignore
	public void getPicOfDayNull2() {
		//TODO: test other null parameters
	}

	//TODO: test other signatures

}
