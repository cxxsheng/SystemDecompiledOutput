package android.content;

/* loaded from: classes.dex */
public final class ContentCaptureOptions implements android.os.Parcelable {
    public final android.content.ContentCaptureOptions.ContentProtectionOptions contentProtectionOptions;
    public final boolean disableFlushForViewTreeAppearing;
    public final boolean enableReceiver;
    public final int idleFlushingFrequencyMs;
    public final boolean lite;
    public final int logHistorySize;
    public final int loggingLevel;
    public final int maxBufferSize;
    public final int textChangeFlushingFrequencyMs;
    public final android.util.ArraySet<android.content.ComponentName> whitelistedComponents;
    private static final java.lang.String TAG = android.content.ContentCaptureOptions.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.content.ContentCaptureOptions> CREATOR = new android.os.Parcelable.Creator<android.content.ContentCaptureOptions>() { // from class: android.content.ContentCaptureOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ContentCaptureOptions createFromParcel(android.os.Parcel parcel) {
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            if (readBoolean) {
                return new android.content.ContentCaptureOptions(readInt);
            }
            return new android.content.ContentCaptureOptions(readInt, parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), android.content.ContentCaptureOptions.ContentProtectionOptions.createFromParcel(parcel), parcel.readArraySet(null));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ContentCaptureOptions[] newArray(int i) {
            return new android.content.ContentCaptureOptions[i];
        }
    };

    public ContentCaptureOptions(int i) {
        this(true, i, 0, 0, 0, 0, false, false, new android.content.ContentCaptureOptions.ContentProtectionOptions(false, 0, java.util.Collections.emptyList(), java.util.Collections.emptyList(), 0), null);
    }

    public ContentCaptureOptions(int i, int i2, int i3, int i4, int i5, android.util.ArraySet<android.content.ComponentName> arraySet) {
        this(false, i, i2, i3, i4, i5, false, true, new android.content.ContentCaptureOptions.ContentProtectionOptions(), arraySet);
    }

    public ContentCaptureOptions(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, android.content.ContentCaptureOptions.ContentProtectionOptions contentProtectionOptions, android.util.ArraySet<android.content.ComponentName> arraySet) {
        this(false, i, i2, i3, i4, i5, z, z2, contentProtectionOptions, arraySet);
    }

    public ContentCaptureOptions(android.util.ArraySet<android.content.ComponentName> arraySet) {
        this(2, 500, 5000, 1000, 10, false, true, new android.content.ContentCaptureOptions.ContentProtectionOptions(), arraySet);
    }

    private ContentCaptureOptions(boolean z, int i, int i2, int i3, int i4, int i5, boolean z2, boolean z3, android.content.ContentCaptureOptions.ContentProtectionOptions contentProtectionOptions, android.util.ArraySet<android.content.ComponentName> arraySet) {
        this.lite = z;
        this.loggingLevel = i;
        this.maxBufferSize = i2;
        this.idleFlushingFrequencyMs = i3;
        this.textChangeFlushingFrequencyMs = i4;
        this.logHistorySize = i5;
        this.disableFlushForViewTreeAppearing = z2;
        this.enableReceiver = z3;
        this.contentProtectionOptions = contentProtectionOptions;
        this.whitelistedComponents = arraySet;
    }

    public static android.content.ContentCaptureOptions forWhitelistingItself() {
        android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
        if (currentActivityThread == null) {
            throw new java.lang.IllegalStateException("No ActivityThread");
        }
        java.lang.String packageName = currentActivityThread.getApplication().getPackageName();
        if (!"android.contentcaptureservice.cts".equals(packageName) && !"android.translation.cts".equals(packageName)) {
            android.util.Log.e(TAG, "forWhitelistingItself(): called by " + packageName);
            throw new java.lang.SecurityException("Thou shall not pass!");
        }
        android.content.ContentCaptureOptions contentCaptureOptions = new android.content.ContentCaptureOptions((android.util.ArraySet<android.content.ComponentName>) null);
        android.util.Log.i(TAG, "forWhitelistingItself(" + packageName + "): " + contentCaptureOptions);
        return contentCaptureOptions;
    }

    public boolean isWhitelisted(android.content.Context context) {
        if (this.whitelistedComponents == null) {
            return true;
        }
        android.view.contentcapture.ContentCaptureManager.ContentCaptureClient contentCaptureClient = context.getContentCaptureClient();
        if (contentCaptureClient == null) {
            android.util.Log.w(TAG, "isWhitelisted(): no ContentCaptureClient on " + context);
            return false;
        }
        return this.whitelistedComponents.contains(contentCaptureClient.contentCaptureClientGetComponentName());
    }

    public java.lang.String toString() {
        if (this.lite) {
            return "ContentCaptureOptions [loggingLevel=" + this.loggingLevel + " (lite)]";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ContentCaptureOptions [");
        sb.append("loggingLevel=").append(this.loggingLevel).append(", maxBufferSize=").append(this.maxBufferSize).append(", idleFlushingFrequencyMs=").append(this.idleFlushingFrequencyMs).append(", textChangeFlushingFrequencyMs=").append(this.textChangeFlushingFrequencyMs).append(", logHistorySize=").append(this.logHistorySize).append(", disableFlushForViewTreeAppearing=").append(this.disableFlushForViewTreeAppearing).append(", enableReceiver=").append(this.enableReceiver).append(", contentProtectionOptions=").append(this.contentProtectionOptions);
        if (this.whitelistedComponents != null) {
            sb.append(", whitelisted=").append(this.whitelistedComponents);
        }
        return sb.append(']').toString();
    }

    public void dumpShort(java.io.PrintWriter printWriter) {
        printWriter.print("logLvl=");
        printWriter.print(this.loggingLevel);
        if (this.lite) {
            printWriter.print(", lite");
            return;
        }
        printWriter.print(", bufferSize=");
        printWriter.print(this.maxBufferSize);
        printWriter.print(", idle=");
        printWriter.print(this.idleFlushingFrequencyMs);
        printWriter.print(", textIdle=");
        printWriter.print(this.textChangeFlushingFrequencyMs);
        printWriter.print(", logSize=");
        printWriter.print(this.logHistorySize);
        printWriter.print(", disableFlushForViewTreeAppearing=");
        printWriter.print(this.disableFlushForViewTreeAppearing);
        printWriter.print(", enableReceiver=");
        printWriter.print(this.enableReceiver);
        printWriter.print(", contentProtectionOptions=[");
        this.contentProtectionOptions.dumpShort(printWriter);
        printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        if (this.whitelistedComponents != null) {
            printWriter.print(", whitelisted=");
            printWriter.print(this.whitelistedComponents);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.lite);
        parcel.writeInt(this.loggingLevel);
        if (this.lite) {
            return;
        }
        parcel.writeInt(this.maxBufferSize);
        parcel.writeInt(this.idleFlushingFrequencyMs);
        parcel.writeInt(this.textChangeFlushingFrequencyMs);
        parcel.writeInt(this.logHistorySize);
        parcel.writeBoolean(this.disableFlushForViewTreeAppearing);
        parcel.writeBoolean(this.enableReceiver);
        this.contentProtectionOptions.writeToParcel(parcel);
        parcel.writeArraySet(this.whitelistedComponents);
    }

    public static class ContentProtectionOptions {
        public final int bufferSize;
        public final boolean enableReceiver;
        public final java.util.List<java.util.List<java.lang.String>> optionalGroups;
        public final int optionalGroupsThreshold;
        public final java.util.List<java.util.List<java.lang.String>> requiredGroups;

        public ContentProtectionOptions() {
            this(false, 150, android.view.contentcapture.ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS, android.view.contentcapture.ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS, 0);
        }

        public ContentProtectionOptions(boolean z, int i, java.util.List<java.util.List<java.lang.String>> list, java.util.List<java.util.List<java.lang.String>> list2, int i2) {
            this.enableReceiver = z;
            this.bufferSize = i;
            this.requiredGroups = list;
            this.optionalGroups = list2;
            this.optionalGroupsThreshold = i2;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("ContentProtectionOptions [");
            sb.append("enableReceiver=").append(this.enableReceiver).append(", bufferSize=").append(this.bufferSize).append(", requiredGroupsSize=").append(this.requiredGroups.size()).append(", optionalGroupsSize=").append(this.optionalGroups.size()).append(", optionalGroupsThreshold=").append(this.optionalGroupsThreshold);
            return sb.append(']').toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpShort(java.io.PrintWriter printWriter) {
            printWriter.print("enableReceiver=");
            printWriter.print(this.enableReceiver);
            printWriter.print(", bufferSize=");
            printWriter.print(this.bufferSize);
            printWriter.print(", requiredGroupsSize=");
            printWriter.print(this.requiredGroups.size());
            printWriter.print(", optionalGroupsSize=");
            printWriter.print(this.optionalGroups.size());
            printWriter.print(", optionalGroupsThreshold=");
            printWriter.print(this.optionalGroupsThreshold);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeBoolean(this.enableReceiver);
            parcel.writeInt(this.bufferSize);
            writeGroupsToParcel(this.requiredGroups, parcel);
            writeGroupsToParcel(this.optionalGroups, parcel);
            parcel.writeInt(this.optionalGroupsThreshold);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.content.ContentCaptureOptions.ContentProtectionOptions createFromParcel(android.os.Parcel parcel) {
            return new android.content.ContentCaptureOptions.ContentProtectionOptions(parcel.readBoolean(), parcel.readInt(), createGroupsFromParcel(parcel), createGroupsFromParcel(parcel), parcel.readInt());
        }

        private static void writeGroupsToParcel(java.util.List<java.util.List<java.lang.String>> list, final android.os.Parcel parcel) {
            parcel.writeInt(list.size());
            java.util.Objects.requireNonNull(parcel);
            list.forEach(new java.util.function.Consumer() { // from class: android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.os.Parcel.this.writeStringList((java.util.List) obj);
                }
            });
        }

        private static java.util.List<java.util.List<java.lang.String>> createGroupsFromParcel(final android.os.Parcel parcel) {
            java.util.stream.Stream mapToObj = java.util.stream.IntStream.range(0, parcel.readInt()).mapToObj(new java.util.function.IntFunction() { // from class: android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i) {
                    return android.content.ContentCaptureOptions.ContentProtectionOptions.lambda$createGroupsFromParcel$0(i);
                }
            });
            java.util.Objects.requireNonNull(parcel);
            return (java.util.List) mapToObj.peek(new java.util.function.Consumer() { // from class: android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.os.Parcel.this.readStringList((java.util.ArrayList) obj);
                }
            }).collect(java.util.stream.Collectors.toUnmodifiableList());
        }

        static /* synthetic */ java.util.ArrayList lambda$createGroupsFromParcel$0(int i) {
            return new java.util.ArrayList();
        }
    }
}
