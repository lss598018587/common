package com.pf.commons.api.controller;

import com.pf.commons.api.model.Model;
import com.pf.commons.api.model.ModelStatusCodeConstants;
import com.pf.commons.exception.BussinessException;
import com.pf.commons.model.OrderBy;
import com.pf.commons.model.Query;
import com.pf.commons.util.MobileUtils;
import com.pf.commons.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected static final String DEFAULT_SERVER_ERROR_MSG = "服务器出错了, 请联系客服！";

	@ResponseBody
	@ExceptionHandler(value = { Exception.class })
	public Model<String> exceptionHandle(HttpServletRequest request, Exception e) {

        String requestInfo = getRequestInfo(request);

		Model<String> model = new Model<String>();
		if (e instanceof BussinessException) {
			logger.warn(requestInfo, e);
			model.setCode(ModelStatusCodeConstants.BUSINESS_ERROR);
			model.setErrorMsg(e.getMessage());
		} else {
			logger.error(requestInfo, e);
			model.setCode(ModelStatusCodeConstants.INTERNAL_ERROR);
			model.setErrorMsg(DEFAULT_SERVER_ERROR_MSG);
		}
		return model;
	}

	public void makeUpQuery(HttpServletRequest request, Query query){
        String offsetStr  = request.getParameter("offset");
        String limitStr  = request.getParameter("limit");
        String sort  = request.getParameter("sort");
        String sortOrder  = request.getParameter("sortOrder");
        if(StringUtils.isBlank(offsetStr)){
            offsetStr="0";
        }
        if(StringUtils.isBlank(limitStr)){
            limitStr="10";
        }
        if(StringUtils.isNotBlank(sort) && StringUtils.isBlank(sortOrder)){
            OrderBy orderBy = new OrderBy(sort,sortOrder);
            query.setOrderBy(orderBy);

        }
        query.setLimit(Integer.parseInt(limitStr));
        query.setOffset(Integer.parseInt(offsetStr));
    }

    public String getRequestInfo(HttpServletRequest request){

        StringBuilder errorStrBuilder = new StringBuilder();
        // RequestURL
        errorStrBuilder.append("RequestURL:[");
        errorStrBuilder.append(request.getRequestURL());
        errorStrBuilder.append("]");
        errorStrBuilder.append(", ");
        // RequestBody
        errorStrBuilder.append("RequestBody:[");
        errorStrBuilder.append(RequestUtils.getRequestBody(request));
        errorStrBuilder.append("]");
        errorStrBuilder.append(", ");
        // MobileDevice
        errorStrBuilder.append("MobileDevice:[");
        errorStrBuilder.append(MobileUtils.getMobileDeviceFromRequest(request));
        errorStrBuilder.append("]");
        errorStrBuilder.append(", ");
        // ClientVersion
        errorStrBuilder.append("ClientVersion:[");
        errorStrBuilder.append(MobileUtils.getClientVersionFromRequest(request));
        errorStrBuilder.append("]");
        // pluginVersion
        errorStrBuilder.append("PluginVersion:[");
        errorStrBuilder.append(MobileUtils.getPluginVersionFromRequest(request));
        errorStrBuilder.append("]");
        // pluginName
        errorStrBuilder.append("PluginName:[");
        errorStrBuilder.append(MobileUtils.getPluginNameFromRequest(request));
        errorStrBuilder.append("]");

        return errorStrBuilder.toString();
    }
}
