package sila_test_cases;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;



public class Check_handle extends Base_class {
	


	@Test(priority=1)
	@Description("Verify with available user_handle.")
	public void Test_01_check_with_available_user_handle() throws Exception {
		ApiResponse response = api.checkHandle(handle1);

		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), handleAvailableMsg);
	}
	

	
	  @Test(priority=2) 
	  @Description("Verify with already taken user_handle.")
	  public void Test_02_check_with_already_taken_user_handle() throws Exception {
	  ApiResponse response = api.checkHandle(handleAlreadyTaken);
	  
	  Assert.assertEquals(response.getStatusCode(), successStatusCode);
	  Assert.assertEquals(response.getSuccess(), successFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
	  statusFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
	  handleTakenMsg);
	  Assert.assertNotEquals(((BaseResponse)response.getData()).getReference(),
	  refNotEmpty); }
	  
	  
	  @Test(priority=3) 
	  @Description("Verify with empty user_handle field.")
	  public void Test_03_check_with_empty_user_handle() throws Exception{
	  ApiResponse response = api.checkHandle(emptyUserHandle);
	  
	  Assert.assertEquals(response.getStatusCode(), 400);
	  Assert.assertEquals(response.getSuccess(), successFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg); }
	  
	  
	  
	  @Test(priority=4) 
	  @Description("Verify with invalid Auth Signature.")
	  public void Test_04_check_with_invalid_authSignature () throws Exception {
	  ApiResponse response = api1.checkHandle(handle1);
	  
	  Assert.assertEquals(response.getStatusCode(), 401);
	  Assert.assertEquals(response.getSuccess(), successFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
	  statusFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
	  invalidSignErrorMsg);
	  Assert.assertNotEquals(((BaseResponse)response.getData()).getReference(),
	  refNotEmpty); }
	  
	  

	  
	  @Test(priority=5) 
	  @Description("Verify with invalid handle pattern")
	  public void Test_05_check_with_invalid_handle_pattern() throws Exception {
	  ApiResponse response = api.checkHandle(handleInvalidPatern);

	  Assert.assertEquals(response.getStatusCode(), 400);
	  Assert.assertEquals(response.getSuccess(), successFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg); }

	
	  
	  @Test(priority=6) 
	  @Description("Verify with invalid app_handle ")
	  public void Test_06_check_with_invalid_App_handle() throws Exception {
	  ApiResponse response = api3.checkHandle(handle1);
		
	  Assert.assertEquals(response.getStatusCode(), 401);
	  Assert.assertEquals(response.getSuccess(), successFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorMsg4); }

	

	  
	  

}