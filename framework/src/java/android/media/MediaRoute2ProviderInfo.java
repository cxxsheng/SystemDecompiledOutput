package android.media;

/* loaded from: classes2.dex */
public final class MediaRoute2ProviderInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.MediaRoute2ProviderInfo> CREATOR = new android.os.Parcelable.Creator<android.media.MediaRoute2ProviderInfo>() { // from class: android.media.MediaRoute2ProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaRoute2ProviderInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.MediaRoute2ProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaRoute2ProviderInfo[] newArray(int i) {
            return new android.media.MediaRoute2ProviderInfo[i];
        }
    };
    final android.util.ArrayMap<java.lang.String, android.media.MediaRoute2Info> mRoutes;
    final java.lang.String mUniqueId;

    MediaRoute2ProviderInfo(android.media.MediaRoute2ProviderInfo.Builder builder) {
        java.util.Objects.requireNonNull(builder, "builder must not be null.");
        this.mUniqueId = builder.mUniqueId;
        this.mRoutes = builder.mRoutes;
    }

    MediaRoute2ProviderInfo(android.os.Parcel parcel) {
        this.mUniqueId = parcel.readString();
        android.util.ArrayMap<java.lang.String, android.media.MediaRoute2Info> createTypedArrayMap = parcel.createTypedArrayMap(android.media.MediaRoute2Info.CREATOR);
        this.mRoutes = createTypedArrayMap == null ? android.util.ArrayMap.EMPTY : createTypedArrayMap;
    }

    public boolean isValid() {
        if (this.mUniqueId == null) {
            return false;
        }
        int size = this.mRoutes.size();
        for (int i = 0; i < size; i++) {
            android.media.MediaRoute2Info valueAt = this.mRoutes.valueAt(i);
            if (valueAt == null || !valueAt.isValid()) {
                return false;
            }
        }
        return true;
    }

    public java.lang.String getUniqueId() {
        return this.mUniqueId;
    }

    public android.media.MediaRoute2Info getRoute(java.lang.String str) {
        return this.mRoutes.get(java.util.Objects.requireNonNull(str, "routeId must not be null"));
    }

    public java.util.Collection<android.media.MediaRoute2Info> getRoutes() {
        return this.mRoutes.values();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mUniqueId);
        parcel.writeTypedArrayMap(this.mRoutes, i);
    }

    public java.lang.String toString() {
        return "MediaRouteProviderInfo { uniqueId=" + this.mUniqueId + ", routes=" + java.util.Arrays.toString(getRoutes().toArray()) + " }";
    }

    public static final class Builder {
        final android.util.ArrayMap<java.lang.String, android.media.MediaRoute2Info> mRoutes;
        java.lang.String mUniqueId;

        public Builder() {
            this.mRoutes = new android.util.ArrayMap<>();
        }

        public Builder(android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
            java.util.Objects.requireNonNull(mediaRoute2ProviderInfo, "descriptor must not be null");
            this.mUniqueId = mediaRoute2ProviderInfo.mUniqueId;
            this.mRoutes = new android.util.ArrayMap<>(mediaRoute2ProviderInfo.mRoutes);
        }

        public android.media.MediaRoute2ProviderInfo.Builder setUniqueId(java.lang.String str, java.lang.String str2) {
            if (android.text.TextUtils.equals(this.mUniqueId, str2)) {
                return this;
            }
            this.mUniqueId = str2;
            android.util.ArrayMap<? extends java.lang.String, ? extends android.media.MediaRoute2Info> arrayMap = new android.util.ArrayMap<>();
            java.util.Iterator<java.util.Map.Entry<java.lang.String, android.media.MediaRoute2Info>> it = this.mRoutes.entrySet().iterator();
            while (it.hasNext()) {
                android.media.MediaRoute2Info build = new android.media.MediaRoute2Info.Builder(it.next().getValue()).setPackageName(str).setProviderId(this.mUniqueId).build();
                arrayMap.put(build.getOriginalId(), build);
            }
            this.mRoutes.clear();
            this.mRoutes.putAll(arrayMap);
            return this;
        }

        public android.media.MediaRoute2ProviderInfo.Builder setSystemRouteProvider(boolean z) {
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                android.media.MediaRoute2Info valueAt = this.mRoutes.valueAt(i);
                if (valueAt.isSystemRoute() != z) {
                    this.mRoutes.setValueAt(i, new android.media.MediaRoute2Info.Builder(valueAt).setSystemRoute(z).build());
                }
            }
            return this;
        }

        public android.media.MediaRoute2ProviderInfo.Builder addRoute(android.media.MediaRoute2Info mediaRoute2Info) {
            java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (this.mRoutes.containsKey(mediaRoute2Info.getOriginalId())) {
                throw new java.lang.IllegalArgumentException("A route with the same id is already added");
            }
            if (this.mUniqueId != null) {
                this.mRoutes.put(mediaRoute2Info.getOriginalId(), new android.media.MediaRoute2Info.Builder(mediaRoute2Info).setProviderId(this.mUniqueId).build());
            } else {
                this.mRoutes.put(mediaRoute2Info.getOriginalId(), mediaRoute2Info);
            }
            return this;
        }

        public android.media.MediaRoute2ProviderInfo.Builder addRoutes(java.util.Collection<android.media.MediaRoute2Info> collection) {
            java.util.Objects.requireNonNull(collection, "routes must not be null");
            if (!collection.isEmpty()) {
                java.util.Iterator<android.media.MediaRoute2Info> it = collection.iterator();
                while (it.hasNext()) {
                    addRoute(it.next());
                }
            }
            return this;
        }

        public android.media.MediaRoute2ProviderInfo build() {
            return new android.media.MediaRoute2ProviderInfo(this);
        }
    }
}
