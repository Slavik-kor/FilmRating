package by.epam.karotki.film_rating.dao;

public class DBColumnNames {

	private DBColumnNames() {
	}
	
	public static final String ACCOUNT_FIRST_NAME = "AccountFirstName";
	public static final String ACCOUNT_LAST_NAME = "AccountLastName";
	public static final String ACCOUNT_BIRTH_DAY = "AccountBirthday";
	public static final String ACCOUNT_EMAIL = "AccountEmail";
	public static final String ACCOUNT_CREATION_DATE = "AccountCreationDate";
	public static final String ACCOUNT_LOGIN = "AccountLogin";
	public static final String ACCOUNT_PASSWORD = "AccountPassword";
	public static final String ACCOUNT_ROLE = "AccountRole";
	public static final String ACCOUNT_IS_ACTIVE = "AccountActive";
	public static final String ACCOUNT_CITY_ID = "City_id";
	
	public static final String AUTHOR_FIRST_NAME = "AuthorFirstName";
	public static final String AUTHOR_LAST_NAME = "AuthorLastName";
	
	public static final String FILM_ID = "idFilm";
	public static final String FILM_TITLE = "Title";
	public static final String FILM_DESCRIPTION = "Description";
	public static final String FILM_BUDGET = "Budget";
	public static final String FILM_BOX_OFFICE_CASH = "BoxOfficeCash";
	public static final String FILM_AUDIENCE = "Audience";
	public static final String FILM_PREMIER_DATE = "PremierDate";
	public static final String FILM_DURATION = "Duration";
	public static final String FILM_SITE = "WebSite";
	public static final String FILM_POSTER = "Poster";
	public static final String FILM_TEASER = "Teaser";
	
	public static final String COUNTRY_ID = "idCountry";
	public static final String COUNTRY_NAME = "CountryName";
	public static final String COUNTRY_CODE = "CountryCode";
}
