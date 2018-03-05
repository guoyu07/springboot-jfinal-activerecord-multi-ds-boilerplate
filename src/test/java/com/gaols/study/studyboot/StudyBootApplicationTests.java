package com.gaols.study.studyboot;

import com.gaols.study.studyboot.service.OrderService;
import com.gaols.study.studyboot.service.PersonService;
import com.sf.jfinal.qs.model.master.CrmOrder;
import com.sf.jfinal.qs.model.slave.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyBootApplicationTests {

    @Autowired
    PersonService personService;

    @Autowired
    OrderService orderService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGet() {
        Person person = personService.findById(16);
        System.out.println(person.getName());
        CrmOrder order = orderService.findById("0003a278109c40499e2680c77b70b580");
        System.out.println(order.getNumber());
    }

    @Test
    public void testSave() {
        Person person = personService.create("gaols11", 10, "abc");
        System.out.println(person.getName());
    }
}
