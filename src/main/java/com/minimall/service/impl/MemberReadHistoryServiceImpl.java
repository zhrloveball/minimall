package com.minimall.service.impl;

import com.minimall.nosql.mongdb.document.MemberReadHistory;
import com.minimall.nosql.mongdb.repository.MemberReadHistoryRepository;
import com.minimall.service.MemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 会员浏览记录管理Service实现类
 * @author: Bran.Zuo
 * @create: 2019-08-22 14:37
 **/
@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {

    @Autowired
    private MemberReadHistoryRepository repository;


    @Override
    public int create(MemberReadHistory history) {
        history.setId(null);
        history.setCreateTime(new Date());
        repository.save(history);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> deleteList = new ArrayList<>();
        for (String id : ids) {
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            deleteList.add(memberReadHistory);
        }
        repository.deleteAll(deleteList);
        return ids.size();
    }

    @Override
    public List<MemberReadHistory> list(Long memberId) {
        return repository.findByMemberIdOrderByCreateTimeDesc(memberId);
    }
}
