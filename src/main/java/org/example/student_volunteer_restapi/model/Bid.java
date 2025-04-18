package org.example.student_volunteer_restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "bids")
public class Bid {

    public Bid() {
        this.creationDate = LocalDateTime.now();
        this.markedDelete = false;
        this.statusBid = StatusBid.IN_PROGRESS;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    User user;

    @Column(name = "creation_date", nullable = false)
    LocalDateTime creationDate;

    @OneToMany(mappedBy = "bid", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Schedule> schedules;

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        schedule.setBid(this);
    }

    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        schedule.setBid(null);
    }

    @Column(name = "marked_delete")
    boolean markedDelete;

    @Column(name = "status_bid", nullable = false)
    StatusBid statusBid;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Bid bid = (Bid) o;
        return getId() != null && Objects.equals(getId(), bid.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
