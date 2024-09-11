package com.bioguard.trident.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class Biometrics
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long biometricid;

    private String fingerprint1;
    private String fingerprint2;
    private String fingerprint3;
    private String fingerprint4;
    private String fingerprint5;
    private String iris;

    @OneToOne
    @JoinColumn(name = "studentid")
    @JsonBackReference
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometrics that = (Biometrics) o;
        return Objects.equals(biometricid, that.biometricid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biometricid);
    }
}

