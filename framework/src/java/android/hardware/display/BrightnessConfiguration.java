package android.hardware.display;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class BrightnessConfiguration implements android.os.Parcelable {
    private static final java.lang.String ATTR_CATEGORY = "category";
    private static final java.lang.String ATTR_COLLECT_COLOR = "collect-color";
    private static final java.lang.String ATTR_DESCRIPTION = "description";
    private static final java.lang.String ATTR_LUX = "lux";
    private static final java.lang.String ATTR_MODEL_LOWER_BOUND = "model-lower-bound";
    private static final java.lang.String ATTR_MODEL_TIMEOUT = "model-timeout";
    private static final java.lang.String ATTR_MODEL_UPPER_BOUND = "model-upper-bound";
    private static final java.lang.String ATTR_NITS = "nits";
    private static final java.lang.String ATTR_PACKAGE_NAME = "package-name";
    public static final android.os.Parcelable.Creator<android.hardware.display.BrightnessConfiguration> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.BrightnessConfiguration>() { // from class: android.hardware.display.BrightnessConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.BrightnessConfiguration createFromParcel(android.os.Parcel parcel) {
            android.hardware.display.BrightnessConfiguration.Builder builder = new android.hardware.display.BrightnessConfiguration.Builder(parcel.createFloatArray(), parcel.createFloatArray());
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                builder.addCorrectionByPackageName(parcel.readString(), android.hardware.display.BrightnessCorrection.CREATOR.createFromParcel(parcel));
            }
            int readInt2 = parcel.readInt();
            for (int i2 = 0; i2 < readInt2; i2++) {
                builder.addCorrectionByCategory(parcel.readInt(), android.hardware.display.BrightnessCorrection.CREATOR.createFromParcel(parcel));
            }
            builder.setDescription(parcel.readString());
            builder.setShouldCollectColorSamples(parcel.readBoolean());
            builder.setShortTermModelTimeoutMillis(parcel.readLong());
            builder.setShortTermModelLowerLuxMultiplier(parcel.readFloat());
            builder.setShortTermModelUpperLuxMultiplier(parcel.readFloat());
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.BrightnessConfiguration[] newArray(int i) {
            return new android.hardware.display.BrightnessConfiguration[i];
        }
    };
    public static final long SHORT_TERM_TIMEOUT_UNSET = -1;
    private static final java.lang.String TAG_BRIGHTNESS_CORRECTION = "brightness-correction";
    private static final java.lang.String TAG_BRIGHTNESS_CORRECTIONS = "brightness-corrections";
    private static final java.lang.String TAG_BRIGHTNESS_CURVE = "brightness-curve";
    private static final java.lang.String TAG_BRIGHTNESS_PARAMS = "brightness-params";
    private static final java.lang.String TAG_BRIGHTNESS_POINT = "brightness-point";
    private final java.util.Map<java.lang.Integer, android.hardware.display.BrightnessCorrection> mCorrectionsByCategory;
    private final java.util.Map<java.lang.String, android.hardware.display.BrightnessCorrection> mCorrectionsByPackageName;
    private final java.lang.String mDescription;
    private final float[] mLux;
    private final float[] mNits;
    private final float mShortTermModelLowerLuxMultiplier;
    private final long mShortTermModelTimeout;
    private final float mShortTermModelUpperLuxMultiplier;
    private final boolean mShouldCollectColorSamples;

    private BrightnessConfiguration(float[] fArr, float[] fArr2, java.util.Map<java.lang.String, android.hardware.display.BrightnessCorrection> map, java.util.Map<java.lang.Integer, android.hardware.display.BrightnessCorrection> map2, java.lang.String str, boolean z, long j, float f, float f2) {
        this.mLux = fArr;
        this.mNits = fArr2;
        this.mCorrectionsByPackageName = map;
        this.mCorrectionsByCategory = map2;
        this.mDescription = str;
        this.mShouldCollectColorSamples = z;
        this.mShortTermModelTimeout = j;
        this.mShortTermModelLowerLuxMultiplier = f;
        this.mShortTermModelUpperLuxMultiplier = f2;
    }

    public android.util.Pair<float[], float[]> getCurve() {
        return android.util.Pair.create(java.util.Arrays.copyOf(this.mLux, this.mLux.length), java.util.Arrays.copyOf(this.mNits, this.mNits.length));
    }

    public android.hardware.display.BrightnessCorrection getCorrectionByPackageName(java.lang.String str) {
        return this.mCorrectionsByPackageName.get(str);
    }

    public android.hardware.display.BrightnessCorrection getCorrectionByCategory(int i) {
        return this.mCorrectionsByCategory.get(java.lang.Integer.valueOf(i));
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public boolean shouldCollectColorSamples() {
        return this.mShouldCollectColorSamples;
    }

    public long getShortTermModelTimeoutMillis() {
        return this.mShortTermModelTimeout;
    }

    public float getShortTermModelUpperLuxMultiplier() {
        return this.mShortTermModelUpperLuxMultiplier;
    }

    public float getShortTermModelLowerLuxMultiplier() {
        return this.mShortTermModelLowerLuxMultiplier;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloatArray(this.mLux);
        parcel.writeFloatArray(this.mNits);
        parcel.writeInt(this.mCorrectionsByPackageName.size());
        for (java.util.Map.Entry<java.lang.String, android.hardware.display.BrightnessCorrection> entry : this.mCorrectionsByPackageName.entrySet()) {
            java.lang.String key = entry.getKey();
            android.hardware.display.BrightnessCorrection value = entry.getValue();
            parcel.writeString(key);
            value.writeToParcel(parcel, i);
        }
        parcel.writeInt(this.mCorrectionsByCategory.size());
        for (java.util.Map.Entry<java.lang.Integer, android.hardware.display.BrightnessCorrection> entry2 : this.mCorrectionsByCategory.entrySet()) {
            int intValue = entry2.getKey().intValue();
            android.hardware.display.BrightnessCorrection value2 = entry2.getValue();
            parcel.writeInt(intValue);
            value2.writeToParcel(parcel, i);
        }
        parcel.writeString(this.mDescription);
        parcel.writeBoolean(this.mShouldCollectColorSamples);
        parcel.writeLong(this.mShortTermModelTimeout);
        parcel.writeFloat(this.mShortTermModelLowerLuxMultiplier);
        parcel.writeFloat(this.mShortTermModelUpperLuxMultiplier);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("BrightnessConfiguration{[");
        int length = this.mLux.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START).append(this.mLux[i]).append(", ").append(this.mNits[i]).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        sb.append("], {");
        for (java.util.Map.Entry<java.lang.String, android.hardware.display.BrightnessCorrection> entry : this.mCorrectionsByPackageName.entrySet()) {
            sb.append("'" + entry.getKey() + "': " + entry.getValue() + ", ");
        }
        for (java.util.Map.Entry<java.lang.Integer, android.hardware.display.BrightnessCorrection> entry2 : this.mCorrectionsByCategory.entrySet()) {
            sb.append(entry2.getKey() + ": " + entry2.getValue() + ", ");
        }
        sb.append("}, '");
        if (this.mDescription != null) {
            sb.append(this.mDescription);
        }
        sb.append(", shouldCollectColorSamples = " + this.mShouldCollectColorSamples);
        if (this.mShortTermModelTimeout >= 0) {
            sb.append(", shortTermModelTimeout = " + this.mShortTermModelTimeout);
        }
        if (!java.lang.Float.isNaN(this.mShortTermModelLowerLuxMultiplier)) {
            sb.append(", shortTermModelLowerLuxMultiplier = " + this.mShortTermModelLowerLuxMultiplier);
        }
        if (!java.lang.Float.isNaN(this.mShortTermModelLowerLuxMultiplier)) {
            sb.append(", shortTermModelUpperLuxMultiplier = " + this.mShortTermModelUpperLuxMultiplier);
        }
        sb.append("'}");
        return sb.toString();
    }

    public int hashCode() {
        int hashCode = ((((((java.util.Arrays.hashCode(this.mLux) + 31) * 31) + java.util.Arrays.hashCode(this.mNits)) * 31) + this.mCorrectionsByPackageName.hashCode()) * 31) + this.mCorrectionsByCategory.hashCode();
        if (this.mDescription != null) {
            hashCode = (hashCode * 31) + this.mDescription.hashCode();
        }
        return (((((((hashCode * 31) + java.lang.Boolean.hashCode(this.mShouldCollectColorSamples)) * 31) + java.lang.Long.hashCode(this.mShortTermModelTimeout)) * 31) + java.lang.Float.hashCode(this.mShortTermModelLowerLuxMultiplier)) * 31) + java.lang.Float.hashCode(this.mShortTermModelUpperLuxMultiplier);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.display.BrightnessConfiguration)) {
            return false;
        }
        android.hardware.display.BrightnessConfiguration brightnessConfiguration = (android.hardware.display.BrightnessConfiguration) obj;
        return java.util.Arrays.equals(this.mLux, brightnessConfiguration.mLux) && java.util.Arrays.equals(this.mNits, brightnessConfiguration.mNits) && this.mCorrectionsByPackageName.equals(brightnessConfiguration.mCorrectionsByPackageName) && this.mCorrectionsByCategory.equals(brightnessConfiguration.mCorrectionsByCategory) && java.util.Objects.equals(this.mDescription, brightnessConfiguration.mDescription) && this.mShouldCollectColorSamples == brightnessConfiguration.mShouldCollectColorSamples && this.mShortTermModelTimeout == brightnessConfiguration.mShortTermModelTimeout && checkFloatEquals(this.mShortTermModelLowerLuxMultiplier, brightnessConfiguration.mShortTermModelLowerLuxMultiplier) && checkFloatEquals(this.mShortTermModelUpperLuxMultiplier, brightnessConfiguration.mShortTermModelUpperLuxMultiplier);
    }

    private boolean checkFloatEquals(float f, float f2) {
        return (java.lang.Float.isNaN(f) && java.lang.Float.isNaN(f2)) || f == f2;
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_CURVE);
        if (this.mDescription != null) {
            typedXmlSerializer.attribute(null, "description", this.mDescription);
        }
        for (int i = 0; i < this.mLux.length; i++) {
            typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_POINT);
            typedXmlSerializer.attributeFloat(null, ATTR_LUX, this.mLux[i]);
            typedXmlSerializer.attributeFloat(null, ATTR_NITS, this.mNits[i]);
            typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_POINT);
        }
        typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_CURVE);
        typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTIONS);
        for (java.util.Map.Entry<java.lang.String, android.hardware.display.BrightnessCorrection> entry : this.mCorrectionsByPackageName.entrySet()) {
            java.lang.String key = entry.getKey();
            android.hardware.display.BrightnessCorrection value = entry.getValue();
            typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTION);
            typedXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, key);
            value.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTION);
        }
        for (java.util.Map.Entry<java.lang.Integer, android.hardware.display.BrightnessCorrection> entry2 : this.mCorrectionsByCategory.entrySet()) {
            int intValue = entry2.getKey().intValue();
            android.hardware.display.BrightnessCorrection value2 = entry2.getValue();
            typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_CORRECTION);
            typedXmlSerializer.attributeInt(null, ATTR_CATEGORY, intValue);
            value2.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTION);
        }
        typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_CORRECTIONS);
        typedXmlSerializer.startTag(null, TAG_BRIGHTNESS_PARAMS);
        if (this.mShouldCollectColorSamples) {
            typedXmlSerializer.attributeBoolean(null, ATTR_COLLECT_COLOR, true);
        }
        if (this.mShortTermModelTimeout >= 0) {
            typedXmlSerializer.attributeLong(null, ATTR_MODEL_TIMEOUT, this.mShortTermModelTimeout);
        }
        if (!java.lang.Float.isNaN(this.mShortTermModelLowerLuxMultiplier)) {
            typedXmlSerializer.attributeFloat(null, ATTR_MODEL_LOWER_BOUND, this.mShortTermModelLowerLuxMultiplier);
        }
        if (!java.lang.Float.isNaN(this.mShortTermModelUpperLuxMultiplier)) {
            typedXmlSerializer.attributeFloat(null, ATTR_MODEL_UPPER_BOUND, this.mShortTermModelUpperLuxMultiplier);
        }
        typedXmlSerializer.endTag(null, TAG_BRIGHTNESS_PARAMS);
    }

    public static android.hardware.display.BrightnessConfiguration loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int i;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.HashMap hashMap2 = new java.util.HashMap();
        int depth = typedXmlPullParser.getDepth();
        java.lang.String str = null;
        long j = -1;
        float f = Float.NaN;
        java.lang.String str2 = null;
        float f2 = Float.NaN;
        boolean z = false;
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (TAG_BRIGHTNESS_CURVE.equals(typedXmlPullParser.getName())) {
                str2 = typedXmlPullParser.getAttributeValue(str, "description");
                int depth2 = typedXmlPullParser.getDepth();
                while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                    if (TAG_BRIGHTNESS_POINT.equals(typedXmlPullParser.getName())) {
                        float loadFloatFromXml = loadFloatFromXml(typedXmlPullParser, ATTR_LUX);
                        float loadFloatFromXml2 = loadFloatFromXml(typedXmlPullParser, ATTR_NITS);
                        arrayList.add(java.lang.Float.valueOf(loadFloatFromXml));
                        arrayList2.add(java.lang.Float.valueOf(loadFloatFromXml2));
                    }
                }
            } else {
                if (TAG_BRIGHTNESS_CORRECTIONS.equals(typedXmlPullParser.getName())) {
                    int depth3 = typedXmlPullParser.getDepth();
                    while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                        if (TAG_BRIGHTNESS_CORRECTION.equals(typedXmlPullParser.getName())) {
                            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(str, ATTR_PACKAGE_NAME);
                            int i2 = depth;
                            int attributeInt = typedXmlPullParser.getAttributeInt(str, ATTR_CATEGORY, -1);
                            android.hardware.display.BrightnessCorrection loadFromXml = android.hardware.display.BrightnessCorrection.loadFromXml(typedXmlPullParser);
                            if (attributeValue != null) {
                                hashMap.put(attributeValue, loadFromXml);
                            } else if (attributeInt != -1) {
                                hashMap2.put(java.lang.Integer.valueOf(attributeInt), loadFromXml);
                            }
                            depth = i2;
                            str = null;
                        }
                    }
                    i = depth;
                } else {
                    i = depth;
                    if (TAG_BRIGHTNESS_PARAMS.equals(typedXmlPullParser.getName())) {
                        str = null;
                        z = typedXmlPullParser.getAttributeBoolean(null, ATTR_COLLECT_COLOR, false);
                        java.lang.Long loadLongFromXml = loadLongFromXml(typedXmlPullParser, ATTR_MODEL_TIMEOUT);
                        if (loadLongFromXml != null) {
                            j = loadLongFromXml.longValue();
                        }
                        f = loadFloatFromXml(typedXmlPullParser, ATTR_MODEL_LOWER_BOUND);
                        f2 = loadFloatFromXml(typedXmlPullParser, ATTR_MODEL_UPPER_BOUND);
                        depth = i;
                    }
                }
                str = null;
                depth = i;
            }
        }
        int size = arrayList.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        for (int i3 = 0; i3 < size; i3++) {
            fArr[i3] = ((java.lang.Float) arrayList.get(i3)).floatValue();
            fArr2[i3] = ((java.lang.Float) arrayList2.get(i3)).floatValue();
        }
        android.hardware.display.BrightnessConfiguration.Builder builder = new android.hardware.display.BrightnessConfiguration.Builder(fArr, fArr2);
        builder.setDescription(str2);
        for (java.util.Map.Entry entry : hashMap.entrySet()) {
            builder.addCorrectionByPackageName((java.lang.String) entry.getKey(), (android.hardware.display.BrightnessCorrection) entry.getValue());
        }
        for (java.util.Map.Entry entry2 : hashMap2.entrySet()) {
            builder.addCorrectionByCategory(((java.lang.Integer) entry2.getKey()).intValue(), (android.hardware.display.BrightnessCorrection) entry2.getValue());
        }
        builder.setShouldCollectColorSamples(z);
        builder.setShortTermModelTimeoutMillis(j);
        builder.setShortTermModelLowerLuxMultiplier(f);
        builder.setShortTermModelUpperLuxMultiplier(f2);
        return builder.build();
    }

    private static float loadFloatFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        return typedXmlPullParser.getAttributeFloat(null, str, Float.NaN);
    }

    private static java.lang.Long loadLongFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        try {
            return java.lang.Long.valueOf(typedXmlPullParser.getAttributeLong(null, str));
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public static class Builder {
        private static final int MAX_CORRECTIONS_BY_CATEGORY = 20;
        private static final int MAX_CORRECTIONS_BY_PACKAGE_NAME = 20;
        private java.util.Map<java.lang.Integer, android.hardware.display.BrightnessCorrection> mCorrectionsByCategory;
        private java.util.Map<java.lang.String, android.hardware.display.BrightnessCorrection> mCorrectionsByPackageName;
        private float[] mCurveLux;
        private float[] mCurveNits;
        private java.lang.String mDescription;
        private boolean mShouldCollectColorSamples;
        private long mShortTermModelTimeout = -1;
        private float mShortTermModelLowerLuxMultiplier = Float.NaN;
        private float mShortTermModelUpperLuxMultiplier = Float.NaN;

        public Builder(float[] fArr, float[] fArr2) {
            java.util.Objects.requireNonNull(fArr);
            java.util.Objects.requireNonNull(fArr2);
            if (fArr.length == 0 || fArr2.length == 0) {
                throw new java.lang.IllegalArgumentException("Lux and nits arrays must not be empty");
            }
            if (fArr.length != fArr2.length) {
                throw new java.lang.IllegalArgumentException("Lux and nits arrays must be the same length");
            }
            if (fArr[0] != 0.0f) {
                throw new java.lang.IllegalArgumentException("Initial control point must be for 0 lux");
            }
            com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr, 0.0f, Float.MAX_VALUE, android.hardware.display.BrightnessConfiguration.ATTR_LUX);
            com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr2, 0.0f, Float.MAX_VALUE, android.hardware.display.BrightnessConfiguration.ATTR_NITS);
            checkMonotonic(fArr, true, android.hardware.display.BrightnessConfiguration.ATTR_LUX);
            checkMonotonic(fArr2, false, android.hardware.display.BrightnessConfiguration.ATTR_NITS);
            this.mCurveLux = fArr;
            this.mCurveNits = fArr2;
            this.mCorrectionsByPackageName = new java.util.HashMap();
            this.mCorrectionsByCategory = new java.util.HashMap();
        }

        public int getMaxCorrectionsByPackageName() {
            return 20;
        }

        public int getMaxCorrectionsByCategory() {
            return 20;
        }

        public android.hardware.display.BrightnessConfiguration.Builder addCorrectionByPackageName(java.lang.String str, android.hardware.display.BrightnessCorrection brightnessCorrection) {
            java.util.Objects.requireNonNull(str, "packageName must not be null");
            java.util.Objects.requireNonNull(brightnessCorrection, "correction must not be null");
            if (this.mCorrectionsByPackageName.size() >= getMaxCorrectionsByPackageName()) {
                throw new java.lang.IllegalArgumentException("Too many corrections by package name");
            }
            this.mCorrectionsByPackageName.put(str, brightnessCorrection);
            return this;
        }

        public android.hardware.display.BrightnessConfiguration.Builder addCorrectionByCategory(int i, android.hardware.display.BrightnessCorrection brightnessCorrection) {
            java.util.Objects.requireNonNull(brightnessCorrection, "correction must not be null");
            if (this.mCorrectionsByCategory.size() >= getMaxCorrectionsByCategory()) {
                throw new java.lang.IllegalArgumentException("Too many corrections by category");
            }
            this.mCorrectionsByCategory.put(java.lang.Integer.valueOf(i), brightnessCorrection);
            return this;
        }

        public android.hardware.display.BrightnessConfiguration.Builder setDescription(java.lang.String str) {
            this.mDescription = str;
            return this;
        }

        public android.hardware.display.BrightnessConfiguration.Builder setShouldCollectColorSamples(boolean z) {
            this.mShouldCollectColorSamples = z;
            return this;
        }

        public android.hardware.display.BrightnessConfiguration.Builder setShortTermModelTimeoutMillis(long j) {
            this.mShortTermModelTimeout = j;
            return this;
        }

        public android.hardware.display.BrightnessConfiguration.Builder setShortTermModelUpperLuxMultiplier(float f) {
            if (f < 0.0f) {
                throw new java.lang.IllegalArgumentException("Negative lux multiplier");
            }
            this.mShortTermModelUpperLuxMultiplier = f;
            return this;
        }

        public android.hardware.display.BrightnessConfiguration.Builder setShortTermModelLowerLuxMultiplier(float f) {
            if (f < 0.0f) {
                throw new java.lang.IllegalArgumentException("Negative lux multiplier");
            }
            this.mShortTermModelLowerLuxMultiplier = f;
            return this;
        }

        public android.hardware.display.BrightnessConfiguration build() {
            if (this.mCurveLux == null || this.mCurveNits == null) {
                throw new java.lang.IllegalStateException("A curve must be set!");
            }
            return new android.hardware.display.BrightnessConfiguration(this.mCurveLux, this.mCurveNits, this.mCorrectionsByPackageName, this.mCorrectionsByCategory, this.mDescription, this.mShouldCollectColorSamples, this.mShortTermModelTimeout, this.mShortTermModelLowerLuxMultiplier, this.mShortTermModelUpperLuxMultiplier);
        }

        private static void checkMonotonic(float[] fArr, boolean z, java.lang.String str) {
            if (fArr.length <= 1) {
                return;
            }
            float f = fArr[0];
            for (int i = 1; i < fArr.length; i++) {
                if (f > fArr[i] || (f == fArr[i] && z)) {
                    throw new java.lang.IllegalArgumentException(str + " values must be " + (z ? "strictly increasing" : "monotonic"));
                }
                f = fArr[i];
            }
        }
    }
}
