package com.ruoyi.generator.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @auther 易胜
 * @date 2019-12-30
 * @desc
 */
public class GenerateDetail implements Serializable {
    private static final long serialVersionUID = -164567294469931676L;
    private String beanName;
    private String beanComment;
    private List<BeanField> fields;

    public GenerateDetail() {
    }

    public String getBeanComment() {
        return this.beanComment;
    }

    public void setBeanComment(String beanComment) {
        this.beanComment = beanComment;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public List<BeanField> getFields() {
        return this.fields;
    }

    public void setFields(List<BeanField> fields) {
        this.fields = fields;
    }
}
