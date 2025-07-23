package com.event.api.repositories;

import com.event.api.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query(value = "SELECT e FROM Event e WHERE e.eventDate >= :currentDate")
    Page<Event> findUpComingEvents(@Param("currentDate")Date currentDate, Pageable pageable);
}
