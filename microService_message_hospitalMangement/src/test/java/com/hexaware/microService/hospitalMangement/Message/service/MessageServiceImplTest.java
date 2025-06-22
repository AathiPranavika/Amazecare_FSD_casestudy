package com.hexaware.microService.hospitalMangement.Message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.microservice.hospitalmangement.message.dto.MessageDTO;
import com.hexaware.microservice.hospitalmangement.message.entity.Message;
import com.hexaware.microservice.hospitalmangement.message.entity.Message.SenderRole;
import com.hexaware.microservice.hospitalmangement.message.exception.MessageNotFoundException;
import com.hexaware.microservice.hospitalmangement.message.service.IMessageService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(OrderAnnotation.class)
class MessageServiceImplTest {

    @Autowired
    private IMessageService messageService;


    @Test
    @Order(1)
    void testSendMessage() {
        MessageDTO dto = new MessageDTO();
        dto.setDoctorId(1L);
        dto.setPatientId(1L);
        dto.setSenderRole(SenderRole.DOCTOR);
        dto.setMessage("Hello, this is a test message");

        Message saved = messageService.sendMessage(dto);
        assertNotNull(saved);
        assertNotNull(saved.getMessageId());
    }

    @Test
    @Order(2)
    void testGetMessageById() throws MessageNotFoundException {
        Message message = messageService.getMessageById(1L);
        assertNotNull(message);

    }

    @Test
    @Order(3)
    void testGetMessagesBetweenDoctorAndPatient() {
        List<Message> messages = messageService.getMessagesBetweenDoctorAndPatient(3L, 5L);
        assertFalse(messages.isEmpty());
    }

    @Test
    @Order(4)
    void testGetUnreadMessagesForPatient() {
        List<Message> messages = messageService.getUnreadMessagesForPatient(5L);
        assertFalse(messages.isEmpty());
        assertEquals(SenderRole.DOCTOR, messages.get(0).getSenderRole());
    }

    @Test
    @Order(5)
    void testGetUnreadMessagesForDoctor() {
        List<Message> messages = messageService.getUnreadMessagesForDoctor(3L);
        assertFalse(messages.isEmpty());
        assertEquals(SenderRole.PATIENT, messages.get(0).getSenderRole());
    }

    @Test
    @Order(6)
    void testMarkMessageAsRead() {
        boolean result = messageService.markMessageAsRead(1L);
        assertTrue(result);
    }

    @Test
    @Order(7)
    void testGetMessagesSentByDoctor() {
        List<Message> messages = messageService.getMessagesSentByDoctor(3L);
        assertFalse(messages.isEmpty());
        assertEquals(SenderRole.DOCTOR, messages.get(0).getSenderRole());
    }

    @Test
    @Order(8)
    void testGetMessagesSentByPatient() {
        List<Message> messages = messageService.getMessagesSentByPatient(5L);
        assertFalse(messages.isEmpty());
        assertEquals(SenderRole.PATIENT, messages.get(0).getSenderRole());
    }

    @Test
    @Order(9)
    void testDeleteMessage() {
        boolean deleted = messageService.deleteMessage(1L);
        assertTrue(deleted);
    }
}