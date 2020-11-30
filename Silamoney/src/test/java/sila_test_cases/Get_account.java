package sila_test_cases;




import java.util.List;

import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.Account;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.User;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;
import io.qameta.allure.Description;

	public class Get_account extends Base_class{

//Get account -plaid account
	
	@Test(priority=1)
	@Description("Get account with empty user_handle")
	public void Test_01_Get_account_with_empty_user_handle() throws Exception {
		ApiResponse getAccountresponse = api.getAccounts("", reader.getCellData(sheetName, privatekeys, 4));
		
		Assert.assertEquals(getAccountresponse.getStatusCode(), 400);
		Assert.assertEquals(getAccountresponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getAccountresponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getAccountresponse.getData()).getMessage(),validationErrorMsg);
	}	
	
	
	@Test(priority=2)
	@Description("Get account with unregistered user_handle")
	public void Test_02_Get_account_with_unregistered_user_handle() throws Exception {
		ApiResponse getAccountresponse = api.getAccounts(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 4));
		
		Assert.assertEquals(getAccountresponse.getStatusCode(), 401);
		Assert.assertEquals(getAccountresponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getAccountresponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getAccountresponse.getData()).getMessage(),invalidSignErrorMsg4);
	}	


	@Test(priority=3)
	@Description("Get account with another private key")
	public void Test_03_Get_account_with_another_private_key() throws Exception {
			ApiResponse getAccountresponse = api.getAccounts(handle22, reader.getCellData(sheetName, privatekeys, 3));

			Assert.assertEquals(getAccountresponse.getSuccess(), successFalse);
			Assert.assertEquals(((BaseResponse) getAccountresponse.getData()).getStatus(),statusFalse);
			Assert.assertEquals(((BaseResponse)getAccountresponse.getData()).getMessage(),ErrorCheckKYCMessage);
			Assert.assertEquals(getAccountresponse.getStatusCode(), 403);


		}		
	
	@Test(priority=4)
	@Description("Get account with invalid auth sign")
	public void Test_04_Get_account_with_invalid_auth_sign() throws Exception {
		ApiResponse getAccountresponse = api1.getAccounts(handle22, reader.getCellData(sheetName, privatekeys, 4));
		
		Assert.assertEquals(getAccountresponse.getStatusCode(), 401);
		Assert.assertEquals(getAccountresponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getAccountresponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getAccountresponse.getData()).getMessage(),ErrorMsg2);
	}	
	
	
	@Test(priority=5)
	@Description("Get account with all valid data")
	public void Test_05_Get_account_with_all_valid_data() throws Exception {
		ApiResponse getAccountresponse = api.getAccounts(handle22, reader.getCellData(sheetName, privatekeys, 2));

		Assert.assertEquals(getAccountresponse.getStatusCode(), 200);
		Assert.assertEquals(((List<Account>) getAccountresponse.getData()).get(0).accountName, validPlaidAccountName);
		Assert.assertNotNull(((List<Account>) getAccountresponse.getData()).get(0).accountNumber);
		Assert.assertEquals(((List<Account>) getAccountresponse.getData()).get(0).accountStatus, "active");
		Assert.assertEquals(((List<Account>) getAccountresponse.getData()).get(0).accountType, "CHECKING");


	}	
	
	
	
	//Get account - direct flow account
	
	
	
	@Test(priority=6)
	@Description("Get account with empty user_handle")
	public void Test_06_Get_account_with_empty_user_handle() throws Exception {
		ApiResponse getAccountresponse = api.getAccounts("", reader.getCellData(sheetName, privatekeys, 3));
		
		Assert.assertEquals(getAccountresponse.getStatusCode(), 400);
		Assert.assertEquals(getAccountresponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getAccountresponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getAccountresponse.getData()).getMessage(),validationErrorMsg);
	}	
	
	
	@Test(priority=7)
	@Description("Get account with unregistered user_handle")
	public void Test_07_Get_account_with_unregistered_user_handle() throws Exception {
		ApiResponse getAccountresponse = api.getAccounts(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 3));
		
		Assert.assertEquals(getAccountresponse.getStatusCode(), 401);
		Assert.assertEquals(getAccountresponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getAccountresponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getAccountresponse.getData()).getMessage(),invalidSignErrorMsg4);
	}	


	@Test(priority=8)
	@Description("Get account with another private key")
	public void Test_08_Get_account_with_another_private_key() throws Exception {
		ApiResponse getAccountresponse = api.getAccounts(handle23, reader.getCellData(sheetName, privatekeys, 2));
		
		Assert.assertEquals(getAccountresponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getAccountresponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getAccountresponse.getData()).getMessage(),ErrorCheckKYCMessage);
		Assert.assertEquals(getAccountresponse.getStatusCode(), 403);
	}	
	
	@Test(priority=9)
	@Description("Get account with invalid auth sign")
	public void Test_09_Get_account_with_invalid_auth_sign() throws Exception {

		ApiResponse getAccountresponse = api1.getAccounts(handle23, reader.getCellData(sheetName, privatekeys, 3));
		
		Assert.assertEquals(getAccountresponse.getStatusCode(), 401);
		Assert.assertEquals(getAccountresponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getAccountresponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getAccountresponse.getData()).getMessage(),ErrorMsg2);
	}	
	
	
	@Test(priority=10)
	@Description("Get account with all valid data")
	public void Test_10_Get_account_with_all_valid_data() throws Exception {
		ApiResponse getAccountresponse = api.getAccounts(handle23, reader.getCellData(sheetName, privatekeys, 3));

		Assert.assertEquals(getAccountresponse.getStatusCode(), 200);
		Assert.assertEquals(((List<Account>) getAccountresponse.getData()).get(0).accountName, validDirectAccountName);
		Assert.assertNotNull(((List<Account>) getAccountresponse.getData()).get(0).accountNumber);
		Assert.assertEquals(((List<Account>) getAccountresponse.getData()).get(0).accountStatus, "active");
		Assert.assertEquals(((List<Account>) getAccountresponse.getData()).get(0).accountType, "CHECKING");
	}	
	

	
}
