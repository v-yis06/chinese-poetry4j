package com.ruoyi.poetry.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.poetry.domain.PoetryAuthor;
import com.ruoyi.poetry.mapper.PoetryAuthorMapper;
import com.ruoyi.poetry.service.IPoetryAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者Service业务层处理
 * 
 * @author ruoyi
 * @date 2019-12-31
 */
@Service
public class PoetryAuthorServiceImpl implements IPoetryAuthorService 
{
    @Autowired
    private PoetryAuthorMapper poetryAuthorMapper;

    /**
     * 查询作者
     * 
     * @param id 作者ID
     * @return 作者
     */
    @Override
    public PoetryAuthor selectPoetryAuthorById(String id)
    {
        return poetryAuthorMapper.selectPoetryAuthorById(id);
    }

    /**
     * 查询作者列表
     * 
     * @param poetryAuthor 作者
     * @return 作者
     */
    @Override
    public List<PoetryAuthor> selectPoetryAuthorList(PoetryAuthor poetryAuthor)
    {
        return poetryAuthorMapper.selectPoetryAuthorList(poetryAuthor);
    }

    /**
     * 新增作者
     * 
     * @param poetryAuthor 作者
     * @return 结果
     */
    @Override
    public int insertPoetryAuthor(PoetryAuthor poetryAuthor)
    {
        poetryAuthor.setCreateTime(DateUtils.getNowDate());
        return poetryAuthorMapper.insertPoetryAuthor(poetryAuthor);
    }

    /**
     * 修改作者
     * 
     * @param poetryAuthor 作者
     * @return 结果
     */
    @Override
    public int updatePoetryAuthor(PoetryAuthor poetryAuthor)
    {
        poetryAuthor.setUpdateTime(DateUtils.getNowDate());
        return poetryAuthorMapper.updatePoetryAuthor(poetryAuthor);
    }

    /**
     * 删除作者对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePoetryAuthorByIds(String ids)
    {
        return poetryAuthorMapper.deletePoetryAuthorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除作者信息
     * 
     * @param id 作者ID
     * @return 结果
     */
    @Override
    public int deletePoetryAuthorById(String id)
    {
        return poetryAuthorMapper.deletePoetryAuthorById(id);
    }

    @Override
    public int batchInsertPoetryAuthor(List<PoetryAuthor> poetryAuthors) {
        return poetryAuthorMapper.batchInsertPoetryAuthor(poetryAuthors);
    }
}
