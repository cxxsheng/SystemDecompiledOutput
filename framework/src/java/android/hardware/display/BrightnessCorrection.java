package android.hardware.display;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class BrightnessCorrection implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.BrightnessCorrection> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.BrightnessCorrection>() { // from class: android.hardware.display.BrightnessCorrection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.BrightnessCorrection createFromParcel(android.os.Parcel parcel) {
            switch (parcel.readInt()) {
                case 1:
                    return android.hardware.display.BrightnessCorrection.ScaleAndTranslateLog.readFromParcel(parcel);
                default:
                    return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.BrightnessCorrection[] newArray(int i) {
            return new android.hardware.display.BrightnessCorrection[i];
        }
    };
    private static final int SCALE_AND_TRANSLATE_LOG = 1;
    private static final java.lang.String TAG_SCALE_AND_TRANSLATE_LOG = "scale-and-translate-log";
    private android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation mImplementation;

    private interface BrightnessCorrectionImplementation {
        float apply(float f);

        void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException;

        java.lang.String toString();

        void writeToParcel(android.os.Parcel parcel);
    }

    private BrightnessCorrection(android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation brightnessCorrectionImplementation) {
        this.mImplementation = brightnessCorrectionImplementation;
    }

    public static android.hardware.display.BrightnessCorrection createScaleAndTranslateLog(float f, float f2) {
        return new android.hardware.display.BrightnessCorrection(new android.hardware.display.BrightnessCorrection.ScaleAndTranslateLog(f, f2));
    }

    public float apply(float f) {
        return this.mImplementation.apply(f);
    }

    public java.lang.String toString() {
        return this.mImplementation.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.display.BrightnessCorrection)) {
            return false;
        }
        return ((android.hardware.display.BrightnessCorrection) obj).mImplementation.equals(this.mImplementation);
    }

    public int hashCode() {
        return this.mImplementation.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mImplementation.writeToParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        this.mImplementation.saveToXml(typedXmlSerializer);
    }

    public static android.hardware.display.BrightnessCorrection loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (TAG_SCALE_AND_TRANSLATE_LOG.equals(typedXmlPullParser.getName())) {
                return android.hardware.display.BrightnessCorrection.ScaleAndTranslateLog.loadFromXml(typedXmlPullParser);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float loadFloatFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        return typedXmlPullParser.getAttributeFloat(null, str, Float.NaN);
    }

    private static class ScaleAndTranslateLog implements android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation {
        private static final java.lang.String ATTR_SCALE = "scale";
        private static final java.lang.String ATTR_TRANSLATE = "translate";
        private static final float MAX_SCALE = 2.0f;
        private static final float MAX_TRANSLATE = 0.7f;
        private static final float MIN_SCALE = 0.5f;
        private static final float MIN_TRANSLATE = -0.6f;
        private final float mScale;
        private final float mTranslate;

        ScaleAndTranslateLog(float f, float f2) {
            if (java.lang.Float.isNaN(f) || java.lang.Float.isNaN(f2)) {
                throw new java.lang.IllegalArgumentException("scale and translate must be numbers");
            }
            this.mScale = android.util.MathUtils.constrain(f, 0.5f, MAX_SCALE);
            this.mTranslate = android.util.MathUtils.constrain(f2, MIN_TRANSLATE, MAX_TRANSLATE);
        }

        @Override // android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation
        public float apply(float f) {
            return android.util.MathUtils.exp((this.mScale * android.util.MathUtils.log(f)) + this.mTranslate);
        }

        @Override // android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation
        public java.lang.String toString() {
            return "ScaleAndTranslateLog(" + this.mScale + ", " + this.mTranslate + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.hardware.display.BrightnessCorrection.ScaleAndTranslateLog)) {
                return false;
            }
            android.hardware.display.BrightnessCorrection.ScaleAndTranslateLog scaleAndTranslateLog = (android.hardware.display.BrightnessCorrection.ScaleAndTranslateLog) obj;
            return scaleAndTranslateLog.mScale == this.mScale && scaleAndTranslateLog.mTranslate == this.mTranslate;
        }

        public int hashCode() {
            return ((java.lang.Float.hashCode(this.mScale) + 31) * 31) + java.lang.Float.hashCode(this.mTranslate);
        }

        @Override // android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation
        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeInt(1);
            parcel.writeFloat(this.mScale);
            parcel.writeFloat(this.mTranslate);
        }

        @Override // android.hardware.display.BrightnessCorrection.BrightnessCorrectionImplementation
        public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag(null, android.hardware.display.BrightnessCorrection.TAG_SCALE_AND_TRANSLATE_LOG);
            typedXmlSerializer.attributeFloat(null, "scale", this.mScale);
            typedXmlSerializer.attributeFloat(null, ATTR_TRANSLATE, this.mTranslate);
            typedXmlSerializer.endTag(null, android.hardware.display.BrightnessCorrection.TAG_SCALE_AND_TRANSLATE_LOG);
        }

        static android.hardware.display.BrightnessCorrection readFromParcel(android.os.Parcel parcel) {
            return android.hardware.display.BrightnessCorrection.createScaleAndTranslateLog(parcel.readFloat(), parcel.readFloat());
        }

        static android.hardware.display.BrightnessCorrection loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            return android.hardware.display.BrightnessCorrection.createScaleAndTranslateLog(android.hardware.display.BrightnessCorrection.loadFloatFromXml(typedXmlPullParser, "scale"), android.hardware.display.BrightnessCorrection.loadFloatFromXml(typedXmlPullParser, ATTR_TRANSLATE));
        }
    }
}
