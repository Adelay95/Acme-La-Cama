
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

	private String	bannerUrl;


	@URL
	@NotBlank
	public String getBannerUrl() {
		return this.bannerUrl;
	}
	public void setBannerUrl(final String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

}
