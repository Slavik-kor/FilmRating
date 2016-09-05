package by.epam.karotki.film_rating.dao.util;

import java.util.ArrayList;
import java.util.List;

public class Criteria {
	private static final String AND = " AND ";

	List<String> criteriaList = new ArrayList<String>();
	String logical = AND;
	
	public void addCriterion(Operator op, String column,String... value) {
		String exp = "";
		switch(op){
		case EQUAL: exp = column+" = '"+value[0]+"'";
		break;
		case NOT_EQUAL: exp = column+" <> '"+value[0]+"'";
		break;
		case GREATER: exp = column+" > '"+value[0]+"'";
		break;
		case GREATER_OR_EQUAL: exp = column+" >= '"+value[0]+"'";
		break;
		case LESS: exp = column+" < '"+value[0]+"'";
		break;
		case LESS_OR_EQUAL: exp = column+" <= '"+value[0]+"'";
		break;
		case BETWEEN: exp = column+" BETWEEN '"+value[0]+"' AND '"+value[1]+"'";
		break;
		default:
		exp = "";
		}
		criteriaList.add(exp);
	}
	
	public String getClause(){
		String exp = "";
		if(criteriaList.size()>0){
		exp = " WHERE ";
		}
		for(int i = 0;i<criteriaList.size();i++){
			exp = exp+" ("+criteriaList.get(i)+") ";
			if(i<(criteriaList.size()-1))
				exp = exp+logical;
		}
		return exp;
	}
	
	public void setLogical(String log){
		logical = log;
	}
	
	public String getLogical(){
		return logical;
	}

}
