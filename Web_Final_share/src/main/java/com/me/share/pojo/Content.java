package com.me.share.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name = "content_table")
public class Content {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "contentID", unique=true, nullable = false)
	private long contentID;
	
	@Column(name="collectCount")
	private long collectCount;
	
	@Transient
	private CommonsMultipartFile pic;
	
	@Column(name="fileName")
	private String fileName;
	
	@Column(name="artical")
	private String artical;
	
	@Column(name="menuName")
	private String menuName;
	
	@Column(name="category")
	private String category;
	
	@Column(name="uploadDate")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date uploadDate;
	
	@ManyToOne
	@JoinColumn(name="fk_personID")
	private User user;
	
	@OneToMany(mappedBy="collectContent")
	private Set<Collect> collects = new HashSet<Collect>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="commentonContent")
	private Set<Comment> comments = new HashSet<Comment>();
	
	public Date getUploadDate() {
		return uploadDate;
	}

	public String getArtical() {
		return artical;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setArtical(String artical) {
		this.artical = artical;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}


	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}


	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getContentID() {
		return contentID;
	}

	public void setContentID(long contentID) {
		this.contentID = contentID;
	}

	public long getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(long collectCount) {
		this.collectCount = collectCount;
	}

	public Set<Collect> getCollects() {
		return collects;
	}

	public void setCollects(Set<Collect> collects) {
		this.collects = collects;
	}

	public CommonsMultipartFile getPic() {
		return pic;
	}

	public void setPic(CommonsMultipartFile pic) {
		this.pic = pic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
