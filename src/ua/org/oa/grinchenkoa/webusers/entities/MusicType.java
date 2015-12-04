package ua.org.oa.grinchenkoa.webusers.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *  
 * Class describes objects "musictype" stored in database, implements Entity interface,
 * 
 * @author Andrei Grinchenko
 *
 */

@Entity
@Table(name="musictype")
public class MusicType implements ua.org.oa.grinchenkoa.webusers.entities.Entity {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="typeName")
	private String musicType;
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="musicTypes")
	private Set<User> users; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMusicType() {
		return musicType;
	}
	public void setMusicType(String musicType) {
		this.musicType = musicType;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "MusicType [id=" + id + ", musicType=" + musicType + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((musicType == null) ? 0 : musicType.hashCode());
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
		MusicType other = (MusicType) obj;
		if (id != other.id)
			return false;
		if (musicType == null) {
			if (other.musicType != null)
				return false;
		} else if (!musicType.equals(other.musicType))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
}
