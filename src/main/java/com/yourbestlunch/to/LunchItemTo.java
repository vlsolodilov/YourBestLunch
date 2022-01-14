package com.yourbestlunch.to;

import com.yourbestlunch.HasId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LunchItemTo extends BaseTo implements HasId {

    @NotNull
    LocalDate localDate;

    @NotBlank
    @Size(min = 2, max = 120)
    String description;

    @Range(min = 0, max = 10000)
    @NotNull
    int price;

    public LunchItemTo(Integer id, LocalDate localDate, String description, int price) {
        super(id);
        this.localDate = localDate;
        this.description = description;
        this.price = price;
    }
}
