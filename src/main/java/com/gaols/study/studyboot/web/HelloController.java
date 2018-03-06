package com.gaols.study.studyboot.web;

import com.gaols.study.studyboot.service.PersonService;
import com.sf.jfinal.qs.model.slave.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    PersonService personService;

    @RequestMapping("/hello")
    public String index() {
        logger.info("enter the index action");
        return "Hello World";
    }

    @RequestMapping("/person")
    public Person person() {
        logger.info("received a connection");
        return personService.findById(16);
    }
}
