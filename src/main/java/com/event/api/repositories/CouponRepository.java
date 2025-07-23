package com.event.api.repositories;

import com.event.api.domain.entities.Coupon;
import com.event.api.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    List<Coupon> findByEventAndValidAfter(Event event, Date currentDate);
}
