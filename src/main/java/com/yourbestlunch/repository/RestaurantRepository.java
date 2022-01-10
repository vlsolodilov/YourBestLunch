package com.yourbestlunch.repository;

import com.yourbestlunch.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}