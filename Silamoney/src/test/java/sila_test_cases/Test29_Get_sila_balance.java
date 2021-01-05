package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.GetSilaBalanceResponse;
import com.silamoney.client.domain.GetWalletsResponse;
import com.silamoney.client.domain.SearchFilters;
import com.silamoney.common_files.Base_class;

import io.qameta.allure.Description;

public class Test29_Get_sila_balance extends Base_class{
	
	@Test(priority = 1)
	@Description("Get sila balance with all valid data")
	public void test_01_get_Sila_balance() throws Exception {
		ApiResponse response = api.silaBalance(reader.getCellData(sheetName, cryptoAddress, 2));

		System.out.println(response.getStatusCode()); // 200
		System.out.println(((GetSilaBalanceResponse)response.getData()).getAddress());
		System.out.println(((GetSilaBalanceResponse)response.getData()).getReference());
		System.out.println(((GetSilaBalanceResponse)response.getData()).getSilaBalance());
		System.out.println(((GetSilaBalanceResponse)response.getData()).getStatus());
		System.out.println(((GetSilaBalanceResponse)response.getData()).getSuccess());
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((GetSilaBalanceResponse)response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((GetSilaBalanceResponse)response.getData()).getSilaBalance(), 0.0);
		//((GetSilaBalanceResponse)response.getData()).getAddress()); // Address
		//System.out.println(((GetSilaBalanceResponse)response.getData()).getSilaBalance()); .getSilaBalance()); // Sila balance.

	}
	
	
	@Test(priority = 2)
	@Description("Get sila balance with empty_blockChainAddress")
	public void test_01_get_Sila_balance_with_empty_blockChainAddress() throws Exception {
		ApiResponse response = api.silaBalance("");

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

	}
	
	
	@Test(priority = 2)
	@Description("Get sila balance with invalid cryto address")
	public void test_01_get_Sila_balance_with_invalid_blockChainAddress() throws Exception {
		ApiResponse response = api.silaBalance("0019c7cae032a181c521ace94397610e163bdfaaaa");
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);


	}


}
