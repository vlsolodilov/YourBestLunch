package com.yourbestlunch.web.lunch;


import com.yourbestlunch.model.LunchItem;
import com.yourbestlunch.web.MatcherFactory;
import com.yourbestlunch.web.restaurant.RestaurantTestData;

import java.time.LocalDate;

public class LunchTestData {
    public static final MatcherFactory.Matcher<LunchItem> LUNCH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(LunchItem.class, "restaurant");

    public static final int LUNCH_ID_1 = 1;
    public static final int LUNCH_ID_6 = 6;
    public static final int NOT_FOUND = 100;

    public static final LocalDate LUNCH_DATE = LocalDate.now();

    public static final LunchItem lunchItem1 = new LunchItem(LUNCH_ID_1, LUNCH_DATE, "Soup_1", 11, RestaurantTestData.restaurant1);
    public static final LunchItem lunchItem6 = new LunchItem(LUNCH_ID_6, LUNCH_DATE, "Dish_1", 21, RestaurantTestData.restaurant1);

    public static LunchItem getNew() {
        return new LunchItem(null, LUNCH_DATE, "Soup_new", 11, RestaurantTestData.restaurant1);
    }

    public static LunchItem getUpdated() {
        return new LunchItem(LUNCH_ID_1, LUNCH_DATE, "Soup_11", 111, RestaurantTestData.restaurant1);
    }
}
