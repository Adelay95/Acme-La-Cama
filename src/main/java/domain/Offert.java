
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "kindOfOffert"),@Index(columnList = "checkIn"),@Index(columnList = "checkOut"),@Index(columnList = "totalPrice")
})
public class Offert extends DomainEntity {

	private KindOfOffert	kindOfOffert;
	private Date			checkIn;
	private Date			checkOut;
	private Double			totalPrice;
	private Client			client;
	private Bill			bill;
	private Room			rooms;


	@NotNull
	@Valid
	public KindOfOffert getKindOfOffert() {
		return this.kindOfOffert;
	}

	public void setKindOfOffert(final KindOfOffert kindOfOffert) {
		this.kindOfOffert = kindOfOffert;
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
	@Min(0)
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(final Double totalPrice) {
		this.totalPrice = totalPrice;
	}


	@Valid
	@ManyToOne(optional = true)
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


	@Valid
	@OneToOne(optional = true)
	public Room getRooms() {
		return this.rooms;
	}

	public void setRooms(final Room rooms) {
		this.rooms = rooms;
	}

}
