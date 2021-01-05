package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.BusinessType;
import com.silamoney.client.domain.BusinessUser;
import com.silamoney.client.domain.GetBusinessTypesResponse;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;

import io.qameta.allure.Description;

public class Test03_Register_business_user extends Base_class{
	

	
	
	@Test(priority = 1)
		@Description("Verify user is able to register business user with all valid details")
		public void test_02_register_business_with_all_valid_data() throws Exception {
			
			ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
			BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);
			
			BusinessUser user2 = new BusinessUser(country, businessHandle_2, businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
					 Utility.getBusinessCryptoAddress_2(), crypto_alias, type,  businessType, business_website, 
			          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());
			reader.setCellData(sheetName, privatekeys, 42, Utility.getBusinessPrivateKey_2());
			reader.setCellData(sheetName, cryptoAddress, 42, Utility.getBusinessCryptoAddress_2());
			ApiResponse businessRegisterResponse2 = api.registerBusiness(user2);	
			System.out.println(((BaseResponse)businessRegisterResponse2.getData()).getMessage());
			

			BusinessUser user = new BusinessUser(country, businessHandle, businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
					 Utility.getBusinessCryptoAddress(), crypto_alias, type,  businessType, business_website, 
			          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());
			reader.setCellData(sheetName, privatekeys, 41, Utility.getBusinessPrivateKey());
			System.out.println(Utility.getBusinessPrivateKey());
			reader.setCellData(sheetName, cryptoAddress, 41, Utility.getBusinessCryptoAddress());
			System.out.println(Utility.getBusinessCryptoAddress());
			ApiResponse businessRegisterResponse = api.registerBusiness(user);	
			
			Assert.assertEquals(businessRegisterResponse.getStatusCode(), 200);
			Assert.assertEquals(businessRegisterResponse.getSuccess(), successTrue);
			Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusTrue);
			Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(), business_Handle+" was successfully registered.");

	}
	


	@Test(priority = 2)
	@Description("Verify user is not able to register business user with empty user_handle")
	public void test_02_register_business_with_empty_user_handle() throws Exception {
		
		ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
		BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);

		BusinessUser user = new BusinessUser(country, "", businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
				 Utility.getBusinessCryptoAddress(), crypto_alias, type,  businessType, business_website, 
		          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());
		

		ApiResponse businessRegisterResponse = api.registerBusiness(user);	
		Assert.assertEquals(businessRegisterResponse.getStatusCode(), 400);
		Assert.assertEquals(businessRegisterResponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(),validationErrorMsg);
}

	
	
	@Test(priority = 3)
	@Description("Verify user is able to register business user with empty identity_alias")
	public void test_03_register_business_empty_identity_alias() throws Exception {
		
		ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
		BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);

		BusinessUser user = new BusinessUser(country, "a"+businessHandle, businessEntityName,  "", identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
				 Utility.getbusinessCryptoAddress_1(), crypto_alias, type,  businessType, business_website, 
		          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());
		

		ApiResponse businessRegisterResponse = api.registerBusiness(user);	
		Assert.assertEquals(businessRegisterResponse.getStatusCode(), 200);
		Assert.assertEquals(businessRegisterResponse.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(), "a"+business_Handle+" was successfully registered.");

}
	

		
	
	
	@Test(priority = 4)
	@Description("Verify user is able to register business user with empty crypto_alias")
	public void test_04_register_business_empty_crypto_alias() throws Exception {
		
		ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
		BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);
		
		
		BusinessUser user = new BusinessUser(country, "b"+businessHandle, businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
				 Utility.getBusinessCryptoAddress_6(), "", type,  businessType, business_website, 
		          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());
		ApiResponse businessRegisterResponse = api.registerBusiness(user);	
		System.out.println(((BaseResponse)businessRegisterResponse.getData()).getMessage());
		Assert.assertEquals(businessRegisterResponse.getStatusCode(), 200);
		Assert.assertEquals(businessRegisterResponse.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(), "b"+business_Handle+" was successfully registered.");

}
		

	@Test(priority = 5)
	@Description("Verify user is able to register business user with empty type")
	public void test_05_register_business_empty_type() throws Exception {
		
		ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
		BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);
		
		
		BusinessUser user = new BusinessUser(country, "c"+businessHandle, businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
				 Utility.getBusinessCryptoAddress_3(), crypto_alias, "",  businessType, business_website, 
		          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());

		ApiResponse businessRegisterResponse = api.registerBusiness(user);	
		
		Assert.assertEquals(businessRegisterResponse.getStatusCode(), 200);
		Assert.assertEquals(businessRegisterResponse.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(), "c"+business_Handle+" was successfully registered.");

}
	
	
	@Test(priority = 6)
	@Description("Verify user is not able to register business user with empty businessEntityName")
	public void test_06_register_business_empty_businessEntityName() throws Exception {
		
		ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
		BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);
		
		BusinessUser user = new BusinessUser(country, "h"+businessHandle, "",  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
				 Utility.getBusinessCryptoAddress(), crypto_alias, type,  businessType, business_website, 
		          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());


		ApiResponse businessRegisterResponse = api.registerBusiness(user);			
		Assert.assertEquals(businessRegisterResponse.getStatusCode(), 400);
		Assert.assertEquals(businessRegisterResponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(),validationErrorMsg);


}
	
	@Test(priority = 7)
	@Description("Verify user is able to register business user with empty business_website")
	public void test_07_register_business_with_empty_business_website() throws Exception {
		
		ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
		BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);

		BusinessUser user = new BusinessUser(country, "d"+businessHandle, businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
				 Utility.getBusinessCryptoAddress_4(), crypto_alias, type,  businessType, "", 
		          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());

		ApiResponse businessRegisterResponse = api.registerBusiness(user);	
		
		System.out.println(((BaseResponse)businessRegisterResponse.getData()).getMessage());
		Assert.assertEquals(businessRegisterResponse.getStatusCode(), 200);
		Assert.assertEquals(businessRegisterResponse.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(), "d"+business_Handle+" was successfully registered.");

}
	
	
	
	
	
	
	@Test(priority = 8)
	@Description("Verify user is not able to register business user with incorrect business_website format")
	public void test_08_register_business_with_incorrect_business_website_formate() throws Exception {
		
		ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
		BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);

		BusinessUser user = new BusinessUser(country, "d"+businessHandle, businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
				 Utility.getBusinessCryptoAddress_5(), crypto_alias, type,  businessType, "testwebsite.com", 
		          doingBusiness_As, Utility.getDefaultNaicCategoryDescription());

		ApiResponse businessRegisterResponse = api.registerBusiness(user);	
		
		Assert.assertEquals(businessRegisterResponse.getStatusCode(), 400);
		Assert.assertEquals(businessRegisterResponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(),validationErrorMsg);

}
	
	
	@Test(priority = 9)
	@Description("Verify user is able to register business user with empty doingBusiness_As")
	public void test_09_register_business_doingBusiness_As() throws Exception {
		
		ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
		BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);

		BusinessUser user = new BusinessUser(country, "e"+businessHandle, businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
				 Utility.getBusinessCryptoAddress_5(), crypto_alias, type,  businessType, business_website, 
				 "", Utility.getDefaultNaicCategoryDescription());

		ApiResponse businessRegisterResponse = api.registerBusiness(user);	
		
		Assert.assertEquals(businessRegisterResponse.getStatusCode(), 200);
		Assert.assertEquals(businessRegisterResponse.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) businessRegisterResponse.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)businessRegisterResponse.getData()).getMessage(), "e"+business_Handle+" was successfully registered.");

}	

	
	
	

}
