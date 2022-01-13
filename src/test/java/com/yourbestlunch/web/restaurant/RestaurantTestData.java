package com.yourbestlunch.web.restaurant;

import com.yourbestlunch.model.Restaurant;
import com.yourbestlunch.to.RestaurantTo;
import com.yourbestlunch.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "lunchItem");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_WITH_LUNCH = MatcherFactory.usingEqualsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int RESTAURANT_ID_1 = 1;
    public static final int RESTAURANT_ID_2 = 2;
    public static final int RESTAURANT_ID_3 = 3;
    public static final int RESTAURANT_ID_4 = 4;
    public static final int RESTAURANT_ID_5 = 5;
    public static final int NOT_FOUND = 100;
    public static final String RESTAURANT_ADDRESS_1 = "First str., 5";
    public static final String RESTAURANT_ADDRESS_2 = "Second str., 4";
    public static final String RESTAURANT_ADDRESS_3 = "Third str., 3";
    public static final String RESTAURANT_ADDRESS_4 = "Fourth str., 2";
    public static final String RESTAURANT_ADDRESS_5 = "Fifth str., 1";

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID_1, "Restaurant_1", RESTAURANT_ADDRESS_1);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID_2, "Restaurant_2", RESTAURANT_ADDRESS_2);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID_3, "Restaurant_3", RESTAURANT_ADDRESS_3);
    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT_ID_4, "Restaurant_4", RESTAURANT_ADDRESS_4);
    public static final Restaurant restaurant5 = new Restaurant(RESTAURANT_ID_5, "Restaurant_5", RESTAURANT_ADDRESS_5);

    public static Restaurant getNew() {
        return new Restaurant(null, "New", "newAddress");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID_1, "UpdatedName", "newAddress");
    }

}
