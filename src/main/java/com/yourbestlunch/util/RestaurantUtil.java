package com.yourbestlunch.util;


import com.yourbestlunch.model.Restaurant;
import com.yourbestlunch.model.Vote;
import com.yourbestlunch.to.RestaurantTo;
import com.yourbestlunch.to.RestaurantWithVoteTo;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class RestaurantUtil {

    public static List<RestaurantWithVoteTo> getTos(Collection<Vote> votes, Collection<Restaurant> restaurants) {
        Map<Integer, Integer> voteSumByRestaurant = new HashMap<>();
        votes.forEach(vote -> voteSumByRestaurant.merge(vote.getRestaurant().getId(), 1, Integer::sum));
        return restaurants.stream()
                .map(restaurant -> createTo(restaurant, voteSumByRestaurant.getOrDefault(restaurant.getId(), 0)))
                .filter(Objects::nonNull)
                .toList();
    }

    public static RestaurantWithVoteTo createTo(Restaurant restaurant, int countVote) {
        return countVote != 0 ? new RestaurantWithVoteTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), countVote) : null;
    }

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        restaurant.setAddress(restaurantTo.getAddress());
        return restaurant;
    }
}
