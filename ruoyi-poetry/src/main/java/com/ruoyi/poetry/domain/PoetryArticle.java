package com.ruoyi.poetry.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 诗词文章对象 poetry_article
 * 
 * @author yisheng
 * @date 2020-01-02
 */
public class PoetryArticle extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 段落 */
    @Excel(name = "段落")
    private String content;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 分类 */
    @Excel(name = "分类")
    private Integer type;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("content", getContent())
            .append("orderNum", getOrderNum())
            .append("author", getAuthor())
            .append("type", getType())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
