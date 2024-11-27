package android.media;

/* loaded from: classes2.dex */
public final class RouteDiscoveryPreference implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.RouteDiscoveryPreference> CREATOR = new android.os.Parcelable.Creator<android.media.RouteDiscoveryPreference>() { // from class: android.media.RouteDiscoveryPreference.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.RouteDiscoveryPreference createFromParcel(android.os.Parcel parcel) {
            return new android.media.RouteDiscoveryPreference(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.RouteDiscoveryPreference[] newArray(int i) {
            return new android.media.RouteDiscoveryPreference[i];
        }
    };

    @android.annotation.SystemApi
    public static final android.media.RouteDiscoveryPreference EMPTY = new android.media.RouteDiscoveryPreference.Builder(java.util.Collections.emptyList(), false).build();
    private final java.util.List<java.lang.String> mAllowedPackages;
    private final android.os.Bundle mExtras;
    private final java.util.List<java.lang.String> mPackageOrder;
    private final java.util.List<java.lang.String> mPreferredFeatures;
    private final boolean mShouldPerformActiveScan;

    RouteDiscoveryPreference(android.media.RouteDiscoveryPreference.Builder builder) {
        this.mPreferredFeatures = builder.mPreferredFeatures;
        this.mPackageOrder = builder.mPackageOrder;
        this.mAllowedPackages = builder.mAllowedPackages;
        this.mShouldPerformActiveScan = builder.mActiveScan;
        this.mExtras = builder.mExtras;
    }

    RouteDiscoveryPreference(android.os.Parcel parcel) {
        this.mPreferredFeatures = parcel.createStringArrayList();
        this.mPackageOrder = parcel.createStringArrayList();
        this.mAllowedPackages = parcel.createStringArrayList();
        this.mShouldPerformActiveScan = parcel.readBoolean();
        this.mExtras = parcel.readBundle();
    }

    public java.util.List<java.lang.String> getPreferredFeatures() {
        return this.mPreferredFeatures;
    }

    public java.util.List<java.lang.String> getDeduplicationPackageOrder() {
        return this.mPackageOrder;
    }

    public java.util.List<java.lang.String> getAllowedPackages() {
        return this.mAllowedPackages;
    }

    public boolean shouldPerformActiveScan() {
        return this.mShouldPerformActiveScan;
    }

    public boolean shouldRemoveDuplicates() {
        return !this.mPackageOrder.isEmpty();
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStringList(this.mPreferredFeatures);
        parcel.writeStringList(this.mPackageOrder);
        parcel.writeStringList(this.mAllowedPackages);
        parcel.writeBoolean(this.mShouldPerformActiveScan);
        parcel.writeBundle(this.mExtras);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "RouteDiscoveryPreference");
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "mShouldPerformActiveScan=" + this.mShouldPerformActiveScan);
        printWriter.println(str2 + "mPreferredFeatures=" + this.mPreferredFeatures);
        printWriter.println(str2 + "mPackageOrder=" + this.mPackageOrder);
        printWriter.println(str2 + "mAllowedPackages=" + this.mAllowedPackages);
        printWriter.println(str2 + "mExtras=" + this.mExtras);
    }

    public java.lang.String toString() {
        return "RouteDiscoveryRequest{ preferredFeatures={" + java.lang.String.join(", ", this.mPreferredFeatures) + "}, activeScan=" + this.mShouldPerformActiveScan + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.media.RouteDiscoveryPreference)) {
            return false;
        }
        android.media.RouteDiscoveryPreference routeDiscoveryPreference = (android.media.RouteDiscoveryPreference) obj;
        return java.util.Objects.equals(this.mPreferredFeatures, routeDiscoveryPreference.mPreferredFeatures) && java.util.Objects.equals(this.mPackageOrder, routeDiscoveryPreference.mPackageOrder) && java.util.Objects.equals(this.mAllowedPackages, routeDiscoveryPreference.mAllowedPackages) && this.mShouldPerformActiveScan == routeDiscoveryPreference.mShouldPerformActiveScan;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPreferredFeatures, this.mPackageOrder, this.mAllowedPackages, java.lang.Boolean.valueOf(this.mShouldPerformActiveScan));
    }

    public static final class Builder {
        boolean mActiveScan;
        java.util.List<java.lang.String> mAllowedPackages;
        android.os.Bundle mExtras;
        java.util.List<java.lang.String> mPackageOrder;
        java.util.List<java.lang.String> mPreferredFeatures;

        public Builder(java.util.List<java.lang.String> list, boolean z) {
            java.util.Objects.requireNonNull(list, "preferredFeatures must not be null");
            this.mPreferredFeatures = (java.util.List) list.stream().filter(new java.util.function.Predicate() { // from class: android.media.RouteDiscoveryPreference$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.media.RouteDiscoveryPreference.Builder.lambda$new$0((java.lang.String) obj);
                }
            }).collect(java.util.stream.Collectors.toList());
            this.mPackageOrder = java.util.List.of();
            this.mAllowedPackages = java.util.List.of();
            this.mActiveScan = z;
        }

        static /* synthetic */ boolean lambda$new$0(java.lang.String str) {
            return !android.text.TextUtils.isEmpty(str);
        }

        public Builder(android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            java.util.Objects.requireNonNull(routeDiscoveryPreference, "preference must not be null");
            this.mPreferredFeatures = routeDiscoveryPreference.getPreferredFeatures();
            this.mPackageOrder = routeDiscoveryPreference.getDeduplicationPackageOrder();
            this.mAllowedPackages = routeDiscoveryPreference.getAllowedPackages();
            this.mActiveScan = routeDiscoveryPreference.shouldPerformActiveScan();
            this.mExtras = routeDiscoveryPreference.getExtras();
        }

        public Builder(java.util.Collection<android.media.RouteDiscoveryPreference> collection) {
            java.util.Objects.requireNonNull(collection, "preferences must not be null");
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.HashSet hashSet2 = new java.util.HashSet();
            this.mPackageOrder = java.util.List.of();
            boolean z = false;
            for (android.media.RouteDiscoveryPreference routeDiscoveryPreference : collection) {
                hashSet.addAll(routeDiscoveryPreference.mPreferredFeatures);
                hashSet2.addAll(routeDiscoveryPreference.mAllowedPackages);
                z |= routeDiscoveryPreference.mShouldPerformActiveScan;
                if (this.mPackageOrder.isEmpty() && !routeDiscoveryPreference.mPackageOrder.isEmpty()) {
                    this.mPackageOrder = java.util.List.copyOf(routeDiscoveryPreference.mPackageOrder);
                }
            }
            this.mPreferredFeatures = java.util.List.copyOf(hashSet);
            this.mAllowedPackages = java.util.List.copyOf(hashSet2);
            this.mActiveScan = z;
        }

        public android.media.RouteDiscoveryPreference.Builder setPreferredFeatures(java.util.List<java.lang.String> list) {
            java.util.Objects.requireNonNull(list, "preferredFeatures must not be null");
            this.mPreferredFeatures = (java.util.List) list.stream().filter(new java.util.function.Predicate() { // from class: android.media.RouteDiscoveryPreference$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.media.RouteDiscoveryPreference.Builder.lambda$setPreferredFeatures$1((java.lang.String) obj);
                }
            }).collect(java.util.stream.Collectors.toList());
            return this;
        }

        static /* synthetic */ boolean lambda$setPreferredFeatures$1(java.lang.String str) {
            return !android.text.TextUtils.isEmpty(str);
        }

        public android.media.RouteDiscoveryPreference.Builder setAllowedPackages(java.util.List<java.lang.String> list) {
            java.util.Objects.requireNonNull(list, "allowedPackages must not be null");
            this.mAllowedPackages = java.util.List.copyOf(list);
            return this;
        }

        public android.media.RouteDiscoveryPreference.Builder setShouldPerformActiveScan(boolean z) {
            this.mActiveScan = z;
            return this;
        }

        public android.media.RouteDiscoveryPreference.Builder setDeduplicationPackageOrder(java.util.List<java.lang.String> list) {
            java.util.Objects.requireNonNull(list, "packageOrder must not be null");
            this.mPackageOrder = java.util.List.copyOf(list);
            return this;
        }

        public android.media.RouteDiscoveryPreference.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.media.RouteDiscoveryPreference build() {
            return new android.media.RouteDiscoveryPreference(this);
        }
    }
}
