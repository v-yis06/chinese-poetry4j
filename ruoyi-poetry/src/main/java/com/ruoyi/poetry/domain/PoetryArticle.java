package com.ruoyi.poetry.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 诗词文章对象 poetry_article
 * 
 * @author ruoyi
 * @date 2019-12-31
 */
public class PoetryArticle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    @NotBlank
    @Size(min = 0, max = 64, message = "标题长度不能超过64个字符")
    private String title;

    /** 作者 */
    @Excel(name = "作者")
    @NotBlank
    @Size(min = 0, max = 64, message = "作者长度不能超过64个字符")
    private String author;

    /** 段落 */
    @Excel(name = "段落")
    private String paragraphs;

    /** 段落序号 */
    @Excel(name = "段落序号")
    @NotBlank
    @Digits(integer = 2, fraction = 0)
    private Integer paragraphOrder;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setParagraphs(String paragraphs) 
    {
        this.paragraphs = paragraphs;
    }

    public String getParagraphs() 
    {
        return paragraphs;
    }
    public void setParagraphOrder(Integer paragraphOrder) 
    {
        this.paragraphOrder = paragraphOrder;
    }

    public Integer getParagraphOrder() 
    {
        return paragraphOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("author", getAuthor())
            .append("paragraphs", getParagraphs())
            .append("paragraphOrder", getParagraphOrder())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
