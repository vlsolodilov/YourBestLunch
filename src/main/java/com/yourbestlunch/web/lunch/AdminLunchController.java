package com.yourbestlunch.web.lunch;

import com.yourbestlunch.error.IllegalRequestDataException;
import com.yourbestlunch.model.LunchItem;
import com.yourbestlunch.model.Restaurant;
import com.yourbestlunch.repository.LunchRepository;
import com.yourbestlunch.repository.RestaurantRepository;
import com.yourbestlunch.to.LunchItemTo;
import com.yourbestlunch.util.LunchItemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.yourbestlunch.util.validation.ValidationUtil.*;


@RestController
@RequestMapping(value = AdminLunchController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminLunchController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/lunch";

    @Autowired
    protected LunchRepository lunchRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public ResponseEntity<LunchItem> get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get {}", id);
        return ResponseEntity.of(lunchRepository.get(id, restaurantId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete {}", id);
        checkModification(lunchRepository.delete(id, restaurantId), id);
    }

    @GetMapping
    public List<LunchItem> getAll(@PathVariable int restaurantId) {
        log.info("getAll");
        return lunchRepository.getAll(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LunchItem> createWithLocation(@Valid @RequestBody LunchItemTo lunchItemTo, @PathVariable int restaurantId) {
        log.info("create {}", lunchItemTo);
        checkNew(lunchItemTo);
        LunchItem created = lunchRepository.save(LunchItemUtil.createNewFromTo(lunchItemTo, restaurantRepository.getById(restaurantId)));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody LunchItemTo lunchItemTo, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} with id={}", lunchItemTo, id);
        assureIdConsistent(lunchItemTo, id);
        lunchRepository.get(id, restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("LunchItem from another restaurant"));
        LunchItem lunchItem = lunchRepository.getById(id);
        lunchRepository.save(LunchItemUtil.updateFromTo(lunchItem, lunchItemTo));
    }

}