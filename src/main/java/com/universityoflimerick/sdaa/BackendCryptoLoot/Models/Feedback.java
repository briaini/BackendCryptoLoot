package com.universityoflimerick.sdaa.BackendCryptoLoot.Models;

import javax.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Integer feedbackscore;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    public Feedback() {
    }

    public boolean hasSufficientFeedbackscore(int score) {
        return feedbackscore >= score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeedbackscore() {
        return feedbackscore;
    }

    public void setFeedbackscore(Integer feedbackscore) {
        this.feedbackscore = feedbackscore;
    }

    public int getUser() {
        return user.getId();
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}
