package com.example.immutability.dao;

import com.example.immutability.entity.Parent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentDao extends CrudRepository<Parent, Long> {

    @Query("select p from Parent p left join fetch p.children where p.id = :id")
    @Override
    Optional<Parent> findById(@Param("id") Long id);
}
