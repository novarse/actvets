package com.actvc.server;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.Ostermiller.util.ExcelCSVParser;
import com.Ostermiller.util.ExcelCSVPrinter;
import com.actvc.client.entities.TR;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.code.twig.ObjectDatastore;

/**
 * This servlet processes a CVS file containing lines of rider number, surname
 * and last name. It attempts to find the rider by these values and if
 * successful makes the id equal to the found rider id, otherwise sets the id to
 * '??'. The rider details plus the id are output to a result file.
 * 
 * @author stephen
 * 
 */
public class GetRiderIdsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ObjectDatastore datastore = Util.getDatastore();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException {
		ServletFileUpload upload = new ServletFileUpload();

		res.setContentType("text/plain");

		try {
			PrintWriter out = res.getWriter();
			ExcelCSVPrinter os = new ExcelCSVPrinter(out);

			FileItemIterator iterator = upload.getItemIterator(req);

			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStreamReader in = new InputStreamReader(item.openStream());
				res.setHeader("Content-Disposition",
						"attachment; filename=Result-" + item.getName());
				if (!item.isFormField()) {
					String[][] values = ExcelCSVParser.parse(in);

					for (String[] line : values) {
						os.print(line);
						os.println(resultLine(line));
					}
				}

			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Returns the rider id if a rider is found by the given fields. If not
	 * match is found then the id part is blank.
	 * 
	 * @param riderNo
	 * @param surname
	 * @param firstName
	 * @return
	 */
	private String resultLine(String[] line) {
		String riderNoStr = line[0];
		String surname = line[1].trim().toUpperCase();
		String firstName = line[2].trim().toUpperCase();
		String result = "";
		System.out.println("Surname: >" + surname + "<; Firstname:>"
				+ firstName + "<");

		try {

			QueryResultIterator<TR> iterator = datastore
					.find()
					.type(TR.class)
					.addFilter("uppercaseLastName", FilterOperator.EQUAL,
							surname)
					.addFilter("uppercaseFirstName", FilterOperator.EQUAL,
							firstName).now();

			int count = 0;
			while (iterator.hasNext()) {
				TR rider = iterator.next();
				if (++count > 1) {
					result = getIdStrFromNumberForDupRider(riderNoStr, surname,
							firstName);
				} else {
					result = rider.getId().toString();
				}
			}
		} catch (RuntimeException ex) {
			result = "err";
		}
		return result;
	}

	private String getIdStrFromNumberForDupRider(String riderNoStr,
			String surname, String firstName) {
		String result = "";
		try {
			Integer riderNo = Integer.parseInt(riderNoStr);

			QueryResultIterator<TR> iterator = datastore
					.find()
					.type(TR.class)
					.addFilter("uppercaseLastName", FilterOperator.EQUAL,
							surname.toUpperCase())
					.addFilter("uppercaseFirstName", FilterOperator.EQUAL,
							firstName.toUpperCase())
					.addFilter("number", FilterOperator.EQUAL, riderNo).now();

			int count = 0;
			while (iterator.hasNext()) {
				TR rider = iterator.next();
				if (++count > 1) {
					result = "dup";
				} else {
					result = rider.getId().toString();
				}
			}

		} catch (RuntimeException e) {
			result = "dup";
		}
		return result;
	}
}
