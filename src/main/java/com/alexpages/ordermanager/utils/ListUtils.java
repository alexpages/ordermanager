package com.alexpages.ordermanager.utils;

import java.util.ArrayList;
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
	
	
	public static void main(String[] args) {
		
		List<String> newList = new ArrayList<>();
		newList.add("Pedro");
		newList.add("José");
		System.out.println(ListUtils.toString(newList));
		System.out.println(newList.toString());

		
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