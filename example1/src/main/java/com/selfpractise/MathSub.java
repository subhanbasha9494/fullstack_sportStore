package com.selfpractise;

import org.springframework.stereotype.Component;

@Component
public class MathSub implements Subject{
    @Override
    public String getSubject(){
        return "Mathematics";
    }
}
