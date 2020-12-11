package dummyTestcases;

import org.apache.commons.lang3.RandomStringUtils;
public class Common_functions {
	
	public static  String random_text(String random_handle ) {
		random_handle =RandomStringUtils.randomAlphanumeric(17).toLowerCase();
		System.out.println(RandomStringUtils.randomAlphanumeric(17).toLowerCase());
		return random_handle;
	
	}

}
