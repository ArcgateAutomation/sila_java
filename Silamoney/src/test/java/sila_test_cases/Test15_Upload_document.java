package sila_test_cases;

import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.DocumentsResponse;
import com.silamoney.client.domain.UploadDocumentMessage;
import com.silamoney.client.domain.User;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;
import io.qameta.allure.Description;

public class Test15_Upload_document extends Base_class{
		

		@Test(priority = 1)
		@Description("Verify user is able to upload document with .png Extension file")
		public void Test_01_upload_document_with_png_extension_file() throws Exception {	
		//User5   : Use for Updated registration data
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User(handle28,  firstName,  lastName, entityName, streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser28CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 8, Utility.getuser28PrivateKey());
		reader.setCellData(sheetName, cryptoAddress, 8, Utility.getuser28CryptoAddress());
		api.register(user);
		//Request KYC
		api.requestKYC(handle28, null, reader.getCellData(sheetName, privatekeys, 8));
		Thread.sleep(3000);


		//UPload Document
		UploadDocumentMessage message = UploadDocumentMessage.builder()
		       .userHandle(handle28) // The user handle
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("dummy file") 
		       .mimeType("image/png").documentType("tax_1040").identityType("other").name("Test png file") .description("upload png file").build();
		ApiResponse response = api.uploadDocument(message);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		Assert.assertNotNull(parsedResponse.getReferenceId());
		Assert.assertNotNull(parsedResponse.getDocumentId());
		}


		@Test(priority = 2)
		@Description("Verify user is able to upload document with .jpg Extension file")
		public void Test_02_upload_document_with_jpg_extension_file() throws Exception {	
		UploadDocumentMessage message = UploadDocumentMessage.builder()
			      .userHandle(handle28)
			      .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/jpg_image.jpg").filename("dummy file") 
			      .mimeType("image/jpeg").documentType("tax_1040").identityType("other").name("Test jpg file").description("upload jpg file").build();
		ApiResponse response = api.uploadDocument(message);
		System.out.println(response.getStatusCode());
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		Assert.assertNotNull(parsedResponse.getReferenceId());
		Assert.assertNotNull(parsedResponse.getDocumentId());

		}

		@Test(priority = 3)
		@Description("Verify user is able to upload document with .pdf Extension file")
		public void Test_03_upload_document_with_pdf_extension_file() throws Exception {	
		UploadDocumentMessage message = UploadDocumentMessage.builder()
		       .userHandle(handle28) // The user handle
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/dummy.pdf").filename("dummy_pdf") 
		       .mimeType("application/pdf").documentType("tax_1040").identityType("other").name("Test pdf file") .description("upload pdf file").build();
		ApiResponse response = api.uploadDocument(message);			

		DocumentsResponse parsedResponse =(DocumentsResponse)response.getData();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		Assert.assertNotNull(parsedResponse.getReferenceId());
		Assert.assertNotNull(parsedResponse.getDocumentId());

		}


		@Test(priority = 4)
		@Description("Verify user is not able to upload document without requested kyc")
		public void Test_04_upload_document_without_requested_kyc() throws Exception { 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handleNotRegistered)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image") 
		       .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description").build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),HandleNotRegisteredErrorMgs);
		


		}




		@Test(priority = 15)
		@Description("Verify user is not able to upload document with empty user_handle")
		public void Test_15_upload_document_with_empty_user_handle() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle("")
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image") 
		       .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description").build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		 Assert.assertEquals(response.getSuccess(), successFalse);
		 Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		 Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
		
		}

		@Test(priority = 16)
		@Description("Verify user is not able to upload document with empty filename field")
		public void Test_16_upload_document_with_empty_filename_field() throws Exception {	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("") 
		       .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description").build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}



//		@Test(priority = 18)
//		@Description("Verify user is not able to upload document with incorrect filepath field")
//		public void Test_18_upload_document_with_empty_filepath_field() throws Exception {
//		
//		 		
//		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
//		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)). null.filename("png_image")
//		       .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
//		       .build();
//		ApiResponse response = api.uploadDocument(message);
//		 Assert.assertEquals(response.getSuccess(), successFalse);
//		 Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
//		 Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
//
//		}

		@Test(priority = 19)
		@Description("Verify user is not able to upload document with empty mime_type field")
		public void Test_19_upload_document_with_empty_mime_type_field() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description").build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		 Assert.assertEquals(response.getSuccess(), successFalse);
		 Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		 Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}

		
		  @Test(priority = 20)
		  
		  @Description("Verify upload document with invalid mime_type field") public
		  void Test_20_upload_document_with_invalid_mime_type_field() throws Exception { 
			  UploadDocumentMessage message =UploadDocumentMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys,8))
					  .filePath(workingDir+"/TestData/png_image.png").filename("png_image") .mimeType("image/pngtest").documentType("id_drivers_permit")
					  .identityType("license").name("id") .description("some file description").build();
		  ApiResponse response = api.uploadDocument(message);
		  
		  Assert.assertEquals(response.getStatusCode(), 400);
		  Assert.assertEquals(response.getSuccess(), successFalse);
		  Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
		  statusFalse);
		  Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), "Unsupported MIME type: image/pngtest."); 
		  }
		 
		 /*	 * 
		 * @Test(priority = 21)
		 * 
		 * @Description("Verify user is not able to upload document with different mime_type from image extension 01"
		 * ) public void
		 * Test_21_upload_document_with_different_mime_type_from_image_extension_01()
		 * throws Exception { UploadDocumentMessage message =
		 * UploadDocumentMessage.builder().userHandle(handle28)
		 * .userPrivateKey(reader.getCellData(sheetName, privatekeys,
		 * 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		 * .mimeType("image/jpeg").documentType("id_drivers_permit").identityType(
		 * "license").name("id") .description("some file description").build();
		 * ApiResponse response = api.uploadDocument(message);
		 * 
		 * Assert.assertEquals(response.getStatusCode(), 400);
		 * Assert.assertEquals(response.getSuccess(), successFalse);
		 * Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
		 * statusFalse); }
		 * 
		 * @Test(priority = 22)
		 * 
		 * @Description("Verify user is not able to upload document with different mime_type from image extension 02"
		 * ) public void
		 * Test_22_upload_document_with_different_mime_type_from_image_extension_02()
		 * throws Exception { UploadDocumentMessage message =
		 * UploadDocumentMessage.builder().userHandle(handle28)
		 * .userPrivateKey(reader.getCellData(sheetName, privatekeys,
		 * 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		 * .mimeType("application/pdf").documentType("id_drivers_permit").identityType(
		 * "license").name("id") .description("some file description").build();
		 * ApiResponse response = api.uploadDocument(message);
		 * Assert.assertEquals(response.getStatusCode(), 400);
		 * Assert.assertEquals(response.getSuccess(), successFalse);
		 * Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
		 * statusFalse); }
		 * 
		 * @Test(priority = 23)
		 * 
		 * @Description("Verify user is not able to upload document with different mime_type from image extension 03"
		 * ) public void
		 * Test_23_upload_document_with_different_mime_type_from_image_extension_03()
		 * throws Exception { UploadDocumentMessage message =
		 * UploadDocumentMessage.builder().userHandle(handle28)
		 * .userPrivateKey(reader.getCellData(sheetName, privatekeys,
		 * 8)).filePath(workingDir+"/TestData/jpg_image.jpg").filename("jpg_image")
		 * .mimeType("image/png").documentType("id_drivers_permit").identityType(
		 * "license").name("id") .description("some file description") .build();
		 * ApiResponse response = api.uploadDocument(message);
		 * 
		 * Assert.assertEquals(response.getStatusCode(), 400);
		 * Assert.assertEquals(response.getSuccess(), successFalse);
		 * Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
		 * statusFalse);
		 * 
		 * }
		 * 
		 * @Test(priority = 24)
		 * 
		 * @Description("Verify user is not able to upload document with different mime_type from image extension 04"
		 * ) public void
		 * Test_24_upload_document_with_different_mime_type_from_image_extension_04()
		 * throws Exception { UploadDocumentMessage message =
		 * UploadDocumentMessage.builder().userHandle(handle28)
		 * .userPrivateKey(reader.getCellData(sheetName, privatekeys,
		 * 8)).filePath(workingDir+"/TestData/jpg_image.jpg").filename("jpg_image")
		 * .mimeType("application/pdf").documentType("id_drivers_permit").identityType(
		 * "license").name("id") .description("some file description") .build();
		 * ApiResponse response = api.uploadDocument(message);
		 * Assert.assertEquals(response.getStatusCode(), 400);
		 * Assert.assertEquals(response.getSuccess(), successFalse);
		 * Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
		 * statusFalse);
		 * 
		 * }
		 * 
		 * @Test(priority = 25)
		 * 
		 * @Description("Verify user is not able to upload document with different mime_type from image extension 04"
		 * ) public void
		 * Test_25_upload_document_with_different_mime_type_from_image_extension_05()
		 * throws Exception { UploadDocumentMessage message =
		 * UploadDocumentMessage.builder().userHandle(handle28)
		 * .userPrivateKey(reader.getCellData(sheetName, privatekeys,
		 * 8)).filePath(workingDir+"/TestData/dummy.pdf").filename("dummy_pdf")
		 * .mimeType("image/png").documentType("id_drivers_permit").identityType(
		 * "license").name("id") .description("some file description") .build();
		 * ApiResponse response = api.uploadDocument(message);
		 * Assert.assertEquals(response.getStatusCode(), 400);
		 * Assert.assertEquals(response.getSuccess(), successFalse);
		 * Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
		 * statusFalse);
		 * 
		 * }
		 * 
		 * @Test(priority = 26)
		 * 
		 * @Description("Verify user is not able to upload document with different mime_type from image extension 06"
		 * ) public void
		 * Test_26_upload_document_with_different_mime_type_from_image_extension_06()
		 * throws Exception { UploadDocumentMessage message =
		 * UploadDocumentMessage.builder().userHandle(handle28)
		 * .userPrivateKey(reader.getCellData(sheetName, privatekeys,
		 * 8)).filePath(workingDir+"/TestData/dummy.pdf").filename("dummy_pdf")
		 * .mimeType("image/jpeg").documentType("id_drivers_permit").identityType(
		 * "license").name("id") .description("some file description") .build();
		 * ApiResponse response = api.uploadDocument(message);
		 * 
		 * Assert.assertEquals(response.getStatusCode(), 400);
		 * Assert.assertEquals(response.getSuccess(), successFalse);
		 * Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),
		 * statusFalse);
		 * 
		 * }
		 */

		@Test(priority = 27)
		@Description("Verify user is not able to upload document with empty document_type field")
		public void Test_27_upload_document_with_empty_document_type_field() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("").identityType("license").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		 Assert.assertEquals(response.getSuccess(), successFalse);
		 Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		 Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

		}

		@Test(priority = 28)
		@Description("Verify user is not able to upload document with invalid document_type field")
		public void Test_28_upload_document_with_invalid_document_type_field() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/jpeg").documentType("id_drivers_permit_test").identityType("license").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		 Assert.assertEquals(response.getSuccess(), successFalse);
		 Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		 Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document type: id_drivers_permit_test.");
		
		}
		

		@Test(priority = 29)
		@Description("Verify user is able to upload document with document_type tax_1040")
		public void Test_29_upload_document_with_document_type_tax_1040() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1040").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 30)
		@Description("Verify user is able to upload document with document_type vtl_birth_certificate")
		public void Test_30_upload_document_with_document_type_vtl_birth_certificate() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("vtl_birth_certificate").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 31)
		@Description("Verify user is able to upload document with document_type doc_name_change")
		public void Test_31_upload_document_with_document_type_doc_name_change() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_name_change").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}
		@Test(priority = 32)
		@Description("Verify user is able to upload document with document_type vtl_divorce")
		public void Test_32_upload_document_with_document_type_vtl_divorce() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("vtl_divorce").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 33)
		@Description("Verify user is able to upload document with document_type id_drivers_permit")
		public void Test_33_upload_document_with_document_type_id_drivers_permit() throws Exception {	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 34)
		@Description("Verify user is able to upload document with document_type tax_w2")
		public void Test_34_upload_document_with_document_type_tax_w2() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_w2").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 35)
		@Description("Verify user is able to upload document with document_type doc_lease")
		public void Test_35_upload_document_with_document_type_doc_lease() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_lease").identityType("contract").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 36)
		@Description("Verify user is able to upload document with document_type vtl_marriage")
		public void Test_36_upload_document_with_document_type_vtl_marriage() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("vtl_marriage").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 37)
		@Description("Verify user is able to upload document with document_type id_military_dependent")
		public void Test_37_upload_document_with_document_type_id_military_dependent() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("id_military_dependent").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 38)
		@Description("Verify user is able to upload document with document_type id_military")
		public void Test_38_upload_document_with_document_type_id_military() throws Exception {	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("id_military").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 39)
		@Description("Verify user is able to upload document with document_type doc_mortgage")
		public void Test_39_upload_document_with_document_type_doc_mortgage() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_mortgage").identityType("contract").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 40)
		@Description("Verify user is able to upload document with document_type id_nyc_id")
		public void Test_40_upload_document_with_document_type_id_nyc_id() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("id_nyc_id").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 41)
		@Description("Verify user is able to upload document with document_type other_doc")
		public void Test_41_upload_document_with_document_type_other_doc() throws Exception {	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("other_doc").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		

		@Test(priority = 42)
		@Description("Verify user is able to upload document with document_type other_id")
		public void Test_42_upload_document_with_document_type_other_id() throws Exception {	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("other_id").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}


		@Test(priority = 43)
		@Description("Verify user is able to upload document with document_type id_passport")
		public void Test_43_upload_document_with_document_type_id_passport() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("id_passport").identityType("passport").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		
		
		@Test(priority = 44)
		@Description("Verify user is able to upload document with document_type doc_paystub")
		public void Test_44_upload_document_with_document_type_doc_paystub() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_paystub").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 45)
		@Description("Verify user is able to upload document with document_type doc_green_card")
		public void Test_45_upload_document_with_document_type_doc_green_card() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_green_card").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 46)
		@Description("Verify user is able to upload document with document_type doc_ssa")
		public void Test_46_upload_document_with_document_type_doc_ssa() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_ssa").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 47)
		@Description("Verify user is able to upload document with document_type doc_ss_card")
		public void Test_47_upload_document_with_document_type_doc_ss_card() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_ss_card").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 48)
		@Description("Verify user is able to upload document with document_type id_state")
		public void Test_48_upload_document_with_document_type_id_state() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("id_state").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 49)
		@Description("Verify user is able to upload document with document_type id_drivers_license")
		public void Test_49_upload_document_with_document_type_id_drivers_license() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("id_drivers_license").identityType("license").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 50)
		@Description("Verify user is able to upload document with document_type tax_1095")
		public void Test_50_upload_document_with_document_type_tax_1095() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1095").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 51)
		@Description("Verify user is able to upload document with document_type tax_1099")
		public void Test_51_upload_document_with_document_type_tax_1099() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1099").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 52)
		@Description("Verify user is able to upload document with document_type doc_tuition")
		public void Test_52_upload_document_with_document_type_doc_tuition() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_tuition").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 53)
		@Description("Verify user is able to upload document with document_type doc_uo_benefits")
		public void Test_53_upload_document_with_document_type_doc_uo_benefits() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_uo_benefits").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 1)
		@Description("Verify user is able to upload document with document_type id_passport_card")
		public void Test_54_upload_document_with_document_type_id_passport_card() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("id_passport_card").identityType("passport").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

		@Test(priority = 55)
		@Description("Verify user is able to upload document with document_type doc_utility")
		public void Test_55_upload_document_with_document_type_doc_utility() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("doc_utility").identityType("utility").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}

	
		@Test(priority = 56)
		@Description("Verify user is able to upload document with document_type tax_W4")
		public void Test_56_upload_document_with_document_type_tax_W4() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_W4").identityType("other").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		
		}


		@Test(priority = 57)
		@Description("Verify user is not able to upload document with mismatched document_type and identity_type fields 01")
		public void Test_57_upload_document_with_mismatched_document_type_and_identity_type_fields_01() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1040").identityType("license").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
				
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"The identity_type tax_1040 is not associated with document_type license.");
		
		}

		@Test(priority = 58)
		@Description("Verify user is not  able to upload document with mismatched document_type and identity_type fields 02")
		public void Test_58_upload_document_with_mismatched_document_type_and_identity_type_fields_02() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1040").identityType("contract").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
				
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"The identity_type tax_1040 is not associated with document_type contract.");
		}

		@Test(priority = 59)
		@Description("Verify user is not able to upload document with mismatched document_type and identity_type fields 03")
		public void Test_59_upload_document_with_mismatched_document_type_and_identity_type_fields_03() throws Exception {
		
		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1040").identityType("passport").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
				
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"The identity_type tax_1040 is not associated with document_type passport.");
		}

		@Test(priority = 60)
		@Description("Verify user is not able to upload document with mismatched document_type and identity_type fields 04")
		public void Test_60_upload_document_with_mismatched_document_type_and_identity_type_fields_04() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1040").identityType("utility").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
				
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"The identity_type tax_1040 is not associated with document_type utility.");
		}
		
		@Test(priority = 61)
		@Description("Verify user is not able to upload document with empty identity_type fields")
		public void Test_61_upload_document_with_empty_identity_type_fields() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1040").identityType("").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
				
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}

		// not working as expected please check once getting 400 status code instead of 200.
		@Test(priority = 62)
		@Description("Verify user is not able to upload document with empty name fields")
		public void Test_62_upload_document_with_empty_name_fields() throws Exception {		 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1040").identityType("other").name("") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		Thread.sleep(3000);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		}

		// not working as expected please check once. Getting 400 status code instead of 200.
		@Test(priority = 63)
		@Description("Verify user is able to upload document with empty description fields")
		public void Test_63_upload_document_with_empty_description_fields() throws Exception {		 			
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
			       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
			       .mimeType("image/png").documentType("tax_W4").identityType("other").name("id") .description("") 
			       .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 200);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		}

		@Test(priority = 64)
		@Description("Verify user is not able to upload document with invalid identity_type fields")
		public void Test_64_upload_document_with_invalid_identity_type_fields() throws Exception {	 		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image")
		       .mimeType("image/png").documentType("tax_1040").identityType("test").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Specified identity_type not found: test.");
		
		}



		@Test(priority = 65)
		@Description("Verify user is not able to upload document with invalid file signature")
		public void Test_65_upload_document_with_invalid_file_signature() throws Exception {	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("jpg_image")
		       .mimeType("application/pdf").documentType("tax_1040").identityType("test").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api1.uploadDocument(message);
		
		System.out.println("Test_65_upload_document_with_invalid_file_signature: "+((BaseResponse)response.getData()).getMessage());
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(), invalidSignErrorMsg);
		}

		
		// not working. please check once.
		@Test(priority = 66)
		@Description("Verify user is not able to upload document with different crypto key")
		public void Test_66_upload_document_with_another_crytp_key() throws Exception {
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle28)
		       .userPrivateKey(reader.getCellData(sheetName, privatekeys, 6)).filePath(workingDir+"/TestData/png_image.png").filename("jpg_image")
		       .mimeType("application/pdf").documentType("tax_1040").identityType("test").name("id") .description("some file description") 
		       .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 6)+" is not registered to "+Handle_28+".".toLowerCase());
		}
				
	
		

}
