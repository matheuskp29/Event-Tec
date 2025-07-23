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

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.address a WHERE e.eventDate >= :currentDate")
    Page<Event> findUpComingEvents(@Param("currentDate")Date currentDate, Pageable pageable);

    @Query("SELECT e From Event e LEFT JOIN e.address a " +
            "WHERE (:title IS NULL OR e.title LIKE %:title%) " +
            "AND (:city IS NULL OR a.city LIKE %:city%) " +
            "AND (:uf IS NULL OR a.uf LIKE %:uf%) " +
            "AND e.eventDate BETWEEN :startDate AND :endDate")
    Page<Event> findFilteredEvents(@Param("title") String title,
                                   @Param("city") String city,
                                   @Param("uf") String uf,
                                   @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate,
                                   Pageable pageable);
}
