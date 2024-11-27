package android.telephony;

/* loaded from: classes3.dex */
public class SubscriptionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.SubscriptionInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.SubscriptionInfo>() { // from class: android.telephony.SubscriptionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SubscriptionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SubscriptionInfo.Builder().setId(parcel.readInt()).setIccId(parcel.readString()).setSimSlotIndex(parcel.readInt()).setDisplayName(android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)).setCarrierName(android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)).setDisplayNameSource(parcel.readInt()).setIconTint(parcel.readInt()).setNumber(parcel.readString()).setDataRoaming(parcel.readInt()).setMcc(parcel.readString()).setMnc(parcel.readString()).setCountryIso(parcel.readString()).setEmbedded(parcel.readBoolean()).setNativeAccessRules((android.telephony.UiccAccessRule[]) parcel.createTypedArray(android.telephony.UiccAccessRule.CREATOR)).setCardString(parcel.readString()).setCardId(parcel.readInt()).setPortIndex(parcel.readInt()).setOpportunistic(parcel.readBoolean()).setGroupUuid(parcel.readString8()).setGroupDisabled(parcel.readBoolean()).setCarrierId(parcel.readInt()).setProfileClass(parcel.readInt()).setType(parcel.readInt()).setEhplmns(parcel.createStringArray()).setHplmns(parcel.createStringArray()).setGroupOwner(parcel.readString()).setCarrierConfigAccessRules((android.telephony.UiccAccessRule[]) parcel.createTypedArray(android.telephony.UiccAccessRule.CREATOR)).setUiccApplicationsEnabled(parcel.readBoolean()).setUsageSetting(parcel.readInt()).setOnlyNonTerrestrialNetwork(parcel.readBoolean()).setServiceCapabilities(android.telephony.SubscriptionManager.getServiceCapabilitiesSet(parcel.readInt())).setTransferStatus(parcel.readInt()).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SubscriptionInfo[] newArray(int i) {
            return new android.telephony.SubscriptionInfo[i];
        }
    };
    private static final int TEXT_SIZE = 16;
    private final boolean mAreUiccApplicationsEnabled;
    private final int mCardId;
    private final java.lang.String mCardString;
    private final android.telephony.UiccAccessRule[] mCarrierConfigAccessRules;
    private final int mCarrierId;
    private final java.lang.CharSequence mCarrierName;
    private final java.lang.String mCountryIso;
    private final int mDataRoaming;
    private final java.lang.CharSequence mDisplayName;
    private final int mDisplayNameSource;
    private final java.lang.String[] mEhplmns;
    private final java.lang.String mGroupOwner;
    private final android.os.ParcelUuid mGroupUuid;
    private final java.lang.String[] mHplmns;
    private final java.lang.String mIccId;
    private android.graphics.Bitmap mIconBitmap;
    private final int mIconTint;
    private final int mId;
    private final boolean mIsEmbedded;
    private final boolean mIsGroupDisabled;
    private final boolean mIsOnlyNonTerrestrialNetwork;
    private final boolean mIsOpportunistic;
    private final java.lang.String mMcc;
    private final java.lang.String mMnc;
    private final android.telephony.UiccAccessRule[] mNativeAccessRules;
    private final java.lang.String mNumber;
    private final int mPortIndex;
    private final int mProfileClass;
    private final int mServiceCapabilities;
    private final int mSimSlotIndex;
    private final int mTransferStatus;
    private final int mType;
    private final int mUsageSetting;

    @java.lang.Deprecated
    public SubscriptionInfo(int i, java.lang.String str, int i2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i3, int i4, java.lang.String str2, int i5, android.graphics.Bitmap bitmap, java.lang.String str3, java.lang.String str4, java.lang.String str5, boolean z, android.telephony.UiccAccessRule[] uiccAccessRuleArr, java.lang.String str6) {
        this(i, str, i2, charSequence, charSequence2, i3, i4, str2, i5, bitmap, str3, str4, str5, z, uiccAccessRuleArr, str6, -1, false, null, false, -1, -1, 0, null, null, true);
    }

    @java.lang.Deprecated
    public SubscriptionInfo(int i, java.lang.String str, int i2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i3, int i4, java.lang.String str2, int i5, android.graphics.Bitmap bitmap, java.lang.String str3, java.lang.String str4, java.lang.String str5, boolean z, android.telephony.UiccAccessRule[] uiccAccessRuleArr, java.lang.String str6, boolean z2, java.lang.String str7, int i6, int i7) {
        this(i, str, i2, charSequence, charSequence2, i3, i4, str2, i5, bitmap, str3, str4, str5, z, uiccAccessRuleArr, str6, -1, z2, str7, false, i6, i7, 0, null, null, true);
    }

    @java.lang.Deprecated
    public SubscriptionInfo(int i, java.lang.String str, int i2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i3, int i4, java.lang.String str2, int i5, android.graphics.Bitmap bitmap, java.lang.String str3, java.lang.String str4, java.lang.String str5, boolean z, android.telephony.UiccAccessRule[] uiccAccessRuleArr, java.lang.String str6, int i6, boolean z2, java.lang.String str7, boolean z3, int i7, int i8, int i9, java.lang.String str8, android.telephony.UiccAccessRule[] uiccAccessRuleArr2, boolean z4) {
        this(i, str, i2, charSequence, charSequence2, i3, i4, str2, i5, bitmap, str3, str4, str5, z, uiccAccessRuleArr, str6, i6, z2, str7, z3, i7, i8, i9, str8, uiccAccessRuleArr2, z4, 0);
    }

    @java.lang.Deprecated
    public SubscriptionInfo(int i, java.lang.String str, int i2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i3, int i4, java.lang.String str2, int i5, android.graphics.Bitmap bitmap, java.lang.String str3, java.lang.String str4, java.lang.String str5, boolean z, android.telephony.UiccAccessRule[] uiccAccessRuleArr, java.lang.String str6, int i6, boolean z2, java.lang.String str7, boolean z3, int i7, int i8, int i9, java.lang.String str8, android.telephony.UiccAccessRule[] uiccAccessRuleArr2, boolean z4, int i10) {
        this(i, str, i2, charSequence, charSequence2, i3, i4, str2, i5, bitmap, str3, str4, str5, z, uiccAccessRuleArr, str6, i6, z2, str7, z3, i7, i8, i9, str8, uiccAccessRuleArr2, z4, i10, 0);
    }

    @java.lang.Deprecated
    public SubscriptionInfo(int i, java.lang.String str, int i2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i3, int i4, java.lang.String str2, int i5, android.graphics.Bitmap bitmap, java.lang.String str3, java.lang.String str4, java.lang.String str5, boolean z, android.telephony.UiccAccessRule[] uiccAccessRuleArr, java.lang.String str6, int i6, boolean z2, java.lang.String str7, boolean z3, int i7, int i8, int i9, java.lang.String str8, android.telephony.UiccAccessRule[] uiccAccessRuleArr2, boolean z4, int i10, int i11) {
        this.mId = i;
        this.mIccId = str;
        this.mSimSlotIndex = i2;
        this.mDisplayName = charSequence;
        this.mCarrierName = charSequence2;
        this.mDisplayNameSource = i3;
        this.mIconTint = i4;
        this.mNumber = str2;
        this.mDataRoaming = i5;
        this.mIconBitmap = bitmap;
        this.mMcc = android.text.TextUtils.emptyIfNull(str3);
        this.mMnc = android.text.TextUtils.emptyIfNull(str4);
        this.mHplmns = null;
        this.mEhplmns = null;
        this.mCountryIso = android.text.TextUtils.emptyIfNull(str5);
        this.mIsEmbedded = z;
        this.mNativeAccessRules = uiccAccessRuleArr;
        this.mCardString = android.text.TextUtils.emptyIfNull(str6);
        this.mCardId = i6;
        this.mIsOpportunistic = z2;
        this.mGroupUuid = str7 != null ? android.os.ParcelUuid.fromString(str7) : null;
        this.mIsGroupDisabled = z3;
        this.mCarrierId = i7;
        this.mProfileClass = i8;
        this.mType = i9;
        this.mGroupOwner = android.text.TextUtils.emptyIfNull(str8);
        this.mCarrierConfigAccessRules = uiccAccessRuleArr2;
        this.mAreUiccApplicationsEnabled = z4;
        this.mPortIndex = i10;
        this.mUsageSetting = i11;
        this.mIsOnlyNonTerrestrialNetwork = false;
        this.mServiceCapabilities = 0;
        this.mTransferStatus = 0;
    }

    private SubscriptionInfo(android.telephony.SubscriptionInfo.Builder builder) {
        this.mId = builder.mId;
        this.mIccId = builder.mIccId;
        this.mSimSlotIndex = builder.mSimSlotIndex;
        this.mDisplayName = builder.mDisplayName;
        this.mCarrierName = builder.mCarrierName;
        this.mDisplayNameSource = builder.mDisplayNameSource;
        this.mIconTint = builder.mIconTint;
        this.mNumber = builder.mNumber;
        this.mDataRoaming = builder.mDataRoaming;
        this.mIconBitmap = builder.mIconBitmap;
        this.mMcc = builder.mMcc;
        this.mMnc = builder.mMnc;
        this.mEhplmns = builder.mEhplmns;
        this.mHplmns = builder.mHplmns;
        this.mCountryIso = builder.mCountryIso;
        this.mIsEmbedded = builder.mIsEmbedded;
        this.mNativeAccessRules = builder.mNativeAccessRules;
        this.mCardString = builder.mCardString;
        this.mCardId = builder.mCardId;
        this.mIsOpportunistic = builder.mIsOpportunistic;
        this.mGroupUuid = builder.mGroupUuid;
        this.mIsGroupDisabled = builder.mIsGroupDisabled;
        this.mCarrierId = builder.mCarrierId;
        this.mProfileClass = builder.mProfileClass;
        this.mType = builder.mType;
        this.mGroupOwner = builder.mGroupOwner;
        this.mCarrierConfigAccessRules = builder.mCarrierConfigAccessRules;
        this.mAreUiccApplicationsEnabled = builder.mAreUiccApplicationsEnabled;
        this.mPortIndex = builder.mPortIndex;
        this.mUsageSetting = builder.mUsageSetting;
        this.mIsOnlyNonTerrestrialNetwork = builder.mIsOnlyNonTerrestrialNetwork;
        this.mServiceCapabilities = builder.mServiceCapabilities;
        this.mTransferStatus = builder.mTransferStatus;
    }

    public int getSubscriptionId() {
        return this.mId;
    }

    public java.lang.String getIccId() {
        return this.mIccId;
    }

    public int getSimSlotIndex() {
        return this.mSimSlotIndex;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public java.lang.CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public java.lang.CharSequence getCarrierName() {
        return this.mCarrierName;
    }

    public int getDisplayNameSource() {
        return this.mDisplayNameSource;
    }

    public android.graphics.Bitmap createIconBitmap(android.content.Context context) {
        if (this.mIconBitmap == null) {
            this.mIconBitmap = android.graphics.BitmapFactory.decodeResource(context.getResources(), com.android.internal.R.drawable.ic_sim_card_multi_24px_clr);
        }
        int width = this.mIconBitmap.getWidth();
        int height = this.mIconBitmap.getHeight();
        android.util.DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(displayMetrics, width, height, this.mIconBitmap.getConfig());
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setColorFilter(new android.graphics.PorterDuffColorFilter(this.mIconTint, android.graphics.PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(this.mIconBitmap, 0.0f, 0.0f, paint);
        paint.setColorFilter(null);
        paint.setAntiAlias(true);
        paint.setTypeface(android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT_FAMILY, 0));
        paint.setColor(-1);
        paint.setTextSize(displayMetrics.density * 16.0f);
        java.lang.String formatSimple = android.text.TextUtils.formatSimple("%d", java.lang.Integer.valueOf(this.mSimSlotIndex + 1));
        paint.getTextBounds(formatSimple, 0, 1, new android.graphics.Rect());
        canvas.drawText(formatSimple, (width / 2.0f) - r6.centerX(), (height / 2.0f) - r6.centerY(), paint);
        return createBitmap;
    }

    public int getIconTint() {
        return this.mIconTint;
    }

    @java.lang.Deprecated
    public java.lang.String getNumber() {
        return this.mNumber;
    }

    public int getDataRoaming() {
        return this.mDataRoaming;
    }

    @java.lang.Deprecated
    public int getMcc() {
        try {
            return this.mMcc != null ? java.lang.Integer.parseInt(this.mMcc) : 0;
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.w(android.telephony.SubscriptionInfo.class.getSimpleName(), "MCC string is not a number");
            return 0;
        }
    }

    @java.lang.Deprecated
    public int getMnc() {
        try {
            return this.mMnc != null ? java.lang.Integer.parseInt(this.mMnc) : 0;
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.w(android.telephony.SubscriptionInfo.class.getSimpleName(), "MNC string is not a number");
            return 0;
        }
    }

    public java.lang.String getMccString() {
        return this.mMcc;
    }

    public java.lang.String getMncString() {
        return this.mMnc;
    }

    public java.lang.String getCountryIso() {
        return this.mCountryIso;
    }

    public boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public boolean isOpportunistic() {
        return this.mIsOpportunistic;
    }

    public boolean isActive() {
        return this.mSimSlotIndex >= 0 || this.mType == 1;
    }

    public android.os.ParcelUuid getGroupUuid() {
        return this.mGroupUuid;
    }

    public java.util.List<java.lang.String> getEhplmns() {
        return java.util.Collections.unmodifiableList(this.mEhplmns == null ? java.util.Collections.emptyList() : java.util.Arrays.asList(this.mEhplmns));
    }

    public java.util.List<java.lang.String> getHplmns() {
        return java.util.Collections.unmodifiableList(this.mHplmns == null ? java.util.Collections.emptyList() : java.util.Arrays.asList(this.mHplmns));
    }

    public java.lang.String getGroupOwner() {
        return this.mGroupOwner;
    }

    @android.annotation.SystemApi
    public int getProfileClass() {
        return this.mProfileClass;
    }

    public int getSubscriptionType() {
        return this.mType;
    }

    @java.lang.Deprecated
    public boolean canManageSubscription(android.content.Context context) {
        return canManageSubscription(context, context.getPackageName());
    }

    @java.lang.Deprecated
    public boolean canManageSubscription(android.content.Context context, java.lang.String str) {
        java.util.List<android.telephony.UiccAccessRule> accessRules = getAccessRules();
        if (accessRules == null) {
            return false;
        }
        try {
            android.content.pm.PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 134217728);
            java.util.Iterator<android.telephony.UiccAccessRule> it = accessRules.iterator();
            while (it.hasNext()) {
                if (it.next().getCarrierPrivilegeStatus(packageInfo) == 1) {
                    return true;
                }
            }
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.d("SubscriptionInfo", "canManageSubscription: Unknown package: " + str, e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.UiccAccessRule> getAccessRules() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (this.mNativeAccessRules != null) {
            arrayList.addAll(java.util.Arrays.asList(this.mNativeAccessRules));
        }
        if (this.mCarrierConfigAccessRules != null) {
            arrayList.addAll(java.util.Arrays.asList(this.mCarrierConfigAccessRules));
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    public java.lang.String getCardString() {
        return this.mCardString;
    }

    public int getCardId() {
        return this.mCardId;
    }

    public int getPortIndex() {
        return this.mPortIndex;
    }

    @android.annotation.SystemApi
    public boolean isGroupDisabled() {
        return this.mIsGroupDisabled;
    }

    @android.annotation.SystemApi
    public boolean areUiccApplicationsEnabled() {
        return this.mAreUiccApplicationsEnabled;
    }

    public int getUsageSetting() {
        return this.mUsageSetting;
    }

    public boolean isOnlyNonTerrestrialNetwork() {
        return this.mIsOnlyNonTerrestrialNetwork;
    }

    public java.util.Set<java.lang.Integer> getServiceCapabilities() {
        return android.telephony.SubscriptionManager.getServiceCapabilitiesSet(this.mServiceCapabilities);
    }

    @android.annotation.SystemApi
    public int getTransferStatus() {
        return this.mTransferStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mIccId);
        parcel.writeInt(this.mSimSlotIndex);
        android.text.TextUtils.writeToParcel(this.mDisplayName, parcel, 0);
        android.text.TextUtils.writeToParcel(this.mCarrierName, parcel, 0);
        parcel.writeInt(this.mDisplayNameSource);
        parcel.writeInt(this.mIconTint);
        parcel.writeString(this.mNumber);
        parcel.writeInt(this.mDataRoaming);
        parcel.writeString(this.mMcc);
        parcel.writeString(this.mMnc);
        parcel.writeString(this.mCountryIso);
        parcel.writeBoolean(this.mIsEmbedded);
        parcel.writeTypedArray(this.mNativeAccessRules, i);
        parcel.writeString(this.mCardString);
        parcel.writeInt(this.mCardId);
        parcel.writeInt(this.mPortIndex);
        parcel.writeBoolean(this.mIsOpportunistic);
        parcel.writeString8(this.mGroupUuid == null ? null : this.mGroupUuid.toString());
        parcel.writeBoolean(this.mIsGroupDisabled);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mProfileClass);
        parcel.writeInt(this.mType);
        parcel.writeStringArray(this.mEhplmns);
        parcel.writeStringArray(this.mHplmns);
        parcel.writeString(this.mGroupOwner);
        parcel.writeTypedArray(this.mCarrierConfigAccessRules, i);
        parcel.writeBoolean(this.mAreUiccApplicationsEnabled);
        parcel.writeInt(this.mUsageSetting);
        parcel.writeBoolean(this.mIsOnlyNonTerrestrialNetwork);
        parcel.writeInt(this.mServiceCapabilities);
        parcel.writeInt(this.mTransferStatus);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static java.lang.String getPrintableId(java.lang.String str) {
        if (str == null) {
            return null;
        }
        if (str.length() > 9 && !com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE) {
            return str.substring(0, 9) + com.android.telephony.Rlog.pii(false, (java.lang.Object) str.substring(9));
        }
        return str;
    }

    public java.lang.String toString() {
        return "[SubscriptionInfo: id=" + this.mId + " iccId=" + getPrintableId(this.mIccId) + " simSlotIndex=" + this.mSimSlotIndex + " portIndex=" + this.mPortIndex + " isEmbedded=" + this.mIsEmbedded + " carrierId=" + this.mCarrierId + " displayName=" + ((java.lang.Object) this.mDisplayName) + " carrierName=" + ((java.lang.Object) this.mCarrierName) + " isOpportunistic=" + this.mIsOpportunistic + " groupUuid=" + this.mGroupUuid + " groupOwner=" + this.mGroupOwner + " isGroupDisabled=" + this.mIsGroupDisabled + " displayNameSource=" + android.telephony.SubscriptionManager.displayNameSourceToString(this.mDisplayNameSource) + " iconTint=" + this.mIconTint + " number=" + com.android.telephony.Rlog.pii(com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE, this.mNumber) + " dataRoaming=" + this.mDataRoaming + " mcc=" + this.mMcc + " mnc=" + this.mMnc + " ehplmns=" + java.util.Arrays.toString(this.mEhplmns) + " hplmns=" + java.util.Arrays.toString(this.mHplmns) + " cardString=" + getPrintableId(this.mCardString) + " cardId=" + this.mCardId + " nativeAccessRules=" + java.util.Arrays.toString(this.mNativeAccessRules) + " carrierConfigAccessRules=" + java.util.Arrays.toString(this.mCarrierConfigAccessRules) + " countryIso=" + this.mCountryIso + " profileClass=" + this.mProfileClass + " mType=" + android.telephony.SubscriptionManager.subscriptionTypeToString(this.mType) + " areUiccApplicationsEnabled=" + this.mAreUiccApplicationsEnabled + " usageSetting=" + android.telephony.SubscriptionManager.usageSettingToString(this.mUsageSetting) + " isOnlyNonTerrestrialNetwork=" + this.mIsOnlyNonTerrestrialNetwork + " serviceCapabilities=" + android.telephony.SubscriptionManager.getServiceCapabilitiesSet(this.mServiceCapabilities).toString() + " transferStatus=" + this.mTransferStatus + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.SubscriptionInfo subscriptionInfo = (android.telephony.SubscriptionInfo) obj;
        if (this.mId == subscriptionInfo.mId && this.mSimSlotIndex == subscriptionInfo.mSimSlotIndex && this.mDisplayNameSource == subscriptionInfo.mDisplayNameSource && this.mIconTint == subscriptionInfo.mIconTint && this.mDataRoaming == subscriptionInfo.mDataRoaming && this.mIsEmbedded == subscriptionInfo.mIsEmbedded && this.mIsOpportunistic == subscriptionInfo.mIsOpportunistic && this.mCarrierId == subscriptionInfo.mCarrierId && this.mProfileClass == subscriptionInfo.mProfileClass && this.mType == subscriptionInfo.mType && this.mAreUiccApplicationsEnabled == subscriptionInfo.mAreUiccApplicationsEnabled && this.mPortIndex == subscriptionInfo.mPortIndex && this.mUsageSetting == subscriptionInfo.mUsageSetting && this.mCardId == subscriptionInfo.mCardId && this.mIsGroupDisabled == subscriptionInfo.mIsGroupDisabled && this.mIccId.equals(subscriptionInfo.mIccId) && this.mDisplayName.equals(subscriptionInfo.mDisplayName) && this.mCarrierName.equals(subscriptionInfo.mCarrierName) && this.mNumber.equals(subscriptionInfo.mNumber) && java.util.Objects.equals(this.mMcc, subscriptionInfo.mMcc) && java.util.Objects.equals(this.mMnc, subscriptionInfo.mMnc) && java.util.Arrays.equals(this.mEhplmns, subscriptionInfo.mEhplmns) && java.util.Arrays.equals(this.mHplmns, subscriptionInfo.mHplmns) && this.mCardString.equals(subscriptionInfo.mCardString) && java.util.Arrays.equals(this.mNativeAccessRules, subscriptionInfo.mNativeAccessRules) && java.util.Arrays.equals(this.mCarrierConfigAccessRules, subscriptionInfo.mCarrierConfigAccessRules) && java.util.Objects.equals(this.mGroupUuid, subscriptionInfo.mGroupUuid) && this.mCountryIso.equals(subscriptionInfo.mCountryIso) && this.mGroupOwner.equals(subscriptionInfo.mGroupOwner) && this.mIsOnlyNonTerrestrialNetwork == subscriptionInfo.mIsOnlyNonTerrestrialNetwork && this.mServiceCapabilities == subscriptionInfo.mServiceCapabilities && this.mTransferStatus == subscriptionInfo.mTransferStatus) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), this.mIccId, java.lang.Integer.valueOf(this.mSimSlotIndex), this.mDisplayName, this.mCarrierName, java.lang.Integer.valueOf(this.mDisplayNameSource), java.lang.Integer.valueOf(this.mIconTint), this.mNumber, java.lang.Integer.valueOf(this.mDataRoaming), this.mMcc, this.mMnc, java.lang.Boolean.valueOf(this.mIsEmbedded), this.mCardString, java.lang.Boolean.valueOf(this.mIsOpportunistic), this.mGroupUuid, this.mCountryIso, java.lang.Integer.valueOf(this.mCarrierId), java.lang.Integer.valueOf(this.mProfileClass), java.lang.Integer.valueOf(this.mType), this.mGroupOwner, java.lang.Boolean.valueOf(this.mAreUiccApplicationsEnabled), java.lang.Integer.valueOf(this.mPortIndex), java.lang.Integer.valueOf(this.mUsageSetting), java.lang.Integer.valueOf(this.mCardId), java.lang.Boolean.valueOf(this.mIsGroupDisabled), java.lang.Boolean.valueOf(this.mIsOnlyNonTerrestrialNetwork), java.lang.Integer.valueOf(this.mServiceCapabilities), java.lang.Integer.valueOf(this.mTransferStatus)) * 31) + java.util.Arrays.hashCode(this.mEhplmns)) * 31) + java.util.Arrays.hashCode(this.mHplmns)) * 31) + java.util.Arrays.hashCode(this.mNativeAccessRules)) * 31) + java.util.Arrays.hashCode(this.mCarrierConfigAccessRules);
    }

    public static class Builder {
        private boolean mAreUiccApplicationsEnabled;
        private int mCardId;
        private java.lang.String mCardString;
        private android.telephony.UiccAccessRule[] mCarrierConfigAccessRules;
        private int mCarrierId;
        private java.lang.CharSequence mCarrierName;
        private java.lang.String mCountryIso;
        private int mDataRoaming;
        private java.lang.CharSequence mDisplayName;
        private int mDisplayNameSource;
        private java.lang.String[] mEhplmns;
        private java.lang.String mGroupOwner;
        private android.os.ParcelUuid mGroupUuid;
        private java.lang.String[] mHplmns;
        private java.lang.String mIccId;
        private android.graphics.Bitmap mIconBitmap;
        private int mIconTint;
        private int mId;
        private boolean mIsEmbedded;
        private boolean mIsGroupDisabled;
        private boolean mIsOnlyNonTerrestrialNetwork;
        private boolean mIsOpportunistic;
        private java.lang.String mMcc;
        private java.lang.String mMnc;
        private android.telephony.UiccAccessRule[] mNativeAccessRules;
        private java.lang.String mNumber;
        private int mPortIndex;
        private int mProfileClass;
        private int mServiceCapabilities;
        private int mSimSlotIndex;
        private int mTransferStatus;
        private int mType;
        private int mUsageSetting;

        public Builder() {
            this.mId = -1;
            this.mIccId = "";
            this.mSimSlotIndex = -1;
            this.mDisplayName = "";
            this.mCarrierName = "";
            this.mDisplayNameSource = -1;
            this.mIconTint = 0;
            this.mNumber = "";
            this.mDataRoaming = 0;
            this.mIconBitmap = null;
            this.mMcc = null;
            this.mMnc = null;
            this.mEhplmns = new java.lang.String[0];
            this.mHplmns = new java.lang.String[0];
            this.mCountryIso = "";
            this.mIsEmbedded = false;
            this.mNativeAccessRules = null;
            this.mCardString = "";
            this.mCardId = -2;
            this.mIsOpportunistic = false;
            this.mGroupUuid = null;
            this.mIsGroupDisabled = false;
            this.mCarrierId = -1;
            this.mProfileClass = -1;
            this.mType = 0;
            this.mGroupOwner = "";
            this.mCarrierConfigAccessRules = null;
            this.mAreUiccApplicationsEnabled = true;
            this.mPortIndex = -1;
            this.mUsageSetting = -1;
            this.mIsOnlyNonTerrestrialNetwork = false;
            this.mTransferStatus = 0;
            this.mServiceCapabilities = 0;
        }

        public Builder(android.telephony.SubscriptionInfo subscriptionInfo) {
            this.mId = -1;
            this.mIccId = "";
            this.mSimSlotIndex = -1;
            this.mDisplayName = "";
            this.mCarrierName = "";
            this.mDisplayNameSource = -1;
            this.mIconTint = 0;
            this.mNumber = "";
            this.mDataRoaming = 0;
            this.mIconBitmap = null;
            this.mMcc = null;
            this.mMnc = null;
            this.mEhplmns = new java.lang.String[0];
            this.mHplmns = new java.lang.String[0];
            this.mCountryIso = "";
            this.mIsEmbedded = false;
            this.mNativeAccessRules = null;
            this.mCardString = "";
            this.mCardId = -2;
            this.mIsOpportunistic = false;
            this.mGroupUuid = null;
            this.mIsGroupDisabled = false;
            this.mCarrierId = -1;
            this.mProfileClass = -1;
            this.mType = 0;
            this.mGroupOwner = "";
            this.mCarrierConfigAccessRules = null;
            this.mAreUiccApplicationsEnabled = true;
            this.mPortIndex = -1;
            this.mUsageSetting = -1;
            this.mIsOnlyNonTerrestrialNetwork = false;
            this.mTransferStatus = 0;
            this.mServiceCapabilities = 0;
            this.mId = subscriptionInfo.mId;
            this.mIccId = subscriptionInfo.mIccId;
            this.mSimSlotIndex = subscriptionInfo.mSimSlotIndex;
            this.mDisplayName = subscriptionInfo.mDisplayName;
            this.mCarrierName = subscriptionInfo.mCarrierName;
            this.mDisplayNameSource = subscriptionInfo.mDisplayNameSource;
            this.mIconTint = subscriptionInfo.mIconTint;
            this.mNumber = subscriptionInfo.mNumber;
            this.mDataRoaming = subscriptionInfo.mDataRoaming;
            this.mIconBitmap = subscriptionInfo.mIconBitmap;
            this.mMcc = subscriptionInfo.mMcc;
            this.mMnc = subscriptionInfo.mMnc;
            this.mEhplmns = subscriptionInfo.mEhplmns;
            this.mHplmns = subscriptionInfo.mHplmns;
            this.mCountryIso = subscriptionInfo.mCountryIso;
            this.mIsEmbedded = subscriptionInfo.mIsEmbedded;
            this.mNativeAccessRules = subscriptionInfo.mNativeAccessRules;
            this.mCardString = subscriptionInfo.mCardString;
            this.mCardId = subscriptionInfo.mCardId;
            this.mIsOpportunistic = subscriptionInfo.mIsOpportunistic;
            this.mGroupUuid = subscriptionInfo.mGroupUuid;
            this.mIsGroupDisabled = subscriptionInfo.mIsGroupDisabled;
            this.mCarrierId = subscriptionInfo.mCarrierId;
            this.mProfileClass = subscriptionInfo.mProfileClass;
            this.mType = subscriptionInfo.mType;
            this.mGroupOwner = subscriptionInfo.mGroupOwner;
            this.mCarrierConfigAccessRules = subscriptionInfo.mCarrierConfigAccessRules;
            this.mAreUiccApplicationsEnabled = subscriptionInfo.mAreUiccApplicationsEnabled;
            this.mPortIndex = subscriptionInfo.mPortIndex;
            this.mUsageSetting = subscriptionInfo.mUsageSetting;
            this.mIsOnlyNonTerrestrialNetwork = subscriptionInfo.mIsOnlyNonTerrestrialNetwork;
            this.mServiceCapabilities = subscriptionInfo.mServiceCapabilities;
            this.mTransferStatus = subscriptionInfo.mTransferStatus;
        }

        public android.telephony.SubscriptionInfo.Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setIccId(java.lang.String str) {
            this.mIccId = android.text.TextUtils.emptyIfNull(str);
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setSimSlotIndex(int i) {
            this.mSimSlotIndex = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setDisplayName(java.lang.CharSequence charSequence) {
            if (charSequence == null) {
                charSequence = "";
            }
            this.mDisplayName = charSequence;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setCarrierName(java.lang.CharSequence charSequence) {
            if (charSequence == null) {
                charSequence = "";
            }
            this.mCarrierName = charSequence;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setDisplayNameSource(int i) {
            this.mDisplayNameSource = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setIconTint(int i) {
            this.mIconTint = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setNumber(java.lang.String str) {
            this.mNumber = android.text.TextUtils.emptyIfNull(str);
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setDataRoaming(int i) {
            this.mDataRoaming = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setIcon(android.graphics.Bitmap bitmap) {
            this.mIconBitmap = bitmap;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setMcc(java.lang.String str) {
            this.mMcc = str;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setMnc(java.lang.String str) {
            this.mMnc = str;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setEhplmns(java.lang.String[] strArr) {
            if (strArr == null) {
                strArr = new java.lang.String[0];
            }
            this.mEhplmns = strArr;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setHplmns(java.lang.String[] strArr) {
            if (strArr == null) {
                strArr = new java.lang.String[0];
            }
            this.mHplmns = strArr;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setCountryIso(java.lang.String str) {
            this.mCountryIso = android.text.TextUtils.emptyIfNull(str);
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setEmbedded(boolean z) {
            this.mIsEmbedded = z;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setNativeAccessRules(android.telephony.UiccAccessRule[] uiccAccessRuleArr) {
            this.mNativeAccessRules = uiccAccessRuleArr;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setCardString(java.lang.String str) {
            this.mCardString = android.text.TextUtils.emptyIfNull(str);
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setCardId(int i) {
            this.mCardId = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setOpportunistic(boolean z) {
            this.mIsOpportunistic = z;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setGroupUuid(java.lang.String str) {
            this.mGroupUuid = android.text.TextUtils.isEmpty(str) ? null : android.os.ParcelUuid.fromString(str);
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setGroupDisabled(boolean z) {
            this.mIsGroupDisabled = z;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setCarrierId(int i) {
            this.mCarrierId = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setProfileClass(int i) {
            this.mProfileClass = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setType(int i) {
            this.mType = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setGroupOwner(java.lang.String str) {
            this.mGroupOwner = android.text.TextUtils.emptyIfNull(str);
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setCarrierConfigAccessRules(android.telephony.UiccAccessRule[] uiccAccessRuleArr) {
            this.mCarrierConfigAccessRules = uiccAccessRuleArr;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setUiccApplicationsEnabled(boolean z) {
            this.mAreUiccApplicationsEnabled = z;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setPortIndex(int i) {
            this.mPortIndex = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setUsageSetting(int i) {
            this.mUsageSetting = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setOnlyNonTerrestrialNetwork(boolean z) {
            this.mIsOnlyNonTerrestrialNetwork = z;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setServiceCapabilities(java.util.Set<java.lang.Integer> set) {
            java.util.Iterator<java.lang.Integer> it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                if (intValue < 1 || intValue > 3) {
                    throw new java.lang.IllegalArgumentException("Invalid service capability value: " + intValue);
                }
                i |= android.telephony.SubscriptionManager.serviceCapabilityToBitmask(intValue);
            }
            this.mServiceCapabilities = i;
            return this;
        }

        public android.telephony.SubscriptionInfo.Builder setTransferStatus(int i) {
            this.mTransferStatus = i;
            return this;
        }

        public android.telephony.SubscriptionInfo build() {
            return new android.telephony.SubscriptionInfo(this);
        }
    }
}
