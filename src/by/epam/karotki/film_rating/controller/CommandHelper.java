package by.epam.karotki.film_rating.controller;

import java.util.HashMap;
import java.util.Map;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.command.impl.AccountList;
import by.epam.karotki.film_rating.command.impl.AddAuthor;
import by.epam.karotki.film_rating.command.impl.AddAuthorPage;
import by.epam.karotki.film_rating.command.impl.AddComment;
import by.epam.karotki.film_rating.command.impl.AddFilm;
import by.epam.karotki.film_rating.command.impl.AddFilmPage;
import by.epam.karotki.film_rating.command.impl.AuthorCard;
import by.epam.karotki.film_rating.command.impl.Authorization;
import by.epam.karotki.film_rating.command.impl.CommentList;
import by.epam.karotki.film_rating.command.impl.DeleteAccount;
import by.epam.karotki.film_rating.command.impl.DeleteAuthor;
import by.epam.karotki.film_rating.command.impl.DeleteComment;
import by.epam.karotki.film_rating.command.impl.DeleteFilm;
import by.epam.karotki.film_rating.command.impl.FilmCard;
import by.epam.karotki.film_rating.command.impl.FilmFavorite;
import by.epam.karotki.film_rating.command.impl.FilmsByGenre;
import by.epam.karotki.film_rating.command.impl.FilmsByRating;
import by.epam.karotki.film_rating.command.impl.FilmsByYear;
import by.epam.karotki.film_rating.command.impl.Localization;
import by.epam.karotki.film_rating.command.impl.NewFilms;
import by.epam.karotki.film_rating.command.impl.UpdateProfilePage;
import by.epam.karotki.film_rating.command.impl.Profile;
import by.epam.karotki.film_rating.command.impl.Registration;
import by.epam.karotki.film_rating.command.impl.RegistrationPage;
import by.epam.karotki.film_rating.command.impl.SignOut;
import by.epam.karotki.film_rating.command.impl.UnknownCommand;
import by.epam.karotki.film_rating.command.impl.UpdateAccount;
import by.epam.karotki.film_rating.command.impl.UpdateComment;
import by.epam.karotki.film_rating.command.impl.UpdateFilm;
import by.epam.karotki.film_rating.command.impl.UpdateFilmPage;

public class CommandHelper {

	private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();
	private static final CommandHelper instance = new CommandHelper();

	public CommandHelper() {
		commands.put(CommandName.AUTHORIZATION, new Authorization());
		commands.put(CommandName.REGISTRATION, new Registration());
		commands.put(CommandName.LOCALIZATION, new Localization());
		commands.put(CommandName.NEWFILMS, new NewFilms());
		commands.put(CommandName.FILM_CARD, new FilmCard());
		commands.put(CommandName.AUTHOR_CARD, new AuthorCard());
		commands.put(CommandName.SIGN_OUT, new SignOut());
		commands.put(CommandName.UNKNOWN_COMMAND, new UnknownCommand());
		commands.put(CommandName.PROFILE, new Profile());
		commands.put(CommandName.ADD_FILM_PAGE, new AddFilmPage());
		commands.put(CommandName.ADD_AUTHOR_PAGE, new AddAuthorPage());
		commands.put(CommandName.ACCOUNT_LIST, new AccountList());
		commands.put(CommandName.COMMENT_LIST, new CommentList());
		commands.put(CommandName.ADD_COMMENT, new AddComment());
		commands.put(CommandName.FILMS_BY_GENRE, new FilmsByGenre());
		commands.put(CommandName.UPDATE_ACCOUNT_PAGE, new UpdateProfilePage());
		commands.put(CommandName.UPDATE_ACCOUNT, new UpdateAccount());
		commands.put(CommandName.REG_PAGE, new RegistrationPage());
		commands.put(CommandName.FILMS_BY_YEAR, new FilmsByYear());
		commands.put(CommandName.FILMS_BY_RATE, new FilmsByRating());
		commands.put(CommandName.FILM_FAVORITE, new FilmFavorite());
		commands.put(CommandName.UPDATE_COMMENT, new UpdateComment());
		commands.put(CommandName.DELETE_ACCOUNT, new DeleteAccount());
		commands.put(CommandName.DELETE_COMMENT, new DeleteComment());
		commands.put(CommandName.ADD_FILM, new AddFilm());
		commands.put(CommandName.ADD_AUTHOR, new AddAuthor());
		commands.put(CommandName.DELETE_AUTHOR, new DeleteAuthor());
		commands.put(CommandName.UPDATE_FILM, new UpdateFilm());
		commands.put(CommandName.DELETE_FILM, new DeleteFilm());
		commands.put(CommandName.UPDATE_FILM_PAGE, new UpdateFilmPage());
	}

	public Command getCommand(String name) {
		CommandName commandName = null;
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			commandName = CommandName.UNKNOWN_COMMAND;
		}
		Command com = commands.get(commandName);
		return com;
	}

	public static CommandHelper getInstance() {
		return instance;
	}

}
