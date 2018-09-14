package com.me.share.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="collect_table")
public class Collect {
	
	
	public Collect(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "collectID", unique=true, nullable = false)
	private long collectID;
	
	
	@ManyToOne
	@JoinColumn(name="fk_contentID")
	private Content collectContent;
	
	@OneToOne
	@JoinColumn(name="fk_personID")
	private User user;


	public long getCollectID() {
		return collectID;
	}

	public void setCollectID(long collectID) {
		this.collectID = collectID;
	}

	public Content getCollectContent() {
		return collectContent;
	}

	public void setCollectContent(Content collectContent) {
		this.collectContent = collectContent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
