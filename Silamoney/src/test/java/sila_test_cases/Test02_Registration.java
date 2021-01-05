package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;
import io.qameta.allure.Description;
import com.silamoney.client.domain.User;
import org.joda.time.LocalDate;



public class Test02_Registration extends Base_class {

	
	// Register Individual User
		@Test(priority = 1)
		@Description("Check registration with all valid data")
		public void Test_01_Test_01_register_with_all_valid_data() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		//
		User user2 = new User(handle22,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser22CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 2, Utility.getuser22PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 2, Utility.getuser22CryptoAddress());
		api.register(user2); //Iser to link control and Administartor role
		Thread.sleep(3000);
		
		
		User user3 = new User(handle23,  firstName,  lastName, entityName, streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser23CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 3, Utility.getuser23PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 3, Utility.getuser23CryptoAddress());
		api.register(user3); //Use for beneficial_owner role
		
		//User4
		User user4 = new User(handle24,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser24CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 4, Utility.getuser24PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 4, Utility.getuser24CryptoAddress());
		api.register(user4);
		Thread.sleep(3000);
		
		//User5
		User user5 = new User(handle25,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser25CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 5, Utility.getuser25PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 5, Utility.getuser25CryptoAddress());
		 api.register(user5);
		Thread.sleep(3000);
		
		User user6 = new User(handle26,  firstName,  lastName, entityName, streetAddress1, streetAddress2, city, state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser26CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 6, Utility.getuser26PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 6, Utility.getuser26CryptoAddress());
		api.register(user6);
		Thread.sleep(3000);
		
		//User5   : Use for Updated registration data
		User user7 = new User(handle27,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser27CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 7, Utility.getuser27PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 7, Utility.getuser27CryptoAddress());
		ApiResponse response =api.register(user7); //Add, update,delete registration related testcases

		Thread.sleep(3000);
		
		User user33 = new User(handle33,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser33CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 33, Utility.getuser33PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 33, Utility.getuser33CryptoAddress());
		api.register(user33);
		Thread.sleep(3000);
		

		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getMessage(), Handle_27+" was successfully registered.");

		}
	


		@Test(priority=2)
		@Description("Check registration with already taken user_handle")
		public void Test_02_register_with_already_taken_user_handle()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31); 
		User user = new User(handleAlreadyTaken,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUserCryptoAddress(), birthdate.toDate(), country);
		
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		handleTakenMsg);
		
		}
		
		
		@Test(priority=3)
		@Description("Check registration with empty First_Name field")
		public void Test_03_register_with_empty_first_name_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("a"+handle1,  "",  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUserCryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
		}
		
		
		
		@Test(priority=4)
		@Description("Check registration with empty Last_Name field")
		public void Test_04_register_with_empty_last_name_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("b"+handle1,  firstName,  "", entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUserCryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
		}
		
		
			
		@Test(priority=5)
		@Description("Check registration with empty Street_Address_1 field")
		public void Test_05_register_with_empty_street_address_1_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("c"+handle1,  firstName,  lastName, entityName,  "", streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUserCryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "c"+Handle_1+ " was successfully registered."); 
		
		}
		
		
		
		@Test(priority=6)
		@Description("Check registration without Street_Address_1 entity")
		public void Test_06_register_without_street_address_1_entity()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("d"+handle1,  firstName,  lastName, entityName,  null, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser2CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg);
		
		}
		
		
		
		
		
		
		@Test(priority=7)
		@Description("Check registration with less than 3 characters Street_Address_1 field")
		public void Test_07_register_With_less_than_3_characters_street_address_1_field() throws Exception{ 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("e"+handle1,  firstName,  lastName, entityName,  "ad", streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser2CryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response = api2.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg);
		
		}
		
		
		
		@Test(priority=8)
		@Description("Register With more than 40 characters street address1 field")
		public void Test_08_register_With_more_than_40_characters_street_address_1_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("f"+handle1,  firstName,  lastName, entityName,  streetAddress_41Chars, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser2CryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response=api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg);
		
		}
		
		

		
		@Test(priority=9)
		@Description("Check registration with 3 characters Street_Address_1 field")
		public void Test_09_register_With_minumum_allowed_3_characters_street_address_1_field() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31); 
		User user = new User("g"+handle1,  firstName,  lastName, entityName,  "ave", streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser2CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "g"+registerSuccessMsg_1);
		
		}
		
		
		
		
		//registration with 40 characters Street_Address_1 field
		
		@Test(priority=10)
		@Description("Check registration with 40 characters Street_Address_1 field")
		public void Test_10_register_with_maximum_allowed_40_characters_street_address_1_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("h"+handle1,  firstName,  lastName, entityName,  streetAddress_40Chars, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser3CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "h"+registerSuccessMsg_1);
		
		}
		
		
		
		
		
		
		//Street Address_2
		
		@Test(priority=11) 
		@Description("Check registration with empty Street_Address_2 field")
		public void Test_11_register_with_empty_street_address_2_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("ia"+handle1,  firstName,  lastName, entityName,  streetAddress1, "", city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser4CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "ia"+registerSuccessMsg_1);
		
		
		}
		
		
		
		//registration with less than 3 characters Street_Address_2 field
		
		@Test(priority=12)
		@Description("Check registration with less than 3 characters Street_Address_2 field")
		public void Test_12_register_with_less_than_3_characters_street_address_2_field() throws Exception{ 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("j"+handle1,  firstName,  lastName, entityName,  streetAddress1, "te", city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser5CryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg);
		
		}
		
		
		
		//registration with more than 40 characters Street_Address_2 field
		
		@Test(priority=13)
		@Description("Check registration with more than 40 characters Street_Address_2 field")
		public void Test_13_register_With_more_than_40_characters_street_address_2() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("k"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress_41Chars, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser5CryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response=api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg);
		
		}
		
		
		
		@Test(priority=14)
		@Description("Check registration with 3 characters Street_Address_2 field")
		public void Test_14_register_With_Minumum_3_Chars_Street_Address_2() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("l"+handle1,  firstName,  lastName, entityName,  streetAddress1, "ave", city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser5CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "l"+registerSuccessMsg_1);
		
		}
		
		
		
		
		//registration with 40 characters Street_Address_1 field
		
		@Test(priority=15)
		@Description("Check registration with 40 characters Street_Address_2 field")
		public void Test_15_register_with_maximum_allowed_40_chars_street_address_2() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("ma"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress_40Chars, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser6CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "ma"+registerSuccessMsg_1);
		
		}
		
		
		
		//City
		
		@Test(priority=16)
		@Description("Check registration with empty City field")
		public void Test_16_register_with_empty_city_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("n"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, "",  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser7CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		System.out.println("getStatusCode: "+response.getStatusCode());
		System.out.println("getStatusCode: "+response.getSuccess());
		System.out.println("getStatusCode: "+((BaseResponse)response.getData()).getStatus());
		System.out.println("getStatusCode: "+((BaseResponse)response.getData()).getReference());
		System.out.println("getStatusCode: "+((BaseResponse)response.getData()).getMessage());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "n"+Handle_1+ " was successfully registered."); 
		}
		
		
		@Test(priority=17)
		@Description("Check registration with less than 2 characters in city field")
		public void Test_17_register_with_less_than_2_characters_city_name_field() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("o"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, "s",  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser8CryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); }
		
		
		
		
		@Test(priority=18)
		@Description("Check registration with more than 40 city characters")
		public void Test_18_register_with_more_than_40_characters_city_name_field() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("p"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city41Chars,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser8CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); }
		
		
		
		
		
		@Test(priority=19)
		@Description("Check registration with allowed 40 City character")
		public void Test_19_register_with_allowed_40_characters_city_name_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("q"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city40Chars,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getUser8CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "q"+registerSuccessMsg_1); }
			
		
		
		@Test(priority=20)
		@Description("Check registration with empty State field")
		public void Test_20_register_with_empty_state_field() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("q1"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  "", 
		postalCode,  phone,  email, identityNumber, Utility.getUser9CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "q1"+Handle_1+ " was successfully registered."); 
		}
		
		
		
		
		@Test(priority=21)
		@Description("Check registration with more than 2 characters state name field")
		public void Test_21_register_with_more_than_2_characters_state_name_field()throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("s"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  "ORG", 
		postalCode,  phone,  email, identityNumber, Utility.getUser10CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); }
		
		
		
		@Test(priority=22)
		@Description("Check registration with less than 2 characters state name field")
		public void Test_22_register_with_less_than_2_characters_state_name_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("t"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  "O", 
		postalCode,  phone,  email, identityNumber, Utility.getUser10CryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); }
		
		
		@Test(priority=23)
		@Description("Check registration with empty identy_number field")
		public void Test_23_register_with_empty_identy_number_field()throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("u"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, "", Utility.getUser10CryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); 
		}
		
		
		@Test(priority=24)
		@Description("Check registration with empty Phone field")
		public void Test_24_register_with_empty_phone_field() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31); 
		User user = new User("v"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  "",  email, identityNumber, Utility.getUser10CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "v"+registerSuccessMsg_1);
		
		}
		

		
		
		@Test(priority=25)
		@Description("Check registration with less than 10 characters in the Phone field")
		public void Test_25_register_with_less_than_10_chars_phone_field()throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("w"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  "456452546",  email, identityNumber, Utility.getuser11CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}

		@Test(priority=26)
		@Description("Check registration with empty Email field")
		public void Test_26_register_with_empty_email_field() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("z"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  "", identityNumber, Utility.getuser11CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "z"+registerSuccessMsg_1);
		
		}
		
		
		
		
		@Test(priority=27)
		@Description("Check registration with invalid Email field")
		public void Test_27_register_with_invalid_email_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("aa"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  invalid_Email, identityNumber, Utility.getuser12CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg);
		
		}
		
		
		
		
		
		
		@Test(priority=28)
		@Description("Check registration with empty postal_code field")
		public void Test_28_register_with_empty_postal_code_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("ab"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		"",  phone,  email, identityNumber, Utility.getuser12CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "ab"+Handle_1+ " was successfully registered."); 
		
		}
		
		
	
		
		@Test(priority=29)
		@Description("Check registration with minumium 1 length postal_code field")
		public void Test_29_register_with_minimum_1_length_postal_code_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("ac"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		"55642",  phone,  email, identityNumber, Utility.getuser13CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		System.out.println("Test_29_register_with_minimum_1_length_postal_code_field: "+((BaseResponse)
		response.getData()).getMessage());
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "ac"+registerSuccessMsg_1);
		
		}
		
		
		
		
		@Test(priority=30)
		@Description("Check registration with empty postal_code field")
		public void Test_30_register_with_mmaximum_10_length_postal_code_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31); 
		User user = new User("ad"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		"9722543065",  phone,  email, identityNumber, Utility.getuser14CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusTrue); Assert.assertEquals(((BaseResponse)
		response.getData()).getMessage(), "ad"+registerSuccessMsg_1);
		
		}
		
		
		
		
		
		
		@Test(priority=31)
		@Description("Check registration with 11 characters length postal_code field")
		public void Test_31_register_with_more_than_10_length_postal_code_field() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("ase"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		"97225-30625",  phone,  email, identityNumber, Utility.getuser15CryptoAddress(), birthdate.toDate(), country);		
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), validationErrorMsg);
		
		}
		
		
		
		@Test(priority=32)
		@Description("Check registration with empty Crypto Address field")
		public void Test_32_register_with_empty_crypto_address_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("af"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, "", birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); }
		
		
		
		
		
		@Test(priority=33)
		@Description("Check registration with more than 42 characters Crypto Address field")
		public void Test_33_register_with_43_characters_crypto_address_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("aj"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuserCryptoAddress43Char(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); }
		
		
		
		@Test(priority=34)
		@Description("Check registration with less than 42 characters Crypto Address field")
		public void Test_34_register_with_41_characters_crypto_address_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("ak"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuserCryptoAddress41Char(), birthdate.toDate(), country);		
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); 
		
		}
				
		
		@Test(priority=35)
		@Description("Check registration with future birthdate_date field")
		public void Test_35_register_with_Future_birth_date_field() throws Exception { 
		int TomowworDate=0;
		if (today >=30) {
		 TomowworDate=1;	
		}else {
		 TomowworDate=tomorrow;
		}

		LocalDate birthdate = new LocalDate(year, month, TomowworDate);
		User user = new User("ag"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser15CryptoAddress(), birthdate.toDate(), country);	
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); 
		
		}
		
		

		
		@Test(priority=36)
		@Description("Check registration with less than 18 years birthdate_date  field")
		public void Test_36_register_with_less_than_18_Years_birth_date_field() throws Exception { 
		LocalDate birthdate = new LocalDate(yearbefore18, month, today); 
		User user = new User("ag"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser15CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); 
		
		}
		
		
		
		
	
		@Test(priority=37)
		@Description("Check registration with more than 18 years birthdate_date field")
		public void Test_37_register_with_after_18_Years_birth_date_field() throws Exception { 
		int yesterdayDate=0;
		int currentMonth=0;
		if (today ==1) {
			yesterdayDate=30;
			if (month==1) {
				currentMonth=12;
			} else {
				currentMonth=month-1;
			}
		} else {yesterdayDate=today-1;
		currentMonth=month;
		}


		LocalDate birthdate = new LocalDate(yearbefore18, currentMonth, yesterdayDate); 
		User user = new User("am"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser15CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "am"+registerSuccessMsg_1);
		
		}
		

		
		@Test(priority=38)
		@Description("Check registration with less than 18 years birthdate_date field")
		public void Test_38_register_with_less_than_18_Years_birth_date_field() throws Exception { 
		int TomorrowDate=0;
		int nextMonth=0;
		if (today >=30) {
			TomorrowDate=1;	
			if (month==12) {
				nextMonth=1;
			}else {
				nextMonth=month+1;
			}

		}else {
			TomorrowDate=today+1;
			nextMonth=month;}

		LocalDate birthdate = new LocalDate(yearbefore18, nextMonth, TomorrowDate); 
		User user = new User("an"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser16CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),
		validationErrorMsg); 
		
		}
		
		
		 
		@Test(priority=39)
		@Description("Check registration with empty user_handle field")
		public void Test_39_register_With_Empty_User_Handle_Field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		
		User user = new User("",  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser17CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api4.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg); 
		
		}
		
		
		
		@Test(priority=40)
		@Description("check Response401")
		public void Test_40_Check_Response_401()throws Exception {
		LocalDate birthdate= new LocalDate(2000, 01, 31);
		User user = new User("aah"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, "0x1234567890abcdef1234567890abcdef12345678", birthdate.toDate(), country);
		ApiResponse response = api4.register(user);
		Thread.sleep(3000);
	
		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorMsg1);
		
		}
		
		
		
		
		
		
		@Test(priority=41)
		@Description("Check registration with different private_key")
		public void Test_41_register_with_different_private_key()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("ai"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser17CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api1.register(user);
		Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 401);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),ErrorMsg2);

		}


		
		
		@Test(priority=42)
		@Description("Check registration with empty country field")
		public void Test_42_register_with_empty_country_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("aj"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser17CryptoAddress(), birthdate.toDate(), "");
		ApiResponse response = api.register(user);
		Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "aj"+Handle_1+ " was successfully registered."); 

		}


		@Test(priority=43)
		@Description("Check registration with lower case country field")
		public void Test_43_register_with_lowercase_country_field()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("aj"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser18CryptoAddress(), birthdate.toDate(), "us");
		ApiResponse response = api.register(user);
		Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg); 

		}

		
		@Test(priority=44)
		@Description("Check registration with another country name")
		public void Test_44_register_with_another_country_name()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("aj"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser18CryptoAddress(), birthdate.toDate(), "INDIA");
		ApiResponse response = api.register(user);
		Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg); 

		}

		
		
		@Test(priority=45)
		@Description("Verify user is not able to register when user send less than 2 characters in country name.")
		public void test_045_register_with_less_than_2_characters_country_name()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("aj"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser18CryptoAddress(), birthdate.toDate(), "U");
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg); 

		}


	
		
		@Test(priority=46)
		@Description("Verify user is not able to register when user send more than 2 characters in country name.")
		public void test_046_register_with_more_than_2_characters_country_name()throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("aj"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser18CryptoAddress(), birthdate.toDate(), "USA");
		ApiResponse response = api.register(user);
		Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg); 

		}

		
		
		@Test(priority=47)
		@Description("Check registration with allowed 2 characters City name")
		public void Test_47_register_with_allowed_2characters_city_name_field() throws Exception { 
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User("ba"+handle1,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, "al",  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser18CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response = api.register(user);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue); 
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "ba"+registerSuccessMsg_1); 
		}
		
		
		
		// Register Individual User
		@Test(priority = 48)
		@Description("Check registration with empty entity name")
		public void Test_48_register_with_empty_entity_name() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
			
		//User4
		User user4 = new User("bb"+handle1,  firstName,  lastName, "",   streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser19CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response =api.register(user4);
		Thread.sleep(3000);

		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue); 
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "bb"+registerSuccessMsg_1); 
		}
		
		
		
		// Register Individual User
		@Test(priority = 49)
		@Description("Check registration with 3 characters entity name")
		public void Test_49_register_with_3_characters_entity_name() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
			
		//User4
		User user4 = new User("bc"+handle1, firstName,  lastName, "tes",   streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser20CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response =api.register(user4);
		Thread.sleep(3000);

		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue); 
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "bc"+registerSuccessMsg_1); 
		}
		
		
		
		@Test(priority = 50)
		@Description("Check registration with 40 characters entity name")
		public void Test_50_register_with_40_characters_entity_name() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
			
		//User4
		User user4 = new User("bd"+handle1, firstName,  lastName, textWith40Chars,   streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser21CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response =api.register(user4);
		Thread.sleep(3000);

		
		Assert.assertEquals(response.getStatusCode(), successStatusCode);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue); 
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "bd"+registerSuccessMsg_1); 
		}
		
		
		
		@Test(priority = 51)
		@Description("Check registration with 2 characters entity name")
		public void Test_51_register_with_2_characters_entity_name() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
			
		//User4
		User user4 = new User("be"+handle1,  firstName,  lastName, "ex",   streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser31CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response =api.register(user4);
		Thread.sleep(3000);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),
		statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

		}
		
		
		
		
		@Test(priority = 52)
		@Description("Check registration with 41 characters entity name")
		public void Test_52_register_with_41_characters_entity_name() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
			
		//User4
		User user4 = new User("bf"+handle1,  firstName,  lastName, textWith41Chars,   streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser32CryptoAddress(), birthdate.toDate(), country);
		ApiResponse response =api.register(user4);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "bf"+Handle_1+ " was successfully registered."); 

		}
		

}




