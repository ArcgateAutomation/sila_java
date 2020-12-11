package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.BusinessRole;
import com.silamoney.client.domain.GetBusinessRolesResponse;
import com.silamoney.client.domain.LinkBusinessMemberResponse;
import com.silamoney.common_files.Base_class;

import io.qameta.allure.Description;

public class Link_business_member extends Base_class {
	@Test(priority = 1)
	@Description("Link business member as administrator")
	public void test_01_Link_business_memeber_as_administrator() throws Exception {	
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);

        ApiResponse response = api.linkBusinessMember(handle22,
        		reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),
        		business_role_Administrator, null, "test details", null);
        
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getSuccess(), successTrue);
        Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "administrator");
        Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "User \"peter parker\" has been made a Administrator for business "+businessEntityName+".");    

}
	
	@Test(priority = 2)
	@Description("Link business member as administrator when already linked")
	public void test_02_Link_business_memeber_When_alredy_linked_as_administrator() throws Exception {
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);

        ApiResponse response = api.linkBusinessMember(handle22,
        		reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),
        		business_role_Administrator, null, "test details", null);
        
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getSuccess(), successTrue);
        Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "administrator");
        Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "Business role \"Administrator\" has already been assigned to member "+Handle_22+" for business "+businessEntityName+".");   

}

	
	
	
	@Test(priority = 3)
	@Description("Link business member as ControlOfficer")
	public void test_03_Link_business_memeber_as_ControlOfficer() throws Exception {
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_ControlOfficer= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);

        ApiResponse response = api.linkBusinessMember(handle23,
        		reader.getCellData(sheetName, privatekeys, 3), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),
        		business_role_ControlOfficer, null, "test details", null); 
        
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getSuccess(), successTrue);
        Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "controlling_officer");
        Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "User \"peter parker\" has been made a Controlling Officer for business "+businessEntityName+".");   

}
	
	@Test(priority = 4)
	@Description("Link business member as ControlOfficer when already linked")
	public void test_04_Link_business_memeber_When_alredy_linked_as_ControlOfficer() throws Exception {
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_ControlOfficer= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);

        ApiResponse response = api.linkBusinessMember(handle23,
        		reader.getCellData(sheetName, privatekeys, 3), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),
        		business_role_ControlOfficer, null, "test details", null);
	
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getSuccess(), successTrue);
        Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "controlling_officer");
        Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "Business role \"Controlling Officer\" has already been assigned to member "+Handle_23+" for business "+businessEntityName+".");   

}
	
	

	
	@Test(priority = 5)
	@Description("Link business member as beneficial_owner")
	public void test_05_Link_business_memeber_as_beneficial_owner() throws Exception {
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle24,reader.getCellData(sheetName, privatekeys, 4), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", (float)0.66);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		//Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "beneficial_owner");
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "User \"peter parker\" has been made a Beneficial Owner for business "+businessEntityName+".");   


}
	
	
	@Test(priority = 6)
	@Description("Link business member when already linked as beneficial_owner")
	public void test_06_Link_business_memeber_if_already_linked_as_beneficial_owner() throws Exception {
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle24,reader.getCellData(sheetName, privatekeys, 4), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", (float)0.66);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		//Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusTrue);
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "beneficial_owner");
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "Business role \"Beneficial Owner\" has already been assigned to member "+Handle_24+" for business "+businessEntityName+".");   


}
	
	
	@Test(priority = 7)
	@Description("Link business member with empty user_handle")
	public void test_07_Link_business_memeber_witn_empty_user_handle() throws Exception {
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
        ApiResponse response = api.linkBusinessMember("" ,reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_Administrator, null, "test details", null);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

}
	
	
	
	@Test(priority = 8)
	@Description("Link business member with empty business_handle")
	public void test_08_Link_business_memeber_witn_empty_business_handle() throws Exception {
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
        ApiResponse response = api.linkBusinessMember(handle22 ,reader.getCellData(sheetName, privatekeys, 2), "",
        		reader.getCellData(sheetName, privatekeys, 41),business_role_Administrator, null, "test details", null);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

}
	
	@Test(priority = 9)
	@Description("Link business member with unregistered user_handle")
	public void test_09_Link_business_memeber_witn_unregistered_user_handle() throws Exception {
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
        ApiResponse response = api.linkBusinessMember(handleNotRegistered,reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_Administrator, null, "test details", null);
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		//Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		//Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
}
	
	
	@Test(priority = 10)
	@Description("Link business member with incorrect user's crypto_addressr")
	public void test_10_Link_business_memeber_with_incorrect_user_crypto_address() throws Exception {		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 7), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_Administrator, null, "test details", null);
		
        
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		//Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		//Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		


}
	

	
	@Test(priority = 11)
	@Description("Link business member with witth unregistered business_handle")
	public void test_11_Link_business_memeber_with_unregistered_business_handle() throws Exception {
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 2), handleNotRegistered,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_Administrator, null, "test details", null);
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		//Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		//Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);


}
	
	@Test(priority = 12)
	@Description("Link business member with incorrect business user's crypto_addressr")
	public void test_12_Link_business_memeber_with_incorrect_business_crypto_address() throws Exception {
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_Administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
		
        ApiResponse response = api.linkBusinessMember(handle22, reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 7), business_role_Administrator, null, "test details", null);
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		//Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		//Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		


}
	
	

	
	
	@Test(priority = 13)
	@Description("Link business member with empty ownershipStake")
	public void test_13_Link_business_memeber_with_empty_ownershipStake() throws Exception {
		
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", null);		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"An ownership stake value must be supplied when granting the \"Beneficial Owner\" role.");
}
	
	
	@Test(priority = 14)
	@Description("Link business member with ownershipStake value as 0")
	public void test_14_Link_business_memeber_with_ownershipStake_value_as_0() throws Exception {
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", (float)0);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"An ownership stake value must be supplied when granting the \"Beneficial Owner\" role.");

}
	
	
	@Test(priority = 15)
	@Description("Link business member with ownershipStake value as 1")
	public void test_15_Link_business_memeber_with_ownershipStake_value_as_1() throws Exception {
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", (float)1);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "beneficial_owner");
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "User \"peter parker\" has been made a Beneficial Owner for business corporate.");   


}
	
	
	
	@Test(priority = 16)
	@Description("Link business member with ownershipStake value as 100")
	public void test_16_Link_business_memeber_with_ownershipStake_value_as_100() throws Exception {
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", (float)1);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "beneficial_owner");
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "Business role \"Beneficial Owner\" has already been assigned to member "+Handle_22+" for business "+businessEntityName+".");   


}
	
	
	
	@Test(priority = 17)
	@Description("Link business member with ownershipStake value_as_101")
	public void test_17_Link_business_memeber_with_ownershipStake_value_as_101() throws Exception {
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 3), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", (float)101);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
}
	
	
	
	@Test(priority = 18)
	@Description("Link business member with ownershipStake as negative ")
	public void test_18_Link_business_memeber_with_ownershipStake_as_negative() throws Exception {
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 3), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "test details", (float)-2);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}
	
	
	
	@Test(priority = 19)
	@Description("Link business member with empty details field")
	public void test_19_Link_business_memeber_with_empty_details_field() throws Exception {
		
		ApiResponse businessRolesResponse = api.getBusinessRoles();
		BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);	

        ApiResponse response = api.linkBusinessMember(handle22,reader.getCellData(sheetName, privatekeys, 2), businessHandle,
        		reader.getCellData(sheetName, privatekeys, 41),business_role_beneficial_owner, null, "", (float)0.66);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getRole(), "beneficial_owner");
	    Assert.assertEquals(((LinkBusinessMemberResponse)response.getData()).getMessage(), "Business role \"Beneficial Owner\" has already been assigned to member "+Handle_22+" for business "+businessEntityName+".");   


	}

	
	
}
