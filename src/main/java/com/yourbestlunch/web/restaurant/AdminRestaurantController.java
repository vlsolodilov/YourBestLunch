package com.yourbestlunch.web.restaurant;

import com.yourbestlunch.error.IllegalRequestDataException;
import com.yourbestlunch.model.Restaurant;
import com.yourbestlunch.to.RestaurantTo;
import com.yourbestlunch.util.RestaurantUtil;
import com.yourbestlunch.web.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.yourbestlunch.util.validation.ValidationUtil.assureIdConsistent;
import static com.yourbestlunch.util.validation.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "restaurants")
public class AdminRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("create {}", restaurantTo);
        checkNew(restaurantTo);
        if (repository.findByNameAndAddress(restaurantTo.getName(), restaurantTo.getAddress())
                .isPresent()){
            throw new IllegalRequestDataException(GlobalExceptionHandler.EXCEPTION_DUPLICATE_RESTAURANT);
        }
        Restaurant created = prepareAndSave(RestaurantUtil.createNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update {} with id={}", restaurantTo, id);
        assureIdConsistent(restaurantTo, id);
        if (repository.findByNameAndAddress(restaurantTo.getName(), restaurantTo.getAddress())
                .isPresent()){
            throw new IllegalRequestDataException(GlobalExceptionHandler.EXCEPTION_DUPLICATE_RESTAURANT);
        }
        Restaurant restaurant = repository.getById(id);
        prepareAndSave(RestaurantUtil.updateFromTo(restaurant, restaurantTo));
    }

}