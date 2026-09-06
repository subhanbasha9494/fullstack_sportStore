package com.selfpractise;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringStudent {

    @Autowired
    private MathSub math;

    @Autowired
    private Physics physics;

    public void preparation(){
        System.out.println(math.getSubject());
        System.out.println(physics.getSubject());
    }
}
