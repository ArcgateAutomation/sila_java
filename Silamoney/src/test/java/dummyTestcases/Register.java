package dummyTestcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.api.SilaApi;
import com.silamoney.client.domain.AddressMessage;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;
import io.qameta.allure.Description;
import com.silamoney.client.domain.User;
import com.silamoney.client.domain.UserHandleMessage;

import java.util.ArrayList;

import org.joda.time.LocalDate;



public class Register extends Base_class {

	
	// Register Individual User
		@Test(priority = 1)
		@Description("Check registration with all valid data")
		public void Test_01_Test_01_register_with_all_valid_data() throws Exception {
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		
		//User2
		User user2 = new User(handle22,  firstName,  lastName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser22CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 2, Utility.getuser22PrivateKey());
		ApiResponse response2 = api.register(user2);
		Thread.sleep(3000);
	
		//User3
		User user3 = new User(handle23,  firstName,  lastName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser23CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 3, Utility.getuser23PrivateKey());
		ApiResponse response3 = api.register(user3);
		Thread.sleep(3000);

	
		//User4
		User user4 = new User(handle24,  firstName,  lastName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser24CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 4, Utility.getuser24PrivateKey());
		ApiResponse response4 = api.register(user4);
		Thread.sleep(3000);
		

		
		Assert.assertEquals(response2.getStatusCode(), successStatusCode);
		Assert.assertEquals(response2.getSuccess(), successTrue);
		Assert.assertNotNull(((BaseResponse) response2.getData()).getReference());
		Assert.assertEquals(((BaseResponse) response2.getData()).getStatus(), statusTrue);
		Assert.assertEquals(((BaseResponse) response2.getData()).getMessage(), registerSuccessMsg_2);

		}
	


		

}
