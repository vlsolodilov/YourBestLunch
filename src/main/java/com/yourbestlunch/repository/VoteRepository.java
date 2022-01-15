package com.yourbestlunch.repository;

import com.yourbestlunch.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.localDate=:localDate")
    List<Vote> getAll(@Param("localDate") LocalDate localDate);

    @Query("SELECT v FROM Vote v WHERE v.localDate=:localDate AND v.user.id=:userId")
    Optional<Vote> getByUser(@Param("localDate") LocalDate localDate, @Param("userId") int userId);

}