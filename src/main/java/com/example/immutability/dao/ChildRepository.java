package com.example.immutability.dao;

import com.example.immutability.entity.Child;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends CrudRepository<Child, Long> {
}
