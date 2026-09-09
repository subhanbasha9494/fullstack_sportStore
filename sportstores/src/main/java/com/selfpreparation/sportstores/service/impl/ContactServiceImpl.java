package com.selfpreparation.sportstores.service.impl;

import com.selfpreparation.sportstores.dto.ContactRequestDto;
import com.selfpreparation.sportstores.entity.Contact;
import com.selfpreparation.sportstores.repository.ContactRepository;
import com.selfpreparation.sportstores.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto){
        try {
            Contact contact = transformToEntity(contactRequestDto);
            contact.setCreatedAt(Instant.now());
            contact.setCreatedBy(contactRequestDto.getName());
            contactRepository.save(contact);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }


    private Contact transformToEntity(ContactRequestDto contactRequestDto) {
        Contact contact = new Contact();
        contact.setName(contactRequestDto.getName());
        contact.setEmail(contactRequestDto.getEmail());
        contact.setMobileNumber(contactRequestDto.getMobileNumber());
        contact.setMessage(contactRequestDto.getMessage());
        return contact;
    }
}
