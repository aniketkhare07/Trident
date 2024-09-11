package com.bioguard.trident.dto;

import com.bioguard.trident.utility.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CenterRequestDto {

    private Long requestId;
    private String username;
    private String centerCode;
    private Status status;

    private LocalDateTime requestDate;

    public CenterRequestDto() {
        this.requestDate = LocalDateTime.now();
        this.status = Status.Pending;
    }
}
