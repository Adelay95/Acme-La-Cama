
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Hotel;
import domain.Location;

public class FormTrip {

	private int			id;
	private int			version;
	private String		title;
	private String		picture;
	private Date		openingTime;
	private Date		closingTime;
	private Double		price;
	private Location	location;
	private Hotel		hotel;

	private Date		checkIn;
	private Date		checkOut;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getOpeningTime() {
		return this.openingTime;
	}

	public void setOpeningTime(final Date openingTime) {
		this.openingTime = openingTime;
	}
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getClosingTime() {
		return this.closingTime;
	}

	public void setClosingTime(final Date closingTime) {
		this.closingTime = closingTime;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(final Hotel hotel) {
		this.hotel = hotel;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckIn() {
		return this.checkIn;
	}

	public void setCheckIn(final Date checkIn) {
		this.checkIn = checkIn;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCheckOut() {
		return this.checkOut;
	}

	public void setCheckOut(final Date checkOut) {
		this.checkOut = checkOut;
	}

}
