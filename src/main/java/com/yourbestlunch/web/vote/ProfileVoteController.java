package com.yourbestlunch.web.vote;

import com.yourbestlunch.repository.RestaurantRepository;
import com.yourbestlunch.repository.VoteRepository;
import com.yourbestlunch.to.RestaurantTo;
import com.yourbestlunch.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ProfileVoteController {
    static final String REST_URL = "/api/profile/restaurants/rating";

    @Autowired
    protected VoteRepository voteRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping
    public List<RestaurantTo> getAllToday() {
        log.info("getAllToday");
        LocalDate today = LocalDate.now();
        return RestaurantUtil.getTos(voteRepository.getAll(today), restaurantRepository.findAll()).stream()
                .sorted(Comparator.comparingInt(RestaurantTo::getCountVote).reversed())
                .toList();
    }

    @GetMapping("/by")
    // https://stackoverflow.com/questions/40274353/how-to-use-localdatetime-requestparam-in-spring-i-get-failed-to-convert-string
    public List<RestaurantTo> getAllByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        log.info("getAllByDate");
        return RestaurantUtil.getTos(voteRepository.getAll(localDate), restaurantRepository.findAll()).stream()
                .sorted(Comparator.comparingInt(RestaurantTo::getCountVote).reversed())
                .toList();
    }
}