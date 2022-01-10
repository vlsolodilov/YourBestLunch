package com.yourbestlunch.util;


import com.yourbestlunch.model.Restaurant;
import com.yourbestlunch.model.Vote;
import com.yourbestlunch.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class RestaurantUtil {

    public static List<RestaurantTo> getTos(Collection<Vote> votes, Collection<Restaurant> restaurants) {
        Map<Integer, Integer> voteSumByRestaurant = new HashMap<>();
        votes.forEach(vote -> voteSumByRestaurant.merge(vote.getRestaurant().getId(), 1, Integer::sum));
        return restaurants.stream()
                .map(restaurant -> createTo(restaurant, voteSumByRestaurant.getOrDefault(restaurant.getId(), 0)))
                .filter(Objects::nonNull)
                .toList();
    }

    public static RestaurantTo createTo(Restaurant restaurant, int countVote) {
        return countVote != 0 ? new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), countVote) : null;
    }
}
