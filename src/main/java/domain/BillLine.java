
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class BillLine extends DomainEntity {

	private Double	amount;
	private String	reason;
	private Bill	bill;


	@NotNull
	@Range(min = 0)
	public Double getAmount() {
		return this.amount;
	}
	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	@NotBlank
	@NotNull
	public String getReason() {
		return this.reason;
	}
	public void setReason(final String reason) {
		this.reason = reason;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Bill getBill() {
		return this.bill;
	}
	public void setBill(final Bill bill) {
		this.bill = bill;
	}

}
