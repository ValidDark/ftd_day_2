package com.cooksys.collections.assignment;

import com.cooksys.collections.Employee;
import com.cooksys.collections.ManagementHierarchy;
import com.cooksys.collections.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConcreteManagementHierarchy implements ManagementHierarchy
{
	Set<Manager> manSet = new HashSet<>();
	Set<Employee> empSet = new HashSet<>();

	/**
	 * Adds a given manager to the hierarchy. If the manager is already included
	 * in the hierarchy, return false and leave the hierarchy unchanged.
	 * Otherwise, incl0ude the manager in the hierarchy and return true.
	 *
	 * @param manager
	 *            the manager to add to the hierarchy.
	 * @return true if the manager was successfully added to the hierarchy,
	 *         false otherwise.
	 */
	@Override
	public boolean addManager(Manager manager)
	{
		return manSet.contains(manager) ? false : manSet.add(manager);
	}

	/**
	 * Retrieves a set of all managers already included in the hierarchy.
	 *
	 * @return a set of the managers already added to the hierarchy. If no
	 *         managers have been added yet, returns an empty set.
	 */
	@Override
	public Set<Manager> getManagers()
	{
		return manSet;
	}

	/**
	 * Check if the given manager has been added to the hierarchy.
	 *
	 * @param manager
	 *            the manager to check for presence in the hierarchy.
	 * @return true if the manager is present in the hierarchy, false otherwise.
	 */
	@Override
	public boolean hasManager(Manager manager)
	{
		return manSet.contains(manager);
	}

	/**
	 * Given a manager, retrieve the set of employees who report directly to
	 * that manager.
	 *
	 * @param manager
	 *            the manager whose underlings are to be retrieved.
	 * @return a set of employees in the hierarchy for which the given manager
	 *         is their direct superior. If the given manager is not present in
	 *         the hierarchy, returns an empty set.
	 */
	@Override
	public Set<Employee> getUnderlings(Manager manager)
	{
		Set<Employee> hisSet = new HashSet<>();

		for (Employee e : empSet)
		{
			if (e.getManager() == manager)
			{
				hisSet.add(e);
			}
		}
		return hisSet;
	}

	/**
	 * Adds a given employee to the hierarchy. If the employee's manager is
	 * present in the hierarchy, include the employee and return true.
	 * Otherwise, return false and leave the hierarchy unchanged.
	 *
	 * @param employee
	 *            the employee to add to the hierarchy
	 * @return true if the employee was added successfully, false otherwise.
	 */
	@Override
	public boolean addEmployee(Employee employee)
	{
		return manSet.contains(employee.getManager()) && !empSet.contains(employee) ? empSet.add(employee) : false;
	}

	/**
	 * @return the set of all employees in the hierarchy
	 */
	@Override
	public Set<Employee> getEmployees()
	{
		Set<Employee> allEmp = new HashSet<>();
		allEmp.addAll(empSet);
		allEmp.addAll(manSet);
		return allEmp;
	}

	/**
	 * Check if the given employee is present in the hierarchy. If the employee
	 * has been added as a manager or as an employee, return true. If not,
	 * return false.
	 *
	 * @param employee
	 *            the employee to check for presence in the hierarchy
	 * @return true if the employee is present in the hierarchy, false
	 *         otherwise.
	 */
	@Override
	public boolean hasEmployee(Employee employee)
	{
		return !(!manSet.contains(employee) && !empSet.contains(employee));
	}

	/**
	 * Returns a representation of the management hierarchy as a map of managers
	 * to sets of employees, representing direct management relationships, i.e.
	 * all employees in a given manager's set should have that manager in its
	 * manager field.
	 *
	 * @return the entire management hierarchy as a map from managers to the set
	 *         of employees directly under their supervision.
	 */
	@Override
	public Map<Manager, Set<Employee>> getHierarchy()
	{
		Map<Manager, Set<Employee>> thisMap = new HashMap<Manager, Set<Employee>>();

		for (Manager m : manSet)
		{
			Set<Employee> hisSet = getUnderlings(m);
			thisMap.put(m, hisSet);	
		}
		return thisMap;
	}

	/**
	 * Returns the chain of command for a given employee, represented by a list
	 * of managers. The first element of the list should be the given employee's
	 * direct manager, the second element should be that manager's direct
	 * manager, and so on.
	 *
	 * @param employee
	 *            the employee whose chain of command is to be retrieved
	 * @return a list of managers that represents the chain of command for the
	 *         given employee. If the employee is not present in the hierarchy,
	 *         returns an empty list.
	 */
	@Override
	public List<Manager> getChainOfCommand(Employee employee)
	{
		if (getEmployees().contains(employee))
		{
			ArrayList<Manager> thisList = new ArrayList<>();
			findCod(employee, thisList);
			return thisList;
		} else
		{
			return new ArrayList<>();
		}
	}

	private void findCod(Employee employee, ArrayList<Manager> list)
	{
		if (employee.getManager() != null)
		{
			list.add(employee.getManager());
			findCod(employee.getManager(), list);
		}
	}
}