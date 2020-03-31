//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.pagehelper;

import com.github.pagehelper.dialect.AbstractHelperDialect;
import com.github.pagehelper.page.PageAutoDialect;
import com.github.pagehelper.page.PageMethod;
import com.github.pagehelper.page.PageParams;
import com.github.pagehelper.util.StringUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 *
 */
public class PageHelper extends PageMethod implements Dialect {
    private PageParams pageParams;
    private PageAutoDialect autoDialect;

    public PageHelper() {
    }

    public boolean skip(MappedStatement ms, Object parameterObject, RowBounds rowBounds) {
        if (ms.getId().endsWith("_COUNT")) {
            throw new RuntimeException("在系统中发现了多个分页插件，请检查系统配置!");
        } else {
            Page page = this.pageParams.getPage(parameterObject, rowBounds);
            if (page == null) {
                return true;
            } else {
                if (StringUtil.isEmpty(page.getCountColumn())) {
                    page.setCountColumn(this.pageParams.getCountColumn());
                }

                this.autoDialect.initDelegateDialect(ms);
                return false;
            }
        }
    }

    public boolean beforeCount(MappedStatement ms, Object parameterObject, RowBounds rowBounds) {
        return this.autoDialect.getDelegate().beforeCount(ms, parameterObject, rowBounds);
    }

    public String getCountSql(MappedStatement ms, BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey countKey) {
        return this.autoDialect.getDelegate().getCountSql(ms, boundSql, parameterObject, rowBounds, countKey);
    }

    public boolean afterCount(long count, Object parameterObject, RowBounds rowBounds) {
        return this.autoDialect.getDelegate().afterCount(count, parameterObject, rowBounds);
    }

    public Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql, CacheKey pageKey) {
        return this.autoDialect.getDelegate().processParameterObject(ms, parameterObject, boundSql, pageKey);
    }

    public boolean beforePage(MappedStatement ms, Object parameterObject, RowBounds rowBounds) {
        return this.autoDialect.getDelegate().beforePage(ms, parameterObject, rowBounds);
    }

    public String getPageSql(MappedStatement ms, BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey pageKey) {
        return this.autoDialect.getDelegate().getPageSql(ms, boundSql, parameterObject, rowBounds, pageKey);
    }

    public String getPageSql(String sql, Page page, RowBounds rowBounds, CacheKey pageKey) {
        return this.autoDialect.getDelegate().getPageSql(sql, page, pageKey);
    }

    public Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds) {
        AbstractHelperDialect delegate = this.autoDialect.getDelegate();
        return delegate != null ? delegate.afterPage(pageList, parameterObject, rowBounds) : pageList;
    }

    public void afterAll() {
        AbstractHelperDialect delegate = this.autoDialect.getDelegate();
        if (delegate != null) {
            delegate.afterAll();
            this.autoDialect.clearDelegate();
        }

        clearPage();
    }

    public void setProperties(Properties properties) {
        setStaticProperties(properties);
        this.pageParams = new PageParams();
        this.autoDialect = new PageAutoDialect();
        this.pageParams.setProperties(properties);
        this.autoDialect.setProperties(properties);
    }
}
