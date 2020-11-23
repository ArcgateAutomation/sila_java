package dummyTestcases;


import org.joda.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.DocumentsResponse;
import com.silamoney.client.domain.GetDocumentMessage;
import com.silamoney.client.domain.UploadDocumentMessage;
import com.silamoney.client.domain.User;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;

import io.qameta.allure.Description;

public class DummyClass extends Base_class{

	
	// Register Individual User
		@Test(priority = 1)
		@Description("Check registration with all valid data")
		public void Test_01_Test_01_register_with_all_valid_data() throws Exception {
		//User Registration
		LocalDate birthdate = new LocalDate(2000, 01, 31);
		User user = new User(handle27,  firstName,  lastName,  streetAddress1, streetAddress2, city,  state, 
		postalCode,  phone,  email, identityNumber, Utility.getuser27CryptoAddress(), birthdate.toDate(), country);
		reader.setCellData(sheetName, privatekeys, 7, Utility.getuser27PrivateKey());
		ApiResponse register_response = api.register(user);
		Thread.sleep(3000);
		System.out.println("Registartion Status: "+register_response.getStatusCode());
		
		
		
		//Request KYC
		ApiResponse response23 = api.requestKYC(handle27, null, reader.getCellData(sheetName, privatekeys, 7));
		Thread.sleep(3000);
		System.out.println("Kyc Status: "+response23.getStatusCode());

		
		//UPload Document
		UploadDocumentMessage message1 = UploadDocumentMessage.builder()
		        .userHandle(handle27) // The user handle
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath(workingDir+"/TestData/jpg_image.jpg").filename("dummy file") 
		        .mimeType("image/jpeg").documentType("tax_1040").identityType("other").name("Test jpg file") .description("upload jpg file") 
		        .build();
		ApiResponse uploadResponse = api.uploadDocument(message1);
		
		System.out.println(uploadResponse.getStatusCode()); // 200
		DocumentsResponse parsedResponse = (DocumentsResponse) uploadResponse.getData();
		String documentUuid=parsedResponse.getDocumentId();
		System.out.println(documentUuid);

	
		
//		//List Documents
//		List<String> docTypes = new ArrayList<String>();
//		docTypes.add("tax_1040");
//		// With no pagination
//		ListDocumentsMessage message = ListDocumentsMessage.builder()
//		        .userHandle(handle27)
//		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).search("Test jpg file")
//		        .sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
//		ApiResponse listResponse = api.listDocuments(message);
//		// With pagination
//		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
//		ApiResponse response = api.listDocuments(message, pagination);
//
//		// Success Response
//		System.out.println(response.getStatusCode()); // 200
		
//		
//		
//		
//		
//		//retrive document
//		GetDocumentMessage message = GetDocumentMessage.builder()
//		        .userHandle(handle27)
//		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7))
//		        .documentId("3b6eb918-f3c3-4753-a7d4-7228efd55cfa")
//		        .build();
//		ApiResponse getDocResponse = api.getDocument(message);
//
//		// Success response
//		System.out.println(getDocResponse.getStatusCode()); // 200
//		System.out.println(getDocResponse.getHeaders().get("Content-Type")); // Document MIME type
//		System.out.println(getDocResponse.getHeaders().get("Content-Length")); // Document size in bytes
//		System.out.println(getDocResponse.getHeaders().get("Content-Disposition")); // filename=<Document file name>
//		System.out.println((String) getDocResponse.getData()); // The file binary data
		

		}
		
		
		@Test(priority = 1)
		@Description("Verify user is able to upload document with .png Extension file")
		public void Test_01_upload_document_with_png_extension_file() throws Exception {
		
	 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image") 
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("test document upload") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		Assert.assertNotNull(parsedResponse.getReferenceId());
		Assert.assertNotNull(parsedResponse.getReferenceId());
	}


@Test(priority = 2)
		@Description("Verify user is able to upload document with .jpg Extension file")
		public void Test_02_upload_document_with_jpg_extension_file() throws Exception {
		
	 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/jpg_image.jpg").filename("jpg_image") 
		        .mimeType("image/jpeg").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
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
		
	 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy.pdf").filename("dummy") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		DocumentsResponse parsedResponse = (DocumentsResponse) response.getData();
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
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle("not registered user handle")
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image") 
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(), statusFalse);

	}

@Test(priority = 5)
		@Description("Verify user is not able to upload document with .csv extension file")
		public void Test_05_upload_document_with_csv_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file01.csv").filename("dummy") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");

	}


@Test(priority = 6)
		@Description("Verify user is not able to upload document with .txt extension file")
		public void Test_06_upload_document_with_txt_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file02.txt").filename("invalid_format_file02") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");

	}

@Test(priority = 7)
		@Description("Verify user is not able to upload document with .docx Extension file")
		public void Test_07_upload_document_with_docx_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file03.docx").filename("invalid_format_file03") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");
	}

@Test(priority = 8)
		@Description("Verify user is not able to upload document with .xlsx Extension file")
		public void Test_08_upload_document_with_xlsx_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file04.xlsx").filename("invalid_format_file04") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");
		
	}

@Test(priority = 9)
		@Description("Verify user is not able to upload document with .xls Extension file")
		public void Test_09_upload_document_with_xls_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file05.xls").filename("invalid_format_file05") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");
	}

@Test(priority = 10)
		@Description("Verify user is not able to upload document with .webp Extension file")
		public void Test_10_upload_document_with_webp_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file06.webp").filename("invalid_format_file06") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");
	}

@Test(priority = 11)
		@Description("Verify user is not able to upload document with .tiff Extension file")
		public void Test_11_upload_document_with_tiff_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file07.tiff").filename("invalid_format_file07") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");
	}

@Test(priority = 12)
		@Description("Verify user is not able to upload document with .svg Extension file")
		public void Test_12_upload_document_with_svg_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file08.svg").filename("invalid_format_file08") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");
	}


@Test(priority = 13)
		@Description("Verify user is not able to upload document with .gif Extension file")
		public void Test_13_upload_document_with_gif_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file09.gif").filename("invalid_format_file09") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");
	}

@Test(priority = 14)
		@Description("Verify user is not able to upload document with .ico Extension file")
		public void Test_14_upload_document_with_ico_extension_file() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy_file10.ico").filename("invalid_format_file10") 
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document_type");
	}


@Test(priority = 15)
		@Description("Verify user is not able to upload document with empty user_handle")
		public void Test_15_upload_document_with_empty_user_handle() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle("")
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image") 
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
	  Assert.assertEquals(response.getSuccess(), successFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
		
	}

@Test(priority = 16)
		@Description("Verify user is not able to upload document with empty filename field")
		public void Test_16_upload_document_with_empty_filename_field() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("") 
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
		
	}

@Test(priority = 17)
		@Description("Verify user is not able to upload document with invalid filename including the file extension")
		public void Test_17_upload_document_with_invalid_filename_field() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image.png")
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}


# not working - pls check
@Test(priority = 18)
		@Description("Verify user is not able to upload document with empty filepath field")
		public void Test_18_upload_document_with_empty_filepath_field() throws Exception {
		
			 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("").filename("png_image")
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

	}

@Test(priority = 19)
		@Description("Verify user is not able to upload document with empty mime_type field")
		public void Test_19_upload_document_with_empty_mime_type_field() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
				
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}

@Test(priority = 20)
		@Description("Verify upload document with invalid mime_type field")
		public void Test_20_upload_document_with_invalid_mime_type_field() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/pngtest").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Unsupported MIME type: image/pngtest.");
	}


@Test(priority = 21)
		@Description("Verify user is not able to upload document with different mime_type from image extension 01")
		public void Test_21_upload_document_with_different_mime_type_from_image_extension_01() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/jpeg").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
				
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	}

@Test(priority = 22)
		@Description("Verify user is not able to upload document with different mime_type from image extension 02")
		public void Test_22_upload_document_with_different_mime_type_from_image_extension_02() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);				
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);	
	}

@Test(priority = 23)
		@Description("Verify user is not able to upload document with different mime_type from image extension 03")
		public void Test_23_upload_document_with_different_mime_type_from_image_extension_03() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/jpg_image.jpg").filename("jpg_image")
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
				
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  	
	}

@Test(priority = 24)
		@Description("Verify user is not able to upload document with different mime_type from image extension 04")
		public void Test_24_upload_document_with_different_mime_type_from_image_extension_04() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/jpg_image.jpg").filename("jpg_image")
		        .mimeType("application/pdf").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);	
		
	}

@Test(priority = 25)
		@Description("Verify user is not able to upload document with different mime_type from image extension 04")
		public void Test_25_upload_document_with_different_mime_type_from_image_extension_05() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy.pdf").filename("dummy_pdf")
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	
	}

@Test(priority = 26)
		@Description("Verify user is not able to upload document with different mime_type from image extension 06")
		public void Test_26_upload_document_with_different_mime_type_from_image_extension_06() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/dummy.pdf").filename("dummy_pdf")
		        .mimeType("image/jpeg").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	
	}

@Test(priority = 1)
		@Description("Verify user is not able to upload document with empty document_type field")
		public void Test_27_upload_document_with_empty_document_type_field() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/jpeg").documentType("").identityType("license").name("id") .description("some file description") 
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
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/jpeg").documentType("id_drivers_permit_test").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 400);
	  	Assert.assertEquals(response.getSuccess(), successFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	  	Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Invalid document type: id_drivers_permit_test.");
	
	}
/*
@Test(priority = 29)
		@Description("Verify user is not able to upload document with invalid document_type field")
		public void Test_29_upload_document_with_document_type_tax_1040() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("tax_1040").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 30)
		@Description("Verify user is able to upload document with document_type vtl_birth_certificate")
		public void Test_30_upload_document_with_document_type_vtl_birth_certificate() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("vtl_birth_certificate").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 31)
		@Description("Verify user is able to upload document with document_type vtl_birth_certificate")
		public void Test_31_upload_document_with_document_type_doc_name_change() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_name_change").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}
@Test(priority = 32)
		@Description("Verify user is able to upload document with document_type vtl_divorce")
		public void Test_32_upload_document_with_document_type_vtl_divorce() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("vtl_divorce").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 33)
		@Description("Verify user is able to upload document with document_type id_drivers_permit")
		public void Test_33_upload_document_with_document_type_id_drivers_permit() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 34)
		@Description("Verify user is able to upload document with document_type id_drivers_permit")
		public void Test_34_upload_document_with_document_type_id_drivers_permits() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 35)
		@Description("Verify user is able to upload document with document_type doc_lease")
		public void Test_35_upload_document_with_document_type_doc_lease() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_lease").identityType("contract").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 36)
		@Description("Verify user is able to upload document with document_type vtl_marriage")
		public void Test_36_upload_document_with_document_type_vtl_marriage() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("vtl_marriage").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 37)
		@Description("Verify user is able to upload document with document_type id_military_dependent")
		public void Test_37_upload_document_with_document_type_id_military_dependent() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_military_dependent").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 38)
		@Description("Verify user is able to upload document with document_type id_military")
		public void Test_38_upload_document_with_document_type_id_military() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_military").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 39)
		@Description("Verify user is able to upload document with document_type doc_mortgage")
		public void Test_39_upload_document_with_document_type_doc_mortgage() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_mortgage").identityType("contract").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 40)
		@Description("Verify user is able to upload document with document_type id_nyc_id")
		public void Test_40_upload_document_with_document_type_id_nyc_id() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_nyc_id").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 41)
		@Description("Verify user is able to upload document with document_type other_doc")
		public void Test_41_upload_document_with_document_type_other_doc() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("other_doc").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

		

@Test(priority = 42)
		@Description("Verify user is able to upload document with document_type other_id")
		public void Test_42_upload_document_with_document_type_other_id() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("other_id").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}


@Test(priority = 43)
		@Description("Verify user is able to upload document with document_type id_passport")
		public void Test_43_upload_document_with_document_type_id_passport() throws Exception {
		
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_passport").identityType("passport").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 44)
		@Description("Verify user is able to upload document with document_type doc_paystub")
		public void Test_44_upload_document_with_document_type_doc_paystub() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_paystub").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 45)
		@Description("Verify user is able to upload document with document_type doc_green_card")
		public void Test_45_upload_document_with_document_type_doc_green_card() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_green_card").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 46)
		@Description("Verify user is able to upload document with document_type doc_ssa")
		public void Test_46_upload_document_with_document_type_doc_ssa() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_ssa").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 47)
		@Description("Verify user is able to upload document with document_type doc_ss_card")
		public void Test_47_upload_document_with_document_type_doc_ss_card() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_ss_card").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 48)
		@Description("Verify user is able to upload document with document_type id_state")
		public void Test_48_upload_document_with_document_type_id_state() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_state").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 49)
		@Description("Verify user is able to upload document with document_type id_drivers_license")
		public void Test_49_upload_document_with_document_type_id_drivers_license() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_drivers_license").identityType("license").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 50)
		@Description("Verify user is able to upload document with document_type tax_1095")
		public void Test_50_upload_document_with_document_type_tax_1095() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("tax_1095").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 51)
		@Description("Verify user is able to upload document with document_type tax_1099")
		public void Test_51_upload_document_with_document_type_tax_1099() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("tax_1099").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 52)
		@Description("Verify user is able to upload document with document_type doc_tuition")
		public void Test_52_upload_document_with_document_type_doc_tuition() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_tuition").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 53)
		@Description("Verify user is able to upload document with document_type doc_uo_benefits")
		public void Test_53_upload_document_with_document_type_doc_uo_benefits() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_uo_benefits").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 1)
		@Description("Verify user is able to upload document with document_type id_passport_card")
		public void Test_54_upload_document_with_document_type_id_passport_card() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("id_passport_card").identityType("passport").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 55)
		@Description("Verify user is able to upload document with document_type doc_utility")
		public void Test_55_upload_document_with_document_type_doc_utility() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("doc_utility").identityType("utility").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}

@Test(priority = 56)
		@Description("Verify user is able to upload document with document_type tax_W4")
		public void Test_56_upload_document_with_document_type_tax_W4() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("tax_W4").identityType("other").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	
	}


@Test(priority = 57)
		@Description("Verify user is not able to upload document with mismatched document_type and identity_type fields 01")
		public void Test_57_upload_document_with_mismatched_document_type_and_identity_type_fields_01() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
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
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
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
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
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
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
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
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
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
		@Description("Verify user is able to upload document with empty name fields")
		public void Test_62_upload_document_with_empty_name_fields() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("tax_1040").identityType("other").name("") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	}

// not working as expected please check once. Getting 400 status code instead of 200.
@Test(priority = 63)
		@Description("Verify user is able to upload document with empty description fields")
		public void Test_63_upload_document_with_empty_description_fields() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("tax_1040").identityType("other").name("id") .description("") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertNotNull(parsedResponse.getDocumentId());
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
	}

@Test(priority = 64)
		@Description("Verify user is not able to upload document with invalid identity_type fields")
		public void Test_64_upload_document_with_invalid_identity_type_fields() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("./TestData/png_image.png").filename("png_image")
		        .mimeType("image/png").documentType("tax_1040").identityType("test").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),"Specified identity_type not found: test.");
		
	}


// not working. please check once.
@Test(priority = 65)
		@Description("Verify user is not able to upload document with invalid file signature")
		public void Test_65_upload_document_with_invalid_file_signature() throws Exception {
		
		 	
		UploadDocumentMessage message = UploadDocumentMessage.builder().userHandle(handle27)
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath("hashlib.sha256().hexdigest()").filename("jpg_image")
		        .mimeType("application/pdf").documentType("tax_1040").identityType("test").name("id") .description("some file description") 
		        .build();
		ApiResponse response = api.uploadDocument(message);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
	}

	

	

*/
}
