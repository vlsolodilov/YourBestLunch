package com.yourbestlunch.web.vote;

import com.yourbestlunch.model.Vote;
import com.yourbestlunch.web.MatcherFactory;
import com.yourbestlunch.web.restaurant.RestaurantTestData;
import com.yourbestlunch.web.user.UserTestData;

import java.time.LocalDate;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE_ID_1 = 1;

    public static final LocalDate VOTE_DATE = LocalDate.of(2022, 1, 13);
    public static final LocalDate VOTE_NOW_DATE = LocalDate.now();

    public static Vote getNew() {
        return new Vote(null, VOTE_NOW_DATE, UserTestData.user2, RestaurantTestData.restaurant1);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID_1, VOTE_DATE, UserTestData.user, RestaurantTestData.restaurant2);
    }
}
