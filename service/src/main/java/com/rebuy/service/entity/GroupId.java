package com.rebuy.service.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "group_id")
public class GroupId {

    @Id
    @SequenceGenerator(name = "group_id_generator", sequenceName = "group_id_sq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_generator")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "date_id")
    private DateLB date;
    private String promotionGroupId;

    private GroupId(Builder builder) {
        this.date = builder.date;
        this.promotionGroupId = builder.promotionGroupId;
    }

    public GroupId() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPromotionGroupId(String promotionGroupId) {
        this.promotionGroupId = promotionGroupId;
    }

    public int getId() {
        return id;
    }

    public void setDate(DateLB date) {
        this.date = date;
    }

    public DateLB getDate() {
        return date;
    }

    public String getPromotionGroupId() {
        return promotionGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupId groupId1 = (GroupId) o;
        return promotionGroupId.equals(groupId1.promotionGroupId) && date.equals(groupId1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, promotionGroupId);
    }

    @Override
    public String toString() {
        return "GroupId{" +
                "id=" + id +
                ", date=" + date +
                ", promotionGroupId='" + promotionGroupId + '\'' +
                '}';
    }

    public static final class Builder {

        private DateLB date;
        private String promotionGroupId;

        public Builder date(DateLB date) {
            this.date = date;
            return this;
        }

        public Builder promotionGroupId(String promotionGroupId) {
            this.promotionGroupId = promotionGroupId;
            return this;
        }

        public GroupId build() {
            return new GroupId(this);
        }

    }

}
