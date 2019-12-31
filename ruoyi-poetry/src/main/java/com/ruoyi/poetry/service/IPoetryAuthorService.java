package com.ruoyi.poetry.service;

import com.ruoyi.poetry.domain.PoetryAuthor;

import java.util.List;

/**
 * 作者Service接口
 * 
 * @author ruoyi
 * @date 2019-12-31
 */
public interface IPoetryAuthorService 
{
    /**
     * 查询作者
     * 
     * @param id 作者ID
     * @return 作者
     */
    public PoetryAuthor selectPoetryAuthorById(String id);

    /**
     * 查询作者列表
     * 
     * @param poetryAuthor 作者
     * @return 作者集合
     */
    public List<PoetryAuthor> selectPoetryAuthorList(PoetryAuthor poetryAuthor);

    /**
     * 新增作者
     * 
     * @param poetryAuthor 作者
     * @return 结果
     */
    public int insertPoetryAuthor(PoetryAuthor poetryAuthor);

    /**
     * 修改作者
     * 
     * @param poetryAuthor 作者
     * @return 结果
     */
    public int updatePoetryAuthor(PoetryAuthor poetryAuthor);

    /**
     * 批量删除作者
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePoetryAuthorByIds(String ids);

    /**
     * 删除作者信息
     * 
     * @param id 作者ID
     * @return 结果
     */
    public int deletePoetryAuthorById(String id);
}
