package com.yourbestlunch.repository;

import com.yourbestlunch.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    //    https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"lunchItems"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1")
    Restaurant getWithLunch(int id);

    Optional<Restaurant> findByNameAndAddress(String name, String address);

}