package com.sermar.zara.orderservice.utils;

import com.sermar.zara.orderservice.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    private DoublyLinkedList<User> list;

    @BeforeEach
    void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test
    void given_not_data_then_list_is_empty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }
    @Test
    void given_data_when_add_object_then_list_not_empty() {
        list.add(this.createUser(1));

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    void given_datas_when_add_objects_then_size_list_is_four() {
        this.addNodes(6);

        assertEquals(6, list.size());
    }

    @Test
    void when_remove_an_object_then_size_list_is_trhee() {
        this.addNodes(4);

        this.list.remove();

        assertEquals(3, this.list.size());
    }

    @Test
    void given_an_empty_list_when_deleting_then_is_error() {
        assertThrows(NoSuchElementException.class, () -> this.list.remove());
    }

    @Test
    void when_remove_all_then_list_is_empty() {
        this.addNodes(4);

        this.list.removeAll();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void given_an_empty_list_when_remove_all_then_is_error() {
        assertThrows(NoSuchElementException.class, () -> this.list.remove());
    }

    @Test
    void when_get_list_then_return() {
        this.addNodes(10);

        List<User> users = list.getAll();

        System.out.println(users.toString());
        assertNotNull(users);
        assertEquals(10, users.size());
    }

    @Test
    void given_index_when_get_data_selected_then_return() {
        this.addNodes(10);

        User user = list.get(9);

        assertNotNull(user);
        assertEquals(user.getId(), list.get(9).getId());
    }

    private void addNodes(int index) {
        for(int i = 0; i < index; i++) {
            list.add(this.createUser(i + 1));
        }
    }

    private User createUser(int index) {
        return new User().setId(Long.valueOf(index))
                .setName("::name" + index + "::")
                .setAddress("::address" + index + "::");
    }

}