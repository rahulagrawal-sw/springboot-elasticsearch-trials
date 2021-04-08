package com.elk.book.controller;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class ElasticsearchCRUDController {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @RequestMapping(value = "/movies", method = RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody List getAllMovieNames() throws IOException {
        SearchRequest searchRequest = new SearchRequest("movies");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getSuccessfulShards());
        System.out.println(searchResponse.getClusters());

        List<Map<String, Object>> resultList = new ArrayList<>();

        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println("sourceAsString : " + sourceAsString);

            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            resultList.add(sourceAsMap);
            for (Map.Entry<String, Object> entry : sourceAsMap.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }

        return resultList;
    }

    @RequestMapping(value = "/movies/add", method = RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody String addMovie() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("year", 2005);
        jsonMap.put("title", "Kaho na pyar hai");
        jsonMap.put("genre", Arrays.asList("Action", "Romance"));

        IndexRequest indexRequest = new IndexRequest("movies")
                .id(UUID.randomUUID().toString()).source(jsonMap);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        String documentId = indexResponse.getId();
        System.out.println("documentId : " + documentId);

        return documentId;
    }
}
