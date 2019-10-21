package com.yevseienko;

import com.yevseienko.branches.Corporation;
import com.yevseienko.branches.Department;
import com.yevseienko.branches.Unit;
import com.yevseienko.reports.IReport;

import java.util.List;

public class Composite {
	public static void main(String[] args) {
		Corporation ms = new Corporation("Microsoft");
		Unit market = new Unit("Market");
		Unit dev = new Unit("Development");
		ms.addChildren(new Unit("HH"), market, dev);
		market.addChildren(new Department("USA"), new Department("US"), new Department("EU"));
		dev.addChildren(new Department("Game"), new Department("Office"), new Department("OS"));

		// структура корпорации
		System.out.println(ms.getStructString());

		// отчёты для dev подразделения
		List<IReport> reports = dev.getReports();
		for (IReport report : reports) {
			System.out.println(report.getReport());
		}
	}
}

