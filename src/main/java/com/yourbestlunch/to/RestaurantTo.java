package com.yourbestlunch.to;

import com.yourbestlunch.HasId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo implements HasId {

    @NotBlank
    @Size(max = 100)
    String address;

    int countVote;

    public RestaurantTo(Integer id, String name, String address, int countVote) {
        super(id, name);
        this.address = address;
        this.countVote = countVote;
    }
}
