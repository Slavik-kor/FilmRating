package by.epam.karotki.film_rating.entity;

import java.io.Serializable;

public class Rate implements Serializable {

	private static final long serialVersionUID = 1L;
	private int rate;
	private int filmId;
	private int accountId;
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	@Override
	public String toString() {
		return "Rate [rate=" + rate + ", filmId=" + filmId + ", accountId=" + accountId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + filmId;
		result = prime * result + rate;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rate other = (Rate) obj;
		if (accountId != other.accountId)
			return false;
		if (filmId != other.filmId)
			return false;
		if (rate != other.rate)
			return false;
		return true;
	}
	
	
	
}
