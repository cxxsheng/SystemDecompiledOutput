package android.view;

/* loaded from: classes4.dex */
public final class KeyboardShortcutInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.KeyboardShortcutInfo> CREATOR = new android.os.Parcelable.Creator<android.view.KeyboardShortcutInfo>() { // from class: android.view.KeyboardShortcutInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.KeyboardShortcutInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.KeyboardShortcutInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.KeyboardShortcutInfo[] newArray(int i) {
            return new android.view.KeyboardShortcutInfo[i];
        }
    };
    private final char mBaseCharacter;
    private final android.graphics.drawable.Icon mIcon;
    private final int mKeycode;
    private final java.lang.CharSequence mLabel;
    private final int mModifiers;

    public KeyboardShortcutInfo(java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, int i, int i2) {
        this.mLabel = charSequence;
        this.mIcon = icon;
        boolean z = false;
        this.mBaseCharacter = (char) 0;
        if (i >= 0 && i <= android.view.KeyEvent.getMaxKeyCode()) {
            z = true;
        }
        com.android.internal.util.Preconditions.checkArgument(z);
        this.mKeycode = i;
        this.mModifiers = i2;
    }

    public KeyboardShortcutInfo(java.lang.CharSequence charSequence, int i, int i2) {
        this(charSequence, null, i, i2);
    }

    public KeyboardShortcutInfo(java.lang.CharSequence charSequence, char c, int i) {
        this.mLabel = charSequence;
        com.android.internal.util.Preconditions.checkArgument(c != 0);
        this.mBaseCharacter = c;
        this.mKeycode = 0;
        this.mModifiers = i;
        this.mIcon = null;
    }

    private KeyboardShortcutInfo(android.os.Parcel parcel) {
        this.mLabel = parcel.readCharSequence();
        this.mIcon = (android.graphics.drawable.Icon) parcel.readParcelable(null, android.graphics.drawable.Icon.class);
        this.mBaseCharacter = (char) parcel.readInt();
        this.mKeycode = parcel.readInt();
        this.mModifiers = parcel.readInt();
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public int getKeycode() {
        return this.mKeycode;
    }

    public char getBaseCharacter() {
        return this.mBaseCharacter;
    }

    public int getModifiers() {
        return this.mModifiers;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeParcelable(this.mIcon, 0);
        parcel.writeInt(this.mBaseCharacter);
        parcel.writeInt(this.mKeycode);
        parcel.writeInt(this.mModifiers);
    }
}
