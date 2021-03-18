
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	private String				name;
	private String				surname;
	private String				email;
	private String				phoneNumber;
	private Collection<Message>	messageSent;
	private Collection<Message>	messageReceived;
	private Collection<Folder>	folders;
	private String				postalAdress;
	private UserAccount			userAccount;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotNull
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Pattern(regexp = "^([+-]\\d+\\s+)?(\\([0-9]+\\)\\s+)?([\\d\\w\\s-]+)$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPostalAdress() {
		return this.postalAdress;
	}

	public void setPostalAdress(final String postalAdress) {
		this.postalAdress = postalAdress;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "sender")
	public Collection<Message> getMessageSent() {
		return this.messageSent;
	}

	public void setMessageSent(final Collection<Message> messageSent) {
		this.messageSent = messageSent;
	}
	@NotNull
	@Valid
	@OneToMany(mappedBy = "receiver")
	public Collection<Message> getMessageReceived() {
		return this.messageReceived;
	}

	public void setMessageReceived(final Collection<Message> messageReceived) {
		this.messageReceived = messageReceived;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "actor")
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

}
