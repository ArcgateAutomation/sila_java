package dummyTestcases;


import java.io.IOException;

import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.User;
import com.silamoney.client.exceptions.BadRequestException;
import com.silamoney.client.exceptions.ForbiddenException;
import com.silamoney.client.exceptions.InvalidSignatureException;
import com.silamoney.client.exceptions.ServerSideException;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;
import io.qameta.allure.Description;

public class Request_kyc extends Base_class {
	


	@Test(priority=1)
	@Description("Request KYC without kyc_level entity")
	public void Test_01_Request_KYC_Without_kyc_level_entity() throws Exception {	
		ApiResponse response24 = api.requestKYC(handle24, null, reader.getCellData(sheetName, privatekeys, 4));
		Thread.sleep(3000); //kyc requested in check_kyc class
		
		ApiResponse response23 = api.requestKYC(handle23, null, reader.getCellData(sheetName, privatekeys, 3));
		Thread.sleep(3000);
		
		ApiResponse response22 = api.requestKYC(handle22, null, reader.getCellData(sheetName, privatekeys, 2));
		Thread.sleep(3000);
		
		Assert.assertEquals(response22.getStatusCode(), successStatusCode);
		Assert.assertEquals(response22.getSuccess(), successTrue);		
		Assert.assertEquals(((BaseResponse)response22.getData()).getStatus(), statusTrue);
		Assert.assertNotEquals(((BaseResponse)response22.getData()).getReference(), refNotEmpty);
		Assert.assertEquals(((BaseResponse)response22.getData()).getMessage(), successKYCMessage22);

	}
	



}
