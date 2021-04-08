package com.elk.book.controller.trades;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class CreateBreachIndexController {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @RequestMapping(value = "/createIndex", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    Boolean createTradeBreachIndex(@RequestParam String indexName) throws IOException {

        deleteExistingIndex(indexName);

        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                                 .put("index.number_of_shards", 3)
                                 .put("index.number_of_replicas", 2)
        );

        XContentBuilder builder = buildXContentBuilder();
        request.mapping(builder);

        request.waitForActiveShards(ActiveShardCount.from(2));

        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

        if(acknowledged) {
            System.out.println("New index created : " + indexName);
            System.out.println("acknowledged : " + acknowledged);
            System.out.println("shardsAcknowledged : " + shardsAcknowledged);
        }

        return acknowledged;
    }

    private void deleteExistingIndex(String indexName) throws IOException {
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            if (acknowledgedResponse.isAcknowledged()) {
                System.out.println("Index deleted :" + indexName);
            }
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {
                System.out.println("Index not found for delete :" + indexName);
            }
        }
    }

    private XContentBuilder buildXContentBuilder() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("ptsSystem");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("tradeId");
                {
                    builder.field("type", "text");
                }
                builder.endObject();

                builder.startObject("tradeVersion");
                {
                    builder.field("type", "text");
                }
                builder.endObject();

                builder.startObject("tradeDate");
                {
                    builder.field("type", "date");
                }
                builder.endObject();

                builder.startObject("notionalAmt");
                {
                    builder.field("type", "double");
                }
                builder.endObject();

                builder.startObject("counterPartyCode");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("traderId");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("supervisorId");
                {
                    builder.field("type", "keyword");
                    //builder.field("fielddata", "true");
                }
                builder.endObject();

                builder.startObject("deskCode");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("businessLineCode");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("kriList");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();

        return builder;
    }
}
