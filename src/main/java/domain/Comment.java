
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	private String	tittle;
	private String	text;
	private Integer	stars;
	private Client	client;
	private Hotel	hotel;
	private Date	creationDate;


	@NotBlank
	public String getTittle() {
		return this.tittle;
	}

	public void setTittle(final String tittle) {
		this.tittle = tittle;
	}
	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}
	@NotNull
	@Range(min = 0, max = 5)
	public Integer getStars() {
		return this.stars;
	}

	public void setStars(final Integer stars) {
		this.stars = stars;
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

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Hotel getHotel() {
		return this.hotel;
	}

	public void setHotel(final Hotel hotel) {
		this.hotel = hotel;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

}
