package com.me.share.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
@PrimaryKeyJoinColumn(name = "personID")
public class User extends Person {

	public User() {

	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	@Column(name = "role")
	private String role;
	
	@Column(name = "userName")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;
	
	@Column(name = "status")
	private String status;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "user_follower_table", joinColumns = { @JoinColumn(name = "fk_personID") }, 
			inverseJoinColumns = {@JoinColumn(name = "fk_followerID") })
	private Set<User> followers = new HashSet<User>();
	
	@OneToMany(mappedBy = "user")
	private Set<Content> contents = new HashSet<Content>();

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}
	
	public Set<Content> getContents() {
		return contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	/*@Override
	public int hashCode() {
		
	    return (int)getPersonID();
	}
	
	@Override
	public boolean equals(Object args){
		
		User u = (User)args;
		
		if(this.getUsername().equals(u.getUsername())){
			return true;
		}
		return false;
	}
*/
}
