package sila_test_cases;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.AccountBalanceResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

	public class Get_account_balance extends Base_class{

//Get -plaid account balance

		@Test(priority=1)
		@Description("Get account with empty user_handle")
		public void Test_01_Get_account_with_empty_user_handle()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance("", reader.getCellData(sheetName, privatekeys, 2), validPlaidAccountName);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}
		
		
		@Test(priority=2)
		@Description("Get account with unregistered user_handle")
		public void Test_02_Get_account_with_unregistered_user_handle()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 2), validPlaidAccountName);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
		}
		
		
		
		@Test(priority=3)
		@Description("Get account with another crypto_key")
		public void Test_03_Get_account_with_another_crypto_key()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance(handle22, reader.getCellData(sheetName, privatekeys, 6), validPlaidAccountName);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 6)+" is not registered to "+Handle_22+".".toLowerCase());
		}
		
		
		
		
		@Test(priority=4)
		@Description("Get account with empty account_name")
		public void Test_04_Get_account_with_empty_account_name()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance(handle22, reader.getCellData(sheetName, privatekeys, 2), "");
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}
		
		
		@Test(priority=5)
		@Description("Get account with invalid account_name")
		public void Test_05_Get_account_with_invalid_account_name()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance(handle22, reader.getCellData(sheetName, privatekeys, 2), "invalid account name");
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "No bank account for user "+Handle_22+" with name invalid account name found.");
		}
		
		
		
		@Test(priority=6)
		@Description("Get account with all valid details")
		public void Test_06_Get_account_with_all_valid_details() throws Exception {	
		//get Account balance
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
		
		
		
		
/*		
		
		
//Get direct linked account balance		
		
		@Test(priority=8)
		@Description("Get account with empty user_handle")
		public void Test_08_Get_account_with_empty_user_handle()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance("", reader.getCellData(sheetName, privatekeys, 4), validPlaidAccountName);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}
		
		
		@Test(priority=2)
		@Description("Get account with unregistered user_handle")
		public void Test_02_Get_account_with_unregistered_user_handle()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 4), validPlaidAccountName);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
		}
		
		
		
		@Test(priority=3)
		@Description("Get account with another crypto_key")
		public void Test_03_Get_account_with_another_crypto_key()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance(handle24, reader.getCellData(sheetName, privatekeys, 6), validPlaidAccountName);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 6)+" is not registered to "+Handle_24+".".toLowerCase());
		}
		
		
		
		
		@Test(priority=4)
		@Description("Get account with empty account_name")
		public void Test_04_Get_account_with_empty_account_name()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance(handle24, reader.getCellData(sheetName, privatekeys, 4), "");
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}
		
		
		@Test(priority=5)
		@Description("Get account with invalid account_name")
		public void Test_05_Get_account_with_invalid_account_name()throws Exception {		
		//get Account balance
		ApiResponse response = api.getAccountBalance(handle24, reader.getCellData(sheetName, privatekeys, 4), "invalid account name");
		Assert.assertEquals(response.getStatusCode(), 404);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "No bank account for user "+Handle_24+" with name invalid account name found.");
		}
		
		
		
		@Test(priority=4)
		@Description("Get account with eall valid details")
		public void Test_14_Get_account_with_all_valid_details() throws Exception {	
		//get Account balance
		ApiResponse getAccountBalanceResponse = api.getAccountBalance(handle23, reader.getCellData(sheetName, privatekeys, 3), validPlaidAccountName);
		
		
		System.out.println(((BaseResponse)getAccountBalanceResponse.getData()).getMessage());
		Assert.assertEquals(getAccountBalanceResponse.getStatusCode(), 200);
		Assert.assertEquals(getAccountBalanceResponse.getSuccess(),  successTrue);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getAccountName(), validPlaidAccountName);
		Assert.assertNotNull(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getRoutingNumber());
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getCurrentBalance(), 110);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getAvailableBalance(), 100);
		//Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getMaskedAccountNumber(), 0000);
		}	
		
		
		
	@Test(priority=1)
	@Description("Get account with empty user_handle")
	public void Test_01_Get_hjkaccyuiyount_with_empty_user_handle() throws Exception {
//		//register User
//		LocalDate birthdate = new LocalDate(2000, 01, 31);
//		User user = new User(handle23,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
//		postalCode,  phone,  email, identityNumber, Utility.getuser23CryptoAddress(), birthdate.toDate(), country);
//		reader.setCellData(sheetName, privatekeys, 3, Utility.getuser27PrivateKey());
//		reader.setCellData(sheetName, cryptoAddress, 3, Utility.getuser27CryptoAddress());
//		ApiResponse registerResponse =api.register(user);
//		System.out.println(((BaseResponse)registerResponse.getData()).getMessage());
//		Thread.sleep(3000);
//		
//		//request KYC

		
		//get Account balance
		ApiResponse getAccountBalanceResponse = api.getAccountBalance(handle23, reader.getCellData(sheetName, privatekeys, 3), validDirectAccountName);
		
		System.out.println(((BaseResponse)getAccountBalanceResponse.getData()).getMessage());
		Assert.assertEquals(getAccountBalanceResponse.getStatusCode(), 200);
		Assert.assertEquals(getAccountBalanceResponse.getSuccess(),  successTrue);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getAccountName(), validPlaidAccountName);
		Assert.assertNotNull(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getRoutingNumber());
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getCurrentBalance(), 110);
		Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getAvailableBalance(), 100);
		//Assert.assertEquals(((AccountBalanceResponse)getAccountBalanceResponse.getData()).getMaskedAccountNumber(), 0000);




	}	
	*/
	
}
