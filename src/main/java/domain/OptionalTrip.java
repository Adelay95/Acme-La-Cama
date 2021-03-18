
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class OptionalTrip extends DomainEntity {

	private String				title;
	private String				picture;
	private Date				openingTime;
	private Date				closingTime;
	private Double				price;
	private Location			location;
	private Collection<Date>	tripDays;
	private Hotel				hotel;
	private Collection<Client>	clients;


	@NotNull
	@Valid
	@ManyToMany()
	public Collection<Client> getClients() {
		return this.clients;
	}

	public void setClients(final Collection<Client> clients) {
		this.clients = clients;
	}

	@NotNull
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@URL
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

	@NotNull
	@Range(min = 0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotNull
	@Valid
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}
	@ElementCollection
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Collection<Date> getTripDays() {
		return this.tripDays;
	}

	public void setTripDays(final Collection<Date> tripDays) {
		this.tripDays = tripDays;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(final Hotel hotel) {
		this.hotel = hotel;
	}

}
