package com.example2.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title = "";

    @Column(name = "content", nullable = false)
    private String content = "";

    @Column(name = "user", nullable = false)
    private String user = "";

    @Column(name = "newdate", nullable = false)
    private String newdate = "";

    @Column(name = "count", nullable = false)
    private String count = "";

    @Column(name = "likecount", nullable = false)
    private String likecount = "";

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @ElementCollection
    @CollectionTable(name = "board_viewed_ips")
    private Set<String> viewedIps = new HashSet<>();

    public Board(String title, String content, String user, String newdate, String count, String likecount) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.newdate = newdate;
        this.count = count;
        this.likecount = likecount;
        this.viewCount = 0;
    }

    public void update(String title, String content, String user, String newdate, String count, String likecount) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.newdate = newdate;
        this.count = count;
        this.likecount = likecount;
    }

    public boolean addViewCount(String ip) {
        if (!viewedIps.contains(ip)) {
            viewedIps.add(ip);
            this.viewCount++;
            return true;
        }
        return false;
    }
}