package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

public class Test23_Get_wallet extends Base_class {
		@Test(priority = 1)
		@Description("Get wallet details for with all valid data")
		public void test_001_get_wallet_details_with_valid_data() throws Exception {
		ApiResponse getWalletResponse = api.getWallet(handle22, reader.getCellData(sheetName, privatekeys, 2));

		Assert.assertEquals(getWalletResponse.getStatusCode(), 200);
		Assert.assertEquals(getWalletResponse.getSuccess(), successTrue);
		Assert.assertEquals(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getSilaBalance(), 0.0);
		Assert.assertNotNull(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getReference());
		}
		
		
		
		@Test(priority = 2)
		@Description("Verify get wallet when user handle is empty.")
		public void test_002_get_wallet_for_empty_user_handle() throws Exception {
		ApiResponse getWalletResponse = api.getWallet("", reader.getCellData(sheetName, privatekeys, 2));
	    
		Assert.assertEquals(getWalletResponse.getStatusCode(), 400);
		Assert.assertEquals(getWalletResponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)getWalletResponse.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse)getWalletResponse.getData()).getMessage(),validationErrorMsg);
		}
		
		@Test(priority = 3)
		@Description("Verify get wallet with invalid crypto.")
		public void test_003_get_wallet_with_invalid_crypto() throws Exception {
		ApiResponse getWalletResponse = api.getWallet(handle22, reader.getCellData(sheetName, privatekeys, 7));
	    
		Assert.assertEquals(getWalletResponse.getStatusCode(), 403);
		Assert.assertEquals(getWalletResponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)getWalletResponse.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse)getWalletResponse.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 7)+" is not registered to "+Handle_22+".".toLowerCase() );
		}
		
		
		@Test(priority = 4)
		@Description("Verify get wallet with unregistered user_handle.")
		public void test_004_get_wallet_with_unregistered_user_handle() throws Exception {
		ApiResponse getWalletResponse = api.getWallet(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 2));
	    
		Assert.assertEquals(getWalletResponse.getStatusCode(), 403);
		Assert.assertEquals(getWalletResponse.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) getWalletResponse.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)getWalletResponse.getData()).getMessage(),invalidSignErrorMsg4);
		}
		

}
