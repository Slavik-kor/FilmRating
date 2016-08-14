package by.epam.karotki.film_rating.controller;

import java.util.HashMap;
import java.util.Map;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.command.impl.Authorization;
import by.epam.karotki.film_rating.command.impl.FilmCard;
import by.epam.karotki.film_rating.command.impl.Localization;
import by.epam.karotki.film_rating.command.impl.NewFilms;

public class CommandHelper {

	private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();
	private static final CommandHelper instance = new CommandHelper();

	public CommandHelper() {
		 commands.put(CommandName.AUTHORIZATION, new Authorization());
		 commands.put(CommandName.LOCALIZATION, new Localization());
		 commands.put(CommandName.NEWFILMS, new NewFilms());
		 commands.put(CommandName.FILM_CARD, new FilmCard());
	}

	public Command getCommand(String name) {
		CommandName commandName = CommandName.valueOf(name.toUpperCase());

		Command com = commands.get(commandName);

		return com;
	}
	
	public static CommandHelper getInstance() {
		return instance;
	}

}
