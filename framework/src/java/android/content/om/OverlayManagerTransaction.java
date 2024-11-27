package android.content.om;

/* loaded from: classes.dex */
public final class OverlayManagerTransaction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.om.OverlayManagerTransaction> CREATOR = new android.os.Parcelable.Creator<android.content.om.OverlayManagerTransaction>() { // from class: android.content.om.OverlayManagerTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.om.OverlayManagerTransaction createFromParcel(android.os.Parcel parcel) {
            return new android.content.om.OverlayManagerTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.om.OverlayManagerTransaction[] newArray(int i) {
            return new android.content.om.OverlayManagerTransaction[i];
        }
    };
    private final java.util.List<android.content.om.OverlayManagerTransaction.Request> mRequests;
    private final boolean mSelfTargeting;

    private OverlayManagerTransaction(java.util.List<android.content.om.OverlayManagerTransaction.Request> list, boolean z) {
        java.util.Objects.requireNonNull(list);
        if (list.contains(null)) {
            throw new java.lang.IllegalArgumentException("null request");
        }
        this.mRequests = list;
        this.mSelfTargeting = z;
    }

    public static android.content.om.OverlayManagerTransaction newInstance() {
        return new android.content.om.OverlayManagerTransaction((java.util.List<android.content.om.OverlayManagerTransaction.Request>) new java.util.ArrayList(), true);
    }

    private OverlayManagerTransaction(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.mRequests = new java.util.ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mRequests.add(new android.content.om.OverlayManagerTransaction.Request(parcel.readInt(), (android.content.om.OverlayIdentifier) parcel.readParcelable(null, android.content.om.OverlayIdentifier.class), parcel.readInt(), parcel.readBundle(null)));
        }
        this.mSelfTargeting = false;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public java.util.Iterator<android.content.om.OverlayManagerTransaction.Request> getRequests() {
        return this.mRequests.iterator();
    }

    public java.lang.String toString() {
        return java.lang.String.format("OverlayManagerTransaction { mRequests = %s }", this.mRequests);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public static final class Request {
        public static final java.lang.String BUNDLE_FABRICATED_OVERLAY = "fabricated_overlay";
        public static final int TYPE_REGISTER_FABRICATED = 2;
        public static final int TYPE_SET_DISABLED = 1;
        public static final int TYPE_SET_ENABLED = 0;
        public static final int TYPE_UNREGISTER_FABRICATED = 3;
        public final android.os.Bundle extras;
        public final android.content.om.OverlayIdentifier overlay;
        public final int type;
        public final int userId;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface RequestType {
        }

        public Request(int i, android.content.om.OverlayIdentifier overlayIdentifier, int i2) {
            this(i, overlayIdentifier, i2, null);
        }

        public Request(int i, android.content.om.OverlayIdentifier overlayIdentifier, int i2, android.os.Bundle bundle) {
            this.type = i;
            this.overlay = overlayIdentifier;
            this.userId = i2;
            this.extras = bundle;
        }

        public java.lang.String toString() {
            return java.lang.String.format(java.util.Locale.US, "Request{type=0x%02x (%s), overlay=%s, userId=%d}", java.lang.Integer.valueOf(this.type), typeToString(), this.overlay, java.lang.Integer.valueOf(this.userId));
        }

        public java.lang.String typeToString() {
            switch (this.type) {
                case 0:
                    return "TYPE_SET_ENABLED";
                case 1:
                    return "TYPE_SET_DISABLED";
                case 2:
                    return "TYPE_REGISTER_FABRICATED";
                case 3:
                    return "TYPE_UNREGISTER_FABRICATED";
                default:
                    return java.lang.String.format("TYPE_UNKNOWN (0x%02x)", java.lang.Integer.valueOf(this.type));
            }
        }
    }

    public static final class Builder {
        private final java.util.List<android.content.om.OverlayManagerTransaction.Request> mRequests = new java.util.ArrayList();

        public android.content.om.OverlayManagerTransaction.Builder setEnabled(android.content.om.OverlayIdentifier overlayIdentifier, boolean z) {
            return setEnabled(overlayIdentifier, z, android.os.UserHandle.myUserId());
        }

        public android.content.om.OverlayManagerTransaction.Builder setEnabled(android.content.om.OverlayIdentifier overlayIdentifier, boolean z, int i) {
            com.android.internal.util.Preconditions.checkNotNull(overlayIdentifier);
            this.mRequests.add(new android.content.om.OverlayManagerTransaction.Request(!z ? 1 : 0, overlayIdentifier, i));
            return this;
        }

        public android.content.om.OverlayManagerTransaction.Builder registerFabricatedOverlay(android.content.om.FabricatedOverlay fabricatedOverlay) {
            this.mRequests.add(android.content.om.OverlayManagerTransaction.generateRegisterFabricatedOverlayRequest(fabricatedOverlay));
            return this;
        }

        public android.content.om.OverlayManagerTransaction.Builder unregisterFabricatedOverlay(android.content.om.OverlayIdentifier overlayIdentifier) {
            this.mRequests.add(android.content.om.OverlayManagerTransaction.generateUnRegisterFabricatedOverlayRequest(overlayIdentifier));
            return this;
        }

        public android.content.om.OverlayManagerTransaction build() {
            return new android.content.om.OverlayManagerTransaction(this.mRequests, false);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int size = this.mRequests.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            android.content.om.OverlayManagerTransaction.Request request = this.mRequests.get(i2);
            parcel.writeInt(request.type);
            parcel.writeParcelable(request.overlay, i);
            parcel.writeInt(request.userId);
            parcel.writeBundle(request.extras);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.om.OverlayManagerTransaction.Request generateRegisterFabricatedOverlayRequest(android.content.om.FabricatedOverlay fabricatedOverlay) {
        java.util.Objects.requireNonNull(fabricatedOverlay);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(android.content.om.OverlayManagerTransaction.Request.BUNDLE_FABRICATED_OVERLAY, fabricatedOverlay.mOverlay);
        return new android.content.om.OverlayManagerTransaction.Request(2, fabricatedOverlay.getIdentifier(), -1, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.om.OverlayManagerTransaction.Request generateUnRegisterFabricatedOverlayRequest(android.content.om.OverlayIdentifier overlayIdentifier) {
        java.util.Objects.requireNonNull(overlayIdentifier);
        return new android.content.om.OverlayManagerTransaction.Request(3, overlayIdentifier, -1);
    }

    public void registerFabricatedOverlay(android.content.om.FabricatedOverlay fabricatedOverlay) {
        this.mRequests.add(generateRegisterFabricatedOverlayRequest(fabricatedOverlay));
    }

    public void unregisterFabricatedOverlay(android.content.om.OverlayIdentifier overlayIdentifier) {
        this.mRequests.add(generateUnRegisterFabricatedOverlayRequest(overlayIdentifier));
    }

    boolean isSelfTargeting() {
        return this.mSelfTargeting;
    }
}
