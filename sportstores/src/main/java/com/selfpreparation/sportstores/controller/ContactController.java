package com.selfpreparation.sportstores.controller;


import com.selfpreparation.sportstores.dto.ContactRequestDto;
import com.selfpreparation.sportstores.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/contacts")
@RequiredArgsConstructor
public class ContactController {

   private final IContactService iContactService;

   @PostMapping
    public ResponseEntity<String> saveContact(@RequestBody ContactRequestDto contactRequestDto){
       boolean isSaved = iContactService.saveContact(contactRequestDto);
       if(isSaved){
           return ResponseEntity.status(HttpStatus.CREATED).body("Request processed successfully");
       }else{
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured. Please try again or contact Dev team.");
       }
   }
}
