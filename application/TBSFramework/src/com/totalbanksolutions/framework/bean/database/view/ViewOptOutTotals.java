package com.totalbanksolutions.framework.bean.database.view;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * @author nmartin
 */
public class ViewOptOutTotals extends AbstractDatabaseBean<ViewOptOutTotals.Field>
{
	
	public static String DATABASE_NAME = DatabaseNames.TBS_Program_XXX.name();
	public static String TABLE_NAME = "";
	
	public enum Field
	{
		  COUNT_OPTOUTS_ADDED
		, COUNT_OPTOUTS_DELETED
		, COUNT_OPTOUTS_NET
		;
	}

	public ViewOptOutTotals()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName						Database_FieldName				Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------------------
		super.addField(Field.COUNT_OPTOUTS_ADDED,					"OptOutsAddedCount",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_OPTOUTS_DELETED,					"OptOutsDeletedCount",				DatabaseDataType.INT,				0,		false);
		super.addField(Field.COUNT_OPTOUTS_NET,						"OptOutsTotalCount",				DatabaseDataType.INT,				0,		false);
	}

	// Getters
	public int getCountOptOutsAdded()
	{
		return this.getField(Field.COUNT_OPTOUTS_ADDED).getIntegerValue();
	}
	
	public int getCountOptOutsDeleted()
	{
		return this.getField(Field.COUNT_OPTOUTS_DELETED).getIntegerValue();
	}

	public int getCountOptOutsNet()
	{
		return this.getField(Field.COUNT_OPTOUTS_NET).getIntegerValue();
	}
}
