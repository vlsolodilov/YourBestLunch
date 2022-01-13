package com.yourbestlunch.repository;

import com.yourbestlunch.model.LunchItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface LunchRepository extends BaseRepository<LunchItem> {

    @Modifying
    @Transactional
    @Query("DELETE FROM LunchItem l WHERE l.id=:id AND l.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query("SELECT l FROM LunchItem l WHERE l.restaurant.id=:restaurantId ORDER BY l.localDate DESC")
    List<LunchItem> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT l FROM LunchItem l JOIN FETCH l.restaurant WHERE l.id = ?1 and l.restaurant.id = ?2")
    LunchItem getWithRestaurant(int id, int restaurantId);

    @Query("SELECT l FROM LunchItem l WHERE l.id=:id AND l.restaurant.id=:restaurantId")
    Optional<LunchItem> get(@Param("id") int id, @Param("restaurantId") int restaurantId);
}