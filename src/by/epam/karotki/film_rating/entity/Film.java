package by.epam.karotki.film_rating.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Film implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String description;
	private double budget;
	private double boxOfficeCash;
	private int audience;
	private Date premierDate;
	private Time duration;
	private String webSite;
	private String poster;
	private String teaser;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public double getBoxOfficeCash() {
		return boxOfficeCash;
	}

	public void setBoxOfficeCash(double boxOfficeCash) {
		this.boxOfficeCash = boxOfficeCash;
	}

	public int getAudience() {
		return audience;
	}

	public void setAudience(int audience) {
		this.audience = audience;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPremierDate() {
		return premierDate;
	}

	public void setPremierDate(Date premierDate) {
		this.premierDate = premierDate;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getTeaser() {
		return teaser;
	}

	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", title=" + title + ", description=" + description + ", budget=" + budget
				+ ", boxOfficeCash=" + boxOfficeCash + ", audience=" + audience + ", premierDate=" + premierDate
				+ ", duration=" + duration + ", webSite=" + webSite + ", poster=" + poster + ", teaser=" + teaser + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + audience;
		long temp;
		temp = Double.doubleToLongBits(boxOfficeCash);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(budget);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + id;
		result = prime * result + ((poster == null) ? 0 : poster.hashCode());
		result = prime * result + ((premierDate == null) ? 0 : premierDate.hashCode());
		result = prime * result + ((teaser == null) ? 0 : teaser.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((webSite == null) ? 0 : webSite.hashCode());
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
		Film other = (Film) obj;
		if (audience != other.audience)
			return false;
		if (Double.doubleToLongBits(boxOfficeCash) != Double.doubleToLongBits(other.boxOfficeCash))
			return false;
		if (Double.doubleToLongBits(budget) != Double.doubleToLongBits(other.budget))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (id != other.id)
			return false;
		if (poster == null) {
			if (other.poster != null)
				return false;
		} else if (!poster.equals(other.poster))
			return false;
		if (premierDate == null) {
			if (other.premierDate != null)
				return false;
		} else if (!premierDate.equals(other.premierDate))
			return false;
		if (teaser == null) {
			if (other.teaser != null)
				return false;
		} else if (!teaser.equals(other.teaser))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (webSite == null) {
			if (other.webSite != null)
				return false;
		} else if (!webSite.equals(other.webSite))
			return false;
		return true;
	}

	
}
