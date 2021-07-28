package solution;

import java.io.*;
import java.util.*;
import java.math.BigDecimal;
import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public class Assessmentsolution {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		
		//these set of variables are used for the montlysummary.json file
		double loanTotal = 0.0;
		double appraisedAmountTotal = 0.0;
		double interestRateTotal = 0.0;
		double loanMedian = 0.0;
		double appraisedRateMedian = 0.0;
		double interestRateMedian = 0.0;
		double loanMin = Double.MAX_VALUE;
		double loanMax = Double.MIN_VALUE;
		double appraisedMin = Double.MAX_VALUE;
		double appraisedMax = Double.MIN_VALUE;
		double interestMin = Double.MAX_VALUE;
		double interestMax = Double.MIN_VALUE;
		
		
		//these variables are used for the monthlyByState.json
		
		//state loan variables
		double stateLength = 0.0;
		double stateTotal = 0.0;
		double stateAverage = 0.0;
		double stateMedian = 0.0;
		double stateMin = Double.MAX_VALUE;
		double stateMax = Double.MIN_VALUE;
		double currTotal = 0.0;
		
		//state appraised varaibles
		double appLength = 0.0;
		double appTotal = 0.0;
		double appAverage = 0.0;
		double appMedian = 0.0;
		double appMin = Double.MAX_VALUE;
		double appMax = Double.MIN_VALUE;
		double currAppTotal = 0.0;
		
		//state interest rate variables
		double intLength = 0.0;
		double intTotal = 0.0;
		double intAverage = 0.0;
		double intMedian = 0.0;
		double intMin = Double.MAX_VALUE;
		double intMax = Double.MIN_VALUE;
		double currIntTotal = 0.0;
		//Map for keep track of loan values for the states using seperate hashmaps
		HashMap<String, List<Double>> medianTrack = new HashMap<String, List<Double>>();
		HashMap<String, List<Double>> map = new HashMap<String, List<Double>>();
		
		HashMap<String, List<Double>> appMedianTrack = new HashMap<String, List<Double>>();
		HashMap<String, List<Double>> appMap = new HashMap<String, List<Double>>();
		
		HashMap<String, List<Double>> interestMedianTrack = new HashMap<String, List<Double>>();
		HashMap<String, List<Double>> interestMap = new HashMap<String, List<Double>>();
		//length to determine how many values are in each state
		int length = 0;
		
		//try statement to parse and write to a new json file
		try {
			
			//selects the json file from the machine
			Object obj = parser.parse(new FileReader("/Users/isiddique/Downloads/SilverworkCodingChallenge/loans.json"));
			JSONArray sub = (JSONArray)obj;

			//getting the median values from the json array
			length = sub.size();
			JSONObject median = (JSONObject)sub.get(length/2);

			loanMedian = (double)median.get("LoanAmount");
			appraisedRateMedian = (double)median.get("SubjectAppraisedAmount");
			interestRateMedian = (double)median.get("InterestRate");
			

			//an iterator variable to iterate through the json array
			Iterator iterator = sub.iterator();
			while(iterator.hasNext()) {
				JSONObject thing = (JSONObject)iterator.next();
				//System.out.println(thing.get("LoanAmount"));
				
				//gather the necessary information such as loan, appraised, and interest calculations
				loanTotal = (double)thing.get("LoanAmount") + loanTotal;
				loanMin = Math.min(loanMin, (double)thing.get("LoanAmount"));
				loanMax = Math.max(loanMax, (double)thing.get("LoanAmount"));
				appraisedAmountTotal = (double)thing.get("SubjectAppraisedAmount") + appraisedAmountTotal;
				appraisedMin = Math.min(appraisedMin, (double)thing.get("SubjectAppraisedAmount"));
				appraisedMax = Math.max(appraisedMax, (double)thing.get("SubjectAppraisedAmount"));
				interestRateTotal = (double)thing.get("InterestRate") + interestRateTotal;
				interestMin = Math.min(interestMin, (double)thing.get("InterestRate"));
				interestMax = Math.max(interestMax, (double)thing.get("InterestRate"));
				
				//gather the loan, appraised, interest values at the current iteration
				currTotal = (double)thing.get("LoanAmount");
				currAppTotal = (double)thing.get("SubjectAppraisedAmount");
				currIntTotal = (double)thing.get("InterestRate");
				
				/*a set of if else statements where if a state is not inside a hashmap, it will begin inserting
				  * and continue to check over the respective hashmap until the end of iteration is reached.
				  */
				 
				//first 3 if/else are for inserting and tracking median value
				if(!medianTrack.containsKey(thing.get("SubjectState".toString()))) {
					List<Double> medianList = new ArrayList<Double>();
					medianList.add(currTotal);
					medianTrack.put(thing.get("SubjectState").toString(), medianList );
				}else {
					List<Double> exists = medianTrack.get(thing.get("SubjectState").toString());
					exists.add(currTotal);
					medianTrack.put(thing.get("SubjectState").toString(), exists );
				}
				
				if(!appMedianTrack.containsKey(thing.get("SubjectState".toString()))) {
					List<Double> medianList = new ArrayList<Double>();
					medianList.add(currAppTotal);
					appMedianTrack.put(thing.get("SubjectState").toString(), medianList );
				}else {
					List<Double> exist = appMedianTrack.get(thing.get("SubjectState").toString());
					exist.add(currAppTotal);
					appMedianTrack.put(thing.get("SubjectState").toString(), exist );
				}
				
				if(!interestMedianTrack.containsKey(thing.get("SubjectState".toString()))) {
					List<Double> medianList = new ArrayList<Double>();
					medianList.add(currAppTotal);
					interestMedianTrack.put(thing.get("SubjectState").toString(), medianList );
				}else {
					List<Double> exist = interestMedianTrack.get(thing.get("SubjectState").toString());
					exist.add(currAppTotal);
					interestMedianTrack.put(thing.get("SubjectState").toString(), exist );
				}
				
				//from here is where hashmaps are used to store information based on each state
				if(!map.containsKey(thing.get("SubjectState").toString())) {
					List<Double> list = new ArrayList<Double>();
					//if it doesnt exist, create a an object and put in the values
					list.add(1.0);
					list.add(currTotal);
					list.add(stateAverage);
					list.add(stateMedian);
					list.add(stateMin);
					list.add(stateMax);
					map.put(thing.get("SubjectState").toString(), list);
				}
					else {
					//if it does, then do the calculations and insert it into the hashmap
					List<Double> exist = map.get(thing.get("SubjectState").toString());
					List<Double> medianCheck = medianTrack.get(thing.get("SubjectState").toString());
					Collections.sort(medianCheck);
					stateLength = exist.get(0) + 1;
					stateTotal = exist.get(1) + currTotal;
					stateAverage = exist.get(0)/exist.get(1);
					stateMedian = medianCheck.get((int)stateLength/2);
					stateMin = Math.min(exist.get(4), currTotal);
					stateMax = Math.max(exist.get(5), currTotal);
					
					
					exist.set(0,stateLength);
					exist.set(1, stateTotal);
					exist.set(2, stateAverage);
					exist.set(3, stateMedian);
					exist.set(4, stateMin);
					exist.set(5, stateMax);
					map.put(thing.get("SubjectState").toString(), exist);
					
				}
				
				if(!appMap.containsKey(thing.get("SubjectState").toString())) {
					List<Double> list = new ArrayList<Double>();
					
					list.add(1.0);
					list.add(currAppTotal);
					list.add(appAverage);
					list.add(appMedian);
					list.add(appMin);
					list.add(appMax);
					appMap.put(thing.get("SubjectState").toString(), list);
				}
					else {
					List<Double> exists = appMap.get(thing.get("SubjectState").toString());
					List<Double> medianCheck = appMedianTrack.get(thing.get("SubjectState").toString());
					Collections.sort(medianCheck);
					appLength = exists.get(0) + 1;
					appTotal = exists.get(1) + currAppTotal;
					appAverage = exists.get(0)/exists.get(1);
					appMedian = medianCheck.get((int)appLength/2);
					appMin = Math.min(exists.get(4), currAppTotal);
					appMax = Math.max(exists.get(5), currAppTotal);
					
					
					exists.set(0, appLength);
					exists.set(1, appTotal);
					exists.set(2, appAverage);
					exists.set(3, appMedian);
					exists.set(4, appMin);
					exists.set(5, appMax);
					appMap.put(thing.get("SubjectState").toString(), exists);					
				}
				
				if(!interestMap.containsKey(thing.get("SubjectState").toString())) {
					List<Double> list = new ArrayList<Double>();
					
					list.add(1.0);
					list.add(currIntTotal);
					list.add(intAverage);
					list.add(intMedian);
					list.add(intMin);
					list.add(intMax);
					interestMap.put(thing.get("SubjectState").toString(), list);
				}
					else {
					List<Double> exists = interestMap.get(thing.get("SubjectState").toString());
					List<Double> medianCheck = interestMedianTrack.get(thing.get("SubjectState").toString());
					Collections.sort(medianCheck);
					intLength = exists.get(0) + 1;
					intTotal = exists.get(1) + currIntTotal;
					intAverage = exists.get(0)/exists.get(1);
					intMedian = medianCheck.get((int)intLength/2);
					intMin = Math.min(exists.get(4), currIntTotal);
					intMax = Math.max(exists.get(5), currIntTotal);
					
					
					exists.set(0, intLength);
					exists.set(1, intTotal);
					exists.set(2, intAverage);
					exists.set(3, intMedian);
					exists.set(4, intMin);
					exists.set(5, intMax);
					interestMap.put(thing.get("SubjectState").toString(), exists);					
				}
				

			}
			
			//json objects that are needed for inputting hthe new information foe the file to be created
			JSONObject stateInfoLoan = new JSONObject();
			JSONObject states = new JSONObject();
			JSONObject combine = new JSONObject();
			
			// 3 iterators to go through each of the maps
			Iterator<Map.Entry<String, List<Double>>> entry = map.entrySet().iterator();
			Iterator<Map.Entry<String, List<Double>>> entry1 = appMap.entrySet().iterator();
			Iterator<Map.Entry<String, List<Double>>> entry2 = interestMap.entrySet().iterator();
			while(entry.hasNext()) {
				
				//as it iterates, put the information of each state with the interest, loan, and apprasied information
				Map.Entry<String, List<Double>> itr = entry.next();
				Map.Entry<String, List<Double>> itr1 = entry1.next();
				Map.Entry<String, List<Double>> itr2 = entry2.next();
				JSONObject loan = new JSONObject();
				BigDecimal loanTotals = new BigDecimal(loanTotal);
				loan.put("Sum", itr.getValue().get(1));
				loan.put("Average", itr.getValue().get(2));
				loan.put("Median", itr.getValue().get(3));
				loan.put("Minimum", itr.getValue().get(4));
				loan.put("Maximum", itr.getValue().get(5));
				
				JSONObject loanName = new JSONObject();
				loanName.put("LoanAmountSummary", loan);
				
				//for appraising
				JSONObject appraise = new JSONObject();
				BigDecimal appraisedAmountTotals = new BigDecimal(appraisedAmountTotal);
				appraise.put("Sum", itr1.getValue().get(1));
				appraise.put("Average", itr1.getValue().get(2));
				appraise.put("Median", itr1.getValue().get(3));
				appraise.put("Minimum", itr1.getValue().get(4));
				appraise.put("Maximum", itr1.getValue().get(5));
				
				//JSONObject appraiseName = new JSONObject();
				loanName.put("AppraiseSummary", appraise);
				
				//for interest
				JSONObject interest = new JSONObject();
				BigDecimal interestRateTotals = new BigDecimal(interestRateTotal);
				interest.put("Sum", itr2.getValue().get(1));
				interest.put("Average", itr2.getValue().get(2));
				interest.put("Median", itr2.getValue().get(3));
				interest.put("Minimum", itr2.getValue().get(4));
				interest.put("Maximum", itr2.getValue().get(5));
				
				//JSONObject appraiseName = new JSONObject();
				loanName.put("InterestSummary", interest);
				
				states.put(itr.getKey(), loanName);
				

			}
			//here we begin the process fo writing to a new file/second file
			String jsonString = states.toJSONString();

			PrintWriter out2 = null;
			try {
				out2 = new PrintWriter(new FileWriter("/Users/isiddique/Downloads/SilverworkCodingChallenge/monthlyByState.json"));
				out2.write(jsonString);
				out2.close();
			} catch(Exception ex){
				System.out.println("failed to create/write file");
			}
			
			//begin creating the first json file and inputting the finished calculations
			
			JSONObject loan = new JSONObject();
			BigDecimal loanTotals = new BigDecimal(loanTotal);
			
			loan.put("Average", loanTotal/length);
			loan.put("Median", loanMedian);
			loan.put("Minimum", loanMin);
			loan.put("Maximum", loanMax);
			loan.put("Sum", loanTotals);
			
			JSONObject loanName = new JSONObject();
			loanName.put("LoanAmountSummary", loan);
			
			//for appraising
			JSONObject appraise = new JSONObject();
			BigDecimal appraisedAmountTotals = new BigDecimal(appraisedAmountTotal);
			appraise.put("Sum", appraisedAmountTotals);
			appraise.put("Average", appraisedAmountTotal/length);
			appraise.put("Median", appraisedRateMedian);
			appraise.put("Minimum", appraisedMin);
			appraise.put("Maximum", appraisedMax);
			
			loanName.put("AppraiseSummary", appraise);
			
			//for interest
			JSONObject interest = new JSONObject();
			BigDecimal interestRateTotals = new BigDecimal(interestRateTotal);
			interest.put("Sum", interestRateTotals);
			interest.put("Average", interestRateTotal/length);
			interest.put("Median", interestRateMedian);
			interest.put("Minimum", interestMin);
			interest.put("Maximum", interestMax);
			
			loanName.put("InterestSummary", interest);
			
			
			//start writing into a new file/first file
			jsonString = loanName.toJSONString();
			PrintWriter out1 = null;
			try {
				out1 = new PrintWriter(new FileWriter("/Users/isiddique/Downloads/SilverworkCodingChallenge/monthlySummary.json"));
				out1.write(jsonString);
				out1.write("");
				out1.close();
			} catch(Exception ex){
				System.out.println("failed to create/write file");
			}
		} catch(Exception e) {
			System.out.println(e);
		}

	}
}
