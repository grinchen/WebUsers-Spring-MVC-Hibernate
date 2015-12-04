package ua.org.oa.grinchenkoa.webusers.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Class describes objects "adress" stored in database, implements Entity interface,
 * 
 * @author Andrei Grinchenko
 * 
 */

@Entity
@Table(name="adress")
public class Adress implements ua.org.oa.grinchenkoa.webusers.entities.Entity {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="country")
	private String country;
	
	@Column(name="region")
	private String region;
	
	@Column(name="city")
	private String city;
	
	@Column(name="street")
	private String street;

	@Column(name="building")
	private String building;
	
	@Column(name="app")
	private String app;
	
	@OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private User user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
