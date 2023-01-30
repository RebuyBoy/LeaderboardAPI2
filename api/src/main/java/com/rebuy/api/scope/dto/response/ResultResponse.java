package com.rebuy.api.scope.dto.response;

import java.math.BigDecimal;
import java.util.Objects;

public class ResultResponse {
    private String name;
    private BigDecimal points;
    private int rank;

    public ResultResponse() {
    }

    private ResultResponse(Builder builder) {
        this.name = builder.name;
        this.points = builder.points;
        this.rank = builder.rank;
    }

    public int getRank() {
        return rank;
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultResponse that = (ResultResponse) o;
        return getRank() == that.getRank() && getName().equals(that.getName()) && getPoints().equals(that.getPoints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPoints(), getRank());
    }

    public static class Builder {
        private String name;
        private BigDecimal points;
        private int rank;

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

        public ResultResponse build() {
            return new ResultResponse(this);
        }
    }

}
