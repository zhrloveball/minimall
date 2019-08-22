package com.minimall.service;

import com.minimall.nosql.mongdb.document.MemberReadHistory;

import java.util.List;

/**
 * 会员浏览商品记录管理 Service
 */
public interface MemberReadHistoryService {

    /**
     * 生成浏览记录
     * @param history
     * @return
     */
    int create(MemberReadHistory history);

    /**
     * 批量删除浏览记录
     * @param ids
     * @return
     */
    int delete(List<String> ids);

    /**
     * 获取用户浏览历史记录
     * @param memberId
     * @return
     */
    List<MemberReadHistory> list(Long memberId);
}
