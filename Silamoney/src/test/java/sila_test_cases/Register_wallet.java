package sila_test_cases;

import java.math.BigInteger;
import java.util.UUID;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletFile;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.domain.Wallet;
import com.silamoney.client.security.EcdsaUtil;
import com.silamoney.common_files.Base_class;

import io.qameta.allure.Description;

public class Register_wallet extends Base_class{

		@Test(priority = 1)
	@Description("Register wallet wil all valida data")
	public void Test_01_Regiter_wallet_with_all_valid_data() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", wallet_name);
	
	String getBlockChainAddress_key=wallet.getBlockChainAddress();
	System.out.println("getBlockChainAddress_key: "+getBlockChainAddress_key);
	String walletVerificationSignature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, walletVerificationSignature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), successStatusCode);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successTrue);
	Assert.assertNotNull(((BaseResponse) registerWalletResponse.getData()).getReference());
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getMessage(), "Blockchain address "+wallet.getBlockChainAddress()+" registered.");
	
	}
	
	@Test(priority = 2)
	@Description("Verify when user try to register empty user_handle")
	public void Test_02_Regiter_wallet_with_empty_user_handle() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", "generated wallet");
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
	ApiResponse registerWalletResponse = api.registerWallet("", wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 400);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getStatus(),statusFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage(), validationErrorMsg);


	}
	
	
	@Test(priority = 3)
	@Description("Verify when user try to register unregistered user_handle")
	public void Test_03_Regiter_wallet_with_unregistered_user_handle() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", "generated wallet");
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
	ApiResponse registerWalletResponse = api.registerWallet(handleNotRegistered, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 403);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getStatus(),statusFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage(),invalidSignErrorMsg4);

	}
	
	
	@Test(priority = 4)
	@Description("Verify when user try to register empty wallet_verification_signature")
	public void Test_04_Regiter_wallet_with_empty_wallet_verification_signature() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", "generated wallet");
    //String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, "", reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 400);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getStatus(),statusFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage(),validationErrorMsg);

	}
	
	@Test(priority = 5)
	@Description("Verify when user try to register invalid wallet_verification_signature")
	public void Test_05_Regiter_wallet_with_invalid_wallet_verification_signature() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", "generated wallet");
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature+"1", reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 400);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getStatus(),statusFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage(),validationErrorMsg);

	}
	
	
	@Test(priority = 6)
	@Description("Verify when user try to register invalid private_key")
	public void Test_06_Regiter_wallet_with_invalid_private_key() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", "generated wallet");
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());
	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 7));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 403);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getStatus(),statusFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage().toLowerCase(),"failed to authenticate user signature. the derived address "+reader.getCellData(sheetName, cryptoAddress, 7)+" is not registered to "+Handle_22+".".toLowerCase());

	}
	
	
	@Test(priority = 7)
	@Description("Register wallet with empty block_chain_network")
	public void test_07_register_wallet_with_empty_block_chain_network() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "", "generated wallet");
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());

	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 400);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage(),validationErrorMsg);

	}
	
	
	@Test(priority = 8)
	@Description("Register wallet with another block_chain_networkk")
	public void test_008_register_wallet_with_another_block_chain_network() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETS", "generated wallet");
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());

	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 400);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage(),validationErrorMsg);

	}
	
	@Test(priority = 9)
	@Description("Register wallet with empty block_chain_address")
	public void test_09_register_wallet_with_empty_block_chain_address() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	Wallet wallet =new Wallet("", privateKeyInDec.toString(16), "ETH", "generated wallet");
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());

	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 400);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage(),validationErrorMsg);

	}
	
	@Test(priority = 10)
	@Description("Register wallet with empty nick_name")
	public void test_010_register_wallet_with_empty_nick_namek() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", "");
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());

	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), successStatusCode);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successTrue);
	Assert.assertNotNull(((BaseResponse) registerWalletResponse.getData()).getReference());
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getMessage(), "Blockchain address "+wallet.getBlockChainAddress()+" registered.");

	}
	
	
	
	@Test(priority = 11)
	@Description("Register wallet with 40 characters nick_name")
	public void test_11_register_wallet_with_40_characters_nick_namek() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", textWith40Chars);
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());

	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), successStatusCode);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successTrue);
	Assert.assertNotNull(((BaseResponse) registerWalletResponse.getData()).getReference());
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getStatus(), statusTrue);
	Assert.assertEquals(((BaseResponse) registerWalletResponse.getData()).getMessage(), "Blockchain address "+wallet.getBlockChainAddress()+" registered.");

	}
	
	
	@Test(priority = 12)
	@Description("Register wallet with 41 characters nick_name")
	public void test_12_register_wallet_with_41_characters_nick_namek() throws Exception {
	ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	WalletFile aWallet = org.web3j.crypto.Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
	Wallet wallet =new Wallet("0x" + aWallet.getAddress(), privateKeyInDec.toString(16), "ETH", textWith41Chars);
    String wallet_verification_signature = EcdsaUtil.sign(wallet.getBlockChainAddress(), wallet.getPrivateKey());

	ApiResponse registerWalletResponse = api.registerWallet(handle22, wallet, wallet_verification_signature, reader.getCellData(sheetName, privatekeys, 2));

	Assert.assertEquals(registerWalletResponse.getStatusCode(), 400);
	Assert.assertEquals(registerWalletResponse.getSuccess(), successFalse);
	Assert.assertEquals(((BaseResponse)registerWalletResponse.getData()).getMessage(),validationErrorMsg);

	}
	
	



	
	



}
