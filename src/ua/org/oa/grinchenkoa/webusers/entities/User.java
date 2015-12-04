
package ua.org.oa.grinchenkoa.webusers.entities;

import java.sql.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 
 * Class describes objects "user" stored in database, implements Entity interface,
 * 
 * @author Andrei Grinchenko
 * 
 * 
 */


@Entity
@Table(name="user")
public class User implements Comparable<User>, ua.org.oa.grinchenkoa.webusers.entities.Entity{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;
	
	@Column(name="birthDate")
	private Date birthDate;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="id_role", referencedColumnName = "id", nullable = false)
	private Role role;
	
	@OneToOne(mappedBy = "user")
	private Adress adress;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="usermusictype", 
			joinColumns={@JoinColumn(name="id_user")}, 
			inverseJoinColumns={@JoinColumn(name="id_musicType")})
	private Set<MusicType> musicTypes; 
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	
	
	public Set<MusicType> getMusicTypes() {
		return musicTypes;
	}

	public void setMusicTypes(Set<MusicType> musicTypes) {
		this.musicTypes = musicTypes;
	}

	
	@Override
	public String toString() {
		return "id: " + id + "\nlogin: " + login + "\npassword: " + password + "\nbirthDate: " + birthDate
			+ "\ne-mail: " + email + musicTypes + "\n";
	}

	@Override
	public int compareTo(User user) {
		if (this.id > user.id)
			return 1;
		else if (this.id == user.id)
			return 0;
			else 
				return -1;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (musicTypes == null) {
			if (other.musicTypes != null)
				return false;
		} else if (!musicTypes.equals(other.musicTypes))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
}
