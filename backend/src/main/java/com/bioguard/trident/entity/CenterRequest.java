package com.bioguard.trident.entity;

import com.bioguard.trident.utility.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "center_request")
public class CenterRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private String username;
    private String centerCode;
    private Status status;

    private LocalDateTime requestDate;

    public CenterRequest() {
        this.requestDate = LocalDateTime.now();
        this.status = Status.Pending;
    }

}
