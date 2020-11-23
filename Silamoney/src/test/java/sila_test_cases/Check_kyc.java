package sila_test_cases;

import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.User;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;

import io.qameta.allure.Description;

public class Check_kyc extends Base_class{

	


	@Test(priority=1)
	@Description("check kyc with not requested kyc user_handle")
	public void Test_01_Check_kyc_with_not_requested_kyc_user_handle() throws Exception {
		
		//User5
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user1 = new User(handle2,  firstName,  lastName,  streetAddress1, streetAddress2, city,  state, 
				postalCode,  phone,  email, identityNumber, Utility.getUser2CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 12, Utility.getUser2PrivateKey());
		ApiResponse response1 = api.register(user1);
		Thread.sleep(3000);

		ApiResponse response = api.checkKYC(handle2, reader.getCellData(sheetName, privatekeys, 12));

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);		
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), ErrorCheckKYCMessage2);
	
	}
	

	@Test(priority=2)
	@Description("check kyc verification pending")
	public void Test_02_Check_kyc_verification_pending() throws Exception {
		ApiResponse response24 = api.requestKYC(handle24, null, reader.getCellData(sheetName, privatekeys, 4)); //request for kyc

		ApiResponse response = api.checkKYC(handle24, reader.getCellData(sheetName, privatekeys, 4));

		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);		
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
		Assert.assertNotEquals(((BaseResponse)response.getData()).getReference(), refNotEmpty);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), pendingKysValificationMgs);
	
	}
	
	
	@Test(priority=3)
	@Description("check kyc verification successfully passed")
	public void Test_03_Check_kyc_verification_successfully_passed() throws Exception {
		ApiResponse response = api.checkKYC(handle24, reader.getCellData(sheetName, privatekeys, 4));
		for (int i = 1; i <=10; i++) {
			Thread.sleep(30000);
			System.out.println(((BaseResponse)response.getData()).getMessage());
			if (((BaseResponse)response.getData()).getMessage().equals(passKycValificationMgs)) {
				break;
			}
		}

		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);		
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusTrue);
		Assert.assertNotEquals(((BaseResponse)response.getData()).getReference(), refNotEmpty);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), passKycValificationMgs);
	
	}

	
	
	@Test(priority=4)
	@Description("check kyc with unregistered user_handle")
	public void Test_04_Check_kyc_with_unregistered_user_handle() throws Exception {
		ApiResponse response = api.checkKYC(handleNotRegistered, Utility.getuser22PrivateKey());

		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
	
	}
	
	

	@Test(priority=5)
	@Description("check kyc with empty user_handle")
	public void Test_05_Check_kyc_with_empty_user_handle() throws Exception {
		ApiResponse response = api.checkKYC("", reader.getCellData(sheetName, privatekeys, 4));

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	
	}
	
	

	@Test(priority=6)
	@Description("check kyc with invalid invalid signature")
	public void Test_06_Check_kyc_with_invalid_signature() throws Exception {
		ApiResponse response = api1.checkKYC(handle22, Utility.getuser22PrivateKey());

		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),invalidSignErrorMsg);
	
	}

	
	



}
