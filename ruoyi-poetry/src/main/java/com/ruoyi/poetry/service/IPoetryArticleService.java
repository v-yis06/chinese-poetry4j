package com.ruoyi.poetry.service;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.poetry.domain.PoetryArticle;

import java.util.List;

/**
 * 诗词文章Service接口
 * 
 * @author yisheng
 * @date 2020-01-02
 */
public interface IPoetryArticleService 
{
    /**
     * 查询诗词文章
     * 
     * @param id 诗词文章ID
     * @return 诗词文章
     */
    public PoetryArticle selectPoetryArticleById(Long id);

    /**
     * 查询诗词文章列表
     * 
     * @param poetryArticle 诗词文章
     * @return 诗词文章集合
     */
    public List<PoetryArticle> selectPoetryArticleTreeList(PoetryArticle poetryArticle);

    /**
     * 查询诗词文章列表
     *
     * @param poetryArticle 诗词文章
     * @return 诗词文章集合
     */
    public List<PoetryArticle> selectPoetryArticleList(PoetryArticle poetryArticle);

    /**
     * 新增诗词文章
     * 
     * @param poetryArticle 诗词文章
     * @return 结果
     */
    public int insertPoetryArticle(PoetryArticle poetryArticle);

    /**
     * 修改诗词文章
     * 
     * @param poetryArticle 诗词文章
     * @return 结果
     */
    public int updatePoetryArticle(PoetryArticle poetryArticle);

    /**
     * 批量删除诗词文章
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePoetryArticleByIds(String ids);

    /**
     * 删除诗词文章信息
     * 
     * @param id 诗词文章ID
     * @return 结果
     */
    public int deletePoetryArticleById(Long id);

    /**
     * 查询诗词文章树列表
     * 
     * @return 所有诗词文章信息
     */
    public List<Ztree> selectPoetryArticleTree();

    /**
     * 获取主键最大值
     * @return
     */
    public Long getMaxKeyId();
}
