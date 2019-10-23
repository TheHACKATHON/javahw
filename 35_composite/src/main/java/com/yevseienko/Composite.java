package com.yevseienko;

import com.yevseienko.branches.Corporation;
import com.yevseienko.branches.Department;
import com.yevseienko.branches.Unit;
import com.yevseienko.reports.IReport;

import java.util.List;

public class Composite {
	public static void main(String[] args) {
		Corporation ms = new Corporation("Microsoft");
		Department market = new Department("Market");
		Department dev = new Department("Development");
		ms.addChildren(new Unit("HH"), market, dev);
		market.addChildren(new Unit("USA"), new Unit("US"), new Unit("EU"));
		dev.addChildren(new Unit("Game"), new Unit("Office"), new Unit("OS"));

		// структура корпорации
		System.out.println(ms.getStructString());

		// отчёты для dev подразделения
		List<IReport> reports = dev.getReports();
		for (IReport report : reports) {
			System.out.println(report.getReport());
		}
	}
}
