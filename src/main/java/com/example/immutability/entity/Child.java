package com.example.immutability.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Getter
@EqualsAndHashCode(of = { "id", "name", "age", "parent" })
@ToString(exclude = "parent")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
