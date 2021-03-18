
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "originalPriceDays"),@Index(columnList = "capacity")
})
public class Room extends DomainEntity {

	private KindOfRoom				kindOfRoom;
	private Collection<Date>		occupiedDays;
	private Double					originalPriceDays;
	private boolean					kids;
	private Integer					capacity;
	private String					description;
	private Integer					number;
	private String					picture;
	private Collection<Reservation>	reservations;
	private Collection<Services>	services;
	private Offert					offert;
	private Hotel					hotel;


	@NotNull
	@Valid
	public KindOfRoom getKindOfRoom() {
		return this.kindOfRoom;
	}

	public void setKindOfRoom(final KindOfRoom kindOfRoom) {
		this.kindOfRoom = kindOfRoom;
	}

	@ElementCollection
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Collection<Date> getOccupiedDays() {
		return this.occupiedDays;
	}

	public void setOccupiedDays(final Collection<Date> occupiedDays) {
		this.occupiedDays = occupiedDays;
	}

	@NotNull
	@Range(min = 0)
	public Double getOriginalPriceDays() {
		return this.originalPriceDays;
	}

	public void setOriginalPriceDays(final Double originalPriceDays) {
		this.originalPriceDays = originalPriceDays;
	}

	@NotNull
	public boolean isKids() {
		return this.kids;
	}

	public void setKids(final boolean kids) {
		this.kids = kids;
	}

	@NotNull
	@Min(0)
	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final Integer capacity) {
		this.capacity = capacity;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Min(0)
	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(final Integer number) {
		this.number = number;
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
	@Valid
	@OneToMany(mappedBy = "rooms")
	public Collection<Reservation> getReservations() {
		return this.reservations;
	}
	public void setReservations(final Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Valid
	@OneToOne(optional = true, mappedBy = "rooms")
	public Offert getOffert() {
		return this.offert;
	}

	public void setOffert(final Offert offert) {
		this.offert = offert;
	}

	@NotNull
	@Valid
	@ManyToMany(mappedBy = "rooms")
	public Collection<Services> getServices() {
		return this.services;
	}

	public void setServices(final Collection<Services> services) {
		this.services = services;
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
