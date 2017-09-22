package com.tale.service.impl;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import com.blade.kit.DateKit;
import com.tale.init.TaleConst;
import com.tale.model.Logs;
import com.tale.model.po.mbg.TLogs;
import com.tale.model.po.mbg.TLogsExample;
import com.tale.mybatis.dao.read.custom.TLogsReadDao;
import com.tale.mybatis.dao.write.custom.TLogsWriteDao;
import com.tale.service.LogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by biezhi on 2017/2/26.
 */
@Service
public class LogServiceImpl implements LogService {

    @Inject
    private ActiveRecord activeRecord;
    @Autowired
    private TLogsReadDao tLogsReadDao;
    @Autowired
    private TLogsWriteDao tLogsWriteDao;

    @Override
    public void save(String action, String data, String ip, Integer author_id) {
        Logs logs = new Logs();
        logs.setAction(action);
        logs.setData(data);
        logs.setIp(ip);
        logs.setAuthor_id(author_id);
        logs.setCreated(DateKit.getCurrentUnixTime());
        tLogsWriteDao.insertSelective(logs);
    }

    @Override
    public List<Logs> getLogs(int page, int limit) {
        if (page <= 0) {
            page = 1;
        }

        if (limit < 1 || limit > TaleConst.MAX_POSTS) {
            limit = 10;
        }
        TLogsExample example = new TLogsExample();
       // TLogsExample.Criteria criteria = example.createCriteria();
        //int total = tLogsReadDao.countByExample(example);
        example.setLimit(Long.valueOf(limit));
        example.setStart(Long.valueOf((page-1)*limit));
        List<TLogs> lists = tLogsReadDao.selectByExample(example);
        List<Logs> results = new ArrayList<>();
        for (TLogs logs : lists) {
            Logs log = new Logs();
            BeanUtils.copyProperties(logs, log);
            results.add(log);
        }
        //Paginator<Logs> logsPaginator = new Paginator((long)total, page, limit);
        //logsPaginator.setList(results);
        return results;
    }
}
