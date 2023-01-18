package com.rebuy.api.scope.dto.response;

import java.math.BigDecimal;
import java.util.Objects;

public class ResultResponse {
    private String name;
    private BigDecimal points;
    private int rank;
    private BigDecimal prize;
    //TODO builder

    public ResultResponse() {
    }

    public ResultResponse(String name, BigDecimal points, int rank, BigDecimal prize) {
        this.name = name;
        this.points = points;
        this.rank = rank;
        this.prize = prize;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", rank=" + rank +
                ", prize=" + prize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultResponse that = (ResultResponse) o;
        return getRank() == that.getRank() && getName().equals(that.getName()) && getPoints().equals(that.getPoints()) && getPrize().equals(that.getPrize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPoints(), getRank(), getPrize());
    }
}
