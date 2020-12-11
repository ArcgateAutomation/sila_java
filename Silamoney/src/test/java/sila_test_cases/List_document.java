package sila_test_cases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.ListDocumentsMessage;
import com.silamoney.client.domain.ListDocumentsResponse;
import com.silamoney.client.domain.PaginationMessage;
import com.silamoney.common_files.Base_class;

import io.qameta.allure.Description;

public class List_document extends Base_class{
	List<String> docTypes = new ArrayList<String>();
	 
	 
	@Test(priority = 1)
	@Description("Verify get list document with valid fields")
	public void Test_01_get_list_document_with_fields() throws Exception {	
	docTypes.add("tax_1040");	
	ListDocumentsMessage message = ListDocumentsMessage.builder() .userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("dummy file").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
	 api.listDocuments(message);

	PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
	ApiResponse response = api.listDocuments(message, pagination);
	ListDocumentsResponse parsedResponse = (ListDocumentsResponse) response.getData();
	
	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
	Assert.assertEquals(parsedResponse.getDocuments().get(0).getName(),"Test jpg file");
	Assert.assertEquals(parsedResponse.getDocuments().get(0).getFilename(),"dummy file");
	Assert.assertEquals(parsedResponse.getDocuments().get(0).getType(),"tax_1040");
	Assert.assertEquals(parsedResponse.getPagination().getCurrentPage(),1);
	Assert.assertEquals(parsedResponse.getPagination().getReturnedCount(), 2);
	Assert.assertEquals(parsedResponse.getPagination().getTotalCount(),2);
	Assert.assertEquals(parsedResponse.getPagination().getTotalPages(),1);
	Assert.assertNotNull(parsedResponse.getDocuments().get(0).getHash());
	Assert.assertNotNull(parsedResponse.getDocuments().get(0).getDocumentId());
	Assert.assertNotNull(parsedResponse.getDocuments().get(0).getCreated());
	
	}
	
	@Test(priority = 2)
		@Description("Verify user is not able to get list document with empty user_handle field")
		public void Test_02_get_list_document_with_empty_user_handle_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle("").userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
	}
	
		@Test(priority = 3)
		@Description("Verify user is not able to get list document with unregistered user_handle field")
		public void Test_03_get_list_document_with_unregistered_user_handle_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(Handle_not_registered).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),invalidSignErrorMsg4);
		
	}
		
		@Test(priority = 4)
		@Description("Verify user is not able to get list document with different registered user_handle field")
		public void Test_04_get_list_document_with_different_user_handle_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
		
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(),"failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 8)+" is not registered to "+Handle_27+".".toLowerCase());
		
	}

		
		
		
		
		@Test(priority = 5)
		@Description("Verify user is able to get list document without search field")
		public void Test_05_get_list_document_without_search_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search(null).sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);

		}

		
		
		@Test(priority = 6)
		@Description("Verify user is not able to get list document with empty start_date")
		public void Test_06_get_list_document_with_empty_start_date() throws Exception {	
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("name").docTypes(docTypes).startDate(null).endDate(LocalDate.now().minusDays(2)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		
	}

		
		
		@Test(priority = 7)
		@Description("Verify user is not able to get list document with empty end_date")
		public void Test_07_get_list_document_with_empty_end_date() throws Exception {	
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(null).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		
	}
	
//	@Test(priority = 8)
//		@Description("Verify user is not able to get list document when start date is a future date")
//		public void Test_08_get_list_document_when_start_date_is_a_future_date() throws IllegalAccessException, Throwable, Exception{
//		docTypes.add("id_drivers_permit");
//		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8))
//				.search("png_image").sortBy("name").docTypes(docTypes).startDate(LocalDate.now().plusDays(01)).endDate(LocalDate.now()).build();
//		api.listDocuments(message);
//		System.out.println(LocalDate.now().plusDays(2));
//		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
//		ApiResponse response = api.listDocuments(message, pagination);
//		
//		Assert.assertEquals(response.getStatusCode(), 400);
//		Assert.assertEquals(response.getSuccess(), successFalse);
//		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
//		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
//	
//		
//	}
	
	
//	@Test(priority = 9)
//		@Description("Verify user is not able to get list document with end_date before then end_date")
//		public void Test_09_get_list_document_with_end_date_before_than_start_date() throws Exception {	
//		docTypes.add("id_drivers_permit");
//		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().minusDays(2)).build();
//		api.listDocuments(message);
//		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
//		ApiResponse response = api.listDocuments(message, pagination);
//		
//		System.out.println(response.getStatusCode());
//		Assert.assertEquals(response.getStatusCode(), 400);
//		Assert.assertEquals(response.getSuccess(), successFalse);
//		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
//		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
//		
//	}
//	
	
	
	@Test(priority = 10)
	@Description("Verify user is not able to get list document with same end_date and end_date")
	public void Test_10_get_list_document_with_same_end_date_and_start_date() throws Exception {	
	docTypes.add("id_drivers_permit");
	ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now()).build();
	api.listDocuments(message);
	PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
	ApiResponse response = api.listDocuments(message, pagination);
	
	Assert.assertEquals(response.getStatusCode(), 200);
	Assert.assertEquals(response.getSuccess(), successTrue);
	
}
	
	
	@Test(priority = 11)
		@Description("Verify user is able to get list document with empty doc_types field")
		public void Test_11_get_list_document_with_empty_doc_types_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("name").docTypes(null).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		// With pagination
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		//Assert.assertEquals(parsedResponse1.getStatus(), statusTrue);
		
		// Success Response
	//	System.out.println(response.getStatusCode()); // 200
	//
	//	System.out.println(parsedResponse.getSuccess()); // true
	//	System.out.println(parsedResponse.getStatus()); // SUCCESS
	//	System.out.println(parsedResponse.getPagination().getCurrentPage()); // 1
	//	System.out.println(parsedResponse.getPagination().getReturnedCount()); // 1
	//	System.out.println(parsedResponse.getPagination().getTotalCount()); // 1
	//	System.out.println(parsedResponse.getPagination().getTotalPages()); // 1
	//	System.out.println(parsedResponse.getDocuments().get(0).getUserHandle()); // user_handle
	//	System.out.println(parsedResponse.getDocuments().get(0).getDocumentId()); // some-uuid-code
	//	System.out.println(parsedResponse.getDocuments().get(0).getName()); // logo
	//	System.out.println(parsedResponse.getDocuments().get(0).getFilename()); // logo
	//	System.out.println(parsedResponse.getDocuments().get(0).getHash()); // yourfilehash
	//	System.out.println(parsedResponse.getDocuments().get(0).getType()); // doc_green_card
	//	System.out.println(parsedResponse.getDocuments().get(0).getSize()); // 211341
	//	System.out.println(parsedResponse.getDocuments().get(0).getCreated()); // 2020-08-03T17:09:24.917939
		
	//	Assert.assertEquals(response.getStatusCode(), 200);
	//	Assert.assertEquals(response.getSuccess(), successTrue);
	//	Assert.assertEquals(parsedResponse.getStatus(), statusTrue);
	//	Assert.assertEquals(parsedResponse.getDocuments().get(0).getName(),"Test jpg file");
	//	Assert.assertEquals(parsedResponse.getDocuments().get(0).getFilename(),"png_image");
	//	Assert.assertEquals(parsedResponse.getDocuments().get(0).getType(),"id_drivers_permit");
	//	Assert.assertEquals(parsedResponse.getPagination().getCurrentPage(),1);
	//	Assert.assertEquals(parsedResponse.getPagination().getReturnedCount(),3);
	//	Assert.assertEquals(parsedResponse.getPagination().getTotalCount(),3);
	//	Assert.assertEquals(parsedResponse.getPagination().getTotalPages(),1);
	//	Assert.assertNotNull(parsedResponse.getDocuments().get(0).getHash());
	//	Assert.assertNotNull(parsedResponse.getDocuments().get(0).getDocumentId());
	//	Assert.assertNotNull(parsedResponse.getDocuments().get(0).getCreated());
		
	//	Assert.assertEquals(response.getStatusCode(), 200);
	//	Assert.assertEquals(response.getSuccess(), successTrue);
	//	Assert.assertEquals(parsedResponse1.getStatus(), statusTrue);
		}
	
	
	@Test(priority = 12)
		@Description("Verify user is able to get list document with unavailable search name field")
		public void Test_12_get_list_document_with_unavailable_search_name_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("test_document_name").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
	
	}
	
	@Test(priority = 13)
		@Description("Verify user is able to get list document with Partial search name field")
		public void Test_13_get_list_document_with_partial_search_name_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("g_im").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
	}
	

	
	@Test(priority = 14)
		@Description("Verify user is able to get list document with empty sort_by field")
		public void Test_14_get_list_document_with_empty_sort_by_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy(null).docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
	}
	
	@Test(priority = 15)
		@Description("Verify user is not able to get list document with invalid sort_by field")
		public void Test_15_get_list_document_with_invalid_sort_by_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("test").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
	
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
	}
	
	
		@Test(priority = 16)
		@Description("Verify user is able to get list document with date sort_by field")
		public void Test_16_get_list_document_with_date_sort_by_field() throws Exception {
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).search("png_image").sortBy("date").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		
	}
		
		
		
	
	@Test(priority = 17)
		@Description("Verify user is not able to get list document with another private key")
		public void Test_17_get_list_document_with_invalid_private_key_field() throws Exception {	
		docTypes.add("id_drivers_permit");
		ListDocumentsMessage message = ListDocumentsMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).search("png_image").sortBy("name").docTypes(docTypes).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).build();
		api.listDocuments(message);
	
		PaginationMessage pagination = PaginationMessage.builder().page(1).perPage(40).build();
		ApiResponse response = api.listDocuments(message, pagination);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(), "failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 7)+" is not registered to "+Handle_28+".".toLowerCase());
	}
	
	
	
	




	
}
