package com.WUE.certificategeneratorjava.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Certificate {

    @Id
    private String id;

    @Lob
    @Column(length = 429496725)
    private byte[] imageData;

    @CreationTimestamp
    private String createdOn;
    @UpdateTimestamp
    private String updatedOn;
}
