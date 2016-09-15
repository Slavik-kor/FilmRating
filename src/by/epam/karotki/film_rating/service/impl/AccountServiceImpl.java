package by.epam.karotki.film_rating.service.impl;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.Operator;

import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import by.epam.karotki.film_rating.dao.AccountDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.exception.AccountServiceException;
import by.epam.karotki.film_rating.service.exception.AuthServiceException;
import by.epam.karotki.film_rating.service.util.ServiceUtil;

public class AccountServiceImpl implements AccountService {
	private static final String LOGIN = "login";
	private static final String PASSWORD = "pass";
	private static final String FIRST_NAME = "first-name";
	private static final String LAST_NAME = "last-name";
	private static final String BIRTHDAY = "birthday";
	private static final String COUNTRY = "country";
	private static final String EMAIL = "email";
	private static final String ROLE = "role";
	private static final String PHONE_NUMBER = "phone-number";
	private static final String USER = "User";
	private static final String PATH_AVATAR = "images\\avatar\\avatar";
	private static final String JPG = ".jpg";
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String ERROR_MESSAGE_LP = "Wrong login or password!";
	private static final String ERROR_MESSAGE_VAL = "Error during validation!";
	private static final String ERROR_MESSAGE_REG = "error in source during registration";
	private static final String ERROR_MESSAGE_EXIST_LOGIN = "login already exist";
	private static final String ERROR_MESSAGE_EMPTY_LOGIN = "Empty login field!";
	private static final String ERROR_MESSAGE_EMPTY_PAS = "Empty password field!";
	private static final String ERROR_MESSAGE_ACC = "Error during query account list";

	@Override
	public Account autorization(String login, String pass) throws AccountServiceException {
		validateLogPas(login, pass);
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();

		Account account = null;

		try {
			account = aDao.authorization(login, pass);

			if (account == null) {
				throw new AuthServiceException(ERROR_MESSAGE_LP);
			}

		} catch (DaoException e) {

			throw new AccountServiceException(ERROR_MESSAGE_VAL, e);

		}
		return account;
	}

	@Override
	public Account registration(Map<String, String> reqParam, InputStream is) throws AccountServiceException {
		validateParam(reqParam);
		Account account = createAccount(reqParam, is);
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		Account newAccount = null;
		try {
			aDao.addAccount(account);
		} catch (DaoException e) {
			// log
			throw new AccountServiceException(ERROR_MESSAGE_REG, e);
		}

		try {
			newAccount = aDao.getAccountByLogin(reqParam.get(LOGIN));
		} catch (DaoException e) {
			//
			throw new AccountServiceException(ERROR_MESSAGE_REG, e);
		}
		return newAccount;
	}

	private void validateParam(Map<String, String> reqParam) throws AccountServiceException {
		validateLogPas(reqParam.get(LOGIN), reqParam.get(PASSWORD));
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		Account existAccount = null;
		try {
			existAccount = aDao.getAccountByLogin(reqParam.get(LOGIN));
		} catch (DaoException e) {
			throw new AccountServiceException(ERROR_MESSAGE_VAL);
		}
		if (existAccount != null) {
			throw new AccountServiceException(ERROR_MESSAGE_EXIST_LOGIN);
		}
	}

	private void validateLogPas(String login, String pass) throws AuthServiceException {
		if (login == null || login.isEmpty()) {
			throw new AuthServiceException(ERROR_MESSAGE_EMPTY_LOGIN);
		}
		if (pass == null || pass.isEmpty()) {
			throw new AuthServiceException(ERROR_MESSAGE_EMPTY_PAS);
		}
	}

	private Account createAccount(Map<String, String> reqParam, InputStream is) {
		Account account = new Account();
		account.setLogin(reqParam.get(LOGIN));
		account.setPassword(reqParam.get(PASSWORD));
		account.setFirstName(reqParam.get(FIRST_NAME));
		account.setLastName(reqParam.get(LAST_NAME));
		account.setEmail(reqParam.get(EMAIL));
		account.setPhone(reqParam.get(PHONE_NUMBER));
		account.setRole(USER);

		Date birthday = null;
		try {
			birthday = Date.valueOf(reqParam.get(BIRTHDAY));
		} catch (IllegalArgumentException e) {
			birthday = null;
		}
		account.setBirthDay(birthday);

		Integer countryId = null;
		try {
			countryId = Integer.valueOf(reqParam.get(COUNTRY));
		} catch (IllegalArgumentException | NullPointerException e) {
			countryId = null;
		}
		account.setCountryId(countryId);

		String rootPath = reqParam.get(PROJECT_PATH);
		String photoPath = PATH_AVATAR + account.getLogin() + JPG;
		String fullPhotoPath = rootPath + "\\" + photoPath;
		ServiceUtil.saveFromRequestFile(is, fullPhotoPath);
		account.setPhoto(photoPath);
		return account;
	}

	@Override
	public List<Account> getAccountList() throws AccountServiceException {
		List<Account> accountList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		try {
			accountList = aDao.getAccountList();
		} catch (DaoException e) {

			throw new AccountServiceException(ERROR_MESSAGE_ACC, e);

		}
		return accountList;
	}

	@Override
	public Account updateAccount(Map<String, String> reqParam, InputStream is) throws AccountServiceException {

		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		Account account = null;
		String login = reqParam.get(LOGIN);
		try {

			account = aDao.getAccountByLogin(login);
		} catch (DaoException e) {
			throw new AccountServiceException(ERROR_MESSAGE_ACC, e);
		}

		if (account == null) {
			throw new AccountServiceException(ERROR_MESSAGE_ACC);
		}

		account.setLogin(login);
		account.setPassword(reqParam.get(PASSWORD));
		account.setFirstName(reqParam.get(FIRST_NAME));
		account.setLastName(reqParam.get(LAST_NAME));
		account.setEmail(reqParam.get(EMAIL));
		account.setPhone(reqParam.get(PHONE_NUMBER));
		String role = reqParam.get(ROLE);
		if (role != null) {
			account.setRole(role);
		}

		Date birthday = null;
		try {
			birthday = Date.valueOf(reqParam.get(BIRTHDAY));
		} catch (IllegalArgumentException e) {
			birthday = null;
		}
		account.setBirthDay(birthday);

		Integer countryId = null;
		try {
			countryId = Integer.valueOf(reqParam.get(COUNTRY));
		} catch (IllegalArgumentException | NullPointerException e) {
			countryId = null;
		}
		account.setCountryId(countryId);

		if (is != null) {
			String rootPath = reqParam.get(PROJECT_PATH);
			String photoPath = PATH_AVATAR + account.getLogin() + JPG;
			String fullPhotoPath = rootPath + "\\" + photoPath;
			ServiceUtil.saveFromRequestFile(is, fullPhotoPath);
			account.setPhoto(photoPath);
		}

		Account newAccount = null;
		try {
			aDao.updateAccount(account);
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.ACCOUNT_ID, String.valueOf(account.getId()));
			newAccount = aDao.getAccountByCriteria(criteria).get(0);
		} catch (NullPointerException e) {
			newAccount = null;
		} catch (DaoException e) {
			throw new AccountServiceException(ERROR_MESSAGE_ACC, e);
		}

		return newAccount;
	}

	@Override
	public Account getAccountById(int idAccount) throws AccountServiceException {
		Account account = null;
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		try {
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.ACCOUNT_ID, String.valueOf(idAccount));
			account = aDao.getAccountByCriteria(criteria).get(0);
		} catch (DaoException e) {
			throw new AccountServiceException(ERROR_MESSAGE_ACC, e);
		}
		return account;
	}

	@Override
	public void deleteAccount(int id) throws AccountServiceException {

		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();

		try {
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.ACCOUNT_ID, String.valueOf(id));
			Account account = aDao.getAccountByCriteria(criteria).get(0);
			File file = new File(account.getPhoto());
			file.delete();
			aDao.deleteAccountById(id);

		} catch (DaoException e) {

			throw new AccountServiceException(ERROR_MESSAGE_ACC, e);

		}

	}

}
