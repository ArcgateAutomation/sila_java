package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.GetEntityResponse;
import com.silamoney.common_files.Base_class;

import io.qameta.allure.Description;

public class Get_entity extends Base_class{
	
	
	
// Get Entity (individual)	

	
	@Test(priority = 2)
	@Description("Get entity for individual user")
	public void test_01_Get_entity_for_individual_user() throws Exception {	  
		ApiResponse response = api.getEntity(handle22, reader.getCellData(sheetName, privatekeys, 2));

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getStreetAddress1(), streetAddress1);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getStreetAddress2(), streetAddress2);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getCity(), city);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getState(), state);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getCountry(), country);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEmails().get(0).getEmail(), email);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntityType(), "individual");
		Assert.assertEquals(((GetEntityResponse)response.getData()).getUserHandle(), Handle_22);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getFirstName(), firstName);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getLastName(), lastName);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getEntityName(), accountName);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getBirthdate(), "2000-01-31");
		Assert.assertNotNull(((GetEntityResponse)response.getData()).getIdentities().get(0).getIdentity());

}
	
	
	@Test(priority = 2)
	@Description("Get entity with empty user_handle")
	public void test_02_Get_entity_with_empyt_user_handle() throws Exception {	  
		ApiResponse response = api.getEntity("", reader.getCellData(sheetName, privatekeys, 2));

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
}
	
	
	
	@Test(priority = 3)
	@Description("Get entity with unregistered user_handle")
	public void test_03_Get_entity_with_unregistered_user_handle() throws Exception {	  
		ApiResponse response = api.getEntity(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 2));
		//System.out.println(((BaseResponse)response.getData()).getMessage());
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		//Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		//Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
}
	
	
	
	@Test(priority = 4)
	@Description("Get entity with invalid crypto_key")
	public void test_04_Get_entity_with_invalid_crypto_key() throws Exception {	  
		ApiResponse response = api.getEntity(handle22, reader.getCellData(sheetName, privatekeys, 7));

		Assert.assertEquals(response.getStatusCode(), 403);
//		Assert.assertEquals(response.getSuccess(), successFalse);
//		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
//		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), validationErrorMsg);
//		System.out.println(((BaseResponse)response.getData()).getMessage());
}
	

// Get Entity (business)		
	
	@Test(priority = 5)
	@Description("Get entity for business user")
	public void test_05_Get_entity_for_business_user() throws Exception {	  
		ApiResponse response = api.getEntity(businessHandle, reader.getCellData(sheetName, privatekeys, 41));

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getStreetAddress1(), streetAddress1);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getStreetAddress2(), streetAddress2);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getCity(), city);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getState(), state);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getCountry(), country);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEmails().get(0).getEmail(), email);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getEntityName(), businessEntityName);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntityType(), "business");
		Assert.assertEquals(((GetEntityResponse)response.getData()).getUserHandle(), business_Handle);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getPhones().get(0).getPhone(), phone);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getAddresses().get(0).getPostalCode(), postalCode);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getBusinessType(), "corporation");
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getBusinessWebsite(), business_website);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getDoingBusinessAs(), doingBusiness_As);
		Assert.assertEquals(((GetEntityResponse)response.getData()).getEntity().getNaicsCategory(), "Accommodation and Food Services");
		Assert.assertNotNull(((GetEntityResponse)response.getData()).getIdentities().get(0).getIdentity());


}

}
