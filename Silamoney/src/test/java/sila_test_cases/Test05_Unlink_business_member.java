package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.BusinessRole;
import com.silamoney.client.domain.GetBusinessRolesResponse;
import com.silamoney.client.domain.LinkBusinessOperationResponse;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

public class Test05_Unlink_business_member extends Base_class {
	
	

	
	
	@Test(priority = 1)
	@Description("Unink business member with empty user_handle")
	public void test_01_Unlink_business_memeber_with_empty_user_handle() throws Exception {	
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
		api.linkBusinessMember(handle25,reader.getCellData(sheetName, privatekeys, 5), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_Administrator, null, "test details", null);
		
		ApiResponse response = api.unlinkBusinessMember("", reader.getCellData(sheetName, privatekeys, 5), 
				businessHandle, reader.getCellData(sheetName, privatekeys, 41), business_role_Administrator);	
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

}
	
	
	
	@Test(priority = 2)
	@Description("Unink business member with empty user_handle")
	public void test_02_Unlink_business_memeber_with_empty_business_handle() throws Exception {		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
		ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), 
				"", reader.getCellData(sheetName, privatekeys, 41), business_role_Administrator);	
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
}
	
	@Test(priority = 3)
	@Description("Unink business member with invalid user_private_key")
	public void test_03_Unlink_business_memeber_with_invalid_user_private_key() throws Exception {	
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
		ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 7), 
				businessHandle, reader.getCellData(sheetName, privatekeys, 41), business_role_Administrator);	
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((LinkBusinessOperationResponse)response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 7)+" is not registered to "+Handle_25+".".toLowerCase());
        Assert.assertEquals(((LinkBusinessOperationResponse) response.getData()).getRole(), null);

}
	
	
	@Test(priority = 4)
	@Description("Unink business member with invalid business_private_key")
	public void test_04_Unlink_business_memeber_with_invalid_business_private_key() throws Exception {	
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), 
				businessHandle, reader.getCellData(sheetName, privatekeys, 7), business_role_Administrator);	
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((LinkBusinessOperationResponse)response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 7)+" is not registered to "+business_Handle+".".toLowerCase());
        Assert.assertEquals(((LinkBusinessOperationResponse) response.getData()).getRole(), null);

}
	
	
	@Test(priority = 5)
	@Description("Unink business member when not linked as controlOfficer")
	public void test_05_Unlink_business_memeber_when_not_linked_as_controlOfficer() throws Exception {	  
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_ControlOfficer= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);
		ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), 
				businessHandle, reader.getCellData(sheetName, privatekeys, 41), business_role_ControlOfficer);	
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((LinkBusinessOperationResponse)response.getData()).getMessage(), "User handle "+Handle_25+" has not been granted the role of Controlling Officer for business corporate.");
        Assert.assertEquals(((LinkBusinessOperationResponse) response.getData()).getRole(), null);

}
	
	
	@Test(priority = 6)
	@Description("Unink business member when linked as ControlOfficer")
	public void test_06_Unlink_business_memeber_when_linked_as_controlOfficer() throws Exception {	
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_ControlOfficer= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);
		api.linkBusinessMember(handle25,reader.getCellData(sheetName, privatekeys, 5), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_ControlOfficer, null, "test details", null);

		ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), 
				businessHandle, reader.getCellData(sheetName, privatekeys, 41), business_role_ControlOfficer);		
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((LinkBusinessOperationResponse)response.getData()).getMessage(), "User \""+Handle_25+"\" has been unlinked as a Controlling Officer for business corporate.");
        Assert.assertEquals(((LinkBusinessOperationResponse) response.getData()).getRole(), "controlling_officer");


	}
	
	@Test(priority = 7)
	@Description("Unink business member when linked as administrator")
	public void test_07_Unlink_business_memeber_when_linked_as_administrator() throws Exception {	
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);

		ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), 
				businessHandle, reader.getCellData(sheetName, privatekeys, 41), business_role_Administrator);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(((LinkBusinessOperationResponse)response.getData()).getMessage(), "User \""+Handle_25+"\" has been unlinked as a Administrator for business corporate.");
        Assert.assertEquals(((LinkBusinessOperationResponse) response.getData()).getRole(), "administrator");

	}
		
		
		
		@Test(priority = 8)
		@Description("Unink business member when not linked as administrator")
		public void test_08_Unlink_business_memeber_when_not_linked_as_administrator() throws Exception {	
			ApiResponse businessRolesResponse = api.getBusinessRoles();
			BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
			
			ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), 
					businessHandle, reader.getCellData(sheetName, privatekeys, 41), business_role_Administrator);	
			
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getSuccess(), successTrue);
			Assert.assertEquals(((LinkBusinessOperationResponse)response.getData()).getMessage(), "User handle "+Handle_25+" has not been granted the role of Administrator for business corporate.");
	        Assert.assertEquals(((LinkBusinessOperationResponse) response.getData()).getRole(), null);
	
	}
	
		
		
		
		@Test(priority = 9)
		@Description("Unink business member when linked as beneficial_owner")
		public void test_09_Unlink_business_memeber_when_linked_as_beneficial_owner() throws Exception {	
			ApiResponse businessRolesResponse = api.getBusinessRoles();
			BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	
			api.linkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), businessHandle,
			reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", (float)0.66);
			
			ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), 
					businessHandle, reader.getCellData(sheetName, privatekeys, 41), business_role_beneficial_owner);	
			
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getSuccess(), successTrue);
			Assert.assertEquals(((LinkBusinessOperationResponse)response.getData()).getMessage(), "User \""+Handle_25+"\" has been unlinked as a Beneficial Owner for business corporate.");
	        Assert.assertEquals(((LinkBusinessOperationResponse) response.getData()).getRole(), "beneficial_owner");
	
	}
		
		
		@Test(priority = 10)
		@Description("Unink business member when not linked as beneficial_owner")
		public void test_10_Unlink_business_memeber_when_not_linked_as_beneficial_owner() throws Exception {	
			ApiResponse businessRolesResponse = api.getBusinessRoles();
			BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	
			ApiResponse response = api.unlinkBusinessMember(handle25, reader.getCellData(sheetName, privatekeys, 5), 
					businessHandle, reader.getCellData(sheetName, privatekeys, 41), business_role_beneficial_owner);	
			
			Assert.assertEquals(response.getStatusCode(), 200);
			Assert.assertEquals(response.getSuccess(), successTrue);
			Assert.assertEquals(((LinkBusinessOperationResponse)response.getData()).getMessage(), "User handle "+Handle_25+" has not been granted the role of Beneficial Owner for business corporate.");
	        Assert.assertEquals(((LinkBusinessOperationResponse) response.getData()).getRole(), null);
	
	}
		
		



	

	
	
		
	}
