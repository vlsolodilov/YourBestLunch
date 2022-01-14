package com.yourbestlunch.repository;

import com.yourbestlunch.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.lunchItems l WHERE r.id=?1 AND l.localDate=?2")
    Restaurant getWithLunch(int id, LocalDate localDate);

    Optional<Restaurant> findByNameAndAddress(String name, String address);
}