package android.view.contentcapture;

/* loaded from: classes4.dex */
public final class DataRemovalRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.contentcapture.DataRemovalRequest> CREATOR = new android.os.Parcelable.Creator<android.view.contentcapture.DataRemovalRequest>() { // from class: android.view.contentcapture.DataRemovalRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.DataRemovalRequest createFromParcel(android.os.Parcel parcel) {
            return new android.view.contentcapture.DataRemovalRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.DataRemovalRequest[] newArray(int i) {
            return new android.view.contentcapture.DataRemovalRequest[i];
        }
    };
    public static final int FLAG_IS_PREFIX = 1;
    private final boolean mForEverything;
    private java.util.ArrayList<android.view.contentcapture.DataRemovalRequest.LocusIdRequest> mLocusIdRequests;
    private final java.lang.String mPackageName;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Flags {
    }

    private DataRemovalRequest(android.view.contentcapture.DataRemovalRequest.Builder builder) {
        this.mPackageName = android.app.ActivityThread.currentActivityThread().getApplication().getPackageName();
        this.mForEverything = builder.mForEverything;
        if (builder.mLocusIds != null) {
            int size = builder.mLocusIds.size();
            this.mLocusIdRequests = new java.util.ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                this.mLocusIdRequests.add(new android.view.contentcapture.DataRemovalRequest.LocusIdRequest((android.content.LocusId) builder.mLocusIds.get(i), builder.mFlags.get(i)));
            }
        }
    }

    private DataRemovalRequest(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mForEverything = parcel.readBoolean();
        if (!this.mForEverything) {
            int readInt = parcel.readInt();
            this.mLocusIdRequests = new java.util.ArrayList<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.mLocusIdRequests.add(new android.view.contentcapture.DataRemovalRequest.LocusIdRequest((android.content.LocusId) parcel.readValue(null), parcel.readInt()));
            }
        }
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public boolean isForEverything() {
        return this.mForEverything;
    }

    public java.util.List<android.view.contentcapture.DataRemovalRequest.LocusIdRequest> getLocusIdRequests() {
        return this.mLocusIdRequests;
    }

    public static final class Builder {
        private boolean mDestroyed;
        private android.util.IntArray mFlags;
        private boolean mForEverything;
        private java.util.ArrayList<android.content.LocusId> mLocusIds;

        public android.view.contentcapture.DataRemovalRequest.Builder forEverything() {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkState(this.mLocusIds == null, "Already added LocusIds");
            this.mForEverything = true;
            return this;
        }

        public android.view.contentcapture.DataRemovalRequest.Builder addLocusId(android.content.LocusId locusId, int i) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkState(!this.mForEverything, "Already is for everything");
            java.util.Objects.requireNonNull(locusId);
            if (this.mLocusIds == null) {
                this.mLocusIds = new java.util.ArrayList<>();
                this.mFlags = new android.util.IntArray();
            }
            this.mLocusIds.add(locusId);
            this.mFlags.add(i);
            return this;
        }

        public android.view.contentcapture.DataRemovalRequest build() {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkState(this.mForEverything || this.mLocusIds != null, "must call either #forEverything() or add one #addLocusId()");
            this.mDestroyed = true;
            return new android.view.contentcapture.DataRemovalRequest(this);
        }

        private void throwIfDestroyed() {
            com.android.internal.util.Preconditions.checkState(!this.mDestroyed, "Already destroyed!");
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeBoolean(this.mForEverything);
        if (!this.mForEverything) {
            int size = this.mLocusIdRequests.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                android.view.contentcapture.DataRemovalRequest.LocusIdRequest locusIdRequest = this.mLocusIdRequests.get(i2);
                parcel.writeValue(locusIdRequest.getLocusId());
                parcel.writeInt(locusIdRequest.getFlags());
            }
        }
    }

    public final class LocusIdRequest {
        private final int mFlags;
        private final android.content.LocusId mLocusId;

        private LocusIdRequest(android.content.LocusId locusId, int i) {
            this.mLocusId = locusId;
            this.mFlags = i;
        }

        public android.content.LocusId getLocusId() {
            return this.mLocusId;
        }

        public int getFlags() {
            return this.mFlags;
        }
    }
}
