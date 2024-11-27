package android.view;

/* loaded from: classes4.dex */
public final class KeyboardShortcutGroup implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.KeyboardShortcutGroup> CREATOR = new android.os.Parcelable.Creator<android.view.KeyboardShortcutGroup>() { // from class: android.view.KeyboardShortcutGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.KeyboardShortcutGroup createFromParcel(android.os.Parcel parcel) {
            return new android.view.KeyboardShortcutGroup(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.KeyboardShortcutGroup[] newArray(int i) {
            return new android.view.KeyboardShortcutGroup[i];
        }
    };
    private final java.util.List<android.view.KeyboardShortcutInfo> mItems;
    private final java.lang.CharSequence mLabel;
    private java.lang.CharSequence mPackageName;
    private boolean mSystemGroup;

    public KeyboardShortcutGroup(java.lang.CharSequence charSequence, java.util.List<android.view.KeyboardShortcutInfo> list) {
        this.mLabel = charSequence;
        this.mItems = new java.util.ArrayList((java.util.Collection) com.android.internal.util.Preconditions.checkNotNull(list));
    }

    public KeyboardShortcutGroup(java.lang.CharSequence charSequence) {
        this(charSequence, (java.util.List<android.view.KeyboardShortcutInfo>) java.util.Collections.emptyList());
    }

    public KeyboardShortcutGroup(java.lang.CharSequence charSequence, java.util.List<android.view.KeyboardShortcutInfo> list, boolean z) {
        this.mLabel = charSequence;
        this.mItems = new java.util.ArrayList((java.util.Collection) com.android.internal.util.Preconditions.checkNotNull(list));
        this.mSystemGroup = z;
    }

    public KeyboardShortcutGroup(java.lang.CharSequence charSequence, boolean z) {
        this(charSequence, java.util.Collections.emptyList(), z);
    }

    private KeyboardShortcutGroup(android.os.Parcel parcel) {
        this.mItems = new java.util.ArrayList();
        this.mLabel = parcel.readCharSequence();
        parcel.readTypedList(this.mItems, android.view.KeyboardShortcutInfo.CREATOR);
        this.mSystemGroup = parcel.readInt() == 1;
        this.mPackageName = parcel.readCharSequence();
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public java.util.List<android.view.KeyboardShortcutInfo> getItems() {
        return this.mItems;
    }

    public boolean isSystemGroup() {
        return this.mSystemGroup;
    }

    public void setPackageName(java.lang.CharSequence charSequence) {
        this.mPackageName = charSequence;
    }

    public java.lang.CharSequence getPackageName() {
        return this.mPackageName;
    }

    public void addItem(android.view.KeyboardShortcutInfo keyboardShortcutInfo) {
        this.mItems.add(keyboardShortcutInfo);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeTypedList(this.mItems);
        parcel.writeInt(this.mSystemGroup ? 1 : 0);
        parcel.writeCharSequence(this.mPackageName);
    }
}
