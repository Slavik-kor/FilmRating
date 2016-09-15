package by.epam.karotki.film_rating.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.Operator;

public class CriteriaImpl implements Criteria {
	private static final String AND = " AND ";

	List<String> criteriaList = new ArrayList<String>();
	String logical = AND;
	List<String> orderColumns = new ArrayList<String>();
	boolean straightOrder = true;

	public void addCriterion(Operator op, String column, String... value) {
		
		String exp = "";
		switch (op) {
		case EQUAL:
			exp = " ("+column + " = '" + value[0] + "')";
			break;
		case NOT_EQUAL:
			exp = "("+column + " <> '" + value[0] + "')";
			break;
		case GREATER:
			exp = "("+column + " > '" + value[0] + "')";
			break;
		case GREATER_OR_EQUAL:
			exp = "("+column + " >= '" + value[0] + "')";
			break;
		case LESS:
			exp = "("+column + " < '" + value[0] + "')";
			break;
		case LESS_OR_EQUAL:
			exp ="("+column + " <= '" + value[0] + "')";
			break;
		case BETWEEN:
			exp = column + " BETWEEN '" + value[0] + "' AND '" + value[1] + "'";
			break;
		case IN:
			 if(value.length==0){break;}
				exp = column + " IN (";
				
				for (int i = 0; i < value.length; i++) {
					exp += value[i];
					if (i < (value.length - 1)) {
						exp += ",";
					}
				}
				exp += ")";
			break;
		default:
			exp = "";
		}
		if(value.length!=0) {
			criteriaList.add(exp);
			}
		
		
	}

	public String getClause() {
		String exp = "";

		if (criteriaList.size() > 0) {
			exp += " WHERE ";
		}
		for (int i = 0; i < criteriaList.size(); i++) {
			exp = exp + " " + criteriaList.get(i) + " ";
			if (i < (criteriaList.size() - 1))
				exp += logical;
		}
		if (orderColumns.size() > 0) {
			exp += " ORDER BY ";
		}
		for (int i = 0; i < orderColumns.size(); i++) {
			exp = exp + orderColumns.get(i);
			if (i < (orderColumns.size() - 1))
				exp += ",";
		}
		if (!straightOrder) {
			exp += " DESC ";
		}
		return exp;
	}

	public void setLogicalConnect(String log) {
		logical = log;
	}

	public String getLogicalConnect() {
		return logical;
	}

	@Override
	public void addOrderColumn(String col, boolean order) {
		orderColumns.add(col);
		straightOrder = order;
	}

}
