package com.hexaware.microservice.hospitalmangement.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.microservice.hospitalmangement.message.dto.MessageDTO;
import com.hexaware.microservice.hospitalmangement.message.entity.Message;
import com.hexaware.microservice.hospitalmangement.message.exception.MessageNotFoundException;
import com.hexaware.microservice.hospitalmangement.message.repository.MessageRepository;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message sendMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setDoctorId(messageDTO.getDoctorId());
        message.setPatientId(messageDTO.getPatientId());
        message.setSenderRole(messageDTO.getSenderRole());
        message.setMessage(messageDTO.getMessage());
        message.setRead(false);
        return messageRepository.save(message);
    }

    @Override
    public Message getMessageById(Long messageId) throws MessageNotFoundException {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("Message not found"));
    }

    @Override
    public List<Message> getMessagesBetweenDoctorAndPatient(Long doctorId, Long patientId) {
        return messageRepository.findByDoctorIdAndPatientIdOrderBySentAtAsc(doctorId, patientId);
    }

    @Override
    public List<Message> getUnreadMessagesForPatient(Long patientId) {
        return messageRepository.findByPatientIdAndIsReadFalseAndSenderRole(patientId, Message.SenderRole.DOCTOR);
    }

    @Override
    public List<Message> getUnreadMessagesForDoctor(Long doctorId) {
        return messageRepository.findByDoctorIdAndIsReadFalseAndSenderRole(doctorId, Message.SenderRole.PATIENT);
    }

    @Override
    public boolean markMessageAsRead(Long messageId) {
        return messageRepository.findById(messageId).map(msg -> {
            msg.setRead(true);
            messageRepository.save(msg);
            return true;
        }).orElse(false);
    }

    @Override
    public List<Message> getMessagesSentByDoctor(Long doctorId) {
        return messageRepository.findByDoctorIdAndSenderRole(doctorId, Message.SenderRole.DOCTOR);
    }

    @Override
    public List<Message> getMessagesSentByPatient(Long patientId) {
        return messageRepository.findByPatientIdAndSenderRole(patientId, Message.SenderRole.PATIENT);
    }

    @Override
    public boolean deleteMessage(Long messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }
}
