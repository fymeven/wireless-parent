package cn._51even.wireless.core.elasticsearch;

import com.alibaba.fastjson.JSONObject;

public class ElasticsearchModel {

    private String _id;

    private JSONObject _source;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public JSONObject get_source() {
        return _source;
    }

    public void set_source(JSONObject _source) {
        this._source = _source;
    }
}
