package android.content;

/* loaded from: classes.dex */
public final class AutofillOptions implements android.os.Parcelable {
    public long appDisabledExpiration;
    public boolean augmentedAutofillEnabled;
    public final boolean compatModeEnabled;
    public android.util.ArrayMap<java.lang.String, java.lang.Long> disabledActivities;
    public final int loggingLevel;
    public android.util.ArraySet<android.content.ComponentName> whitelistedActivitiesForAugmentedAutofill;
    private static final java.lang.String TAG = android.content.AutofillOptions.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.content.AutofillOptions> CREATOR = new android.os.Parcelable.Creator<android.content.AutofillOptions>() { // from class: android.content.AutofillOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.AutofillOptions createFromParcel(android.os.Parcel parcel) {
            android.content.AutofillOptions autofillOptions = new android.content.AutofillOptions(parcel.readInt(), parcel.readBoolean());
            autofillOptions.augmentedAutofillEnabled = parcel.readBoolean();
            autofillOptions.whitelistedActivitiesForAugmentedAutofill = parcel.readArraySet(null);
            autofillOptions.appDisabledExpiration = parcel.readLong();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                autofillOptions.disabledActivities = new android.util.ArrayMap<>();
                for (int i = 0; i < readInt; i++) {
                    autofillOptions.disabledActivities.put(parcel.readString(), java.lang.Long.valueOf(parcel.readLong()));
                }
            }
            return autofillOptions;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.AutofillOptions[] newArray(int i) {
            return new android.content.AutofillOptions[i];
        }
    };

    public AutofillOptions(int i, boolean z) {
        this.loggingLevel = i;
        this.compatModeEnabled = z;
    }

    public boolean isAugmentedAutofillEnabled(android.content.Context context) {
        android.view.autofill.AutofillManager.AutofillClient autofillClient;
        if (this.augmentedAutofillEnabled && (autofillClient = context.getAutofillClient()) != null) {
            return this.whitelistedActivitiesForAugmentedAutofill == null || this.whitelistedActivitiesForAugmentedAutofill.contains(autofillClient.autofillClientGetComponentName());
        }
        return false;
    }

    public boolean isAutofillDisabledLocked(android.content.ComponentName componentName) {
        java.lang.Long l;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        java.lang.String flattenToString = componentName.flattenToString();
        if (this.appDisabledExpiration >= elapsedRealtime) {
            return true;
        }
        if (this.disabledActivities != null && (l = this.disabledActivities.get(flattenToString)) != null) {
            if (l.longValue() >= elapsedRealtime) {
                return true;
            }
            this.disabledActivities.remove(flattenToString);
        }
        this.appDisabledExpiration = 0L;
        return false;
    }

    public static android.content.AutofillOptions forWhitelistingItself() {
        android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
        if (currentActivityThread == null) {
            throw new java.lang.IllegalStateException("No ActivityThread");
        }
        java.lang.String packageName = currentActivityThread.getApplication().getPackageName();
        if (!"android.autofillservice.cts".equals(packageName)) {
            android.util.Log.e(TAG, "forWhitelistingItself(): called by " + packageName);
            throw new java.lang.SecurityException("Thou shall not pass!");
        }
        android.content.AutofillOptions autofillOptions = new android.content.AutofillOptions(4, true);
        autofillOptions.augmentedAutofillEnabled = true;
        android.util.Log.i(TAG, "forWhitelistingItself(" + packageName + "): " + autofillOptions);
        return autofillOptions;
    }

    public java.lang.String toString() {
        return "AutofillOptions [loggingLevel=" + this.loggingLevel + ", compatMode=" + this.compatModeEnabled + ", augmentedAutofillEnabled=" + this.augmentedAutofillEnabled + ", appDisabledExpiration=" + this.appDisabledExpiration + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public void dumpShort(java.io.PrintWriter printWriter) {
        printWriter.print("logLvl=");
        printWriter.print(this.loggingLevel);
        printWriter.print(", compatMode=");
        printWriter.print(this.compatModeEnabled);
        printWriter.print(", augmented=");
        printWriter.print(this.augmentedAutofillEnabled);
        if (this.whitelistedActivitiesForAugmentedAutofill != null) {
            printWriter.print(", whitelistedActivitiesForAugmentedAutofill=");
            printWriter.print(this.whitelistedActivitiesForAugmentedAutofill);
        }
        printWriter.print(", appDisabledExpiration=");
        printWriter.print(this.appDisabledExpiration);
        if (this.disabledActivities != null) {
            printWriter.print(", disabledActivities=");
            printWriter.print(this.disabledActivities);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.loggingLevel);
        parcel.writeBoolean(this.compatModeEnabled);
        parcel.writeBoolean(this.augmentedAutofillEnabled);
        parcel.writeArraySet(this.whitelistedActivitiesForAugmentedAutofill);
        parcel.writeLong(this.appDisabledExpiration);
        int size = this.disabledActivities != null ? this.disabledActivities.size() : 0;
        parcel.writeInt(size);
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                java.lang.String keyAt = this.disabledActivities.keyAt(i2);
                parcel.writeString(keyAt);
                parcel.writeLong(this.disabledActivities.get(keyAt).longValue());
            }
        }
    }
}
