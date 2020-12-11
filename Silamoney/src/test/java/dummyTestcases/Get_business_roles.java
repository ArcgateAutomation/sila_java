package dummyTestcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.api.SilaApi;
import com.silamoney.client.domain.AddressMessage;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.BusinessRole;
import com.silamoney.client.domain.GetBusinessRolesResponse;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.DefaultConfigurations;
import com.silamoney.common_files.Utility;
import io.qameta.allure.Description;
import com.silamoney.client.domain.User;
import com.silamoney.client.domain.UserHandleMessage;

import java.util.ArrayList;

import org.joda.time.LocalDate;



public class Get_business_roles extends Base_class {

	
	// Register Individual User
		@Test(priority = 1)
		@Description("Check registration with all valid data")
		public void Test_01_Test_01_register_with_all_valid_data() throws Exception {

			//ApiResponse businessRolesResponse = api.getBusinessRoles();
			//DefaultConfigurations.setBusinessRoles(((GetBusinessRolesResponse) businessRolesResponse.getData()).getBusinessRoles());
			
			
			ApiResponse response = api.getBusinessRoles();

			// Success Object Response
			System.out.println(response.getStatusCode()); // 200
			((GetBusinessRolesResponse) response.getData()).getBusinessRoles(); // List
			System.out.println(((GetBusinessRolesResponse) response.getData()).getBusinessRoles());
			
			
			
			
			ApiResponse businessRolesResponse = api.getBusinessRoles();
			//DefaultConfigurations.setBusinessRoles(((GetBusinessRolesResponse) businessRolesResponse.getData()).getBusinessRoles());
			
			//BusinessRole businessRole = ((GetBusinessRolesResponse)api.getBusinessRoles().getData()).get(0);
			
			BusinessRole business_role= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);
			System.out.println(business_role);
			
	

		}
	


		

}
