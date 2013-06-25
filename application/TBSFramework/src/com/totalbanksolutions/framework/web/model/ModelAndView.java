package com.totalbanksolutions.framework.web.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author vcatrini
 *
 */
public class ModelAndView 
{
	private String viewName = "";
	private String modelId = "model";
	private Map<String,Object> model = new HashMap<>();

	public ModelAndView()
	{
		super();
	}
	
	public ModelAndView(String viewName, String modelId, Map<String,Object> model)
	{
		this.viewName = viewName;
		this.modelId = modelId;
		this.model = model;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	
	
}
