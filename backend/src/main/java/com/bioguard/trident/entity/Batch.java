package com.bioguard.trident.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batcheid;
    private String batchCode;
    @ManyToOne
    @JoinColumn(name = "centerid")
    @JsonBackReference
    private Center center;

    @OneToMany(mappedBy = "batch")
    @JsonManagedReference
    private Set<Student> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batch batch = (Batch) o;
        return Objects.equals(batcheid, batch.batcheid) &&
                Objects.equals(batchCode, batch.batchCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batcheid, batchCode);
    }
}
