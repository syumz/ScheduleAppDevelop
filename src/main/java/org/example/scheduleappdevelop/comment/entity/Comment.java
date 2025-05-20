package org.example.scheduleappdevelop.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.scheduleappdevelop.common.entity.BaseEntity;
import org.example.scheduleappdevelop.schedule.entity.Schedule;
import org.example.scheduleappdevelop.user.entity.User;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50, message = "내용은 1자 이상, 50자 이하로 적어주세요.")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(String comment) {
        this.comment = comment;
    }

    public Comment() {

    }

    public void setUser(User user){
        this.user = user;
    }

    public void setSchedule(Schedule schedule){
        this.schedule = schedule;
    }
}


