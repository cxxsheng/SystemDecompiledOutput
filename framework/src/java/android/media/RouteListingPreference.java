package android.media;

/* loaded from: classes2.dex */
public final class RouteListingPreference implements android.os.Parcelable {
    public static final java.lang.String ACTION_TRANSFER_MEDIA = "android.media.action.TRANSFER_MEDIA";
    public static final android.os.Parcelable.Creator<android.media.RouteListingPreference> CREATOR = new android.os.Parcelable.Creator<android.media.RouteListingPreference>() { // from class: android.media.RouteListingPreference.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.RouteListingPreference createFromParcel(android.os.Parcel parcel) {
            return new android.media.RouteListingPreference(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.RouteListingPreference[] newArray(int i) {
            return new android.media.RouteListingPreference[i];
        }
    };
    public static final java.lang.String EXTRA_ROUTE_ID = "android.media.extra.ROUTE_ID";
    private final java.util.List<android.media.RouteListingPreference.Item> mItems;
    private final android.content.ComponentName mLinkedItemComponentName;
    private final boolean mUseSystemOrdering;

    private RouteListingPreference(android.media.RouteListingPreference.Builder builder) {
        this.mItems = builder.mItems;
        this.mUseSystemOrdering = builder.mUseSystemOrdering;
        this.mLinkedItemComponentName = builder.mLinkedItemComponentName;
    }

    private RouteListingPreference(android.os.Parcel parcel) {
        this.mItems = java.util.List.copyOf(parcel.readParcelableList(new java.util.ArrayList(), android.media.RouteListingPreference.Item.class.getClassLoader(), android.media.RouteListingPreference.Item.class));
        this.mUseSystemOrdering = parcel.readBoolean();
        this.mLinkedItemComponentName = android.content.ComponentName.readFromParcel(parcel);
    }

    public java.util.List<android.media.RouteListingPreference.Item> getItems() {
        return this.mItems;
    }

    public boolean getUseSystemOrdering() {
        return this.mUseSystemOrdering;
    }

    public android.content.ComponentName getLinkedItemComponentName() {
        return this.mLinkedItemComponentName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelableList(this.mItems, i);
        parcel.writeBoolean(this.mUseSystemOrdering);
        android.content.ComponentName.writeToParcel(this.mLinkedItemComponentName, parcel);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.media.RouteListingPreference)) {
            return false;
        }
        android.media.RouteListingPreference routeListingPreference = (android.media.RouteListingPreference) obj;
        return this.mItems.equals(routeListingPreference.mItems) && this.mUseSystemOrdering == routeListingPreference.mUseSystemOrdering && java.util.Objects.equals(this.mLinkedItemComponentName, routeListingPreference.mLinkedItemComponentName);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mItems, java.lang.Boolean.valueOf(this.mUseSystemOrdering), this.mLinkedItemComponentName);
    }

    public static final class Builder {
        private android.content.ComponentName mLinkedItemComponentName;
        private java.util.List<android.media.RouteListingPreference.Item> mItems = java.util.List.of();
        private boolean mUseSystemOrdering = true;

        public android.media.RouteListingPreference.Builder setItems(java.util.List<android.media.RouteListingPreference.Item> list) {
            this.mItems = java.util.List.copyOf((java.util.Collection) java.util.Objects.requireNonNull(list));
            return this;
        }

        public android.media.RouteListingPreference.Builder setUseSystemOrdering(boolean z) {
            this.mUseSystemOrdering = z;
            return this;
        }

        public android.media.RouteListingPreference.Builder setLinkedItemComponentName(android.content.ComponentName componentName) {
            this.mLinkedItemComponentName = componentName;
            return this;
        }

        public android.media.RouteListingPreference build() {
            return new android.media.RouteListingPreference(this);
        }
    }

    public static final class Item implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.RouteListingPreference.Item> CREATOR = new android.os.Parcelable.Creator<android.media.RouteListingPreference.Item>() { // from class: android.media.RouteListingPreference.Item.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.RouteListingPreference.Item createFromParcel(android.os.Parcel parcel) {
                return new android.media.RouteListingPreference.Item(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.RouteListingPreference.Item[] newArray(int i) {
                return new android.media.RouteListingPreference.Item[i];
            }
        };
        public static final int FLAG_ONGOING_SESSION = 1;
        public static final int FLAG_ONGOING_SESSION_MANAGED = 2;
        public static final int FLAG_SUGGESTED = 4;
        public static final int SELECTION_BEHAVIOR_GO_TO_APP = 2;
        public static final int SELECTION_BEHAVIOR_NONE = 0;
        public static final int SELECTION_BEHAVIOR_TRANSFER = 1;
        public static final int SUBTEXT_AD_ROUTING_DISALLOWED = 4;
        public static final int SUBTEXT_CUSTOM = 10000;
        public static final int SUBTEXT_DEVICE_LOW_POWER = 5;
        public static final int SUBTEXT_DOWNLOADED_CONTENT_ROUTING_DISALLOWED = 3;
        public static final int SUBTEXT_ERROR_UNKNOWN = 1;
        public static final int SUBTEXT_NONE = 0;
        public static final int SUBTEXT_SUBSCRIPTION_REQUIRED = 2;
        public static final int SUBTEXT_TRACK_UNSUPPORTED = 7;
        public static final int SUBTEXT_UNAUTHORIZED = 6;
        private final java.lang.CharSequence mCustomSubtextMessage;
        private final int mFlags;
        private final java.lang.String mRouteId;
        private final int mSelectionBehavior;
        private final int mSubText;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SelectionBehavior {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SubText {
        }

        private Item(android.media.RouteListingPreference.Item.Builder builder) {
            this.mRouteId = builder.mRouteId;
            this.mSelectionBehavior = builder.mSelectionBehavior;
            this.mFlags = builder.mFlags;
            this.mSubText = builder.mSubText;
            this.mCustomSubtextMessage = builder.mCustomSubtextMessage;
            validateCustomMessageSubtext();
        }

        private Item(android.os.Parcel parcel) {
            this.mRouteId = parcel.readString();
            com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(this.mRouteId));
            this.mSelectionBehavior = parcel.readInt();
            this.mFlags = parcel.readInt();
            this.mSubText = parcel.readInt();
            this.mCustomSubtextMessage = parcel.readCharSequence();
            validateCustomMessageSubtext();
        }

        public java.lang.String getRouteId() {
            return this.mRouteId;
        }

        public int getSelectionBehavior() {
            return this.mSelectionBehavior;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public int getSubText() {
            return this.mSubText;
        }

        public java.lang.CharSequence getCustomSubtextMessage() {
            return this.mCustomSubtextMessage;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mRouteId);
            parcel.writeInt(this.mSelectionBehavior);
            parcel.writeInt(this.mFlags);
            parcel.writeInt(this.mSubText);
            parcel.writeCharSequence(this.mCustomSubtextMessage);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.media.RouteListingPreference.Item)) {
                return false;
            }
            android.media.RouteListingPreference.Item item = (android.media.RouteListingPreference.Item) obj;
            return this.mRouteId.equals(item.mRouteId) && this.mSelectionBehavior == item.mSelectionBehavior && this.mFlags == item.mFlags && this.mSubText == item.mSubText && android.text.TextUtils.equals(this.mCustomSubtextMessage, item.mCustomSubtextMessage);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mRouteId, java.lang.Integer.valueOf(this.mSelectionBehavior), java.lang.Integer.valueOf(this.mFlags), java.lang.Integer.valueOf(this.mSubText), this.mCustomSubtextMessage);
        }

        private void validateCustomMessageSubtext() {
            com.android.internal.util.Preconditions.checkArgument((this.mSubText == 10000 && this.mCustomSubtextMessage == null) ? false : true, "The custom subtext message cannot be null if subtext is SUBTEXT_CUSTOM.");
        }

        public static final class Builder {
            private java.lang.CharSequence mCustomSubtextMessage;
            private int mFlags;
            private final java.lang.String mRouteId;
            private int mSelectionBehavior;
            private int mSubText;

            public Builder(java.lang.String str) {
                com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(str));
                this.mRouteId = str;
                this.mSelectionBehavior = 1;
                this.mSubText = 0;
            }

            public android.media.RouteListingPreference.Item.Builder setSelectionBehavior(int i) {
                this.mSelectionBehavior = i;
                return this;
            }

            public android.media.RouteListingPreference.Item.Builder setFlags(int i) {
                this.mFlags = i;
                return this;
            }

            public android.media.RouteListingPreference.Item.Builder setSubText(int i) {
                this.mSubText = i;
                return this;
            }

            public android.media.RouteListingPreference.Item.Builder setCustomSubtextMessage(java.lang.CharSequence charSequence) {
                this.mCustomSubtextMessage = charSequence;
                return this;
            }

            public android.media.RouteListingPreference.Item build() {
                return new android.media.RouteListingPreference.Item(this);
            }
        }
    }
}
