package android.print;

/* loaded from: classes3.dex */
public final class PrinterCapabilitiesInfo implements android.os.Parcelable {
    public static final int DEFAULT_UNDEFINED = -1;
    private static final int PROPERTY_COLOR_MODE = 2;
    private static final int PROPERTY_COUNT = 4;
    private static final int PROPERTY_DUPLEX_MODE = 3;
    private static final int PROPERTY_MEDIA_SIZE = 0;
    private static final int PROPERTY_RESOLUTION = 1;
    private int mColorModes;
    private final int[] mDefaults;
    private int mDuplexModes;
    private java.util.List<android.print.PrintAttributes.MediaSize> mMediaSizes;
    private android.print.PrintAttributes.Margins mMinMargins;
    private java.util.List<android.print.PrintAttributes.Resolution> mResolutions;
    private static final android.print.PrintAttributes.Margins DEFAULT_MARGINS = new android.print.PrintAttributes.Margins(0, 0, 0, 0);
    public static final android.os.Parcelable.Creator<android.print.PrinterCapabilitiesInfo> CREATOR = new android.os.Parcelable.Creator<android.print.PrinterCapabilitiesInfo>() { // from class: android.print.PrinterCapabilitiesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrinterCapabilitiesInfo createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrinterCapabilitiesInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrinterCapabilitiesInfo[] newArray(int i) {
            return new android.print.PrinterCapabilitiesInfo[i];
        }
    };

    public PrinterCapabilitiesInfo() {
        this.mMinMargins = DEFAULT_MARGINS;
        this.mDefaults = new int[4];
        java.util.Arrays.fill(this.mDefaults, -1);
    }

    public PrinterCapabilitiesInfo(android.print.PrinterCapabilitiesInfo printerCapabilitiesInfo) {
        this.mMinMargins = DEFAULT_MARGINS;
        this.mDefaults = new int[4];
        copyFrom(printerCapabilitiesInfo);
    }

    public void copyFrom(android.print.PrinterCapabilitiesInfo printerCapabilitiesInfo) {
        if (this == printerCapabilitiesInfo) {
            return;
        }
        this.mMinMargins = printerCapabilitiesInfo.mMinMargins;
        if (printerCapabilitiesInfo.mMediaSizes != null) {
            if (this.mMediaSizes != null) {
                this.mMediaSizes.clear();
                this.mMediaSizes.addAll(printerCapabilitiesInfo.mMediaSizes);
            } else {
                this.mMediaSizes = new java.util.ArrayList(printerCapabilitiesInfo.mMediaSizes);
            }
        } else {
            this.mMediaSizes = null;
        }
        if (printerCapabilitiesInfo.mResolutions != null) {
            if (this.mResolutions != null) {
                this.mResolutions.clear();
                this.mResolutions.addAll(printerCapabilitiesInfo.mResolutions);
            } else {
                this.mResolutions = new java.util.ArrayList(printerCapabilitiesInfo.mResolutions);
            }
        } else {
            this.mResolutions = null;
        }
        this.mColorModes = printerCapabilitiesInfo.mColorModes;
        this.mDuplexModes = printerCapabilitiesInfo.mDuplexModes;
        int length = printerCapabilitiesInfo.mDefaults.length;
        for (int i = 0; i < length; i++) {
            this.mDefaults[i] = printerCapabilitiesInfo.mDefaults[i];
        }
    }

    public java.util.List<android.print.PrintAttributes.MediaSize> getMediaSizes() {
        return java.util.Collections.unmodifiableList(this.mMediaSizes);
    }

    public java.util.List<android.print.PrintAttributes.Resolution> getResolutions() {
        return java.util.Collections.unmodifiableList(this.mResolutions);
    }

    public android.print.PrintAttributes.Margins getMinMargins() {
        return this.mMinMargins;
    }

    public int getColorModes() {
        return this.mColorModes;
    }

    public int getDuplexModes() {
        return this.mDuplexModes;
    }

    public android.print.PrintAttributes getDefaults() {
        android.print.PrintAttributes.Builder builder = new android.print.PrintAttributes.Builder();
        builder.setMinMargins(this.mMinMargins);
        int i = this.mDefaults[0];
        if (i >= 0) {
            builder.setMediaSize(this.mMediaSizes.get(i));
        }
        int i2 = this.mDefaults[1];
        if (i2 >= 0) {
            builder.setResolution(this.mResolutions.get(i2));
        }
        int i3 = this.mDefaults[2];
        if (i3 > 0) {
            builder.setColorMode(i3);
        }
        int i4 = this.mDefaults[3];
        if (i4 > 0) {
            builder.setDuplexMode(i4);
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void enforceValidMask(int i, java.util.function.IntConsumer intConsumer) {
        while (i > 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            intConsumer.accept(numberOfTrailingZeros);
        }
    }

    private PrinterCapabilitiesInfo(android.os.Parcel parcel) {
        this.mMinMargins = DEFAULT_MARGINS;
        this.mDefaults = new int[4];
        this.mMinMargins = (android.print.PrintAttributes.Margins) com.android.internal.util.Preconditions.checkNotNull(readMargins(parcel));
        readMediaSizes(parcel);
        readResolutions(parcel);
        this.mColorModes = parcel.readInt();
        enforceValidMask(this.mColorModes, new java.util.function.IntConsumer() { // from class: android.print.PrinterCapabilitiesInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                android.print.PrintAttributes.enforceValidColorMode(i);
            }
        });
        this.mDuplexModes = parcel.readInt();
        enforceValidMask(this.mDuplexModes, new java.util.function.IntConsumer() { // from class: android.print.PrinterCapabilitiesInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                android.print.PrintAttributes.enforceValidDuplexMode(i);
            }
        });
        readDefaults(parcel);
        com.android.internal.util.Preconditions.checkArgument(this.mMediaSizes.size() > this.mDefaults[0]);
        com.android.internal.util.Preconditions.checkArgument(this.mResolutions.size() > this.mDefaults[1]);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeMargins(this.mMinMargins, parcel);
        writeMediaSizes(parcel);
        writeResolutions(parcel);
        parcel.writeInt(this.mColorModes);
        parcel.writeInt(this.mDuplexModes);
        writeDefaults(parcel);
    }

    public int hashCode() {
        return (((((((((((this.mMinMargins == null ? 0 : this.mMinMargins.hashCode()) + 31) * 31) + (this.mMediaSizes == null ? 0 : this.mMediaSizes.hashCode())) * 31) + (this.mResolutions != null ? this.mResolutions.hashCode() : 0)) * 31) + this.mColorModes) * 31) + this.mDuplexModes) * 31) + java.util.Arrays.hashCode(this.mDefaults);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.print.PrinterCapabilitiesInfo printerCapabilitiesInfo = (android.print.PrinterCapabilitiesInfo) obj;
        if (this.mMinMargins == null) {
            if (printerCapabilitiesInfo.mMinMargins != null) {
                return false;
            }
        } else if (!this.mMinMargins.equals(printerCapabilitiesInfo.mMinMargins)) {
            return false;
        }
        if (this.mMediaSizes == null) {
            if (printerCapabilitiesInfo.mMediaSizes != null) {
                return false;
            }
        } else if (!this.mMediaSizes.equals(printerCapabilitiesInfo.mMediaSizes)) {
            return false;
        }
        if (this.mResolutions == null) {
            if (printerCapabilitiesInfo.mResolutions != null) {
                return false;
            }
        } else if (!this.mResolutions.equals(printerCapabilitiesInfo.mResolutions)) {
            return false;
        }
        if (this.mColorModes == printerCapabilitiesInfo.mColorModes && this.mDuplexModes == printerCapabilitiesInfo.mDuplexModes && java.util.Arrays.equals(this.mDefaults, printerCapabilitiesInfo.mDefaults)) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("PrinterInfo{");
        sb.append("minMargins=").append(this.mMinMargins);
        sb.append(", mediaSizes=").append(this.mMediaSizes);
        sb.append(", resolutions=").append(this.mResolutions);
        sb.append(", colorModes=").append(colorModesToString());
        sb.append(", duplexModes=").append(duplexModesToString());
        sb.append("\"}");
        return sb.toString();
    }

    private java.lang.String colorModesToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append('[');
        int i = this.mColorModes;
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(android.print.PrintAttributes.colorModeToString(numberOfTrailingZeros));
        }
        sb.append(']');
        return sb.toString();
    }

    private java.lang.String duplexModesToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append('[');
        int i = this.mDuplexModes;
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(android.print.PrintAttributes.duplexModeToString(numberOfTrailingZeros));
        }
        sb.append(']');
        return sb.toString();
    }

    private void writeMediaSizes(android.os.Parcel parcel) {
        if (this.mMediaSizes == null) {
            parcel.writeInt(0);
            return;
        }
        int size = this.mMediaSizes.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            this.mMediaSizes.get(i).writeToParcel(parcel);
        }
    }

    private void readMediaSizes(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt > 0 && this.mMediaSizes == null) {
            this.mMediaSizes = new java.util.ArrayList();
        }
        for (int i = 0; i < readInt; i++) {
            this.mMediaSizes.add(android.print.PrintAttributes.MediaSize.createFromParcel(parcel));
        }
    }

    private void writeResolutions(android.os.Parcel parcel) {
        if (this.mResolutions == null) {
            parcel.writeInt(0);
            return;
        }
        int size = this.mResolutions.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            this.mResolutions.get(i).writeToParcel(parcel);
        }
    }

    private void readResolutions(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt > 0 && this.mResolutions == null) {
            this.mResolutions = new java.util.ArrayList();
        }
        for (int i = 0; i < readInt; i++) {
            this.mResolutions.add(android.print.PrintAttributes.Resolution.createFromParcel(parcel));
        }
    }

    private void writeMargins(android.print.PrintAttributes.Margins margins, android.os.Parcel parcel) {
        if (margins == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            margins.writeToParcel(parcel);
        }
    }

    private android.print.PrintAttributes.Margins readMargins(android.os.Parcel parcel) {
        if (parcel.readInt() == 1) {
            return android.print.PrintAttributes.Margins.createFromParcel(parcel);
        }
        return null;
    }

    private void readDefaults(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mDefaults[i] = parcel.readInt();
        }
    }

    private void writeDefaults(android.os.Parcel parcel) {
        int length = this.mDefaults.length;
        parcel.writeInt(length);
        for (int i = 0; i < length; i++) {
            parcel.writeInt(this.mDefaults[i]);
        }
    }

    public static final class Builder {
        private final android.print.PrinterCapabilitiesInfo mPrototype;

        public Builder(android.print.PrinterId printerId) {
            if (printerId == null) {
                throw new java.lang.IllegalArgumentException("printerId cannot be null.");
            }
            this.mPrototype = new android.print.PrinterCapabilitiesInfo();
        }

        public android.print.PrinterCapabilitiesInfo.Builder addMediaSize(android.print.PrintAttributes.MediaSize mediaSize, boolean z) {
            if (this.mPrototype.mMediaSizes == null) {
                this.mPrototype.mMediaSizes = new java.util.ArrayList();
            }
            int size = this.mPrototype.mMediaSizes.size();
            this.mPrototype.mMediaSizes.add(mediaSize);
            if (z) {
                throwIfDefaultAlreadySpecified(0);
                this.mPrototype.mDefaults[0] = size;
            }
            return this;
        }

        public android.print.PrinterCapabilitiesInfo.Builder addResolution(android.print.PrintAttributes.Resolution resolution, boolean z) {
            if (this.mPrototype.mResolutions == null) {
                this.mPrototype.mResolutions = new java.util.ArrayList();
            }
            int size = this.mPrototype.mResolutions.size();
            this.mPrototype.mResolutions.add(resolution);
            if (z) {
                throwIfDefaultAlreadySpecified(1);
                this.mPrototype.mDefaults[1] = size;
            }
            return this;
        }

        public android.print.PrinterCapabilitiesInfo.Builder setMinMargins(android.print.PrintAttributes.Margins margins) {
            if (margins == null) {
                throw new java.lang.IllegalArgumentException("margins cannot be null");
            }
            this.mPrototype.mMinMargins = margins;
            return this;
        }

        public android.print.PrinterCapabilitiesInfo.Builder setColorModes(int i, int i2) {
            android.print.PrinterCapabilitiesInfo.enforceValidMask(i, new java.util.function.IntConsumer() { // from class: android.print.PrinterCapabilitiesInfo$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.IntConsumer
                public final void accept(int i3) {
                    android.print.PrintAttributes.enforceValidColorMode(i3);
                }
            });
            android.print.PrintAttributes.enforceValidColorMode(i2);
            this.mPrototype.mColorModes = i;
            this.mPrototype.mDefaults[2] = i2;
            return this;
        }

        public android.print.PrinterCapabilitiesInfo.Builder setDuplexModes(int i, int i2) {
            android.print.PrinterCapabilitiesInfo.enforceValidMask(i, new java.util.function.IntConsumer() { // from class: android.print.PrinterCapabilitiesInfo$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.IntConsumer
                public final void accept(int i3) {
                    android.print.PrintAttributes.enforceValidDuplexMode(i3);
                }
            });
            android.print.PrintAttributes.enforceValidDuplexMode(i2);
            this.mPrototype.mDuplexModes = i;
            this.mPrototype.mDefaults[3] = i2;
            return this;
        }

        public android.print.PrinterCapabilitiesInfo build() {
            if (this.mPrototype.mMediaSizes == null || this.mPrototype.mMediaSizes.isEmpty()) {
                throw new java.lang.IllegalStateException("No media size specified.");
            }
            if (this.mPrototype.mDefaults[0] == -1) {
                throw new java.lang.IllegalStateException("No default media size specified.");
            }
            if (this.mPrototype.mResolutions == null || this.mPrototype.mResolutions.isEmpty()) {
                throw new java.lang.IllegalStateException("No resolution specified.");
            }
            if (this.mPrototype.mDefaults[1] == -1) {
                throw new java.lang.IllegalStateException("No default resolution specified.");
            }
            if (this.mPrototype.mColorModes == 0) {
                throw new java.lang.IllegalStateException("No color mode specified.");
            }
            if (this.mPrototype.mDefaults[2] == -1) {
                throw new java.lang.IllegalStateException("No default color mode specified.");
            }
            if (this.mPrototype.mDuplexModes == 0) {
                setDuplexModes(1, 1);
            }
            if (this.mPrototype.mMinMargins == null) {
                throw new java.lang.IllegalArgumentException("margins cannot be null");
            }
            return this.mPrototype;
        }

        private void throwIfDefaultAlreadySpecified(int i) {
            if (this.mPrototype.mDefaults[i] != -1) {
                throw new java.lang.IllegalArgumentException("Default already specified.");
            }
        }
    }
}
