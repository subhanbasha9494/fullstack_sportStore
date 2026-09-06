package com.selfpractise;

import org.springframework.stereotype.Component;

@Component
public class Physics implements Subject{
    @Override
    public String getSubject(){
        return "Physics";
    }
}
