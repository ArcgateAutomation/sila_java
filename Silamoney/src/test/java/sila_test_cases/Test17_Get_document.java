package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.DocumentsResponse;
import com.silamoney.client.domain.GetDocumentMessage;
import com.silamoney.client.domain.UploadDocumentMessage;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;
		
		public class Test17_Get_document extends Base_class{
		String documentUuid=null;	
		@Test(priority = 1)
		@Description("Verify user is able to get document with valid fields")
		public void Test_01_get_document_with_valid_fields() throws Exception {
		//Upload Document
		UploadDocumentMessage message1 = UploadDocumentMessage.builder()
		        .userHandle(handle28) // The user handle
		        .userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).filePath(workingDir+"/TestData/png_image.png").filename("png_image") 
		        .mimeType("image/png").documentType("id_drivers_permit").identityType("license").name("id") .description("upload png file").build();
		ApiResponse uploadResponse = api.uploadDocument(message1);
		DocumentsResponse parsedResponse = (DocumentsResponse) uploadResponse.getData();
		documentUuid =parsedResponse.getDocumentId();
		System.out.println(documentUuid);
		

		//Get document
		GetDocumentMessage message = GetDocumentMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).documentId(documentUuid).build();
		ApiResponse response = api.getDocument(message);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getHeaders().get("Content-Type").contains("image/png"));
		Assert.assertNotNull(response.getHeaders().get("Content-Length"));
		Assert.assertTrue(response.getHeaders().get("Content-Disposition").contains("filename=\"png_image\""));
		Assert.assertNotNull((String) response.getData());	
		}
		
		@Test(priority = 2)
		@Description("Verify user is not able to get document with empty user_handle field")
		public void Test_02_get_document_with_empty_user_handle_fields() throws Exception {
		GetDocumentMessage message = GetDocumentMessage.builder().userHandle("").userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).documentId(documentUuid).build();
		ApiResponse response = api.getDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
		}
		
		@Test(priority = 3)
		@Description("Verify user is not able to get document with unregistered user_handle field")
		public void Test_03_get_document_with_unregistereduser_handle_fields() throws Exception {
		GetDocumentMessage message = GetDocumentMessage.builder().userHandle(Handle_not_registered).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).documentId(documentUuid).build();
		ApiResponse response = api.getDocument(message);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),invalidSignErrorMsg4);
		
		}
		
		
		@Test(priority = 4)
		@Description("Verify user is not able to get document with another registered user_handle field")
		public void Test_04_get_document_with_different_user_handle_field() throws Exception {
		GetDocumentMessage message = GetDocumentMessage.builder().userHandle(handle27).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).documentId(documentUuid).build();
		ApiResponse response = api.getDocument(message);
		
		System.out.println("Test_04_get_document_with_different_user_handle_field: "+((BaseResponse)response.getData()).getMessage());
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(),"failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 8)+" is not registered to "+Handle_27+".".toLowerCase());
		
		}
		
		@Test(priority = 5)
		@Description("Verify user is not able to get document with empty document_id field")
		public void Test_05_get_document_with_empty_document_id_field() throws Exception {
		GetDocumentMessage message = GetDocumentMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).documentId("").build();
		ApiResponse response = api.getDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
		
		}

		
		@Test(priority = 6)
		@Description("Verify user is not able to get document with invalid private_key")
		public void Test_05_get_document_with_invalid_private_key() throws Exception {
		GetDocumentMessage message = GetDocumentMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 7)).documentId(documentUuid).build();
		ApiResponse response = api.getDocument(message);
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage().toLowerCase(),"failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 7)+" is not registered to "+Handle_28+".".toLowerCase());		
		
		}
		
		@Test(priority = 7)
		@Description("Verify user is not able get document with invalid document_id field")
		public void Test_06_get_document_with_invalid_document_id_field() throws Exception {
		GetDocumentMessage message = GetDocumentMessage.builder().userHandle(handle28).userPrivateKey(reader.getCellData(sheetName, privatekeys, 8)).documentId("dd29dafa-ecbc-4e6c-a89a-17c7aba20e388").build();
		ApiResponse response = api.getDocument(message);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);		
		}
		



}
