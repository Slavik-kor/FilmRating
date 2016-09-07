package by.epam.karotki.film_rating.dao;

public interface Criteria {
	
	void addCriterion(Operator op, String column,String... value);
	
	void addOrderColumn(String col);
	
	String getClause();

	void setLogicalConnect(String logic);
	
	String getLogicalConnect();
	
}
