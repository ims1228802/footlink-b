package com.footlink.footlink.search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.search.domain.SuggestItem;
import com.footlink.footlink.search.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {
	
	private final SearchService searchService;

	@GetMapping("/search")
    public Map<String, List<SuggestItem>> suggest(
            @RequestParam("q") String q,
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return searchService.suggest(q, limit);
    }
}
