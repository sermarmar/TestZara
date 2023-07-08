package com.sermar.zara.orderservice.utils;

import com.sermar.zara.orderservice.dto.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NodeTest {

    @Test
    void given_data_then_return() {
        User user = new User()
                .setId(1L)
                .setName("::name::")
                .setAddress("::address::");

        Node<User> userNode = new Node<>(user);

        assertNotNull(userNode.data);
        assertEquals(user, userNode.data);
    }

}