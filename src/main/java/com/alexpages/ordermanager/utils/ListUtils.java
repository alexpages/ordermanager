package com.alexpages.ordermanager.utils;

import java.util.List;

public class ListUtils {
	
	private ListUtils() 
	{
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
	
	public static boolean isBlank(List<?> list)
    {
    	return (list == null || list.isEmpty());
    }
	
	public static String toString(List<?> list)
    {
		String sListToString = null;
		
    	if(list != null)
    	{
    		StringBuilder sbListToString 	= new StringBuilder("{");
    		boolean bFirstElement 			= true;
    		
    		for(Object oObject : list) 
    		{
    			if(bFirstElement) {
    				bFirstElement = false;
    			} else {
    				sbListToString.append(", ");
    			}
    			
    			sbListToString.append(oObject);
    		}
    		
    		sbListToString.append("}");
    		sListToString = sbListToString.toString();
    	}
    	
    	return sListToString;
    }
}