package com.footlink.footlink.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footlink.footlink.search.domain.SuggestItem;
import com.footlink.footlink.search.mapper.SearchMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {
	
	private final SearchMapper searchMapper;

	 @Override
	    public Map<String, List<SuggestItem>> suggest(String keyword, int limit) {
	        String q = keyword == null ? "" : keyword.trim();
	        if (q.isEmpty()) {
	            return Map.of("grounds", List.of(), "teams", List.of());
	        }
	        int capped = Math.max(1, Math.min(limit, 10));
	        List<SuggestItem> grounds = searchMapper.findStadiums(q, capped);
	        List<SuggestItem> teams   = searchMapper.findTeams(q, capped);
	        return Map.of("grounds", grounds, "teams", teams);
	    }
	}