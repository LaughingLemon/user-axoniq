package org.lemon.useraxoniq.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "landline")
    private Telephone landLine;

    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;
    @Column(name = "town")
    private String town;
    @Column(name = "post_code", nullable = false)
    private String postCode;
}
