package dummyTestcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.api.SilaApi;
import com.silamoney.client.domain.AddressMessage;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.DocumentType;
import com.silamoney.client.domain.DocumentsResponse;
import com.silamoney.client.domain.ListDocumentsMessage;
import com.silamoney.client.domain.ListDocumentsResponse;
import com.silamoney.client.domain.PaginationMessage;
import com.silamoney.client.domain.UploadDocumentMessage;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;
import io.qameta.allure.Description;
import com.silamoney.client.domain.User;
import com.silamoney.client.domain.UserHandleMessage;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;



public class Upload_Documents extends Base_class{

	
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
		api.requestKYC(handle27, null, reader.getCellData(sheetName, privatekeys, 7));
		Thread.sleep(3000);


		
		//UPload Document
		UploadDocumentMessage message1 = UploadDocumentMessage.builder()
		        .userHandle(handle27) // The user handle
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).filePath(workingDir+"/TestData/jpg_image.jpg").filename("dummy file") 
		        .mimeType("image/jpeg").documentType("tax_1040").identityType("other").name("Test jpg file") .description("upload jpg file") 
		        .build();
		ApiResponse uploadResponse = api.uploadDocument(message1);

		// Success response
		System.out.println(uploadResponse.getStatusCode()); // 200
		DocumentsResponse parsedResponse = (DocumentsResponse) uploadResponse.getData();
		Assert.assertEquals(uploadResponse.getStatusCode(), 200);
		Assert.assertEquals(uploadResponse.getSuccess(), successTrue);
		Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
		Assert.assertEquals(parsedResponse.getMessage(), "File uploaded successfully.");
		Assert.assertNotNull(parsedResponse.getReferenceId());
		Assert.assertNotNull(parsedResponse.getReferenceId());
	
		
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
//		ListDocumentsResponse parsedResponse = (ListDocumentsResponse) response.getData();
//		System.out.println(parsedResponse.getSuccess()); // true
//		System.out.println(parsedResponse.getStatus()); // SUCCESS
//		System.out.println(parsedResponse.getPagination().getCurrentPage()); // 1
//		System.out.println(parsedResponse.getPagination().getReturnedCount()); // 1
//		System.out.println(parsedResponse.getPagination().getTotalCount()); // 1
//		System.out.println(parsedResponse.getPagination().getTotalPages()); // 1
//		System.out.println(parsedResponse.getDocuments().get(0).getUserHandle()); // user_handle
//		System.out.println(parsedResponse.getDocuments().get(0).getDocumentId()); // some-uuid-code
//		System.out.println(parsedResponse.getDocuments().get(0).getName()); // logo
//		System.out.println(parsedResponse.getDocuments().get(0).getFilename()); // logo
//		System.out.println(parsedResponse.getDocuments().get(0).getHash()); // yourfilehash
//		System.out.println(parsedResponse.getDocuments().get(0).getType()); // doc_green_card
//		System.out.println(parsedResponse.getDocuments().get(0).getSize()); // 211341
//		System.out.println(parsedResponse.getDocuments().get(0).getCreated()); // 2020-08-03T17:09:24.917939

		}
	

	


}
