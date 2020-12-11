package sila_test_cases;


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
		api.requestKYC(handle22, null, reader.getCellData(sheetName, privatekeys, 2));
		Thread.sleep(3000);
		
		api.requestKYC(handle23, null, reader.getCellData(sheetName, privatekeys, 3));
		Thread.sleep(3000);
		
		ApiResponse response=api.requestKYC(handle24, null, reader.getCellData(sheetName, privatekeys, 4));
		Thread.sleep(3000);

		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);		
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
		Assert.assertNotEquals(((BaseResponse)response.getData()).getReference(), refNotEmpty);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), Handle_24+" submitted for KYC review.");

	}
	


	@Test(priority=2)
	@Description("Request KYC with empty kyc_level field")
	public void Test_02_Request_KYC_With_empty_kyc_level_field() throws Exception {
		ApiResponse response = api.requestKYC(handle22, null, reader.getCellData(sheetName, privatekeys, 2));
		Thread.sleep(3000);
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);		
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
		Assert.assertNotEquals(((BaseResponse)response.getData()).getReference(), refNotEmpty);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), successKYCMessage22);
	
	}
	

	
	@Test(priority=3)
	@Description("Request KYC with empty user_handle")
	public void Test_03_Request_KYC_With_empty_user_handle() throws Exception {	
		ApiResponse response = api.requestKYC("", null, reader.getCellData(sheetName, privatekeys, 2));
		Thread.sleep(3000);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	
	}
	


	
	@Test(priority=4)
	@Description("Request KYC with invalid private key")
	public void Test_04_Request_KYC_With_401() throws Exception {
		//test=extent.createTest("RequestKYC_04: Request KYC with invalid private key");		
		ApiResponse response = api1.requestKYC(handle23, null, Utility.getuser23PrivateKey());
		Thread.sleep(3000);
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorMsg2);
	
	}
	


	@Test(priority=5)
	@Description("Request KYC with kyc_level not configured/approved for auth_handle in current environment.")
	public void Test_05_Request_KYC_With_403() throws BadRequestException, InvalidSignatureException, ServerSideException, IOException,
	InterruptedException, ForbiddenException{		
		ApiResponse response = api.requestKYC(handle22, "FAIL", reader.getCellData(sheetName, privatekeys, 2));
		Thread.sleep(3000);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorMsg3);
	}
	
	


	@Test(priority=6)
	@Description("Request KYC with unregistered user_handle")
	public void Test_06_Request_KYC_With_unregistered_user_handle() throws Exception {
		//test=extent.createTest("RequestKYC_06: Request KYC with unregistered user_handle");		
		ApiResponse response = api.requestKYC(handleNotRegistered, null, reader.getCellData(sheetName, privatekeys, 2));
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
	}



}
