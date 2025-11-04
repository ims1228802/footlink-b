package com.footlink.footlink.search.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.footlink.footlink.search.domain.SuggestItem;

@Mapper
public interface SearchMapper {
	
	List<SuggestItem> findStadiums(@Param("q") String q, @Param("limit") int limit);

	List<SuggestItem> findTeams(@Param("q") String q, @Param("limit") int limit);
}
