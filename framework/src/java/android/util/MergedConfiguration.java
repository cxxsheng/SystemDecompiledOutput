package android.util;

/* loaded from: classes3.dex */
public class MergedConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.util.MergedConfiguration> CREATOR = new android.os.Parcelable.Creator<android.util.MergedConfiguration>() { // from class: android.util.MergedConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.util.MergedConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.util.MergedConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.util.MergedConfiguration[] newArray(int i) {
            return new android.util.MergedConfiguration[i];
        }
    };
    private final android.content.res.Configuration mGlobalConfig;
    private final android.content.res.Configuration mMergedConfig;
    private final android.content.res.Configuration mOverrideConfig;

    public MergedConfiguration() {
        this.mGlobalConfig = new android.content.res.Configuration();
        this.mOverrideConfig = new android.content.res.Configuration();
        this.mMergedConfig = new android.content.res.Configuration();
    }

    public MergedConfiguration(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        this.mGlobalConfig = new android.content.res.Configuration();
        this.mOverrideConfig = new android.content.res.Configuration();
        this.mMergedConfig = new android.content.res.Configuration();
        setConfiguration(configuration, configuration2);
    }

    public MergedConfiguration(android.content.res.Configuration configuration) {
        this.mGlobalConfig = new android.content.res.Configuration();
        this.mOverrideConfig = new android.content.res.Configuration();
        this.mMergedConfig = new android.content.res.Configuration();
        setGlobalConfiguration(configuration);
    }

    public MergedConfiguration(android.util.MergedConfiguration mergedConfiguration) {
        this.mGlobalConfig = new android.content.res.Configuration();
        this.mOverrideConfig = new android.content.res.Configuration();
        this.mMergedConfig = new android.content.res.Configuration();
        setConfiguration(mergedConfiguration.getGlobalConfiguration(), mergedConfiguration.getOverrideConfiguration());
    }

    private MergedConfiguration(android.os.Parcel parcel) {
        this.mGlobalConfig = new android.content.res.Configuration();
        this.mOverrideConfig = new android.content.res.Configuration();
        this.mMergedConfig = new android.content.res.Configuration();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mGlobalConfig.writeToParcel(parcel, i);
        this.mOverrideConfig.writeToParcel(parcel, i);
        this.mMergedConfig.writeToParcel(parcel, i);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mGlobalConfig.readFromParcel(parcel);
        this.mOverrideConfig.readFromParcel(parcel);
        this.mMergedConfig.readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setConfiguration(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        this.mGlobalConfig.setTo(configuration);
        this.mOverrideConfig.setTo(configuration2);
        updateMergedConfig();
    }

    public void setGlobalConfiguration(android.content.res.Configuration configuration) {
        this.mGlobalConfig.setTo(configuration);
        updateMergedConfig();
    }

    public void setOverrideConfiguration(android.content.res.Configuration configuration) {
        this.mOverrideConfig.setTo(configuration);
        updateMergedConfig();
    }

    public void setTo(android.util.MergedConfiguration mergedConfiguration) {
        setConfiguration(mergedConfiguration.mGlobalConfig, mergedConfiguration.mOverrideConfig);
    }

    public void unset() {
        this.mGlobalConfig.unset();
        this.mOverrideConfig.unset();
        updateMergedConfig();
    }

    public android.content.res.Configuration getGlobalConfiguration() {
        return this.mGlobalConfig;
    }

    public android.content.res.Configuration getOverrideConfiguration() {
        return this.mOverrideConfig;
    }

    public android.content.res.Configuration getMergedConfiguration() {
        return this.mMergedConfig;
    }

    private void updateMergedConfig() {
        this.mMergedConfig.setTo(this.mGlobalConfig);
        this.mMergedConfig.updateFrom(this.mOverrideConfig);
    }

    public java.lang.String toString() {
        return "{mGlobalConfig=" + this.mGlobalConfig + " mOverrideConfig=" + this.mOverrideConfig + "}";
    }

    public int hashCode() {
        return this.mMergedConfig.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.util.MergedConfiguration)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return this.mMergedConfig.equals(((android.util.MergedConfiguration) obj).mMergedConfig);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "mGlobalConfig=" + this.mGlobalConfig);
        printWriter.println(str + "mOverrideConfig=" + this.mOverrideConfig);
    }
}
