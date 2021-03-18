
package forms;

import java.util.Collection;
import java.util.Date;

import domain.CreditCard;
import domain.Hotel;
import domain.KindOfRoom;
import domain.Offert;
import domain.Reservation;
import domain.Services;


public class FormRoom {

	private int			id;
	private int			version;
	private KindOfRoom				kindOfRoom;
	private Double					originalPriceDays;
	private Boolean                 checkAutomaticPrice;
	private Double					personalPriceDays;
	private Boolean					kids;
	private Integer					capacity;
	private String					description;
	private Integer					number;
	private String					picture;
	private Collection<String>	services;
	private Hotel					hotel;
	private Integer                 numberOfRooms;
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	
	public KindOfRoom getKindOfRoom() {
		return kindOfRoom;
	}

	
	public void setKindOfRoom(KindOfRoom kindOfRoom) {
		this.kindOfRoom = kindOfRoom;
	}

	
	public Double getOriginalPriceDays() {
		return originalPriceDays;
	}

	
	public void setOriginalPriceDays(Double originalPriceDays) {
		this.originalPriceDays = originalPriceDays;
	}

	
	public Boolean getCheckAutomaticPrice() {
		return checkAutomaticPrice;
	}

	
	public void setCheckAutomaticPrice(Boolean checkAutomaticPrice) {
		this.checkAutomaticPrice = checkAutomaticPrice;
	}

	
	public Boolean getKids() {
		return kids;
	}

	
	public void setKids(Boolean kids) {
		this.kids = kids;
	}

	
	public Integer getCapacity() {
		return capacity;
	}

	
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
	public Integer getNumber() {
		return number;
	}

	
	public void setNumber(Integer number) {
		this.number = number;
	}

	
	public String getPicture() {
		return picture;
	}

	
	public void setPicture(String picture) {
		this.picture = picture;
	}

	
	public Collection<String> getServices() {
		return services;
	}

	
	public void setServices(Collection<String> services) {
		this.services = services;
	}

	

	public Double getPersonalPriceDays() {
		return personalPriceDays;
	}

	public void setPersonalPriceDays(Double personalPriceDays) {
		this.personalPriceDays = personalPriceDays;
	}

	public Integer getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(Integer numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}


}
