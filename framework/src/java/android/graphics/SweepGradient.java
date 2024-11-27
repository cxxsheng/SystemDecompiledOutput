package android.graphics;

/* loaded from: classes.dex */
public class SweepGradient extends android.graphics.Shader {
    private int mColor0;
    private int mColor1;
    private final long[] mColorLongs;
    private int[] mColors;
    private float mCx;
    private float mCy;
    private float[] mPositions;

    private static native long nativeCreate(long j, float f, float f2, long[] jArr, float[] fArr, long j2);

    public SweepGradient(float f, float f2, int[] iArr, float[] fArr) {
        this(f, f2, convertColors(iArr), fArr, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
    }

    public SweepGradient(float f, float f2, long[] jArr, float[] fArr) {
        this(f, f2, (long[]) jArr.clone(), fArr, detectColorSpace(jArr));
    }

    private SweepGradient(float f, float f2, long[] jArr, float[] fArr, android.graphics.ColorSpace colorSpace) {
        super(colorSpace);
        if (fArr != null && jArr.length != fArr.length) {
            throw new java.lang.IllegalArgumentException("color and position arrays must be of equal length");
        }
        this.mCx = f;
        this.mCy = f2;
        this.mColorLongs = jArr;
        this.mPositions = fArr != null ? (float[]) fArr.clone() : null;
    }

    public SweepGradient(float f, float f2, int i, int i2) {
        this(f, f2, android.graphics.Color.pack(i), android.graphics.Color.pack(i2));
    }

    public SweepGradient(float f, float f2, long j, long j2) {
        this(f, f2, new long[]{j, j2}, (float[]) null);
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        return nativeCreate(j, this.mCx, this.mCy, this.mColorLongs, this.mPositions, colorSpace().getNativeInstance());
    }
}
