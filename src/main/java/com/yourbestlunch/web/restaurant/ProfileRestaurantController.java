package com.yourbestlunch.web.restaurant;

import com.yourbestlunch.error.IllegalRequestDataException;
import com.yourbestlunch.model.Restaurant;
import com.yourbestlunch.model.Vote;
import com.yourbestlunch.repository.VoteRepository;
import com.yourbestlunch.web.AuthUser;
import com.yourbestlunch.web.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.yourbestlunch.util.validation.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = ProfileRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "restaurants")
public class ProfileRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/profile/restaurants";
    static final LocalTime FINISH_CHANGING_VOTE = LocalTime.of(11, 0);

    @Autowired
    protected VoteRepository voteRepository;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @GetMapping("/{id}/with-lunch")
    public Restaurant getWithMeals(@PathVariable int id) {
        return repository.getWithLunch(id);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        LocalDate dateNow = LocalDate.now();
        log.info("create vote user{}", authUser.id());
        if (voteRepository.getByUser(dateNow, authUser.id()).isEmpty()) {
            voteRepository.save(new Vote(null, dateNow, authUser.getUser(), repository.getById(id)));
        } else {
            throw new IllegalRequestDataException(GlobalExceptionHandler.EXCEPTION_DUPLICATE_VOTE);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void updateVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        log.info("update vote user{}", authUser.id());
        Vote updated = voteRepository.getByUser(dateNow, authUser.id()).orElseThrow(
                () ->new IllegalRequestDataException("User has not voted yet"));
        if (timeNow.isBefore(FINISH_CHANGING_VOTE)) {
            updated.setRestaurant(repository.getById(id));
            voteRepository.save(updated);
        } else {
            throw new IllegalRequestDataException("It's too late for the user to change vote ");
        }
    }
}