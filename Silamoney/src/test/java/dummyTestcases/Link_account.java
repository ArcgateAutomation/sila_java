package dummyTestcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

public class Link_account extends Base_class{

	
	
	//Link account with Plaid

	@Test(priority=1)
	@Description("Link account with all valid Data")
	public void Test_01_Link_account_with_all_valid_data() throws Exception {
		ApiResponse response2 = api.linkAccount(handle22, reader.getCellData(sheetName, privatekeys, 2), validPlaidAccountName, validPublicToken);
		//ApiResponse response3 = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccountName, validPublicToken);
		ApiResponse response4 = api.linkAccount(handle24, reader.getCellData(sheetName, privatekeys, 4), validPlaidAccountName, validPublicToken);
		
		Assert.assertEquals(response4.getStatusCode(), successStatusCode);
		Assert.assertEquals(response4.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response4.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response4.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response4.getData()).getMessage(), successAccountLinkMsg);
	}
			

	
	
//Link acocunt with direct link account -------------------------------------------------------------------------
	
	@Test(priority=11)
	@Description("Link account with direct link account_success")
	public void Test_11_Link_account_with_direct_link_account_success() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validDirectAccountName, accountNumber, routingNumber, accountType);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), directLinkAccSuccessMsg);
	}
	
	


}
