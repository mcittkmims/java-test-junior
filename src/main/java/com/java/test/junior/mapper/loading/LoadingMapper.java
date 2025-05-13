package com.java.test.junior.mapper.loading;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



@Mapper
public interface LoadingMapper {
    void copyFromFile(@Param("fileAddress") String fileAddress, @Param("id") Long id);
}
