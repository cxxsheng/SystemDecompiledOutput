package android.media;

/* loaded from: classes2.dex */
public final class RoutingSessionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.RoutingSessionInfo> CREATOR = new android.os.Parcelable.Creator<android.media.RoutingSessionInfo>() { // from class: android.media.RoutingSessionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.RoutingSessionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.RoutingSessionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.RoutingSessionInfo[] newArray(int i) {
            return new android.media.RoutingSessionInfo[i];
        }
    };
    private static final java.lang.String KEY_GROUP_ROUTE = "androidx.mediarouter.media.KEY_GROUP_ROUTE";
    private static final java.lang.String KEY_VOLUME_HANDLING = "volumeHandling";
    private static final java.lang.String TAG = "RoutingSessionInfo";
    public static final int TRANSFER_REASON_APP = 2;
    public static final int TRANSFER_REASON_FALLBACK = 0;
    public static final int TRANSFER_REASON_SYSTEM_REQUEST = 1;
    final java.lang.String mClientPackageName;
    final android.os.Bundle mControlHints;
    final java.util.List<java.lang.String> mDeselectableRoutes;
    final java.lang.String mId;
    final boolean mIsSystemSession;
    final java.lang.CharSequence mName;
    final java.lang.String mOwnerPackageName;
    final java.lang.String mProviderId;
    final java.util.List<java.lang.String> mSelectableRoutes;
    final java.util.List<java.lang.String> mSelectedRoutes;
    final java.lang.String mTransferInitiatorPackageName;
    final android.os.UserHandle mTransferInitiatorUserHandle;
    final int mTransferReason;
    final java.util.List<java.lang.String> mTransferableRoutes;
    final int mVolume;
    final int mVolumeHandling;
    final int mVolumeMax;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TransferReason {
    }

    RoutingSessionInfo(android.media.RoutingSessionInfo.Builder builder) {
        java.util.Objects.requireNonNull(builder, "builder must not be null.");
        this.mId = builder.mId;
        this.mName = builder.mName;
        this.mOwnerPackageName = builder.mOwnerPackageName;
        this.mClientPackageName = builder.mClientPackageName;
        this.mProviderId = builder.mProviderId;
        this.mSelectedRoutes = java.util.Collections.unmodifiableList(convertToUniqueRouteIds(builder.mSelectedRoutes));
        this.mSelectableRoutes = java.util.Collections.unmodifiableList(convertToUniqueRouteIds(builder.mSelectableRoutes));
        this.mDeselectableRoutes = java.util.Collections.unmodifiableList(convertToUniqueRouteIds(builder.mDeselectableRoutes));
        this.mTransferableRoutes = java.util.Collections.unmodifiableList(convertToUniqueRouteIds(builder.mTransferableRoutes));
        this.mVolumeMax = builder.mVolumeMax;
        this.mVolume = builder.mVolume;
        this.mIsSystemSession = builder.mIsSystemSession;
        this.mVolumeHandling = defineVolumeHandling(this.mIsSystemSession, builder.mVolumeHandling, this.mSelectedRoutes, android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_volumeAdjustmentForRemoteGroupSessions));
        this.mControlHints = updateVolumeHandlingInHints(builder.mControlHints, this.mVolumeHandling);
        this.mTransferReason = builder.mTransferReason;
        this.mTransferInitiatorUserHandle = builder.mTransferInitiatorUserHandle;
        this.mTransferInitiatorPackageName = builder.mTransferInitiatorPackageName;
    }

    RoutingSessionInfo(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(this.mId));
        this.mName = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mOwnerPackageName = parcel.readString();
        this.mClientPackageName = ensureString(parcel.readString());
        this.mProviderId = parcel.readString();
        this.mSelectedRoutes = ensureList(parcel.createStringArrayList());
        com.android.internal.util.Preconditions.checkArgument(!this.mSelectedRoutes.isEmpty());
        this.mSelectableRoutes = ensureList(parcel.createStringArrayList());
        this.mDeselectableRoutes = ensureList(parcel.createStringArrayList());
        this.mTransferableRoutes = ensureList(parcel.createStringArrayList());
        this.mVolumeHandling = parcel.readInt();
        this.mVolumeMax = parcel.readInt();
        this.mVolume = parcel.readInt();
        this.mControlHints = parcel.readBundle();
        this.mIsSystemSession = parcel.readBoolean();
        this.mTransferReason = parcel.readInt();
        this.mTransferInitiatorUserHandle = android.os.UserHandle.readFromParcel(parcel);
        this.mTransferInitiatorPackageName = parcel.readString();
    }

    private static android.os.Bundle updateVolumeHandlingInHints(android.os.Bundle bundle, int i) {
        android.os.Bundle bundle2;
        if (bundle != null && bundle.containsKey(KEY_GROUP_ROUTE) && (bundle2 = bundle.getBundle(KEY_GROUP_ROUTE)) != null && bundle2.containsKey(KEY_VOLUME_HANDLING) && i != bundle2.getInt(KEY_VOLUME_HANDLING)) {
            android.os.Bundle bundle3 = new android.os.Bundle(bundle2);
            bundle3.putInt(KEY_VOLUME_HANDLING, i);
            android.os.Bundle bundle4 = new android.os.Bundle(bundle);
            bundle4.putBundle(KEY_GROUP_ROUTE, bundle3);
            return bundle4;
        }
        return bundle;
    }

    private static int defineVolumeHandling(boolean z, int i, java.util.List<java.lang.String> list, boolean z2) {
        if (!z && !z2 && list.size() > 1) {
            return 0;
        }
        return i;
    }

    private static java.lang.String ensureString(java.lang.String str) {
        return str != null ? str : "";
    }

    private static <T> java.util.List<T> ensureList(java.util.List<? extends T> list) {
        if (list != null) {
            return java.util.Collections.unmodifiableList(list);
        }
        return java.util.Collections.emptyList();
    }

    public java.lang.String getId() {
        if (!android.text.TextUtils.isEmpty(this.mProviderId)) {
            return android.media.MediaRouter2Utils.toUniqueId(this.mProviderId, this.mId);
        }
        return this.mId;
    }

    public java.lang.CharSequence getName() {
        return this.mName;
    }

    public java.lang.String getOriginalId() {
        return this.mId;
    }

    public java.lang.String getOwnerPackageName() {
        return this.mOwnerPackageName;
    }

    public java.lang.String getClientPackageName() {
        return this.mClientPackageName;
    }

    public java.lang.String getProviderId() {
        return this.mProviderId;
    }

    public java.util.List<java.lang.String> getSelectedRoutes() {
        return this.mSelectedRoutes;
    }

    public java.util.List<java.lang.String> getSelectableRoutes() {
        return this.mSelectableRoutes;
    }

    public java.util.List<java.lang.String> getDeselectableRoutes() {
        return this.mDeselectableRoutes;
    }

    public java.util.List<java.lang.String> getTransferableRoutes() {
        return this.mTransferableRoutes;
    }

    public int getVolumeHandling() {
        return this.mVolumeHandling;
    }

    public int getVolumeMax() {
        return this.mVolumeMax;
    }

    public int getVolume() {
        return this.mVolume;
    }

    public android.os.Bundle getControlHints() {
        return this.mControlHints;
    }

    public boolean isSystemSession() {
        return this.mIsSystemSession;
    }

    public int getTransferReason() {
        return this.mTransferReason;
    }

    public android.os.UserHandle getTransferInitiatorUserHandle() {
        return this.mTransferInitiatorUserHandle;
    }

    public java.lang.String getTransferInitiatorPackageName() {
        return this.mTransferInitiatorPackageName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeCharSequence(this.mName);
        parcel.writeString(this.mOwnerPackageName);
        parcel.writeString(this.mClientPackageName);
        parcel.writeString(this.mProviderId);
        parcel.writeStringList(this.mSelectedRoutes);
        parcel.writeStringList(this.mSelectableRoutes);
        parcel.writeStringList(this.mDeselectableRoutes);
        parcel.writeStringList(this.mTransferableRoutes);
        parcel.writeInt(this.mVolumeHandling);
        parcel.writeInt(this.mVolumeMax);
        parcel.writeInt(this.mVolume);
        parcel.writeBundle(this.mControlHints);
        parcel.writeBoolean(this.mIsSystemSession);
        parcel.writeInt(this.mTransferReason);
        android.os.UserHandle.writeToParcel(this.mTransferInitiatorUserHandle, parcel);
        parcel.writeString(this.mTransferInitiatorPackageName);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + TAG);
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "mId=" + this.mId);
        printWriter.println(str2 + "mName=" + ((java.lang.Object) this.mName));
        printWriter.println(str2 + "mOwnerPackageName=" + this.mOwnerPackageName);
        printWriter.println(str2 + "mClientPackageName=" + this.mClientPackageName);
        printWriter.println(str2 + "mProviderId=" + this.mProviderId);
        printWriter.println(str2 + "mSelectedRoutes=" + this.mSelectedRoutes);
        printWriter.println(str2 + "mSelectableRoutes=" + this.mSelectableRoutes);
        printWriter.println(str2 + "mDeselectableRoutes=" + this.mDeselectableRoutes);
        printWriter.println(str2 + "mTransferableRoutes=" + this.mTransferableRoutes);
        printWriter.println(str2 + "mVolumeHandling=" + this.mVolumeHandling);
        printWriter.println(str2 + "mVolumeMax=" + this.mVolumeMax);
        printWriter.println(str2 + "mVolume=" + this.mVolume);
        printWriter.println(str2 + "mControlHints=" + this.mControlHints);
        printWriter.println(str2 + "mIsSystemSession=" + this.mIsSystemSession);
        printWriter.println(str2 + "mTransferReason=" + this.mTransferReason);
        printWriter.println(str2 + "mtransferInitiatorUserHandle=" + this.mTransferInitiatorUserHandle);
        printWriter.println(str2 + "mtransferInitiatorPackageName=" + this.mTransferInitiatorPackageName);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        android.media.RoutingSessionInfo routingSessionInfo = (android.media.RoutingSessionInfo) obj;
        if (!java.util.Objects.equals(this.mId, routingSessionInfo.mId) || !java.util.Objects.equals(this.mName, routingSessionInfo.mName) || !java.util.Objects.equals(this.mOwnerPackageName, routingSessionInfo.mOwnerPackageName) || !java.util.Objects.equals(this.mClientPackageName, routingSessionInfo.mClientPackageName) || !java.util.Objects.equals(this.mProviderId, routingSessionInfo.mProviderId) || !java.util.Objects.equals(this.mSelectedRoutes, routingSessionInfo.mSelectedRoutes) || !java.util.Objects.equals(this.mSelectableRoutes, routingSessionInfo.mSelectableRoutes) || !java.util.Objects.equals(this.mDeselectableRoutes, routingSessionInfo.mDeselectableRoutes) || !java.util.Objects.equals(this.mTransferableRoutes, routingSessionInfo.mTransferableRoutes) || this.mVolumeHandling != routingSessionInfo.mVolumeHandling || this.mVolumeMax != routingSessionInfo.mVolumeMax || this.mVolume != routingSessionInfo.mVolume || this.mTransferReason != routingSessionInfo.mTransferReason || !java.util.Objects.equals(this.mTransferInitiatorUserHandle, routingSessionInfo.mTransferInitiatorUserHandle) || !java.util.Objects.equals(this.mTransferInitiatorPackageName, routingSessionInfo.mTransferInitiatorPackageName)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId, this.mName, this.mOwnerPackageName, this.mClientPackageName, this.mProviderId, this.mSelectedRoutes, this.mSelectableRoutes, this.mDeselectableRoutes, this.mTransferableRoutes, java.lang.Integer.valueOf(this.mVolumeMax), java.lang.Integer.valueOf(this.mVolumeHandling), java.lang.Integer.valueOf(this.mVolume), java.lang.Integer.valueOf(this.mTransferReason), this.mTransferInitiatorUserHandle, this.mTransferInitiatorPackageName);
    }

    public java.lang.String toString() {
        return "RoutingSessionInfo{ sessionId=" + getId() + ", name=" + getName() + ", clientPackageName=" + getClientPackageName() + ", selectedRoutes={" + java.lang.String.join(",", getSelectedRoutes()) + "}, selectableRoutes={" + java.lang.String.join(",", getSelectableRoutes()) + "}, deselectableRoutes={" + java.lang.String.join(",", getDeselectableRoutes()) + "}, transferableRoutes={" + java.lang.String.join(",", getTransferableRoutes()) + "}, volumeHandling=" + getVolumeHandling() + ", volumeMax=" + getVolumeMax() + ", volume=" + getVolume() + ", transferReason=" + getTransferReason() + ", transferInitiatorUserHandle=" + getTransferInitiatorUserHandle() + ", transferInitiatorPackageName=" + getTransferInitiatorPackageName() + " }";
    }

    private java.util.List<java.lang.String> convertToUniqueRouteIds(java.util.List<java.lang.String> list) {
        java.util.Objects.requireNonNull(list, "RouteIds cannot be null.");
        if (android.text.TextUtils.isEmpty(this.mProviderId)) {
            return new java.util.ArrayList(list);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(android.media.MediaRouter2Utils.toUniqueId(this.mProviderId, it.next()));
        }
        return arrayList;
    }

    public static final class Builder {
        private java.lang.String mClientPackageName;
        private android.os.Bundle mControlHints;
        private final java.util.List<java.lang.String> mDeselectableRoutes;
        private final java.lang.String mId;
        private boolean mIsSystemSession;
        private java.lang.CharSequence mName;
        private java.lang.String mOwnerPackageName;
        private java.lang.String mProviderId;
        private final java.util.List<java.lang.String> mSelectableRoutes;
        private final java.util.List<java.lang.String> mSelectedRoutes;
        private java.lang.String mTransferInitiatorPackageName;
        private android.os.UserHandle mTransferInitiatorUserHandle;
        private int mTransferReason;
        private final java.util.List<java.lang.String> mTransferableRoutes;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;

        public Builder(java.lang.String str, java.lang.String str2) {
            this.mVolumeHandling = 0;
            this.mTransferReason = 0;
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("id must not be empty");
            }
            this.mId = str;
            this.mClientPackageName = (java.lang.String) java.util.Objects.requireNonNull(str2, "clientPackageName must not be null");
            this.mSelectedRoutes = new java.util.ArrayList();
            this.mSelectableRoutes = new java.util.ArrayList();
            this.mDeselectableRoutes = new java.util.ArrayList();
            this.mTransferableRoutes = new java.util.ArrayList();
        }

        public Builder(android.media.RoutingSessionInfo routingSessionInfo) {
            this.mVolumeHandling = 0;
            this.mTransferReason = 0;
            java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            this.mId = routingSessionInfo.mId;
            this.mName = routingSessionInfo.mName;
            this.mClientPackageName = routingSessionInfo.mClientPackageName;
            this.mProviderId = routingSessionInfo.mProviderId;
            this.mSelectedRoutes = new java.util.ArrayList(routingSessionInfo.mSelectedRoutes);
            this.mSelectableRoutes = new java.util.ArrayList(routingSessionInfo.mSelectableRoutes);
            this.mDeselectableRoutes = new java.util.ArrayList(routingSessionInfo.mDeselectableRoutes);
            this.mTransferableRoutes = new java.util.ArrayList(routingSessionInfo.mTransferableRoutes);
            if (this.mProviderId != null) {
                this.mSelectedRoutes.replaceAll(new java.util.function.UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return android.media.MediaRouter2Utils.getOriginalId((java.lang.String) obj);
                    }
                });
                this.mSelectableRoutes.replaceAll(new java.util.function.UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return android.media.MediaRouter2Utils.getOriginalId((java.lang.String) obj);
                    }
                });
                this.mDeselectableRoutes.replaceAll(new java.util.function.UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return android.media.MediaRouter2Utils.getOriginalId((java.lang.String) obj);
                    }
                });
                this.mTransferableRoutes.replaceAll(new java.util.function.UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return android.media.MediaRouter2Utils.getOriginalId((java.lang.String) obj);
                    }
                });
            }
            this.mVolumeHandling = routingSessionInfo.mVolumeHandling;
            this.mVolumeMax = routingSessionInfo.mVolumeMax;
            this.mVolume = routingSessionInfo.mVolume;
            this.mControlHints = routingSessionInfo.mControlHints;
            this.mIsSystemSession = routingSessionInfo.mIsSystemSession;
            this.mTransferReason = routingSessionInfo.mTransferReason;
            this.mTransferInitiatorUserHandle = routingSessionInfo.mTransferInitiatorUserHandle;
            this.mTransferInitiatorPackageName = routingSessionInfo.mTransferInitiatorPackageName;
        }

        public android.media.RoutingSessionInfo.Builder setName(java.lang.CharSequence charSequence) {
            this.mName = charSequence;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setOwnerPackageName(java.lang.String str) {
            this.mOwnerPackageName = str;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setClientPackageName(java.lang.String str) {
            this.mClientPackageName = str;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setProviderId(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("providerId must not be empty");
            }
            this.mProviderId = str;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder clearSelectedRoutes() {
            this.mSelectedRoutes.clear();
            return this;
        }

        public android.media.RoutingSessionInfo.Builder addSelectedRoute(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectedRoutes.add(str);
            return this;
        }

        public android.media.RoutingSessionInfo.Builder removeSelectedRoute(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectedRoutes.remove(str);
            return this;
        }

        public android.media.RoutingSessionInfo.Builder clearSelectableRoutes() {
            this.mSelectableRoutes.clear();
            return this;
        }

        public android.media.RoutingSessionInfo.Builder addSelectableRoute(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectableRoutes.add(str);
            return this;
        }

        public android.media.RoutingSessionInfo.Builder removeSelectableRoute(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectableRoutes.remove(str);
            return this;
        }

        public android.media.RoutingSessionInfo.Builder clearDeselectableRoutes() {
            this.mDeselectableRoutes.clear();
            return this;
        }

        public android.media.RoutingSessionInfo.Builder addDeselectableRoute(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("routeId must not be empty");
            }
            this.mDeselectableRoutes.add(str);
            return this;
        }

        public android.media.RoutingSessionInfo.Builder removeDeselectableRoute(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("routeId must not be empty");
            }
            this.mDeselectableRoutes.remove(str);
            return this;
        }

        public android.media.RoutingSessionInfo.Builder clearTransferableRoutes() {
            this.mTransferableRoutes.clear();
            return this;
        }

        public android.media.RoutingSessionInfo.Builder addTransferableRoute(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("routeId must not be empty");
            }
            this.mTransferableRoutes.add(str);
            return this;
        }

        public android.media.RoutingSessionInfo.Builder removeTransferableRoute(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("routeId must not be empty");
            }
            this.mTransferableRoutes.remove(str);
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setVolumeHandling(int i) {
            this.mVolumeHandling = i;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setVolumeMax(int i) {
            this.mVolumeMax = i;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setVolume(int i) {
            this.mVolume = i;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setControlHints(android.os.Bundle bundle) {
            this.mControlHints = bundle;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setSystemSession(boolean z) {
            this.mIsSystemSession = z;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setTransferReason(int i) {
            this.mTransferReason = i;
            return this;
        }

        public android.media.RoutingSessionInfo.Builder setTransferInitiator(android.os.UserHandle userHandle, java.lang.String str) {
            this.mTransferInitiatorUserHandle = userHandle;
            this.mTransferInitiatorPackageName = str;
            return this;
        }

        public android.media.RoutingSessionInfo build() {
            if (this.mSelectedRoutes.isEmpty()) {
                throw new java.lang.IllegalArgumentException("selectedRoutes must not be empty");
            }
            return new android.media.RoutingSessionInfo(this);
        }
    }
}
