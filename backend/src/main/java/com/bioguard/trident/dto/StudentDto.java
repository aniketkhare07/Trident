package com.bioguard.trident.dto;

import com.bioguard.trident.entity.Batch;
import com.bioguard.trident.entity.Biometrics;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
public class StudentDto {
    private Long studentid;
    private String name;
    private String aadharNumber;
    @JsonBackReference
    private BatchDto batchDto;
    @JsonManagedReference
    private BiometricsDto biometricsDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return Objects.equals(studentid, that.studentid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(aadharNumber, that.aadharNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentid, name, aadharNumber);
    }
}
