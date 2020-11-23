package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

public class Plaid_sameday_auth extends Base_class {

	@Test(priority=1)
	@Description("Verify plaid sameday auth with unregistered user_handle")
	public void Test_01_plaid_sameday_auth_with_unregistered_user_handle() throws Exception {
		ApiResponse response = api.plaidSameDayAuth(handleNotRegistered, accountName, reader.getCellData(sheetName, privatekeys, 2));
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
	}
		

	@Test(priority=2)
	@Description("Verify plaid sameday auth with empty user_handle")
	public void Test_02_plaid_sameday_auth_with_empty_user_handle() throws Exception {
		ApiResponse response = api.plaidSameDayAuth("", emptyAccountName, reader.getCellData(sheetName, privatekeys, 2));
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}
	
	

	@Test(priority=3)
	@Description("Verify plaid sameday auth with empty account_name")
	public void Test_03_plaid_sameday_auth_with_empty_account_name() throws Exception {
		ApiResponse response = api.plaidSameDayAuth(handle22, "", reader.getCellData(sheetName, privatekeys, 2));
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}

	

	@Test(priority=4)
	@Description("Verify plaid sameday auth with default account_name")
	public void Test_04_plaid_sameday_auth_with_default_account_name() throws Exception {
		ApiResponse response = api.plaidSameDayAuth(handle22, validAccountName, reader.getCellData(sheetName, privatekeys, 2));
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationMsg1);
	}
	



	@Test(priority=5)
	@Description("Verify plaid sameday auth with incorrect account_name")
	public void Test_05_plaid_sameday_auth_with_incorrect_account_name() throws Exception {
		ApiResponse response = api.plaidSameDayAuth(handle22, "saving", reader.getCellData(sheetName, privatekeys, 2));

		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationMsg2);
	}
	
		
	

	@Test(priority=6)
	@Description("Verify plaid sameday auth with Response401")
	public void Test_06_plaid_sameday_auth_with_Response401() throws Exception {
		ApiResponse response = api1.plaidSameDayAuth(handle22, validAccountName, reader.getCellData(sheetName, privatekeys, 2));
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),invalidSignErrorMsg);
	}
	
	@Test(priority=7)
	@Description("Verify validation when user private key is different")
	public void test_07_plaid_same_day_with_different_private_key() throws Exception {
		ApiResponse response = api.plaidSameDayAuth(handle22, validAccountName, reader.getCellData(sheetName, privatekeys, 3));

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "Bank account \""+validAccountName+"\" for user/handle \""+Handle_22+"\" not in status \"microdeposit_pending_manual_verification\"");
	}
	
	

	
	
	
}
