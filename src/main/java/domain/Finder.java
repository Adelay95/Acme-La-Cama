
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private Double			minimumPrice;
	private Double			maximumPrice;
	private Date			checkIn;
	private Date			checkOut;
	private KindOfRoom		kindOfRoom;
	private Terrain			terrain;
	private KindOfOffert	kindOfOffert;
	private Integer			capacity;
	private String			population;
	private String			province;
	private String			hotelName;
	private Client			client;


	@Min(0)
	public Double getMinimumPrice() {
		return this.minimumPrice;
	}

	public void setMinimumPrice(final Double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	@Min(0)
	public Double getMaximumPrice() {
		return this.maximumPrice;
	}

	public void setMaximumPrice(final Double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCheckIn() {
		return this.checkIn;
	}

	public String getPopulation() {
		return this.population;
	}

	public void setPopulation(final String population) {
		this.population = population;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	public void setCheckIn(final Date checkIn) {
		this.checkIn = checkIn;
	}

	@Valid
	public KindOfRoom getKindOfRoom() {
		return this.kindOfRoom;
	}

	public void setKindOfRoom(final KindOfRoom kindOfRoom) {
		this.kindOfRoom = kindOfRoom;
	}

	@Valid
	public Terrain getTerrain() {
		return this.terrain;
	}

	public void setTerrain(final Terrain terrain) {
		this.terrain = terrain;
	}

	@Valid
	public KindOfOffert getKindOfOffert() {
		return this.kindOfOffert;
	}

	public void setKindOfOffert(final KindOfOffert kindOfOffert) {
		this.kindOfOffert = kindOfOffert;
	}

	public String getHotelName() {
		return this.hotelName;
	}

	public void setHotelName(final String hotelName) {
		this.hotelName = hotelName;
	}

	@Valid
	@OneToOne(optional = false)
	public Client getClient() {
		return this.client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCheckOut() {
		return this.checkOut;
	}

	public void setCheckOut(final Date checkOut) {
		this.checkOut = checkOut;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final Integer capacity) {
		this.capacity = capacity;
	}

}
