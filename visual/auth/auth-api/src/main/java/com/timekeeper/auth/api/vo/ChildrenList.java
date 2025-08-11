package com.timekeeper.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * ChildrenList 类用于封装一组子节点名称的列表。
 * 通常用于表示某个实体的多个子项名称集合。
 */
@Data
@AllArgsConstructor
public class ChildrenList {

    /**
     * childrenNames 是存储子节点名称的字符串列表。
     */
    List<String> childrenNames;
}
