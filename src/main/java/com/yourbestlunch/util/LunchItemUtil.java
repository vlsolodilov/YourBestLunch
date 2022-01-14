package com.yourbestlunch.util;

import com.yourbestlunch.model.LunchItem;
import com.yourbestlunch.model.Restaurant;
import com.yourbestlunch.to.LunchItemTo;
import lombok.experimental.UtilityClass;


@UtilityClass
public class LunchItemUtil {

    public static LunchItem createNewFromTo(LunchItemTo lunchItemTo, Restaurant restaurant) {
        return new LunchItem(null, lunchItemTo.getLocalDate(), lunchItemTo.getDescription(),
                lunchItemTo.getPrice(), restaurant);
    }

    public static LunchItem updateFromTo(LunchItem lunchItem, LunchItemTo lunchItemTo) {
        lunchItem.setLocalDate(lunchItemTo.getLocalDate());
        lunchItem.setDescription(lunchItemTo.getDescription());
        lunchItem.setPrice(lunchItemTo.getPrice());
        return lunchItem;
    }
}