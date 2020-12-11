package sila_test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.GetWalletsResponse;
import com.silamoney.client.domain.SearchFilters;
import com.silamoney.common_files.Base_class;

import io.qameta.allure.Description;

public class Get_wallets extends Base_class {
		String Wallets_handle="automationuserwallet.silamoney.eth";
		String Wallets_private_key="7c9f5c662eab079d6b72721cc806b2e7015205109c4303fabc0deea14ac9da3a";
		@Test(priority = 1)
		@Description("Get wallet details with all valid data")
		public void test_01_get_wallet_details_with_valid_data() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress("0x97c67ceeca771d7d5ed44e105dab1975c8fd53fa");
		filters.setNickname("generated wallet"); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, Wallets_private_key);		

		int Act_page=((GetWalletsResponse)response.getData()).page;
		int Act_returnedCounte=((GetWalletsResponse)response.getData()).returnedCount;
		int Act_totalCount=((GetWalletsResponse)response.getData()).totalCount;
		int Act_totalPageCount=((GetWalletsResponse)response.getData()).totalPageCount;
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(Act_page, 1);
		Assert.assertEquals(Act_returnedCounte, 1);
		Assert.assertEquals(Act_totalCount, 1);
		Assert.assertEquals(Act_totalPageCount, 1);
		}
		
		
		@Test(priority = 2)
		@Description("Verify get wallets when page value is 0")
		public void test_02_get_wallets_page_count_zero() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(0);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress("0x97c67ceeca771d7d5ed44e105dab1975c8fd53fa");
		filters.setNickname("generated wallet"); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, Wallets_private_key);
		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

	}
		
		
		@Test(priority = 3)
		@Description("Verify get wallet without Filter is field")
		public void test_03_get_wallets_empty_filter_field() throws Exception {
		ApiResponse response = api.getWallets(Wallets_handle, null, Wallets_private_key);
		
		int Act_page=((GetWalletsResponse)response.getData()).page;
		int Act_returnedCounte=((GetWalletsResponse)response.getData()).returnedCount;
		int Act_totalCount=((GetWalletsResponse)response.getData()).totalCount;
		int Act_totalPageCount=((GetWalletsResponse)response.getData()).totalPageCount;
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(Act_page, 1);
		Assert.assertEquals(Act_returnedCounte, 20);
		Assert.assertEquals(Act_totalCount, 29);
		Assert.assertEquals(Act_totalPageCount, 2);
	}
		
		@Test(priority = 4)
		@Description("Verify get wallets when setPerPage value is 0")
		public void test_04_get_wallets_with_setPerPage_value_as_0() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(0);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress("0x97c67ceeca771d7d5ed44e105dab1975c8fd53fa");
		filters.setNickname("generated wallet"); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, Wallets_private_key);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(), statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);

	}

		@Test(priority = 5)
		@Description("Verify get wallets when blockchain network is invalid")
		public void test_05_get_wallets_invalid_blockchain_network() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETS"); 
		filters.setBlockChainAddress("0x97c67ceeca771d7d5ed44e105dab1975c8fd53fa");
		filters.setNickname("generated wallet"); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, Wallets_private_key);

		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}
		
		

		@Test(priority = 6)
		@Description("Verify get wallet when block chain address is empty")
		public void test_06_get_wallets_empty_blockchain_address() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress(null);
		filters.setNickname("generated wallet"); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, Wallets_private_key);
		
		int Act_page=((GetWalletsResponse)response.getData()).page;
		int Act_returnedCounte=((GetWalletsResponse)response.getData()).returnedCount;
		int Act_totalCount=((GetWalletsResponse)response.getData()).totalCount;
		int Act_totalPageCount=((GetWalletsResponse)response.getData()).totalPageCount;
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(Act_page, 1);
		Assert.assertEquals(Act_returnedCounte, 20);
		Assert.assertEquals(Act_totalCount, 28);
		Assert.assertEquals(Act_totalPageCount, 2);
	}

		
		
		@Test(priority = 7)
		@Description("Verify get wallet when nick name is invalid for wallet user")
		public void test_07_get_wallets_invalid_nickname() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress("0x97c67ceeca771d7d5ed44e105dab1975c8fd53fa");
		filters.setNickname("TESTUSER"); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, Wallets_private_key);

		int Act_page=((GetWalletsResponse)response.getData()).page;
		int Act_returnedCounte=((GetWalletsResponse)response.getData()).returnedCount;
		int Act_totalCount=((GetWalletsResponse)response.getData()).totalCount;
		int Act_totalPageCount=((GetWalletsResponse)response.getData()).totalPageCount;
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(Act_page, 1);
		Assert.assertEquals(Act_returnedCounte, 0);
		Assert.assertEquals(Act_totalCount, 0);
		Assert.assertEquals(Act_totalPageCount, 1);

	}
		
		
		@Test(priority = 8)
		@Description("Verify get wallet when name is empty")
		public void test_08_get_wallets_empty_nickname() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress("0x97c67ceeca771d7d5ed44e105dab1975c8fd53fa");
		filters.setNickname(null); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, Wallets_private_key);
		
		int Act_page=((GetWalletsResponse)response.getData()).page;
		int Act_returnedCounte=((GetWalletsResponse)response.getData()).returnedCount;
		int Act_totalCount=((GetWalletsResponse)response.getData()).totalCount;
		int Act_totalPageCount=((GetWalletsResponse)response.getData()).totalPageCount;
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(Act_page, 1);
		Assert.assertEquals(Act_returnedCounte, 1);
		Assert.assertEquals(Act_totalCount, 1);
		Assert.assertEquals(Act_totalPageCount, 1);
	}
		
		
		@Test(priority = 9)
		@Description("Verify get wallet when user_handle is empty")
		public void test_09_get_wallets_empty_user_handle() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress("0x97c67ceeca771d7d5ed44e105dab1975c8fd53fa");
		filters.setNickname("generated wallet"); 

		ApiResponse response = api.getWallets("", filters, Wallets_private_key);
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getSuccess(), successFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getStatus(),statusFalse);
		Assert.assertEquals(((BaseResponse)response.getData()).getMessage(),validationErrorMsg);
	}

		
		@Test(priority = 10)
		@Description("Verify get wallet with another crypto key")
		public void test_10_get_wallets_with_another_crpto_key() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress("0x97c67ceeca771d7d5ed44e105dab1975c8fd53fa");
		filters.setNickname("generated wallet"); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, reader.getCellData(sheetName, privatekeys, 7));
		System.out.println(reader.getCellData(sheetName, privatekeys, 7));
		Assert.assertEquals(response.getStatusCode(), 403);
		Assert.assertEquals(response.getSuccess(), successFalse);
	}
		
		
		@Test(priority = 11)
		@Description("Verify get wallet with not available wallets BlockChainAddress")
		public void test_11_get_walletsoop_with_invalid_BlockChainAddress() throws Exception {
		SearchFilters filters = new SearchFilters();
		filters.setPage(1);
		filters.setPerPage(20);
		filters.sortAscending();  
		filters.setBlockChainNetwork("ETH"); 
		filters.setBlockChainAddress("0xea298a251045d7295e625bb98ed382d525fdac68");
		filters.setNickname("generated wallet"); 

		ApiResponse response = api.getWallets(Wallets_handle, filters, Wallets_private_key);
		int Act_page=((GetWalletsResponse)response.getData()).page;
		int Act_returnedCounte=((GetWalletsResponse)response.getData()).returnedCount;
		int Act_totalCount=((GetWalletsResponse)response.getData()).totalCount;
		int Act_totalPageCount=((GetWalletsResponse)response.getData()).totalPageCount;
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getSuccess(), successTrue);
		Assert.assertEquals(Act_page, 1);
		Assert.assertEquals(Act_returnedCounte, 0);
		Assert.assertEquals(Act_totalCount, 0);
		Assert.assertEquals(Act_totalPageCount, 1);
	}





}
