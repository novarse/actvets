package com.actvc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class Uploader {
	private static final Logger log = Logger
			.getLogger(Uploader.class.getName());

	private static final int MAXLINES = 50;
	private static final String WEBSITE = // "http://actveteranscycling.appspot.com";
	"http://localhost:8888";
	// "http://actvccrms.appspot.com";
	// "http://localhost:8888";//
	//
	// private static final String dir =
	// "C:\\work\\ide\\eclipse\\workspace\\ACTVC\\files\\";
	private static final String WORKSPACE = "/media/novarse/Storage/dev/workspace/";
	private static final String dir = WORKSPACE + "ACTVC/files/";
	private static final String[] FILENAMES = { dir + "event.txt",
			dir + "eventdescription.txt", dir + "eventtype.txt",
			dir + "eventlocation.txt", dir + "rider.txt",
			dir + "raceResults.txt", dir + "marshall.txt", dir + "season.txt",
			dir + "system.txt" };

	private static final int ADMIN = 200;
	private static final int MAINTENANCE = 300;
	// private static final String USERFILE =
	// "C:\\work\\ide\\eclipse\\workspace\\admin.txt";
	private static final String USERFILE = WORKSPACE + "/admin.txt";
	private static final String MAINTENANCEFILE = dir
			+ "updateeventseasons.txt";// "updateriders.txt";

	public static void main(String[] args) throws IOException {

		// for (int fileNo = 0; fileNo <= 8; fileNo++) {
		{
			int fileNo = 1;
			if (fileNo < FILENAMES.length) {
				if (fileNo == 5) {
					processRaceHistory();
				} else {
					processFile(fileNo);
				}
			}
		}
		processFile(ADMIN);
		// processFile(MAINTENANCE);
		// runExecuteStatement("update");
		log.info("Done");
	}

	private static void processRaceHistory() throws FileNotFoundException,
			MalformedURLException, IOException {
		for (int i = 1; i <= 1; i++) { // done 4 for local
			process(5, dir + "racehistory" + i + ".txt");
		}
	}

	private static void processFile(int fileNo) throws FileNotFoundException,
			IOException, MalformedURLException {
		String fileName;
		if (fileNo == ADMIN) {
			fileName = USERFILE;
		} else if (fileNo == MAINTENANCE) {
			fileName = MAINTENANCEFILE;
		} else {
			fileName = FILENAMES[fileNo];
		}
		process(fileNo, fileName);
	}

	private static void process(int fileNo, String fileName)
			throws FileNotFoundException, IOException, MalformedURLException {
		if (fileName != null) {
			File file = new File(fileName);
			int count = 0, batch = 0;

			StringBuilder s = new StringBuilder();
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line;
			while ((line = br.readLine()) != null) {
				s.append(line);
				s.append("\n");
				if (++count % MAXLINES == 0) {
					try {
						sendReceive(s, fileNo);
						count = 0;
						s.setLength(0);
						log.info("Batch: " + ++batch);
					} catch (Exception e) {
						e.getStackTrace();
						break;
					}
				}
			}
			if (s.length() != 0) {
				sendReceive(s, fileNo);
				log.info("Batch: " + ++batch);

			}

			br.close();

			log.info("Processed " + fileName);
		}
	}

	private static void sendReceive(StringBuilder s, int type)
			throws MalformedURLException, IOException {

		final URL url = new URL(WEBSITE + "/upload?type=" + type);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		os.write(s.toString().getBytes());
		os.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String returnedLine;
		while ((returnedLine = rd.readLine()) != null) {
			// Process line...
		}
		os.close();
		rd.close();
	}

	private static void runExecuteStatement(String statement) {

	}
}
