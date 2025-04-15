package com.event.api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon")
@Entity
public class Coupon {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    private Integer discount;
    private LocalDate valid;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
