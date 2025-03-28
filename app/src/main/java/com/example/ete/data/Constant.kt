package com.example.ete.data

object Constant {

    //Preference key
    object PrefsKeys {
        const val FIREBASE_MESSAGE_TOKEN: String = "firebaseMessageToken"
        const val USER_DATA: String = "userData"
        const val AUTH_DATA: String = "authData"
    }

    //Intent keys
    object IntentObject {
        const val INTENT_IS_PHONE_AUTH = "isPhoneAuth"
        const val INTENT_PHONE = "phone"
        const val INTENT_EMAIL = "email"
        const val INTENT_AUTH_BEAN = "authBean"
        const val INTENT_IS_FIRST_TIME= "isFirstTime"
    }

    //Api key
    object ApiObject {
        const val PLATFORM = "platform"
        const val VERSION = "version"
        const val ANDROID = "Android"
        const val COUNTRY_CODE = "countryCode"
        const val PHONE = "phone"
        const val ORDER_ID = "orderId"
        const val EMAIL = "email"
        const val OTP = "otp"
        const val NAME = "name"
        const val FULL_NAME = "fullName"
        const val PROFILE_PIC = "profilePic"
        const val USER_NAME = "userName"
        const val DOB = "dateOfBirth"
        const val GENDER = "gender"
        const val LINK = "link"
        const val COUNTRY_ID = "countryId"
        const val BIO = "bio"
        const val PROVIDER_TYPE = "providerType"
        const val GOOGLE = "google"
        const val APPLE = "apple"
        const val POST_ID = "postId"
        const val ID = "id"
        const val USER_ID = "userId"
        const val POST_TYPE_ID = "postTypeId"
        const val OTHER_POST_TYPE = "otherPostType"
        const val CAPTION = "caption"
        const val LIBRARY_ID = "libraryId"
        const val LABEL = "label"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val ADDRESS = "address"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val POST_IMAGE_VIDEO_FILE = "postImageVideoFile"
        const val JOURNAL_IMAGE_VIDEO_FILE = "journalImageVideoFile"
        const val DELETED_MEDIA_ID = "deletedMediaId"
        const val LIMIT = "limit"
        const val LIMIT_15 = 15
        const val LIMIT_20 = 20
        const val PAGE = "page"
        const val SEARCH = "search"
        const val ALPHABETIC_SEARCH = "alphabeticSearch"
        const val YEAR = "year"
        const val PATH = "path"
        const val REASON_ID = "reasonId"
        const val REPORT_USER_ID = "reportedUserId"
        const val CUSTOM_REASON = "customReason"
        const val COMMENT = "comment"
        const val POST_COMMENT_ID = "postCommentId"
        const val LIST = "list"
        const val POST = "post"
        const val ACCOUNT_TITLE = "account"
        const val LIBRARY_TITLE = "library"
        const val HASHTAG_TITLE = "hashtags"
        const val FOR_YOU_TITLE = "forYou"
        const val RESOURCES = "resources"
        const val IS_IMAGE = "isImage"
        const val FILE_URL = "fileUrl"
        const val LIBRARY_VIEW_TYPE = "libraryViewType"
        const val THUMBNAIL_URL = "thumbnailUrl"
        const val URL = "url"
        const val IS_WISH_LIST = "isWishlist"
        const val NOTE = "note"
        const val VOICE_NOTE_URL = "voiceNoteUrl"
        const val VOICE_NOTE_TITLE = "voiceNoteTitle"
        const val VOICE_NOTE_SIZE = "voiceNoteSize"
        const val VOICE_NOTE_DURATION = "voiceNoteDuration"
        const val SEEDING_DATE = "seedingDate"
        const val GERMINATION_DATE = "germinationDate"
        const val GERMINATION_RATE = "germinationRate"
        const val TRANSPLANTATION_DATE = "transplantationDate"
        const val MATURITY_DATE = "maturityDate"
        const val MATURITY_TIME = "maturityTime"
        const val CUTTING_DATE = "cuttingDate"
        const val SOWING_DATE = "sowingTypeId"
        const val OTHER_SOWING_TYPE = "otherSowingType"
        const val SUN_EXPOSURE_ID = "sunExposureId"
        const val WATER = "water"
        const val SOWING_TYPE_ID = "sowingTypeId"
        const val JOURNAL_TYPE_ID = "journalTypeId"
        const val OTHER_JOURNAL_TYPE = "otherJournalType"
        const val PLANT_TYPE_ID = "plantTypeId"
        const val OTHER_PLANT_TYPE = "otherPlantType"
        const val OTHER_SUN_EXPOSURE = "otherSunExposure"
        const val SOIL_MEDIUM = "soilMedium"
        const val SOIL_TYPE = "soilType"
        const val SEED_SOURCE = "seedSource"
        const val FERTILIZER = "fertilizer"
        const val MANURE = "manure"
        const val IS_PINCHING = "isPinching"
        const val PINCHING_DATE = "pinchingDate"
        const val PLANT_SPACING = "plantSpacing"
        const val VARIETY = "variety"
        const val TEMPERATURE = "temperature"
        const val HUMIDITY = "humidity"
        const val COLOUR = "colour"
        const val HYBRID_STATUS = "hybridStatus"
        const val BLOOM_TIME = "bloomTime"
        const val IMAGES = "images"
        const val JOURNAL_ID = "journalId"
        const val COMMENT_ID = "commentId"
        const val COMMENT_REPLY_ID = "commentReplyId"
        const val MONTHS = "months"
        const val LAST_ADDRESS_ID = "lastAddressId"
        const val TYPE = "type"
    }

    //Force update key
    object ForceUpdate {
        const val UP_TO_DATE: Int = 0
        const val FORCE_UPDATE: Int = 1
        const val OPTIONAL_UPDATE: Int = 2
    }

    object Gender{
        const val MALE = "Male"
        const val FEMALE = "Female"
        const val OTHER = "Other"
    }

    enum class Screen {
        WELCOME,
        LOGIN,
        OTP,
        CREATE_ACCOUNT
    }
}