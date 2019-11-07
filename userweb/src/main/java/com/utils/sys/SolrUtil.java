package com.utils.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.*;

public class SolrUtil {

    //根据关键字返回指定数量的solr数据
    public static SolrDocumentList query(String keyWord, int start, int rows, String url) {
        HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        SolrQuery query = new SolrQuery();
        query.set("q", keyWord);
        query.setStart(start);
        query.setRows(rows);
        QueryResponse query1 = null;
        try {
            query1 = client.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return query1.getResults();
    }

    public static SolrDocumentList queryById(String kerWord, String url) {
        HttpSolrClient client = new HttpSolrClient.Builder(url).build();
        SolrQuery query = new SolrQuery();
        query.set("q", "id:" + kerWord);
        QueryResponse query1 = null;
        try {
            query1 = client.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return query1.getResults();
    }

}
