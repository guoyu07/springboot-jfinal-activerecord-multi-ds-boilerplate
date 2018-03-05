package com.gaols.study.studyboot.service;

import com.gaols.study.studyboot.db.config.TargetDataSource;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sf.jfinal.qs.model.master.CrmOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Transactional
    @TargetDataSource("master")
    public CrmOrder findById(String id) {
        CrmOrder p = new CrmOrder();
        Record record = Db.findFirst("select * from crm_order where id=?", id);
        p.setId(record.getStr("id"));
        p.setNumber(record.getStr("number"));
        return p;
    }
}
