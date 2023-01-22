package com.rebuy.api.scope.dto.response;

import java.math.BigDecimal;
import java.util.Objects;

public class ResultResponse {
    private String name;
    private BigDecimal points;
    private int rank;
    private BigDecimal prize;

    public ResultResponse() {
    }

    private ResultResponse(Builder builder) {
        this.name = builder.name;
        this.points = builder.points;
        this.rank = builder.rank;
        this.prize = builder.prize;
    }

    public int getRank() {
        return rank;
    }


    public BigDecimal getPrize() {
        return prize;
    }


    public String getName() {
        return name;
    }


    public BigDecimal getPoints() {
        return points;
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

    public static class Builder {
        private String name;
        private BigDecimal points;
        private int rank;
        private BigDecimal prize;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder points(BigDecimal points) {
            this.points = points;
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

        public ResultResponse build() {
            return new ResultResponse(this);
        }
    }
}
