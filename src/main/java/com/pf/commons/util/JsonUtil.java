package com.pf.commons.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Set;

public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static JSONObject getJSONObjectFromRequestStream(HttpServletRequest req) {

		JSONObject json = null;
		String jsonStr = null;
		try {
			jsonStr = RequestUtils.getRequestBody(req);
			json = JSONObject.parseObject(jsonStr);
		} catch (Exception e) {
			logger.error("Req body: " + jsonStr, e);
		}
		return json;
	}

	public static JSONArray getJSONArrayFromRequestStream(HttpServletRequest req) {
		JSONArray json = null;
		BufferedReader reader = null;
		String jsonStr = null;
		try {
			jsonStr = RequestUtils.getRequestBody(req);
			json = JSONArray.parseArray(jsonStr);
		} catch (Exception e) {
			logger.error("Req body: " + jsonStr, e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return json;
	}

	private static void escapeJSONArray(JSONArray jsonArray){

		if(isEmptyJsonArray(jsonArray)){
			return;
		}

		int size = jsonArray.size();
		JSONObject jsonObject;
		for(int i = 0; i < size; i++) {

			jsonObject = jsonArray.getJSONObject(i);
			if (isEmpty(jsonObject)) {
				continue;
			}

			escapeJSONObject(jsonObject);
		}
	}

	public static boolean isEmptyJsonArray(Object obj) throws JSONException {
		if (obj == null)
			return true;

		if (!(obj instanceof JSONArray)) {
			throw new JSONException("param does not instanceof JSONArray");
		}

		return ((JSONArray) obj).isEmpty();
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;

		if (!(obj instanceof JSONObject)) {
			throw new JSONException("param does not instanceof JSONObject");
		}

		return ((JSONObject) obj).isEmpty();
	}

	public static <T> T getObjectFromRequest(HttpServletRequest req, Class<T> cls) {
		JSONObject jsonObject = getJSONObjectFromRequestStream(req);
		return JSONObject.toJavaObject(jsonObject, cls);
	}

	private static void escapeJSONObject(JSONObject jsonObject){

		if(jsonObject == null){
			return;
		}

		Set<String> keys = jsonObject.keySet();
		if(CollectionUtils.isEmpty(keys)){
			return;
		}

		String content;
		for (String key : keys) {

			Object object = jsonObject.get(key);
			if(object instanceof String){
				content = StringEscapeUtil.escapeHtml((String) object);
				content = StringEscapeUtil.escapeJavaScript(content);
				content = StringEscapeUtils.escapeSql(content);
				jsonObject.put(key, content);
			}
		}

	}
}
