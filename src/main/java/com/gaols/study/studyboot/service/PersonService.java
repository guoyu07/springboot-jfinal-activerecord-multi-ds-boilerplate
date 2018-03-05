package com.gaols.study.studyboot.service;

import com.gaols.study.studyboot.db.config.TargetDataSource;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sf.jfinal.qs.model.slave.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    @Transactional
    @TargetDataSource("slave")
    public Person findById(int id) {
        Person p = new Person();
        Record record = Db.findFirst("select * from person where id=?", id);
        p.setName(record.getStr("name"));
        p.setId(record.getInt("id"));
        return p;
    }

    @Transactional(rollbackFor = Exception.class)
    @TargetDataSource("slave")
    public Person create(String name, int age, String address) {
        Person p = new Person();
        p.setName(name);
        p.setAge(age);
        p.setAddress(address);
        p.save();
        return p;
    }
}
