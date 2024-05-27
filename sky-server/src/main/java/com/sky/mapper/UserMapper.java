package com.sky.mapper;

import com.sky.entity.AddressBook;
import com.sky.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {



    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    @Select("select * from user")
    User getById(Long id);

    @Select("select count(*) from address_book")
    Integer countByMap(Map map);

}
