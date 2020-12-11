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
		ApiResponse response = api.checkKYC(handle26, reader.getCellData(sheetName, privatekeys, 6));

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "ID verification was not requested for "+Handle_26+".");
	
	}
	

	@Test(priority=2)
	@Description("check kyc verification pending")
	public void Test_02_Check_kyc_verification_pending() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User(handle25,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser25CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 5, Utility.getuser25PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 5, Utility.getuser25CryptoAddress());
		api.register(user);
		Thread.sleep(3000);
		
		api.requestKYC(handle25, null, reader.getCellData(sheetName, privatekeys, 5)); //request for kyc
		ApiResponse response = api.checkKYC(handle25, reader.getCellData(sheetName, privatekeys, 5)); //Check KYC

		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);		
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
		Assert.assertNotEquals(((BaseResponse)response.getData()).getReference(), refNotEmpty);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), Handle_25+" is pending ID verification.");
	
	}
	
	
	@Test(priority=3)
	@Description("check kyc verification successfully passed")
	public void Test_03_Check_kyc_verification_successfully_passed() throws Exception {
		
		for (int i = 1; i <=10; i++) {
			Thread.sleep(30000);
			ApiResponse response = api.checkKYC(handle24, reader.getCellData(sheetName, privatekeys, 4));
			System.out.println(((BaseResponse)response.getData()).getMessage());
			if (((BaseResponse)response.getData()).getMessage().equals(passKycValificationMgs)) {
				break;
			}
		}
		
		ApiResponse response = api.checkKYC(handle24, reader.getCellData(sheetName, privatekeys, 4));

		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);		
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
