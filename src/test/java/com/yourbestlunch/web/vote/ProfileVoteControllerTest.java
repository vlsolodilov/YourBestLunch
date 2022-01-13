package com.yourbestlunch.web.vote;

import com.yourbestlunch.repository.RestaurantRepository;
import com.yourbestlunch.repository.VoteRepository;
import com.yourbestlunch.to.RestaurantTo;
import com.yourbestlunch.util.RestaurantUtil;
import com.yourbestlunch.web.AbstractControllerTest;
import com.yourbestlunch.web.restaurant.RestaurantTestData;
import com.yourbestlunch.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Comparator;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileVoteController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAllToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.getTos(voteRepository.getAll(LocalDate.now()),
                        restaurantRepository.findAll()).stream().sorted(Comparator.comparingInt(RestaurantTo::getCountVote).reversed()).toList()));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by")
                .param("localDate", "2022-01-13"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.getTos(voteRepository.getAll(VoteTestData.VOTE_DATE),
                        restaurantRepository.findAll()).stream().sorted(Comparator.comparingInt(RestaurantTo::getCountVote).reversed()).toList()));
    }
}