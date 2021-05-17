package com.yh.cloud.db.model.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.cloud.base.util.CamelTransferUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author yanghan
 * @date 2020/7/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPageQuery<T> implements Serializable {
    @ApiModelProperty("分页：第几页")
    private Integer pageNum = 1;
    @ApiModelProperty("分页：每页大小")
    private Integer pageSize;
    @ApiModelProperty("升序")
    private String order$ASC;
    @ApiModelProperty("降序")
    private String order$DESC;

    public static final String NO_PAGE = "noPage";

    public IPage<T> initPageQuery() {
        IPage<T> queryPage = new Page<>();
        if (null == pageNum && null == pageSize) {
            return queryPage;
        }
        queryPage.setCurrent(null == pageNum ? 1 : pageNum);
        queryPage.setSize(null == pageSize ? 10 : pageSize);
        return queryPage;
    }

    public QueryWrapper<T> initFilter(QueryWrapper<T> queryWrapper, List<String> passes) {
        // 当前对象属性
        Field[] fields = this.getDeclaredFields();

        Object queryObj = this;

        for (Field declaredField : fields) {
            String fieldName = declaredField.getName();
            Object fieldValue = null;

            // 过滤掉特殊值
            if (null != passes && passes.contains(fieldName)) {
                continue;
            }

            try {
                declaredField.setAccessible(true);
                fieldValue = declaredField.get(queryObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null == fieldValue) {
                continue;
            }

            String[] strs = fieldName.split("\\$");
            String sqlKey = CamelTransferUtil.camelToUnderline(strs[0]);
            QueryOP queryOP = QueryOP.getByVal(strs.length > 1 ? strs[1] : "");
            if (null != queryOP) {
                switch (queryOP) {
                    case GREAT:
                        queryWrapper.gt(sqlKey, fieldValue);
                        break;
                    case LESS:
                        queryWrapper.lt(sqlKey, fieldValue);
                        break;
                    case EQUAL:
                        String[] pArray = fieldValue.toString().split("\\|");
                        if (pArray.length > 1) {
                            queryWrapper.in(sqlKey, (Object) pArray);
                        } else {
                            queryWrapper.eq(sqlKey, fieldValue);
                        }
                        break;
                    case NOT_EQUAL:
                        queryWrapper.ne(sqlKey, fieldValue);
                        break;
                    case RIGHT_LIKE:
                        queryWrapper.likeRight(sqlKey, fieldValue);
                        break;
                    case IN:
                        List<String> pList = (List<String>) fieldValue;
                        queryWrapper.in(sqlKey, pList.toArray());
                        break;
                    case ORDER_BY_ASC:
                        queryWrapper.orderByAsc(CamelTransferUtil.camelToUnderline(fieldValue.toString()));
                        break;
                    case ORDER_BY_DESC:
                        queryWrapper.orderByDesc(CamelTransferUtil.camelToUnderline(fieldValue.toString()));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + queryOP);
                }
            }
        }

        return queryWrapper;
    }

    /**
     * 返回类属性 todo: 若是多层继承，请重写该方法
     *
     * @return java.lang.reflect.Field[]
     * @author yanghan
     * @date 2020/11/26
     */
    protected Field[] getDeclaredFields() {

        Class clazz = this.getClass();
        // 当前对象属性
        Field[] currentFields = clazz.getDeclaredFields();
        Class pageClazz = clazz.getSuperclass();
        if (pageClazz != AbstractPageQuery.class) {
            return currentFields;
        }
        // 分页对象属性
        Field[] pageFields = pageClazz.getDeclaredFields();
        // 所有属性
        Field[] fields = Arrays.copyOf(currentFields, currentFields.length + pageFields.length);
        System.arraycopy(pageFields, 0, fields, currentFields.length, pageFields.length);
        return fields;
    }
}
