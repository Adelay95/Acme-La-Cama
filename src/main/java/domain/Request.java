
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "state"),@Index(columnList = "timeIn"),@Index(columnList = "timeOut")
})
public class Request extends DomainEntity {

	private Double	price;
	private State	state;
	private Date	timeIn;
	private Date	timeOut;
	private Manager	manager;
	private Hotel	hotel;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}


	@Valid
	@OneToOne(optional = true)
	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(final Hotel hotel) {
		this.hotel = hotel;
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
	public State getState() {
		return this.state;
	}

	public void setState(final State state) {
		this.state = state;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTimeIn() {
		return this.timeIn;
	}

	public void setTimeIn(final Date timeIn) {
		this.timeIn = timeIn;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTimeOut() {
		return this.timeOut;
	}

	public void setTimeOut(final Date timeOut) {
		this.timeOut = timeOut;
	}
}
