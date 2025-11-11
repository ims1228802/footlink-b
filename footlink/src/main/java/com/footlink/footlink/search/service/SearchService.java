package com.footlink.footlink.search.service;

import java.util.List;
import java.util.Map;

import com.footlink.footlink.search.domain.SuggestItem;

public interface SearchService {

	Map<String, List<SuggestItem>> suggest(String keyword, int limit);
}
