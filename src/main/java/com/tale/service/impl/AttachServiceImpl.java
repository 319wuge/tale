package com.tale.service.impl;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import com.blade.kit.DateKit;
import com.tale.model.Attach;
import com.tale.mybatis.dao.read.custom.TAttachReadDao;
import com.tale.mybatis.dao.write.custom.TAttachWriteDao;
import com.tale.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by biezhi on 2017/2/23.
 */
@Service
public class AttachServiceImpl implements AttachService {

    @Inject
    private ActiveRecord activeRecord;
    @Autowired
    private TAttachWriteDao tAttachWriteDao;
    @Autowired
    private TAttachReadDao attachReadDao;

    @Override
    public Attach save(String fname, String fkey, String ftype, Integer author) {
        Attach attach = new Attach();
        attach.setFname(fname);
        attach.setAuthor_id(author);
        attach.setFkey(fkey);
        attach.setFtype(ftype);
        attach.setCreated(DateKit.getCurrentUnixTime());
        activeRecord.insert(attach);
        return attach;
    }

    @Override
    public Attach byId(Integer id) {
        if(null != id){
            return activeRecord.byId(Attach.class, id);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (null != id) {
            activeRecord.delete(Attach.class, id);
        }
    }

    @Override
    public Paginator<Attach> getAttachs(Take take) {
        if (null != take) {
            return activeRecord.page(take);
        }
        return null;
    }
}
