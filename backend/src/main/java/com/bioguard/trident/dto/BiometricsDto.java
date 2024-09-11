package com.bioguard.trident.dto;

import com.bioguard.trident.entity.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.Objects;

@Data
public class BiometricsDto {
    private Long biometricid;
    private String fingerprint1;
    private String fingerprint2;
    private String fingerprint3;
    private String fingerprint4;
    private String fingerprint5;
    private String iris;

    @JsonBackReference
    private StudentDto studentDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiometricsDto that = (BiometricsDto) o;
        return Objects.equals(biometricid, that.biometricid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biometricid);
    }
}
