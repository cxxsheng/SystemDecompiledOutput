package android.hardware.biometrics;

/* loaded from: classes.dex */
public final class PromptVerticalListContentView implements android.hardware.biometrics.PromptContentViewParcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.PromptVerticalListContentView> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.PromptVerticalListContentView>() { // from class: android.hardware.biometrics.PromptVerticalListContentView.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.PromptVerticalListContentView createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.PromptVerticalListContentView(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.PromptVerticalListContentView[] newArray(int i) {
            return new android.hardware.biometrics.PromptVerticalListContentView[i];
        }
    };
    private static final int MAX_EACH_ITEM_CHARACTER_NUMBER = 640;
    private static final int MAX_ITEM_NUMBER = 20;
    private final java.util.List<android.hardware.biometrics.PromptContentItemParcelable> mContentList;
    private final java.lang.String mDescription;

    private PromptVerticalListContentView(java.util.List<android.hardware.biometrics.PromptContentItemParcelable> list, java.lang.String str) {
        this.mContentList = list;
        this.mDescription = str;
    }

    private PromptVerticalListContentView(android.os.Parcel parcel) {
        this.mContentList = parcel.readArrayList(android.hardware.biometrics.PromptContentItemParcelable.class.getClassLoader(), android.hardware.biometrics.PromptContentItemParcelable.class);
        this.mDescription = parcel.readString();
    }

    public static int getMaxItemCount() {
        return 20;
    }

    public static int getMaxEachItemCharacterNumber() {
        return 640;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public java.util.List<android.hardware.biometrics.PromptContentItem> getListItems() {
        return new java.util.ArrayList(this.mContentList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeList(this.mContentList);
        parcel.writeString(this.mDescription);
    }

    public static final class Builder {
        private final java.util.List<android.hardware.biometrics.PromptContentItemParcelable> mContentList = new java.util.ArrayList();
        private java.lang.String mDescription;

        public android.hardware.biometrics.PromptVerticalListContentView.Builder setDescription(java.lang.String str) {
            this.mDescription = str;
            return this;
        }

        public android.hardware.biometrics.PromptVerticalListContentView.Builder addListItem(android.hardware.biometrics.PromptContentItem promptContentItem) {
            if (doesListItemExceedsCharLimit(promptContentItem)) {
                throw new java.lang.IllegalStateException("The character number of list item exceeds 640");
            }
            this.mContentList.add((android.hardware.biometrics.PromptContentItemParcelable) promptContentItem);
            return this;
        }

        public android.hardware.biometrics.PromptVerticalListContentView.Builder addListItem(android.hardware.biometrics.PromptContentItem promptContentItem, int i) {
            if (doesListItemExceedsCharLimit(promptContentItem)) {
                throw new java.lang.IllegalStateException("The character number of list item exceeds 640");
            }
            this.mContentList.add(i, (android.hardware.biometrics.PromptContentItemParcelable) promptContentItem);
            return this;
        }

        private boolean doesListItemExceedsCharLimit(android.hardware.biometrics.PromptContentItem promptContentItem) {
            return promptContentItem instanceof android.hardware.biometrics.PromptContentItemPlainText ? ((android.hardware.biometrics.PromptContentItemPlainText) promptContentItem).getText().length() > 640 : (promptContentItem instanceof android.hardware.biometrics.PromptContentItemBulletedText) && ((android.hardware.biometrics.PromptContentItemBulletedText) promptContentItem).getText().length() > 640;
        }

        public android.hardware.biometrics.PromptVerticalListContentView build() {
            if (this.mContentList.size() > 20) {
                throw new java.lang.IllegalStateException("The number of list items exceeds 20");
            }
            return new android.hardware.biometrics.PromptVerticalListContentView(this.mContentList, this.mDescription);
        }
    }
}
