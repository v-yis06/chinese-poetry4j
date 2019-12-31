package com.ruoyi.poetry.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.poetry.domain.PoetryArticle;
import com.ruoyi.poetry.mapper.PoetryArticleMapper;
import com.ruoyi.poetry.service.IPoetryArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 诗词文章Service业务层处理
 * 
 * @author ruoyi
 * @date 2019-12-31
 */
@Service
public class PoetryArticleServiceImpl implements IPoetryArticleService 
{
    @Autowired
    private PoetryArticleMapper poetryArticleMapper;

    /**
     * 查询诗词文章
     * 
     * @param id 诗词文章ID
     * @return 诗词文章
     */
    @Override
    public PoetryArticle selectPoetryArticleById(String id)
    {
        return poetryArticleMapper.selectPoetryArticleById(id);
    }

    /**
     * 查询诗词文章列表
     * 
     * @param poetryArticle 诗词文章
     * @return 诗词文章
     */
    @Override
    public List<PoetryArticle> selectPoetryArticleList(PoetryArticle poetryArticle)
    {
        return poetryArticleMapper.selectPoetryArticleList(poetryArticle);
    }

    /**
     * 新增诗词文章
     * 
     * @param poetryArticle 诗词文章
     * @return 结果
     */
    @Override
    public int insertPoetryArticle(PoetryArticle poetryArticle)
    {
        poetryArticle.setCreateTime(DateUtils.getNowDate());
        return poetryArticleMapper.insertPoetryArticle(poetryArticle);
    }

    /**
     * 修改诗词文章
     * 
     * @param poetryArticle 诗词文章
     * @return 结果
     */
    @Override
    public int updatePoetryArticle(PoetryArticle poetryArticle)
    {
        poetryArticle.setUpdateTime(DateUtils.getNowDate());
        return poetryArticleMapper.updatePoetryArticle(poetryArticle);
    }

    /**
     * 删除诗词文章对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePoetryArticleByIds(String ids)
    {
        return poetryArticleMapper.deletePoetryArticleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除诗词文章信息
     * 
     * @param id 诗词文章ID
     * @return 结果
     */
    @Override
    public int deletePoetryArticleById(String id)
    {
        return poetryArticleMapper.deletePoetryArticleById(id);
    }
}
