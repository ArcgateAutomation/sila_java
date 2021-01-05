package com.silamoney.common_files;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.domain.BusinessRole;
import com.silamoney.client.domain.BusinessType;
import com.silamoney.client.domain.Environments;
import com.silamoney.client.domain.GetNaicsCategoriesResponse;
import com.silamoney.client.domain.NaicsCategoryDescription;
import com.silamoney.client.domain.SearchFilters;
import com.silamoney.client.exceptions.BadRequestException;
import com.silamoney.client.exceptions.ForbiddenException;
import com.silamoney.client.exceptions.InvalidSignatureException;
import com.silamoney.client.exceptions.ServerSideException;
import com.silamoney.client.security.EcdsaUtil;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

public class Utility {

	public static String host = Environments.SilaEnvironment.SANDBOX.getUrl();
	public static String appHandle = "sila_app_handle_test.silamoney.eth";
	public static String privateKey = "e60a5c57130f4e82782cbdb498943f31fe8f92ab96daac2cc13cbbbf9c0b4d9e";
	public static String privateKey2 = "e60a5c57130f4e82782cbdb498943f31fe8f92ab96daac2cc13cbbbf9c0b4000";
	public static String privateKey1 = "ead74b1aa51f6087d091cfac6585c79cc6fa2f6414d3c1be79b5dc7f598e368b";  //Valid key for App Handle: arcgqa1
	public static String privateKey3 = "67EAFC2CBCB512A976B2656CAF671C0BC114130D4E9A9F687E3D37EBECB5FE41"; //Valid key 

	private static String userHandle;
	/**
	 * @return String
	 */
	public static String getUserHandle() {
		userHandle = userHandle == null || userHandle.isBlank() ? "javaSDK-" + new Random().nextInt() : userHandle;
		return userHandle;
	}

	
	
	private static String user2Handle;
	/**
	 * @return String
	 */
	public static String getUser2Handle() {
		user2Handle = user2Handle == null || user2Handle.isBlank() ? "javaSDK-" + new Random().nextInt() : user2Handle;
		return user2Handle;
	}

	
	
	private static String businessHandle;
	/**
	 * @return String
	 */
	public static String getBusinessHandle() {
		businessHandle = businessHandle == null || businessHandle.isBlank() ? "javaSDK-" + new Random().nextInt()
				: businessHandle;
		return businessHandle;
	}

	
	private static String wallet_verification_signature;
	/**
	 * @return String
	 */
	public static String getWallet_verification_signature() {
		return wallet_verification_signature;
	}

	
	private static List<BusinessType> businessTypes;
	/**
	 * @return List<BusinessType>
	 */
	public static List<BusinessType> getBusinessTypes() {
		return businessTypes;
	}

	/**
	 * @param pBusinessTypes
	 */
	public static void setBusinessTypes(List<BusinessType> pBusinessTypes) {
		businessTypes = pBusinessTypes;
	}

	private static Map<String, ArrayList<NaicsCategoryDescription>> naicsCategories;
	/**
	 * Default correct business uuid
	 */
	public static String correctUuid = "9f280665-629f-45bf-a694-133c86bffd5e";

	/**
	 * Default wrong business uuid
	 */
	public static String wrongUuid = "6d933c10-fa89-41ab-b443-2e78a7cc8cac";

	/**
	 * @return Map<String, ArrayList<NaicsCategoryDescription>>
	 */
	public static Map<String, ArrayList<NaicsCategoryDescription>> getNaicsCategories() {
		return naicsCategories;
	}

	/**
	 * @param pNaicsCategories
	 */
	public static void setNaicsCategories(Map<String, ArrayList<NaicsCategoryDescription>> pNaicsCategories) {
		naicsCategories = pNaicsCategories;
	}

	/**
	 * @return NaicsCategoryDescription
	 */
	public static NaicsCategoryDescription getDefaultNaicCategoryDescription() throws IOException, InterruptedException, BadRequestException, InvalidSignatureException, ServerSideException, ForbiddenException {
		ApiResponse response = Base_class.api.getNaicsCategories();
		Map<String, ArrayList<NaicsCategoryDescription>> naicsCategories=((GetNaicsCategoriesResponse)response.getData()).getNaicsCategories();
		Utility.setNaicsCategories(naicsCategories);
		for (String key : naicsCategories.keySet()) {
			for (NaicsCategoryDescription categoryDescription : naicsCategories.get(key)) {
				return categoryDescription;
			}
		}

		return null;
	}

	private static List<BusinessRole> businessRoles;

	/**
	 * @return List<BusinessRole>
	 */
	public static List<BusinessRole> getBusinessRoles() {
		return businessRoles;
	}

	/**
	 * @param businessRoles
	 */
	public static void setBusinessRoles(List<BusinessRole> businessRoles) {
		Utility.businessRoles = businessRoles;
	}

	/**
	 * @param role
	 * @return BusinessRole
	 */
	public static BusinessRole getBusinessRole(String role) {
		for (BusinessRole businessRole : getBusinessRoles()) {
			if (businessRole.getName().equals(role)) {
				return businessRole;
			}
		}
		return null;
	}

	public static SearchFilters filters = new SearchFilters().showTimelines().setMaxSilaAmount(1300)
			.setMinSilaAmount(1000).setStatuses(new ArrayList<>() {
				private static final long serialVersionUID = -5522339729487411576L;
				{
					add(SearchFilters.StatusesEnum.PENDING);
					// add(SearchFilters.StatusesEnum.SUCCESSFUL);
					add(SearchFilters.StatusesEnum.FAILED);
					// add(SearchFilters.StatusesEnum.COMPLETE);
				}
			}).setPage(1).setPerPage(20).setTransactionTypes(new ArrayList<>() {
				private static final long serialVersionUID = -5630390615963025868L;
				{
					add(SearchFilters.TransactionTypesEnum.ISSUE);
					add(SearchFilters.TransactionTypesEnum.REDEEM);
					add(SearchFilters.TransactionTypesEnum.TRANSFER);
				}
			});

	private static String userCryptoAddress;

	/**
	 * @return String
	 */
	public static String getUserCryptoAddress() {
		if (userCryptoAddress == null || userCryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				userPrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				userCryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return userCryptoAddress;
	}

	
	private static String user2CryptoAddress;

	/**
	 * @return String
	 */
	public static String getUser2CryptoAddress() {
		if (user2CryptoAddress == null || user2CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user2PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user2CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}
		return user2CryptoAddress;
	}


	
	private static String user3CryptoAddress;

	/**
	 * @return String
	 */
	public static String getUser3CryptoAddress() {
		if (user3CryptoAddress == null || user3CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user3PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user3CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user3CryptoAddress;
	}
		
	
	
	private static String user4CryptoAddress;
	public static String getUser4CryptoAddress() {
		if (user4CryptoAddress == null || user4CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user4PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user4CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user4CryptoAddress;
	}
	
	

	private static String user5CryptoAddress;
	public static String getUser5CryptoAddress() {
		if (user5CryptoAddress == null || user5CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user5PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user5CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user5CryptoAddress;
	}
	
	
	
	private static String user6CryptoAddress;
	public static String getUser6CryptoAddress() {
		if (user6CryptoAddress == null || user6CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user6PrivateKey = privateKeyInDec.toString(16);
				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user6CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user6CryptoAddress;
	}
	
	
	
	private static String user7CryptoAddress;
	public static String getUser7CryptoAddress() {
		if (user7CryptoAddress == null || user7CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user7PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user7CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user7CryptoAddress;
	}
	
	
	
	private static String user8CryptoAddress;
	public static String getUser8CryptoAddress() {
		if (user8CryptoAddress == null || user8CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user8PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user8CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user8CryptoAddress;
	}
	
	
	
	private static String user9CryptoAddress;
	public static String getUser9CryptoAddress() {
		if (user9CryptoAddress == null || user9CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user9PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user9CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user9CryptoAddress;
	}
	
	
	
	private static String user10CryptoAddress;
	public static String getUser10CryptoAddress() {
		if (user10CryptoAddress == null || user10CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user10PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user10CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user10CryptoAddress;
	}
	
	
	

	
	private static String user11CryptoAddress;
	public static String getuser11CryptoAddress() {
		if (user11CryptoAddress == null || user11CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user11PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user11CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user11CryptoAddress;
	}
	
	
	
	private static String user12CryptoAddress;
	public static String getuser12CryptoAddress() {
		if (user12CryptoAddress == null || user12CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user12PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user12CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user12CryptoAddress;
	}
	
	
	
	private static String user13CryptoAddress;
	public static String getuser13CryptoAddress() {
		if (user13CryptoAddress == null || user13CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user13PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user13CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user13CryptoAddress;
	}
	
	
	
	private static String user14CryptoAddress;
	public static String getuser14CryptoAddress() {
		if (user14CryptoAddress == null || user14CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user14PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user14CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user14CryptoAddress;
	}
	
	
	
	private static String user15CryptoAddress;
	public static String getuser15CryptoAddress() {
		if (user15CryptoAddress == null || user15CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user15PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user15CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user15CryptoAddress;
	}
	

	private static String user16CryptoAddress;
	public static String getuser16CryptoAddress() {
		if (user16CryptoAddress == null || user16CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user16PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user16CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user16CryptoAddress;
	}
	
	
	private static String user17CryptoAddress;
	public static String getuser17CryptoAddress() {
		if (user17CryptoAddress == null || user17CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user17PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user17CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user17CryptoAddress;
	}
	
	
	
	private static String user18CryptoAddress;
	public static String getuser18CryptoAddress() {
		if (user18CryptoAddress == null || user18CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user18PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user18CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user18CryptoAddress;
	}
	

	
	private static String user19CryptoAddress;
	public static String getuser19CryptoAddress() {
		if (user19CryptoAddress == null || user19CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user19PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user19CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user19CryptoAddress;
	}
	
	
	private static String user20CryptoAddress;
	public static String getuser20CryptoAddress() {
		if (user20CryptoAddress == null || user20CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user20PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user20CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user20CryptoAddress;
	}
	
	private static String user21CryptoAddress;
	public static String getuser21CryptoAddress() {
		if (user21CryptoAddress == null || user21CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user21PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user21CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user21CryptoAddress;
	}
	
	
	private static String user22CryptoAddress;
	public static String getuser22CryptoAddress() {
		if (user22CryptoAddress == null || user22CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user22PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user22CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user22CryptoAddress;
	}
	
	
	
	private static String user23CryptoAddress;
	public static String getuser23CryptoAddress() {
		if (user23CryptoAddress == null || user23CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user23PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user23CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user23CryptoAddress;
	}
	
	
	private static String user24CryptoAddress;
	public static String getuser24CryptoAddress() {
		if (user24CryptoAddress == null || user24CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user24PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user24CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user24CryptoAddress;
	}
	
	
	
	private static String user25CryptoAddress;
	public static String getuser25CryptoAddress() {
		if (user25CryptoAddress == null || user25CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user25PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user25CryptoAddress = "0x" + aWallet.getAddress();
				//user25CryptoAddress = "0x039e8166cd0ccbe7abae3036c4c5d055bcabd582";
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user25CryptoAddress;
	}
	
	
	
	private static String user41CryptoAddress;
	public static String getuser41CryptoAddress() {
		if (user41CryptoAddress == null || user41CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user41PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user41CryptoAddress = "0x" + aWallet.getAddress();
				//user41CryptoAddress = "0x039e8166cd0ccbe7abae3036c4c5d055bcabd582";
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user41CryptoAddress;
	}
	
	
	
	private static String user26CryptoAddress;
	public static String getuser26CryptoAddress() {
		if (user26CryptoAddress == null || user26CryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				user26PrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user26CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user26CryptoAddress;
	}

	
	private static String user27CryptoAddress;
	public static String getuser27CryptoAddress() {
		if (user27CryptoAddress == null || user27CryptoAddress.isBlank()) {
			try {
				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
				user27PrivateKey = privateKeyInDec.toString(16);
				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user27CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user27CryptoAddress;
	}
	
	
	private static String user28CryptoAddress;
	public static String getuser28CryptoAddress() {
		if (user28CryptoAddress == null || user28CryptoAddress.isBlank()) {
			try {
				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
				user28PrivateKey = privateKeyInDec.toString(16);
				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user28CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user28CryptoAddress;
	}

	
	private static String user31CryptoAddress;
	public static String getuser31CryptoAddress() {
		if (user31CryptoAddress == null || user31CryptoAddress.isBlank()) {
			try {
				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
				user31PrivateKey = privateKeyInDec.toString(16);
				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user31CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user31CryptoAddress;
	}
	
	private static String user32CryptoAddress;
	public static String getuser32CryptoAddress() {
		if (user32CryptoAddress == null || user32CryptoAddress.isBlank()) {
			try {
				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
				user32PrivateKey = privateKeyInDec.toString(16);
				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user32CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user32CryptoAddress;
	}
	
	private static String user33CryptoAddress;
	public static String getuser33CryptoAddress() {
		if (user33CryptoAddress == null || user33CryptoAddress.isBlank()) {
			try {
				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
				user33PrivateKey = privateKeyInDec.toString(16);
				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user33CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user33CryptoAddress;
	}
	
	
	private static String user34CryptoAddress;
	public static String getuser34CryptoAddress() {
		if (user34CryptoAddress == null || user34CryptoAddress.isBlank()) {
			try {
				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
				user34PrivateKey = privateKeyInDec.toString(16);
				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				user34CryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return user34CryptoAddress;
	}
	
	
	private static String userCryptoAddress41Char;
	public static String getuserCryptoAddress41Char() {
		if (userCryptoAddress41Char == null || userCryptoAddress41Char.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				userPrivateKey41Char = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				String userCryptoAddressdata= aWallet.getAddress();
				userCryptoAddress41Char = "0x" + userCryptoAddressdata.substring(0, userCryptoAddressdata.length() - 1);
				
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return userCryptoAddress41Char;
	}
	
	
	private static String userCryptoAddress43Char;
	public static String getuserCryptoAddress43Char() {
		if (userCryptoAddress43Char == null || userCryptoAddress43Char.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				userPrivateKey43Char = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				userCryptoAddress43Char = "0x" + aWallet.getAddress()+"1";
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return userCryptoAddress43Char;
	}
	
	

	
	
	private static String userPrivateKey;
	/**
	 * @return String
	 */
	public static String getUserPrivateKey() {
		getUserCryptoAddress();
		return userPrivateKey;
	}

	private static String user2PrivateKey;

	/**
	 * @return String
	 */
	public static String getUser2PrivateKey() {
		getUser2CryptoAddress();
		return user2PrivateKey;
	}
	

	private static String user3PrivateKey;
	/**
	 * @return String
	 */
	public static String getUser3PrivateKey() {
		getUser3CryptoAddress();
		return user3PrivateKey;
	}
	
	

	private static String user4PrivateKey;
	/**
	 * @return String
	 */
	public static String getUser4PrivateKey() {
		getUser4CryptoAddress();
		return user4PrivateKey;
	}


	private static String user5PrivateKey;
	/**
	 * @return String
	 */
	public static String getUser5PrivateKey() {
		getUser5CryptoAddress();
		return user5PrivateKey;
	}

	
	
	private static String user6PrivateKey;
	/**
	 * @return String
	 */
	public static String getUser6PrivateKey() {
		getUser6CryptoAddress();
		return user6PrivateKey;
	}

	
	
	private static String user7PrivateKey;
	/**
	 * @return String
	 */
	public static String getUser74PrivateKey() {
		getUser7CryptoAddress();
		return user7PrivateKey;
	}

	
	
	private static String user8PrivateKey;
	/**
	 * @return String
	 */
	public static String getUser8PrivateKey() {
		getUser8CryptoAddress();
		return user8PrivateKey;
	}

	
	private static String user9PrivateKey;
	/**
	 * @return String
	 */
	public static String getUser9PrivateKey() {
		getUser9CryptoAddress();
		return user9PrivateKey;
	}

	
	private static String user10PrivateKey;
	/**
	 * @return String
	 */
	public static String getUser10PrivateKey() {
		getUser10CryptoAddress();
		return user10PrivateKey;
	}


	
	
	private static String user11PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser11PrivateKey() {
		getuser11CryptoAddress();
		return user11PrivateKey;
	}
	
	
	private static String user12PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser12PrivateKey() {
		getuser12CryptoAddress();
		return user12PrivateKey;
	}
	
	
	private static String user13PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser13PrivateKey() {
		getuser13CryptoAddress();
		return user13PrivateKey;
	}
	
	
	private static String user14PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser14PrivateKey() {
		getuser14CryptoAddress();
		return user14PrivateKey;
	}
	
	
	private static String user15PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser15PrivateKey() {
		getuser15CryptoAddress();
		return user15PrivateKey;
	}
	

	private static String user16PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser16PrivateKey() {
		getuser16CryptoAddress();
		return user16PrivateKey;
	}
	

	
	private static String user17PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser17PrivateKey() {
		getuser17CryptoAddress();
		return user17PrivateKey;
	}
	
	
	private static String user18PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser18PrivateKey() {
		getuser18CryptoAddress();
		return user18PrivateKey;
	}	
	
	private static String user19PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser19PrivateKey() {
		getuser19CryptoAddress();
		return user19PrivateKey;
	}
	
	
	private static String user20PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser20PrivateKey() {
		getuser20CryptoAddress();
		return user20PrivateKey;
	}
	
	
	private static String user21PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser21PrivateKey() {
		getuser21CryptoAddress();
		return user21PrivateKey;
	}
	
	
	private static String user22PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser22PrivateKey() {
		getuser22CryptoAddress();
		return user22PrivateKey;
	}
	
	
	
	private static String user23PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser23PrivateKey() {
		getuser23CryptoAddress();
		return user23PrivateKey;
	}

	
	private static String user24PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser24PrivateKey() {
		getuser24CryptoAddress();
		return user24PrivateKey;
	}

	
	private static String user25PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser25PrivateKey() {
		getuser25CryptoAddress();
		return user25PrivateKey;
	}
	
	
	
	private static String user41PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser41PrivateKey() {
		getuser41CryptoAddress();
		return user41PrivateKey;
	}
	
	
	private static String user26PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser26PrivateKey() {
		getuser26CryptoAddress();
		return user26PrivateKey;
	}	
	

	private static String user27PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser27PrivateKey() {
		getuser27CryptoAddress();
		return user27PrivateKey;
	}	
	

	private static String user28PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser28PrivateKey() {
		getuser28CryptoAddress();
		return user28PrivateKey;
	}	
	private static String user31PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser31PrivateKey() {
		getuser31CryptoAddress();
		return user31PrivateKey;
	}	
	
	private static String user32PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser32PrivateKey() {
		getuser32CryptoAddress();
		return user32PrivateKey;
	}	
	
	private static String user33PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser33PrivateKey() {
		getuser33CryptoAddress();
		return user33PrivateKey;
	}	
	
	private static String user34PrivateKey;
	/**
	 * @return String
	 */
	public static String getuser34PrivateKey() {
		getuser34CryptoAddress();
		return user34PrivateKey;
	}	
	
	
	
	private static String userPrivateKey41Char;
	/**
	 * @return String
	 */
	public static String getuserPrivateKey41Char() {
		getuserCryptoAddress41Char();
		return userPrivateKey41Char;
	}
	
	
	private static String userPrivateKey43Char;
	/**
	 * @return String
	 */
	public static String getuserPrivateKey43Char() {
		getuserCryptoAddress43Char();
		return userPrivateKey43Char;
	}
	

	
	
	private static String businessCryptoAddress;

	/**
	 * @return String
	 */
	public static String getBusinessCryptoAddress() {
		if (businessCryptoAddress == null || businessCryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				businessPrivateKey = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				businessCryptoAddress = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return businessCryptoAddress;
	}
	
//	private static String businessCryptoAddress;
//
//	/**
//	 * @return String
//	 */
//	public static String getBusinessCryptoAddress() {
//		if (businessCryptoAddress == null || businessCryptoAddress.isBlank()) {
//			try {
//
//				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
//				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
//
//				businessPrivateKey = privateKeyInDec.toString(16);
//
//				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
//				businessCryptoAddress = "0x" + aWallet.getAddress();
//			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
//					| CipherException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return businessCryptoAddress;
//	}

	
	private static String businessCryptoAddress_1;

	/**
	 * @return String
	 */
	public static String getbusinessCryptoAddress_1() {
		if (businessCryptoAddress_1 == null || businessCryptoAddress_1.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				businessPrivateKey_1 = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				businessCryptoAddress_1 = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return businessCryptoAddress_1;
	}
	
	
	private static String businessCryptoAddress_2;

	/**
	 * @return String
	 */
	public static String getBusinessCryptoAddress_2() {
		if (businessCryptoAddress_2 == null || businessCryptoAddress_2.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				businessPrivateKey_2 = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				businessCryptoAddress_2 = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return businessCryptoAddress_2;
	}
	
	
	private static String businessCryptoAddress_3;

	/**
	 * @return String
	 */
	public static String getBusinessCryptoAddress_3() {
		if (businessCryptoAddress_3 == null || businessCryptoAddress_3.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				businessPrivateKey_3 = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				businessCryptoAddress_3 = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return businessCryptoAddress_3;
	}
	

	
	private static String businessCryptoAddress_4;

	/**
	 * @return String
	 */
	public static String getBusinessCryptoAddress_4() {
		if (businessCryptoAddress_4 == null || businessCryptoAddress_4.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				businessPrivateKey_4 = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				businessCryptoAddress_4 = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return businessCryptoAddress_5;
	}
	
	
	private static String businessCryptoAddress_5;

	/**
	 * @return String
	 */
	public static String getBusinessCryptoAddress_5() {
		if (businessCryptoAddress_5 == null || businessCryptoAddress_5.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				businessPrivateKey_5 = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				businessCryptoAddress_5 = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return businessCryptoAddress_5;
	}
	
	
	private static String businessCryptoAddress_6;

	/**
	 * @return String
	 */
	public static String getBusinessCryptoAddress_6() {
		if (businessCryptoAddress_6 == null || businessCryptoAddress_6.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				businessPrivateKey_6 = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				businessCryptoAddress_6 = "0x" + aWallet.getAddress();
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return businessCryptoAddress_6;
	}
	
	
	
	private static String businessPrivateKey;

	/**
	 * @return String
	 */
	public static String getBusinessPrivateKey() {
		getBusinessCryptoAddress();
		return businessPrivateKey;
	}
	

	
	private static String businessPrivateKey_1;

	/**
	 * @return String
	 */
	public static String getbusinessPrivateKey_1() {
		getBusinessCryptoAddress();
		return businessPrivateKey_1;
	}
	
	
	private static String businessPrivateKey_2;

	/**
	 * @return String
	 */
	public static String getBusinessPrivateKey_2() {
		getBusinessCryptoAddress_2();
		return businessPrivateKey_2;
	}
	
	
	
	private static String businessPrivateKey_3;

	/**
	 * @return String
	 */
	public static String getBusinessPrivateKey_3() {
		getBusinessCryptoAddress_3();
		return businessPrivateKey_3;
	}
	
	
	private static String businessPrivateKey_4;

	/**
	 * @return String
	 */
	public static String getBusinessPrivateKey_4() {
		getBusinessCryptoAddress_4();
		return businessPrivateKey_4;
	}
	
	
	private static String businessPrivateKey_5;

	/**
	 * @return String
	 */
	public static String getBusinessPrivateKey_5() {
		getBusinessCryptoAddress_5();
		return businessPrivateKey_5;
	}
	
	
	
	private static String businessPrivateKey_6;

	/**
	 * @return String
	 */
	public static String getBusinessPrivateKey_6() {
		getBusinessCryptoAddress_6();
		return businessPrivateKey_6;
	}
	

	private static String blockchain_address;

	/**
	 * @return String
	 */
	public static String registerWallet() {
		if (blockchain_address == null || userCryptoAddress.isBlank()) {
			try {

				ECKeyPair ecKeyPair = Keys.createEcKeyPair();
				BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

				wallet_verification_signature = privateKeyInDec.toString(16);

				WalletFile aWallet = Wallet.createLight(UUID.randomUUID().toString(), ecKeyPair);
				blockchain_address = "0x" + aWallet.getAddress();
				// System.out.println("wallet_verification_signature >>> " +
				// wallet_verification_signature);
			} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
					| CipherException e) {
				e.printStackTrace();
			}
		}

		return blockchain_address;
	}

	private static String plaidToken;

	/**
	 * @return String
	 */
	public static String getPlaidToken() {
		try {
			plaidToken = plaidToken == null || plaidToken.isBlank() ? PlaidTokenHelper.getPlaidToken() : plaidToken;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return plaidToken;
	}

	
	
	private static String businessOwnerToken;

	/**
	 * @return the businessOwnerToken
	 */
	public static String getBusinessOwnerToken() {
		return businessOwnerToken;
	}

	/**
	 * @param businessOwnerToken the businessOwnerToken to set
	 */
	public static void setBusinessOwnerToken(String businessOwnerToken) {
		Utility.businessOwnerToken = businessOwnerToken;
	}
	

	

	

}