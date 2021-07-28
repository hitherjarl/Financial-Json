This program will develop a monthly report for a given month. It is a program that can import a loan data from the JSON file, perform a series of calculations on the data, and then write the results of those calculations to new JSON files.
 
 The program will analyze the loans contained in the loanFiles.json file and perform the following calculations:
	
	* The sum, average, median, minimum and maximum values for the following fields:
		* LoanAmount
		* SubjectAppraisedAmount
		* InterestRate

The first should be called monthlySummary.json and should contain the calculations across all states. It's format should be as follows:
{
	"LoanAmountSummary": {	
		"Sum": #####.##,
		"Average": #####.##,
		"Median": #####.##,
		"Minimum": #####.##,
		"Maximum": #####.##,
	},
	"SubjectAppraisedAmountSummary": {	
		"Sum": #####.##,
		"Average": #####.##,
		"Median": #####.##,
		"Minimum": #####.##,
		"Maximum": #####.##,
	},
		"InterestRateSummary": {	
		"Sum": #####.##,
		"Average": #####.##,
		"Median": #####.##,
		"Minimum": #####.##,
		"Maximum": #####.##,
	}
}

The second JSON file should be called monthlyByState.json. It should contain the same calculations as the monthlySummary.json file, but this time grouped by state. It's format should be as follows (the order the states are in in the file does not matter):
{
	"IL": {
		"LoanAmmountSummary": {	
			"Sum": #####.##,
			"Average": #####.##,
			"Median": #####.##,
			"Mininum": #####.##,
			"Maximum": #####.##,
		},
		"SubjectAppraisedAmountSummary": {	
			"Sum": #####.##,
			"Average": #####.##,
			"Median": #####.##,
			"Mininum": #####.##,
			"Maximum": #####.##,
		},
			"InterestRateSummary": {	
			"Sum": #####.##,
			"Average": #####.##,
			"Median": #####.##,
			"Mininum": #####.##,
			"Maximum": #####.##,
		}
	},
	"CA": {
		"LoanAmmountSummary": {	
			"Sum": #####.##,
			"Average": #####.##,
			"Median": #####.##,
			"Mininum": #####.##,
			"Maximum": #####.##,
		},
		"SubjectAppraisedAmountSummary": {	
			"Sum": #####.##,
			"Average": #####.##,
			"Median": #####.##,
			"Mininum": #####.##,
			"Maximum": #####.##,
		},
			"InterestRateSummary": {	
			"Sum": #####.##,
			"Average": #####.##,
			"Median": #####.##,
			"Mininum": #####.##,
			"Maximum": #####.##,
		}
	},
	...
}
