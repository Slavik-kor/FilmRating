package by.epam.karotki.film_rating.dao;

public class DBColumnName {

	private DBColumnName() {
	}
	
	public static final String ACCOUNT_ID = "idAccount";
	public static final String ACCOUNT_FIRST_NAME = "AccountFirstName";
	public static final String ACCOUNT_LAST_NAME = "AccountLastName";
	public static final String ACCOUNT_BIRTH_DAY = "AccountBirthday";
	public static final String ACCOUNT_EMAIL = "AccountEmail";
	public static final String ACCOUNT_CREATION_DATE = "AccountCreationDate";
	public static final String ACCOUNT_LOGIN = "AccountLogin";
	public static final String ACCOUNT_PASSWORD = "AccountPassword";
	public static final String ACCOUNT_ROLE = "AccountRole";
	public static final String ACCOUNT_IS_ACTIVE = "AccountActive";
	public static final String ACCOUNT_COUNTRY_ID = "Country_id";
	public static final String ACCOUNT_PHONE = "Phone";
	public static final String ACCOUNT_PHOTO = "Photo";
	
	public static final String AUTHOR_ID = "idAuthor";
	public static final String AUTHOR_FIRST_NAME = "AuthorFirstName";
	public static final String AUTHOR_LAST_NAME = "AuthorLastName";
	public static final String AUTHOR_BIRTHDAY = "AuthorsBirthday";
	public static final String AUTHOR_COUNTRY = "CountryOfBirth_id";
	public static final String AUTHOR_PHOTO = "Photo";
	
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
	
	public static final String COMMENT_ACCOUNT_ID = "Account_id";
	public static final String COMMENT_FILM_ID = "Film_id";
	public static final String COMMENT_TEXT = "CommentText";
	public static final String COMMENT_RATE = "Rate";
	public static final String COMMENT_DATE = "CommentDate";
	
	public static final String COUNTRY_ID = "idCountry";
	public static final String COUNTRY_NAME = "CountryName";
	public static final String COUNTRY_CODE = "CountryCode";
	
	public static final String GENRE_ID = "idGenre";
	public static final String GENRE_NAME = "Name";
	public static final String GENRE_DESCRIPTION = "Description";
	
	public static final String FILM_COUNTRY_FILM_ID = "Film_id";
	public static final String FILM_COUNTRY_COUNTRY_ID = "Country_id";
	
	public static final String FILM_GENRE_GENRE_ID = "Genre_id";
	public static final String FILM_GENRE_FILM_ID = "Film_id";
}
