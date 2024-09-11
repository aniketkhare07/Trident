package com.bioguard.trident.dto;

import com.bioguard.trident.entity.Center;
import com.bioguard.trident.entity.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
public class BatchDto {
    private Long batcheid;
    private String batchCode;
    @JsonBackReference
    private CenterDto centerDto;
    @JsonManagedReference
    private Set<StudentDto> studentDtos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchDto batchDto = (BatchDto) o;
        return Objects.equals(batcheid, batchDto.batcheid) &&
                Objects.equals(batchCode, batchDto.batchCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batcheid, batchCode);
    }
}
