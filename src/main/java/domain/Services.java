
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Services extends DomainEntity {

	private String				name;
	private String				imageURL;
	private Collection<Room>	rooms;


	@Column(unique = true)
	@NotBlank
	@NotNull
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Room> getRooms() {
		return this.rooms;
	}

	public void setRooms(final Collection<Room> rooms) {
		this.rooms = rooms;
	}

	@NotBlank
	@URL
	public String getImageURL() {
		return this.imageURL;
	}

	public void setImageURL(final String imageURL) {
		this.imageURL = imageURL;
	}

}
