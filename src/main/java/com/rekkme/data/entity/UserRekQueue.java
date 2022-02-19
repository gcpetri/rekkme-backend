package com.rekkme.data.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="USER_REK_QUEUES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRekQueue {
    
    @Id
    @GeneratedValue
    @Column(name="USER_REK_QUEUE_ID", columnDefinition = "uuid", updatable=false)
    private UUID userRekQueueId;

    @Column(name="QUEUE_ORDER")
    @Min(0)
    private float queueOrder = 0.0f;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REK_ID", referencedColumnName="REK_ID")
    private Rek rek;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
    private User user;
}
