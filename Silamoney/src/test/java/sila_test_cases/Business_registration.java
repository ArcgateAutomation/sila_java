package sila_test_cases;


import org.testng.annotations.Test;
import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.api.SilaApi;
import com.silamoney.client.domain.BusinessType;
import com.silamoney.client.domain.BusinessUser;
import com.silamoney.client.domain.DefaultConfigurations;
import com.silamoney.client.domain.NaicsCategoryDescription;
import com.silamoney.common_files.Base_class;
import com.silamoney.common_files.Utility;


public class Business_registration extends Base_class{


		@Test
		public void Response200() throws Exception {
			BusinessType businessType = DefaultConfigurations.getBusinessTypes().get(0);
			NaicsCategoryDescription naicsCategory = DefaultConfigurations.getDefaultNaicCategoryDescription();
			
			
			


	
	}


}
