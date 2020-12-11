package com.silamoney.common_files;



import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.Date;
import java.util.UUID;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletFile;

import com.silamoney.client.api.SilaApi;
import com.silamoney.client.domain.Environments;
import com.silamoney.client.domain.Wallet;


public class Base_class extends ValidationData{

	protected static String workingDir = System.getProperty("user.dir");
	public static Xls_Reader1 reader = new  Xls_Reader1(workingDir+"/TestData/datafile.xlsx");
	public static String sheetName ="TestData";
	public static String privatekeys ="privatekeys";
	public static String cryptoAddress ="cryptoAddress";
	
	


	LocalDate localDate = LocalDate.now();
	protected int year = localDate.getYear();
	protected int month = localDate.getMonthValue();
	protected int today = localDate.getDayOfMonth();

	
	protected int tomorrow = today+1;
	protected int yesterday = today-1;
	protected int yearbefore18 = year-18;
	protected int previousMonth = month-1;
	
	public static DateFormat TimeFormat1 = new SimpleDateFormat("MMddyyyymm");
	public static String DateNTime = TimeFormat1.format(new Date()).toString();
	
	DateFormat TimeFormat2 = new SimpleDateFormat("MMM-dd-yyyy");
	String executiondate = TimeFormat2.format(new Date()).toString();
	
	public static DateFormat TimeFormat3 = new SimpleDateFormat("yyyyMdHH");
	public static String DateNTime1 = TimeFormat3.format(new Date()).toString();

	static String RandaomValue="b";
	public static String Handle_1 =RandaomValue+"b"+DateNTime1;
	public static String handle1 =Handle_1+".silamoney.eth";
	
	public static String Handle_2 =RandaomValue+"b"+DateNTime1;
	public static String handle2 =Handle_2+".silamoney.eth";
	
	public static String Handle_3 =RandaomValue+"c"+DateNTime1;
	public static String handle3 =Handle_3+".silamoney.eth";
	
	public static String Handle_4 =RandaomValue+"d"+DateNTime1;
	public static String handle4 =Handle_4+".silamoney.eth";
	
	public static String Handle_21 =RandaomValue+"f"+DateNTime1;
	public static String handle21 =Handle_21+".silamoney.eth";
	
	public static String Handle_22 =RandaomValue+"g"+DateNTime1;
	public static String handle22 =Handle_22+".silamoney.eth";
	
	public static String Handle_23 =RandaomValue+"h"+DateNTime1;
	public static String handle23 =Handle_23+".silamoney.eth";
	
	public static String Handle_24 =RandaomValue+"i"+DateNTime1;
	public static String handle24 =Handle_24+".silamoney.eth";
	
	public static String Handle_25 =RandaomValue+"j"+DateNTime1;
	public static String handle25 =Handle_25+".silamoney.eth";
	
	public static String Handle_26 =RandaomValue+"k"+DateNTime1;
	public static String handle26 =Handle_26+".silamoney.eth";
	
	public static String Handle_27 =RandaomValue+"l"+DateNTime1;
	public static String handle27 =Handle_27+".silamoney.eth";
	
	public static String Handle_28 =RandaomValue+"p"+DateNTime1;
	public static String handle28 =Handle_28+".silamoney.eth";
	
	public static String Handle_31 =RandaomValue+"m"+DateNTime1;
	public static String handle31 =Handle_31+".silamoney.eth";
	
	public static String Handle_32 =RandaomValue+"n"+DateNTime1;
	public static String handle32 =Handle_32+".silamoney.eth";
	
	public static String Handle_33 =RandaomValue+"o"+DateNTime1;
	public static String handle33 =Handle_33+".silamoney.eth";
	
	public static String Handle_not_registered ="apptestuser";
	public static String handleNotRegistered =Handle_not_registered+".silamoney.eth";
	//public static String Handle_5 =RandaomValue+"e"+DateNTime1;
	//public static String Unregistered_handle =Handle_not_registered+".silamoney.eth";
	
	public static String business_Handle =RandaomValue+"s"+DateNTime1;
	public static String businessHandle =business_Handle+".silamoney.eth";
	
	

	

	
	public static String handleTaken = "test_handle";
	public static String handleAlreadyTaken =handleTaken+".silamoney.eth";
	public static String emptyUserHandle ="";
	
	public static String invalidHandlePattern ="test:40";
	public static String handleInvalidPatern =invalidHandlePattern+".silamoney.eth";
	
	public static String host=Environments.SilaEnvironment.SANDBOX.getUrl();
	//Valid API
	public static String appHandle = "arcgqa1";
	public static String privateKey="ead74b1aa51f6087d091cfac6585c79cc6fa2f6414d3c1be79b5dc7f598e368b";
	public static SilaApi api = new SilaApi(host, appHandle, privateKey);
	
	//Valid API
	public static String appHandle2 = "arcgqa2";
	public static String privateKey2="f653203db252dee4a48ae1005f9328a620ba8baad6458379b898d2aa878985ed";
	public static SilaApi api2 = new SilaApi(host, appHandle2, privateKey2);
	

	//Invalid private key
	public static String invalidPrivateKey="ead74b1aa51f6087d091cfac6585c79cc6fa2f6414d3c1be79b5dc7f598e3611";
	public static SilaApi api1 = new SilaApi(host, appHandle, invalidPrivateKey);
	
	
	//invalid appp handle
	public static String invalidAppHandle="ead74b1aa51f6087d091cfac6585c79cc6fa2f6414d3c1be79b5dc7f598e368b";
	public static SilaApi api3 = new SilaApi(host, appHandle2, privateKey);
	
	public static SilaApi api4 = new SilaApi(Utility.host, Utility.appHandle, "3a1076bf45ab87712ad64ccb3b10217737f7faacbf2872e88fdd9a537d8fe266");  //for generate 401 status code
	
	protected String validPublicToken=Utility.getPlaidToken();
	protected String emptyPublicToken="";
	protected String invalidPublicToken="public-sandbox-dce1bfb8-3ca8-4591-b9ba-111111111111";


	

	
	@BeforeSuite
	public void TearUp(){
		// delete files in folder
		File folder = new File(System.getProperty("user.dir")+"/allure-results");
        File fList[] = folder.listFiles();

        for (File f : fList) {
            if (f.getName().endsWith(".json")) {
                f.delete(); 
            }}

	}

	
	
	@BeforeClass
	public void StartClass(){
	
	}	
	@BeforeTest
	public void StartTest(){	

		
		
		//SoftAssert TetsCaseID = new SoftAssert();

	}
	
	
	
	
//	@AfterMethod
//	public void getResult(ITestResult result) { 
//		
//		
//	}
	

	
	
	@AfterTest
	public void Endtest( ) throws IOException {
		

		
	}
	
	
	
	@AfterClass
	public void EndClass() throws Exception{

	}
	
	
	
	@AfterSuite
	public void TearDown() throws IOException, Exception{


	}

}
