package com.example.immutability.dao;

import com.example.immutability.entity.Child;
import com.example.immutability.entity.Parent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ParentDaoTest {

    @Autowired
    private ParentDao parentDao;

    @Autowired
    private ChildRepository childRepository;

    @Test
    public void saveParent() {
        final Parent parent = Parent.builder()
                                    .name("parent")
                                    .age(30)
                                    .build();

        parentDao.save(parent);

        final Optional<Parent> recovered = parentDao.findById(parent.getId());
        assertThat(recovered, is(Optional.of(parent)));
    }

    @Test
    public void saveParentWithChildren() {
        final Parent parent = Parent.builder()
                                    .name("parent")
                                    .age(30)
                                    .build();

        parentDao.save(parent);

        final Child child1 = Child.builder()
                .name("child-1")
                .age(10)
                .parent(parent)
                .build();

        final Child child2 = Child.builder()
                .name("child-2")
                .parent(parent)
                .build();

        final List<Child> children = Arrays.asList(child1, child2);

        childRepository.saveAll(children);

        final Parent parentWithChildren = parent.toBuilder().children(children).build();

        parentDao.save(parentWithChildren);

        final Optional<Parent> recovered = parentDao.findById(parentWithChildren.getId());
        assertTrue(recovered.isPresent());
        assertThat(recovered.get(), is(parentWithChildren));
        assertThat(recovered.get().getChildren(), containsInAnyOrder(child1, child2));
    }

    @Test
    public void updateParent() {
        final Parent parent = Parent.builder()
                .name("parent")
                .build();

        parentDao.save(parent);

        final Parent updatedParent = parent.toBuilder()
                .age(30)
                .build();

        parentDao.save(updatedParent);

        final Optional<Parent> recovered = parentDao.findById(updatedParent.getId());
        assertTrue(recovered.isPresent());
        assertThat(recovered.get(), is(updatedParent));
    }

    @Test
    public void updateParentWithNewChildren() {
        final Parent parent = Parent.builder()
                .name("parent")
                .age(30)
                .build();

        parentDao.save(parent);

        final Child child1 = Child.builder()
                .name("child-1")
                .age(10)
                .parent(parent)
                .build();

        final Child child2 = Child.builder()
                .name("child-2")
                .parent(parent)
                .build();

        childRepository.saveAll(Arrays.asList(child1, child2));

        final Parent parentWithChildren = parent.toBuilder().children(Arrays.asList(child1, child2)).build();

        parentDao.save(parentWithChildren);

        final Optional<Parent> recovered = parentDao.findById(parentWithChildren.getId());
        assertTrue(recovered.isPresent());

        final Parent recoveredParent = recovered.get();

        final Child child3 = Child.builder()
                .name("child-2")
                .parent(recoveredParent)
                .build();

        childRepository.save(child3);

        final Parent updatedParent = recoveredParent.toBuilder()
                            .child(child3)
                            .build();
        parentDao.save(updatedParent);

        final Optional<Parent> recoveredWithNewChild = parentDao.findById(updatedParent.getId());
        assertTrue(recoveredWithNewChild.isPresent());
        assertThat(recoveredWithNewChild.get(), is(updatedParent));
        assertThat(recoveredWithNewChild.get().getChildren(), containsInAnyOrder(child1, child2, child3));
    }
}