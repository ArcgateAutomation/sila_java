package sila_test_cases;

import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.AddressMessage;
import com.silamoney.client.domain.AddressResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.EmailMessage;
import com.silamoney.client.domain.EmailResponse;
import com.silamoney.client.domain.GetEntityResponse;
import com.silamoney.client.domain.IdentityMessage;
import com.silamoney.client.domain.IdentityResponse;
import com.silamoney.client.domain.IndividualEntityMessage;
import com.silamoney.client.domain.IndividualEntityResponse;
import com.silamoney.client.domain.PhoneMessage;
import com.silamoney.client.domain.PhoneResponse;
import com.silamoney.client.domain.UserHandleMessage;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

public class Register_update_registration_data extends Base_class {
	
	public String identityUuid="";
	public String addressUuid="";
	public String emailUuid="";
	public String phoneUuid="";
	
	//LocalDate birthdate = new LocalDate(2000, 01, 31);
	String newBirthdate = "1994, 1, 8";
	

	@Test(priority = 1)
	@Description("Verify user is not able to update email when user handle is empty\"")
	public void test_001_registration_update_email_with_empty_user_handle() throws Exception {	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	emailUuid=entityResponse.getEmails().get(0).getUuid();
	phoneUuid=entityResponse.getPhones().get(0).getUuid();
	identityUuid=entityResponse.getIdentities().get(0).getUuid();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle("").userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().uuid(emailUuid).email(updateEmail).build();
	ApiResponse response = api.updateEmail(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);


	}

	
	@Test(priority = 2)
	@Description("Verify user is not able to update email when email is empty")
	public void test_002_registration_update_empty_email_field() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().uuid(emailUuid).email("").build();
	ApiResponse response = api.updateEmail(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	@Test(priority = 3)
	@Description("Verify user is not able to update email when email is invalid")
	public void test_003_registration_update_invalid_email_field() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().uuid(emailUuid).email(invalid_Email).build();
	ApiResponse response = api.updateEmail(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	

	@Test(priority = 4)
	@Description("Verify user is not able to update email when user private key is invalid")
	public void test_004_registration_update_email_with_invalid_private_key() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).build();
	EmailMessage message = EmailMessage.builder().uuid(emailUuid).email(updateEmail).build();
	ApiResponse response = api.updateEmail(user, message);

	String Expected_Err_Mgs="Failed to authenticate user signature. The derived address "+Base_class.reader.getCellData(Base_class.sheetName, Base_class.cryptoAddress, 7)+" is not registered to "+Base_class.Handle_26+".";
	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage().toLowerCase(), Expected_Err_Mgs.toLowerCase());

	}

	


	@Test(priority = 5)
	@Description("Verify user is not able to update email when user handle is not registered.")
	public void test_005_registration_update_email_with_not_registered_handle() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handleNotRegistered).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().uuid(emailUuid).email(updateEmail).build();
	ApiResponse response = api.updateEmail(user, message);

	//String Expected_Err_Mgs="Failed to authenticate user signature. The derived address "+Base_class.reader.getCellData(Base_class.sheetName, Base_class.cryptoAddress, 6)+" is not registered to "+Base_class.Handle_26+".";
	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), invalidSignErrorMsg4);

	}

	



	@Test(priority = 6)
	@Description("Verify user is not able to update email when email is more than 254 characters")
	public void test_006_registration_update_email_with_more_than_max_email_length() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().uuid(emailUuid).email(above_max_length_email).build();
	ApiResponse response = api.updateEmail(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), invalidSignErrorMsg4);

	}

	


	@Test(priority = 7)
	@Description("Verify user is not able to update email with invalid uuid")
	public void test_007_registration_update_email_with_invalid_uuid() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().uuid(emailUuid+"1").email(updateEmail).build();
	ApiResponse response = api.updateEmail(user, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	

	

	@Test(priority = 8)
	@Description("Verify user is able to update 254 characters email successfully.")
	public void test_008_registration_update_email_successfully_with_254_characters_email() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	EmailMessage message = EmailMessage.builder().uuid(emailUuid).email(max_length_email).build();
	ApiResponse response = api.updateEmail(user, message);

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	EmailResponse parsedResponse = (EmailResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated email with UUID "+emailUuid+".");
	Assert.assertNotNull(parsedResponse.getEmail().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getEmail().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getEmail().getUuid());
	Assert.assertEquals(parsedResponse.getEmail().getEmail(), max_length_email);


	}

	
	
	

	@Test(priority = 9)
	@Description("Verify user is not able to update empty phone number")
	public void test_009_registration_update_phone_num_with_empty_phone_field() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().uuid(phoneUuid).phone("").build();
	ApiResponse response = api.updatePhone(user, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	

	@Test(priority = 10)
	@Description("Verify user is not able to update invalid phone number.")
	public void test_010_registration_update_invalid_phone_number() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().uuid(phoneUuid).phone("test").build();
	ApiResponse response = api.updatePhone(user, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	

	@Test(priority = 11)
	@Description("Verify user is not able to update less than 10 digits phone number in payload.")
	public void test_011_registration_update_phone_number_with_less_than_10_digits() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().uuid(phoneUuid).phone("245-985-999").build();
	ApiResponse response = api.updatePhone(user, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}



	@Test(priority = 12)
	@Description("Verify user is not able to add more than 10 digits phone number in payload")
	public void test_012_registration_update_phone_number_with_more_than_10_digits() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().uuid(phoneUuid).phone("958-858-989856").build();
	ApiResponse response = api.updatePhone(user, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	

	@Test(priority = 13)
	@Description("Verify user is not able to add phone number with invalid uuid")
	public void test_013_registration_update_phone_number_with_invalid_uuid() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().uuid(emailUuid).phone("958-858-9898").build();
	ApiResponse response = api.updatePhone(user, message);

	Assert.assertEquals(response.getStatusCode(), 404);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "No phone with UUID "+emailUuid+" exists for user "+Handle_26+".");

	}

	

	@Test(priority = 14)
	@Description("Verify user is able to update 10 digits phone number successfully.")
	public void test_014_registration_update_10_digits_phone_number_successfully() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	PhoneMessage message = PhoneMessage.builder().uuid(phoneUuid).phone(updatePhone).build();
	ApiResponse response = api.updatePhone(user, message);

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	PhoneResponse parsedResponse = (PhoneResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated phone with UUID "+phoneUuid+".");
	Assert.assertNotNull(parsedResponse.getPhone().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getPhone().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getPhone().getUuid());
	Assert.assertEquals(parsedResponse.getPhone().getPhone(), updatePhone);

	}


	
	

	@Test(priority = 15)
	@Description("Verify user is not able to update identity alias value when field is empty.")
	public void test_015_registration_update_identity_with_empty_identity_alias() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message = IdentityMessage.builder().uuid(identityUuid).identityAlias("").identityValue(updateIdentity).build();
	ApiResponse response = api.updateIdentity(user, message);	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	


	@Test(priority = 16)
	@Description("Verify user is not able to update empty identity value.")
	public void test_016_registration_update_identity_with_empty_identity_value() throws Exception {
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message = IdentityMessage.builder().uuid(identityUuid).identityAlias("SSN").identityValue("").build();
	ApiResponse response = api.updateIdentity(user, message);	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	
	@Test(priority = 17)
	@Description("Verify user is not able to update identity with invalid identity alias field")
	public void test_017_registration_update_identity_with_invalid_identity_alias() throws Exception {
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	identityUuid=entityResponse.getIdentities().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message = IdentityMessage.builder().uuid(identityUuid).identityAlias("TEST").identityValue(updateIdentity).build();
	ApiResponse response = api.updateIdentity(user, message);
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	
	
	@Test(priority = 18)
	@Description("Verify user is not able to update identity alias value in lowercase.")
	public void test_018_registration_update_identity_alias_value_in_lowercase() throws Exception {
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	identityUuid=entityResponse.getIdentities().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message = IdentityMessage.builder().uuid(identityUuid).identityAlias("ssn").identityValue(updateIdentity).build();
	ApiResponse response = api.updateIdentity(user, message);	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	
	
	
	
	
	
	@Test(priority = 19)
	@Description("Verify user is not able to update EIN to individual users.")
	public void test_019_registration_update_identity_alias_value_as_EIN_to_individual_user() throws Exception {
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	identityUuid=entityResponse.getIdentities().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message = IdentityMessage.builder().uuid(identityUuid).identityAlias("EIN").identityValue(updateIdentity).build();
	ApiResponse response = api.updateIdentity(user, message);	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), identityAliasErrorMgs);
	
	}

	

	@Test(priority = 20)
	@Description("Verify user is able to update identity for individual user successfully")
	public void test_020_registration_update_individual_user_identity_successfully() throws Exception {
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	identityUuid=entityResponse.getIdentities().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IdentityMessage message = IdentityMessage.builder().uuid(identityUuid).identityAlias("SSN").identityValue(updateIdentity).build();
	ApiResponse response = api.updateIdentity(user, message);

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IdentityResponse parsedResponse = (IdentityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated identity with UUID "+identityUuid+".");
	Assert.assertNotNull(parsedResponse.getIdentity().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getIdentity().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getIdentity().getUuid());
	Assert.assertNotNull(parsedResponse.getIdentity().getIdentity());
	

	}


	
	

	@Test(priority = 21)
	@Description("Verify user is not able to update address when address_alias field is empty in payload.")
	public void test_021_registration_update_address_with_empty_address_alias() throws Exception {
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).addressAlias(emptyAddressAlias).build();
	ApiResponse response = api.updateAddress(user, message);
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	

	@Test(priority = 22)
	@Description("Verify user is able to update address with address_alias field payload.")
	public void test_022_registration_update_address_alias() throws Exception {
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).addressAlias(updateAddressAlias).build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	Assert.assertEquals(parsedResponse.getAddress().getNickname(), updateAddressAlias);

	}


	
	@Test(priority = 23)
	@Description("Verify user is not able to update address when street_address_1 field is empty")
	public void test_023_registration_update_address_with_empty_street_address_1() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress1("").build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	
	
	
	@Test(priority = 24)
	@Description("Verify user is not able to update less than 3 characters in street_address_1.")
	public void test_024_registration_update_street_address_1_address_with_less_than_3_characters() throws Exception {
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress1("st").build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	
	
	
	@Test(priority = 25)
	@Description("Verify user is not able to update more than 40 characters in street_address_1.")
	public void test_025_registration_update_street_address_1_address_with_more_than_max_character() throws Exception {
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress1(streetAddress_41Chars).build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	
	
	@Test(priority = 26)
	@Description("Verify user is able to update 40 characters in street_address_1.")
	public void test_026_registration_update_street_address_1_address_with_40_characters() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress1(streetAddress_40Chars).build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	Assert.assertEquals(parsedResponse.getAddress().getStreetAddress1(),streetAddress_40Chars );


	}

	
		
	
	
	@Test(priority = 27)
	@Description("Verify user is not able to update 3 characters in street_address_1.")
	public void test_027_registration_update_street_address_1_address_with_3_characters() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress1("str").build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	Assert.assertEquals(parsedResponse.getAddress().getStreetAddress1(),"str" );


	}

	
	
	
	
	@Test(priority = 28)
	@Description("Verify user is not able to update address when street_address_2 field is empty")
	public void test_028_registration_update_address_with_empty_street_address_2() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress2("").build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	//Assert.assertEquals(parsedResponse.getAddress().getStreetAddress2(),""); update required

	}

	
	

	
	@Test(priority = 29)
	@Description("Verify user is not able to update less than 3 characters in street_address_2.")
	public void test_029_registration_update_street_address_2_address_with_less_than_3_characters() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress2("st").build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	
	
	
	@Test(priority = 30)
	@Description("Verify user is not able to update more than 40 characters in street_address_2.")
	public void test_030_registration_update_street_address_2_address_with_more_than_max_character() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress2(streetAddress_41Chars).build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}

	
	
	
	@Test(priority = 31)
	@Description("Verify user is able to update 40 characters in street_address_2.")
	public void test_031_registration_update_street_address_2_address_with_40_characters() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress2(streetAddress_40Chars).build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	Assert.assertEquals(parsedResponse.getAddress().getStreetAddress2(),streetAddress_40Chars );


	}

	
	
	
	
	@Test(priority = 32)
	@Description("Verify user is able to update 3 characters in street_address_2.")
	public void test_032_registration_update_street_address_2_address_with_3_characters() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).streetAddress2("str").build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	Assert.assertEquals(parsedResponse.getAddress().getStreetAddress2(),"str" );


	}

	
	
	@Test(priority = 33)
	@Description("Verify user is able to update address when city field is empty in payload.")
	public void test_033_registration_update_address_with_empty_city() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).city("").build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	
	}
	
	
	
	
	@Test(priority = 34)
	@Description("Verify user is not able to update less than 2 characters in city name.")
	public void test_034_registration_update_address_with_less_than_2_characters_city_name() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).city("O").build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	
	}
	


	

	@Test(priority = 35)
	@Description("Verify user is not able to update more than 40 characters in city name")
	public void test_035_registration_update_address_with_more_than_40_characters_city_name() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).city(city41Chars).build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	
	}
	


	

	@Test(priority = 36)
	@Description("Verify user is able to update 40 characters in city name.")
	public void test_36_registration_update_address_with_40_characters_city_name() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).city(city40Chars).build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	Assert.assertEquals(parsedResponse.getAddress().getCity(), city40Chars );
	
	
	}
	

///////////////////////////////////////////////
	
	
	
	
	
	@Test(priority = 37)
	@Description("Verify user is able to update address when state field is empty in payload.")
	public void test_037_registration_update_address_with_empty_state() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).state("").build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	
	}
	
	
	
	
	@Test(priority = 38)
	@Description("Verify user is not able to update less than 2 characters in state name.")
	public void test_038_registration_update_address_with_less_than_2_characters_state_name() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).state("O").build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	
	}
	


	

	@Test(priority = 39)
	@Description("Verify user is not able to update more than 2 characters in state name")
	public void test_039_registration_update_address_with_more_than_2_characters_state_name() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).state("ORE").build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	
	}
	


	

	@Test(priority = 40)
	@Description("Verify user is able to update 2 characters in state name.")
	public void test_040_registration_update_2_characters_state_name() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).state("OR").build();
	ApiResponse response = api.updateAddress(user, message);	
	
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	Assert.assertEquals(parsedResponse.getAddress().getState(), "OR" );
	
	
	}
	

	

	
	
	
	
	

	@Test(priority = 41)
	@Description("Verify user is able to update address successfully with all the valid fields")
	public void test_041_registration_update_address_successfully() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();

	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid).addressAlias(updateAddressAlias)
	        .streetAddress1(updateStreetupdateress1).streetAddress2(updateStreetupdateress2).city(updateCity).state(updateState)
	        .state(updateState).country(updateCountry).postalCode(updatePostalCode).build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	AddressResponse parsedResponse = (AddressResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated address with UUID "+addressUuid+".");
	Assert.assertNotNull(parsedResponse.getAddress().getAddedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getModifiedEpoch());
	Assert.assertNotNull(parsedResponse.getAddress().getUuid());
	Assert.assertEquals(parsedResponse.getAddress().getNickname(), updateAddressAlias );
	Assert.assertEquals(parsedResponse.getAddress().getStreetAddress1(),updateStreetupdateress1 );
	Assert.assertEquals(parsedResponse.getAddress().getStreetAddress2(),updateStreetupdateress2 );
	Assert.assertEquals(parsedResponse.getAddress().getCity(), updateCity );
	Assert.assertEquals(parsedResponse.getAddress().getState(), updateState );
	Assert.assertEquals(parsedResponse.getAddress().getCountry(), updateCountry );
	Assert.assertEquals(parsedResponse.getAddress().getPostalCode(), updatePostalCode );
	}

	


	@Test(priority = 42)
	@Description("Verify user is not able to update address with another uuid")
	public void test_042_registration_update_address_with_another_uuid() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	emailUuid=entityResponse.getEmails().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(emailUuid).addressAlias(updateAddressAlias)
	        .streetAddress1(updateStreetupdateress1).streetAddress2(updateStreetupdateress2).city(updateCity).state(updateState)
	        .state(updateState).country(updateCountry).postalCode(updatePostalCode).build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), 404);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "No address with UUID "+emailUuid+" exists for user "+Handle_26+".");
	}


	@Test(priority = 43)
	@Description("Verify user is not able to update address with invalid uuid")
	public void test_043_registration_update_address_with_invalid_uuid() throws Exception {
	
	
	//Get Entity
	ApiResponse apiResponse = api.getEntity(handle26, reader.getCellData(sheetName, privatekeys, 6));
	GetEntityResponse entityResponse = (GetEntityResponse) apiResponse.getData();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();
	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	AddressMessage message = AddressMessage.builder().uuid(addressUuid+"1").addressAlias(updateAddressAlias)
	        .streetAddress1(updateStreetupdateress1).streetAddress2(updateStreetupdateress2).city(updateCity).state(updateState)
	        .state(updateState).country(updateCountry).postalCode(updatePostalCode).build();
	ApiResponse response = api.updateAddress(user, message);	

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}	
	
	
	
	
	
	@Test(priority = 44)
	@Description("Verify user is able to update entity with valid data.")
	public void test_044_registration_update_with_all_valid_Entity() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().firstName(newFirstName) .lastName(newLastName).entityName(newEntity).build();
	ApiResponse response = api.updateEntity(user, message);
	
	// Success response
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
	Assert.assertNotNull(parsedResponse.getEntity().getEntityName());
	Assert.assertNotNull(parsedResponse.getEntity().getBirthdate());
	Assert.assertNotNull(parsedResponse.getEntity().getFirstName());
	Assert.assertNotNull(parsedResponse.getEntity().getLastName());
	}



	

	@Test(priority = 45)
	@Description("Verify user is not able to update empty first name.")
	public void test_045_registration_update_empty_first_name() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().firstName("").build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	

	}
	
	

	@Test(priority = 46)
	@Description("Verify user is able to update entity first name with minimum 1 character.")
	public void test_046_registration_update_first_name_with_min_character() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().firstName("a").build();
	ApiResponse response = api.updateEntity(user, message);
	
	// Success response
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
	Assert.assertEquals(parsedResponse.getEntity().getFirstName(), "a");

	}
	

	
	@Test(priority = 47)
	@Description("Verify user is able to update entity first name with maximum 40 characters.")
	public void test_047_registration_update_first_name_with_max_character() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().firstName(accountNameWith40Chars).build();
	ApiResponse response = api.updateEntity(user, message);
	
	// Success response
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
	Assert.assertEquals(parsedResponse.getEntity().getFirstName(), accountNameWith40Chars);


	}
	
	
	
	@Test(priority = 48)
	@Description("Verify user is not able to update entity first name with more than 40 characters.")
	public void test_048_registration_update_first_name_with_more_than_40_character() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().firstName(accountNameWith41Chars).build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

	}
	
	




	@Test(priority = 49)
	@Description("Verify user is not able to update empty last name.")
	public void test_049_registration_update_empty_last_name() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().lastName("").build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	
	}
	
	
	
	@Test(priority = 50)
	@Description("Verify user is able to update entity last name with minimum 1 character.")
	public void test_050_registration_update_last_name_with_min_character() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().lastName("a").build();
	ApiResponse response = api.updateEntity(user, message);
	
	// Success response
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
	Assert.assertEquals(parsedResponse.getEntity().getLastName(), "a");
	
	}
	
	
	
	@Test(priority = 51)
	@Description("Verify user is able to update entity last name with maximum 40 characters.")
	public void test_051_registration_update_last_name_with_max_character() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().lastName(accountNameWith40Chars).build();
	ApiResponse response = api.updateEntity(user, message);
	
	// Success response
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
	Assert.assertEquals(parsedResponse.getEntity().getLastName(), accountNameWith40Chars);
	
	
	}
	
	
	
	@Test(priority = 52)
	@Description("Verify user is not able to update entity last name with more than 40 characters.")
	public void test_052_registration_update_last_name_with_more_than_40_character() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().lastName(accountNameWith41Chars).build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

}


	
	@Test(priority = 53)
	@Description("Verify user is able to update empty entity name.")
	public void test_053_registration_update_empty_entity_name() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().entityName("").build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
	//Assert.assertEquals(parsedResponse.getEntity().getEntityName(), ""); update required

}

	
	@Test(priority = 54)
	@Description("Verify user is able to update entity name with 3 characters.")
	public void test_054_registration_update_entity_name_with_min_characters() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().entityName("owe").build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
	Assert.assertEquals(parsedResponse.getEntity().getEntityName(), "owe");

	}


	
	
	@Test(priority = 55)
	@Description("Verify user is able to update entity name with 40 characters.")
	public void test_055_registration_update_entity_name_with_max_characters() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().entityName(textWith_40Chars).build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), successStatusCode);
	Assert.assertEquals(response.getSuccess(), successTrue);
	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
	Assert.assertEquals(parsedResponse.getEntity().getEntityName(), textWith_40Chars);
}


	
	@Test(priority = 56)
	@Description("Verify user is not able to update with 3 characters in entity name.")
	public void test_056_registration_update_entity_name_less_than_3_characters() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().entityName("en").build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
}



	@Test(priority = 57)
	@Description("Verify user is not able to update 41 characters in entity name.")
	public void test_057_registration_update_entity_name_more_than_40_characters() throws Exception {	
	UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
	IndividualEntityMessage message = IndividualEntityMessage.builder().entityName(textWith_41Chars).build();
	ApiResponse response = api.updateEntity(user, message);
	
	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
}


	
   @Test(priority = 58)
    @Description("Verify user is not able to update birthday below 18 years in request.")
    public void test_058_registration_update_birth_date_below_18_years() throws Exception {
        UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
                .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
        IndividualEntityMessage message = IndividualEntityMessage.builder().birthdate(LocalDate.of(year, month, today)).build();
        
    	ApiResponse response = api.updateEntity(user, message);
    	Assert.assertEquals(response.getStatusCode(), 400);
    	Assert.assertEquals(response.getSuccess(), successFalse);
    	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
    	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
    }
    
    

	
    @Test(priority = 59)
    @Description("Verify user is not able to update birthdate in future.")
    public void test_059_registration_update_birth_date_with_future_date() throws Exception {
		int TomowworDate=0;
		if (today >=30) {
		 TomowworDate=1;	
		}else {
		 TomowworDate=tomorrow;
		}
		
        UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
                .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
        IndividualEntityMessage message = IndividualEntityMessage.builder().birthdate(LocalDate.of(year, month, TomowworDate)).build();
    	ApiResponse response = api.updateEntity(user, message);
    	Assert.assertEquals(response.getStatusCode(), 400);
    	Assert.assertEquals(response.getSuccess(), successFalse);
    	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
    	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
    }
    
    
    

	
   @Test(priority = 60)
    @Description("Verify user is  able to update birthdate with less than 18 years")
    public void test_060_registration_update_birth_date_as_18_years() throws Exception {
		int YesterdayDate=0;
		int lastMonth=0;
		if (today ==1) {
		YesterdayDate=28;	
		lastMonth=previousMonth;
		}else {
		YesterdayDate=yesterday;
		lastMonth=month;}
		
		
        UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
                .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
        IndividualEntityMessage message = IndividualEntityMessage.builder().birthdate(LocalDate.of(yearbefore18, lastMonth, YesterdayDate)).build();
    	ApiResponse response = api.updateEntity(user, message);
    	Assert.assertEquals(response.getStatusCode(), successStatusCode);
    	Assert.assertEquals(response.getSuccess(), successTrue);
    	IndividualEntityResponse parsedResponse = (IndividualEntityResponse) response.getData();
    	Assert.assertEquals(parsedResponse.getStatus(), "SUCCESS");
    	Assert.assertEquals(parsedResponse.getMessage(), "Successfully updated entity with handle \""+Handle_26+"\".");
    	Assert.assertEquals(parsedResponse.getUserHandle(), Handle_26 );
    	Assert.assertEquals(parsedResponse.getEntityType(), "individual" );
    	Assert.assertNotNull(parsedResponse.getEntity().getCreatedEpoch());
    	//Assert.assertEquals(parsedResponse.getEntity().getBirthdate(), yearbefore18+"-"+lastMonth+"-"+YesterdayDate);

    }
    
    
   @Test(priority = 61)
    @Description("Verify user is  able to update birthdate with more than 18 years")
    public void test_061_registration_update_birth_date_more_than_18_years() throws Exception {
        UserHandleMessage user = UserHandleMessage.builder().userHandle(handle26)
                .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).build();
        IndividualEntityMessage message = IndividualEntityMessage.builder().birthdate(LocalDate.of(yearbefore18, month, today)).build();
    	ApiResponse response = api.updateEntity(user, message);

    	Assert.assertEquals(response.getStatusCode(), 400);
    	Assert.assertEquals(response.getSuccess(), successFalse);
    	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
    	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);

    }



}











