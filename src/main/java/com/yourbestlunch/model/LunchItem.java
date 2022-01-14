package com.yourbestlunch.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = LunchItem.ALL_SORTED, query = "SELECT li FROM LunchItem li WHERE li.restaurant.id=:restaurantId ORDER BY li.description"),
        @NamedQuery(name = LunchItem.DELETE, query = "DELETE FROM LunchItem li WHERE li.id=:id AND li.restaurant.id=:restaurantId"),
})
@Entity
@Table(name = "lunch_item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class LunchItem extends BaseEntity implements Serializable {
    public static final String ALL_SORTED = "LunchItem.getAll";
    public static final String DELETE = "LunchItem.delete";

    @Column(name = "local_date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate localDate;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 10000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "restaurant-lunchItem")
    @ToString.Exclude
    private Restaurant restaurant;

    public LunchItem(LunchItem l) {
        this(l.id, l.localDate, l.description, l.price, l.restaurant);
    }

    public LunchItem(Integer id, LocalDate localDate, String description, int price) {
        super(id);
        this.localDate = localDate;
        this.description = description;
        this.price = price;
    }

    public LunchItem(Integer id, LocalDate localDate, String description, int price, Restaurant restaurant) {
        super(id);
        this.localDate = localDate;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
    }
}
