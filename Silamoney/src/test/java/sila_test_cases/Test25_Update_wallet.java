package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;

import io.qameta.allure.Description;

public class Test25_Update_wallet extends Base_class{
	
	@Test(priority = 1)
	@Description("Verify user is able to update wallet name")
	public void test_01_update_wallet() throws Exception {
	ApiResponse response = api.updateWallet(handle22, "wallet_test_nick", false, reader.getCellData(sheetName, privatekeys, 2));
	
	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Wallet updated.");

	}
	
	
	@Test(priority = 2)
	@Description("Verify user is able to update wallet with alredy used nick name")
	public void test_02_update_wallet_with_nickname_already_in_use()throws Exception {
	ApiResponse response = api.updateWallet(handle22, wallet_name,  false, reader.getCellData(sheetName, privatekeys, 2));
	
	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
	Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
	//Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Case-insensitive wallet nickname \""+wallet_name+"\" already assigned to address "+Register_wallet.getBlockChainAddress_key+".");

}


	@Test(priority = 3)
	@Description("Verify user is not able to update the wallet when user_handle is empty.")
	public void test_03_update_wallet_with_empty_user_handle() throws Exception {
	ApiResponse response = api.updateWallet("", "wallet_test_UPD", false, reader.getCellData(sheetName, privatekeys, 2));
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}
	
	
	
	@Test(priority = 4)
	@Description("Verify user is not able to update the wallet when user_handle is not regietered.")
	public void test_04_update_wallet_with_unregistered__user_handle() throws Exception {
	ApiResponse response = api.updateWallet(Handle_not_registered, "wallet_test_UPD", false, reader.getCellData(sheetName, privatekeys, 2));
	
	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), HandleNotRegisteredErrorMgs);
	}


	@Test(priority = 5)
	@Description("Verify auth signature validation sending invalid eth_private_key for user_handle")
	public void test_05_update_wallet_invalid_key() throws Exception {
	ApiResponse response = api.updateWallet(handle22, "wallet_test_UPD", false, reader.getCellData(sheetName, privatekeys, 7));
	
	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
	Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(),"failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 7)+" is not registered to "+Handle_22+".".toLowerCase());
}

	@Test(priority = 6)
	@Description("Verify user is able to update wallet when nickname is empty")
	public void test_06_update_wallet_empty_nickname() throws Exception {
	ApiResponse response = api.updateWallet(handle22, null, false, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
	Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"No changes to apply.");

}
	
	
	@Test(priority = 7)
	@Description("Verify user is able to update wallet with 40 characters nock name")
	public void test_07_update_wallet_with_40_characters_nickname() throws Exception {
	ApiResponse response = api.updateWallet(handle22, textWith40Char,  false, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
	Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Wallet updated.");

}
	
	
	@Test(priority = 8)
	@Description("Verify user is able to update wallet with 1 character nock name")
	public void test_08_update_wallet_with_1_character_nickname() throws Exception {
	ApiResponse response = api.updateWallet(handle22, "t", false, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
	Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Wallet updated.");

}
	
	
	@Test(priority = 9)
	@Description("Verify user is able to update wallet with 41 character nock name")
	public void test_09_update_wallet_with_41_character_nickname() throws Exception {
	ApiResponse response = api.updateWallet(handle22, textWith41Char, false, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

}

	@Test(priority = 10)
	@Description("Verify user is able to update wallet with default = True")
	public void test_10_update_wallet_default_true() throws Exception {
	ApiResponse response = api.updateWallet(handle22, "wallet updated", true, reader.getCellData(sheetName, privatekeys, 2));
	
	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
	Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Wallet updated.");

}




}
