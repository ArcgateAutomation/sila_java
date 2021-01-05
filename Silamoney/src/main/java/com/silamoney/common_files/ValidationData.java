package com.silamoney.common_files;


public class ValidationData{

	public int successStatusCode=200;
	protected boolean  successTrue= true;
	protected boolean successFalse= false;

	protected String statusTrue= "SUCCESS";
	protected String statusFalse= "FAILURE";
	
	
	
	protected String refNotEmpty="null";
	
	
	
	//Check_Handle
	protected String handleTakenMsg=Base_class.handleTaken+" is taken.";
	protected String handleAvailableMsg=Base_class.Handle_1+" is available!";

	
	protected String validationErrorMsg="Bad request.";
	protected String invalidSignErrorMsg="Failed to authenticate app signature. The derived address 0x51851c2EF4db74d7F37E6f061d769994CbF5ae27 is not registered to arcgqa1.";
	protected String invalidSignErrorMsg2="Failed to authenticate user signature. The derived address 0x51851c2EF4db74d7F37E6f061d769994CbF5ae27 is not registered to "+Base_class.Handle_27+".";
	protected String invalidSignErrorMsg3="Failed to authenticate user signature. The derived address "+Base_class.reader.getCellData(Base_class.sheetName, Base_class.cryptoAddress, 6)+" is not registered to "+Base_class.Handle_22+".";
	protected String invalidSignErrorMsg4="Handle "+Base_class.Handle_not_registered+" not registered by app.";
	protected String invalidSignErrorMsg5="Handle "+Base_class.Handle_26+" not registered by app.";
	protected String ErrorMsg1="The requested app handle is forbidden access to the Sila platform.";
	protected String ErrorMsg2 ="Failed to authenticate app signature. The derived address 0x51851c2EF4db74d7F37E6f061d769994CbF5ae27 is not registered to arcgqa1.";
	protected String ErrorMsg3 ="This app is not configured for the \"FAIL\" KYC flow. Contact support if this is an issue.";
	protected String ErrorMsg4 ="Failed to authenticate app signature. The derived address 0x039e8166Cd0ccbE7AbaE3036c4C5D055BCaBd582 is not registered to arcgqa2.";


	protected String successKYCMessage1 =Base_class.Handle_21+ " submitted for KYC review.";
	//protected String successKYCMessage2 =Helper2.Handle_5+ " submitted for KYC review.";
	protected String successKYCMessage21 =Base_class.Handle_21+ " submitted for KYC review.";
	protected String successKYCMessage22 =Base_class.Handle_22+ " submitted for KYC review.";
	protected String successKYCMessage23 =Base_class.Handle_23+ " submitted for KYC review.";
	protected String ErrorCheckKYCMessage ="Handle "+Base_class.Handle_not_registered+ " not registered by app.";
	protected String HandleNotRegisteredErrorMgs ="Handle "+Base_class.Handle_not_registered+ " not registered by app.";

	protected String ErrorCheckKYCMessage2 ="ID verification was not requested for "+Base_class.Handle_2+".";
	protected String ErrorCheckKYCMessage25 ="ID verification was not requested for "+Base_class.Handle_25+".";
	protected String ErrorCheckKYCMessage24 ="ID verification was not requested for "+Base_class.Handle_24+".";
	protected String ErrorCheckKYCMessage26 ="ID verification was not requested for "+Base_class.Handle_26+".";

	protected String pendingKysValificationMgs =Base_class.Handle_24+ " is pending ID verification.";
	protected String passKycValificationMgs =Base_class.Handle_24+ " has passed ID verification!";
	
	
	protected String invalidPublicTokenErrorMsg ="The provided Plaid public token is in an invalid format. Expected format: public-<environment>-<identifier>.";
	protected String accountNameWith41Chars ="this account name 4 the test purpose only";
	protected String accountNameWith40Chars ="this account name 4 the test purpos only";
	protected String textWith41Chars ="te this test name 4 the test purpose only";
	protected String textWith40Chars ="t this test name 4 the test purpose only";
	
	
	protected String textWith41Char =Base_class.DateNTime+"1 this account name 4 the test.";
	protected String textWith40Char =Base_class.DateNTime+" this account name 4 the test.";
	protected String validAccountName ="defaultacc";
	protected String validPlaidAccountName ="defaultacc1";
	protected String validDirectAccountName ="defaultacc2";
	
	protected String successAccountLinkMsg ="Bank account successfully linked with status \"instantly_verified\".";



	
	//Registration
	String userHandle1=Base_class.handle1;
	protected static String firstName="peter";
	protected static String lastName="parker";
	protected static String entityName="test user";
	protected static String streetAddress1="123 Main Street"; 
	protected static String streetAddress_40Chars="123 main street, link road, near central"; 
	protected static String streetAddress_41Chars="123 main street, link road, near central."; 
	protected static String textWith_40Chars="123 main street, link road, near central"; 
	protected static String textWith_41Chars="123 main street, link road, near central."; 
	protected static String streetAddress2="Link Road";
	protected static String city="Alaska";
	protected static String city40Chars="test city name to test 40 chars validati";
	protected static String city41Chars="test city name to test 41 chars validatio";
	protected static String state="OR"; 
	protected static String postalCode="972254306"; 
	protected static String phone="1234567890";
	protected static String email="peter@test.com";
	protected static String cryptoAddress="java_wallet_1"; 
	protected static String identityNumber ="574-86-1689"; 
	protected static String identityNumber2 ="SSN"; 
	protected static String individualIdentityAlias ="EIN"; 
	protected static String businessIdentyAlias ="EIN";
	protected static String emptyIdentityNumber =""; 
	protected static String country ="US"; 
	//protected String birthdate="1990,01,31";
	protected static String invalid_Email ="invalidemail@com"; 
	protected static String above_max_length_email = "fakedsadasdasddsadasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdsadassadasdasddsadasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdsadasssadasdasddsadasdasdasdasdasdasdasdasdasdasdasdasdasdgsadadasdasdasdasdasdasdsadasdasdasdasdasdasdasdasdasdasd@gmail.com";
	protected static String max_length_email = "fakedsadasdasddsadasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdsadassadasdasddsadasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdsadasssadasdasddsadasdasdasdasdasdasdasdasdasdasdasdasdasdgsadadasdasdasdasdasdasdsadasdasdasdasdasdasdasdasdasd@gmail.com";

	protected String registerSuccessMsg_1=Base_class.Handle_1+" was successfully registered.";
	protected String registerSuccessMsg_2=Base_class.Handle_24+" was successfully registered.";
	protected String registerSuccessMsg_3=Base_class.Handle_3+" was successfully registered.";
	protected String registerSuccessMsg_4=Base_class.Handle_4+" was successfully registered.";
	protected String registerSuccessMsg_5=Base_class.Handle_1+" is taken.";
	protected String registerSuccessMsg_23=Base_class.Handle_21+" was successfully registered.";
	
	//Link Direct account linking
	protected String accountNumber="123456789012";
	protected String accountNumber1Digit="1";
	protected String accountNumber17Digit="12345678901225423";
	protected String accountNumber18Digit="123456789012254238";
	protected String accountNumberWithInvalidFormat="12345tsdyr";
	protected String routingNumber="123456789";
	protected String routingNumber8Digits="12345678";
	protected String routingNumber10Digits="1234567890";
	protected String routingNumberWithInvalidFormat="1234r6f89";
	
	protected String accountType="CHECKING";
	protected String accountTypeLoweCase="checking";
	protected String accountTypeWith10Chars="SAVINGACCO";
	protected String accountTypeWith11Chars="SAVINGACCOU";
	protected String accountName="peter parker";
	protected String accountNameWith1Char="p";
	
	//protected String accountNameWith40Chars="peter parker test account for the test .";
	
	protected String emptyAccountNumber="";
	protected String emptyRoutingNumber="";
	protected String emptyAccountType="";
	protected String emptyAccountName="";
	
	
	
	
	protected String directLinkAccSuccessMsg="Bank account successfully manually linked.";
	
	//plaid sameday auth
	protected String validationMsg1="Bank account \""+validAccountName+"\" for user/handle \""+Base_class.Handle_22+"\" not in status \"microdeposit_pending_manual_verification\"";
	protected String validationMsg2="No bank account found matching name: \"saving\"";
	protected String validationMsg3="No bank account found matching name: \"defaultacc\"";
	
	//Add registration data
	protected static String emptyStreetAddress1="";
	protected static String emptyStreetAddress2="";
	protected static String emptyPostalCode="";
	protected static String emptyCity="";
	protected static String emptyState="";
	protected static String emptyCountry ="";
	protected static String emptyAddressAlias =""; 
	
	protected String addNewEmail=Base_class.DateNTime+"test@sila.com";
	protected String addNewPhone="1234567890";
	protected String addNewIdentity="123452222";
	protected String invalid_identity_value = "12345678te";
	protected String identityErrorMgs ="Users should not have multiple SSNs, and this request would result in "+Base_class.Handle_27+" having more than one SSN registered. Contact support@silamoney.com for further assistance if needed.";
	protected String identityAliasErrorMgs ="Identity type EIN cannot be added to the individual entity type.";
	protected static String addStreetAddress1="new center mall"; 
	protected static String addStreetAddress2="airport road";
	protected static String addPostalCode="972254388";
	protected static String addCity="Alaska";
	protected static String addState="OR"; 
	protected static String addCountry ="US"; 
	protected static String addAddressAlias ="new address"; 
	
	
	protected String updateEmail=Base_class.DateNTime+"new@sila.com";
	protected String updatePhone="1234567800";
	protected String updateIdentity="123452200";
	protected static String updateStreetupdateress1="newest center mall"; 
	protected static String updateStreetupdateress2="airport line road";
	protected static String updatePostalCode="972254000";
	protected static String updateCity="Albama";
	protected static String updateState="OR"; 
	protected static String updateCountry ="US"; 
	protected static String updateAddressAlias ="updateress"; 
	
	protected static String newFirstName="John";
	protected static String newLastName="watson";
	protected static String newEntity="Add Test Entity";
	
	
	
	//Business User
	protected static String naicsCategory="property dealer";
	//protected static String doing_business_as="seller";
	protected static String wallet_name="test_wallet";
	
	
	
	//Business User
	protected static String businessAlias="property dealer";
	protected static String business_website="https://www.silatest.crm";
	protected static String doing_business_as="seller";
	protected static String naics_code="721";
	protected static String type="business";
	protected static String crypto_alias="java_wallet";
	//protected static String businessType="corporation";
	protected static String entity_name="test Corporation";
	protected static String identity_alias ="EIN"; 
	protected static String identityValue ="123452222"; 
	protected static String businessEntityName ="corporate"; 
	protected static String doingBusiness_As ="doing business as"; 
	protected static String descriptor_text ="test descriptor"; 
	//protected static String type ="business"; 


	
	
	

	

	

	

	


	
	}


