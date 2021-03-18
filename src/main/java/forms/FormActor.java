
package forms;

import domain.CreditCard;
import domain.Hotel;


public class FormActor {

	private int			id;
	private int			version;
	private String		name;
	private String		surname;
	private String		email;
	private String		phoneNumber;
	private String username;
	private String password;
	private String		password2;
	private Boolean		confirmed;
	private String		postalAdress;
	private CreditCard	creditCard;
	private String dniNif;
	private Hotel hotel;
	private Double salary;
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}


	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPostalAdress() {
		return postalAdress;
	}

	
	public void setPostalAdress(String postalAdress) {
		this.postalAdress = postalAdress;
	}

	
	public CreditCard getCreditCard() {
		return creditCard;
	}

	
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	
	
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDniNif() {
		return dniNif;
	}

	public void setDniNif(String dniNif) {
		this.dniNif = dniNif;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}


}
