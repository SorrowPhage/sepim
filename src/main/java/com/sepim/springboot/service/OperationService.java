package com.sepim.springboot.service;

/**
 * @author: SorrowPhage
 * @date: 2023/7/31
 */
public interface OperationService {

    /**
     * 记录新增操作
     *
     * @param id   新增文章id
      */
    void insert(String id,String uid);

    /**
     * 记录修改操作
     * @param id 文章id
     */
    void update(String id);

    /**
     * 文章删除时，删掉关联操作
     * @param id  文章id
     */
    void delete(String id);


}
