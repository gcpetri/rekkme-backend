package com.rekkme.data.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="REKS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rek {

    @Id
    @GeneratedValue
    @Column(name="REK_ID", columnDefinition = "uuid", updatable=false)
    private UUID rekId;

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

    @Column(name="NUM_LIKES")
    @Formula(value = "(SELECT COUNT(*) FROM LIKES l WHERE l.REK_ID=REK_ID)")
    private Long numLikes;
}
