package com.assignment.abn.model;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "recipe")
public class Recipe implements Serializable {

    @Serial
    private static final long serialVersionUID = -8413487235945979386L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_generator")
    @SequenceGenerator(name="recipe_generator", sequenceName = "recipe_seq", allocationSize=1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "num_serving")
    private int numServing;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "is_vegetarian")
    private boolean vegetarian;

    @ElementCollection
    private Set<String> ingredients;

}
