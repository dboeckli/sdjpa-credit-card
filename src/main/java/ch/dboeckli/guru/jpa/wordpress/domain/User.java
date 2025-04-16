package ch.dboeckli.guru.jpa.wordpress.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "wp_users", indexes = {
    @Index(name = "user_login_key", columnList = "user_login"),
    @Index(name = "user_nicename", columnList = "user_nicename"),
    @Index(name = "user_email", columnList = "user_email")
}) // hibernate would automatically create indexes for these columns, if hibernae is configured to generate the schema definitions
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_login", length = 60)
    @NotNull
    @Size(max = 60)
    private String login;

    @Column(name = "user_pass", length = 255)
    @NotNull
    @Size(max = 255)
    private String password;

    @Column(name = "user_nicename", length = 50)
    @NotNull
    @Size(max = 50)
    private String nicename;

    @Column(name = "user_email", length = 100)
    @Email
    @NotNull
    @Size(max = 100)
    private String email;

    @Column(name = "user_url", length = 100)
    @URL
    @NotNull
    @Size(max = 100)
    private String url;

    @Column(name = "user_registered")
    @NotNull
    private Timestamp registered;

    @Column(name = "user_activation_key", length = 255)
    @NotNull
    @Size(max = 255)
    private String activationKey;

    @Column(name = "user_status")
    @NotNull
    private Integer status;

    @Column(name = "display_name", nullable = false, length = 255)
    // or @Basic(optional = false) for same purpose as @Column
    @NotNull
    @Size(max = 255)
    private String displayName;

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Set<UserMeta> userMetaSet;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Comment> comments;
}
