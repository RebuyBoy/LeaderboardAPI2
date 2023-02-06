package com.rebuy.service.dto.client.gg;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class GGResultResponse {

    @JsonProperty("nickname")
    private String name;
    @JsonProperty("countryId")
    private String countryCode;
    private BigDecimal points;
    private BigDecimal prize;
    private int rank;
    private int stake;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public BigDecimal getPoints() {
        return points;
    }

    @JsonProperty("point")
    public void setPoints(String points) {
        this.points = BigDecimal.valueOf(Double.parseDouble(points));
    }

    public BigDecimal getPrize() {
        return prize;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("prize")
    public void setPrize(Map<String, Object> prize) {
        if (prize == null) {
            this.prize = new BigDecimal(0);
        } else {
            List<Map<String, Object>> prizeItems = (List<Map<String, Object>>) prize.get("prizeItem");
            Object value = prizeItems.stream()
                    .filter(prizeItem -> prizeItem.get("currencyId").equals("GCD"))
                    .findFirst()
                    .map(prizeItem -> prizeItem.get("value"))
                    .orElse(0);
            if (value instanceof Double doubleValue) {
                this.prize = BigDecimal.valueOf(doubleValue);
            } else if (value instanceof Integer integerValue) {
                this.prize = BigDecimal.valueOf(integerValue);
            }
        }
    }

    public int getRank() {
        return rank;
    }

    public int getStake() {
        return stake;
    }

    public void setStake(int stake) {
        this.stake = stake;
    }

    @Override
    public String toString() {
        return "GGResultResponse{" +
                "name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", points=" + points +
                ", prize=" + prize +
                ", rank=" + rank +
                ", stake=" + stake +
                '}';
    }

}
