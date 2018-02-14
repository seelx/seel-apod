package com.seel.apod.web.entity;

import org.apache.commons.lang3.tuple.Pair;

import com.seel.apod.web.nasa.entity.Apod;

public class SeelApod {

	private String message;
	public void setMessage(String message) {this.message = message;}
	public String getMessage() {return message;}

	private String file;
	public void setFile(String file) {this.file = file;}
	public String getFile() {return file;}

	private Apod apod;
	public void setApod(Apod apod) {this.apod = apod;}
	public Apod getApod() {return apod;}

	public SeelApod() {}

	public SeelApod(Pair<String,Apod> pair) {
		setFile(pair.getLeft());
		setApod(pair.getRight());
	}

}
