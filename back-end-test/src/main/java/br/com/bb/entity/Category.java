package br.com.bb.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by e068635 on 2/26/2019.
 */


@Entity(name = "CATEGORY")
@Getter
@Setter
public class Category {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CATEGORY_ID", length = 20)
    private Long categoryId;

    @Column(name = "CATEGORY_NAME", length = 50)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "PRODUCT_CATEGORY",
            joinColumns = { @JoinColumn(name = "CATEGORY_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PRODUCT_ID") })

    private List<Product> products;

}
