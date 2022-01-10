package com.yourbestlunch.web.lunch;

import com.yourbestlunch.model.LunchItem;
import com.yourbestlunch.repository.LunchRepository;
import com.yourbestlunch.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.yourbestlunch.util.validation.ValidationUtil.assureIdConsistent;
import static com.yourbestlunch.util.validation.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = AdminLunchController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminLunchController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/lunch/";

    @Autowired
    protected LunchRepository lunchRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public ResponseEntity<LunchItem> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(lunchRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        lunchRepository.deleteExisted(id);
    }

    @GetMapping
    public List<LunchItem> getAll(@PathVariable int restaurantId) {
        log.info("getAll");
        return lunchRepository.getAll(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LunchItem> createWithLocation(@Valid @RequestBody LunchItem lunchItem, @PathVariable int restaurantId) {
        log.info("create {}", lunchItem);
        checkNew(lunchItem);
        lunchItem.setRestaurant(restaurantRepository.getById(restaurantId));
        LunchItem created = lunchRepository.save(lunchItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody LunchItem lunchItem, @PathVariable int id) {
        log.info("update {} with id={}", lunchItem, id);
        assureIdConsistent(lunchItem, id);
        lunchRepository.save(lunchItem);
    }

}