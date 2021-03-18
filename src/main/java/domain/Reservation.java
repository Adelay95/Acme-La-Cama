
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "checkIn"),@Index(columnList = "checkOut")
})
public class Reservation extends DomainEntity {

	private KindOfOffert	kindOfOffert;
	private Date			checkIn;
	private Date			checkOut;
	private Double			priceDay;
	private Client			client;
	private Bill			bill;
	private Room			rooms;

	private Integer			numDays;


	@NotNull
	@Range(min = 1)
	public Integer getNumDays() {
		return this.numDays;
	}

	public void setNumDays(final Integer numDays) {
		this.numDays = numDays;
	}

	@NotNull
	@Valid
	public KindOfOffert getKindOfOffert() {
		return this.kindOfOffert;
	}

	public void setKindOfOffert(final KindOfOffert kindOfOffert) {
		this.kindOfOffert = kindOfOffert;
	}

	@NotNull
	@Min(0)
	public Double getPriceDay() {
		return this.priceDay;
	}

	public void setPriceDay(final Double priceDay) {
		this.priceDay = priceDay;
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

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Client getClient() {
		return this.client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	@Valid
	@OneToOne(optional = true)
	public Bill getBill() {
		return this.bill;
	}

	public void setBill(final Bill bill) {
		this.bill = bill;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Room getRooms() {
		return this.rooms;
	}

	public void setRooms(final Room rooms) {
		this.rooms = rooms;
	}

}
