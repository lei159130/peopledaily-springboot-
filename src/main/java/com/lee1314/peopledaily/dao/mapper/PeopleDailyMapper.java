package com.lee1314.peopledaily.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee1314.peopledaily.entity.PeopleDaily;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author leili
 * @since 2021-07-24
 */
@Repository
public interface PeopleDailyMapper extends BaseMapper<PeopleDaily> {

    @Select("SELECT id FROM people_daily WHERE seminar_id = #{seminarId,jdbcType=INTEGER}")
    List<Integer> selectIdsBySeminarId(@Param("seminarId") Integer seminarId);
}
