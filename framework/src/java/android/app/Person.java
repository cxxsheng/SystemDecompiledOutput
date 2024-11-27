package android.app;

/* loaded from: classes.dex */
public final class Person implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.Person> CREATOR = new android.os.Parcelable.Creator<android.app.Person>() { // from class: android.app.Person.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.Person createFromParcel(android.os.Parcel parcel) {
            return new android.app.Person(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.Person[] newArray(int i) {
            return new android.app.Person[i];
        }
    };
    private android.graphics.drawable.Icon mIcon;
    private boolean mIsBot;
    private boolean mIsImportant;
    private java.lang.String mKey;
    private java.lang.CharSequence mName;
    private java.lang.String mUri;

    private Person(android.os.Parcel parcel) {
        this.mName = parcel.readCharSequence();
        if (parcel.readInt() != 0) {
            this.mIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        }
        this.mUri = parcel.readString();
        this.mKey = parcel.readString();
        this.mIsImportant = parcel.readBoolean();
        this.mIsBot = parcel.readBoolean();
    }

    private Person(android.app.Person.Builder builder) {
        this.mName = builder.mName;
        this.mIcon = builder.mIcon;
        this.mUri = builder.mUri;
        this.mKey = builder.mKey;
        this.mIsBot = builder.mIsBot;
        this.mIsImportant = builder.mIsImportant;
    }

    public android.app.Person.Builder toBuilder() {
        return new android.app.Person.Builder();
    }

    public java.lang.String getUri() {
        return this.mUri;
    }

    public java.lang.CharSequence getName() {
        return this.mName;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public java.lang.String getKey() {
        return this.mKey;
    }

    public boolean isBot() {
        return this.mIsBot;
    }

    public boolean isImportant() {
        return this.mIsImportant;
    }

    public java.lang.String resolveToLegacyUri() {
        if (this.mUri != null) {
            return this.mUri;
        }
        if (this.mName != null) {
            return "name:" + ((java.lang.Object) this.mName);
        }
        return "";
    }

    public android.net.Uri getIconUri() {
        if (this.mIcon != null) {
            if (this.mIcon.getType() == 4 || this.mIcon.getType() == 6) {
                return this.mIcon.getUri();
            }
            return null;
        }
        return null;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.app.Person)) {
            return false;
        }
        android.app.Person person = (android.app.Person) obj;
        if (!java.util.Objects.equals(this.mName, person.mName)) {
            return false;
        }
        if (this.mIcon == null) {
            if (person.mIcon != null) {
                return false;
            }
        } else if (person.mIcon == null || !this.mIcon.sameAs(person.mIcon)) {
            return false;
        }
        return java.util.Objects.equals(this.mUri, person.mUri) && java.util.Objects.equals(this.mKey, person.mKey) && this.mIsBot == person.mIsBot && this.mIsImportant == person.mIsImportant;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mName, this.mIcon, this.mUri, this.mKey, java.lang.Boolean.valueOf(this.mIsBot), java.lang.Boolean.valueOf(this.mIsImportant));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mName);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.mUri);
        parcel.writeString(this.mKey);
        parcel.writeBoolean(this.mIsImportant);
        parcel.writeBoolean(this.mIsBot);
    }

    public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
        consumer.accept(getIconUri());
        if (android.app.Flags.visitRiskyUris() && this.mUri != null && !this.mUri.isEmpty()) {
            consumer.accept(android.net.Uri.parse(this.mUri));
        }
    }

    public static class Builder {
        private android.graphics.drawable.Icon mIcon;
        private boolean mIsBot;
        private boolean mIsImportant;
        private java.lang.String mKey;
        private java.lang.CharSequence mName;
        private java.lang.String mUri;

        public Builder() {
        }

        private Builder(android.app.Person person) {
            this.mName = person.mName;
            this.mIcon = person.mIcon;
            this.mUri = person.mUri;
            this.mKey = person.mKey;
            this.mIsBot = person.mIsBot;
            this.mIsImportant = person.mIsImportant;
        }

        public android.app.Person.Builder setName(java.lang.CharSequence charSequence) {
            this.mName = charSequence;
            return this;
        }

        public android.app.Person.Builder setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public android.app.Person.Builder setUri(java.lang.String str) {
            this.mUri = str;
            return this;
        }

        public android.app.Person.Builder setKey(java.lang.String str) {
            this.mKey = str;
            return this;
        }

        public android.app.Person.Builder setImportant(boolean z) {
            this.mIsImportant = z;
            return this;
        }

        public android.app.Person.Builder setBot(boolean z) {
            this.mIsBot = z;
            return this;
        }

        public android.app.Person build() {
            return new android.app.Person(this);
        }
    }
}
