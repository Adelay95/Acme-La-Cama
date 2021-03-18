
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Client extends Actor {

	private CreditCard				creditCard;
	private String					dniNif;
	private Finder					finder;
	private Collection<Comment>		comments;
	private Collection<Offert>		offerts;
	private Collection<Reservation>	reservations;


	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	

	@Valid
	@OneToOne
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "client")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "client")
	public Collection<Offert> getOfferts() {
		return this.offerts;
	}

	public void setOfferts(final Collection<Offert> offerts) {
		this.offerts = offerts;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "client")
	public Collection<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(final Collection<Reservation> reservations) {
		this.reservations = reservations;
	}


	@Column(unique=true)
	@NotBlank
	@NotNull
	@Pattern(regexp = "^(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))$")
	public String getDniNif() {
		return dniNif;
	}

	public void setDniNif(String dniNif) {
		this.dniNif = dniNif;
	}

}
