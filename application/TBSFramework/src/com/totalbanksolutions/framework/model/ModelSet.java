package com.totalbanksolutions.framework.model;

import java.util.List;

public interface ModelSet
{	
	public long getIdByName(String name);
	public long getIdByCode(String code);

	public String getNameById(long id);
	public String getCodeById(long id);
	
	public ModelRow getDataRowById(long id);
    public ModelRow getDataRowByName(String name);
    public ModelRow getDataRowByCode(String code);
    
	public List<ModelRow> getDataRowList();
	public List<ModelRow> getDataRowList(Long... idList);
    
	public boolean containsId(long id);
}
