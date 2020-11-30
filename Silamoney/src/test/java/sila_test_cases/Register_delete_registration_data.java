package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.DeleteRegistrationMessage;
import com.silamoney.client.domain.GetEntityResponse;
import com.silamoney.client.domain.RegistrationDataEnum;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

public class Register_delete_registration_data extends Base_class {
	
	public String identityUuid="";
	public String addressUuid="";
	public String emailUuid="";
	public String phoneUuid="";
	
	//LocalDate birthdate = new LocalDate(2000, 01, 31);
	String newBirthdate = "1994, 1, 8";
	

	@Test(priority = 1)
	@Description("Verify user is not able to delete email when user handle field is empty.")
	public void test_001_registration_delete_email_with_empty_user_handle() throws Exception {	
	
	//Get Entity
	ApiResponse responseEntiy = api.getEntity(handle27, reader.getCellData(sheetName, privatekeys, 7));
	GetEntityResponse entityResponse = (GetEntityResponse) responseEntiy.getData();
	identityUuid=entityResponse.getIdentities().get(0).getUuid();
	addressUuid=entityResponse.getAddresses().get(0).getUuid();	
	emailUuid=entityResponse.getEmails().get(0).getUuid();	
	phoneUuid=entityResponse.getPhones().get(0).getUuid();	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle("").userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(emailUuid).build();
	ApiResponse response = api.deleteRegistrationData(RegistrationDataEnum.EMAIL, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	

	}

	@Test(priority = 2)
	@Description("Verify user is not able to delete email when uuid field is empty.")
	public void test_002_registration_delete_email_when_UUID_is_empty() throws Exception {	

	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid("").build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.EMAIL, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	

	}
	
	@Test(priority = 3)
	@Description("Verify user is not able to delete email for non registered user handle.")
	public void test_003_registration_delete_email_with_not_registered_handle() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handleNotRegistered).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(emailUuid).build();
	ApiResponse response = api.deleteRegistrationData(RegistrationDataEnum.EMAIL, message);

	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), HandleNotRegisteredErrorMgs);
	}

	@Test(priority = 4)
	@Description("Verify user is not able to delete email when private key is invalid.")
	public void test_004_registration_delete_email_with_invalid_private_key() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).uuid(emailUuid).build();
	ApiResponse response = api.deleteRegistrationData(RegistrationDataEnum.EMAIL, message);

	Assert.assertEquals(response.getStatusCode(), 403);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 6)+" is not registered to "+Handle_27+".".toLowerCase());
	}

	
	@Test(priority = 5)
	@Description("Verify user is not able to delete email with invalid uuid")
	public void test_005_registration_delete_email_with_invalid_uuid() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(addressUuid).build();
	ApiResponse response = api.deleteRegistrationData(RegistrationDataEnum.EMAIL, message);

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "No email with UUID "+addressUuid+" exists for user "+Handle_27+".");
	}

	@Test(priority = 6)
	@Description("Verify user is able to delete email successfully.")
	public void test_006_registration_delete_email_successfully() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(emailUuid).build();
	ApiResponse response = api.deleteRegistrationData(RegistrationDataEnum.EMAIL, message);

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "Successfully deleted email with UUID "+emailUuid+".");
	}
	

	
		
	@Test(priority = 7)
	@Description("Verify user is not able to delete phone number when uuid field is empty.")
	public void test_007_registration_delete_phone_number_when_UUID_is_empty() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid("").build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.PHONE, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	}
	

	@Test(priority = 8)
	@Description("Verify user is not able to delete phone number with invalid uuid")
	public void test_008_registration_delete_phone_number_with_invalid_uuid() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(emailUuid).build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.PHONE, message);

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "No phone with UUID "+emailUuid+" exists for user "+Handle_27+".");
	}
	

	@Test(priority = 9)
	@Description("Verify user is able to delete phone number successfully.")
	public void test_009_registration_delete_phone_number_successfully() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(phoneUuid).build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.PHONE, message);

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "Successfully deleted phone with UUID "+phoneUuid+".");
	
	}
	

	@Test(priority = 10)
	@Description("Verify user is not able to delete address with invalid uuid")
	public void test_010_registration_delete_address_with_invalid_uuid() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid("").build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	}
	
	

	@Test(priority = 11)
	@Description("Verify user is not able to delete address when uuid is empty.")
	public void test_011_registration_delete_address_when_UUID_is_empty() throws Exception {	
	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(emailUuid).build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "No address with UUID "+emailUuid+" exists for user "+Handle_27+".");
	
	}
	
	
	

	@Test(priority = 12)
	@Description("Verify user is able to delete address successfully.")
	public void test_012_registration_delete_address_successfully() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(addressUuid).build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.ADDRESS, message);

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "Successfully deleted address with UUID "+addressUuid+".");
	}
	
		


	@Test(priority = 13)
	@Description("Verify user is not able to delete identy with empty uuid")
	public void test_011_registration_delete_identy_with_empty_uuid() throws Exception {			
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid("").build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.IDENTITY, message);

	Assert.assertEquals(response.getStatusCode(), 400);
	Assert.assertEquals(response.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), validationErrorMsg);
	
	}
	
	

	@Test(priority = 14)
	@Description("Verify user is not able to delete address when uuid is invalid.")
	public void test_014_registration_delete_address_when_UUID_is_invalid() throws Exception {
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(emailUuid).build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.IDENTITY, message);

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "No identity with UUID "+emailUuid+" exists for user "+Handle_27+".");
	
	}
	
	
	

	@Test(priority = 15)
	@Description("Verify user is able to delete identity successfully.")
	public void test_015_registration_delete_identity_successfully() throws Exception {	
	//Delete data
	DeleteRegistrationMessage message = DeleteRegistrationMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).uuid(identityUuid).build();
	ApiResponse response  = api.deleteRegistrationData(RegistrationDataEnum.IDENTITY, message);

	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), "Successfully deleted identity with UUID "+identityUuid+".");
	
	}
	
		
	





}











