package ch.dboeckli.guru.jpa.wordpress.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "wp_usermeta")
@Getter
@Setter
@ToString
public class UserMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "umeta_id")
    private Long id;

    @ManyToOne
    private User user;

    @Size(max = 255)
    private String metaKey;

    @Lob
    private String metaValue;

}
