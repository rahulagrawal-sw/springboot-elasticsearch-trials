package com.elk.book.controller.trades;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class KRIAggregationsController {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @RequestMapping(value = "/aggrBySupervisorAndKRI", method = RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody List aggrBySupervisorAndKRI() throws IOException {
        SearchRequest searchRequest = new SearchRequest("tradebreaches");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // Add aggregations
        AggregationBuilder aggregationBuilder =
                AggregationBuilders
                        .terms("supervisorId")
                        .field("supervisorId")
                        .subAggregation(
                                AggregationBuilders
                                        .terms("kriList")
                                        .field("kriList")
                        );

        searchSourceBuilder.aggregation(aggregationBuilder);
        searchRequest.source(searchSourceBuilder);

        // Get response
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<String> resultList = new ArrayList<>();

        Terms supverisorIdSums = searchResponse.getAggregations().get("supervisorId");
        for (Terms.Bucket bucket : supverisorIdSums.getBuckets()) {
            System.out.println("S : " + bucket.getKey() + " == " + bucket.getDocCount());
            Terms  kriListSums = bucket.getAggregations().get("kriList");
            for (Terms.Bucket bucketKRI : kriListSums.getBuckets()) {
                System.out.println(" KRI : " + bucketKRI.getKey() + " == " + bucketKRI.getDocCount());
            }
            resultList.add(bucket.toString());
        }

        return resultList;
    }

}
