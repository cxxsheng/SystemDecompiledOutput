package android.util;

/* loaded from: classes3.dex */
public class PathParser {
    static final java.lang.String LOGTAG = android.util.PathParser.class.getSimpleName();

    @dalvik.annotation.optimization.FastNative
    private static native boolean nCanMorph(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreateEmptyPathData();

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native long nCreatePathData(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nCreatePathDataFromString(java.lang.String str, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nCreatePathFromPathData(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nFinalize(long j);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nInterpolatePathData(long j, long j2, long j3, float f);

    private static native void nParseStringForPath(long j, java.lang.String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native void nSetPathData(long j, long j2);

    public static android.graphics.Path createPathFromPathData(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Path string can not be null.");
        }
        android.graphics.Path path = new android.graphics.Path();
        nParseStringForPath(path.mNativePath, str, str.length());
        return path;
    }

    public static void createPathFromPathData(android.graphics.Path path, android.util.PathParser.PathData pathData) {
        nCreatePathFromPathData(path.mNativePath, pathData.mNativePathData);
    }

    public static boolean canMorph(android.util.PathParser.PathData pathData, android.util.PathParser.PathData pathData2) {
        return nCanMorph(pathData.mNativePathData, pathData2.mNativePathData);
    }

    public static class PathData {
        long mNativePathData;

        public PathData() {
            this.mNativePathData = 0L;
            this.mNativePathData = android.util.PathParser.nCreateEmptyPathData();
        }

        public PathData(android.util.PathParser.PathData pathData) {
            this.mNativePathData = 0L;
            this.mNativePathData = android.util.PathParser.nCreatePathData(pathData.mNativePathData);
        }

        public PathData(java.lang.String str) {
            this.mNativePathData = 0L;
            this.mNativePathData = android.util.PathParser.nCreatePathDataFromString(str, str.length());
            if (this.mNativePathData == 0) {
                throw new java.lang.IllegalArgumentException("Invalid pathData: " + str);
            }
        }

        public long getNativePtr() {
            return this.mNativePathData;
        }

        public void setPathData(android.util.PathParser.PathData pathData) {
            android.util.PathParser.nSetPathData(this.mNativePathData, pathData.mNativePathData);
        }

        protected void finalize() throws java.lang.Throwable {
            if (this.mNativePathData != 0) {
                android.util.PathParser.nFinalize(this.mNativePathData);
                this.mNativePathData = 0L;
            }
            super.finalize();
        }
    }

    public static boolean interpolatePathData(android.util.PathParser.PathData pathData, android.util.PathParser.PathData pathData2, android.util.PathParser.PathData pathData3, float f) {
        return nInterpolatePathData(pathData.mNativePathData, pathData2.mNativePathData, pathData3.mNativePathData, f);
    }
}
