package com.hexaware.microservice.hospitalmangement.message.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.microservice.hospitalmangement.message.dto.MessageDTO;
import com.hexaware.microservice.hospitalmangement.message.entity.Message;
import com.hexaware.microservice.hospitalmangement.message.exception.MessageNotFoundException;
import com.hexaware.microservice.hospitalmangement.message.service.IMessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

    @Autowired
    private IMessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageDTO) {
        Message savedMessage = messageService.sendMessage(messageDTO);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("getbyid/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) throws MessageNotFoundException {
        Message message = messageService.getMessageById(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/messageBetween/doctor/{doctorId}/patient/{patientId}")
    public ResponseEntity<List<Message>> getMessagesBetweenDoctorAndPatient(
            @PathVariable Long doctorId,
            @PathVariable Long patientId) {
        List<Message> messages = messageService.getMessagesBetweenDoctorAndPatient(doctorId, patientId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unread/patient/{patientId}")
    public ResponseEntity<List<Message>> getUnreadMessagesForPatient(@PathVariable Long patientId) {
        List<Message> messages = messageService.getUnreadMessagesForPatient(patientId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unread/doctor/{doctorId}")
    public ResponseEntity<List<Message>> getUnreadMessagesForDoctor(@PathVariable Long doctorId) {
        List<Message> messages = messageService.getUnreadMessagesForDoctor(doctorId);
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/mark-read/{id}")
    public ResponseEntity<String> markMessageAsRead(@PathVariable Long id) {
        boolean success = messageService.markMessageAsRead(id);
        if (success) {
            return ResponseEntity.ok("Message marked as read");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sent/doctor/{doctorId}")
    public ResponseEntity<List<Message>> getMessagesSentByDoctor(@PathVariable Long doctorId) {
        List<Message> messages = messageService.getMessagesSentByDoctor(doctorId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sent/patient/{patientId}")
    public ResponseEntity<List<Message>> getMessagesSentByPatient(@PathVariable Long patientId) {
    	System.out.println("hello message service");
        List<Message> messages = messageService.getMessagesSentByPatient(patientId);
        return ResponseEntity.ok(messages);
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        boolean deleted = messageService.deleteMessage(id);
        if (deleted) {
            return ResponseEntity.ok("Message deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
