package com.sky.mapper;

import com.sky.entity.AddressBook;
import com.sky.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {



    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    User getById(Long id);

}
