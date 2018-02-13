package com.seel.apod.web.nasa.entity;


import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Apod {

	/**
	 * Format YYYY-MM-DD
	 * ex. "date": "2018-02-10"
	 */
	private String date;
	public void setDate(String date) {this.date = date;}
	public String getDate() {return date;}

	/**
	 * ex. "explanation": "Don't panic. It's just a spacesuited mannequin named Starman. As the sunlit crescent of planet Earth recedes in the background, Starman is comfortably seated at the wheel of a Tesla Roadster in this final image of the payload launched by a Falcon Heavy rocket on February 6. Internationally designated 2018-017A, roadster and Starman are headed for space beyond the orbit of Mars. The successful Falcon Heavy rocket has now become the most powerful rocket in operation and the roadster one of four electric cars launched from planet Earth. The other three were launched to the Moon by historically more powerful (but not reusable) Saturn V rockets. Still, Starman's roadster is probably the only one that would be considered street legal."
	 */
	private String explanation;
	public void setExplanation(String explanation) {this.explanation = explanation;}
	public String getExplanation() {return explanation;}

	/**
	 * ex. "hdurl": "https://apod.nasa.gov/apod/image/1802/Starman_SpaceX.jpg"
	 */
	private String hdurl;
	public void setHdurl(String hdurl) {this.hdurl = hdurl;}
	public String getHdurl() {return hdurl;}

	/**
	 * ex. "media_type": "image"
	 */
	@JsonProperty("media_type")
	private String mediaType;
	public void setMediaType(String mediaType) {this.mediaType = mediaType;}
	public String getMediaType() {return mediaType;}

	/**
	 * ex. "service_version": "v1"
	 */
	@JsonProperty("service_version")
	private String serviceVersion;
	public void setServiceVersion(String serviceVersion) {this.serviceVersion = serviceVersion;}
	public String getServiceVersion() {return serviceVersion;}

	/**
	 * ex. "title": "Roadster, Starman, Planet Earth"
	 */
	private String title;
	public void setTitle(String title) {this.title = title;}
	public String getTitle() {return title;}

	/**
	 * ex. "url": "https://apod.nasa.gov/apod/image/1802/Starman_SpaceX1067.jpg"
	 */
	private String url;
	public void setUrl(String url) {this.url = url;}
	public String getUrl() {return url;}

	public Apod() {}

	/**
	 * Since each day as a single image, we'll use the date as the identifier for the image
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null) return false;
		if (!(o instanceof Apod)) return false;

		Apod other = (Apod)o;
		return (StringUtils.equals(getDate(), other.getDate()));
	}

	@Override
	public int hashCode() {
		return getDate().hashCode();
	}

	@Override
	public String toString() {
		return "Apod {date:'" + date + "'"
				+ ",explanation:'" + explanation + "'"
				+ ",hdurl:'" + hdurl + "'"
				+ ",mediaType:'" + mediaType + "'"
				+ ",serviceVersion:'" + serviceVersion + "'"
				+ ",title:'" + title + "'"
				+ ",url:'" + url + "'}";
	}
}
