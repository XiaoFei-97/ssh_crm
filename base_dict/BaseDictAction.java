package com.jzfblog.crm.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.jzfblog.crm.domain.BaseDict;
import com.jzfblog.crm.service.BaseDictService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class BaseDictAction extends ActionSupport implements ModelDriven<BaseDict> {

	// �����ֵ䣺ģ������
	private BaseDict baseDict = new BaseDict();

	public BaseDict getModel() {
		return baseDict;
	}
	
	// ����ע��
	private BaseDictService baseDictService;

	public void setBaseDictService(BaseDictService baseDictService) {
		this.baseDictService = baseDictService;
	}
	
	/**
	 * �����������Ʋ�ѯ�ֵ�ķ�����findByTypeCode
	 * @throws IOException 
	 */
	public String findByTypeCode() throws IOException {
		
		List<BaseDict> list = baseDictService.findByTypeCode(baseDict.getDict_type_code());
		// ��listת��json��ʽ
		/**
		 * JSONArray���������list����ת��json
		 * JSONObject���������Mapת����json
		 * JSONConfig��תJSON�����ö���
		 */
		
		// ��Ϊlist����һ���ֵ������ǲ���Ҫ�ģ����������ﻹҪʹ��jsonConfig
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"dict_sort", "dict_enable", "dict_memo"});
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		
		System.out.println(jsonArray.toString());
		
		// ��Ҫ��json��ӡ��ҳ��
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
		
		return NONE;
	}
	
	
	
	
}
