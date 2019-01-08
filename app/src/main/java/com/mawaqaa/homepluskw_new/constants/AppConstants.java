package com.mawaqaa.homepluskw_new.constants;

/**
 * Created by JijoCJ on 10/26/2016.
 */
public class AppConstants {
    public static final String HP_ENGLISH = "en";
    public static final String HP_ARABIC = "ar";
    public static final String HP_SECURITY_KEY_VALUE = "WEBNASECURITY";
    public static final String GOOGLE_API = "AIzaSyA12bA0v6-mRQ03fi9OFKlj893_gdxbEWE";
    public static final String FCM_SERVER_API = "AIzaSyDeVWFNMyfaBojSzrmw4gew_Ba2m9cbodM";

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
    public static final String DEVICE_ID = "DeviceId";
    public static final String DEVICE_TOKEN = "DeviceToken";
    public static final String DEVICE_PLATFORM = "Platform";

    //public static final String HP_BASE_URL = "http://hpservice.mawaqaademo.com/HPlus.svc/";
//     public static final String HP_BASE_URL = "http://hpservicev2.mawaqaademo.com/HPlus.svc/";
    public static final String HP_BASE_URL = "http://services.homepluskw.com/HPlus.svc/";
    public static final String MAKE_THUMBNAIL_BASE_URL = "http://img.youtube.com/vi/";
    public static final String MAKE_THUMBNAIL_DEFAULT = "/default.jpg";

    public static final String GET_SOCIALMEDIA_LINK = HP_BASE_URL + "GetSocialMedia";
    public static final String GET_SLIDSHOW_BANNER = HP_BASE_URL + "GetBanners";
    public static final String LOGIN = HP_BASE_URL + "Login";
    public static final String REGISTRATION = HP_BASE_URL + "Registration";
    public static final String HP_FORGOT_PASSWORD = HP_BASE_URL + "ForgotPassword";
    public static final String HP_REAL_ESTATE = HP_BASE_URL + "RealEstate";
    public static final String HP_REAL_ESTATE_INNER_DATA = HP_BASE_URL + "RealEstateDetails";
    public static final String HP_CONSTRUCTION_INDIVIDUAL = HP_BASE_URL + "IndividualConstruction";
    public static final String HP_CONSTRUCTION_INDIVIDUAL_NEW = HP_BASE_URL + "IndividualConstructionNew";
    public static final String HP_CONSTRUCTION_INDIVIDUAL_INNER = HP_BASE_URL + "IndividualInner";

    public static final String HP_CONSTRUCTION_INDIVIDUAL_INNER_SEARCH = HP_BASE_URL + "IndividualInnerSearch";
    public static final String HP_CONSTRUCTION_INDIVIDUAL_DETAILS = HP_BASE_URL + "IndividualDetailsPage";
    public static final String HP_CONSTRUCTION_CORPRATE = HP_BASE_URL + "CorporateConstruction";
    public static final String HP_CONSTRUCTION_CORPRATE_NEW = HP_BASE_URL + "CorporateConstructionNew";

    public static final String HP_CONSTRUCTION_CORPRATE_INNER = HP_BASE_URL + "CorporateInner";
    public static final String HP_CONSTRUCTION_CORPRATE_INNER_SEARCH = HP_BASE_URL + "CorporateInnerSearch";
    public static final String HP_CONSTRUCTION_CORPRATE_DETAILS = HP_BASE_URL + "CorporateDetailsPage";
    public static final String HP_PROPERTY_SERVICE_REQUEST_DATA = HP_BASE_URL + "MyFirstRequest";
    public static final String HP_VIEWPROFILE_REQUEST_DATA = HP_BASE_URL + "ViewProfile";
    public static final String HP_PROPERTY_SERVICE_SEND_REQUEST = HP_BASE_URL + "MyRequest";
    public static final String HP_SPECIAL_OFFER = HP_BASE_URL + "SpecialOffer";
    public static final String HP_HOME_SPECIAL_OFFER = HP_BASE_URL + "HomeSpecialOffer";
    public static final String HP_PHOTO_GALLERY_lIST_DATA = HP_BASE_URL + "HPPhotos";
    public static final String HP_NEWS_lIST_DATA = HP_BASE_URL + "NewsList";
    public static final String HP_VIDEO_GALLERY_DATA = HP_BASE_URL + "HPVideos";
    public static final String HP_DEVICE_REGISTRATION = HP_BASE_URL + "ManageDevice";
    public static final String HP_MY_REQUEST_LIST = HP_BASE_URL + "MyRequestList";
    public static final String HP_MY_REQUEST_LIST_DELETE = HP_BASE_URL + "DeleteRequest";
    public static final String HP_EDIT_PROFILE = HP_BASE_URL + "EditProfile";
    public static final String HP_ABOUTUS = HP_BASE_URL + "CorporateOverview";
    public static final String HP_CONTACTUS_ADDRESSINFO = HP_BASE_URL + "AddressInfo";
    public static final String HP_PARTNERS = HP_BASE_URL + "Partners";
    public static final String HP_PHOTOSEARCH = HP_BASE_URL + "HPPhotosSearch";
    public static final String HP_NEWSFILTER = HP_BASE_URL + "NewsListSearch";
    public static final String HP_PUSHNOTIFICATION_LIST = HP_BASE_URL + "GetNotificationHistory";
    public static final String HP_INDIVIDUAL_RATING = HP_BASE_URL + "RateService";
    public static final String HP_FEDDBACK_FORM = HP_BASE_URL + "Feedback";
    public static final String HP_CHANGEPASSWORD = HP_BASE_URL + "ChangePassword";
    public static final String HP_DELETENOTIFICATION= HP_BASE_URL + "DeleteNotification";

    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String blockCharacterSet = "@~#^|$%&*!,/+-{}[]<>";

    public static final String LANGUAGE_KEY = "LanguageKey";
    public static final String SECURITY_KEY = "SecurityKey";
    public static final String BANNER_LIST = "BannerList";
    public static final String URL = "URL";


    // Registration Fragment
    public static final String NAME = "UserName";
    public static final String AGE = "Age";
    public static final String EMAIL = "EmailId";
    public static final String GENDER = "Gender";
    public static final String MOBILE_NO = "MobileNo";
    public static final String PASSWORD = "Password";

    // Login Fragment
    public static final String IS_SUCCESS = "IsSuccess";
    public static final String USER_ID = "UserId";
    public static final String MESSAGE = "Message";

    //RealEstate Fragment
    public static final String REAL_ESTATE_ARR = "RealEstates";
    public static final String ID = "ID";
    public static final String IMAGE = "Image";
    public static final String LOCATION = "Location";
    public static final String PRICE = "Price";
    public static final String PROPERTY_NAME = "PropertyName";
    public static final String PROPERTY_TYPE = "PropertyType";
    public static final String PURPOSE = "Purpose";
    public static final String REGION = "Region";
    public static final String SEARCHKEYWORD = "SearchKeyword";
    public static final String SPECIALOFFER_PROPERTYTYPE = "PropertyType";


    //RealEstateDetails Fragment
    public static final String IMAGES = "Images";
    public static final String PROPERTY_ID = "Id";
    public static final String PROPERTY_DETAILS = "PropertyDetails";
    public static final String PROPERTY_SPACE = "SquareFeet";
    public static final String PROPERTY_SPEC_ARRAY = "Specifications";
    public static final String SPONSERS = "Sponcers";
    public static final String LATITUDE = "Lattitude";
    public static final String LONGITUDE = "Longitude";
    //public static final String VIDEO_THUMB_NAIL = "VideoCoverImage";

    public static final String VIDEO_LINK = "VideoLink";
    public static final String OWNER_NAME = "OwnerName";
    public static final String OWNER_MOBILE = "OwnerMobile";
    public static final String OWNER_EMAIL = "OwnerEmail";
    public static final String PROPERTY_ADDRESS1 = "PropertyAddress1";
    public static final String PROPERTY_ADDRESS2 = "PropertyAddress2";
    public static final String PROPERTY_ADDRESS3 = "PropertyAddress3";
    public static final String PROPERTY_ADDRESS4 = "PropertyAddress4";
    public static final String REALESTATE_PROPERTY_LOCATION = "Location";
    public static final String REALESTATE_PRICE_FROM = "PriceFrom";
    public static final String REALESTATE_PRICE_TO = "PriceTo";
    public static final String REALESTATE_PURPOSE = "Purpose";
    public static final String REALESTATE_REGION = "Region";
    public static final String REALESTATE_SIZEFROM = "SizeFrom";
    public static final String REALESTATE_SIZETO = "SizeTo";
    public static final String REALESTATE_TYPE = "Type";
    public static final String REALESTATE_PROPERTY_SPACE = "Size";
    public static final String REALESTATE_PRICE_RANGE = "PriceRange";

    //ConstructionIndividual Fragment
    public static final String CONSTRUCTION_INDIVIDUAL_ARR = "ContructionServices";
    public static final String CONSTRUCTION_SERVICE_IMAGE = "ServiceImage";
    public static final String CONSTRUCTION_SERVICE_TITLE = "ServiceTitle";
    public static final String REALESTATE_REGION_TEXT = "Text";
    public static final String REALESTATE_REGION_VALUE = "Value";

    //ConstructionIndividualInner Fragment
    public static final String CONSTRUCTION_ID = "Id";
    public static final String AVERAGE_RATING = "AverageRating";
    public static final String DETAIL_LIST_ARR = "DetailList";
    public static final String CONSTRUCTION_NAME = "Name";
    public static final String PERSONS_RATED = "PersonsRated";
    public static final String PROFILE_PHOTO = "ProfilePhoto";
    public static final String NATIONALITY_ARR = "Nationality";
    public static final String COUNTRY_CODE = "CountryCode";
    public static final String COUNTRY_NAME = "CountryName";
    public static final String COUNTRY_ID = "ID";
    public static final String SORT_BY_ARR = "SortBy";
    public static final String NATION = "Nation";
    public static final String TEXT = "Text";
    public static final String TYPE = "Type";
    public static final String LOGINEMAIL = "EmailId";
    public static final String PASSWORDLOGIN = "Password";

    public static final String MUREQUESTEMAIL = "UserEmail";

    //ConstructionIndividualInnerDetails Fragment
    public static final String EMAIL_CONSTRUCTION = "Email";
    public static final String EXPERIENCE_DETAILS_ARR = "ExperienceDetails";
    public static final String NAME_CONSTRUCTION = "Name";
    public static final String NATIONALITY = "Nationality";
    public static final String PHONE_NUMBER = "PhoneNumber";
    public static final String SERVICE_DESCRIPTION_ARR = "ServiceDescription";
    public static final String VIDEO_THUMBNAIL = "VideoThumbnail";
    public static final String VIDEO_URL = "VideoUrl";
    public static final String THUMBNAIL = "Thumbnail";
    public static final String ABOUT_VIDEO = "About Real Estate";

    //ConstructionCorporate Fragment
    public static final String CORPORATE_ARR = "ContructionServices";

    //ConstructionCorporateInner Fragment
    public static final String COMPANY_LOCATION = "CompanyLocation";
    public static final String COMPANY_NAME = "CompanyName";
    public static final String FEATURED_IMAGE = "FeaturedImage";

    //ConstructionCorporareDetails Fragment
    public static final String COMPANY_ADDRESS = "CompanyAddress";
    public static final String WORKING_HOURS = "WorkingHours";
    public static final String IMAGE_LIST = "ImageList";
    public static final String COMPANY_EMAIL = "CompanyEmail";
    public static final String COMPANY_PHONE = "CompanyPhone";
    public static final String COMPANY_WEBSITE = "CompanyWebsite";
    public static final String LATITUDE_CORPORATE_DETAILS = "Latitude";
    public static final String LONGITUDE_CORPORATE_DETAILS = "Longitude";
    public static final String LONGITUDE_CORPORATE_DETAILS_RATING = "Rating";


    //RequestPropertyService Fragment
    public static final String PROPERTY_TYPE_ARR = "PropertyTypeList";
    public static final String PURPOSE_ARR = "PurposeList";
    public static final String REGION_ARR = "RegionList";

    //SpecialOffer Fragment
    public static final String SPECIAL_OFFER_REAL_ESTATE = "RealEstate";
    public static final String SPECIAL_OFFER_REAL_ESTATE_ALLOFFERS = "AllOffers";

    public static final String SPECIAL_OFFER_CONSTRUCTION = "Construction";
    public static final String SPECIAL_OFFER_DESCRIPTION = "Description";
    public static final String SPECIAL_OFFER_ID = "Id";
    public static final String SPECIAL_OFFER_IS_INDIVIDUAL = "Isindividual";
    public static final String SPECIAL_OFFER_DATE = "OfferDate";
    public static final String SPECIAL_OFFER_TITLE = "Title";
    public static final String SPECIAL_OFFER_PURPOSE = "Purpose";
    public static final String SPECIAL_OFFER_REGION = "Region";
    public static final String SPECIAL_OFFER_TYPE = "Type";


    //MediaCenterPhotoGallery Fragment
    public static final String PHOTO_GALLERY_lIST = "PhotoGalleryList";
    public static final String PHOTO_GALLERY_ID = "Id";
    public static final String PHOTO_ALBUM_DATE = "AlbumDate";
    public static final String PHOTO_ALBUM_COVER_PHOTO = "AlbumCoverPhoto";
    public static final String PHOTO_ALBUM_IMAGE = "AlbumImagesPath";
    public static final String PHOTO_ALBUM_TITLE = "AlbumTitle";
    public static final String PHOTO_ALBUM_PHOTO_ARR = "Photos";
    public static final String PHOTO_ALBUM_MONTH_LIST = "MonthList";
    public static final String PHOTO_ALBUM_YEAR_LIST = "YearList";
    public static final String PHOTO_ALBUM_FILTER_TEXT = "Text";
    public static final String PHOTO_ALBUM_FILTER_VALUE = "Value";

    //MediaCenterNews Fragment
    public static final String NEWS_LIST = "NewsList";
    public static final String NEWS_DATE = "NewsDate";
    public static final String NEWS_DESCRIPTION = "NewsDescription";
    public static final String NEWS_IMAGE = "NewsImage";
    public static final String NEWS_TITLE = "NewsTitle";
    public static final String NEWS_MONTH = "NewsMonth";
    public static final String NEWS_YEAR = "NewsYear";
    public static final String RELATEDLIST_IMAGE = "RelatedImages";

    //MediaCenterVideoGallery Fragment
    public static final String VIDEO_GALLERY_LIST = "VideoGalleryList";
    public static final String VIDEO_GALLERY_DATE = "Date";
    public static final String VIDEO_ALBUM_ID = "Id";
    public static final String VIDEO_ALBUM_NAME = "VideoAlbumName";
    public static final String VIDEO_COVER_IMAGE = "VideoCoverImage";
    public static final String VIDEOS_ARR = "Videos";
    public static final String VIDEO_NAME = "VideoName";
    public static final String VIDEO_THUMB_IMAGE = "VideoThumbImage";

    //MyRequestList Fragment
    public static final String APPLIED_DATE = "AppliedDate";
    public static final String APPLIATION_ID = "Id";
    public static final String PROPERTY_LOCATION = "PropertyLocation";
    public static final String DETAILS_ARR = "Details";
    /*public static final String PROPERTY_TYPE = "PropertyType";
    public static final String PURPOSE = "Purpose";*/


    //ViewProfile Fragment
    public static final String VIEWPROFILE_ID = "Id";

    //EditProfile
    public static final String FIRSTNAME = "FirstName";
    public static final String LASTNAME = "LastName";

    //Pushnotification List
    public static final String NOTIFICATION_ARR = "Notifications";
    public static final String DESCRIPTION = "Description";
    public static final String PROPERTID = "Id";
    public static final String POSTON = "PostedOn";
    public static final String READSTATUS = "ReadStatus";
    public static final String TITLE = "Title";
    public static final String ISINDIVIDUAL = "IsIndividual";
    public static final String ISREQUEST = "IsRequest";
    public static final String RETURNIMAGE = "ReturnImage";
    public static final String PROPERTYLOCATION = "Location";
    public static final String PROPERTY_PURPOSE = "Purpose";
    public static final String PROPERTYTYPE = "PropertyType";

    /* public static final String PROPERTYIMAGE = "PropertyImage";
     public static final String PROPERTYLOCATION = "PropertyLocation";
     public static final String PROPERTYNAME = "PropertyName";
     public static final String PROPERTY_POSTDATE = "PropertyPostedDate";
     public static final String PROPERTY_PURPOSE = "PropertyPurpose";
     public static final String PROPERTYTYPE = "PropertyType";*/
    //ContactUs
    public static final String LATITUDECONTACT = "Latitude";
    //CotactUs Feedback

    public static final String FEEDBACK_NAME = "FirstName";
    public static final String FEEDBACK_EMAIL = "Email";
    public static final String FEEDBACK_MOBNO = "MobNo";
    public static final String FEEDBACK_TELNO = "TelNo";
    public static final String FEEDBACK_SUBJECT = "Subject";
    public static final String FEEDBACK_MESSAGE = "MessageToSend";

    //AoutUs Partners
    public static final String PARTNERS_HOMECLIENTS_ARRAY = "HomeClients";
    public static final String PARNERS_IMAGE = "Image";
    public static final String PARTNERS_URL = "URL";
    //MyRequest Delete
    public static final String MyREQUEST_USERID = "Id";
    //SocialMediaWebservice

    public static final String HP_SOCIALMEDIA_ARRAY = "SocialMedia";
    public static final String HP_MEDIA_LINK = "Link";
    public static final String HP_MEDIANAME = "Name";
    public static final String HP_INSTAGRAM = "Link";
    public static final String HP_TWITTER = "Link";
    public static final String HP_LINKEDIN = "Link";
    public static final String HP_FACEBOOK = "Link";

    //ConstructionIndividualRatingBar
    public static final String RATING = "Rating";
    public static final String SERVICEID = "ServiceID";
    public static final String SERVICETYPE = "ServiceType";
    public static final String USERID = "UserID";

    //phptogalleryfilter
    public static final String PHOTOGALLERYMONTH = "Month";
    public static final String PHOTOGALLERYYEAR = "Year";

    /*getbanner*/
    public static final String ISFIRST = "IsFirst";
    /*HomeSpecialOffer*/
    public static final String HOME_SPECIAL_OFFER = "Offers";
    public static final String HOME_SPECIAL_DESCRIPTION = "Description";
    public static final String HOME_SPECIAL_OFFER_ID = "Id";
    public static final String HOME_SPECIAL_OFFER_IMAGE = "Image";

    public static final String HOME_SPECIAL_OFFER_TITLE = "Title";
    public static final String HOME_SPECIAL_OFFER_TYPE = "Type";
    /*chnagePassword*/
    public static final String HOME_PLUS_NEWPASSWORD = "NewPassword";
    public static final String HOME_PLUS_OLDPASSWORD = "OldPassword";
    public static final String HOME_PLUS_ANDROID_LINK = "Android_Link";
    public static final String HOME_PLUS_NOTIFICATION_ID = "NotificationId";


}
