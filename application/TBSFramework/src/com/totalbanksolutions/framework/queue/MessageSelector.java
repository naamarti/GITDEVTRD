package com.totalbanksolutions.framework.queue;

import java.util.HashSet;
import java.util.Set;

public class MessageSelector 
{
	private Set inclusionSet = null;
	private Set exclusionSet = null;
	
	/**
	 * By default, a selector is exclusion based, which means ALL topics are selected, except for those
	 * topics which are in the exclusion set.  
	 * 
	 * An inclusion based selector, will assume all topics are excluded, except for those topics in
	 * the inclusion set.
	 */
	private boolean isExclusionBased = true;
	private boolean isInclusionBased = false;
	
	public void MessageSelector()
	{
		inclusionSet = new HashSet();
		exclusionSet = new HashSet();
	}

	public void addInclusion( QueueTopic qt )
	{
		this.inclusionSet.add(qt);
	}

	public void addExclusion( QueueTopic qt )
	{
		this.exclusionSet.add(qt);
	}

	public void isInclusionBased( boolean isInclusionBased )
	{
		this.isInclusionBased = isInclusionBased;
	}
	
	public void isExclusionBased( boolean isExclusionBased )
	{
		this.isExclusionBased = isExclusionBased;
	}


}
