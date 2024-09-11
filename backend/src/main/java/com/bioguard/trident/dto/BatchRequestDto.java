package com.bioguard.trident.dto;

import com.bioguard.trident.entity.Batch;
import com.bioguard.trident.entity.Center;
import com.bioguard.trident.entity.User;
import com.bioguard.trident.utility.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BatchRequestDto {

    private Long requestId;
    private String username;
    private String batchCode;
    private String centerCode;
    private Status status;

    private LocalDateTime requestDate;

    public BatchRequestDto() {
        this.requestDate = LocalDateTime.now();
        this.status = Status.Pending;
    }
}
