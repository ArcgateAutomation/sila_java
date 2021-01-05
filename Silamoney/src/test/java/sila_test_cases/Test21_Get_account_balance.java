package sila_test_cases;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.AccountBalanceResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

	public class Test21_Get_account_balance extends Base_class{

//Get -plaid account balance

		@Test(priority=1)
		@Description("Get plaid_account balance with empty user_handle")
		public void Test_01_Get_plaid_account_balance_with_empty_user_handle()throws Exception {		
		ApiResponse response = api.getAccountBalance("", reader.getCellData(sheetName, privatekeys, 2), validPlaidAccountName);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}
		
		
		@Test(priority=2)
		@Description("Get plaid_account balance with unregistered user_handle")
		public void Test_02_Get_plaid_account_balance_with_unregistered_user_handle()throws Exception {		
		ApiResponse response = api.getAccountBalance(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 2), validPlaidAccountName);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
		}
		
		
		
		@Test(priority=3)
		@Description("Get plaid_account balance with another crypto_key")
		public void Test_03_Get_plaid_account_balance_with_another_crypto_key()throws Exception {		
		ApiResponse response = api.getAccountBalance(handle22, reader.getCellData(sheetName, privatekeys, 6), validPlaidAccountName);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 6)+" is not registered to "+Handle_22+".".toLowerCase());
		}
		
		
		
		
		@Test(priority=4)
		@Description("Get plaid_account balance with empty account_name")
		public void Test_04_Get_plaid_account_balance_with_empty_account_name()throws Exception {		
		ApiResponse response = api.getAccountBalance(handle22, reader.getCellData(sheetName, privatekeys, 2), "");
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}
		
		
		@Test(priority=5)
		@Description("Get plaid_account balance with invalid account_name")
		public void Test_05_Get_plaid_account_balance_with_invalid_account_name()throws Exception {		
		ApiResponse response = api.getAccountBalance(handle22, reader.getCellData(sheetName, privatekeys, 2), "invalid account name");
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "No bank account for user "+Handle_22+" with name invalid account name found.");
		}
		
		
		
		@Test(priority=6)
		@Description("Get plaid_account balance with all valid details")
		public void Test_06_Get_plaid_account_balance_with_all_valid_details() throws Exception {	
		ApiResponse getAccountBalanceResponse = api.getAccountBalance(handle22, reader.getCellData(sheetName, privatekeys, 2), validPlaidAccountName);
		Assert.assertEquals(getAccountBalanceResponse.getStatusCode(), 200);
		Assert.assertEquals(getAccountBalanceResponse.getSuccess(),  successTrue);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getAccountName(), validPlaidAccountName);
		Assert.assertNotNull(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getRoutingNumber());
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getCurrentBalance(), 110.0);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getAvailableBalance(), 100.0);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getMaskedAccountNumber(), "0000");
		}	
		
		
		
		
	
		
		
//Get direct linked account balance		
		
		@Test(priority=7)
		@Description("Get direct_account balance with empty user_handle")
		public void Test_07_Get_direct_account_balance_with_empty_user_handle()throws Exception {		
		ApiResponse response = api.getAccountBalance("", reader.getCellData(sheetName, privatekeys, 3), validDirectAccountName);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}
		
		
		@Test(priority=8)
		@Description("Get direct_account balance with unregistered user_handle")
		public void Test_02_Get_direct_account_with_balance_unregistered_user_handle()throws Exception {		
		ApiResponse response = api.getAccountBalance(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 3), validDirectAccountName);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
		}
		
		
		
		@Test(priority=9)
		@Description("Get direct_account balance with another crypto_key")
		public void Test_09_Get_direct_account_balance_with_another_crypto_key()throws Exception {		
		ApiResponse response = api.getAccountBalance(handle23, reader.getCellData(sheetName, privatekeys, 7), validDirectAccountName);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 7)+" is not registered to "+Handle_23+".".toLowerCase());
		}
		
		
		
		
		@Test(priority=10)
		@Description("Get direct_account with empty account_name")
		public void Test_10_Get_direct_account_with_empty_account_name()throws Exception {		
		ApiResponse response = api.getAccountBalance(handle23, reader.getCellData(sheetName, privatekeys, 3), "");
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}
		
		
		@Test(priority=11)
		@Description("Get direct_account with invalid account_name")
		public void Test_11_Get_direct_account_with_invalid_account_name()throws Exception {		
		ApiResponse response = api.getAccountBalance(handle23, reader.getCellData(sheetName, privatekeys, 3), "invalid account name");
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "No bank account for user "+Handle_23+" with name invalid account name found.");
		}
		
		
		
		@Test(priority=12)
		@Description("Get direct_account with all valid details")
		public void Test_12_Get_direct_account_with_all_valid_details() throws Exception {	
		ApiResponse getAccountBalanceResponse = api.getAccountBalance(handle23, reader.getCellData(sheetName, privatekeys, 3), validDirectAccountName);
		
		Assert.assertEquals(getAccountBalanceResponse.getStatusCode(), 400);
		Assert.assertEquals(getAccountBalanceResponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getAccountBalanceResponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getAccountBalanceResponse.getData()).getMessage(), "Cannot fetch account balance for account "+validDirectAccountName+"; account either was not linked with Plaid or has not completed verification.");
		

		}	
		
		
		
	
	
	
}
