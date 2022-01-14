package com.yourbestlunch.web.lunch;

import com.yourbestlunch.model.LunchItem;
import com.yourbestlunch.repository.LunchRepository;
import com.yourbestlunch.to.LunchItemTo;
import com.yourbestlunch.util.JsonUtil;
import com.yourbestlunch.util.LunchItemUtil;
import com.yourbestlunch.web.AbstractControllerTest;
import com.yourbestlunch.web.restaurant.RestaurantTestData;
import com.yourbestlunch.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminLunchControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/admin/restaurants/";

    @Autowired
    private LunchRepository lunchRepository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch/" + LunchTestData.LUNCH_ID_1))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(LunchTestData.LUNCH_MATCHER.contentJson(LunchTestData.lunchItem1));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch/" + LunchTestData.NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch/" + LunchTestData.LUNCH_ID_1))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(lunchRepository.findById(LunchTestData.LUNCH_ID_1).isPresent());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch/" + LunchTestData.NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        LunchItemTo updatedTo = new LunchItemTo(null, LocalDate.now(), "newDesc",99);
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch/" + LunchTestData.LUNCH_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        LunchTestData.LUNCH_MATCHER.assertMatch(lunchRepository.getById(LunchTestData.LUNCH_ID_1),
                LunchItemUtil.updateFromTo(new LunchItem(LunchTestData.lunchItem1), updatedTo));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createWithLocation() throws Exception {
        LunchItemTo newTo = new LunchItemTo(null, LocalDate.now(), "newDesc", 99);
        LunchItem newLunchItem = LunchItemUtil.createNewFromTo(newTo, RestaurantTestData.restaurant1);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isCreated());

        LunchItem created = LunchTestData.LUNCH_MATCHER.readFromJson(action);
        int newId = created.id();
        newLunchItem.setId(newId);
        LunchTestData.LUNCH_MATCHER.assertMatch(created, newLunchItem);
        LunchTestData.LUNCH_MATCHER.assertMatch(lunchRepository.getById(newId), newLunchItem);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.RESTAURANT_ID_1 + "/lunch/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(LunchTestData.LUNCH_MATCHER.contentJson(LunchTestData.lunchItem1, LunchTestData.lunchItem6));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void createInvalid() throws Exception {
        LunchItemTo invalid = new LunchItemTo(null, LocalDate.now(), "", 99);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateInvalid() throws Exception {
        LunchItemTo invalid = new LunchItemTo(null, LocalDate.now(), "", 99);
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        LunchItemTo updated = new LunchItemTo(null, LocalDate.now(), "<script>alert(123)</script>", 99);
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}