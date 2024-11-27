package android.telecom;

/* loaded from: classes3.dex */
public final class PhoneAccount implements android.os.Parcelable {
    public static final int CAPABILITY_ADHOC_CONFERENCE_CALLING = 16384;
    public static final int CAPABILITY_CALL_COMPOSER = 32768;
    public static final int CAPABILITY_CALL_PROVIDER = 2;
    public static final int CAPABILITY_CALL_SUBJECT = 64;
    public static final int CAPABILITY_CONNECTION_MANAGER = 1;

    @android.annotation.SystemApi
    public static final int CAPABILITY_EMERGENCY_CALLS_ONLY = 128;

    @android.annotation.SystemApi
    public static final int CAPABILITY_EMERGENCY_PREFERRED = 8192;

    @android.annotation.SystemApi
    public static final int CAPABILITY_EMERGENCY_VIDEO_CALLING = 512;

    @android.annotation.SystemApi
    public static final int CAPABILITY_MULTI_USER = 32;
    public static final int CAPABILITY_PLACE_EMERGENCY_CALLS = 16;
    public static final int CAPABILITY_RTT = 4096;
    public static final int CAPABILITY_SELF_MANAGED = 2048;
    public static final int CAPABILITY_SIM_SUBSCRIPTION = 4;
    public static final int CAPABILITY_SUPPORTS_CALL_STREAMING = 524288;
    public static final int CAPABILITY_SUPPORTS_TRANSACTIONAL_OPERATIONS = 262144;
    public static final int CAPABILITY_SUPPORTS_VIDEO_CALLING = 1024;
    public static final int CAPABILITY_SUPPORTS_VOICE_CALLING_INDICATIONS = 65536;
    public static final int CAPABILITY_VIDEO_CALLING = 8;
    public static final int CAPABILITY_VIDEO_CALLING_RELIES_ON_PRESENCE = 256;
    public static final int CAPABILITY_VOICE_CALLING_AVAILABLE = 131072;
    public static final android.os.Parcelable.Creator<android.telecom.PhoneAccount> CREATOR = new android.os.Parcelable.Creator<android.telecom.PhoneAccount>() { // from class: android.telecom.PhoneAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.PhoneAccount createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.PhoneAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.PhoneAccount[] newArray(int i) {
            return new android.telecom.PhoneAccount[i];
        }
    };
    public static final java.lang.String EXTRA_ADD_SELF_MANAGED_CALLS_TO_INCALLSERVICE = "android.telecom.extra.ADD_SELF_MANAGED_CALLS_TO_INCALLSERVICE";
    public static final java.lang.String EXTRA_ALWAYS_USE_VOIP_AUDIO_MODE = "android.telecom.extra.ALWAYS_USE_VOIP_AUDIO_MODE";
    public static final java.lang.String EXTRA_CALL_SUBJECT_CHARACTER_ENCODING = "android.telecom.extra.CALL_SUBJECT_CHARACTER_ENCODING";
    public static final java.lang.String EXTRA_CALL_SUBJECT_MAX_LENGTH = "android.telecom.extra.CALL_SUBJECT_MAX_LENGTH";
    public static final java.lang.String EXTRA_LOG_SELF_MANAGED_CALLS = "android.telecom.extra.LOG_SELF_MANAGED_CALLS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PLAY_CALL_RECORDING_TONE = "android.telecom.extra.PLAY_CALL_RECORDING_TONE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SKIP_CALL_FILTERING = "android.telecom.extra.SKIP_CALL_FILTERING";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SORT_ORDER = "android.telecom.extra.SORT_ORDER";
    public static final java.lang.String EXTRA_SUPPORTS_HANDOVER_FROM = "android.telecom.extra.SUPPORTS_HANDOVER_FROM";
    public static final java.lang.String EXTRA_SUPPORTS_HANDOVER_TO = "android.telecom.extra.SUPPORTS_HANDOVER_TO";
    public static final java.lang.String EXTRA_SUPPORTS_VIDEO_CALLING_FALLBACK = "android.telecom.extra.SUPPORTS_VIDEO_CALLING_FALLBACK";
    public static final int NO_HIGHLIGHT_COLOR = 0;
    public static final int NO_ICON_TINT = 0;
    public static final int NO_RESOURCE_ID = -1;
    public static final java.lang.String SCHEME_SIP = "sip";
    public static final java.lang.String SCHEME_TEL = "tel";
    public static final java.lang.String SCHEME_VOICEMAIL = "voicemail";
    private final android.telecom.PhoneAccountHandle mAccountHandle;
    private final android.net.Uri mAddress;
    private final int mCapabilities;
    private final android.os.Bundle mExtras;
    private java.lang.String mGroupId;
    private final int mHighlightColor;
    private final android.graphics.drawable.Icon mIcon;
    private boolean mIsEnabled;
    private final java.lang.CharSequence mLabel;
    private final java.lang.CharSequence mShortDescription;
    private final java.util.Set<android.telecom.PhoneAccountHandle> mSimultaneousCallingRestriction;
    private final android.net.Uri mSubscriptionAddress;
    private final int mSupportedAudioRoutes;
    private final java.util.List<java.lang.String> mSupportedUriSchemes;

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telecom.PhoneAccount phoneAccount = (android.telecom.PhoneAccount) obj;
        if (this.mCapabilities == phoneAccount.mCapabilities && this.mHighlightColor == phoneAccount.mHighlightColor && this.mSupportedAudioRoutes == phoneAccount.mSupportedAudioRoutes && this.mIsEnabled == phoneAccount.mIsEnabled && java.util.Objects.equals(this.mAccountHandle, phoneAccount.mAccountHandle) && java.util.Objects.equals(this.mAddress, phoneAccount.mAddress) && java.util.Objects.equals(this.mSubscriptionAddress, phoneAccount.mSubscriptionAddress) && java.util.Objects.equals(this.mLabel, phoneAccount.mLabel) && java.util.Objects.equals(this.mShortDescription, phoneAccount.mShortDescription) && java.util.Objects.equals(this.mSupportedUriSchemes, phoneAccount.mSupportedUriSchemes) && areBundlesEqual(this.mExtras, phoneAccount.mExtras) && java.util.Objects.equals(this.mGroupId, phoneAccount.mGroupId) && java.util.Objects.equals(this.mSimultaneousCallingRestriction, phoneAccount.mSimultaneousCallingRestriction)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mAccountHandle, this.mAddress, this.mSubscriptionAddress, java.lang.Integer.valueOf(this.mCapabilities), java.lang.Integer.valueOf(this.mHighlightColor), this.mLabel, this.mShortDescription, this.mSupportedUriSchemes, java.lang.Integer.valueOf(this.mSupportedAudioRoutes), this.mExtras, java.lang.Boolean.valueOf(this.mIsEnabled), this.mGroupId, this.mSimultaneousCallingRestriction);
    }

    public static class Builder {
        private android.telecom.PhoneAccountHandle mAccountHandle;
        private android.net.Uri mAddress;
        private int mCapabilities;
        private android.os.Bundle mExtras;
        private java.lang.String mGroupId;
        private int mHighlightColor;
        private android.graphics.drawable.Icon mIcon;
        private boolean mIsEnabled;
        private java.lang.CharSequence mLabel;
        private java.lang.CharSequence mShortDescription;
        private java.util.Set<android.telecom.PhoneAccountHandle> mSimultaneousCallingRestriction;
        private android.net.Uri mSubscriptionAddress;
        private int mSupportedAudioRoutes;
        private java.util.List<java.lang.String> mSupportedUriSchemes;

        public Builder(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.CharSequence charSequence) {
            this.mSupportedAudioRoutes = 31;
            this.mHighlightColor = 0;
            this.mSupportedUriSchemes = new java.util.ArrayList();
            this.mIsEnabled = false;
            this.mGroupId = "";
            this.mSimultaneousCallingRestriction = null;
            this.mAccountHandle = phoneAccountHandle;
            this.mLabel = charSequence;
        }

        public Builder(android.telecom.PhoneAccount phoneAccount) {
            this.mSupportedAudioRoutes = 31;
            this.mHighlightColor = 0;
            this.mSupportedUriSchemes = new java.util.ArrayList();
            this.mIsEnabled = false;
            this.mGroupId = "";
            this.mSimultaneousCallingRestriction = null;
            this.mAccountHandle = phoneAccount.getAccountHandle();
            this.mAddress = phoneAccount.getAddress();
            this.mSubscriptionAddress = phoneAccount.getSubscriptionAddress();
            this.mCapabilities = phoneAccount.getCapabilities();
            this.mHighlightColor = phoneAccount.getHighlightColor();
            this.mLabel = phoneAccount.getLabel();
            this.mShortDescription = phoneAccount.getShortDescription();
            this.mSupportedUriSchemes.addAll(phoneAccount.getSupportedUriSchemes());
            this.mIcon = phoneAccount.getIcon();
            this.mIsEnabled = phoneAccount.isEnabled();
            this.mExtras = phoneAccount.getExtras();
            this.mGroupId = phoneAccount.getGroupId();
            this.mSupportedAudioRoutes = phoneAccount.getSupportedAudioRoutes();
            if (phoneAccount.hasSimultaneousCallingRestriction()) {
                this.mSimultaneousCallingRestriction = phoneAccount.getSimultaneousCallingRestriction();
            }
        }

        public android.telecom.PhoneAccount.Builder setLabel(java.lang.CharSequence charSequence) {
            this.mLabel = charSequence;
            return this;
        }

        public android.telecom.PhoneAccount.Builder setAddress(android.net.Uri uri) {
            this.mAddress = uri;
            return this;
        }

        public android.telecom.PhoneAccount.Builder setSubscriptionAddress(android.net.Uri uri) {
            this.mSubscriptionAddress = uri;
            return this;
        }

        public android.telecom.PhoneAccount.Builder setCapabilities(int i) {
            this.mCapabilities = i;
            return this;
        }

        public android.telecom.PhoneAccount.Builder setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public android.telecom.PhoneAccount.Builder setHighlightColor(int i) {
            this.mHighlightColor = i;
            return this;
        }

        public android.telecom.PhoneAccount.Builder setShortDescription(java.lang.CharSequence charSequence) {
            this.mShortDescription = charSequence;
            return this;
        }

        public android.telecom.PhoneAccount.Builder addSupportedUriScheme(java.lang.String str) {
            if (!android.text.TextUtils.isEmpty(str) && !this.mSupportedUriSchemes.contains(str)) {
                this.mSupportedUriSchemes.add(str);
            }
            return this;
        }

        public android.telecom.PhoneAccount.Builder setSupportedUriSchemes(java.util.List<java.lang.String> list) {
            this.mSupportedUriSchemes.clear();
            if (list != null && !list.isEmpty()) {
                java.util.Iterator<java.lang.String> it = list.iterator();
                while (it.hasNext()) {
                    addSupportedUriScheme(it.next());
                }
            }
            return this;
        }

        public android.telecom.PhoneAccount.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.telecom.PhoneAccount.Builder setIsEnabled(boolean z) {
            this.mIsEnabled = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.telecom.PhoneAccount.Builder setGroupId(java.lang.String str) {
            if (str != null) {
                this.mGroupId = str;
            } else {
                this.mGroupId = "";
            }
            return this;
        }

        public android.telecom.PhoneAccount.Builder setSupportedAudioRoutes(int i) {
            this.mSupportedAudioRoutes = i;
            return this;
        }

        public android.telecom.PhoneAccount.Builder setSimultaneousCallingRestriction(java.util.Set<android.telecom.PhoneAccountHandle> set) {
            if (set == null) {
                throw new java.lang.IllegalArgumentException("the Set of PhoneAccountHandles must not be null");
            }
            this.mSimultaneousCallingRestriction = set;
            return this;
        }

        public android.telecom.PhoneAccount.Builder clearSimultaneousCallingRestriction() {
            this.mSimultaneousCallingRestriction = null;
            return this;
        }

        public android.telecom.PhoneAccount build() {
            if (this.mSupportedUriSchemes.isEmpty()) {
                addSupportedUriScheme(android.telecom.PhoneAccount.SCHEME_TEL);
            }
            return new android.telecom.PhoneAccount(this.mAccountHandle, this.mAddress, this.mSubscriptionAddress, this.mCapabilities, this.mIcon, this.mHighlightColor, this.mLabel, this.mShortDescription, this.mSupportedUriSchemes, this.mExtras, this.mSupportedAudioRoutes, this.mIsEnabled, this.mGroupId, this.mSimultaneousCallingRestriction);
        }
    }

    private PhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri, android.net.Uri uri2, int i, android.graphics.drawable.Icon icon, int i2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.util.List<java.lang.String> list, android.os.Bundle bundle, int i3, boolean z, java.lang.String str, java.util.Set<android.telecom.PhoneAccountHandle> set) {
        this.mAccountHandle = phoneAccountHandle;
        this.mAddress = uri;
        this.mSubscriptionAddress = uri2;
        this.mCapabilities = i;
        this.mIcon = icon;
        this.mHighlightColor = i2;
        this.mLabel = charSequence;
        this.mShortDescription = charSequence2;
        this.mSupportedUriSchemes = java.util.Collections.unmodifiableList(list);
        this.mExtras = bundle;
        this.mSupportedAudioRoutes = i3;
        this.mIsEnabled = z;
        this.mGroupId = str;
        this.mSimultaneousCallingRestriction = set;
    }

    public static android.telecom.PhoneAccount.Builder builder(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.CharSequence charSequence) {
        return new android.telecom.PhoneAccount.Builder(phoneAccountHandle, charSequence);
    }

    public android.telecom.PhoneAccount.Builder toBuilder() {
        return new android.telecom.PhoneAccount.Builder(this);
    }

    public android.telecom.PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
    }

    public android.net.Uri getAddress() {
        return this.mAddress;
    }

    public android.net.Uri getSubscriptionAddress() {
        return this.mSubscriptionAddress;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public boolean hasCapabilities(int i) {
        return (this.mCapabilities & i) == i;
    }

    public boolean hasAudioRoutes(int i) {
        return (this.mSupportedAudioRoutes & i) == i;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public java.lang.CharSequence getShortDescription() {
        return this.mShortDescription;
    }

    public java.util.List<java.lang.String> getSupportedUriSchemes() {
        return this.mSupportedUriSchemes;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public java.lang.String getGroupId() {
        return this.mGroupId;
    }

    public boolean supportsUriScheme(java.lang.String str) {
        if (this.mSupportedUriSchemes == null || str == null) {
            return false;
        }
        for (java.lang.String str2 : this.mSupportedUriSchemes) {
            if (str2 != null && str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    public void setIsEnabled(boolean z) {
        this.mIsEnabled = z;
    }

    public boolean isSelfManaged() {
        return (this.mCapabilities & 2048) == 2048;
    }

    public java.util.Set<android.telecom.PhoneAccountHandle> getSimultaneousCallingRestriction() {
        if (this.mSimultaneousCallingRestriction == null) {
            throw new java.lang.IllegalStateException("This method can not be called if there is no simultaneous calling restriction. See #hasSimultaneousCallingRestriction");
        }
        return this.mSimultaneousCallingRestriction;
    }

    public boolean hasSimultaneousCallingRestriction() {
        return this.mSimultaneousCallingRestriction != null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mAccountHandle == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mAccountHandle.writeToParcel(parcel, i);
        }
        if (this.mAddress == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mAddress.writeToParcel(parcel, i);
        }
        if (this.mSubscriptionAddress == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mSubscriptionAddress.writeToParcel(parcel, i);
        }
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mHighlightColor);
        parcel.writeCharSequence(this.mLabel);
        parcel.writeCharSequence(this.mShortDescription);
        parcel.writeStringList(this.mSupportedUriSchemes);
        if (this.mIcon == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, i);
        }
        parcel.writeByte(this.mIsEnabled ? (byte) 1 : (byte) 0);
        parcel.writeBundle(this.mExtras);
        parcel.writeString(this.mGroupId);
        parcel.writeInt(this.mSupportedAudioRoutes);
        if (this.mSimultaneousCallingRestriction == null) {
            parcel.writeBoolean(false);
        } else {
            parcel.writeBoolean(true);
            parcel.writeTypedList(this.mSimultaneousCallingRestriction.stream().toList());
        }
    }

    private PhoneAccount(android.os.Parcel parcel) {
        if (parcel.readInt() > 0) {
            this.mAccountHandle = android.telecom.PhoneAccountHandle.CREATOR.createFromParcel(parcel);
        } else {
            this.mAccountHandle = null;
        }
        if (parcel.readInt() > 0) {
            this.mAddress = android.net.Uri.CREATOR.createFromParcel(parcel);
        } else {
            this.mAddress = null;
        }
        if (parcel.readInt() > 0) {
            this.mSubscriptionAddress = android.net.Uri.CREATOR.createFromParcel(parcel);
        } else {
            this.mSubscriptionAddress = null;
        }
        this.mCapabilities = parcel.readInt();
        this.mHighlightColor = parcel.readInt();
        this.mLabel = parcel.readCharSequence();
        this.mShortDescription = parcel.readCharSequence();
        this.mSupportedUriSchemes = java.util.Collections.unmodifiableList(parcel.createStringArrayList());
        if (parcel.readInt() > 0) {
            this.mIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        this.mIsEnabled = parcel.readByte() == 1;
        this.mExtras = parcel.readBundle();
        this.mGroupId = parcel.readString();
        this.mSupportedAudioRoutes = parcel.readInt();
        if (parcel.readBoolean()) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            parcel.readTypedList(arrayList, android.telecom.PhoneAccountHandle.CREATOR);
            this.mSimultaneousCallingRestriction = new android.util.ArraySet(arrayList);
            return;
        }
        this.mSimultaneousCallingRestriction = null;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append("[[").append(this.mIsEnabled ? 'X' : ' ').append("] PhoneAccount: ").append(this.mAccountHandle).append(" Capabilities: ").append(capabilitiesToString()).append(" Audio Routes: ").append(audioRoutesToString()).append(" Schemes: ");
        java.util.Iterator<java.lang.String> it = this.mSupportedUriSchemes.iterator();
        while (it.hasNext()) {
            append.append(it.next()).append(" ");
        }
        append.append(" Extras: ");
        append.append(this.mExtras);
        append.append(" GroupId: ");
        append.append(android.telecom.Log.pii(this.mGroupId));
        append.append(" SC Restrictions: ");
        if (hasSimultaneousCallingRestriction()) {
            append.append("[ ");
            java.util.Iterator<android.telecom.PhoneAccountHandle> it2 = this.mSimultaneousCallingRestriction.iterator();
            while (it2.hasNext()) {
                append.append(it2.next());
                append.append(" ");
            }
            append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            append.append("[NONE]");
        }
        append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return append.toString();
    }

    public java.lang.String capabilitiesToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (hasCapabilities(2048)) {
            sb.append("SelfManaged ");
        }
        if (hasCapabilities(1024)) {
            sb.append("SuppVideo ");
        }
        if (hasCapabilities(8)) {
            sb.append("Video ");
        }
        if (hasCapabilities(256)) {
            sb.append("Presence ");
        }
        if (hasCapabilities(2)) {
            sb.append("CallProvider ");
        }
        if (hasCapabilities(64)) {
            sb.append("CallSubject ");
        }
        if (hasCapabilities(1)) {
            sb.append("ConnectionMgr ");
        }
        if (hasCapabilities(128)) {
            sb.append("EmergOnly ");
        }
        if (hasCapabilities(32)) {
            sb.append("MultiUser ");
        }
        if (hasCapabilities(16)) {
            sb.append("PlaceEmerg ");
        }
        if (hasCapabilities(8192)) {
            sb.append("EmerPrefer ");
        }
        if (hasCapabilities(512)) {
            sb.append("EmergVideo ");
        }
        if (hasCapabilities(4)) {
            sb.append("SimSub ");
        }
        if (hasCapabilities(4096)) {
            sb.append("Rtt ");
        }
        if (hasCapabilities(16384)) {
            sb.append("AdhocConf ");
        }
        if (hasCapabilities(32768)) {
            sb.append("CallComposer ");
        }
        if (hasCapabilities(65536)) {
            sb.append("SuppVoice ");
        }
        if (hasCapabilities(131072)) {
            sb.append("Voice ");
        }
        if (hasCapabilities(262144)) {
            sb.append("TransactOps ");
        }
        if (hasCapabilities(524288)) {
            sb.append("Stream ");
        }
        return sb.toString();
    }

    private java.lang.String audioRoutesToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (hasAudioRoutes(2)) {
            sb.append(android.hardware.gnss.GnssSignalType.CODE_TYPE_B);
        }
        if (hasAudioRoutes(1)) {
            sb.append("E");
        }
        if (hasAudioRoutes(8)) {
            sb.append(android.hardware.gnss.GnssSignalType.CODE_TYPE_S);
        }
        if (hasAudioRoutes(4)) {
            sb.append(android.hardware.gnss.GnssSignalType.CODE_TYPE_W);
        }
        return sb.toString();
    }

    private static boolean areBundlesEqual(android.os.Bundle bundle, android.os.Bundle bundle2) {
        if (bundle == null || bundle2 == null) {
            return bundle == bundle2;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (java.lang.String str : bundle.keySet()) {
            if (str != null && !java.util.Objects.equals(bundle.get(str), bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }
}
