package com.rekkme.data.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="REKS")
@DynamicUpdate
public class Rek {

    @Id
    @Column(name="REK_ID", updatable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long rekId;

    @Column(name="URL", nullable=false)
    private String url;

    @Column(name="DESCRIPTION", nullable=false)
    private String description;

    @Column(name="WAGER")
    @Min(0)
	private Integer wager;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TO_USER_ID", referencedColumnName="USER_ID")
    private User toUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FROM_USER_ID", referencedColumnName="USER_ID")
    private User fromUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CATEGORY_ID", referencedColumnName="CATEGORY_ID")
    private Category category;

    @Column(name="CREATED_ON", updatable=false)
    private LocalDateTime createdOn;

    @Column(name="TITLE")
    private String title;

    @Column(name="IMAGE_URL")
    private String imageUrl;

    @Column(name="ARTIST")
    private String artist;

    @Column(name="LOCATION")
    private String location;

    @OneToOne(mappedBy="rek",
        targetEntity=RekResult.class,
        cascade=CascadeType.ALL,
        orphanRemoval=true,
        fetch=FetchType.LAZY)
    private RekResult rekResult;

    @OneToMany(mappedBy="rek",
        targetEntity=Comment.class,
        cascade=CascadeType.ALL,
        orphanRemoval=true)
    private List<Comment> comments;

    @ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinTable(name="REK_TAG_LINKS",
        joinColumns=@JoinColumn(name="REK_ID", referencedColumnName="REK_ID"),
        inverseJoinColumns=@JoinColumn(name="TAG_ID", referencedColumnName="TAG_ID"))
    private Set<Tag> tags = new HashSet<>();

    public Long getRekId() {
        return this.rekId;
    }

    public void setRekId(Long rekId) {
        this.rekId = rekId;
    }

    public Integer getWager() {
        return this.wager;
    }

    public void setWager(Integer wager) {
        this.wager = wager;
    }

    public User getToUser() {
        return this.toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return this.fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public RekResult getRekResult() {
        return this.rekResult;
    }

    public void setRekResult(RekResult rekResult) {
        this.rekResult = rekResult;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
