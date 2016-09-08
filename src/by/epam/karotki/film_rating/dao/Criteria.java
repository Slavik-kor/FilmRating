package by.epam.karotki.film_rating.dao;

public interface Criteria {
	
	void addCriterion(Operator op, String column,String... value);
	
	/**
	 * 
	 * @param col - Name of order column
	 * @param inverse - parameter indicating straight or inverse order:
	 * true - from smallest to largest
	 * false - from largest to smallest
	 */
	void addOrderColumn(String col,boolean order);
	
	String getClause();

	void setLogicalConnect(String logic);
	
	String getLogicalConnect();
	
}
