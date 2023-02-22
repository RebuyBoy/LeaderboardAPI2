package com.rebuy.service.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_generator")
    @SequenceGenerator(name = "result_generator", sequenceName = "result_sq", allocationSize = 1)
    private int id;
    private int rank;
    private BigDecimal prize;
    private BigDecimal points;
    @ManyToOne()
    @JoinColumn(name = "player_id")
    private Player player;
    @Enumerated(EnumType.STRING)
    private Stake stake;
    @ManyToOne()
    @JoinColumn(name = "date_id")
    private DateLB dateLB;

    public Result() {
    }

    private Result(Builder builder) {
        this.id = builder.id;
        this.rank = builder.rank;
        this.prize = builder.prize;
        this.points = builder.point;
        this.player = builder.player;
        this.stake = builder.stake;
        this.dateLB = builder.date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public BigDecimal getPrize() {
        return prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal point) {
        this.points = point;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stake getStake() {
        return stake;
    }

    public void setStake(Stake stake) {
        this.stake = stake;
    }

    public DateLB getDateLB() {
        return dateLB;
    }

    public void setDateLB(DateLB date) {
        this.dateLB = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result that = (Result) o;
        return rank == that.rank
                && Objects.equals(prize, that.prize)
                && Objects.equals(points, that.points)
                && player.equals(that.player)
                && stake.equals(that.stake)
                && dateLB.equals(that.dateLB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, prize, points, player, stake, dateLB);
    }

    @Override
    public String toString() {
        return "PlayerResult{" +
                "id=" + id +
                ", rank=" + rank +
                ", prize=" + prize +
                ", point=" + points +
                ", player=" + player +
                ", stake=" + stake +
                ", date=" + dateLB +
                '}';
    }

    public static final class Builder {

        private int id;
        private int rank;
        private BigDecimal prize;
        private BigDecimal point;
        private Player player;
        private Stake stake;
        private DateLB date;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder rank(int rank) {
            this.rank = rank;
            return this;
        }

        public Builder prize(BigDecimal prize) {
            this.prize = prize;
            return this;
        }

        public Builder point(BigDecimal point) {
            this.point = point;
            return this;
        }

        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        public Builder stake(Stake stake) {
            this.stake = stake;
            return this;
        }

        public Builder date(DateLB date) {
            this.date = date;
            return this;
        }

        public Result build() {
            return new Result(this);
        }

    }

}
