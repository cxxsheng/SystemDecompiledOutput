package android.graphics;

/* loaded from: classes.dex */
public abstract class ColorSpace {
    public static final int MAX_ID = 63;
    public static final int MIN_ID = -1;
    private final int mId;
    private final android.graphics.ColorSpace.Model mModel;
    private final java.lang.String mName;
    public static final float[] ILLUMINANT_A = {0.44757f, 0.40745f};
    public static final float[] ILLUMINANT_B = {0.34842f, 0.35161f};
    public static final float[] ILLUMINANT_C = {0.31006f, 0.31616f};
    public static final float[] ILLUMINANT_D50 = {0.34567f, 0.3585f};
    public static final float[] ILLUMINANT_D55 = {0.33242f, 0.34743f};
    public static final float[] ILLUMINANT_D60 = {0.32168f, 0.33767f};
    public static final float[] ILLUMINANT_D65 = {0.31271f, 0.32902f};
    public static final float[] ILLUMINANT_D75 = {0.29902f, 0.31485f};
    public static final float[] ILLUMINANT_E = {0.33333f, 0.33333f};
    private static final float[] SRGB_PRIMARIES = {0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f};
    private static final float[] NTSC_1953_PRIMARIES = {0.67f, 0.33f, 0.21f, 0.71f, 0.14f, 0.08f};
    private static final float[] DCI_P3_PRIMARIES = {0.68f, 0.32f, 0.265f, 0.69f, 0.15f, 0.06f};
    private static final float[] BT2020_PRIMARIES = {0.708f, 0.292f, 0.17f, 0.797f, 0.131f, 0.046f};
    private static final float[] GRAY_PRIMARIES = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] ILLUMINANT_D50_XYZ = {0.964212f, 1.0f, 0.825188f};
    private static final android.graphics.ColorSpace.Rgb.TransferParameters SRGB_TRANSFER_PARAMETERS = new android.graphics.ColorSpace.Rgb.TransferParameters(0.9478672985781991d, 0.05213270142180095d, 0.07739938080495357d, 0.04045d, 2.4d);
    private static final android.graphics.ColorSpace.Rgb.TransferParameters SMPTE_170M_TRANSFER_PARAMETERS = new android.graphics.ColorSpace.Rgb.TransferParameters(0.9099181073703367d, 0.09008189262966333d, 0.2222222222222222d, 0.081d, 2.2222222222222223d);
    private static final android.graphics.ColorSpace.Rgb.TransferParameters BT2020_HLG_TRANSFER_PARAMETERS = new android.graphics.ColorSpace.Rgb.TransferParameters(2.0d, 2.0d, 5.591816309728916d, 0.28466892d, 0.55991073d, -0.685490157d, -3.0d);
    private static final android.graphics.ColorSpace.Rgb.TransferParameters BT2020_PQ_TRANSFER_PARAMETERS = new android.graphics.ColorSpace.Rgb.TransferParameters(-1.555223d, 1.860454d, 0.012683313515655966d, 18.8515625d, -18.6875d, 6.277394636015326d, -2.0d);
    private static final android.graphics.ColorSpace[] sNamedColorSpaces = new android.graphics.ColorSpace[android.graphics.ColorSpace.Named.values().length];
    private static final android.util.SparseIntArray sDataToColorSpaces = new android.util.SparseIntArray();

    public enum Named {
        SRGB,
        LINEAR_SRGB,
        EXTENDED_SRGB,
        LINEAR_EXTENDED_SRGB,
        BT709,
        BT2020,
        DCI_P3,
        DISPLAY_P3,
        NTSC_1953,
        SMPTE_C,
        ADOBE_RGB,
        PRO_PHOTO_RGB,
        ACES,
        ACESCG,
        CIE_XYZ,
        CIE_LAB,
        BT2020_HLG,
        BT2020_PQ
    }

    public enum RenderIntent {
        PERCEPTUAL,
        RELATIVE,
        SATURATION,
        ABSOLUTE
    }

    public abstract float[] fromXyz(float[] fArr);

    public abstract float getMaxValue(int i);

    public abstract float getMinValue(int i);

    public abstract boolean isWideGamut();

    public abstract float[] toXyz(float[] fArr);

    static {
        sNamedColorSpaces[android.graphics.ColorSpace.Named.SRGB.ordinal()] = new android.graphics.ColorSpace.Rgb("sRGB IEC61966-2.1", SRGB_PRIMARIES, ILLUMINANT_D65, (float[]) null, SRGB_TRANSFER_PARAMETERS, android.graphics.ColorSpace.Named.SRGB.ordinal());
        sDataToColorSpaces.put(142671872, android.graphics.ColorSpace.Named.SRGB.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.LINEAR_SRGB.ordinal()] = new android.graphics.ColorSpace.Rgb("sRGB IEC61966-2.1 (Linear)", SRGB_PRIMARIES, ILLUMINANT_D65, 1.0d, 0.0f, 1.0f, android.graphics.ColorSpace.Named.LINEAR_SRGB.ordinal());
        sDataToColorSpaces.put(138477568, android.graphics.ColorSpace.Named.LINEAR_SRGB.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.EXTENDED_SRGB.ordinal()] = new android.graphics.ColorSpace.Rgb("scRGB-nl IEC 61966-2-2:2003", SRGB_PRIMARIES, ILLUMINANT_D65, null, new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d) {
                double absRcpResponse;
                absRcpResponse = android.graphics.ColorSpace.absRcpResponse(d, 0.9478672985781991d, 0.05213270142180095d, 0.07739938080495357d, 0.04045d, 2.4d);
                return absRcpResponse;
            }
        }, new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda1
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d) {
                double absResponse;
                absResponse = android.graphics.ColorSpace.absResponse(d, 0.9478672985781991d, 0.05213270142180095d, 0.07739938080495357d, 0.04045d, 2.4d);
                return absResponse;
            }
        }, -0.799f, 2.399f, SRGB_TRANSFER_PARAMETERS, android.graphics.ColorSpace.Named.EXTENDED_SRGB.ordinal());
        sDataToColorSpaces.put(411107328, android.graphics.ColorSpace.Named.EXTENDED_SRGB.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.LINEAR_EXTENDED_SRGB.ordinal()] = new android.graphics.ColorSpace.Rgb("scRGB IEC 61966-2-2:2003", SRGB_PRIMARIES, ILLUMINANT_D65, 1.0d, -0.5f, 7.499f, android.graphics.ColorSpace.Named.LINEAR_EXTENDED_SRGB.ordinal());
        sDataToColorSpaces.put(406913024, android.graphics.ColorSpace.Named.LINEAR_EXTENDED_SRGB.ordinal());
        float[] fArr = null;
        sNamedColorSpaces[android.graphics.ColorSpace.Named.BT709.ordinal()] = new android.graphics.ColorSpace.Rgb("Rec. ITU-R BT.709-5", SRGB_PRIMARIES, ILLUMINANT_D65, fArr, SMPTE_170M_TRANSFER_PARAMETERS, android.graphics.ColorSpace.Named.BT709.ordinal());
        sDataToColorSpaces.put(281083904, android.graphics.ColorSpace.Named.BT709.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.BT2020.ordinal()] = new android.graphics.ColorSpace.Rgb("Rec. ITU-R BT.2020-1", BT2020_PRIMARIES, ILLUMINANT_D65, fArr, new android.graphics.ColorSpace.Rgb.TransferParameters(0.9096697898662786d, 0.09033021013372146d, 0.2222222222222222d, 0.08145d, 2.2222222222222223d), android.graphics.ColorSpace.Named.BT2020.ordinal());
        sDataToColorSpaces.put(147193856, android.graphics.ColorSpace.Named.BT2020.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.DCI_P3.ordinal()] = new android.graphics.ColorSpace.Rgb("SMPTE RP 431-2-2007 DCI (P3)", DCI_P3_PRIMARIES, new float[]{0.314f, 0.351f}, 2.6d, 0.0f, 1.0f, android.graphics.ColorSpace.Named.DCI_P3.ordinal());
        sDataToColorSpaces.put(155844608, android.graphics.ColorSpace.Named.DCI_P3.ordinal());
        float[] fArr2 = null;
        sNamedColorSpaces[android.graphics.ColorSpace.Named.DISPLAY_P3.ordinal()] = new android.graphics.ColorSpace.Rgb("Display P3", DCI_P3_PRIMARIES, ILLUMINANT_D65, fArr2, SRGB_TRANSFER_PARAMETERS, android.graphics.ColorSpace.Named.DISPLAY_P3.ordinal());
        sDataToColorSpaces.put(143261696, android.graphics.ColorSpace.Named.DISPLAY_P3.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.NTSC_1953.ordinal()] = new android.graphics.ColorSpace.Rgb("NTSC (1953)", NTSC_1953_PRIMARIES, ILLUMINANT_C, fArr2, SMPTE_170M_TRANSFER_PARAMETERS, android.graphics.ColorSpace.Named.NTSC_1953.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.SMPTE_C.ordinal()] = new android.graphics.ColorSpace.Rgb("SMPTE-C RGB", new float[]{0.63f, 0.34f, 0.31f, 0.595f, 0.155f, 0.07f}, ILLUMINANT_D65, fArr2, SMPTE_170M_TRANSFER_PARAMETERS, android.graphics.ColorSpace.Named.SMPTE_C.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.ADOBE_RGB.ordinal()] = new android.graphics.ColorSpace.Rgb("Adobe RGB (1998)", new float[]{0.64f, 0.33f, 0.21f, 0.71f, 0.15f, 0.06f}, ILLUMINANT_D65, 2.2d, 0.0f, 1.0f, android.graphics.ColorSpace.Named.ADOBE_RGB.ordinal());
        sDataToColorSpaces.put(151715840, android.graphics.ColorSpace.Named.ADOBE_RGB.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.PRO_PHOTO_RGB.ordinal()] = new android.graphics.ColorSpace.Rgb("ROMM RGB ISO 22028-2:2013", new float[]{0.7347f, 0.2653f, 0.1596f, 0.8404f, 0.0366f, 1.0E-4f}, ILLUMINANT_D50, (float[]) null, new android.graphics.ColorSpace.Rgb.TransferParameters(1.0d, 0.0d, 0.0625d, 0.031248d, 1.8d), android.graphics.ColorSpace.Named.PRO_PHOTO_RGB.ordinal());
        double d = 1.0d;
        float f = -65504.0f;
        float f2 = 65504.0f;
        sNamedColorSpaces[android.graphics.ColorSpace.Named.ACES.ordinal()] = new android.graphics.ColorSpace.Rgb("SMPTE ST 2065-1:2012 ACES", new float[]{0.7347f, 0.2653f, 0.0f, 1.0f, 1.0E-4f, -0.077f}, ILLUMINANT_D60, d, f, f2, android.graphics.ColorSpace.Named.ACES.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.ACESCG.ordinal()] = new android.graphics.ColorSpace.Rgb("Academy S-2014-004 ACEScg", new float[]{0.713f, 0.293f, 0.165f, 0.83f, 0.128f, 0.044f}, ILLUMINANT_D60, d, f, f2, android.graphics.ColorSpace.Named.ACESCG.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.CIE_XYZ.ordinal()] = new android.graphics.ColorSpace.Xyz("Generic XYZ", android.graphics.ColorSpace.Named.CIE_XYZ.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.CIE_LAB.ordinal()] = new android.graphics.ColorSpace.Lab("Generic L*a*b*", android.graphics.ColorSpace.Named.CIE_LAB.ordinal());
        float[] fArr3 = null;
        float f3 = 0.0f;
        float f4 = 1.0f;
        sNamedColorSpaces[android.graphics.ColorSpace.Named.BT2020_HLG.ordinal()] = new android.graphics.ColorSpace.Rgb("Hybrid Log Gamma encoding", BT2020_PRIMARIES, ILLUMINANT_D65, fArr3, new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda2
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                double transferHLGOETF;
                transferHLGOETF = android.graphics.ColorSpace.transferHLGOETF(android.graphics.ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS, d2);
                return transferHLGOETF;
            }
        }, new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda3
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                double transferHLGEOTF;
                transferHLGEOTF = android.graphics.ColorSpace.transferHLGEOTF(android.graphics.ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS, d2);
                return transferHLGEOTF;
            }
        }, f3, f4, BT2020_HLG_TRANSFER_PARAMETERS, android.graphics.ColorSpace.Named.BT2020_HLG.ordinal());
        sDataToColorSpaces.put(168165376, android.graphics.ColorSpace.Named.BT2020_HLG.ordinal());
        sNamedColorSpaces[android.graphics.ColorSpace.Named.BT2020_PQ.ordinal()] = new android.graphics.ColorSpace.Rgb("Perceptual Quantizer encoding", BT2020_PRIMARIES, ILLUMINANT_D65, fArr3, new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda4
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                double transferST2048OETF;
                transferST2048OETF = android.graphics.ColorSpace.transferST2048OETF(android.graphics.ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS, d2);
                return transferST2048OETF;
            }
        }, new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda5
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                double transferST2048EOTF;
                transferST2048EOTF = android.graphics.ColorSpace.transferST2048EOTF(android.graphics.ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS, d2);
                return transferST2048EOTF;
            }
        }, f3, f4, BT2020_PQ_TRANSFER_PARAMETERS, android.graphics.ColorSpace.Named.BT2020_PQ.ordinal());
        sDataToColorSpaces.put(163971072, android.graphics.ColorSpace.Named.BT2020_PQ.ordinal());
    }

    public enum Adaptation {
        BRADFORD(new float[]{0.8951f, -0.7502f, 0.0389f, 0.2664f, 1.7135f, -0.0685f, -0.1614f, 0.0367f, 1.0296f}),
        VON_KRIES(new float[]{0.40024f, -0.2263f, 0.0f, 0.7076f, 1.16532f, 0.0f, -0.08081f, 0.0457f, 0.91822f}),
        CIECAT02(new float[]{0.7328f, -0.7036f, 0.003f, 0.4296f, 1.6975f, 0.0136f, -0.1624f, 0.0061f, 0.9834f});

        final float[] mTransform;

        Adaptation(float[] fArr) {
            this.mTransform = fArr;
        }
    }

    public enum Model {
        RGB(3),
        XYZ(3),
        LAB(3),
        CMYK(4);

        private final int mComponentCount;

        Model(int i) {
            this.mComponentCount = i;
        }

        public int getComponentCount() {
            return this.mComponentCount;
        }
    }

    ColorSpace(java.lang.String str, android.graphics.ColorSpace.Model model, int i) {
        if (str == null || str.length() < 1) {
            throw new java.lang.IllegalArgumentException("The name of a color space cannot be null and must contain at least 1 character");
        }
        if (model == null) {
            throw new java.lang.IllegalArgumentException("A color space must have a model");
        }
        if (i < -1 || i > 63) {
            throw new java.lang.IllegalArgumentException("The id must be between -1 and 63");
        }
        this.mName = str;
        this.mModel = model;
        this.mId = i;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getId() {
        return this.mId;
    }

    public android.graphics.ColorSpace.Model getModel() {
        return this.mModel;
    }

    public int getComponentCount() {
        return this.mModel.getComponentCount();
    }

    public boolean isSrgb() {
        return false;
    }

    public float[] toXyz(float f, float f2, float f3) {
        return toXyz(new float[]{f, f2, f3});
    }

    public float[] fromXyz(float f, float f2, float f3) {
        float[] fArr = new float[this.mModel.getComponentCount()];
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        return fromXyz(fArr);
    }

    public java.lang.String toString() {
        return this.mName + " (id=" + this.mId + ", model=" + this.mModel + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.graphics.ColorSpace colorSpace = (android.graphics.ColorSpace) obj;
        if (this.mId == colorSpace.mId && this.mName.equals(colorSpace.mName) && this.mModel == colorSpace.mModel) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.mName.hashCode() * 31) + this.mModel.hashCode()) * 31) + this.mId;
    }

    public static android.graphics.ColorSpace.Connector connect(android.graphics.ColorSpace colorSpace, android.graphics.ColorSpace colorSpace2) {
        return connect(colorSpace, colorSpace2, android.graphics.ColorSpace.RenderIntent.PERCEPTUAL);
    }

    public static android.graphics.ColorSpace.Connector connect(android.graphics.ColorSpace colorSpace, android.graphics.ColorSpace colorSpace2, android.graphics.ColorSpace.RenderIntent renderIntent) {
        if (colorSpace.equals(colorSpace2)) {
            return android.graphics.ColorSpace.Connector.identity(colorSpace);
        }
        if (colorSpace.getModel() == android.graphics.ColorSpace.Model.RGB && colorSpace2.getModel() == android.graphics.ColorSpace.Model.RGB) {
            return new android.graphics.ColorSpace.Connector.Rgb((android.graphics.ColorSpace.Rgb) colorSpace, (android.graphics.ColorSpace.Rgb) colorSpace2, renderIntent);
        }
        return new android.graphics.ColorSpace.Connector(colorSpace, colorSpace2, renderIntent);
    }

    public static android.graphics.ColorSpace.Connector connect(android.graphics.ColorSpace colorSpace) {
        return connect(colorSpace, android.graphics.ColorSpace.RenderIntent.PERCEPTUAL);
    }

    public static android.graphics.ColorSpace.Connector connect(android.graphics.ColorSpace colorSpace, android.graphics.ColorSpace.RenderIntent renderIntent) {
        if (colorSpace.isSrgb()) {
            return android.graphics.ColorSpace.Connector.identity(colorSpace);
        }
        if (colorSpace.getModel() == android.graphics.ColorSpace.Model.RGB) {
            return new android.graphics.ColorSpace.Connector.Rgb((android.graphics.ColorSpace.Rgb) colorSpace, (android.graphics.ColorSpace.Rgb) get(android.graphics.ColorSpace.Named.SRGB), renderIntent);
        }
        return new android.graphics.ColorSpace.Connector(colorSpace, get(android.graphics.ColorSpace.Named.SRGB), renderIntent);
    }

    public static android.graphics.ColorSpace adapt(android.graphics.ColorSpace colorSpace, float[] fArr) {
        return adapt(colorSpace, fArr, android.graphics.ColorSpace.Adaptation.BRADFORD);
    }

    public static android.graphics.ColorSpace adapt(android.graphics.ColorSpace colorSpace, float[] fArr, android.graphics.ColorSpace.Adaptation adaptation) {
        if (colorSpace.getModel() == android.graphics.ColorSpace.Model.RGB) {
            android.graphics.ColorSpace.Rgb rgb = (android.graphics.ColorSpace.Rgb) colorSpace;
            if (compare(rgb.mWhitePoint, fArr)) {
                return colorSpace;
            }
            return new android.graphics.ColorSpace.Rgb(rgb, mul3x3(chromaticAdaptation(adaptation.mTransform, xyYToXyz(rgb.getWhitePoint()), fArr.length == 3 ? java.util.Arrays.copyOf(fArr, 3) : xyYToXyz(fArr)), rgb.mTransform), fArr);
        }
        return colorSpace;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] adaptToIlluminantD50(float[] fArr, float[] fArr2) {
        float[] fArr3 = ILLUMINANT_D50;
        if (compare(fArr, fArr3)) {
            return fArr2;
        }
        return mul3x3(chromaticAdaptation(android.graphics.ColorSpace.Adaptation.BRADFORD.mTransform, xyYToXyz(fArr), xyYToXyz(fArr3)), fArr2);
    }

    static android.graphics.ColorSpace get(int i) {
        if (i < 0 || i >= sNamedColorSpaces.length) {
            throw new java.lang.IllegalArgumentException("Invalid ID, must be in the range [0.." + sNamedColorSpaces.length + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return sNamedColorSpaces[i];
    }

    public static android.graphics.ColorSpace getFromDataSpace(int i) {
        int i2 = sDataToColorSpaces.get(i, -1);
        if (i2 != -1) {
            return get(i2);
        }
        return null;
    }

    public int getDataSpace() {
        int indexOfValue = sDataToColorSpaces.indexOfValue(getId());
        if (indexOfValue != -1) {
            return sDataToColorSpaces.keyAt(indexOfValue);
        }
        return 0;
    }

    public static android.graphics.ColorSpace get(android.graphics.ColorSpace.Named named) {
        return sNamedColorSpaces[named.ordinal()];
    }

    public static android.graphics.ColorSpace match(float[] fArr, android.graphics.ColorSpace.Rgb.TransferParameters transferParameters) {
        for (android.graphics.ColorSpace colorSpace : sNamedColorSpaces) {
            if (colorSpace.getModel() == android.graphics.ColorSpace.Model.RGB) {
                android.graphics.ColorSpace.Rgb rgb = (android.graphics.ColorSpace.Rgb) adapt(colorSpace, ILLUMINANT_D50_XYZ);
                if (compare(fArr, rgb.mTransform) && compare(transferParameters, rgb.mTransferParameters)) {
                    return colorSpace;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferHLGOETF(android.graphics.ColorSpace.Rgb.TransferParameters transferParameters, double d) {
        double d2 = d < 0.0d ? -1.0d : 1.0d;
        double d3 = 1.0d / transferParameters.a;
        double d4 = 1.0d / transferParameters.b;
        double d5 = 1.0d / transferParameters.c;
        double d6 = transferParameters.d;
        double d7 = transferParameters.e;
        double d8 = (d * d2) / (transferParameters.f + 1.0d);
        return d2 * (d8 <= 1.0d ? d3 * java.lang.Math.pow(d8, d4) : (d5 * java.lang.Math.log(d8 - d6)) + d7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferHLGEOTF(android.graphics.ColorSpace.Rgb.TransferParameters transferParameters, double d) {
        double d2 = d < 0.0d ? -1.0d : 1.0d;
        double d3 = d * d2;
        double d4 = transferParameters.a;
        double d5 = transferParameters.b;
        double d6 = transferParameters.c;
        double d7 = d4 * d3;
        return (transferParameters.f + 1.0d) * d2 * (d7 <= 1.0d ? java.lang.Math.pow(d7, d5) : java.lang.Math.exp((d3 - transferParameters.e) * d6) + transferParameters.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferST2048OETF(android.graphics.ColorSpace.Rgb.TransferParameters transferParameters, double d) {
        double d2 = d < 0.0d ? -1.0d : 1.0d;
        double d3 = d * d2;
        double d4 = -transferParameters.a;
        double d5 = transferParameters.d;
        double d6 = 1.0d / transferParameters.f;
        return d2 * java.lang.Math.pow(java.lang.Math.max(d4 + (d5 * java.lang.Math.pow(d3, d6)), 0.0d) / (transferParameters.b + ((-transferParameters.e) * java.lang.Math.pow(d3, d6))), 1.0d / transferParameters.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferST2048EOTF(android.graphics.ColorSpace.Rgb.TransferParameters transferParameters, double d) {
        double d2 = d < 0.0d ? -1.0d : 1.0d;
        double d3 = d * d2;
        return d2 * java.lang.Math.pow(java.lang.Math.max(transferParameters.a + (transferParameters.b * java.lang.Math.pow(d3, transferParameters.c)), 0.0d) / (transferParameters.d + (transferParameters.e * java.lang.Math.pow(d3, transferParameters.c))), transferParameters.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double rcpResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        return d >= d5 * d4 ? (java.lang.Math.pow(d, 1.0d / d6) - d3) / d2 : d / d4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double response(double d, double d2, double d3, double d4, double d5, double d6) {
        return d >= d5 ? java.lang.Math.pow((d2 * d) + d3, d6) : d * d4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double rcpResponse(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return d >= d5 * d4 ? (java.lang.Math.pow(d - d6, 1.0d / d8) - d3) / d2 : (d - d7) / d4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double response(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return d >= d5 ? java.lang.Math.pow((d2 * d) + d3, d8) + d6 : (d4 * d) + d7;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double absRcpResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        return java.lang.Math.copySign(rcpResponse(d < 0.0d ? -d : d, d2, d3, d4, d5, d6), d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double absResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        return java.lang.Math.copySign(response(d < 0.0d ? -d : d, d2, d3, d4, d5, d6), d);
    }

    private static boolean compare(android.graphics.ColorSpace.Rgb.TransferParameters transferParameters, android.graphics.ColorSpace.Rgb.TransferParameters transferParameters2) {
        if (transferParameters == null && transferParameters2 == null) {
            return true;
        }
        return transferParameters != null && transferParameters2 != null && java.lang.Math.abs(transferParameters.a - transferParameters2.a) < 0.001d && java.lang.Math.abs(transferParameters.b - transferParameters2.b) < 0.001d && java.lang.Math.abs(transferParameters.c - transferParameters2.c) < 0.001d && java.lang.Math.abs(transferParameters.d - transferParameters2.d) < 0.002d && java.lang.Math.abs(transferParameters.e - transferParameters2.e) < 0.001d && java.lang.Math.abs(transferParameters.f - transferParameters2.f) < 0.001d && java.lang.Math.abs(transferParameters.g - transferParameters2.g) < 0.001d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean compare(float[] fArr, float[] fArr2) {
        if (fArr == fArr2) {
            return true;
        }
        for (int i = 0; i < fArr.length; i++) {
            if (java.lang.Float.compare(fArr[i], fArr2[i]) != 0 && java.lang.Math.abs(fArr[i] - fArr2[i]) > 0.001f) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] inverse3x3(float[] fArr) {
        float f = fArr[0];
        float f2 = fArr[3];
        float f3 = fArr[6];
        float f4 = fArr[1];
        float f5 = fArr[4];
        float f6 = fArr[7];
        float f7 = fArr[2];
        float f8 = fArr[5];
        float f9 = fArr[8];
        float f10 = (f5 * f9) - (f6 * f8);
        float f11 = (f6 * f7) - (f4 * f9);
        float f12 = (f4 * f8) - (f5 * f7);
        float f13 = (f * f10) + (f2 * f11) + (f3 * f12);
        float[] fArr2 = new float[fArr.length];
        fArr2[0] = f10 / f13;
        fArr2[1] = f11 / f13;
        fArr2[2] = f12 / f13;
        fArr2[3] = ((f3 * f8) - (f2 * f9)) / f13;
        fArr2[4] = ((f9 * f) - (f3 * f7)) / f13;
        fArr2[5] = ((f7 * f2) - (f8 * f)) / f13;
        fArr2[6] = ((f2 * f6) - (f3 * f5)) / f13;
        fArr2[7] = ((f3 * f4) - (f6 * f)) / f13;
        fArr2[8] = ((f * f5) - (f2 * f4)) / f13;
        return fArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3(float[] fArr, float[] fArr2) {
        return new float[]{(fArr[0] * fArr2[0]) + (fArr[3] * fArr2[1]) + (fArr[6] * fArr2[2]), (fArr[1] * fArr2[0]) + (fArr[4] * fArr2[1]) + (fArr[7] * fArr2[2]), (fArr[2] * fArr2[0]) + (fArr[5] * fArr2[1]) + (fArr[8] * fArr2[2]), (fArr[0] * fArr2[3]) + (fArr[3] * fArr2[4]) + (fArr[6] * fArr2[5]), (fArr[1] * fArr2[3]) + (fArr[4] * fArr2[4]) + (fArr[7] * fArr2[5]), (fArr[2] * fArr2[3]) + (fArr[5] * fArr2[4]) + (fArr[8] * fArr2[5]), (fArr[0] * fArr2[6]) + (fArr[3] * fArr2[7]) + (fArr[6] * fArr2[8]), (fArr[1] * fArr2[6]) + (fArr[4] * fArr2[7]) + (fArr[7] * fArr2[8]), (fArr[2] * fArr2[6]) + (fArr[5] * fArr2[7]) + (fArr[8] * fArr2[8])};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3Float3(float[] fArr, float[] fArr2) {
        float f = fArr2[0];
        float f2 = fArr2[1];
        float f3 = fArr2[2];
        fArr2[0] = (fArr[0] * f) + (fArr[3] * f2) + (fArr[6] * f3);
        fArr2[1] = (fArr[1] * f) + (fArr[4] * f2) + (fArr[7] * f3);
        fArr2[2] = (fArr[2] * f) + (fArr[5] * f2) + (fArr[8] * f3);
        return fArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3Diag(float[] fArr, float[] fArr2) {
        return new float[]{fArr[0] * fArr2[0], fArr[1] * fArr2[1], fArr[2] * fArr2[2], fArr[0] * fArr2[3], fArr[1] * fArr2[4], fArr[2] * fArr2[5], fArr[0] * fArr2[6], fArr[1] * fArr2[7], fArr[2] * fArr2[8]};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] xyYToXyz(float[] fArr) {
        return new float[]{fArr[0] / fArr[1], 1.0f, ((1.0f - fArr[0]) - fArr[1]) / fArr[1]};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] chromaticAdaptation(float[] fArr, float[] fArr2, float[] fArr3) {
        float[] mul3x3Float3 = mul3x3Float3(fArr, fArr2);
        float[] mul3x3Float32 = mul3x3Float3(fArr, fArr3);
        return mul3x3(inverse3x3(fArr), mul3x3Diag(new float[]{mul3x3Float32[0] / mul3x3Float3[0], mul3x3Float32[1] / mul3x3Float3[1], mul3x3Float32[2] / mul3x3Float3[2]}, fArr));
    }

    public static float[] cctToXyz(int i) {
        float f;
        float f2;
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("Temperature must be greater than 0");
        }
        float f3 = i;
        float f4 = 1000.0f / f3;
        float f5 = f4 * f4;
        if (f3 <= 4000.0f) {
            f = (((0.8776956f * f4) + 0.17991f) - (0.2343589f * f5)) - ((f5 * 0.2661239f) * f4);
        } else {
            f = (((0.2226347f * f4) + 0.24039f) + (2.1070378f * f5)) - ((f5 * 3.025847f) * f4);
        }
        float f6 = f * f;
        if (f3 <= 2222.0f) {
            f2 = (((2.1855583f * f) - 0.20219684f) - (1.3481102f * f6)) - ((f6 * 1.1063814f) * f);
        } else if (f3 <= 4000.0f) {
            f2 = (((2.09137f * f) - 0.16748866f) - (1.3741859f * f6)) - ((f6 * 0.9549476f) * f);
        } else {
            f2 = (((3.7511299f * f) - 0.37001482f) - (5.873387f * f6)) + (f6 * 3.081758f * f);
        }
        return xyYToXyz(new float[]{f, f2});
    }

    public static float[] chromaticAdaptation(android.graphics.ColorSpace.Adaptation adaptation, float[] fArr, float[] fArr2) {
        if ((fArr.length != 2 && fArr.length != 3) || (fArr2.length != 2 && fArr2.length != 3)) {
            throw new java.lang.IllegalArgumentException("A white point array must have 2 or 3 floats");
        }
        float[] copyOf = fArr.length == 3 ? java.util.Arrays.copyOf(fArr, 3) : xyYToXyz(fArr);
        float[] copyOf2 = fArr2.length == 3 ? java.util.Arrays.copyOf(fArr2, 3) : xyYToXyz(fArr2);
        if (compare(copyOf, copyOf2)) {
            return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        }
        return chromaticAdaptation(adaptation.mTransform, copyOf, copyOf2);
    }

    private static final class Xyz extends android.graphics.ColorSpace {
        private Xyz(java.lang.String str, int i) {
            super(str, android.graphics.ColorSpace.Model.XYZ, i);
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return true;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int i) {
            return -2.0f;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int i) {
            return 2.0f;
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] fArr) {
            fArr[0] = clamp(fArr[0]);
            fArr[1] = clamp(fArr[1]);
            fArr[2] = clamp(fArr[2]);
            return fArr;
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] fArr) {
            fArr[0] = clamp(fArr[0]);
            fArr[1] = clamp(fArr[1]);
            fArr[2] = clamp(fArr[2]);
            return fArr;
        }

        private static float clamp(float f) {
            float f2 = -2.0f;
            if (f >= -2.0f) {
                f2 = 2.0f;
                if (f <= 2.0f) {
                    return f;
                }
            }
            return f2;
        }
    }

    private static final class Lab extends android.graphics.ColorSpace {
        private static final float A = 0.008856452f;
        private static final float B = 7.787037f;
        private static final float C = 0.13793103f;
        private static final float D = 0.20689656f;

        private Lab(java.lang.String str, int i) {
            super(str, android.graphics.ColorSpace.Model.LAB, i);
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return true;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int i) {
            return i == 0 ? 0.0f : -128.0f;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int i) {
            return i == 0 ? 100.0f : 128.0f;
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] fArr) {
            fArr[0] = clamp(fArr[0], 0.0f, 100.0f);
            fArr[1] = clamp(fArr[1], -128.0f, 128.0f);
            fArr[2] = clamp(fArr[2], -128.0f, 128.0f);
            float f = (fArr[0] + 16.0f) / 116.0f;
            float f2 = (fArr[1] * 0.002f) + f;
            float f3 = f - (fArr[2] * 0.005f);
            float f4 = f2 > D ? f2 * f2 * f2 : (f2 - C) * 0.12841855f;
            float f5 = f > D ? f * f * f : (f - C) * 0.12841855f;
            float f6 = f3 > D ? f3 * f3 * f3 : (f3 - C) * 0.12841855f;
            fArr[0] = f4 * android.graphics.ColorSpace.ILLUMINANT_D50_XYZ[0];
            fArr[1] = f5 * android.graphics.ColorSpace.ILLUMINANT_D50_XYZ[1];
            fArr[2] = f6 * android.graphics.ColorSpace.ILLUMINANT_D50_XYZ[2];
            return fArr;
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] fArr) {
            float f = fArr[0] / android.graphics.ColorSpace.ILLUMINANT_D50_XYZ[0];
            float f2 = fArr[1] / android.graphics.ColorSpace.ILLUMINANT_D50_XYZ[1];
            float f3 = fArr[2] / android.graphics.ColorSpace.ILLUMINANT_D50_XYZ[2];
            float pow = f > A ? (float) java.lang.Math.pow(f, 0.3333333333333333d) : (f * B) + C;
            float pow2 = f2 > A ? (float) java.lang.Math.pow(f2, 0.3333333333333333d) : (f2 * B) + C;
            float f4 = (116.0f * pow2) - 16.0f;
            float f5 = (pow - pow2) * 500.0f;
            float pow3 = (pow2 - (f3 > A ? (float) java.lang.Math.pow(f3, 0.3333333333333333d) : (f3 * B) + C)) * 200.0f;
            fArr[0] = clamp(f4, 0.0f, 100.0f);
            fArr[1] = clamp(f5, -128.0f, 128.0f);
            fArr[2] = clamp(pow3, -128.0f, 128.0f);
            return fArr;
        }

        private static float clamp(float f, float f2, float f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f;
        }
    }

    long getNativeInstance() {
        throw new java.lang.IllegalArgumentException("colorSpace must be an RGB color space");
    }

    public static class Rgb extends android.graphics.ColorSpace {
        private final java.util.function.DoubleUnaryOperator mClampedEotf;
        private final java.util.function.DoubleUnaryOperator mClampedOetf;
        private final java.util.function.DoubleUnaryOperator mEotf;
        private final float[] mInverseTransform;
        private final boolean mIsSrgb;
        private final boolean mIsWideGamut;
        private final float mMax;
        private final float mMin;
        private final long mNativePtr;
        private final java.util.function.DoubleUnaryOperator mOetf;
        private final float[] mPrimaries;
        private final android.graphics.ColorSpace.Rgb.TransferParameters mTransferParameters;
        private final float[] mTransform;
        private final float[] mWhitePoint;

        private static native long nativeCreate(float f, float f2, float f3, float f4, float f5, float f6, float f7, float[] fArr);

        /* JADX INFO: Access modifiers changed from: private */
        public static native long nativeGetNativeFinalizer();

        public static class TransferParameters {
            private static final double TYPE_HLGish = -3.0d;
            private static final double TYPE_PQish = -2.0d;
            public final double a;
            public final double b;
            public final double c;
            public final double d;
            public final double e;
            public final double f;
            public final double g;

            private static boolean isSpecialG(double d) {
                return d == TYPE_PQish || d == TYPE_HLGish;
            }

            public TransferParameters(double d, double d2, double d3, double d4, double d5) {
                this(d, d2, d3, d4, 0.0d, 0.0d, d5);
            }

            public TransferParameters(double d, double d2, double d3, double d4, double d5, double d6, double d7) {
                if (java.lang.Double.isNaN(d) || java.lang.Double.isNaN(d2) || java.lang.Double.isNaN(d3) || java.lang.Double.isNaN(d4) || java.lang.Double.isNaN(d5) || java.lang.Double.isNaN(d6) || java.lang.Double.isNaN(d7)) {
                    throw new java.lang.IllegalArgumentException("Parameters cannot be NaN");
                }
                if (!isSpecialG(d7)) {
                    if (d4 < 0.0d || d4 > java.lang.Math.ulp(1.0f) + 1.0f) {
                        throw new java.lang.IllegalArgumentException("Parameter d must be in the range [0..1], was " + d4);
                    }
                    if (d4 == 0.0d && (d == 0.0d || d7 == 0.0d)) {
                        throw new java.lang.IllegalArgumentException("Parameter a or g is zero, the transfer function is constant");
                    }
                    if (d4 >= 1.0d && d3 == 0.0d) {
                        throw new java.lang.IllegalArgumentException("Parameter c is zero, the transfer function is constant");
                    }
                    if ((d == 0.0d || d7 == 0.0d) && d3 == 0.0d) {
                        throw new java.lang.IllegalArgumentException("Parameter a or g is zero, and c is zero, the transfer function is constant");
                    }
                    if (d3 < 0.0d) {
                        throw new java.lang.IllegalArgumentException("The transfer function must be increasing");
                    }
                    if (d < 0.0d || d7 < 0.0d) {
                        throw new java.lang.IllegalArgumentException("The transfer function must be positive or increasing");
                    }
                }
                this.a = d;
                this.b = d2;
                this.c = d3;
                this.d = d4;
                this.e = d5;
                this.f = d6;
                this.g = d7;
            }

            public boolean equals(java.lang.Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                android.graphics.ColorSpace.Rgb.TransferParameters transferParameters = (android.graphics.ColorSpace.Rgb.TransferParameters) obj;
                if (java.lang.Double.compare(transferParameters.a, this.a) == 0 && java.lang.Double.compare(transferParameters.b, this.b) == 0 && java.lang.Double.compare(transferParameters.c, this.c) == 0 && java.lang.Double.compare(transferParameters.d, this.d) == 0 && java.lang.Double.compare(transferParameters.e, this.e) == 0 && java.lang.Double.compare(transferParameters.f, this.f) == 0 && java.lang.Double.compare(transferParameters.g, this.g) == 0) {
                    return true;
                }
                return false;
            }

            public int hashCode() {
                long doubleToLongBits = java.lang.Double.doubleToLongBits(this.a);
                long doubleToLongBits2 = java.lang.Double.doubleToLongBits(this.b);
                int i = (((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)));
                long doubleToLongBits3 = java.lang.Double.doubleToLongBits(this.c);
                int i2 = (i * 31) + ((int) (doubleToLongBits3 ^ (doubleToLongBits3 >>> 32)));
                long doubleToLongBits4 = java.lang.Double.doubleToLongBits(this.d);
                int i3 = (i2 * 31) + ((int) (doubleToLongBits4 ^ (doubleToLongBits4 >>> 32)));
                long doubleToLongBits5 = java.lang.Double.doubleToLongBits(this.e);
                int i4 = (i3 * 31) + ((int) (doubleToLongBits5 ^ (doubleToLongBits5 >>> 32)));
                long doubleToLongBits6 = java.lang.Double.doubleToLongBits(this.f);
                int i5 = (i4 * 31) + ((int) (doubleToLongBits6 ^ (doubleToLongBits6 >>> 32)));
                long doubleToLongBits7 = java.lang.Double.doubleToLongBits(this.g);
                return (i5 * 31) + ((int) ((doubleToLongBits7 >>> 32) ^ doubleToLongBits7));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean isHLGish() {
                return this.g == TYPE_HLGish;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean isPQish() {
                return this.g == TYPE_PQish;
            }
        }

        @Override // android.graphics.ColorSpace
        long getNativeInstance() {
            if (this.mNativePtr == 0) {
                throw new java.lang.IllegalArgumentException("ColorSpace must use an ICC parametric transfer function! used " + this);
            }
            return this.mNativePtr;
        }

        private static java.util.function.DoubleUnaryOperator generateOETF(final android.graphics.ColorSpace.Rgb.TransferParameters transferParameters) {
            if (transferParameters.isHLGish()) {
                return new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda2
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double transferHLGOETF;
                        transferHLGOETF = android.graphics.ColorSpace.transferHLGOETF(android.graphics.ColorSpace.Rgb.TransferParameters.this, d);
                        return transferHLGOETF;
                    }
                };
            }
            if (transferParameters.isPQish()) {
                return new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda3
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double transferST2048OETF;
                        transferST2048OETF = android.graphics.ColorSpace.transferST2048OETF(android.graphics.ColorSpace.Rgb.TransferParameters.this, d);
                        return transferST2048OETF;
                    }
                };
            }
            if (transferParameters.e == 0.0d && transferParameters.f == 0.0d) {
                return new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda4
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double rcpResponse;
                        rcpResponse = android.graphics.ColorSpace.rcpResponse(d, r0.a, r0.b, r0.c, r0.d, android.graphics.ColorSpace.Rgb.TransferParameters.this.g);
                        return rcpResponse;
                    }
                };
            }
            return new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda5
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    double rcpResponse;
                    rcpResponse = android.graphics.ColorSpace.rcpResponse(d, r0.a, r0.b, r0.c, r0.d, r0.e, r0.f, android.graphics.ColorSpace.Rgb.TransferParameters.this.g);
                    return rcpResponse;
                }
            };
        }

        private static java.util.function.DoubleUnaryOperator generateEOTF(final android.graphics.ColorSpace.Rgb.TransferParameters transferParameters) {
            if (transferParameters.isHLGish()) {
                return new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda7
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double transferHLGEOTF;
                        transferHLGEOTF = android.graphics.ColorSpace.transferHLGEOTF(android.graphics.ColorSpace.Rgb.TransferParameters.this, d);
                        return transferHLGEOTF;
                    }
                };
            }
            if (transferParameters.isPQish()) {
                return new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda8
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double transferST2048OETF;
                        transferST2048OETF = android.graphics.ColorSpace.transferST2048OETF(android.graphics.ColorSpace.Rgb.TransferParameters.this, d);
                        return transferST2048OETF;
                    }
                };
            }
            if (transferParameters.e == 0.0d && transferParameters.f == 0.0d) {
                return new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda9
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double response;
                        response = android.graphics.ColorSpace.response(d, r0.a, r0.b, r0.c, r0.d, android.graphics.ColorSpace.Rgb.TransferParameters.this.g);
                        return response;
                    }
                };
            }
            return new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda10
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    double response;
                    response = android.graphics.ColorSpace.response(d, r0.a, r0.b, r0.c, r0.d, r0.e, r0.f, android.graphics.ColorSpace.Rgb.TransferParameters.this.g);
                    return response;
                }
            };
        }

        public Rgb(java.lang.String str, float[] fArr, java.util.function.DoubleUnaryOperator doubleUnaryOperator, java.util.function.DoubleUnaryOperator doubleUnaryOperator2) {
            this(str, computePrimaries(fArr), computeWhitePoint(fArr), null, doubleUnaryOperator, doubleUnaryOperator2, 0.0f, 1.0f, null, -1);
        }

        public Rgb(java.lang.String str, float[] fArr, float[] fArr2, java.util.function.DoubleUnaryOperator doubleUnaryOperator, java.util.function.DoubleUnaryOperator doubleUnaryOperator2, float f, float f2) {
            this(str, fArr, fArr2, null, doubleUnaryOperator, doubleUnaryOperator2, f, f2, null, -1);
        }

        public Rgb(java.lang.String str, float[] fArr, android.graphics.ColorSpace.Rgb.TransferParameters transferParameters) {
            this(str, isGray(fArr) ? android.graphics.ColorSpace.GRAY_PRIMARIES : computePrimaries(fArr), computeWhitePoint(fArr), isGray(fArr) ? fArr : null, transferParameters, -1);
        }

        public Rgb(java.lang.String str, float[] fArr, float[] fArr2, android.graphics.ColorSpace.Rgb.TransferParameters transferParameters) {
            this(str, fArr, fArr2, null, transferParameters, -1);
        }

        private Rgb(java.lang.String str, float[] fArr, float[] fArr2, float[] fArr3, android.graphics.ColorSpace.Rgb.TransferParameters transferParameters, int i) {
            this(str, fArr, fArr2, fArr3, generateOETF(transferParameters), generateEOTF(transferParameters), 0.0f, 1.0f, transferParameters, i);
        }

        public Rgb(java.lang.String str, float[] fArr, double d) {
            this(str, computePrimaries(fArr), computeWhitePoint(fArr), d, 0.0f, 1.0f, -1);
        }

        public Rgb(java.lang.String str, float[] fArr, float[] fArr2, double d) {
            this(str, fArr, fArr2, d, 0.0f, 1.0f, -1);
        }

        private Rgb(java.lang.String str, float[] fArr, float[] fArr2, final double d, float f, float f2, int i) {
            this(str, fArr, fArr2, null, d == 1.0d ? java.util.function.DoubleUnaryOperator.identity() : new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda0
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d2) {
                    return android.graphics.ColorSpace.Rgb.lambda$new$8(d, d2);
                }
            }, d == 1.0d ? java.util.function.DoubleUnaryOperator.identity() : new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda1
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d2) {
                    return android.graphics.ColorSpace.Rgb.lambda$new$9(d, d2);
                }
            }, f, f2, new android.graphics.ColorSpace.Rgb.TransferParameters(1.0d, 0.0d, 0.0d, 0.0d, d), i);
        }

        static /* synthetic */ double lambda$new$8(double d, double d2) {
            if (d2 < 0.0d) {
                d2 = 0.0d;
            }
            return java.lang.Math.pow(d2, 1.0d / d);
        }

        static /* synthetic */ double lambda$new$9(double d, double d2) {
            if (d2 < 0.0d) {
                d2 = 0.0d;
            }
            return java.lang.Math.pow(d2, d);
        }

        private Rgb(java.lang.String str, float[] fArr, float[] fArr2, float[] fArr3, java.util.function.DoubleUnaryOperator doubleUnaryOperator, java.util.function.DoubleUnaryOperator doubleUnaryOperator2, float f, float f2, android.graphics.ColorSpace.Rgb.TransferParameters transferParameters, int i) {
            super(str, android.graphics.ColorSpace.Model.RGB, i);
            if (fArr == null || (fArr.length != 6 && fArr.length != 9)) {
                throw new java.lang.IllegalArgumentException("The color space's primaries must be defined as an array of 6 floats in xyY or 9 floats in XYZ");
            }
            if (fArr2 == null || (fArr2.length != 2 && fArr2.length != 3)) {
                throw new java.lang.IllegalArgumentException("The color space's white point must be defined as an array of 2 floats in xyY or 3 float in XYZ");
            }
            if (doubleUnaryOperator == null || doubleUnaryOperator2 == null) {
                throw new java.lang.IllegalArgumentException("The transfer functions of a color space cannot be null");
            }
            if (f < f2) {
                this.mWhitePoint = xyWhitePoint(fArr2);
                this.mPrimaries = xyPrimaries(fArr);
                if (fArr3 == null) {
                    this.mTransform = computeXYZMatrix(this.mPrimaries, this.mWhitePoint);
                } else {
                    if (fArr3.length != 9) {
                        throw new java.lang.IllegalArgumentException("Transform must have 9 entries! Has " + fArr3.length);
                    }
                    this.mTransform = fArr3;
                }
                this.mInverseTransform = android.graphics.ColorSpace.inverse3x3(this.mTransform);
                this.mOetf = doubleUnaryOperator;
                this.mEotf = doubleUnaryOperator2;
                this.mMin = f;
                this.mMax = f2;
                java.util.function.DoubleUnaryOperator doubleUnaryOperator3 = new java.util.function.DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda6
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double clamp;
                        clamp = android.graphics.ColorSpace.Rgb.this.clamp(d);
                        return clamp;
                    }
                };
                this.mClampedOetf = doubleUnaryOperator.andThen(doubleUnaryOperator3);
                this.mClampedEotf = doubleUnaryOperator3.andThen(doubleUnaryOperator2);
                this.mTransferParameters = transferParameters;
                this.mIsWideGamut = isWideGamut(this.mPrimaries, f, f2);
                this.mIsSrgb = isSrgb(this.mPrimaries, this.mWhitePoint, doubleUnaryOperator, doubleUnaryOperator2, f, f2, i);
                if (this.mTransferParameters == null) {
                    this.mNativePtr = 0L;
                    return;
                } else {
                    if (this.mWhitePoint != null && this.mTransform != null) {
                        this.mNativePtr = nativeCreate((float) this.mTransferParameters.a, (float) this.mTransferParameters.b, (float) this.mTransferParameters.c, (float) this.mTransferParameters.d, (float) this.mTransferParameters.e, (float) this.mTransferParameters.f, (float) this.mTransferParameters.g, android.graphics.ColorSpace.adaptToIlluminantD50(this.mWhitePoint, this.mTransform));
                        android.graphics.ColorSpace.Rgb.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativePtr);
                        return;
                    }
                    throw new java.lang.IllegalStateException("ColorSpace (" + this + ") cannot create native object! mWhitePoint: " + java.util.Arrays.toString(this.mWhitePoint) + " mTransform: " + java.util.Arrays.toString(this.mTransform));
                }
            }
            throw new java.lang.IllegalArgumentException("Invalid range: min=" + f + ", max=" + f2 + "; min must be strictly < max");
        }

        private static class NoImagePreloadHolder {
            public static final libcore.util.NativeAllocationRegistry sRegistry = new libcore.util.NativeAllocationRegistry(android.graphics.ColorSpace.Rgb.class.getClassLoader(), android.graphics.ColorSpace.Rgb.nativeGetNativeFinalizer(), 0);

            private NoImagePreloadHolder() {
            }
        }

        private Rgb(android.graphics.ColorSpace.Rgb rgb, float[] fArr, float[] fArr2) {
            this(rgb.getName(), rgb.mPrimaries, fArr2, fArr, rgb.mOetf, rgb.mEotf, rgb.mMin, rgb.mMax, rgb.mTransferParameters, -1);
        }

        public float[] getWhitePoint(float[] fArr) {
            fArr[0] = this.mWhitePoint[0];
            fArr[1] = this.mWhitePoint[1];
            return fArr;
        }

        public float[] getWhitePoint() {
            return java.util.Arrays.copyOf(this.mWhitePoint, this.mWhitePoint.length);
        }

        public float[] getPrimaries(float[] fArr) {
            java.lang.System.arraycopy(this.mPrimaries, 0, fArr, 0, this.mPrimaries.length);
            return fArr;
        }

        public float[] getPrimaries() {
            return java.util.Arrays.copyOf(this.mPrimaries, this.mPrimaries.length);
        }

        public float[] getTransform(float[] fArr) {
            java.lang.System.arraycopy(this.mTransform, 0, fArr, 0, this.mTransform.length);
            return fArr;
        }

        public float[] getTransform() {
            return java.util.Arrays.copyOf(this.mTransform, this.mTransform.length);
        }

        public float[] getInverseTransform(float[] fArr) {
            java.lang.System.arraycopy(this.mInverseTransform, 0, fArr, 0, this.mInverseTransform.length);
            return fArr;
        }

        public float[] getInverseTransform() {
            return java.util.Arrays.copyOf(this.mInverseTransform, this.mInverseTransform.length);
        }

        public java.util.function.DoubleUnaryOperator getOetf() {
            return this.mClampedOetf;
        }

        public java.util.function.DoubleUnaryOperator getEotf() {
            return this.mClampedEotf;
        }

        public android.graphics.ColorSpace.Rgb.TransferParameters getTransferParameters() {
            if (this.mTransferParameters != null && !this.mTransferParameters.equals(android.graphics.ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS) && !this.mTransferParameters.equals(android.graphics.ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS)) {
                return this.mTransferParameters;
            }
            return null;
        }

        @Override // android.graphics.ColorSpace
        public boolean isSrgb() {
            return this.mIsSrgb;
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return this.mIsWideGamut;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int i) {
            return this.mMin;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int i) {
            return this.mMax;
        }

        public float[] toLinear(float f, float f2, float f3) {
            return toLinear(new float[]{f, f2, f3});
        }

        public float[] toLinear(float[] fArr) {
            fArr[0] = (float) this.mClampedEotf.applyAsDouble(fArr[0]);
            fArr[1] = (float) this.mClampedEotf.applyAsDouble(fArr[1]);
            fArr[2] = (float) this.mClampedEotf.applyAsDouble(fArr[2]);
            return fArr;
        }

        public float[] fromLinear(float f, float f2, float f3) {
            return fromLinear(new float[]{f, f2, f3});
        }

        public float[] fromLinear(float[] fArr) {
            fArr[0] = (float) this.mClampedOetf.applyAsDouble(fArr[0]);
            fArr[1] = (float) this.mClampedOetf.applyAsDouble(fArr[1]);
            fArr[2] = (float) this.mClampedOetf.applyAsDouble(fArr[2]);
            return fArr;
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] fArr) {
            fArr[0] = (float) this.mClampedEotf.applyAsDouble(fArr[0]);
            fArr[1] = (float) this.mClampedEotf.applyAsDouble(fArr[1]);
            fArr[2] = (float) this.mClampedEotf.applyAsDouble(fArr[2]);
            return android.graphics.ColorSpace.mul3x3Float3(this.mTransform, fArr);
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] fArr) {
            android.graphics.ColorSpace.mul3x3Float3(this.mInverseTransform, fArr);
            fArr[0] = (float) this.mClampedOetf.applyAsDouble(fArr[0]);
            fArr[1] = (float) this.mClampedOetf.applyAsDouble(fArr[1]);
            fArr[2] = (float) this.mClampedOetf.applyAsDouble(fArr[2]);
            return fArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public double clamp(double d) {
            float f;
            if (d < this.mMin) {
                f = this.mMin;
            } else {
                if (d <= this.mMax) {
                    return d;
                }
                f = this.mMax;
            }
            return f;
        }

        @Override // android.graphics.ColorSpace
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
                return false;
            }
            android.graphics.ColorSpace.Rgb rgb = (android.graphics.ColorSpace.Rgb) obj;
            if (java.lang.Float.compare(rgb.mMin, this.mMin) != 0 || java.lang.Float.compare(rgb.mMax, this.mMax) != 0 || !java.util.Arrays.equals(this.mWhitePoint, rgb.mWhitePoint) || !java.util.Arrays.equals(this.mPrimaries, rgb.mPrimaries)) {
                return false;
            }
            if (this.mTransferParameters != null) {
                return this.mTransferParameters.equals(rgb.mTransferParameters);
            }
            if (rgb.mTransferParameters == null) {
                return true;
            }
            if (!this.mOetf.equals(rgb.mOetf)) {
                return false;
            }
            return this.mEotf.equals(rgb.mEotf);
        }

        @Override // android.graphics.ColorSpace
        public int hashCode() {
            int hashCode = (((((((((super.hashCode() * 31) + java.util.Arrays.hashCode(this.mWhitePoint)) * 31) + java.util.Arrays.hashCode(this.mPrimaries)) * 31) + (this.mMin != 0.0f ? java.lang.Float.floatToIntBits(this.mMin) : 0)) * 31) + (this.mMax != 0.0f ? java.lang.Float.floatToIntBits(this.mMax) : 0)) * 31) + (this.mTransferParameters != null ? this.mTransferParameters.hashCode() : 0);
            if (this.mTransferParameters == null) {
                return (((hashCode * 31) + this.mOetf.hashCode()) * 31) + this.mEotf.hashCode();
            }
            return hashCode;
        }

        private static boolean isSrgb(float[] fArr, float[] fArr2, java.util.function.DoubleUnaryOperator doubleUnaryOperator, java.util.function.DoubleUnaryOperator doubleUnaryOperator2, float f, float f2, int i) {
            if (i == 0) {
                return true;
            }
            if (!android.graphics.ColorSpace.compare(fArr, android.graphics.ColorSpace.SRGB_PRIMARIES) || !android.graphics.ColorSpace.compare(fArr2, ILLUMINANT_D65) || f != 0.0f || f2 != 1.0f) {
                return false;
            }
            android.graphics.ColorSpace.Rgb rgb = (android.graphics.ColorSpace.Rgb) get(android.graphics.ColorSpace.Named.SRGB);
            for (double d = 0.0d; d <= 1.0d; d += 0.00392156862745098d) {
                if (!compare(d, doubleUnaryOperator, rgb.mOetf) || !compare(d, doubleUnaryOperator2, rgb.mEotf)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isGray(float[] fArr) {
            return fArr.length == 9 && fArr[1] == 0.0f && fArr[2] == 0.0f && fArr[3] == 0.0f && fArr[5] == 0.0f && fArr[6] == 0.0f && fArr[7] == 0.0f;
        }

        private static boolean compare(double d, java.util.function.DoubleUnaryOperator doubleUnaryOperator, java.util.function.DoubleUnaryOperator doubleUnaryOperator2) {
            return java.lang.Math.abs(doubleUnaryOperator.applyAsDouble(d) - doubleUnaryOperator2.applyAsDouble(d)) <= 0.001d;
        }

        private static boolean isWideGamut(float[] fArr, float f, float f2) {
            return (area(fArr) / area(android.graphics.ColorSpace.NTSC_1953_PRIMARIES) > 0.9f && contains(fArr, android.graphics.ColorSpace.SRGB_PRIMARIES)) || (f < 0.0f && f2 > 1.0f);
        }

        private static float area(float[] fArr) {
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float f4 = fArr[3];
            float f5 = fArr[4];
            float f6 = fArr[5];
            float f7 = ((((((f * f4) + (f2 * f5)) + (f3 * f6)) - (f4 * f5)) - (f2 * f3)) - (f * f6)) * 0.5f;
            return f7 < 0.0f ? -f7 : f7;
        }

        private static float cross(float f, float f2, float f3, float f4) {
            return (f * f4) - (f2 * f3);
        }

        private static boolean contains(float[] fArr, float[] fArr2) {
            float[] fArr3 = {fArr[0] - fArr2[0], fArr[1] - fArr2[1], fArr[2] - fArr2[2], fArr[3] - fArr2[3], fArr[4] - fArr2[4], fArr[5] - fArr2[5]};
            return cross(fArr3[0], fArr3[1], fArr2[0] - fArr2[4], fArr2[1] - fArr2[5]) >= 0.0f && cross(fArr2[0] - fArr2[2], fArr2[1] - fArr2[3], fArr3[0], fArr3[1]) >= 0.0f && cross(fArr3[2], fArr3[3], fArr2[2] - fArr2[0], fArr2[3] - fArr2[1]) >= 0.0f && cross(fArr2[2] - fArr2[4], fArr2[3] - fArr2[5], fArr3[2], fArr3[3]) >= 0.0f && cross(fArr3[4], fArr3[5], fArr2[4] - fArr2[2], fArr2[5] - fArr2[3]) >= 0.0f && cross(fArr2[4] - fArr2[0], fArr2[5] - fArr2[1], fArr3[4], fArr3[5]) >= 0.0f;
        }

        private static float[] computePrimaries(float[] fArr) {
            float[] mul3x3Float3 = android.graphics.ColorSpace.mul3x3Float3(fArr, new float[]{1.0f, 0.0f, 0.0f});
            float[] mul3x3Float32 = android.graphics.ColorSpace.mul3x3Float3(fArr, new float[]{0.0f, 1.0f, 0.0f});
            float[] mul3x3Float33 = android.graphics.ColorSpace.mul3x3Float3(fArr, new float[]{0.0f, 0.0f, 1.0f});
            float f = mul3x3Float3[0] + mul3x3Float3[1] + mul3x3Float3[2];
            float f2 = mul3x3Float32[0] + mul3x3Float32[1] + mul3x3Float32[2];
            float f3 = mul3x3Float33[0] + mul3x3Float33[1] + mul3x3Float33[2];
            return new float[]{mul3x3Float3[0] / f, mul3x3Float3[1] / f, mul3x3Float32[0] / f2, mul3x3Float32[1] / f2, mul3x3Float33[0] / f3, mul3x3Float33[1] / f3};
        }

        private static float[] computeWhitePoint(float[] fArr) {
            float[] mul3x3Float3 = android.graphics.ColorSpace.mul3x3Float3(fArr, new float[]{1.0f, 1.0f, 1.0f});
            float f = mul3x3Float3[0] + mul3x3Float3[1] + mul3x3Float3[2];
            return new float[]{mul3x3Float3[0] / f, mul3x3Float3[1] / f};
        }

        private static float[] xyPrimaries(float[] fArr) {
            float[] fArr2 = new float[6];
            if (fArr.length == 9) {
                float f = fArr[0] + fArr[1] + fArr[2];
                fArr2[0] = fArr[0] / f;
                fArr2[1] = fArr[1] / f;
                float f2 = fArr[3] + fArr[4] + fArr[5];
                fArr2[2] = fArr[3] / f2;
                fArr2[3] = fArr[4] / f2;
                float f3 = fArr[6] + fArr[7] + fArr[8];
                fArr2[4] = fArr[6] / f3;
                fArr2[5] = fArr[7] / f3;
            } else {
                java.lang.System.arraycopy(fArr, 0, fArr2, 0, 6);
            }
            return fArr2;
        }

        private static float[] xyWhitePoint(float[] fArr) {
            float[] fArr2 = new float[2];
            if (fArr.length == 3) {
                float f = fArr[0] + fArr[1] + fArr[2];
                fArr2[0] = fArr[0] / f;
                fArr2[1] = fArr[1] / f;
            } else {
                java.lang.System.arraycopy(fArr, 0, fArr2, 0, 2);
            }
            return fArr2;
        }

        private static float[] computeXYZMatrix(float[] fArr, float[] fArr2) {
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float f4 = fArr[3];
            float f5 = fArr[4];
            float f6 = fArr[5];
            float f7 = fArr2[0];
            float f8 = fArr2[1];
            float f9 = 1.0f - f;
            float f10 = f9 / f2;
            float f11 = 1.0f - f3;
            float f12 = 1.0f - f5;
            float f13 = (1.0f - f7) / f8;
            float f14 = f / f2;
            float f15 = (f3 / f4) - f14;
            float f16 = (f7 / f8) - f14;
            float f17 = (f11 / f4) - f10;
            float f18 = (f5 / f6) - f14;
            float f19 = (((f13 - f10) * f15) - (f16 * f17)) / ((((f12 / f6) - f10) * f15) - (f17 * f18));
            float f20 = (f16 - (f18 * f19)) / f15;
            float f21 = (1.0f - f20) - f19;
            float f22 = f21 / f2;
            float f23 = f20 / f4;
            float f24 = f19 / f6;
            return new float[]{f * f22, f21, f22 * (f9 - f2), f3 * f23, f20, f23 * (f11 - f4), f5 * f24, f19, f24 * (f12 - f6)};
        }
    }

    public static class Connector {
        private final android.graphics.ColorSpace mDestination;
        private final android.graphics.ColorSpace.RenderIntent mIntent;
        private final android.graphics.ColorSpace mSource;
        private final float[] mTransform;
        private final android.graphics.ColorSpace mTransformDestination;
        private final android.graphics.ColorSpace mTransformSource;

        Connector(android.graphics.ColorSpace colorSpace, android.graphics.ColorSpace colorSpace2, android.graphics.ColorSpace.RenderIntent renderIntent) {
            this(colorSpace, colorSpace2, colorSpace.getModel() == android.graphics.ColorSpace.Model.RGB ? android.graphics.ColorSpace.adapt(colorSpace, android.graphics.ColorSpace.ILLUMINANT_D50_XYZ) : colorSpace, colorSpace2.getModel() == android.graphics.ColorSpace.Model.RGB ? android.graphics.ColorSpace.adapt(colorSpace2, android.graphics.ColorSpace.ILLUMINANT_D50_XYZ) : colorSpace2, renderIntent, computeTransform(colorSpace, colorSpace2, renderIntent));
        }

        private Connector(android.graphics.ColorSpace colorSpace, android.graphics.ColorSpace colorSpace2, android.graphics.ColorSpace colorSpace3, android.graphics.ColorSpace colorSpace4, android.graphics.ColorSpace.RenderIntent renderIntent, float[] fArr) {
            this.mSource = colorSpace;
            this.mDestination = colorSpace2;
            this.mTransformSource = colorSpace3;
            this.mTransformDestination = colorSpace4;
            this.mIntent = renderIntent;
            this.mTransform = fArr;
        }

        private static float[] computeTransform(android.graphics.ColorSpace colorSpace, android.graphics.ColorSpace colorSpace2, android.graphics.ColorSpace.RenderIntent renderIntent) {
            if (renderIntent != android.graphics.ColorSpace.RenderIntent.ABSOLUTE) {
                return null;
            }
            boolean z = colorSpace.getModel() == android.graphics.ColorSpace.Model.RGB;
            boolean z2 = colorSpace2.getModel() == android.graphics.ColorSpace.Model.RGB;
            if (z && z2) {
                return null;
            }
            if (!z && !z2) {
                return null;
            }
            if (!z) {
                colorSpace = colorSpace2;
            }
            android.graphics.ColorSpace.Rgb rgb = (android.graphics.ColorSpace.Rgb) colorSpace;
            float[] xyYToXyz = z ? android.graphics.ColorSpace.xyYToXyz(rgb.mWhitePoint) : android.graphics.ColorSpace.ILLUMINANT_D50_XYZ;
            float[] xyYToXyz2 = z2 ? android.graphics.ColorSpace.xyYToXyz(rgb.mWhitePoint) : android.graphics.ColorSpace.ILLUMINANT_D50_XYZ;
            return new float[]{xyYToXyz[0] / xyYToXyz2[0], xyYToXyz[1] / xyYToXyz2[1], xyYToXyz[2] / xyYToXyz2[2]};
        }

        public android.graphics.ColorSpace getSource() {
            return this.mSource;
        }

        public android.graphics.ColorSpace getDestination() {
            return this.mDestination;
        }

        public android.graphics.ColorSpace.RenderIntent getRenderIntent() {
            return this.mIntent;
        }

        public float[] transform(float f, float f2, float f3) {
            return transform(new float[]{f, f2, f3});
        }

        public float[] transform(float[] fArr) {
            float[] xyz = this.mTransformSource.toXyz(fArr);
            if (this.mTransform != null) {
                xyz[0] = xyz[0] * this.mTransform[0];
                xyz[1] = xyz[1] * this.mTransform[1];
                xyz[2] = xyz[2] * this.mTransform[2];
            }
            return this.mTransformDestination.fromXyz(xyz);
        }

        private static class Rgb extends android.graphics.ColorSpace.Connector {
            private final android.graphics.ColorSpace.Rgb mDestination;
            private final android.graphics.ColorSpace.Rgb mSource;
            private final float[] mTransform;

            Rgb(android.graphics.ColorSpace.Rgb rgb, android.graphics.ColorSpace.Rgb rgb2, android.graphics.ColorSpace.RenderIntent renderIntent) {
                super(rgb2, rgb, rgb2, renderIntent, null);
                this.mSource = rgb;
                this.mDestination = rgb2;
                this.mTransform = computeTransform(rgb, rgb2, renderIntent);
            }

            @Override // android.graphics.ColorSpace.Connector
            public float[] transform(float[] fArr) {
                fArr[0] = (float) this.mSource.mClampedEotf.applyAsDouble(fArr[0]);
                fArr[1] = (float) this.mSource.mClampedEotf.applyAsDouble(fArr[1]);
                fArr[2] = (float) this.mSource.mClampedEotf.applyAsDouble(fArr[2]);
                android.graphics.ColorSpace.mul3x3Float3(this.mTransform, fArr);
                fArr[0] = (float) this.mDestination.mClampedOetf.applyAsDouble(fArr[0]);
                fArr[1] = (float) this.mDestination.mClampedOetf.applyAsDouble(fArr[1]);
                fArr[2] = (float) this.mDestination.mClampedOetf.applyAsDouble(fArr[2]);
                return fArr;
            }

            private static float[] computeTransform(android.graphics.ColorSpace.Rgb rgb, android.graphics.ColorSpace.Rgb rgb2, android.graphics.ColorSpace.RenderIntent renderIntent) {
                if (android.graphics.ColorSpace.compare(rgb.mWhitePoint, rgb2.mWhitePoint)) {
                    return android.graphics.ColorSpace.mul3x3(rgb2.mInverseTransform, rgb.mTransform);
                }
                float[] fArr = rgb.mTransform;
                float[] fArr2 = rgb2.mInverseTransform;
                float[] xyYToXyz = android.graphics.ColorSpace.xyYToXyz(rgb.mWhitePoint);
                float[] xyYToXyz2 = android.graphics.ColorSpace.xyYToXyz(rgb2.mWhitePoint);
                if (!android.graphics.ColorSpace.compare(rgb.mWhitePoint, android.graphics.ColorSpace.ILLUMINANT_D50)) {
                    fArr = android.graphics.ColorSpace.mul3x3(android.graphics.ColorSpace.chromaticAdaptation(android.graphics.ColorSpace.Adaptation.BRADFORD.mTransform, xyYToXyz, java.util.Arrays.copyOf(android.graphics.ColorSpace.ILLUMINANT_D50_XYZ, 3)), rgb.mTransform);
                }
                if (!android.graphics.ColorSpace.compare(rgb2.mWhitePoint, android.graphics.ColorSpace.ILLUMINANT_D50)) {
                    fArr2 = android.graphics.ColorSpace.inverse3x3(android.graphics.ColorSpace.mul3x3(android.graphics.ColorSpace.chromaticAdaptation(android.graphics.ColorSpace.Adaptation.BRADFORD.mTransform, xyYToXyz2, java.util.Arrays.copyOf(android.graphics.ColorSpace.ILLUMINANT_D50_XYZ, 3)), rgb2.mTransform));
                }
                if (renderIntent == android.graphics.ColorSpace.RenderIntent.ABSOLUTE) {
                    fArr = android.graphics.ColorSpace.mul3x3Diag(new float[]{xyYToXyz[0] / xyYToXyz2[0], xyYToXyz[1] / xyYToXyz2[1], xyYToXyz[2] / xyYToXyz2[2]}, fArr);
                }
                return android.graphics.ColorSpace.mul3x3(fArr2, fArr);
            }
        }

        static android.graphics.ColorSpace.Connector identity(android.graphics.ColorSpace colorSpace) {
            return new android.graphics.ColorSpace.Connector(colorSpace, colorSpace, android.graphics.ColorSpace.RenderIntent.RELATIVE) { // from class: android.graphics.ColorSpace.Connector.1
                @Override // android.graphics.ColorSpace.Connector
                public float[] transform(float[] fArr) {
                    return fArr;
                }
            };
        }
    }
}
