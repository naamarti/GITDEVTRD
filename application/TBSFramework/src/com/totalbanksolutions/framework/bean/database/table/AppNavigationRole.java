package com.totalbanksolutions.framework.bean.database.table;

/**
 * @author vcatrini
 */
public class AppNavigationRole
{	
	private Long			navigationId;
	private Long			roleId;
	private boolean			isFullAccess;
	
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if((obj == null) || (obj.getClass() != this.getClass())) return false;
		// object must be same type at this point
		AppNavigationRole role = (AppNavigationRole)obj;
		return (navigationId == navigationId && roleId == role.roleId && isFullAccess == role.isFullAccess);
		//&& (stringData == role.stringData || (stringData != null && stringData.equals(role.stringData)));
	}

	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + navigationId.intValue();
		hash = 31 * hash + roleId.intValue();
		//hash = 31 * hash + (null == stringData ? 0 : stringData.hashCode());
		return hash;
	}
	
	public Long getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(Long navigationId) {
		this.navigationId = navigationId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}	
	
	public boolean getIsFullAccess() {
		return isFullAccess;
	}

	public void setFullAccess(boolean isFullAccess) {
		this.isFullAccess = isFullAccess;
	}	
}
