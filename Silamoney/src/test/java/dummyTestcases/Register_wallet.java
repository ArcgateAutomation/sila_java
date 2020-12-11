package dummyTestcases;


import org.testng.Assert;
import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.api.SilaApi;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.DeleteWalletMsg;
import com.silamoney.client.domain.GetSilaBalanceResponse;
import com.silamoney.client.domain.GetWalletResponse;
import com.silamoney.client.domain.GetWalletsResponse;
import com.silamoney.client.domain.SearchFilters;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;
import com.sun.source.tree.AssertTree;

import io.qameta.allure.Description;
import com.silamoney.client.domain.User;
import com.silamoney.client.domain.Wallet;
import com.silamoney.client.security.EcdsaUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;



public class Register_wallet extends Base_class {

	@Test(priority = 1)
	@Description("Check register wallet wil all valida data")
	public void Regiter_wallet_with_all_valid_data() throws Exception {
	
//	SearchFilters filters = new SearchFilters();
//			filters.setPage(1);//enter page 
//			filters.setPerPage(20);//Enyer par page number
//			filters.sortAscending(); //sorting 
//			filters.setBlockChainNetwork(null); //network like ETH
//			filters.setBlockChainAddress(null);
//			filters.setNickname(""); //send nick name
//
//			//ApiResponse response = api.getWallets("automationuserwallet.silamoney.eth", filters, "7c9f5c662eab079d6b72721cc806b2e7015205109c4303fabc0deea14ac9da3a");
//			ApiResponse response = api.getWallets("automationuserwallet.silamoney.eth", filters, "7c9f5c662eab079d6b72721cc806b2e7015205109c4303fabc0deea14ac9da3a");
//
//			
//			//  Success Response Object
//			System.out.println(response.getStatusCode()); // 200
//			System.out.println(((GetWalletsResponse)response.getData()).page);
//			System.out.println(((GetWalletsResponse)response.getData()).returnedCount);
//			System.out.println(((GetWalletsResponse)response.getData()).totalCount);
//			System.out.println(((GetWalletsResponse)response.getData()).totalPageCount);
//			System.out.println(((GetWalletsResponse)response.getData()).getWallets());
//			System.out.println(((GetWalletsResponse)response.getData()).getWallets().get(0));
//			System.out.println(((GetWalletsResponse)response.getData()).getWallets());
//			System.out.println(((GetWalletResponse)response.getData()).getWallet().getBlockChainAddress());
//			
			
//			assertEquals(200, response.getStatusCode());
//			assertNotNull(((GetWalletsResponse)response.getData()).page);
//			assertNotNull(((GetWalletsResponse)response.getData()).returnedCount);
//			assertNotNull(((GetWalletsResponse)response.getData()).totalCount);
//			assertNotNull(((GetWalletsResponse)response.getData()).totalPageCount);
//			assertNotNull(((GetWalletsResponse)response.getData()).getWallets());
//			assertNotNull(((GetWalletsResponse)response.getData()).getWallets().get(0));
			

		
//User register
//			LocalDate birthdate = new LocalDate(2000, 01, 31);
//	User user2 = new User("automationuserwallet.silamoney.eth",  firstName,  lastName,  streetAddress1, streetAddress2, city,  state, 
//	postalCode,  phone,  email, identityNumber, Utility.getuser22CryptoAddress(), birthdate.toDate(), country);
//	reader.setCellData(sheetName, privatekeys, 2, Utility.getuser22PrivateKey());
//	System.out.println(Utility.getuser22PrivateKey());
//	reader.setCellData(sheetName, cryptoAddress, 2, Utility.getuser22CryptoAddress());
//	System.out.println(Utility.getuser22CryptoAddress());
//	ApiResponse registerResponse = api.register(user2);
//	Thread.sleep(3000);
	

//			
////Register wallet
		

			
//		    Wallet wallet = api.generateWallet();
//		    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
//		    
//			ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));
//			System.out.println(registerWalletResponse.getStatusCode()); // 200
//			Thread.sleep(3000);
//			System.out.println("wallet_verification_signature: "+wallet_verification_signature);	
//			System.out.println("getStatusCode: "+registerWalletResponse.getStatusCode());
//			System.out.println("getStatusCode: "+registerWalletResponse.getSuccess());
//			System.out.println("getStatusCode: "+((BaseResponse)registerWalletResponse.getData()).getStatus());
//			System.out.println("getStatusCode: "+((BaseResponse)registerWalletResponse.getData()).getReference());
//			System.out.println("getStatusCode: "+((BaseResponse)registerWalletResponse.getData()).getMessage());
//			Thread.sleep(3000);
//			
//		}
//    Wallet wallet = api.generateWallet();
//    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
//    
//	ApiResponse registerWalletResponse = api.registerWallet("automationuserwallet.silamoney.eth", wallet, wallet_verification_signature, "7c9f5c662eab079d6b72721cc806b2e7015205109c4303fabc0deea14ac9da3a");
//	System.out.println(registerWalletResponse.getStatusCode()); // 200
//	System.out.println("wallet_verification_signature: "+wallet_verification_signature);	
//	System.out.println("getStatusCode: "+registerWalletResponse.getStatusCode());
//	System.out.println("getStatusCode: "+registerWalletResponse.getSuccess());
//	System.out.println("getStatusCode: "+((BaseResponse)registerWalletResponse.getData()).getStatus());
//	System.out.println("getStatusCode: "+((BaseResponse)registerWalletResponse.getData()).getReference());
//	System.out.println("getStatusCode: "+((BaseResponse)registerWalletResponse.getData()).getMessage());
	
	
	
//// Get wallet	
//	ApiResponse getWalletResponse = api.getWallet(handle22,  reader.getCellData(sheetName, privatekeys, 2));
//
//	// Success Response Object
//	System.out.println(getWalletResponse.getStatusCode()); // 200
//	System.out.println(((GetWalletResponse)getWalletResponse.getData()).getWallet().getNickname());
//	System.out.println(((GetWalletResponse)getWalletResponse.getData()).getWallet().getBlockChainAddress());
//	System.out.println(((GetWalletResponse)getWalletResponse.getData()).getWallet().getBlockChainNetwork());
//	System.out.println(((GetWalletResponse)getWalletResponse.getData()).getStatus());
//	System.out.println(((GetWalletResponse)getWalletResponse.getData()).getSuccess());
//	System.out.println(((GetWalletResponse)getWalletResponse.getData()).getSilaBalance());
//	
//	Assert.assertEquals(getWalletResponse.getStatusCode(), 200);
//	Assert.assertEquals(((GetWalletResponse)getWalletResponse.getData()).getStatus(),statusTrue);
//	Assert.assertTrue(((GetWalletResponse)getWalletResponse.getData()).getSuccess());
//	assertNotNull(((GetWalletResponse)getWalletResponse.getData()).getWallet().getNickname());
//	assertNotNull(((GetWalletResponse)getWalletResponse.getData()).getWallet().getBlockChainAddress());
//	assertNotNull(((GetWalletResponse)getWalletResponse.getData()).getWallet().getBlockChainNetwork());
//	Assert.assertEquals(((GetWalletResponse)getWalletResponse.getData()).getSilaBalance(), 0.0);
		
		//-----------------------------------------**---------------------------------????????????????????????????----------------------////////////////
		
//register wallet	
		Wallet wallet = api.generateWallet();
		String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
		ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));
		Thread.sleep(3000);

		
		
// Get wallet	
//		ApiResponse getWalletResponse = api.getWallet(handle22, reader.getCellData(sheetName, privatekeys, 2));
//
		// Success Response Object
//		System.out.println(getWalletResponse.getStatusCode()); // 200
//		String nickname=((GetWalletResponse)getWalletResponse.getData()).getWallet().getNickname();
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getWallet());
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).isWhitelisted());
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getSilaBalance());
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getWallet());
		
//		System.out.println(getWalletResponse.getStatusCode()); // 200
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getWallet().getNickname());
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getWallet().getBlockChainAddress());
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getWallet().getBlockChainNetwork());
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getStatus());
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getSuccess());
//		System.out.println(((GetWalletResponse)getWalletResponse.getData()).getSilaBalance());





//update wallet
//		ApiResponse response1 = api.updateWallet(handle22, "wallet_test_UPD", false, reader.getCellData(sheetName, privatekeys, 2));

//		// Success Response Object
//		System.out.println(response.getStatusCode()); // 200
//		//System.out.println(response.getData().); // 200
//		System.out.println(((BaseResponse) response.getData()).getMessage());
//		System.out.println(response.getSuccess());
//		System.out.println(((BaseResponse) response.getData()).getReference());
//		System.out.println(((BaseResponse) response.getData()).getStatus());

//Get Sila balance
		
//		ApiResponse response2 = api.silaBalance(reader.getCellData(sheetName, cryptoAddress, 2));

		// Success Object Response
//		System.out.println(response.getStatusCode()); // 200
//		System.out.println(((GetSilaBalanceResponse)response.getData()).getStatus());
//		System.out.println(((GetSilaBalanceResponse)response.getData()).getSuccess());
//		System.out.println(((GetSilaBalanceResponse)response.getData()).getAddress());
//		System.out.println(((GetSilaBalanceResponse)response.getData()).getSilaBalance());
		
//delete wallet
		
		ApiResponse response = api.deleteWallet(handle22, reader.getCellData(sheetName, privatekeys, 2));

		// Success Response Object
		System.out.println(response.getStatusCode()); // 200
		System.out.println(((BaseResponse) response.getData()).getMessage());
		System.out.println(response.getSuccess());
		System.out.println(((BaseResponse) response.getData()).getReference());
		System.out.println(((BaseResponse) response.getData()).getStatus());
		
		

	}

}
