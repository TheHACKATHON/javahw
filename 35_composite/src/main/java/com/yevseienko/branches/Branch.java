package com.yevseienko.branches;

import com.yevseienko.reports.IReport;
import com.yevseienko.reports.MonthReport;

import java.util.*;

public abstract class Branch implements Iterable<Branch>, Iterator<Branch> {
	private final String name;
	private int iteratorIdx = 0;
	protected String type = null;
	private final List<Branch> children = new ArrayList<>();

	public Branch(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public IReport getReport() {
		return new MonthReport(String.format("%s - %s | Отчёт #%d..", type, getName(), new Random().nextInt(1000)));
	}

	public List<IReport> getReports() {
		List<IReport> result = new ArrayList<>();
		result.add(getReport());
		for (Branch b : this) {
			result.add(b.getReport());
		}
		reset();
		// FIXME: перенести ресет в нормальное место, что бы не вызывать руками
		return result;
	}

	public String getStructString() {
		StringBuilder builder = new StringBuilder();
		Map<Class, Integer> map = new HashMap<>();
		int maxTabs = 0;
		maxTabs = formatStructString(builder, getName(), map, this.getClass(), maxTabs);
		for (Branch b : this) {
			maxTabs = formatStructString(builder, b.getName(), map, b.getClass(), maxTabs);
		}
		reset();
		// FIXME: перенести ресет в нормальное место, что бы не вызывать руками
		return builder.toString();
	}

	private int formatStructString(StringBuilder builder, String name, Map<Class, Integer> map, Class type, int maxTabs) {
		if (!map.containsKey(type)) {
			map.put(type, maxTabs++);
		}
		builder.append("\t".repeat(map.get(type))).append("└ ").append(name).append("\n");
		return maxTabs;
	}

	public void addChildren(Branch... branches) {
		children.addAll(Arrays.asList(branches));
	}

	public void removeChildren(Branch... branches) {
		children.removeAll(Arrays.asList(branches));
	}

	private void reset() {
		iteratorIdx = 0;
		for (Branch child : children) {
			child.reset();
		}
	}

	@Override
	public void remove() {
		removeChildren(children.get(iteratorIdx - 1));
	}

	@Override
	public Iterator<Branch> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		if (iteratorIdx < children.size()) {
			return true;
		}
		// TODO: ресет сюда под какое-нибудь условие
		return children.stream().anyMatch(Branch::hasNext);
	}

	@Override
	public Branch next() {
		// что бы сохранить структуру
		if (iteratorIdx > 0) {
			Branch last = children.get(iteratorIdx - 1);
			if (last.hasNext()) {
				return last.next();
			}
		}
		if (iteratorIdx < children.size()) {
			return children.get(iteratorIdx++);
		}
		return null;
	}
}
