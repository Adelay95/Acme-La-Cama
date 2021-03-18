
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "province"),@Index(columnList = "population")
})
public class Location {

	private String	gpsCoords;
	private String	population;
	private String	province;


	public Location() {
	}

	@NotBlank
	@NotNull
	public String getPopulation() {
		return this.population;
	}

	public void setPopulation(final String population) {
		this.population = population;
	}

	@NotBlank
	@NotNull
	public String getProvince() {
		return this.province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	@Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
	public String getGpsCoords() {
		return this.gpsCoords;
	}

	public void setGpsCoords(final String gpsCoords) {
		this.gpsCoords = gpsCoords;
	}

}
