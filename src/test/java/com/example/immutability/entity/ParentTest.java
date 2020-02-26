package com.example.immutability.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParentTest {

    @Test
    public void testCollectionImmutability() {
        final Child child1 = Child.builder()
                .name("child-1")
                .build();

        final Child child2 = Child.builder()
                .name("child-2")
                .build();

        final List<Child> mutableChildren = new ArrayList<>();
        mutableChildren.add(child1);
        mutableChildren.add(child2);

        final Parent parent = Parent.builder()
                .name("parent")
                .children(mutableChildren)
                .build();

        final Child child3 = Child.builder().name("child-3").build();
        mutableChildren.add(child3);

        assertThat(parent.getChildren(), hasSize(2));
        assertThat(parent.getChildren(), not(contains(child3)));

        assertThrows(UnsupportedOperationException.class, () -> parent.getChildren().add(null));
    }
}