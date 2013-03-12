package com.actvc.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.Ostermiller.util.ExcelCSVParser;
import com.actvc.client.entities.TE;
import com.google.code.twig.ObjectDatastore;

/**
 * Processing an uploaded race results file. The file is expected to be an Excel
 * comma delimited file. The parsing is handled by Ostermiller classes, in
 * particular {@link ExcelCSVParser}.
 * 
 * @author stephen
 * 
 */
public class FileServlet extends HttpServlet implements Servlet {
	private static final Logger log = Logger.getLogger(FileServlet.class
			.getName());

	private static ProcessUploadDAO processUploadDao = new ProcessUploadDAO();
	private final ObjectDatastore datastore = Util.getDatastore();
	private final GetDataDAO getDataDAO = new GetDataDAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			ServletFileUpload upload = new ServletFileUpload();
			boolean isRaceDayFileUploadUpdatesRiders = getDataDAO.getTS()
					.isRaceDayFileUploadUpdatesRiders();

			TE event = datastore.load(TE.class, new Long(req.getQueryString()
					.substring(8)));

			res.setContentType("text/html");
			PrintWriter out = res.getWriter();

			try {
				FileItemIterator iterator = upload.getItemIterator(req);
				while (iterator.hasNext()) {
					FileItemStream item = iterator.next();
					InputStreamReader in = new InputStreamReader(
							item.openStream());

					if (item.isFormField()) {
					} else {
						String fileName = item.getName();
						log.info("filename: " + fileName);

						String[][] values = ExcelCSVParser.parse(in);

						for (String[] line : values) {
							processUploadDao.processLine(event, line,
									isRaceDayFileUploadUpdatesRiders);
						}
					}
					out.println("File processed");
				}
			} catch (SizeLimitExceededException se) {
				throw new Exception(se);
			}
		} catch (Exception ex) {

			throw new ServletException(ex);
		}
	}

}
