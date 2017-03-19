package com.cuberoot.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.websystique.springmvc.model.Reports;

public class DTOProcessor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<Reports> ProcessReportDTO(List<Reports> Report,
			String dateStart, String dateEnd, int reportCode) {

		Map<String, List<String>> report = new HashMap<String, List<String>>();
		List<String> dates = new ArrayList<String>();
		Set<String> campaignIds = new HashSet<String>();

		dates = getDatesDateRange(dateStart, dateEnd);

		for (int j = 0; j < dates.size(); j++) {
			
			campaignIds.clear();
			for (int i = 0; i < Report.size(); i++) {
				String date = Report.get(i).getDate();
				if (date.equals(dates.get(j)) == false) {

					Reports report1 = new Reports();

					if (reportCode == 1) {
						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setImpressions("0");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
						
					
					
					
					}

					if (reportCode == 2) {
						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setClicks("0");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
						
					}

					if (reportCode == 3) {
						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setConversions("0");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}

					}

					if (reportCode == 4) {

						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setCost("0");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
					}
					
					
			
					if (reportCode == 17) {

						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setReach("0");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
					}
					
				
					if (reportCode == 5) {

						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setAudience_segment("NA");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
					}
				
				
					if (reportCode == 6 ) {

						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setCity("NA");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
					}
				
					
					if (reportCode == 7 ) {

						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setDevice_type("NA");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
					}
					
					
					
					
					if (reportCode == 8 ) {

						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setOs("NA");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
					}
					
					
					
					
					
					if (reportCode == 19 ) {

						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setAge("NA");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
					}
					
					
					
					if (reportCode == 20 ) {

						if (campaignIds.contains(Report.get(i).getCampaign_id()) == false) {
							report1.setDate(dates.get(j));
							report1.setCampaign_id(Report.get(i)
									.getCampaign_id());
							report1.setGender("NA");
							report1.setChannel(Report.get(i).getChannel());
							campaignIds.add(Report.get(i).getCampaign_id());
							Report.add(report1);
						    System.out.println(Report.toString());
						}
					}
					
					
					
					
					
				}
			
				else{
					
					campaignIds.add(Report.get(i).getCampaign_id());
				}
			
			}

		}

		return Report;

	}

	public static List<String> getDatesDateRange(String str_date,
			String end_date) {
		List<Date> dates = new ArrayList<Date>();

		// String str_date ="27/08/2010";
		// String end_date ="02/09/2010";
		List<String> Dates = new ArrayList<String>();
		DateFormat formatter;

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		try {
			startDate = (Date) formatter.parse(str_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endDate = null;
		try {
			endDate = (Date) formatter.parse(end_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
		long endTime = endDate.getTime(); // create your endtime here, possibly
											// using Calendar or Date
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
			dates.add(new Date(curTime));
			curTime += interval;
		}
		for (int i = 0; i < dates.size(); i++) {
			Date lDate = (Date) dates.get(i);
			String ds = formatter.format(lDate);
			Dates.add(ds);
			System.out.println(" Date is ..." + ds);
		}
		return Dates;

	}
}
