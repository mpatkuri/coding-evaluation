package com.aa.act.interview.org;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Organization {

	private Position root;
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		if (title.equals(root.getTitle())) {
			Employee employee = new Employee(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE),  person);
			root.setEmployee(Optional.of(employee));
			return Optional.of(root);
		}
		Position p = null;
		hireEmployee(root.getDirectReports(), title, person, p);
		return Optional.ofNullable(p);
	}

	private void hireEmployee(Set<Position> directReports, String title, Name person, Position p1) {

		for (Position p : directReports) {
			if (title.equals(p.getTitle())) {
				Employee employee = new Employee(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE), person);
				p.setEmployee(Optional.of(employee));
				p1 = p;
				return;
			} else {
				hireEmployee(p.getDirectReports(), title, person, p1);
			}
		}
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
