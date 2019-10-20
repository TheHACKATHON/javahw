package com.yevseienko.reports;

public class MonthReport implements IReport {
	private String report;

	public MonthReport(String report) {
		this.report = report;
	}

	@Override
	public String getReport() {
		return report;
	}
}
