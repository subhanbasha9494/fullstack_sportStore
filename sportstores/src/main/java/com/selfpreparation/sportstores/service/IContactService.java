package com.selfpreparation.sportstores.service;

import com.selfpreparation.sportstores.dto.ContactRequestDto;

public interface IContactService {

    boolean saveContact(ContactRequestDto contactRequestDto);
}
