package org.example.scheduleappdevelop.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.example.scheduleappdevelop.common.entity.BaseEntity;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "이름은 필수값입니다.")
    @Column(nullable = false)
    private String username;

    @Email
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$")
    @NotBlank(message = "이메일은 필수값입니다.")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Column(nullable = false)
    private String password;

    public User(){
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String password){
        this.password = password;
    }

}
