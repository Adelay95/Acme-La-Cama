
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Bill extends DomainEntity {

	private double					totalAmount;
	private boolean					paid;
	private Offert					offert;
	private Collection<BillLine>	billLines;
	private Reservation				reservation;


	@NotNull
	@Range(min = 0)
	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(final double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@NotNull
	public boolean getPaid() {
		return this.paid;
	}

	public void setPaid(final boolean paid) {
		this.paid = paid;
	}

	@Valid
	@OneToOne
	public Offert getOffert() {
		return this.offert;
	}

	public void setOffert(final Offert offert) {
		this.offert = offert;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<BillLine> getBillLines() {
		return this.billLines;
	}

	public void setBillLines(final Collection<BillLine> billLines) {
		this.billLines = billLines;
	}

	@Valid
	@OneToOne
	public Reservation getReservation() {
		return this.reservation;
	}

	public void setReservation(final Reservation reservation) {
		this.reservation = reservation;
	}

}
