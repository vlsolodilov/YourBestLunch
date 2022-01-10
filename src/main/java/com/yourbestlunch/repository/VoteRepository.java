package com.yourbestlunch.repository;

import com.yourbestlunch.model.LunchItem;
import com.yourbestlunch.model.Restaurant;
import com.yourbestlunch.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.localDate=:localDate")
    List<Vote> getAll(@Param("localDate") LocalDate localDate);
}