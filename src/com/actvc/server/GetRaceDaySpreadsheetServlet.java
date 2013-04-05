package com.actvc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.actvc.client.common.MyUtils;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TPRH;
import com.actvc.client.entities.TR;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.code.twig.ObjectDatastore;

public class GetRaceDaySpreadsheetServlet extends HttpServlet implements
		Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1326817640950427069L;
	private static final Logger log = Logger.getLogger(FileServlet.class
			.getName());
	private static GetDataDAO getDataDao = new GetDataDAO();
	private final ObjectDatastore datastore = Util.getDatastore();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			String[] params = req.getQueryString().split("&");
			Long eventId = new Long(params[0]);
			String[] dateParts = params[1].split("/");
			String dateStr = dateParts[2]
					+ dateParts[1]
					+ (dateParts[0].length() == 1 ? "0" + dateParts[0]
							: dateParts[0]);
			res.setContentType("text/plain");
			res.setHeader("Content-Disposition", "attachment; filename=raceday"
					+ dateStr + ".txt");

			PrintWriter out = res.getWriter();

			String eventDesc = getDataDao.getEventDescNoDate(eventId) + "-"
					+ dateStr;

			out.println(eventDesc);
			out.println();
			out.println("Number\tSurname\tFirstName\tGrade\tSubGrade\tCriterium\tRace Grade\tPosition\tOverTheLine\tTime\tPoints\tAVCCNumber\tDOB\tGender\tStreet"
					+ "\tSuburb\tState\tPostcode\tHome Phone\tWork or Mobile\tEmail\tFirst Aid\tEmergency Contact\tEmergency Contact No\tEmergency Contact No2\tComment\tID");
			outputActiveRiders(out);

			outputPendingRiders(out);

			outputInactiveRiders(out);

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new ServletException(e);
		}
	}

	private void outputActiveRiders(PrintWriter out) {
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addFilter("number", FilterOperator.GREATER_THAN, 0)
				.addSort("number").now();
		while (iterator.hasNext()) {
			Person tr = iterator.next();
			out.println(tr.getNumber()
					+ "\t"
					+ tr.getLastName()
					+ "\t"
					+ tr.getFirstName()
					+ "\t"
					+ (tr.getGrade() == null ? "" : tr.getGrade())
					+ "\t"
					+ (tr.getSubGrade() == 0 ? "" : tr.getSubGrade())
					+ "\t"
					+ (tr.getCriteriumGrade() == null ? "" : tr
							.getCriteriumGrade()) + "\t" + "\t" + "\t" + "\t"
					+ "\t" + "\t"
					+ (tr.getAVCCNumber() == null ? "" : tr.getAVCCNumber())
					+ "\t" + MyUtils.getDateStr((Date) tr.getDob()) + "\t"
					+ tr.getGender() + "\t" + tr.getStreet() + "\t"
					+ tr.getSuburb() + "\t" + tr.getState() + "\t"
					+ tr.getPostcode() + "\t" + tr.getPhoneHome() + "\t"
					+ tr.getPhoneWorkOrMobile() + "\t" + tr.getEmail() + "\t"
					+ (tr.isFirstAid() ? "1" : "") + "\t"
					+ tr.getEmergencyContact() + "\t"
					+ tr.getPhoneEmergencyContact() + "\t"
					+ tr.getPhoneEmergencyContact2() + "\t" + "\t" + tr.getId());
		}
	}

	private void outputPendingRiders(PrintWriter out) {
		QueryResultIterator<TPRH> prhIterator = datastore.find()
				.type(TPRH.class).addSort("number").now();
		if (prhIterator.hasNext()) {
			out.println();
			out.println();
			out.println("Pending Riders");
		}
		while (prhIterator.hasNext()) {
			TPRH prh = prhIterator.next();
			out.println(prh.getNumber()
					+ "\t"
					+ prh.getLastName()
					+ "\t"
					+ prh.getFirstName()
					+ "\t"
					+ (prh.getGrade() == null || prh.getGrade().equals("null") ? ""
							: prh.getGrade())
					+ "\t"
					+ (prh.getSubGrade() == 0 ? "" : prh.getSubGrade())
					+ "\t"
					+ (prh.getCriteriumGrade() == null
							|| prh.getCriteriumGrade().equals("null") ? ""
							: prh.getCriteriumGrade()) + "\t" + "\t" + "\t"
					+ "\t" + "\t" + "\t"
					+ (prh.getAVCCNumber() == null ? "" : prh.getAVCCNumber())
					+ "\t" + prh.getDob() + "\t" + prh.getGender() + "\t"
					+ prh.getStreet() + "\t" + prh.getSuburb() + "\t"
					+ prh.getState() + "\t" + prh.getPostcode() + "\t"
					+ prh.getPhoneHome() + "\t" + prh.getPhoneWorkOrMobile()
					+ "\t" + prh.getEmail() + "\t"
					+ (prh.isFirstAid() ? "1" : "") + "\t"
					+ prh.getEmergencyContact() + "\t"
					+ prh.getPhoneEmergencyContact() + "\t"
					+ prh.getPhoneEmergencyContact2() + "\t");
		}
	}

	private void outputInactiveRiders(PrintWriter out) {
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addFilter("number", FilterOperator.EQUAL, 0).addSort("number")
				.now();
		if (iterator.hasNext()) {
			out.println();
			out.println();
			out.println("Inactive Riders");
		}
		while (iterator.hasNext()) {
			Person tr = iterator.next();
			out.println(tr.getNumber()
					+ "\t"
					+ tr.getLastName()
					+ "\t"
					+ tr.getFirstName()
					+ "\t"
					+ (tr.getGrade() == null ? "" : tr.getGrade())
					+ "\t"
					+ (tr.getSubGrade() == 0 ? "" : tr.getSubGrade())
					+ "\t"
					+ (tr.getCriteriumGrade() == null ? "" : tr
							.getCriteriumGrade()) + "\t" + "\t" + "\t" + "\t"
					+ "\t" + "\t"
					+ (tr.getAVCCNumber() == null ? "" : tr.getAVCCNumber())
					+ "\t" + MyUtils.getDateStr((Date) tr.getDob()) + "\t"
					+ tr.getGender() + "\t" + tr.getStreet() + "\t"
					+ tr.getSuburb() + "\t" + tr.getState() + "\t"
					+ tr.getPostcode() + "\t" + tr.getPhoneHome() + "\t"
					+ tr.getPhoneWorkOrMobile() + "\t" + tr.getEmail() + "\t"
					+ (tr.isFirstAid() ? "1" : "") + "\t"
					+ tr.getEmergencyContact() + "\t"
					+ tr.getPhoneEmergencyContact() + "\t"
					+ tr.getPhoneEmergencyContact2() + "\t" + "\t" + tr.getId());
		}
	}

}
