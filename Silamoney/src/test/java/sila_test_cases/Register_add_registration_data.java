package sila_test_cases;

import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.AddressMessage;
import com.silamoney.client.domain.AddressResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.DeleteRegistrationMessage;
import com.silamoney.client.domain.EmailMessage;
import com.silamoney.client.domain.EmailResponse;
import com.silamoney.client.domain.GetEntityResponse;
import com.silamoney.client.domain.IdentityMessage;
import com.silamoney.client.domain.IdentityResponse;
import com.silamoney.client.domain.PhoneMessage;
import com.silamoney.client.domain.PhoneResponse;
import com.silamoney.client.domain.RegistrationDataEnum;
import com.silamoney.client.domain.User;
import com.silamoney.client.domain.UserHandleMessage;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;

import io.qameta.allure.Description;

public class Register_add_registration_data extends Base_class {
	
	public String identityUuid="";
	public String addressUuid="";


//Add email test cases	
	@Test(priority = 1)
	@Description("Check add registration with empty user_handle")
	public void test_001_registration_add_email_with_empty_user_handle() throws Exception {
	LocalDate birthdate = new LocalDate(2000, 01, 31);
	User user6 = new User(handle26,  firstName,  lastName,  streetAddress1, streetAddress2, city,  state, postalCode,  phone,  email, identityNumber, Utility.getuser26CryptoAddress(), birthdate.toDate(), country);
	reader.setCellData(sheetName, privatekeys, 6, Utility.getuser26PrivateKey());
	reader.setCellData(sheetName, cryptoAddress, 6, Utility.getuser26CryptoAddress());
	ApiResponse response26 = api.register(user6);
	Thread.sleep(3000);
//Get Entity
	ApiResponse response4 = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) response4.getData();
	identityUuid=entityResponse.getIdentities().get(0).getUuid();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();	
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle("").userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().email(addNewEmail).build();
	ApiResponse response = api.addEmail(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	@Test(priority = 2)
	@Description("Check add registration with empty email field")
	public void test_002_registration_add_empty_email_field() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().email("").build();
	ApiResponse response = api.addEmail(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}

	@Test(priority = 3)
	@Description("Check add registration with invalid private_key")
	public void test_003_registration_add_email_with_invalid_private_key() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(invalidPrivateKey)
	.build();
	EmailMessage message = EmailMessage.builder().email(addNewEmail).build();
	ApiResponse response = api.addEmail(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), invalidSignErrorMsg2);
	}

	@Test(priority = 4)
	@Description("Check add registration with not registered user_handle")
	public void test_004_registration_add_email_with_not_registered_handle() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handleNotRegistered)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().email(addNewEmail).build();
	ApiResponse response = api.addEmail(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), HandleNotRegisteredErrorMgs);
	}

	@Test(priority = 5)
	@Description("Check add registration with invalid email format")
	public void test_005_registration_add_invalid_email() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().email(invalid_Email).build();
	ApiResponse response = api.addEmail(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}

	@Test(priority = 6)
	@Description("Check add registration with more than max email length")
	public void test_006_registration_add_email_with_more_than_max_email_length() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().email(above_max_length_email).build();
	ApiResponse response = api.addEmail(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}

	@Test(priority = 7)
	@Description("Verify user is  able to add email with 254 characters.")
	public void test_007_registration_add_email_successfully_with_254_characters_email() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().email(max_length_email).build();
	ApiResponse response = api.addEmail(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	EmailResponse parsedResponse = (EmailResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully added email to user " + Handle_26 + ".");
	Assert.assertNotNull(parsedResponse.getEmail().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getEmail().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getEmail().getUuid());
	Assert.assertEquals(parsedResponse.getEmail().getEmail(), max_length_email);

	}

	@Test(priority = 8)
	@Description("Check registration add email with all valid data")
	public void test_008_registration_add_email_with_all_valid_data() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().email(addNewEmail).build();
	ApiResponse response = api.addEmail(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	EmailResponse parsedResponse = (EmailResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully added email to user " + Handle_26 + ".");
	Assert.assertNotNull(parsedResponse.getEmail().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getEmail().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getEmail().getUuid());
	Assert.assertEquals(parsedResponse.getEmail().getEmail(), addNewEmail);
	}

//Add phone test cases///////////////////////////////////////////////////////////////

	@Test(priority = 9)
	@Description("Verify user is not able to add identity when phone is empty.")
	public void test_009_registration_add_phone_num_with_empty_phone_field() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().phone("").build();
	ApiResponse response = api.addPhone(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}

	@Test(priority = 10)
	@Description("Verify user is not able to add phone number when phone is invalid.")
	public void test_010_registration_add_invalid_phone_number() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().phone("test").build();
	ApiResponse response = api.addPhone(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}

	@Test(priority = 11)
	@Description("Verify user is not able to add phone number when phone is less than 10 digits")
	public void test_010_registration_add_less_than_10_digits_phone_number() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().phone("245-985-999").build();
	ApiResponse response = api.addPhone(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}

	@Test(priority = 12)
	@Description("Verify user is not able to add phone number when phone is more than 11 digits")
	public void test_012_registration_add_more_than_11_digits_phone_number() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
	.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().phone("958-858-989856").build();
	ApiResponse response = api.addPhone(user, message);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}

	@Test(priority = 13)
	@Description("Verify user is not able to add phone number when 10 digits phone field")
	public void test_013_registration_add_10_digits_phone_number_successfully() throws Exception {

	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().phone(addNewPhone).build();
	ApiResponse response = api.addPhone(user, message);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	PhoneResponse parsedResponse = (PhoneResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully added phone to user " + Handle_26 + ".");
	Assert.assertNotNull(parsedResponse.getPhone().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getPhone().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getPhone().getUuid());
	Assert.assertEquals(parsedResponse.getPhone().getPhone(), addNewPhone);
	}
	
	
	

	
//Add identity test cases

	@Test(priority = 14)
	@Description("Verify user is not able to add identity when identity is empty.")
	public void test_014_registration_add_identity_with_empty_identity_field() throws Exception {
    UserHandleMessage user = UserHandleMessage.builder().userHandle("").userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
    IdentityMessage message2 = IdentityMessage.builder().identityAlias("SSN").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}


	@Test(priority = 15)
	@Description("Verify user is not able to add identity alias value when field is empty")
	public void test_015_Registration_add_empty_identity_alias() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);


	}	
	
	
	
	@Test(priority = 16)
	@Description("Verify user is not able to add identity alias value when field is empty")
	public void test_016_registration_add_empty_identity_value() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("SSN").identityValue("").build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}	
		
	
	
	@Test(priority = 17)
	@Description("Verify user is not able to add identity with invalid identity alias field in payload.")
	public void test_017_registration_add_invalid_identity_alias() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("test").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}	
	

	@Test(priority = 18)
	@Description("Verify user is not able to add lowercase identity alias value.")
	public void test_018_registration_add_lowercase_identity_alias_value() throws Exception {
	//Add identity	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("ssn").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}	
		
	
	@Test(priority = 19)
	@Description("Verify user is not able to add EIN to individual users.")
	public void test_019_registration_add_identity_alias_value_as_EIN_to_individual_user() throws Exception {	
	//Add identity	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("EIN").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), identityAliasErrorMgs);
	}	
		
	
	@Test(priority = 20)
	@Description("Verify user is not able to add EIN to individual users.")
	public void test_020_registration_add_identity_alias_value_as_EIN_to_individual_user() throws Exception {	
	//Add identity	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("EIN").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), identityAliasErrorMgs);
	}	
	
	
	
	
	@Test(priority = 21)
	@Description("Verify user will not be able to add identity for individual with different user_handle")
	public void test_021_registration_add_individual_user_identity_with_another_user_handle() throws Exception {	
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		
	//Add identity	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle22).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("SSN").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);

	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	String Expected_Err_Mgs="Failed to authenticate user signature. The derived address "+Base_class.reader.getCellData(Base_class.sheetName, Base_class.cryptoAddress, 6)+" is not registered to "+Base_class.Handle_22+".";
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage().toLowerCase(), Expected_Err_Mgs.toLowerCase());
	}	
	
	
	@Test(priority = 22)
	@Description("Verify user will not be able to add identity for individual with unregistered user_handle")
	public void test_022_registration_add_individual_user_identity_with_unregistered_user_handle() throws Exception {	
	//Add identity	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handleNotRegistered).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("SSN").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), invalidSignErrorMsg4);
	}

	@Test(priority = 23)
	@Description("Verify user will not be able to add identity for individual with invalid auth_signature")
	public void test_023_registration_add_individual_user_identity_with_invalid_auth_signature() throws Exception {	
	//Add identity	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("SSN").identityValue(addNewIdentity).build();
	ApiResponse response = api2.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), invalidSignErrorMsg5);
	}
	
	
	@Test(priority = 24)
	@Description("Verify user is able to add identity for individual user successfully.")
	public void test_024_registration_add_individual_user_identity_successfull() throws Exception {	
	
	ApiResponse response4 = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) response4.getData();
	String identityUuid1=entityResponse.getIdentities().get(0).getUuid();
		
	//Delete identity
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).uuid(identityUuid1).build();
	ApiResponse response2 = api.deleteRegistrationData(RegistrationDataEnum.IDENTITY, message);
	BaseResponse parsedResponse1 = (BaseResponse) response2.getData();

		
	//Add identity	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message1 = IdentityMessage.builder().identityAlias("SSN").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message1);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IdentityResponse parsedResponse = (IdentityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully added identity to user " + Handle_26 + ".");
	Assert.assertNotNull(parsedResponse.getIdentity().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getIdentity().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getIdentity().getUuid());
	Assert.assertNotNull(parsedResponse.getIdentity().getIdentity());
	}	
		

	
	@Test(priority = 25)
	@Description("Verify user is not able to assigned identity value to already assigned user")
	public void test_025_registration_add_identity_alias_value_to_already_assigned_user() throws Exception {	
	//Add identity	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message2 = IdentityMessage.builder().identityAlias("SSN").identityValue(addNewIdentity).build();
	ApiResponse response = api.addIdentity(user, message2);
	Thread.sleep(3000);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), identityErrorMgs);
	}	
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
//Add Address
	

	
		
		@Test(priority = 26)
		@Description("Verify user is not able to add address when address_alias field is empty")
		public void test_026_registration_add_address_with_empty_address_alias() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias("").streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
	  
		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getNickname(), "");
		}		
		

		
		
		@Test(priority = 27)
		@Description("Verify user is not able to add address with empty  street_address_1 field")
		public void test_27_registration_add_address_with_empty_street_address_1() throws Exception {

//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1("")
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
 
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		

		
		
		@Test(priority = 28)
		@Description("Verify user is not able to add less than 3 characters in street_address_1.")
		public void test_028_registration_add_street_address_1_address_with_less_than_3_characters() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1("at")
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
	  
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

		
		}		
		
		
		@Test(priority = 29)
		@Description("Verify user is able to add 3 characters in the street_address_1")
		public void test_029_registration_add_street_address_1_address_with_3_characters() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);
		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1("ate")
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);	  
  
		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getStreetAddress1(), "ate");
		}		
		
		
		@Test(priority = 30)
		@Description("Verify user is not able to add 40 characters in street_address_1.")
		public void test_030_registration_add_street_address_1_address_with_40_character() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(streetAddress_40Chars)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);

		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getStreetAddress1(), streetAddress_40Chars);
		}		
		
		
		@Test(priority = 31)
		@Description("Verify user is not able to add more than 40 characters in street_address_1.")
		public void test_031_registration_add_street_address_1_address_with_more_than_max_character() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(streetAddress_41Chars)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		
		@Test(priority = 32)
		@Description("Verify user is able to add empty value in street address 2.")
		public void test_032_registration_add_empty_street_address_2_address() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2("").city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
	  
	  	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	  
		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getStreetAddress2(), "");

		}		
		

		
		@Test(priority = 33)
		@Description("Verify user is not able to add less than 3 characters in street_address_2.")
		public void test_033_registration_add_street_address_2_address_with_less_than_3_characters() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2("at").city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
	  
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		
		@Test(priority = 34)
		@Description("Verify user is not able to add more than 40 characters in street_address_2.")
		public void test_034_registration_add_street_address_2_address_with_more_than_max_characters() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(streetAddress_41Chars).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
	  
	  
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		
		@Test(priority = 35)
		@Description("Verify user is able to add 40 characters in street_address_2 successfully")
		public void test_035_registration_add_street_address_2_address_with_40_characters() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(streetAddress_40Chars).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
	  

		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getStreetAddress2(), streetAddress_40Chars);
		}		
		
			
		
		@Test(priority = 36)
		@Description("Verify user is able to add 3 characters in street_address_2 successfully")
		public void test_036_registration_add_street_address_2_address_with_3_characters() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2("ate").city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
	  

		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getNickname(), addAddressAlias);
		Assert.assertEquals(parsedResponse.getAddress().getStreetAddress1(), addStreetAddress1);
		Assert.assertEquals(parsedResponse.getAddress().getStreetAddress2(), "ate");
		Assert.assertEquals(parsedResponse.getAddress().getCity(), addCity);
		Assert.assertEquals(parsedResponse.getAddress().getState(), addState);
		Assert.assertEquals(parsedResponse.getAddress().getCountry(), addCountry);
		Assert.assertEquals(parsedResponse.getAddress().getPostalCode(), addPostalCode);
		}		
		
			
		
		@Test(priority = 37)
		@Description("Verify user is not able to add address when city field is empty in payload.")
		public void test_037_registration_add_address_with_empty_city() throws Exception {
		//Delete aaddress
//		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) 
//				.userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).uuid(addressUuid).build();
//		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
//		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city("").state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);
	  

	  Assert.assertEquals(response.getStatusCode(), 400);
	  Assert.assertEquals(response.getSuccess(), successFalse);
	  Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	  Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		
		@Test(priority = 38)
		@Description("Verify user is not able to add less than 2 characters in city name.")
		public void test_038_registration_add_address_with_less_than_2_characters_city_name() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city("a").state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		

		
		@Test(priority = 39)
		@Description("Verify user is not able to add more than 40 characters in city name.")
		public void test_039_registration_add_address_with_more_than_40_characters_city_name() throws Exception {	
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		 .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(city41Chars).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		
		@Test(priority = 40)
		@Description("Verify user is able to add 40 characters in city name.")
		public void test_040_registration_add_address_with_40_characters_city_name() throws Exception {	
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(city40Chars).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
	  Thread.sleep(3000);


		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getCity(), city40Chars);


		}		
		

		@Test(priority = 41)
		@Description("Verify user is not able to add address when state field is empty.")
		public void test_041_registration_add_address_with_empty_state() throws Exception {

//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state("").country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		
		@Test(priority = 42)
		@Description("Verify user is not able to add address with less than 2 charcters state field")
		public void test_042_registration_add_adress_with_less_than_2_chars_state_field() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state("O").country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		

		
		@Test(priority = 43)
		@Description("Verify user is not able to add address with more than 2 charcters state field")
		public void test_043_registration_add_adress_with_more_than_2_chars_state_field() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state("ORE").country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		
		
		@Test(priority = 44)
		@Description("Verify user is not able to add address when state field has invalid US abbreviations.")
		public void test_044_registration_add_address_with_invalid_abbreviations_for_US_states() throws Exception {

	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state("00").country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		

		
		
		@Test(priority = 45)
		@Description("Verify user is not able to add address with empty postal code in payload.")
		public void test_045_registration_add_address_with_empty_postal_code() throws Exception {	
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode("").build();
	  ApiResponse response = api.addAddress(user, message3);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		

		
		@Test(priority = 46)
		@Description("Verify user is not able to add more than 10 digits postal code.")
		public void test_046_registration_add_address_with_more_than_max_length_postal_code() throws Exception {	
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode("97204-525241").build();
	  ApiResponse response = api.addAddress(user, message3);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		

		
		@Test(priority = 47)
		@Description("Verify user is able to add 10 digits postal code successfully.")
		public void test_047_registration_add_address_with_10_digitss_postal_code() throws Exception {	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode("9722543881").build();
	  ApiResponse response = api.addAddress(user, message3);
	  
		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getPostalCode(), "9722543881");
		}	
		
		

//		
//		@Test(priority = 48)
//		@Description("Verify user is able to add 1 digit postal code successfully.")
//		public void test_048_registration_add_address_with_1_digit_postal_code() throws Exception {	
//			//Add address	
//			  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
//			  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
//			  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode("9").build();
//			  ApiResponse response = api.addAddress(user, message3);
//			  
//				AddressResponse parsedResponse = (AddressResponse) response.getData();
//				Assert.assertEquals(response.getStatusCode(), successStatusCode);
//				Assert.assertEquals(response.getSuccess(), successTrue);
//				Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
//				Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
//				Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
//				Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
//				Assert.assertNotNull(parsedResponse.getAddress().getUuid());
//				Assert.assertEquals(parsedResponse.getAddress().getPostalCode(), "5");
//		}	
//		

		
		

		
		@Test(priority = 49)
		@Description("Verify user is not able to add address with empty country field in payload.")
		public void test_049_registration_add_address_with_empty_country() throws Exception {
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country("").postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		@Test(priority = 50)
		@Description("Verify user is not able to add another country name in payload.")
		public void test_050_registration_add_address_with_another_country_name() throws Exception {
	
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);
		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country("INDIA").postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
			

		@Test(priority = 51)
		@Description("Verify user is not able to add less than 2 characters country name.")
		public void test_051_registration_add_address_with_less_than_2_characters_country_name() throws Exception {
	
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);
		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country("U").postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

		}		
		
		
		@Test(priority = 52)
		@Description("Verify user is not able to add more than 2 characters country name.")
		public void test_052_registration_add_address_with_more_than_2_characters_country_name() throws Exception {
	
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);
		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country("USA").postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}	
		
		
		
		
		@Test(priority = 53)
		@Description("Verify user is not able to add lowercase country name in payload.")
		public void test_053_registration_add_address_with_lower_case_country_name() throws Exception {
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);
		
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country("us").postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
		}		
		
		
		@Test(priority = 54)
		@Description("Verify user is able to add address successfully with all the valid fields")
		public void test_054_registration_add_address_successfully() throws Exception {	
//Delete aaddress
		DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle26) .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6))
		        .uuid(addressUuid).build();
		ApiResponse deleteResponse = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);
		
	
//Add address	
	  UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	  AddressMessage message3 = AddressMessage.builder().addressAlias(addAddressAlias).streetAddress1(addStreetAddress1)
	  		.streetAddress2(addStreetAddress2).city(addCity).state(addState).country(addCountry).postalCode(addPostalCode).build();
	  ApiResponse response = api.addAddress(user, message3);		
		
		AddressResponse parsedResponse = (AddressResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "Successfully added address to user "+Handle_26+".");
		Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
		Assert.assertNotNull(parsedResponse.getAddress().getUuid());
		Assert.assertEquals(parsedResponse.getAddress().getNickname(), addAddressAlias);
		Assert.assertEquals(parsedResponse.getAddress().getStreetAddress1(), addStreetAddress1);
		Assert.assertEquals(parsedResponse.getAddress().getStreetAddress2(), addStreetAddress2);
		Assert.assertEquals(parsedResponse.getAddress().getCity(), addCity);
		Assert.assertEquals(parsedResponse.getAddress().getState(), addState);
		Assert.assertEquals(parsedResponse.getAddress().getCountry(), addCountry);
		Assert.assertEquals(parsedResponse.getAddress().getPostalCode(), addPostalCode);
		}		
		

	

}





