package com.timekeeper.auth.biz.converter;

import com.timekeeper.auth.api.vo.ChildrenList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface ToChildrenList {

    ToChildrenList INSTANCE = Mappers.getMapper(ToChildrenList.class);

    default ChildrenList toChildrenList(List<String> childrenList) {
        return new ChildrenList(childrenList);
    }
}
