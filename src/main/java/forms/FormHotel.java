
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Location;
import domain.Terrain;

public class FormHotel {

	private int			id;
	private int			version;
	private Terrain		terrain;
	private String		hotelChain;
	private Integer		stars;
	private Location	location;
	private String		description;
	private String		name;
	private Integer		totalRooms;
	private String		picture;
	private Double		roomPrice;

	private Date		timeIn;
	private Date		timeOut;


	public Terrain getTerrain() {
		return this.terrain;
	}

	public void setTerrain(final Terrain terrain) {
		this.terrain = terrain;
	}

	public String getHotelChain() {
		return this.hotelChain;
	}

	public void setHotelChain(final String hotelChain) {
		this.hotelChain = hotelChain;
	}

	public Integer getStars() {
		return this.stars;
	}

	public void setStars(final Integer stars) {
		this.stars = stars;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getTotalRooms() {
		return this.totalRooms;
	}

	public void setTotalRooms(final Integer totalRooms) {
		this.totalRooms = totalRooms;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public Double getRoomPrice() {
		return this.roomPrice;
	}

	public void setRoomPrice(final Double roomPrice) {
		this.roomPrice = roomPrice;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTimeIn() {
		return this.timeIn;
	}

	public void setTimeIn(final Date timeIn) {
		this.timeIn = timeIn;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTimeOut() {
		return this.timeOut;
	}

	public void setTimeOut(final Date timeOut) {
		this.timeOut = timeOut;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

}
