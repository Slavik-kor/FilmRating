package by.epam.karotki.film_rating.entity;

import java.io.Serializable;
import java.sql.Date;

public class Author implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDay;
	private String photo;
	private int countryOfBirthId;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public int getCountryOfBirthId() {
		return countryOfBirthId;
	}

	public void setCountryOfBirthId(int countryOfBirthId) {
		this.countryOfBirthId = countryOfBirthId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDay=" + birthDay
				+ ", photo=" + photo + ", countryOfBirthId=" + countryOfBirthId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDay == null) ? 0 : birthDay.hashCode());
		result = prime * result + countryOfBirthId;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
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
		Author other = (Author) obj;
		if (birthDay == null) {
			if (other.birthDay != null)
				return false;
		} else if (!birthDay.equals(other.birthDay))
			return false;
		if (countryOfBirthId != other.countryOfBirthId)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		return true;
	}

	

	

}
