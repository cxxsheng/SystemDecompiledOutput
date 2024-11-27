package android.hardware.biometrics;

/* loaded from: classes.dex */
public class PromptInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.PromptInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.PromptInfo>() { // from class: android.hardware.biometrics.PromptInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.PromptInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.PromptInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.PromptInfo[] newArray(int i) {
            return new android.hardware.biometrics.PromptInfo[i];
        }
    };
    private boolean mAllowBackgroundAuthentication;
    private java.util.List<java.lang.Integer> mAllowedSensorIds;
    private int mAuthenticators;
    private boolean mConfirmationRequested;
    private android.hardware.biometrics.PromptContentViewParcelable mContentView;
    private java.lang.CharSequence mDescription;
    private boolean mDeviceCredentialAllowed;
    private java.lang.CharSequence mDeviceCredentialDescription;
    private java.lang.CharSequence mDeviceCredentialSubtitle;
    private java.lang.CharSequence mDeviceCredentialTitle;
    private boolean mDisallowBiometricsIfPolicyExists;
    private boolean mIgnoreEnrollmentState;
    private boolean mIsForLegacyFingerprintManager;
    private android.graphics.Bitmap mLogoBitmap;
    private java.lang.String mLogoDescription;
    private int mLogoRes;
    private java.lang.CharSequence mNegativeButtonText;
    private boolean mReceiveSystemEvents;
    private boolean mShowEmergencyCallButton;
    private java.lang.CharSequence mSubtitle;
    private java.lang.CharSequence mTitle;
    private boolean mUseDefaultSubtitle;
    private boolean mUseDefaultTitle;
    private boolean mUseParentProfileForDeviceCredential;

    public PromptInfo() {
        this.mLogoRes = -1;
        this.mConfirmationRequested = true;
        this.mAllowedSensorIds = new java.util.ArrayList();
        this.mIsForLegacyFingerprintManager = false;
        this.mShowEmergencyCallButton = false;
        this.mUseParentProfileForDeviceCredential = false;
    }

    PromptInfo(android.os.Parcel parcel) {
        this.mLogoRes = -1;
        this.mConfirmationRequested = true;
        this.mAllowedSensorIds = new java.util.ArrayList();
        this.mIsForLegacyFingerprintManager = false;
        this.mShowEmergencyCallButton = false;
        this.mUseParentProfileForDeviceCredential = false;
        this.mLogoRes = parcel.readInt();
        this.mLogoBitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
        this.mLogoDescription = parcel.readString();
        this.mTitle = parcel.readCharSequence();
        this.mUseDefaultTitle = parcel.readBoolean();
        this.mSubtitle = parcel.readCharSequence();
        this.mUseDefaultSubtitle = parcel.readBoolean();
        this.mDescription = parcel.readCharSequence();
        this.mContentView = (android.hardware.biometrics.PromptContentViewParcelable) parcel.readParcelable(android.hardware.biometrics.PromptContentViewParcelable.class.getClassLoader(), android.hardware.biometrics.PromptContentViewParcelable.class);
        this.mDeviceCredentialTitle = parcel.readCharSequence();
        this.mDeviceCredentialSubtitle = parcel.readCharSequence();
        this.mDeviceCredentialDescription = parcel.readCharSequence();
        this.mNegativeButtonText = parcel.readCharSequence();
        this.mConfirmationRequested = parcel.readBoolean();
        this.mDeviceCredentialAllowed = parcel.readBoolean();
        this.mAuthenticators = parcel.readInt();
        this.mDisallowBiometricsIfPolicyExists = parcel.readBoolean();
        this.mReceiveSystemEvents = parcel.readBoolean();
        this.mAllowedSensorIds = parcel.readArrayList(java.lang.Integer.class.getClassLoader(), java.lang.Integer.class);
        this.mAllowBackgroundAuthentication = parcel.readBoolean();
        this.mIgnoreEnrollmentState = parcel.readBoolean();
        this.mIsForLegacyFingerprintManager = parcel.readBoolean();
        this.mShowEmergencyCallButton = parcel.readBoolean();
        this.mUseParentProfileForDeviceCredential = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLogoRes);
        parcel.writeTypedObject(this.mLogoBitmap, 0);
        parcel.writeString(this.mLogoDescription);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeBoolean(this.mUseDefaultTitle);
        parcel.writeCharSequence(this.mSubtitle);
        parcel.writeBoolean(this.mUseDefaultSubtitle);
        parcel.writeCharSequence(this.mDescription);
        parcel.writeParcelable(this.mContentView, 0);
        parcel.writeCharSequence(this.mDeviceCredentialTitle);
        parcel.writeCharSequence(this.mDeviceCredentialSubtitle);
        parcel.writeCharSequence(this.mDeviceCredentialDescription);
        parcel.writeCharSequence(this.mNegativeButtonText);
        parcel.writeBoolean(this.mConfirmationRequested);
        parcel.writeBoolean(this.mDeviceCredentialAllowed);
        parcel.writeInt(this.mAuthenticators);
        parcel.writeBoolean(this.mDisallowBiometricsIfPolicyExists);
        parcel.writeBoolean(this.mReceiveSystemEvents);
        parcel.writeList(this.mAllowedSensorIds);
        parcel.writeBoolean(this.mAllowBackgroundAuthentication);
        parcel.writeBoolean(this.mIgnoreEnrollmentState);
        parcel.writeBoolean(this.mIsForLegacyFingerprintManager);
        parcel.writeBoolean(this.mShowEmergencyCallButton);
        parcel.writeBoolean(this.mUseParentProfileForDeviceCredential);
    }

    public boolean containsTestConfigurations() {
        if (this.mIsForLegacyFingerprintManager && this.mAllowedSensorIds.size() == 1 && !this.mAllowBackgroundAuthentication) {
            return false;
        }
        return !this.mAllowedSensorIds.isEmpty() || this.mAllowBackgroundAuthentication || this.mIsForLegacyFingerprintManager || this.mIgnoreEnrollmentState;
    }

    public boolean containsPrivateApiConfigurations() {
        return this.mDisallowBiometricsIfPolicyExists || this.mUseDefaultTitle || this.mUseDefaultSubtitle || this.mDeviceCredentialTitle != null || this.mDeviceCredentialSubtitle != null || this.mDeviceCredentialDescription != null || this.mReceiveSystemEvents;
    }

    public boolean containsSetLogoApiConfigurations() {
        return (this.mLogoRes == -1 && this.mLogoBitmap == null && this.mLogoDescription == null) ? false : true;
    }

    public boolean shouldUseParentProfileForDeviceCredential() {
        return this.mUseParentProfileForDeviceCredential;
    }

    public void setLogoRes(int i) {
        this.mLogoRes = i;
        checkOnlyOneLogoSet();
    }

    public void setLogoBitmap(android.graphics.Bitmap bitmap) {
        this.mLogoBitmap = bitmap;
        checkOnlyOneLogoSet();
    }

    public void setLogoDescription(java.lang.String str) {
        this.mLogoDescription = str;
    }

    public void setTitle(java.lang.CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public void setUseDefaultTitle(boolean z) {
        this.mUseDefaultTitle = z;
    }

    public void setSubtitle(java.lang.CharSequence charSequence) {
        this.mSubtitle = charSequence;
    }

    public void setUseDefaultSubtitle(boolean z) {
        this.mUseDefaultSubtitle = z;
    }

    public void setDescription(java.lang.CharSequence charSequence) {
        this.mDescription = charSequence;
    }

    public void setContentView(android.hardware.biometrics.PromptContentView promptContentView) {
        this.mContentView = (android.hardware.biometrics.PromptContentViewParcelable) promptContentView;
    }

    public void setDeviceCredentialTitle(java.lang.CharSequence charSequence) {
        this.mDeviceCredentialTitle = charSequence;
    }

    public void setDeviceCredentialSubtitle(java.lang.CharSequence charSequence) {
        this.mDeviceCredentialSubtitle = charSequence;
    }

    public void setDeviceCredentialDescription(java.lang.CharSequence charSequence) {
        this.mDeviceCredentialDescription = charSequence;
    }

    public void setNegativeButtonText(java.lang.CharSequence charSequence) {
        this.mNegativeButtonText = charSequence;
    }

    public void setConfirmationRequested(boolean z) {
        this.mConfirmationRequested = z;
    }

    public void setDeviceCredentialAllowed(boolean z) {
        this.mDeviceCredentialAllowed = z;
    }

    public void setAuthenticators(int i) {
        this.mAuthenticators = i;
    }

    public void setDisallowBiometricsIfPolicyExists(boolean z) {
        this.mDisallowBiometricsIfPolicyExists = z;
    }

    public void setReceiveSystemEvents(boolean z) {
        this.mReceiveSystemEvents = z;
    }

    public void setAllowedSensorIds(java.util.List<java.lang.Integer> list) {
        this.mAllowedSensorIds.clear();
        this.mAllowedSensorIds.addAll(list);
    }

    public void setAllowBackgroundAuthentication(boolean z) {
        this.mAllowBackgroundAuthentication = z;
    }

    public void setIgnoreEnrollmentState(boolean z) {
        this.mIgnoreEnrollmentState = z;
    }

    public void setIsForLegacyFingerprintManager(int i) {
        this.mIsForLegacyFingerprintManager = true;
        this.mAllowedSensorIds.clear();
        this.mAllowedSensorIds.add(java.lang.Integer.valueOf(i));
    }

    public void setShowEmergencyCallButton(boolean z) {
        this.mShowEmergencyCallButton = z;
    }

    public void setUseParentProfileForDeviceCredential(boolean z) {
        this.mUseParentProfileForDeviceCredential = z;
    }

    public int getLogoRes() {
        return this.mLogoRes;
    }

    public android.graphics.Bitmap getLogoBitmap() {
        return this.mLogoBitmap;
    }

    public java.lang.String getLogoDescription() {
        return this.mLogoDescription;
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public boolean isUseDefaultTitle() {
        return this.mUseDefaultTitle;
    }

    public java.lang.CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public boolean isUseDefaultSubtitle() {
        return this.mUseDefaultSubtitle;
    }

    public java.lang.CharSequence getDescription() {
        return this.mDescription;
    }

    public android.hardware.biometrics.PromptContentView getContentView() {
        return this.mContentView;
    }

    public java.lang.CharSequence getDeviceCredentialTitle() {
        return this.mDeviceCredentialTitle;
    }

    public java.lang.CharSequence getDeviceCredentialSubtitle() {
        return this.mDeviceCredentialSubtitle;
    }

    public java.lang.CharSequence getDeviceCredentialDescription() {
        return this.mDeviceCredentialDescription;
    }

    public java.lang.CharSequence getNegativeButtonText() {
        return this.mNegativeButtonText;
    }

    public boolean isConfirmationRequested() {
        return this.mConfirmationRequested;
    }

    @java.lang.Deprecated
    public boolean isDeviceCredentialAllowed() {
        return this.mDeviceCredentialAllowed;
    }

    public int getAuthenticators() {
        return this.mAuthenticators;
    }

    public boolean isDisallowBiometricsIfPolicyExists() {
        return this.mDisallowBiometricsIfPolicyExists;
    }

    public boolean isReceiveSystemEvents() {
        return this.mReceiveSystemEvents;
    }

    public java.util.List<java.lang.Integer> getAllowedSensorIds() {
        return this.mAllowedSensorIds;
    }

    public boolean isAllowBackgroundAuthentication() {
        return this.mAllowBackgroundAuthentication;
    }

    public boolean isIgnoreEnrollmentState() {
        return this.mIgnoreEnrollmentState;
    }

    public boolean isForLegacyFingerprintManager() {
        return this.mIsForLegacyFingerprintManager;
    }

    public boolean isShowEmergencyCallButton() {
        return this.mShowEmergencyCallButton;
    }

    private void checkOnlyOneLogoSet() {
        if (this.mLogoRes != -1 && this.mLogoBitmap != null) {
            throw new java.lang.IllegalStateException("Exclusively one of logo resource or logo bitmap can be set");
        }
    }
}
