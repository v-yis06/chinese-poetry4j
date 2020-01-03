package com.ruoyi.poetry.mapper;

import com.ruoyi.poetry.domain.PoetryArticle;
import java.util.List;

/**
 * 诗词文章Mapper接口
 * 
 * @author yisheng
 * @date 2020-01-02
 */
public interface PoetryArticleMapper 
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
     * 删除诗词文章
     * 
     * @param id 诗词文章ID
     * @return 结果
     */
    public int deletePoetryArticleById(Long id);

    /**
     * 批量删除诗词文章
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePoetryArticleByIds(String[] ids);

    Long getMaxKeyId();
}
