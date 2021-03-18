
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	private String	holderName;
	private Brand	brandName;
	private String	accountNumber;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private Integer	cVV;


	public CreditCard() {
	}

	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}
	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotNull
	@Valid
	public Brand getBrandName() {
		return this.brandName;
	}
	public void setBrandName(final Brand brandName) {
		this.brandName = brandName;
	}

	@NotBlank
	@CreditCardNumber
	public String getAccountNumber() {
		return this.accountNumber;
	}
	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}
	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	@Range(min = 0)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}
	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getcVV() {
		return this.cVV;
	}
	public void setcVV(final Integer cVV) {
		this.cVV = cVV;
	}

}
