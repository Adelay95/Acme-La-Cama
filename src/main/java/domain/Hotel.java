
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "name"),@Index(columnList = "terrain")
})
public class Hotel extends DomainEntity {

	private Terrain						terrain;
	private String						hotelChain;
	private Integer						stars;
	private Location					location;
	private String						description;
	private String						name;
	private Integer						totalRooms;
	private String						picture;
	private Double						roomPrice;
	private Request						request;
	private Collection<Worker>			workers;
	private Collection<OptionalTrip>	optionalTrips;
	private Collection<Comment>			comments;
	private Collection<Room>			rooms;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "hotel")
	public Collection<Worker> getWorkers() {
		return this.workers;
	}

	public void setWorkers(final Collection<Worker> workers) {
		this.workers = workers;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "hotel")
	public Collection<OptionalTrip> getOptionalTrips() {
		return this.optionalTrips;
	}

	public void setOptionalTrips(final Collection<OptionalTrip> optionalTrips) {
		this.optionalTrips = optionalTrips;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "hotel")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "hotel")
	public Collection<Room> getRooms() {
		return this.rooms;
	}

	public void setRooms(final Collection<Room> rooms) {
		this.rooms = rooms;
	}

	@Valid
	@OneToOne(optional = true)
	public Request getRequest() {
		return this.request;
	}

	public void setRequest(final Request request) {
		this.request = request;
	}

	@NotNull
	@Valid
	public Terrain getTerrain() {
		return this.terrain;
	}

	public void setTerrain(final Terrain terrain) {
		this.terrain = terrain;
	}

	@NotBlank
	@NotNull
	public String getHotelChain() {
		return this.hotelChain;
	}

	public void setHotelChain(final String hotelChain) {
		this.hotelChain = hotelChain;
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
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	@NotNull
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@Range(min = 0)
	public Integer getTotalRooms() {
		return this.totalRooms;
	}

	public void setTotalRooms(final Integer totalRooms) {
		this.totalRooms = totalRooms;
	}

	@URL
	@NotBlank
	@NotNull
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}
	@NotNull
	@Range(min = 0)
	public Double getRoomPrice() {
		return this.roomPrice;
	}

	public void setRoomPrice(final Double roomPrice) {
		this.roomPrice = roomPrice;
	}

}
