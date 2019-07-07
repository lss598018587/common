package com.pf.commons.api.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;

@XmlRootElement
@XmlSeeAlso({ ModelPageList.class })
public class Model<E> implements Serializable {

	private static final long serialVersionUID = -5011002158992496093L;

	//
	private int code = ModelStatusCodeConstants.OK;

	// 数据, 具体接口, 具体约定
	private E data;

	// 错误信息
	private String errorMsg;
	

	// 交互token
	private String token;
	
	public Model() {
	}

	public Model(E data) {
		this.data = (E) data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public Model setToken(String token) {
		this.token = token;
		return this;
	}

	public static <T> Model<T> buidSucc(T data) {
		Model<T> model = new Model<T>();
		model.setData(data);
		return model;
	}
	
	public static <T> Model<T> buidSucc() {
		Model<T> model = new Model<T>();
		model.setData(null);
		return model;
	}

	/**
	 * 
	 * @param code
	 *            {@link ModelStatusCodeConstants}
	 * @param errorMsg
	 * @return
	 */
	public static <T> Model<T> buidFail(int code, String errorMsg) {
		Model<T> model = new Model<T>();
		model.setCode(code);
		model.setErrorMsg(errorMsg);
		return model;
	}
	
	/**
	 * 创建业务失败
	 * @param errorMsg
	 * @return
	 */
	public static <T> Model<T> buidBusinessFail(String errorMsg) {
		Model<T> model = new Model<T>();
		model.setCode(ModelStatusCodeConstants.BUSINESS_ERROR);
		model.setErrorMsg(errorMsg);
		return model;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
	}


}
