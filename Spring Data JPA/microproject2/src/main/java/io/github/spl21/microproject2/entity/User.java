package io.github.spl21.microproject2.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.FilenameFilter;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;

    // constructor
    public User(String name, String email)
    {
        this.name = name;
        this.email = email;
    }
}
