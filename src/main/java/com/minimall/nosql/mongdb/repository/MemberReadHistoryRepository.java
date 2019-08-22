package com.minimall.nosql.mongdb.repository;

import com.minimall.nosql.mongdb.document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @description: 会员浏览商品历史 Repository
 * @author: Bran.Zuo
 * @create: 2019-08-22 14:31
 **/
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {

    /**
     * 根据会员ID按时间倒叙获取浏览记录
     * @param memberId 会员ID
     * @return
     */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
