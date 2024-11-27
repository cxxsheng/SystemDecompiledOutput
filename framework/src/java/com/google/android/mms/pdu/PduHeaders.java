package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class PduHeaders {
    public static final int ADAPTATION_ALLOWED = 188;
    public static final int ADDITIONAL_HEADERS = 176;
    public static final int APPLIC_ID = 183;
    public static final int ATTRIBUTES = 168;
    public static final int AUX_APPLIC_ID = 185;
    public static final int BCC = 129;
    public static final int CANCEL_ID = 190;
    public static final int CANCEL_STATUS = 191;
    public static final int CANCEL_STATUS_REQUEST_CORRUPTED = 129;
    public static final int CANCEL_STATUS_REQUEST_SUCCESSFULLY_RECEIVED = 128;
    public static final int CC = 130;
    public static final int CONTENT = 174;
    public static final int CONTENT_CLASS = 186;
    public static final int CONTENT_CLASS_CONTENT_BASIC = 134;
    public static final int CONTENT_CLASS_CONTENT_RICH = 135;
    public static final int CONTENT_CLASS_IMAGE_BASIC = 129;
    public static final int CONTENT_CLASS_IMAGE_RICH = 130;
    public static final int CONTENT_CLASS_MEGAPIXEL = 133;
    public static final int CONTENT_CLASS_TEXT = 128;
    public static final int CONTENT_CLASS_VIDEO_BASIC = 131;
    public static final int CONTENT_CLASS_VIDEO_RICH = 132;
    public static final int CONTENT_LOCATION = 131;
    public static final int CONTENT_TYPE = 132;
    public static final int CURRENT_MMS_VERSION = 18;
    public static final int DATE = 133;
    public static final int DELIVERY_REPORT = 134;
    public static final int DELIVERY_TIME = 135;
    public static final int DISTRIBUTION_INDICATOR = 177;
    public static final int DRM_CONTENT = 187;
    public static final int ELEMENT_DESCRIPTOR = 178;
    public static final int EXPIRY = 136;
    public static final int FROM = 137;
    public static final int FROM_ADDRESS_PRESENT_TOKEN = 128;
    public static final java.lang.String FROM_ADDRESS_PRESENT_TOKEN_STR = "address-present-token";
    public static final int FROM_INSERT_ADDRESS_TOKEN = 129;
    public static final java.lang.String FROM_INSERT_ADDRESS_TOKEN_STR = "insert-address-token";
    public static final int LIMIT = 179;
    public static final int MBOX_QUOTAS = 172;
    public static final int MBOX_TOTALS = 170;
    public static final int MESSAGE_CLASS = 138;
    public static final int MESSAGE_CLASS_ADVERTISEMENT = 129;
    public static final java.lang.String MESSAGE_CLASS_ADVERTISEMENT_STR = "advertisement";
    public static final int MESSAGE_CLASS_AUTO = 131;
    public static final java.lang.String MESSAGE_CLASS_AUTO_STR = "auto";
    public static final int MESSAGE_CLASS_INFORMATIONAL = 130;
    public static final java.lang.String MESSAGE_CLASS_INFORMATIONAL_STR = "informational";
    public static final int MESSAGE_CLASS_PERSONAL = 128;
    public static final java.lang.String MESSAGE_CLASS_PERSONAL_STR = "personal";
    public static final int MESSAGE_COUNT = 173;
    public static final int MESSAGE_ID = 139;
    public static final int MESSAGE_SIZE = 142;
    public static final int MESSAGE_TYPE = 140;
    public static final int MESSAGE_TYPE_ACKNOWLEDGE_IND = 133;
    public static final int MESSAGE_TYPE_CANCEL_CONF = 151;
    public static final int MESSAGE_TYPE_CANCEL_REQ = 150;
    public static final int MESSAGE_TYPE_DELETE_CONF = 149;
    public static final int MESSAGE_TYPE_DELETE_REQ = 148;
    public static final int MESSAGE_TYPE_DELIVERY_IND = 134;
    public static final int MESSAGE_TYPE_FORWARD_CONF = 138;
    public static final int MESSAGE_TYPE_FORWARD_REQ = 137;
    public static final int MESSAGE_TYPE_MBOX_DELETE_CONF = 146;
    public static final int MESSAGE_TYPE_MBOX_DELETE_REQ = 145;
    public static final int MESSAGE_TYPE_MBOX_DESCR = 147;
    public static final int MESSAGE_TYPE_MBOX_STORE_CONF = 140;
    public static final int MESSAGE_TYPE_MBOX_STORE_REQ = 139;
    public static final int MESSAGE_TYPE_MBOX_UPLOAD_CONF = 144;
    public static final int MESSAGE_TYPE_MBOX_UPLOAD_REQ = 143;
    public static final int MESSAGE_TYPE_MBOX_VIEW_CONF = 142;
    public static final int MESSAGE_TYPE_MBOX_VIEW_REQ = 141;
    public static final int MESSAGE_TYPE_NOTIFICATION_IND = 130;
    public static final int MESSAGE_TYPE_NOTIFYRESP_IND = 131;
    public static final int MESSAGE_TYPE_READ_ORIG_IND = 136;
    public static final int MESSAGE_TYPE_READ_REC_IND = 135;
    public static final int MESSAGE_TYPE_RETRIEVE_CONF = 132;
    public static final int MESSAGE_TYPE_SEND_CONF = 129;
    public static final int MESSAGE_TYPE_SEND_REQ = 128;
    public static final int MMS_VERSION = 141;
    public static final int MMS_VERSION_1_0 = 16;
    public static final int MMS_VERSION_1_1 = 17;
    public static final int MMS_VERSION_1_2 = 18;
    public static final int MMS_VERSION_1_3 = 19;
    public static final int MM_FLAGS = 164;
    public static final int MM_FLAGS_ADD_TOKEN = 128;
    public static final int MM_FLAGS_FILTER_TOKEN = 130;
    public static final int MM_FLAGS_REMOVE_TOKEN = 129;
    public static final int MM_STATE = 163;
    public static final int MM_STATE_DRAFT = 128;
    public static final int MM_STATE_FORWARDED = 132;
    public static final int MM_STATE_NEW = 130;
    public static final int MM_STATE_RETRIEVED = 131;
    public static final int MM_STATE_SENT = 129;
    public static final int PREVIOUSLY_SENT_BY = 160;
    public static final int PREVIOUSLY_SENT_DATE = 161;
    public static final int PRIORITY = 143;
    public static final int PRIORITY_HIGH = 130;
    public static final int PRIORITY_LOW = 128;
    public static final int PRIORITY_NORMAL = 129;
    public static final int QUOTAS = 171;
    public static final int READ_REPLY = 144;
    public static final int READ_REPORT = 144;
    public static final int READ_STATUS = 155;
    public static final int READ_STATUS_READ = 128;
    public static final int READ_STATUS__DELETED_WITHOUT_BEING_READ = 129;
    public static final int RECOMMENDED_RETRIEVAL_MODE = 180;
    public static final int RECOMMENDED_RETRIEVAL_MODE_MANUAL = 128;
    public static final int RECOMMENDED_RETRIEVAL_MODE_TEXT = 181;
    public static final int REPLACE_ID = 189;
    public static final int REPLY_APPLIC_ID = 184;
    public static final int REPLY_CHARGING = 156;
    public static final int REPLY_CHARGING_ACCEPTED = 130;
    public static final int REPLY_CHARGING_ACCEPTED_TEXT_ONLY = 131;
    public static final int REPLY_CHARGING_DEADLINE = 157;
    public static final int REPLY_CHARGING_ID = 158;
    public static final int REPLY_CHARGING_REQUESTED = 128;
    public static final int REPLY_CHARGING_REQUESTED_TEXT_ONLY = 129;
    public static final int REPLY_CHARGING_SIZE = 159;
    public static final int REPORT_ALLOWED = 145;
    public static final int RESPONSE_STATUS = 146;
    public static final int RESPONSE_STATUS_ERROR_CONTENT_NOT_ACCEPTED = 135;
    public static final int RESPONSE_STATUS_ERROR_MESSAGE_FORMAT_CORRUPT = 131;
    public static final int RESPONSE_STATUS_ERROR_MESSAGE_NOT_FOUND = 133;
    public static final int RESPONSE_STATUS_ERROR_NETWORK_PROBLEM = 134;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_ADDRESS_HIDING_NOT_SUPPORTED = 234;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_CONTENT_NOT_ACCEPTED = 229;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_END = 255;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_FAILURE = 224;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_LACK_OF_PREPAID = 235;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_MESSAGE_FORMAT_CORRUPT = 226;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND = 228;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_FORWARDING_DENIED = 232;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_LIMITATIONS_NOT_MET = 230;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_NOT_SUPPORTED = 233;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_REPLY_CHARGING_REQUEST_NOT_ACCEPTED = 230;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_SENDING_ADDRESS_UNRESOLVED = 227;
    public static final int RESPONSE_STATUS_ERROR_PERMANENT_SERVICE_DENIED = 225;
    public static final int RESPONSE_STATUS_ERROR_SENDING_ADDRESS_UNRESOLVED = 132;
    public static final int RESPONSE_STATUS_ERROR_SERVICE_DENIED = 130;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_FAILURE = 192;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_MESSAGE_NOT_FOUND = 194;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM = 195;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_PARTIAL_SUCCESS = 196;
    public static final int RESPONSE_STATUS_ERROR_TRANSIENT_SENDNG_ADDRESS_UNRESOLVED = 193;
    public static final int RESPONSE_STATUS_ERROR_UNSPECIFIED = 129;
    public static final int RESPONSE_STATUS_ERROR_UNSUPPORTED_MESSAGE = 136;
    public static final int RESPONSE_STATUS_OK = 128;
    public static final int RESPONSE_TEXT = 147;
    public static final int RETRIEVE_STATUS = 153;
    public static final int RETRIEVE_STATUS_ERROR_END = 255;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_CONTENT_UNSUPPORTED = 227;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_FAILURE = 224;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND = 226;
    public static final int RETRIEVE_STATUS_ERROR_PERMANENT_SERVICE_DENIED = 225;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_FAILURE = 192;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_MESSAGE_NOT_FOUND = 193;
    public static final int RETRIEVE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM = 194;
    public static final int RETRIEVE_STATUS_OK = 128;
    public static final int RETRIEVE_TEXT = 154;
    public static final int SENDER_VISIBILITY = 148;
    public static final int SENDER_VISIBILITY_HIDE = 128;
    public static final int SENDER_VISIBILITY_SHOW = 129;
    public static final int START = 175;
    public static final int STATUS = 149;
    public static final int STATUS_DEFERRED = 131;
    public static final int STATUS_EXPIRED = 128;
    public static final int STATUS_FORWARDED = 134;
    public static final int STATUS_INDETERMINATE = 133;
    public static final int STATUS_REJECTED = 130;
    public static final int STATUS_RETRIEVED = 129;
    public static final int STATUS_TEXT = 182;
    public static final int STATUS_UNREACHABLE = 135;
    public static final int STATUS_UNRECOGNIZED = 132;
    public static final int STORE = 162;
    public static final int STORED = 167;
    public static final int STORE_STATUS = 165;
    public static final int STORE_STATUS_ERROR_END = 255;
    public static final int STORE_STATUS_ERROR_PERMANENT_FAILURE = 224;
    public static final int STORE_STATUS_ERROR_PERMANENT_MESSAGE_FORMAT_CORRUPT = 226;
    public static final int STORE_STATUS_ERROR_PERMANENT_MESSAGE_NOT_FOUND = 227;
    public static final int STORE_STATUS_ERROR_PERMANENT_MMBOX_FULL = 228;
    public static final int STORE_STATUS_ERROR_PERMANENT_SERVICE_DENIED = 225;
    public static final int STORE_STATUS_ERROR_TRANSIENT_FAILURE = 192;
    public static final int STORE_STATUS_ERROR_TRANSIENT_NETWORK_PROBLEM = 193;
    public static final int STORE_STATUS_SUCCESS = 128;
    public static final int STORE_STATUS_TEXT = 166;
    public static final int SUBJECT = 150;
    public static final int TO = 151;
    public static final int TOTALS = 169;
    public static final int TRANSACTION_ID = 152;
    public static final int VALUE_ABSOLUTE_TOKEN = 128;
    public static final int VALUE_NO = 129;
    public static final int VALUE_RELATIVE_TOKEN = 129;
    public static final int VALUE_YES = 128;
    private java.util.HashMap<java.lang.Integer, java.lang.Object> mHeaderMap;

    public PduHeaders() {
        this.mHeaderMap = null;
        this.mHeaderMap = new java.util.HashMap<>();
    }

    protected int getOctet(int i) {
        java.lang.Integer num = (java.lang.Integer) this.mHeaderMap.get(java.lang.Integer.valueOf(i));
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    protected void setOctet(int i, int i2) throws com.google.android.mms.InvalidHeaderValueException {
        switch (i2) {
            case 134:
            case 144:
            case 145:
            case 148:
            case 162:
            case 167:
            case 169:
            case 171:
            case 177:
            case 187:
            case 188:
                if (128 != i && 129 != i) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
                break;
            case 140:
                if (i < 128 || i > 151) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
                break;
            case 141:
                if (i < 16 || i > 19) {
                    i = 18;
                    break;
                }
                break;
            case 143:
                if (i < 128 || i > 130) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
                break;
            case 146:
                if (i > 196 && i < 224) {
                    i = 192;
                    break;
                } else if ((i > 235 && i <= 255) || i < 128 || ((i > 136 && i < 192) || i > 255)) {
                    i = 224;
                    break;
                }
            case 149:
                if (i < 128 || i > 135) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
            case 153:
                if (i > 194 && i < 224) {
                    i = 192;
                    break;
                } else if (i > 227 && i <= 255) {
                    i = 224;
                    break;
                } else if (i < 128 || ((i > 128 && i < 192) || i > 255)) {
                    i = 224;
                    break;
                }
            case 155:
                if (128 != i && 129 != i) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
                break;
            case 156:
                if (i < 128 || i > 131) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
                break;
            case 163:
                if (i < 128 || i > 132) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
                break;
            case 165:
                if (i > 193 && i < 224) {
                    i = 192;
                    break;
                } else if (i > 228 && i <= 255) {
                    i = 224;
                    break;
                } else if (i < 128 || ((i > 128 && i < 192) || i > 255)) {
                    i = 224;
                    break;
                }
            case 180:
                if (128 != i) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
                break;
            case 186:
                if (i < 128 || i > 135) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
            case 191:
                if (128 != i && 129 != i) {
                    throw new com.google.android.mms.InvalidHeaderValueException("Invalid Octet value!");
                }
                break;
            default:
                throw new java.lang.RuntimeException("Invalid header field!");
        }
        this.mHeaderMap.put(java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i));
    }

    protected byte[] getTextString(int i) {
        return (byte[]) this.mHeaderMap.get(java.lang.Integer.valueOf(i));
    }

    protected void setTextString(byte[] bArr, int i) {
        if (bArr == null) {
            throw new java.lang.NullPointerException();
        }
        switch (i) {
            case 131:
            case 132:
            case 138:
            case 139:
            case 152:
            case 158:
            case 183:
            case 184:
            case 185:
            case 189:
            case 190:
                this.mHeaderMap.put(java.lang.Integer.valueOf(i), bArr);
                return;
            default:
                throw new java.lang.RuntimeException("Invalid header field!");
        }
    }

    protected com.google.android.mms.pdu.EncodedStringValue getEncodedStringValue(int i) {
        return (com.google.android.mms.pdu.EncodedStringValue) this.mHeaderMap.get(java.lang.Integer.valueOf(i));
    }

    protected com.google.android.mms.pdu.EncodedStringValue[] getEncodedStringValues(int i) {
        java.util.ArrayList arrayList = (java.util.ArrayList) this.mHeaderMap.get(java.lang.Integer.valueOf(i));
        if (arrayList == null) {
            return null;
        }
        return (com.google.android.mms.pdu.EncodedStringValue[]) arrayList.toArray(new com.google.android.mms.pdu.EncodedStringValue[arrayList.size()]);
    }

    protected void setEncodedStringValue(com.google.android.mms.pdu.EncodedStringValue encodedStringValue, int i) {
        if (encodedStringValue == null) {
            throw new java.lang.NullPointerException();
        }
        switch (i) {
            case 137:
            case 147:
            case 150:
            case 154:
            case 160:
            case 164:
            case 166:
            case 181:
            case 182:
                this.mHeaderMap.put(java.lang.Integer.valueOf(i), encodedStringValue);
                return;
            default:
                throw new java.lang.RuntimeException("Invalid header field!");
        }
    }

    protected void setEncodedStringValues(com.google.android.mms.pdu.EncodedStringValue[] encodedStringValueArr, int i) {
        if (encodedStringValueArr == null) {
            throw new java.lang.NullPointerException();
        }
        switch (i) {
            case 129:
            case 130:
            case 151:
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (com.google.android.mms.pdu.EncodedStringValue encodedStringValue : encodedStringValueArr) {
                    arrayList.add(encodedStringValue);
                }
                this.mHeaderMap.put(java.lang.Integer.valueOf(i), arrayList);
                return;
            default:
                throw new java.lang.RuntimeException("Invalid header field!");
        }
    }

    protected void appendEncodedStringValue(com.google.android.mms.pdu.EncodedStringValue encodedStringValue, int i) {
        if (encodedStringValue == null) {
            throw new java.lang.NullPointerException();
        }
        switch (i) {
            case 129:
            case 130:
            case 151:
                java.util.ArrayList arrayList = (java.util.ArrayList) this.mHeaderMap.get(java.lang.Integer.valueOf(i));
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(encodedStringValue);
                this.mHeaderMap.put(java.lang.Integer.valueOf(i), arrayList);
                return;
            default:
                throw new java.lang.RuntimeException("Invalid header field!");
        }
    }

    protected long getLongInteger(int i) {
        java.lang.Long l = (java.lang.Long) this.mHeaderMap.get(java.lang.Integer.valueOf(i));
        if (l == null) {
            return -1L;
        }
        return l.longValue();
    }

    protected void setLongInteger(long j, int i) {
        switch (i) {
            case 133:
            case 135:
            case 136:
            case 142:
            case 157:
            case 159:
            case 161:
            case 173:
            case 175:
            case 179:
                this.mHeaderMap.put(java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j));
                return;
            default:
                throw new java.lang.RuntimeException("Invalid header field!");
        }
    }
}