package com.silamoney.client.domain;


import io.reactivex.annotations.Nullable;

/**
 * Class used in the registerBusiness method.
 *
 * @author Karlo Lorenzana
 */
public class BusinessUser {
	private final String country;;
     
    private final String handle;
    
    private final String entityName;
    
    private final String identityAlias;
    
    private final String identityValue;
    
    private final String phone;
    
    private final String email;
    
    private final String Address;
    
    private final String Address2;
    
    private final String city;
    
    private final String state;
     
    private final String zipCode;
    
    private final String cryptoAddress;
    
    private final String cryptoAlias;
    
    private final String type;
    
    private final BusinessType businessType;
    
    private final String businessWebsite;
    
    private final String doingBusinessAs;
    
    private final NaicsCategoryDescription naicsCategory;
    

    public  BusinessUser(String country, String handle, String entityName, String identityAlias, String identityValue, String phone, String email, String Address,  @Nullable String Address2, String city,
            String state, String zipCode,  String cryptoAddress, String cryptoAlias,  String type, BusinessType businessType, String businessWebsite, String doingBusinessAs,
            NaicsCategoryDescription naicsCategory) {
    	
    	this.country = country;
        this.handle = handle;
        this.entityName = entityName;
        this.identityAlias = identityAlias;
        this.identityValue = identityValue;
        this.phone = phone;
        this.email = email;
        this.Address = Address;
        this.Address2 = Address2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.cryptoAddress = cryptoAddress;
        this.cryptoAlias=cryptoAlias;
        this.type=type;
        this.businessType = businessType;
        this.businessWebsite = businessWebsite;
        this.doingBusinessAs = doingBusinessAs;
        this.naicsCategory = naicsCategory;
        
        
    }
    
	    public String getCountry() {
	        return country;
	    }   
        
        public String getHandle() {
            return handle;
        }
        
        public String getEntityName() {
            return entityName;
        }  
        
        public String getIdentityAlias() {
            return identityAlias;
        }  
        
        public String getIdentityValue() {
            return identityValue;
        }  
        

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }
        
        
        public String getAddress() {
            return Address;
        }
        
        
        public String getAddress2() {
            return Address2;
        }
        
        
        public String getCity() {
            return city;
        }


        public String getState() {
            return state;
        }


        public String getZipCode() {
            return zipCode;
        }
        
        public String getCryptoAddress() {
            return cryptoAddress;
        }

        public String getCryptoAlias() {
            return cryptoAlias;
        }
        

        public String getType() {
            return type;
        }
        

        public BusinessType getBusinessType() {
            return businessType;
        }  
        
        public String getBusinessWebsite() {
            return businessWebsite;
        }  
        
        
        public String getDoingBusinessAs() {
            return doingBusinessAs;
        }  
        
        public NaicsCategoryDescription getNaicsCategory() {
            return naicsCategory;
        }  
        
        

        

   
}