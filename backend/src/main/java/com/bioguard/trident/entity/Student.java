package com.bioguard.trident.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentid;

    private String name;
    private String aadharNumber;

    @ManyToOne
    @JoinColumn(name = "batchid")
    @JsonBackReference
    private Batch batch;

    @OneToOne(mappedBy = "student")
    @JsonManagedReference
    private Biometrics biometrics;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentid, student.studentid) &&
                Objects.equals(name, student.name) &&
                Objects.equals(aadharNumber, student.aadharNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentid, name, aadharNumber);
    }
}
