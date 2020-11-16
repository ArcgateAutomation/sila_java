package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

public class Link_account extends Base_class{

	
	//String accountName="defaultacc";
	
	//Link account with Plaid

	@Test(priority=1)
	@Description("Link account with all valid Data")
	public void Test_01_Link_account_with_all_valid_data() throws Exception {
		ApiResponse response2 = api.linkAccount(handle22, reader.getCellData(sheetName, privatekeys, 2), validAccounrName, validPublicToken);
		ApiResponse response3 = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, validPublicToken);
		ApiResponse response4 = api.linkAccount(handle24, reader.getCellData(sheetName, privatekeys, 4), validAccounrName, validPublicToken);
		
		Assert.assertEquals(response4.getStatusCode(), successStatusCode);
		Assert.assertEquals(response4.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response4.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response4.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response4.getData()).getMessage(), successAccountLinkMsg);
	}
			

	@Test(priority=2)
	@Description("Link account with empty user_handle")
	public void Test_02_Link_account_with_empty_user_handle() throws Exception {
		//test=extent.createTest("LinkAccount_01: Link account with empty user_handle");	
		ApiResponse response = api.linkAccount("", reader.getCellData(sheetName, privatekeys, 3), validAccounrName, validPublicToken);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

	}
	
	

	@Test(priority=3)
	@Description("Link account with unregistered user_handle")
	public void Test_03_Link_account_with_unregistered_user_handle() throws Exception {
		//test=extent.createTest("LinkAccount_02: Link account with unregistered user_handle");	
		ApiResponse response = api.linkAccount(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, validPublicToken);



		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);

	}
	


	@Test(priority=4)
	@Description("Link account with empty Public_Token")
	public void Test_04_Link_account_with_empty_Public_Token() throws Exception {
		//test=extent.createTest("LinkAccount_03: Link account with empty Public_Token");	
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, emptyPublicToken);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);


	
	}
	
	

	@Test(priority=5)
	@Description("Link account with empty account name")
	public void Test_05_Link_account_with_empty_account_name() throws Exception {
		ApiResponse response = api.linkAccount(handle24, reader.getCellData(sheetName, privatekeys, 4), emptyAccountName, validPublicToken);

		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), successAccountLinkMsg);
	}
	
	

	

	@Test(priority=6)
	@Description("Link account with 1 character account name")
	public void Test_06_Link_account_with_1_char_account_name() throws Exception {
		//test=extent.createTest("LinkAccount_05: Link account with 1 character account name");	
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), accountNameWith1Char, validPublicToken);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), successAccountLinkMsg);
	}
	
	
	

	@Test(priority=7)
	@Description("Link account with 41 characters account name")
	public void Test_07_Link_account_with_41_chars_account_name() throws Exception {
		//test=extent.createTest("LinkAccount_05: Link account with 41 characters account name");	
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), accountNameWith41Chars, validPublicToken);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}
	


	@Test(priority=8)
	@Description("Link account with allowed 40 characters account name")
	public void Test_08_Link_account_with_40_chars_account_name() throws Exception {
		//test=extent.createTest("LinkAccount_06: Link account with allowed 40 characters account name");	
		ApiResponse response = api.linkAccount(handle24, reader.getCellData(sheetName, privatekeys, 4), accountNameWith40Chars, validPublicToken);


		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), successAccountLinkMsg);
	}
	
	

	@Test(priority=9)
	@Description("Link account with invalid signature")
	public void Test_09_Link_account_with_invalid_signature() throws Exception {
		//test=extent.createTest("LinkAccount_07: Link account with invalid signature");	
		ApiResponse response = api1.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), accountNameWith40Chars, validPublicToken);
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),invalidSignErrorMsg);
	}
	

	
	

	@Test(priority=10)
	@Description("Link account with invalid Public_Token")
	public void Test_10_Link_account_with_invalid_Public_Token() throws Exception {
		ApiResponse response = api.linkAccount(handle24, reader.getCellData(sheetName, privatekeys, 4), validAccounrName, invalidPublicToken);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),invalidPublicTokenErrorMsg);
	}

	
	
	
//Link acocunt with direct link account -------------------------------------------------------------------------
	
	@Test(priority=11)
	@Description("Link account with direct link account_success")
	public void Test_11_Link_account_with_direct_link_account_success() throws Exception {
		ApiResponse response = api.linkAccount(handle22, reader.getCellData(sheetName, privatekeys, 2), validAccounrName, accountNumber, routingNumber, accountType);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), directLinkAccSuccessMsg);
	}
	
	
	
	@Test(priority=12)
	@Description("Link direct link account with Empty user_handle")
	public void Test_12_Link_direct_link_account_with_empty_user_handle() throws Exception {
		ApiResponse response = api.linkAccount("", reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumber, accountType);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}
	
	
	
	@Test(priority=13)
	@Description("Link direct link account with unregistered user_handle")
	public void Test_13_Link_direct_link_account_with_unregistered_user_handle() throws Exception {
		ApiResponse response = api.linkAccount(handleNotRegistered, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumber, accountType);
		
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorCheckKYCMessage);
	}
	
	


	
	@Test(priority=14)
	@Description("Link direct link account with empty account name")
	public void link_direct_link_account_with_empty_account_name() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, emptyAccountNumber, routingNumber, accountType);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), directLinkAccSuccessMsg);
	}
	
	
	
	@Test(priority=15)
	@Description("Link direct link account with 40 characters account name")
	public void link_direct_link_account_with_40Characters_account_name() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), accountNameWith40Chars, accountNumber, routingNumber, accountType);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), directLinkAccSuccessMsg);
	}
	
	
	@Test(priority=16)
	@Description("Link direct link account with 41 characters account name")
	public void link_direct_link_account_with_41Characters_account_name() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), accountNameWith41Chars, accountNumber, routingNumber, accountType);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	
	
	

	
	@Test(priority=17)
	@Description("Link direct link account with empty account number")
	public void link_direct_link_account_with_empty_account_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, emptyAccountNumber, routingNumber, accountType);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	
	
	
	
	
	@Test(priority=18)
	@Description("Link direct link account with 1 digit account number")
	public void link_direct_link_account_with_1_digit_account_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber1Digit, routingNumber, accountType);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), directLinkAccSuccessMsg);
	}	
	
	
	@Test(priority=19)
	@Description("Link direct link account with 17 digit account number")
	public void link_direct_link_account_with_17_digit_account_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber17Digit, routingNumber, accountType);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), directLinkAccSuccessMsg);
	}	

	@Test(priority=20)
	@Description("Link direct link account with 18 digit account number")
	public void link_direct_link_account_with_18_digit_account_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber18Digit, routingNumber, accountType);

		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	
	
	
	

	@Test(priority=21)
	@Description("Link direct link account with invalid format account number")
	public void link_direct_link_account_with_invalid_format_account_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumberWithInvalidFormat, routingNumber, accountType);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	
	
	
	
	
	
	@Test(priority=22)
	@Description("Link direct link account with empty routing number")
	public void link_direct_link_account_with_empty_routing_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, emptyRoutingNumber, accountType);


		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	

	
	@Test(priority=23)
	@Description("Link direct link account with 8 digits routing number")
	public void link_direct_link_account_with_8_digits_routing_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumber8Digits, accountType);

		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	

	
	@Test(priority=24)
	@Description("Link direct link account with 10 digits routing number")
	public void link_direct_link_account_with_10_digits_routing_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumber10Digits, accountType);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	


	
	@Test(priority=25)
	@Description("Link direct link account with invalid format routing number")
	public void link_direct_link_account_with_invalid_format_routing_number() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumberWithInvalidFormat, accountType);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	

	
	@Test(priority=26)
	@Description("Link direct link account with empty account type")
	public void link_direct_link_account_with_empty_account_type() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumber, emptyAccountType);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), directLinkAccSuccessMsg);
	}	

	
	
	@Test(priority=27)
	@Description("Link direct link account with empty account type")
	public void link_direct_link_account_with_lower_case_account_type() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumber, accountTypeLoweCase);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	
	
	
	@Test(priority=28)
	@Description("Link direct link account with 10 characters account type")
	public void link_direct_link_account_with_10_characters_account_type() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumber, accountTypeWith10Chars);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), directLinkAccSuccessMsg);
	}	
	

	
	@Test(priority=29)
	@Description("Link direct link account with 11 characters account type")
	public void link_direct_link_account_with_11_characters_account_type() throws Exception {
		ApiResponse response = api.linkAccount(handle23, reader.getCellData(sheetName, privatekeys, 3), validAccounrName, accountNumber, routingNumber, accountTypeWith11Chars);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}	
	
	

}