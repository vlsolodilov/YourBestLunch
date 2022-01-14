package com.yourbestlunch.web.restaurant;

import com.yourbestlunch.model.Vote;
import com.yourbestlunch.repository.VoteRepository;
import com.yourbestlunch.util.JsonUtil;
import com.yourbestlunch.web.AbstractControllerTest;
import com.yourbestlunch.web.user.UserTestData;
import com.yourbestlunch.web.vote.VoteTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestaurantController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID_1))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.restaurant1));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RestaurantTestData.RESTAURANT_ID_1))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.restaurant1, RestaurantTestData.restaurant2,
                        RestaurantTestData.restaurant3, RestaurantTestData.restaurant4, RestaurantTestData.restaurant5));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getWithLunch() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/with-lunch"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER_WITH_LUNCH.contentJson(RestaurantTestData.restaurant1));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL_4)
    void createVote() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.RESTAURANT_ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL_4)
    void updateVote() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT_ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                //.andExpect(status().isNoContent()); //right time and conditions
                .andExpect(status().isUnprocessableEntity()); //wrong time and conditions

        VoteTestData.VOTE_MATCHER.assertMatch(voteRepository.getById(VoteTestData.VOTE_ID_1), VoteTestData.getUpdated());
    }
}