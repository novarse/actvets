package com.actvc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class TestServletCaller {
	private final static Logger log = Logger.getLogger(TestServletCaller.class
			.getName());
	private static final String WEBSITE = "http://localhost:8888";// "http://actveteranscycling.appspot.com";//

	public static void main(String[] args) {
		try {
			URL url = new URL(WEBSITE + "/emaildirector");

			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			String returnedLine;
			while ((returnedLine = rd.readLine()) != null) {
				log.info(returnedLine);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
