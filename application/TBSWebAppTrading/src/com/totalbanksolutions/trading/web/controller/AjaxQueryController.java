package com.totalbanksolutions.trading.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.totalbanksolutions.framework.bean.database.view.ViewCustomerAccountList;
import com.totalbanksolutions.framework.model.ModelRow;
import com.totalbanksolutions.framework.model.ModelTable;
import com.totalbanksolutions.framework.model.database.table.DeveloperExamplesConfigurationTable;
import com.totalbanksolutions.framework.web.model.ModelAndView;
import com.totalbanksolutions.framework.web.util.WebUtils;
import com.totalbanksolutions.trading.web.model.ModelHelper;

/**
 * @author Val Catrini
 */
public class AjaxQueryController extends AbstractControllerNew 
{
	public AjaxQueryController() 
    {
		super(false, false); // isValidateUser=false, isSetMenuList=false
	}
	
	@Override
	public void processRequest( HttpServletRequest request, ModelAndView modelAndView ) 
	{
		ModelHelper h = new ModelHelper(modelAndView);

		if ( h.getSessionUserAction().equalsIgnoreCase("getAccountList") )
		{
			List<ViewCustomerAccountList> accountList = null;
			long programId = WebUtils.getLongParameterValue(request, "programId");
			long sourceInstitutionId = WebUtils.getLongParameterValue(request, "sourceInstitutionId");
			String account = WebUtils.getStringParameterValue(request, "account");
			accountList = ds.util.getAccountList( programId, sourceInstitutionId, account );
			for(ViewCustomerAccountList item : accountList )
			{
				Map<String,String> row = new LinkedHashMap<String,String>();
				row.put("AccountNumber", item.getAccount());
				h.addAjaxResultsRow(row);
			}
		
		}
		else if ( h.getSessionUserAction().equalsIgnoreCase("getDeveloperExamplesConfiguration") )
		{
			DeveloperExamplesConfigurationTable t = new DeveloperExamplesConfigurationTable();
			t = this.ds.developerExamples.getDeveloperExamplesConfiguration();
			h.addAjaxResultsRow( t.getRow().getAjaxResultsRow() );
		}
		else if ( h.getSessionUserAction().equalsIgnoreCase("getDeveloperExamplesConfigurationSQLError") )
		{
			// Forced SQL Error, for demo purposes
			DeveloperExamplesConfigurationTable t = new DeveloperExamplesConfigurationTable();
			t = this.ds.developerExamples.getDeveloperExamplesConfigurationSQLError();
			h.addAjaxResultsRow( t.getRow().getAjaxResultsRow() );
		}
		else if ( h.getSessionUserAction().equalsIgnoreCase("getDeveloperExamplesFileList") )
		{
			ModelTable t = this.ds.developerExamples.getDeveloperExamplesFiles();
			for( ModelRow row : t.getRowList() )
			{
				h.addAjaxResultsRow( row.getAjaxResultsRow() );
			}
		}
		else if ( h.getSessionUserAction().equalsIgnoreCase("getDeveloperExamplesFileListError") )
		{
			ModelTable t = this.ds.developerExamples.getDeveloperExamplesFiles();
			for( ModelRow row : t.getRowList() )
			{
				// Forced Divide by ZERO Error, for demo purposes
				int x = row.getColumnList().size() / 0;
				h.addAjaxResultsRow( row.getAjaxResultsRow() );
			}
		}

		
/*
		if (queryType.equalsIgnoreCase("checklistVersion"))
		{
			Map<String,String> row = new LinkedHashMap<String,String>();
			row.put("IsRefreshRequired", "true");
			row.put("PercentComplete", "25");
			results.add(row);
		}
		else if (queryType.equalsIgnoreCase("test1"))
		{
			Map<String,String> row = new LinkedHashMap<String,String>();
			row.put("IsRefreshRequired", "true");
			row.put("PercentComplete", "25");
			results.add(row);
		}
		else if (queryType.equalsIgnoreCase("test2"))
		{
			Map<String,String> row = new LinkedHashMap<String,String>();
			for(int i = 1; i <= 4; i++)
			{
				row.put("Field" + i, "Data" + i);
			}
			results.add(row);
		}
		else if (queryType.equalsIgnoreCase("test3"))
		{
			for(int i = 1; i <= 8; i++)
			{
				Map<String,String> row = new LinkedHashMap<String,String>();
				row.put("Account", "10245000" + i);
				row.put("TaxID", "99999000" + i);
				results.add(row);
			}
		}
		else if (queryType.equalsIgnoreCase("test4"))
		{
			Map<String,String> row = new LinkedHashMap<String,String>();
			row.put("data", "Name|Phone|Email");
			results.add(row);

			row = new LinkedHashMap<String,String>();
			row.put("data", "Val Catrini|(917) 533-6942|vcatrini@totalbanksolutions");
			results.add(row);

			row = new LinkedHashMap<String,String>();
			row.put("data", "Dennis Borecki|(201) 400-2650|dborecki@totalbanksolutions");
			results.add(row);
		}
		else if (queryType.equalsIgnoreCase("test5"))
		{
//			for(int i = 1; i <= 8; i++)
//			{
//				Map<String,String> row = new LinkedHashMap<String,String>();
//				row.put("Program", "RBC");
//				row.put("Program_PK", "28");
//				results.add(row);
//			}
			webBean.getModel().put("programList", programList);
			  programList = ds.util.getProgramList();
			  for(Program item : programList )
				{
				  Map<String,String> row = new LinkedHashMap<String,String>();
					row.put("Program", item.getInternalCode());
					row.put("Program_PK", String.valueOf(item.getProgramId()));
					results.add(row);
				}
		}
		
		else if (queryType.equalsIgnoreCase("test6"))
		{
			webBean.getModel().put("programList", programList);
			  programList = ds.util.getProgramList();
			  for(Program item : programList )
				{
				  Map<String,String> row = new LinkedHashMap<String,String>();
					row.put("Program", item.getInternalCode());
					row.put("Program_PK", String.valueOf(item.getProgramId()));
					results.add(row);
				}
		}
		else if (queryType.equalsIgnoreCase("checklistMaintenanceDate"))
		{
			Long programId = WebUtils.getLongParameterValue(request, "programId");
			//try { Thread.sleep(5000); } catch (Exception e) {};
			//programId = programId / 0;
			Map<String,String> row = new LinkedHashMap<String,String>();

			// This would be done in list/object binding via database
			DemoForm demoForm = new DemoForm();
			demoForm.getField(DemoForm.Field.fullName).setValue("Val Catrini");
			demoForm.getField(DemoForm.Field.address).setValue("123 Front Street");
			demoForm.getField(DemoForm.Field.city).setValue("Rockville Centre");
			demoForm.getField("state").setValue("NY");
			demoForm.getField("zipCode").setValue(new Long(98765));
			demoForm.getField("contactTypeId").setValue(new Integer(2));
			demoForm.getField("isActive").setValue(true);
			demoForm.getField("affiliationTypeId").setValue(3);

			// We need some auto generation here
			//row.put(demoForm.getAddressWebId(), demoForm.getAddress());
			//row.put(demoForm.getFullNameWebId(), demoForm.getFullName());
			for(BeanField f : demoForm.getFieldList())
			{
				row.put(f.getName(), f.getValueAsString());
			}
			
			
			//DemoForm f = ds.util.get();
			//f.writeXMl();
			
			results.add(row);
		}
		else if (queryType.equalsIgnoreCase("checklistMaintenanceSteps"))
		{
			Long programId = WebUtils.getLongParameterValue(request, "programId");
			//try { Thread.sleep(5000); } catch (Exception e) {};
			//programId = programId / 0;

			// This would be done in list/object binding via database
			List<DemoForm> demoFormList = new ArrayList<DemoForm>();
			demoFormList.add(getContact("Val Catrini", true));
			demoFormList.add(getContact("Dan Merkel", false));
			demoFormList.add(getContact("Nate Martin", true));
			
			for(DemoForm d : demoFormList)
			{
//				Map<String,String> row = new LinkedHashMap<String,String>();
//				for(BeanField f : d.getFieldList())
//				{
//					row.put(f.getName(), f.getValueAsString());
//				}			
//				results.add(row);
				results.add(d.getAjaxResultsRow());
			}
		}
		
		webBean.getModel().put("queryResults", results);
		webBean.getModel().put("ajaxStatus", true);
*/
	}
	
}
