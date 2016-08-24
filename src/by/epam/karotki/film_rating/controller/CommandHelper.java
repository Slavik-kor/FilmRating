package by.epam.karotki.film_rating.controller;

import java.util.HashMap;
import java.util.Map;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.command.impl.AccountList;
import by.epam.karotki.film_rating.command.impl.AddAuthor;
import by.epam.karotki.film_rating.command.impl.AddFilm;
import by.epam.karotki.film_rating.command.impl.AuthorCard;
import by.epam.karotki.film_rating.command.impl.Authorization;
import by.epam.karotki.film_rating.command.impl.CommentList;
import by.epam.karotki.film_rating.command.impl.FilmCard;
import by.epam.karotki.film_rating.command.impl.Localization;
import by.epam.karotki.film_rating.command.impl.NewFilms;
import by.epam.karotki.film_rating.command.impl.Profile;
import by.epam.karotki.film_rating.command.impl.Registration;
import by.epam.karotki.film_rating.command.impl.SignOut;
import by.epam.karotki.film_rating.command.impl.UnknownCommand;

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
		commands.put(CommandName.ADD_FILM, new AddFilm());
		commands.put(CommandName.ADD_AUTHOR, new AddAuthor());
		commands.put(CommandName.ACCOUNT_LIST, new AccountList());
		commands.put(CommandName.COMMENT_LIST, new CommentList());
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
