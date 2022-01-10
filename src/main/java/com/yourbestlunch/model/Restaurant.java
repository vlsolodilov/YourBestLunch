package com.yourbestlunch.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r ORDER BY r.name"),
})
@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String ALL_SORTED = "Restaurant.getAllSorted";

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dateTime DESC")
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE) //https://stackoverflow.com/a/44988100/548473
    @JsonManagedReference
    private List<LunchItem> lunchItems;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.address);
    }

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }

}