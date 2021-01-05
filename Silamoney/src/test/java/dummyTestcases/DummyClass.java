package dummyTestcases;


import java.math.BigInteger;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletFile;

import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.BusinessRole;
import com.silamoney.client.domain.BusinessType;
import com.silamoney.client.domain.BusinessUser;
import com.silamoney.client.domain.DocumentsResponse;
import com.silamoney.client.domain.GetBusinessRolesResponse;
import com.silamoney.client.domain.GetBusinessTypesResponse;
import com.silamoney.client.domain.GetDocumentMessage;
import com.silamoney.client.domain.GetEntityResponse;
import com.silamoney.client.domain.GetWalletsResponse;
import com.silamoney.client.domain.LinkBusinessMemberResponse;
import com.silamoney.client.domain.Membership;
import com.silamoney.client.domain.SearchFilters;
import com.silamoney.client.domain.UploadDocumentMessage;
import com.silamoney.client.domain.User;
import com.silamoney.client.domain.Wallet;
import com.silamoney.client.security.EcdsaUtil;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;
import io.qameta.allure.Description;

public class DummyClass extends Base_class{

	
	
	
	@Test(priority = 1)
	public void Regiter_User() throws Exception {
	//Individual User 1 Registration
	LocalDate birthdate = new LocalDate(2000, 01, 31);
	User user1 = new User(handle22,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
	postalCode,  phone,  email, identityNumber, Utility.getuser22CryptoAddress(), birthdate.toDate(), country);
	reader.setCellData(sheetName, privatekeys, 2, Utility.getuser22PrivateKey());
	reader.setCellData(sheetName, cryptoAddress, 2, Utility.getuser22CryptoAddress());
	ApiResponse User_1_register_response =api.register(user1);
	Thread.sleep(3000);
	System.out.println("User 1 Registartion Status: "+((BaseResponse) User_1_register_response.getData()).getMessage());
	
	//Individual User 2 Registration	
	
	User user2 = new User(handle23,  firstName,  lastName, entityName,  streetAddress1, streetAddress2, city,  state, 
	postalCode,  phone,  email, identityNumber, Utility.getuser23CryptoAddress(), birthdate.toDate(), country);
	reader.setCellData(sheetName, privatekeys, 3, Utility.getuser23PrivateKey());
	reader.setCellData(sheetName, cryptoAddress, 3, Utility.getuser23CryptoAddress());
	ApiResponse User_2_register_response =api.register(user2);
	Thread.sleep(3000);
	System.out.println("User 2 Registartion Status: "+((BaseResponse) User_2_register_response.getData()).getMessage());
	
	//Business User Registration
	
	ApiResponse getBusinessTypesResponse = api.getBusinessTypes();
	BusinessType businessType =((GetBusinessTypesResponse)getBusinessTypesResponse.getData()).getBusinessTypes().get(0);
	
	BusinessUser user = new BusinessUser(country, businessHandle, businessEntityName,  identity_alias, identityValue, phone, email, streetAddress1, streetAddress2,  city, state, postalCode,
	 Utility.getBusinessCryptoAddress(), crypto_alias, type,  businessType, business_website, 
	         doingBusiness_As, Utility.getDefaultNaicCategoryDescription());
	reader.setCellData(sheetName, privatekeys, 41, Utility.getBusinessPrivateKey());
	reader.setCellData(sheetName, cryptoAddress, 41, Utility.getBusinessCryptoAddress());
	ApiResponse businessRegisterResponse = api.registerBusiness(user);	
	
	System.out.println("businessRegisterResponse: "+((BaseResponse)businessRegisterResponse.getData()).getMessage()); // user was success

	
	}
	
	
	@Test(priority = 1)
	public void link_business_memer() throws Exception {
	//link_business_member1_administrator	
	ApiResponse businessRolesResponse = api.getBusinessRoles();
	BusinessRole business_role_ControlOfficer= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);
	BusinessRole business_role_administrator= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(2);
	BusinessRole business_role_beneficial_owner= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(1);
	
    ApiResponse LinkMember_1_asAdmin= api.linkBusinessMember(handle22,
    	reader.getCellData(sheetName, privatekeys, 2), businessHandle,
       	reader.getCellData(sheetName, privatekeys, 41),
       	business_role_administrator, null, "test details", null);
    System.out.println("Handle_22_administrator: "+((LinkBusinessMemberResponse)LinkMember_1_asAdmin.getData()).getMessage());

	        
	//link_business_member1__ControlOfficer	
	ApiResponse LinkMember_1_asControlOffices = api.linkBusinessMember(handle22,
	   	reader.getCellData(sheetName, privatekeys, 2), businessHandle,
	   	reader.getCellData(sheetName, privatekeys, 41),
	   	business_role_ControlOfficer, null, "test details", null); 
	System.out.println("Handle_22_ControlOfficer: "+((LinkBusinessMemberResponse)LinkMember_1_asControlOffices.getData()).getMessage());
	
	//link_business_member__beneficial_owner	        
	ApiResponse LinkMember_2_asAdministratorBeneficial_owner = api.linkBusinessMember(handle23,
	   	reader.getCellData(sheetName, privatekeys, 3), businessHandle,
	   	reader.getCellData(sheetName, privatekeys, 41),
	   	business_role_beneficial_owner, null, "test details", (float)0.66);
	System.out.println("Handle_23_beneficial_owner: "+((LinkBusinessMemberResponse)LinkMember_2_asAdministratorBeneficial_owner.getData()).getMessage());

	}
	
	
	
	
	@Test(priority = 1)
	public void RequesrKYC() throws Exception {
		//Request KYC	
        api.requestKYC(handle22, null, reader.getCellData(sheetName, privatekeys, 2));
        Thread.sleep(5000);
        api.requestKYC(handle23, null, reader.getCellData(sheetName, privatekeys, 3));
        Thread.sleep(5000);
        api.requestKYC(businessHandle, null, reader.getCellData(sheetName, privatekeys, 41));
        Thread.sleep(10000);	

	
	}
	
	
	
	
	@Test(priority = 1)
	public void GetEntity() throws Exception {
		
		//Get Entity
		 ApiResponse getEntityResponse = api.getEntity(handle23, reader.getCellData(sheetName, privatekeys, 3));
		 String membership_token= ((GetEntityResponse) getEntityResponse.getData()).getMemberships().get(0).getCertificationToken();           
		 System.out.println("membership_token: "+membership_token);

	
	}
	
	
	
	
	@Test(priority = 1)
	public void Certufy_Benificial_Owner() throws Exception {
		
		//Certify benificial owner	
		ApiResponse certifyBeneficialOwnerResponse = api.certifyBeneficialOwner(handle22,
		reader.getCellData(sheetName, privatekeys, 2), businessHandle,reader.getCellData(sheetName, privatekeys, 41), handle23, membership_token);
		
	
	}
	
	

	
	
	
	
	

	
	@Test(priority = 1)
	public void Generate_wallet() throws Exception {

	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", wallet_name);
	String walletVerificationSignature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, walletVerificationSignature, reader.getCellData(sheetName, privatekeys, 2));
	System.out.println(((BaseResponse) registerWalletResponse.getData()).getMessage());

	}
	
	
	@Test(priority = 1)
	public void Generate_walle2() throws Exception {

	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", wallet_name);
	String walletVerificationSignature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
	ApiResponse registerWalletResponse = api.registerWallet(handle23, wallet, walletVerificationSignature, reader.getCellData(sheetName, privatekeys, 3));
	System.out.println(((BaseResponse) registerWalletResponse.getData()).getMessage());

	}
	
	// Register Individual User
	@Test(priority = 1)
	@Description("Check registration with all valid data")

	public void Test_01_Tafter() throws Exception {



	        

	}
	// Register Individual User
	@Test(priority = 1)
	@Description("Check registration with all valid data")
	public void Test_01_Test_01_register_with_all_valid_data() throws Exception {

	
////Get Business Roles
//	ApiResponse businessRolesResponse = api.getBusinessRoles();
//	BusinessRole business_role1= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);
//	BusinessRole business_role2= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);
//	BusinessRole business_role3= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);
//	BusinessRole business_role4= ((GetBusinessRolesResponse)businessRolesResponse.getData()).getBusinessRoles().get(0);
//	System.out.println("business_role1: "+business_role1);
//	System.out.println("business_role2: "+business_role2);
//	System.out.println("business_role3: "+business_role3);
//	System.out.println("business_role4: "+business_role4);
//	
//	

	
	
	

	
           // System.out.println(LinkMemberResponseAsbeneficial_owner.getStatusCode()); // 200
            //System.out.println(((LinkBusinessMemberResponse)LinkMemberResponseAsbeneficial_owner.getData()).getDetails());
            //System.out.println(((LinkBusinessMemberResponse)LinkMemberResponseAsbeneficial_owner.getData()).getRole());
            //System.out.println(((LinkBusinessMemberResponse)LinkMemberResponseAsbeneficial_owner.getData()).getVerificationUuid());            


      

   
    
           
//Get Entity
//           ApiResponse getEntityResponse = api.getEntity(handle23, reader.getCellData(sheetName, privatekeys, 3));
//        // Success Object Response
//           String membership_token= ((GetEntityResponse) getEntityResponse.getData()).getMemberships().get(0).getCertificationToken();           
//           System.out.println("membership_token: "+membership_token);
//           
//           String business_certification_token= ((GetEntityResponse) getEntityResponse.getData()).getMembers().get(0).getBusinessCertificationS;   
//           System.out.println(business_certification_token);
           
           
//Certify benificial owner	
           
          
	
//	ApiResponse certifyBeneficialOwnerResponse = api.certifyBeneficialOwner(handle22,
//	reader.getCellData(sheetName, privatekeys, 2), businessHandle,
//	reader.getCellData(sheetName, privatekeys, 41), handle23, membership_token);
//	
//	System.out.println("certifyBeneficialOwnerResponse: "+((BaseResponse)certifyBeneficialOwnerResponse.getData()).getMessage()); // user was success
//	
//           

	
////Request KYC
//	ApiResponse requestKycResponse = api.requestKYC(handle22, null, reader.getCellData(sheetName, privatekeys, 2));
//	Thread.sleep(3000);
//	System.out.println("Kyc Statuss message: "+((BaseResponse) requestKycResponse.getData()).getMessage());
//	
	
	
//// Check Kyc	
//	  ApiResponse check_Kyc_Response = api.checkKYC(DefaultConfigurations.getUserHandle(),
//	            DefaultConfigurations.getUserPrivateKey());
//
//	//ApiResponse check_Kyc_Response = api.checkKYC(handle22, reader.getCellData(sheetName, privatekeys, 2));
//	for (int i = 1; i <=1; i++) {
//	Thread.sleep(30000);
//	System.out.println(((BaseResponse)check_Kyc_Response.getData()).getMessage());
//	if (((BaseResponse)check_Kyc_Response.getData()).getMessage().equals(passKycValificationMgs)) {
//	break;
//	}
//	}
//	
	
	
////Link Account
//	api.linkAccount(handle22, reader.getCellData(sheetName, privatekeys, 2), validPlaidAccountName, validPublicToken);
//	ApiResponse linkAccountResponse = api.linkAccount(handle22, reader.getCellData(sheetName, privatekeys, 2), validPlaidAccountName, validPublicToken);
//	System.out.println(((BaseResponse)linkAccountResponse.getData()).getMessage());
//	
//	
//	
	

	
//register wallet
//	    Wallet wallet = api.generateWallet();
//	    String walletVerificationSignature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
//	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, walletVerificationSignature, reader.getCellData(sheetName, privatekeys, 2));
//
//	System.out.println("registerWalletResponse: "+registerWalletResponse.getStatusCode()); // 200
	//Assert.assertEquals(registerWalletResponse.getStatusCode(), successStatusCode);
	//Assert.assertEquals(registerWalletResponse.getSuccess(), successTrue);
	//Assert.assertNotNull(((BaseResponse) registerWalletResponse.getData()).getReference());
	//Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getStatus(), statusTrue);
	//Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getMessage(), "Blockchain address 0x21cafefa25be9da6b61ffc282d024401639bdafe registered");
	
	
	
//Get Wallet
	
//	ApiResponse getWalletResponse = api.getWallet(handle22, reader.getCellData(sheetName, privatekeys, 2));
//
//	// Success Response Object
//	System.out.println(getWalletResponse.getStatusCode()); // 200
//	System.out.println(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getMessage());
//	System.out.println(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getReference());
//	System.out.println(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getSilaBalance());
//	System.out.println(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getStatus());
//	System.out.println(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getSuccess());
//	System.out.println(((com.silamoney.client.domain.GetWalletResponse) getWalletResponse.getData()).getWallet());
//	System.out.println(getWalletResponse.getData().getWallet()); // Wallet object
//	System.out.println(getWalletResponse.getData().getIsWhitelisted()); // Boolean
//	System.out.println(getWalletResponse.getData().getSilaBalance); // Sila balance
//	
//
//	Assert.assertEquals(getWalletResponse.getStatusCode(), 200);
//	
//	Assert.assertEquals(((GetWalletResponse)getWalletResponse.getData()).getStatus(),statusTrue);
//	Assert.assertTrue(((GetWalletResponse)getWalletResponse.getData()).getSuccess());
//	Assert.assertNotNull(((GetWalletResponse)getWalletResponse.getData()).getWallet().getNickname());
//	Assert.assertNotNull(((GetWalletResponse)getWalletResponse.getData()).getWallet().getBlockChainAddress());
//	Assert.assertNotNull(((GetWalletResponse)getWalletResponse.getData()).getWallet().getBlockChainNetwork());
//	Assert.assertEquals(((GetWalletResponse)getWalletResponse.getData()).getSilaBalance(), 0.0);

	
	
//// Get Wallets
//	SearchFilters filters = new SearchFilters();
//	filters.setPage(1);//enter page 
//	filters.setPerPage(20);//Enyer par page number
//	filters.sortAscending(); //sorting 
//	filters.setBlockChainNetwork(""); //network like ETH
//	filters.setBlockChainAddress("");
//	filters.setNickname(""); //send nick name
//
//	ApiResponse response = api.getWallets("automationuser.silamoney.eth", filters, "55eaa88c07d028973319bf0ddf2278e5613183bbf0796c28d2a8b6bd0ab61f72");
//
//	//  Success Response Object
//	System.out.println(response.getStatusCode()); // 200
//	System.out.println(((GetWalletsResponse)response.getData()).page);
//	System.out.println(((GetWalletsResponse)response.getData()).returnedCount);
//	System.out.println(((GetWalletsResponse)response.getData()).totalCount);
//	System.out.println(((GetWalletsResponse)response.getData()).totalPageCount);
//	System.out.println(((GetWalletsResponse)response.getData()).getWallets());
//	System.out.println(((GetWalletsResponse)response.getData()).getWallets().getTotalCount);
//	
	
/*
	
//UPload Document
	UploadDocumentMessage message1 = UploadDocumentMessage.builder()
	        .userHandle(handle22) // The user handle
	        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 2)).filePath(workingDir+"/TestData/jpg_image.jpg").filename("dummy file") 
	        .mimeType("image/jpeg").documentType("tax_1040").identityType("other").name("Test jpg file") .description("upload jpg file") 
	        .build();
	ApiResponse uploadResponse = api.uploadDocument(message1);
	
	System.out.println(uploadResponse.getStatusCode()); // 200
	DocumentsResponse parsedResponse = (DocumentsResponse) uploadResponse.getData();
	String documentUuid=parsedResponse.getDocumentId();
	System.out.println(documentUuid);
*/
	
/*	
//List Documents
	List<String> docTypes = new ArrayList<String>();
	docTypes.add("tax_1040");
	// With no pagination
	ListDocumentsMessage message = ListDocumentsMessage.builder()
	        .userHandle(handle22)
	        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 2)).search("Test jpg file")
	        .sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
	ApiResponse listResponse = api.listDocuments(message);
	// With pagination
	PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
	ApiResponse response = api.listDocuments(message, pagination);

	// Success Response
	System.out.println(response.getStatusCode()); // 200
	*/	
	
	
	/*	
	
//retrive document
	GetDocumentMessage message = GetDocumentMessage.builder()
	        .userHandle(handle22)
	        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 2))
	        .documentId("3b6eb918-f3c3-4753-a7d4-7228efd55cfa")
	        .build();
	ApiResponse getDocResponse = api.getDocument(message);

	// Success response
	System.out.println(getDocResponse.getStatusCode()); // 200
	System.out.println(getDocResponse.getHeaders().get("Content-Type")); // Document MIME type
	System.out.println(getDocResponse.getHeaders().get("Content-Length")); // Document size in bytes
	System.out.println(getDocResponse.getHeaders().get("Content-Disposition")); // filename=<Document file name>
	System.out.println((String) getDocResponse.getData()); // The file binary data
	

	}
	
	*/
	

	}
	

}
