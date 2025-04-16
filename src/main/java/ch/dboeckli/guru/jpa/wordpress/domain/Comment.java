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
@Table(name = "wp_comments", indexes = {
    @Index(name = "comment_post_ID", columnList = "comment_post_ID"),
    @Index(name = "comment_approved_date_gmt",
        columnList = "comment_approved, comment_date_gmt"),
    @Index(name = "comment_date_gmt", columnList = "comment_date_gmt"),
    @Index(name = "comment_parent", columnList = "comment_parent"),
    @Index(name = "comment_author_email", columnList = "comment_author_email")
})
@Getter
@Setter
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_ID")
    private Long id;

    @NotNull  //todo - convert when post is mapped
    @Column(name = "comment_post_ID")
    private Long postId;

    @NotNull
    @Size(max = 255)
    @Column(name = "comment_author", columnDefinition = "tinytext")
    //@Column(name = "comment_author", length = 255)
    private String author;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(name = "comment_author_email", length = 100)
    private String authorEmail;

    @NotNull
    @URL
    @Size(max = 200)
    @Column(name = "comment_author_url", length = 200)
    private String authorUrl;

    @NotNull
    @Size(max = 100)
    @Column(name = "comment_author_IP", length = 100)
    private String authorIp;

    @NotNull
    private Timestamp commentDate;

    @NotNull
    @Column(name = "comment_date_gmt")
    private Timestamp commentDateGmt;

    @Lob
    @NotNull
    @Column(name = "comment_content", columnDefinition = "text")
    private String content;

    @NotNull
    @Column(name = "comment_karma")
    private Integer karma;

    @NotNull
    @Size(max = 20)
    @Column(name = "comment_approved", length = 20)
    private String approved;

    @NotNull
    @Size(max = 255)
    @Column(name = "comment_agent")
    private String agent;

    @NotNull
    @Size(max = 20)
    @Column(length = 20)
    private String commentType;

    @NotNull
    @Column(name = "comment_parent")
    private Long commentParent;

    @OneToMany(mappedBy = "comment")
    @ToString.Exclude
    private Set<CommentMeta> commentMetaSet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
