package com.yourbestlunch.repository;

import com.yourbestlunch.model.LunchItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Transactional(readOnly = true)
public interface LunchRepository extends BaseRepository<LunchItem> {

    @Query("SELECT l FROM LunchItem l WHERE l.restaurant.id=:restaurantId ORDER BY l.dateTime DESC")
    List<LunchItem> getAll(@Param("restaurantId") int restaurantId);
}