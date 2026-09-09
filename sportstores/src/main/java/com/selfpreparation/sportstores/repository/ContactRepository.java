package com.selfpreparation.sportstores.repository;

import com.selfpreparation.sportstores.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
