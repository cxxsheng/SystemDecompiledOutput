package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ImsCallProfile implements android.os.Parcelable {
    public static final int CALL_RESTRICT_CAUSE_DISABLED = 2;
    public static final int CALL_RESTRICT_CAUSE_HD = 3;
    public static final int CALL_RESTRICT_CAUSE_NONE = 0;
    public static final int CALL_RESTRICT_CAUSE_RAT = 1;
    public static final int CALL_TYPE_NONE = 0;
    public static final int CALL_TYPE_VIDEO_N_VOICE = 3;
    public static final int CALL_TYPE_VOICE = 2;
    public static final int CALL_TYPE_VOICE_N_VIDEO = 1;
    public static final int CALL_TYPE_VS = 8;
    public static final int CALL_TYPE_VS_RX = 10;
    public static final int CALL_TYPE_VS_TX = 9;
    public static final int CALL_TYPE_VT = 4;
    public static final int CALL_TYPE_VT_NODIR = 7;
    public static final int CALL_TYPE_VT_RX = 6;
    public static final int CALL_TYPE_VT_TX = 5;
    public static final android.os.Parcelable.Creator<android.telephony.ims.ImsCallProfile> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.ImsCallProfile>() { // from class: android.telephony.ims.ImsCallProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsCallProfile createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.ImsCallProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsCallProfile[] newArray(int i) {
            return new android.telephony.ims.ImsCallProfile[i];
        }
    };
    public static final int DIALSTRING_NORMAL = 0;
    public static final int DIALSTRING_SS_CONF = 1;
    public static final int DIALSTRING_USSD = 2;
    public static final java.lang.String EXTRA_ADDITIONAL_CALL_INFO = "AdditionalCallInfo";
    public static final java.lang.String EXTRA_ADDITIONAL_SIP_INVITE_FIELDS = "android.telephony.ims.extra.ADDITIONAL_SIP_INVITE_FIELDS";
    public static final java.lang.String EXTRA_ASSERTED_DISPLAY_NAME = "android.telephony.ims.extra.ASSERTED_DISPLAY_NAME";
    public static final java.lang.String EXTRA_CALL_DISCONNECT_CAUSE = "android.telephony.ims.extra.CALL_DISCONNECT_CAUSE";
    public static final java.lang.String EXTRA_CALL_MODE_CHANGEABLE = "call_mode_changeable";
    public static final java.lang.String EXTRA_CALL_NETWORK_TYPE = "android.telephony.ims.extra.CALL_NETWORK_TYPE";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_CALL_RAT_TYPE = "CallRadioTech";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_CALL_RAT_TYPE_ALT = "callRadioTech";
    public static final java.lang.String EXTRA_CALL_SUBJECT = "android.telephony.ims.extra.CALL_SUBJECT";
    public static final java.lang.String EXTRA_CHILD_NUMBER = "ChildNum";
    public static final java.lang.String EXTRA_CNA = "cna";
    public static final java.lang.String EXTRA_CNAP = "cnap";
    public static final java.lang.String EXTRA_CODEC = "Codec";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CONFERENCE = "android.telephony.ims.extra.CONFERENCE";
    public static final java.lang.String EXTRA_CONFERENCE_AVAIL = "conference_avail";
    public static final java.lang.String EXTRA_CONFERENCE_DEPRECATED = "conference";
    public static final java.lang.String EXTRA_DIALSTRING = "dialstring";
    public static final java.lang.String EXTRA_DISPLAY_TEXT = "DisplayText";
    public static final java.lang.String EXTRA_EMERGENCY_CALL = "e_call";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_EXTENDING_TO_CONFERENCE_SUPPORTED = "android.telephony.ims.extra.EXTENDING_TO_CONFERENCE_SUPPORTED";
    public static final java.lang.String EXTRA_FORWARDED_NUMBER = "android.telephony.ims.extra.FORWARDED_NUMBER";
    public static final java.lang.String EXTRA_IS_BUSINESS_CALL = "android.telephony.ims.extra.IS_BUSINESS_CALL";
    public static final java.lang.String EXTRA_IS_CALL_PULL = "CallPull";
    public static final java.lang.String EXTRA_IS_CROSS_SIM_CALL = "android.telephony.ims.extra.IS_CROSS_SIM_CALL";
    public static final java.lang.String EXTRA_LOCATION = "android.telephony.ims.extra.LOCATION";
    public static final java.lang.String EXTRA_OEM_EXTRAS = "android.telephony.ims.extra.OEM_EXTRAS";
    public static final java.lang.String EXTRA_OI = "oi";
    public static final java.lang.String EXTRA_OIR = "oir";
    public static final java.lang.String EXTRA_PICTURE_URL = "android.telephony.ims.extra.PICTURE_URL";
    public static final java.lang.String EXTRA_PRIORITY = "android.telephony.ims.extra.PRIORITY";
    public static final java.lang.String EXTRA_REMOTE_URI = "remote_uri";
    public static final java.lang.String EXTRA_RETRY_CALL_FAIL_NETWORKTYPE = "android.telephony.ims.extra.RETRY_CALL_FAIL_NETWORKTYPE";
    public static final java.lang.String EXTRA_RETRY_CALL_FAIL_REASON = "android.telephony.ims.extra.RETRY_CALL_FAIL_REASON";
    public static final java.lang.String EXTRA_USSD = "ussd";
    public static final java.lang.String EXTRA_VMS = "vms";
    public static final int OIR_DEFAULT = 0;
    public static final int OIR_PRESENTATION_NOT_RESTRICTED = 2;
    public static final int OIR_PRESENTATION_PAYPHONE = 4;
    public static final int OIR_PRESENTATION_RESTRICTED = 1;
    public static final int OIR_PRESENTATION_UNAVAILABLE = 5;
    public static final int OIR_PRESENTATION_UNKNOWN = 3;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_URGENT = 1;
    public static final int SERVICE_TYPE_EMERGENCY = 2;
    public static final int SERVICE_TYPE_NONE = 0;
    public static final int SERVICE_TYPE_NORMAL = 1;
    private static final java.lang.String TAG = "ImsCallProfile";
    public static final int VERIFICATION_STATUS_FAILED = 2;
    public static final int VERIFICATION_STATUS_NOT_VERIFIED = 0;
    public static final int VERIFICATION_STATUS_PASSED = 1;
    private java.util.Set<android.telephony.ims.RtpHeaderExtensionType> mAcceptedRtpHeaderExtensionTypes;
    public android.os.Bundle mCallExtras;
    public int mCallType;
    private int mCallerNumberVerificationStatus;
    private int mEmergencyCallRouting;
    private boolean mEmergencyCallTesting;
    private int mEmergencyServiceCategories;
    private java.util.List<java.lang.String> mEmergencyUrns;
    private boolean mHasKnownUserIntentEmergency;
    public android.telephony.ims.ImsStreamMediaProfile mMediaProfile;
    public int mRestrictCause;
    public int mServiceType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallRestrictCause {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VerificationStatus {
    }

    public ImsCallProfile(android.os.Parcel parcel) {
        this.mRestrictCause = 0;
        this.mEmergencyServiceCategories = 0;
        this.mEmergencyUrns = new java.util.ArrayList();
        this.mEmergencyCallRouting = 0;
        this.mEmergencyCallTesting = false;
        this.mHasKnownUserIntentEmergency = false;
        this.mAcceptedRtpHeaderExtensionTypes = new android.util.ArraySet();
        readFromParcel(parcel);
    }

    public ImsCallProfile() {
        this.mRestrictCause = 0;
        this.mEmergencyServiceCategories = 0;
        this.mEmergencyUrns = new java.util.ArrayList();
        this.mEmergencyCallRouting = 0;
        this.mEmergencyCallTesting = false;
        this.mHasKnownUserIntentEmergency = false;
        this.mAcceptedRtpHeaderExtensionTypes = new android.util.ArraySet();
        this.mServiceType = 1;
        this.mCallType = 1;
        this.mCallExtras = new android.os.Bundle();
        this.mMediaProfile = new android.telephony.ims.ImsStreamMediaProfile();
    }

    public ImsCallProfile(int i, int i2) {
        this.mRestrictCause = 0;
        this.mEmergencyServiceCategories = 0;
        this.mEmergencyUrns = new java.util.ArrayList();
        this.mEmergencyCallRouting = 0;
        this.mEmergencyCallTesting = false;
        this.mHasKnownUserIntentEmergency = false;
        this.mAcceptedRtpHeaderExtensionTypes = new android.util.ArraySet();
        this.mServiceType = i;
        this.mCallType = i2;
        this.mCallExtras = new android.os.Bundle();
        this.mMediaProfile = new android.telephony.ims.ImsStreamMediaProfile();
    }

    public ImsCallProfile(int i, int i2, android.os.Bundle bundle, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        this.mRestrictCause = 0;
        this.mEmergencyServiceCategories = 0;
        this.mEmergencyUrns = new java.util.ArrayList();
        this.mEmergencyCallRouting = 0;
        this.mEmergencyCallTesting = false;
        this.mHasKnownUserIntentEmergency = false;
        this.mAcceptedRtpHeaderExtensionTypes = new android.util.ArraySet();
        this.mServiceType = i;
        this.mCallType = i2;
        this.mCallExtras = bundle;
        this.mMediaProfile = imsStreamMediaProfile;
    }

    public java.lang.String getCallExtra(java.lang.String str) {
        return getCallExtra(str, "");
    }

    public java.lang.String getCallExtra(java.lang.String str, java.lang.String str2) {
        if (this.mCallExtras == null) {
            return str2;
        }
        return this.mCallExtras.getString(str, str2);
    }

    public boolean getCallExtraBoolean(java.lang.String str) {
        return getCallExtraBoolean(str, false);
    }

    public boolean getCallExtraBoolean(java.lang.String str, boolean z) {
        if (this.mCallExtras == null) {
            return z;
        }
        return this.mCallExtras.getBoolean(str, z);
    }

    public int getCallExtraInt(java.lang.String str) {
        return getCallExtraInt(str, -1);
    }

    public int getCallExtraInt(java.lang.String str, int i) {
        if (this.mCallExtras == null) {
            return i;
        }
        return this.mCallExtras.getInt(str, i);
    }

    public <T extends android.os.Parcelable> T getCallExtraParcelable(java.lang.String str) {
        if (this.mCallExtras != null) {
            return (T) this.mCallExtras.getParcelable(str);
        }
        return null;
    }

    public void setCallExtra(java.lang.String str, java.lang.String str2) {
        if (this.mCallExtras != null) {
            this.mCallExtras.putString(str, str2);
        }
    }

    public void setCallExtraBoolean(java.lang.String str, boolean z) {
        if (this.mCallExtras != null) {
            this.mCallExtras.putBoolean(str, z);
        }
    }

    public void setCallExtraInt(java.lang.String str, int i) {
        if (this.mCallExtras != null) {
            this.mCallExtras.putInt(str, i);
        }
    }

    public void setCallExtraParcelable(java.lang.String str, android.os.Parcelable parcelable) {
        if (this.mCallExtras != null) {
            this.mCallExtras.putParcelable(str, parcelable);
        }
    }

    public void setCallRestrictCause(int i) {
        this.mRestrictCause = i;
    }

    public void updateCallType(android.telephony.ims.ImsCallProfile imsCallProfile) {
        this.mCallType = imsCallProfile.mCallType;
    }

    public void updateCallExtras(android.telephony.ims.ImsCallProfile imsCallProfile) {
        this.mCallExtras.clear();
        this.mCallExtras = (android.os.Bundle) imsCallProfile.mCallExtras.clone();
    }

    public void updateMediaProfile(android.telephony.ims.ImsCallProfile imsCallProfile) {
        this.mMediaProfile = imsCallProfile.mMediaProfile;
    }

    public void setCallerNumberVerificationStatus(int i) {
        this.mCallerNumberVerificationStatus = i;
    }

    public int getCallerNumberVerificationStatus() {
        return this.mCallerNumberVerificationStatus;
    }

    public java.lang.String toString() {
        return "{ serviceType=" + this.mServiceType + ", callType=" + this.mCallType + ", restrictCause=" + this.mRestrictCause + ", mediaProfile=" + (this.mMediaProfile != null ? this.mMediaProfile.toString() : "null") + ", emergencyServiceCategories=" + this.mEmergencyServiceCategories + ", emergencyUrns=" + this.mEmergencyUrns + ", emergencyCallRouting=" + this.mEmergencyCallRouting + ", emergencyCallTesting=" + this.mEmergencyCallTesting + ", hasKnownUserIntentEmergency=" + this.mHasKnownUserIntentEmergency + ", mRestrictCause=" + this.mRestrictCause + ", mCallerNumberVerstat= " + this.mCallerNumberVerificationStatus + ", mAcceptedRtpHeaderExtensions= " + this.mAcceptedRtpHeaderExtensionTypes + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.os.Bundle maybeCleanseExtras = maybeCleanseExtras(this.mCallExtras);
        parcel.writeInt(this.mServiceType);
        parcel.writeInt(this.mCallType);
        parcel.writeBundle(maybeCleanseExtras);
        parcel.writeParcelable(this.mMediaProfile, 0);
        parcel.writeInt(this.mEmergencyServiceCategories);
        parcel.writeStringList(this.mEmergencyUrns);
        parcel.writeInt(this.mEmergencyCallRouting);
        parcel.writeBoolean(this.mEmergencyCallTesting);
        parcel.writeBoolean(this.mHasKnownUserIntentEmergency);
        parcel.writeInt(this.mRestrictCause);
        parcel.writeInt(this.mCallerNumberVerificationStatus);
        parcel.writeArray(this.mAcceptedRtpHeaderExtensionTypes.toArray());
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mServiceType = parcel.readInt();
        this.mCallType = parcel.readInt();
        this.mCallExtras = parcel.readBundle();
        this.mMediaProfile = (android.telephony.ims.ImsStreamMediaProfile) parcel.readParcelable(android.telephony.ims.ImsStreamMediaProfile.class.getClassLoader(), android.telephony.ims.ImsStreamMediaProfile.class);
        this.mEmergencyServiceCategories = parcel.readInt();
        this.mEmergencyUrns = parcel.createStringArrayList();
        this.mEmergencyCallRouting = parcel.readInt();
        this.mEmergencyCallTesting = parcel.readBoolean();
        this.mHasKnownUserIntentEmergency = parcel.readBoolean();
        this.mRestrictCause = parcel.readInt();
        this.mCallerNumberVerificationStatus = parcel.readInt();
        this.mAcceptedRtpHeaderExtensionTypes = (java.util.Set) java.util.Arrays.stream(parcel.readArray(android.telephony.ims.RtpHeaderExtensionType.class.getClassLoader(), android.telephony.ims.RtpHeaderExtensionType.class)).map(new java.util.function.Function() { // from class: android.telephony.ims.ImsCallProfile$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.telephony.ims.ImsCallProfile.lambda$readFromParcel$0(obj);
            }
        }).collect(java.util.stream.Collectors.toSet());
    }

    static /* synthetic */ android.telephony.ims.RtpHeaderExtensionType lambda$readFromParcel$0(java.lang.Object obj) {
        return (android.telephony.ims.RtpHeaderExtensionType) obj;
    }

    public int getServiceType() {
        return this.mServiceType;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public int getRestrictCause() {
        return this.mRestrictCause;
    }

    public android.os.Bundle getCallExtras() {
        return this.mCallExtras;
    }

    public android.os.Bundle getProprietaryCallExtras() {
        if (this.mCallExtras == null) {
            return new android.os.Bundle();
        }
        android.os.Bundle bundle = this.mCallExtras.getBundle(EXTRA_OEM_EXTRAS);
        if (bundle == null) {
            return new android.os.Bundle();
        }
        return new android.os.Bundle(bundle);
    }

    public android.telephony.ims.ImsStreamMediaProfile getMediaProfile() {
        return this.mMediaProfile;
    }

    public static int getVideoStateFromImsCallProfile(android.telephony.ims.ImsCallProfile imsCallProfile) {
        int videoStateFromCallType = getVideoStateFromCallType(imsCallProfile.mCallType);
        if (imsCallProfile.isVideoPaused() && !android.telecom.VideoProfile.isAudioOnly(videoStateFromCallType)) {
            return videoStateFromCallType | 4;
        }
        return videoStateFromCallType & (-5);
    }

    public static int getVideoStateFromCallType(int i) {
        switch (i) {
            case 2:
            case 3:
            default:
                return 0;
            case 4:
                return 3;
            case 5:
                return 1;
            case 6:
                return 2;
        }
    }

    public static int getCallTypeFromVideoState(int i) {
        boolean isVideoStateSet = isVideoStateSet(i, 1);
        boolean isVideoStateSet2 = isVideoStateSet(i, 2);
        if (isVideoStateSet(i, 4)) {
            return 7;
        }
        if (isVideoStateSet && !isVideoStateSet2) {
            return 5;
        }
        if (!isVideoStateSet && isVideoStateSet2) {
            return 6;
        }
        if (!isVideoStateSet || !isVideoStateSet2) {
            return 2;
        }
        return 4;
    }

    public static int presentationToOIR(int i) {
        switch (i) {
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return 0;
        }
    }

    public static int presentationToOir(int i) {
        return presentationToOIR(i);
    }

    public static int OIRToPresentation(int i) {
        switch (i) {
        }
        return 3;
    }

    public boolean isVideoPaused() {
        return this.mMediaProfile.mVideoDirection == 0;
    }

    public boolean isVideoCall() {
        return android.telecom.VideoProfile.isVideo(getVideoStateFromCallType(this.mCallType));
    }

    private android.os.Bundle maybeCleanseExtras(android.os.Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        int size = bundle.size();
        android.os.Bundle filterValues = com.android.internal.telephony.util.TelephonyUtils.filterValues(bundle);
        int size2 = filterValues.size();
        if (size != size2) {
            android.util.Log.i(TAG, "maybeCleanseExtras: " + (size - size2) + " extra values were removed - only primitive types and system parcelables are permitted.");
        }
        return filterValues;
    }

    private static boolean isVideoStateSet(int i, int i2) {
        return (i & i2) == i2;
    }

    public void setEmergencyCallInfo(android.telephony.emergency.EmergencyNumber emergencyNumber, boolean z) {
        setEmergencyServiceCategories(emergencyNumber.getEmergencyServiceCategoryBitmaskInternalDial());
        setEmergencyUrns(emergencyNumber.getEmergencyUrns());
        setEmergencyCallRouting(emergencyNumber.getEmergencyCallRouting());
        setEmergencyCallTesting(emergencyNumber.getEmergencyNumberSourceBitmask() == 32);
        setHasKnownUserIntentEmergency(z);
    }

    public void setEmergencyServiceCategories(int i) {
        this.mEmergencyServiceCategories = i;
    }

    public void setEmergencyUrns(java.util.List<java.lang.String> list) {
        this.mEmergencyUrns = list;
    }

    public void setEmergencyCallRouting(int i) {
        this.mEmergencyCallRouting = i;
    }

    public void setEmergencyCallTesting(boolean z) {
        this.mEmergencyCallTesting = z;
    }

    public void setHasKnownUserIntentEmergency(boolean z) {
        this.mHasKnownUserIntentEmergency = z;
    }

    public int getEmergencyServiceCategories() {
        return this.mEmergencyServiceCategories;
    }

    public java.util.List<java.lang.String> getEmergencyUrns() {
        return this.mEmergencyUrns;
    }

    public int getEmergencyCallRouting() {
        return this.mEmergencyCallRouting;
    }

    public boolean isEmergencyCallTesting() {
        return this.mEmergencyCallTesting;
    }

    public boolean hasKnownUserIntentEmergency() {
        return this.mHasKnownUserIntentEmergency;
    }

    public java.util.Set<android.telephony.ims.RtpHeaderExtensionType> getAcceptedRtpHeaderExtensionTypes() {
        return this.mAcceptedRtpHeaderExtensionTypes;
    }

    public void setAcceptedRtpHeaderExtensionTypes(java.util.Set<android.telephony.ims.RtpHeaderExtensionType> set) {
        this.mAcceptedRtpHeaderExtensionTypes.clear();
        this.mAcceptedRtpHeaderExtensionTypes.addAll(set);
    }
}
