package org.example.scheduleappdevelop.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.scheduleappdevelop.common.entity.BaseEntity;
import org.example.scheduleappdevelop.user.entity.User;


@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(){

    }

    public Schedule( String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
