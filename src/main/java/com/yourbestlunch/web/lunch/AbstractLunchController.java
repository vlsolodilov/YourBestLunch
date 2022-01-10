package com.yourbestlunch.web.lunch;

import com.yourbestlunch.model.LunchItem;
import com.yourbestlunch.repository.LunchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


@Slf4j
public abstract class AbstractLunchController {

    @Autowired
    protected LunchRepository repository;

    public ResponseEntity<LunchItem> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    protected LunchItem prepareAndSave(LunchItem lunchItem) {
        return repository.save(lunchItem);
    }
}