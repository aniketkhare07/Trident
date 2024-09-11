package com.bioguard.trident.dto;

import com.bioguard.trident.entity.Batch;
import com.bioguard.trident.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
public class CenterDto {
    private Long centerid;
    private String centerCode;
    private UserDto userDto;

    @JsonManagedReference
    private Set<BatchDto> batcheDtos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CenterDto centerDto = (CenterDto) o;
        return Objects.equals(centerid, centerDto.centerid) &&
                Objects.equals(centerCode, centerDto.centerCode) &&
                Objects.equals(userDto, centerDto.userDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerid, centerCode, userDto);
    }
}
