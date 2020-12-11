package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.GetEntitiesResponse;
import com.silamoney.common_files.Base_class;
import io.qameta.allure.Description;

public class Get_entities extends Base_class{
	

	
	@Test(priority = 1)
	@Description("Get entities with all valid details")
	public void test_01_Get_entities_with_valid_details() throws Exception {	  
		int page = 10;
		int perPage = 5;
		ApiResponse response = api.getEntities(null, page, perPage);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getCurrentPage(), page);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getReturnedCount(), perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalCount() >= page *perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalPages()>=1);
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getBusinesses().size());
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getIndividuals().size());
	
	}

	
	
	@Test(priority = 2)
	@Description("Get entities with page_value as 0")
	public void test_02_Get_entity_for_individual_user_with_page_value_0() throws Exception {	  
		int page = 0;
		int perPage = 5;
		ApiResponse response = api.getEntities(null, page, perPage);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getCurrentPage(), page);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getReturnedCount(), perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalCount() >= page *perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalPages()>=1);
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getBusinesses().size());
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getIndividuals().size());

	
	}


	@Test(priority = 3)
	@Description("Get entities with page_value as null")
	public void test_03_Get_entity_for_individual_user_without_page_value() throws Exception {	  
		int page = 0;
		int perPage = 5;
		ApiResponse response = api.getEntities(null, null, perPage);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getCurrentPage() >= 1);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getReturnedCount(), perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalCount() >= page *perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalPages()>=1);
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getBusinesses().size());
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getIndividuals().size());
	
	}
	
	
	@Test(priority = 4)
	@Description("Get entities with patpage_value as 0")
	public void test_04_Get_entities_with_perPage_page_value_0() throws Exception {	  
		int page = 10;
		int perPage = 0;
		ApiResponse response = api.getEntities(null, page, perPage);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getCurrentPage(), page);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getReturnedCount() >0);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalCount() >= page *perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalPages()>=1);
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getBusinesses().size());
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getIndividuals().size());
	
	}
	
	
	@Test(priority = 5)
	@Description("Get entities with patpage_value as null")
	public void test_05_Get_entity_for_individual_user_without_perPage_page_value() throws Exception {	  
		int page = 10;
		int perPage = 0;
		ApiResponse response = api.getEntities(null, page, null);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getCurrentPage(), page);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getReturnedCount() >0);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalCount() >= page *perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalPages()>=1);
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getBusinesses().size());
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getIndividuals().size());
	
	}
	
	
	@Test(priority = 6)
	@Description("Get entities with entity_type as individual")
	public void test_06_Get_entities_with_entity_type_as_individual() throws Exception {	  
		int page = 10;
		int perPage = 20;
		ApiResponse response = api.getEntities("individual", page, perPage);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getCurrentPage(), page);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getReturnedCount(), perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalCount() >= page *perPage);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalPages()>=1);
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getBusinesses().size());
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getIndividuals().size());
		
	
	}
	
	
	@Test(priority = 7)
	@Description("Get entities with entity_type as business")
	public void test_07_Get_entities_with_entity_type_as_business() throws Exception {	  
		int page = 10;
		int perPage = 20;
		ApiResponse response = api.getEntities("business", page, perPage);
	
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(((GetEntitiesResponse)response.getData()).getPagination().getCurrentPage(), page);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getReturnedCount() >=0);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalCount() >=0);
		Assert.assertTrue(((GetEntitiesResponse)response.getData()).getPagination().getTotalPages()>=1);
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getBusinesses().size());
		Assert.assertNotNull(((GetEntitiesResponse)response.getData()).getEntities().getIndividuals().size());
		

		}


	
	@Test(priority = 8)
	@Description("Get entities with invalid entity_type")
	public void test_08_Get_entities_with_invalid_entity_type() throws Exception {	  
		int page = 10;
		int perPage = 20;
		ApiResponse response = api.getEntities("testentity", page, perPage);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	
	
		}
	
	
	
	@Test(priority = 9)
	@Description("Get entities with empty entity_type")
	public void test_09_Get_entities_with_empty_entity_type() throws Exception {	  
		int page = 10;
		int perPage = 20;
		ApiResponse response = api.getEntities("", page, perPage);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse) response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	
	
		}
	
	}

