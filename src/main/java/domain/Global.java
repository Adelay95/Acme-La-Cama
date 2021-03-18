
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Global extends DomainEntity {

	private Season	season;
	private Double	requestPriceDay;


	@NotNull
	@Range(min = 0)
	public Double getRequestPriceDay() {
		return this.requestPriceDay;
	}

	public void setRequestPriceDay(final Double requestPriceDay) {
		this.requestPriceDay = requestPriceDay;
	}

	@NotNull
	@Valid
	public Season getSeason() {
		return this.season;
	}

	public void setSeason(final Season season) {
		this.season = season;
	}

}
