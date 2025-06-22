package com.hexaware.microservice.hospitalmangement.message.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.microservice.hospitalmangement.message.dto.MessageDTO;
import com.hexaware.microservice.hospitalmangement.message.entity.Message;
import com.hexaware.microservice.hospitalmangement.message.exception.MessageNotFoundException;

public interface IMessageService {

    Message sendMessage(MessageDTO messageDTO);

    Message getMessageById(Long messageId) throws MessageNotFoundException;

    List<Message> getMessagesBetweenDoctorAndPatient(Long doctorId, Long patientId);

    List<Message> getUnreadMessagesForPatient(Long patientId);

    List<Message> getUnreadMessagesForDoctor(Long doctorId);

    boolean markMessageAsRead(Long messageId);

    List<Message> getMessagesSentByDoctor(Long doctorId);

    List<Message> getMessagesSentByPatient(Long patientId);

    boolean deleteMessage(Long messageId);


}
