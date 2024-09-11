package com.bioguard.trident.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerid;
    private String centerCode;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", columnDefinition = "varchar")
    private User user;

    @OneToMany(mappedBy = "center")
    @JsonManagedReference
    private Set<Batch> batches;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Center center = (Center) o;
        return Objects.equals(centerid, center.centerid) &&
                Objects.equals(centerCode, center.centerCode) &&
                Objects.equals(user, center.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerid, centerCode, user);
    }
}
