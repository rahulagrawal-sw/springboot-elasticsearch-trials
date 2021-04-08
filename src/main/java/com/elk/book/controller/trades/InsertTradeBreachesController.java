package com.elk.book.controller.trades;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class InsertTradeBreachesController {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    private static String INDEX_NAME = "tradebreaches";

    @RequestMapping(value = "/tradebreaches/insert", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody long insertTradeBreach(@RequestParam long total) throws IOException {
        long startTime = System.currentTimeMillis();

        Random random = new Random();

        int cnt = 0;
        for(long i = 0; i < total; i++) {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("ptsSystem", StaticDataForGenerator.randomPTSSystem());
            jsonMap.put("tradeId", String.valueOf(random.nextInt(10000)));
            jsonMap.put("tradeVersion", StaticDataForGenerator.randomTradeVersion());
            jsonMap.put("tradeDate", StaticDataForGenerator.randomTradeDate());
            jsonMap.put("notionalAmt", StaticDataForGenerator.randomNotionalAmt());
            jsonMap.put("counterPartyCode", StaticDataForGenerator.randomCounterPartyCode());
            jsonMap.put("traderId", StaticDataForGenerator.randomTraderId());
            jsonMap.put("supervisorId", StaticDataForGenerator.randomSupervisorId());
            jsonMap.put("deskCode", StaticDataForGenerator.randomDeskCode());
            jsonMap.put("businessLineCode", StaticDataForGenerator.randomBusinessLineCode());
            jsonMap.put("kriList", Arrays.asList(StaticDataForGenerator.randomKRI(),
                    StaticDataForGenerator.randomKRI(),
                    StaticDataForGenerator.randomKRI()));

            IndexRequest indexRequest = new IndexRequest(INDEX_NAME)
                    .id(UUID.randomUUID().toString()).source(jsonMap);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            String documentId = indexResponse.getId();
            if(documentId != null) {
                System.out.println("INSERTED documentId : " + documentId);
                cnt++;
            }
        }
        long endTime = System.currentTimeMillis();
        long timeTakenInSec = (endTime - startTime) / 1000;
        System.out.println("TOTAL DOCUMENTS INSERTED : " + cnt);
        System.out.println("TOTAL TIME TAKEN TO INSERT In SECONDS : " + timeTakenInSec);
        return timeTakenInSec;
    }
}


