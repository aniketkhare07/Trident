package com.bioguard.trident.entity;

import com.bioguard.trident.utility.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "batch_request")
public class BatchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private String username;
    private String batchCode;
    private String centerCode;
    private Status status;

    private LocalDateTime requestDate;

    public BatchRequest() {
        this.requestDate = LocalDateTime.now();
        this.status = Status.Pending;
    }
}
