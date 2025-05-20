package org.example.scheduleappdevelop.schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "제목은 필수값입니다.")
    @Size(min = 1, max = 15, message = "제목은 1자 이상, 15자 이하로 적어주세요.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "내용은 필수값입니다.")
    @Size(min = 1, max = 50, message = "내용은 1자 이상, 50자 이하로 적어주세요.")
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
