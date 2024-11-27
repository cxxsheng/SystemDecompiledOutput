package com.google.android.mms.util;

/* loaded from: classes5.dex */
public class DrmConvertSession {
    public static final int STATUS_FILE_ERROR = 492;
    public static final int STATUS_NOT_ACCEPTABLE = 406;
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_UNKNOWN_ERROR = 491;
    private static final java.lang.String TAG = "DrmConvertSession";
    private int mConvertSessionId;
    private android.drm.DrmManagerClient mDrmClient;

    private DrmConvertSession(android.drm.DrmManagerClient drmManagerClient, int i) {
        this.mDrmClient = drmManagerClient;
        this.mConvertSessionId = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0058 A[ADDED_TO_REGION] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.drm.DrmManagerClient] */
    /* JADX WARN: Type inference failed for: r3v10, types: [android.drm.DrmManagerClient] */
    /* JADX WARN: Type inference failed for: r3v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.google.android.mms.util.DrmConvertSession open(android.content.Context context, java.lang.String str) {
        ?? r3;
        int i = -1;
        if (context != null && str != null && (r3 = str.equals("")) == 0) {
            try {
                try {
                    r3 = new android.drm.DrmManagerClient(context);
                } catch (java.lang.IllegalArgumentException e) {
                    r3 = 0;
                    android.util.Log.w(TAG, "DrmManagerClient instance could not be created, context is Illegal.");
                    if (r3 != 0) {
                    }
                    return null;
                } catch (java.lang.IllegalStateException e2) {
                    r3 = 0;
                    android.util.Log.w(TAG, "DrmManagerClient didn't initialize properly.");
                    if (r3 != 0) {
                    }
                    return null;
                }
                try {
                    i = r3.openConvertSession(str);
                } catch (java.lang.IllegalArgumentException e3) {
                    android.util.Log.w(TAG, "Conversion of Mimetype: " + str + " is not supported.", e3);
                    if (r3 != 0) {
                    }
                    return null;
                } catch (java.lang.IllegalStateException e4) {
                    android.util.Log.w(TAG, "Could not access Open DrmFramework.", e4);
                    if (r3 != 0) {
                    }
                    return null;
                }
            } catch (java.lang.IllegalArgumentException e5) {
                android.util.Log.w(TAG, "DrmManagerClient instance could not be created, context is Illegal.");
                if (r3 != 0) {
                }
                return null;
            } catch (java.lang.IllegalStateException e6) {
                android.util.Log.w(TAG, "DrmManagerClient didn't initialize properly.");
                if (r3 != 0) {
                }
                return null;
            }
        } else {
            r3 = 0;
        }
        if (r3 != 0 || i < 0) {
            return null;
        }
        return new com.google.android.mms.util.DrmConvertSession(r3, i);
    }

    public byte[] convert(byte[] bArr, int i) {
        android.drm.DrmConvertedStatus convertData;
        if (bArr != null) {
            try {
                if (i != bArr.length) {
                    byte[] bArr2 = new byte[i];
                    java.lang.System.arraycopy(bArr, 0, bArr2, 0, i);
                    convertData = this.mDrmClient.convertData(this.mConvertSessionId, bArr2);
                } else {
                    convertData = this.mDrmClient.convertData(this.mConvertSessionId, bArr);
                }
                if (convertData == null || convertData.statusCode != 1 || convertData.convertedData == null) {
                    return null;
                }
                return convertData.convertedData;
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.w(TAG, "Buffer with data to convert is illegal. Convertsession: " + this.mConvertSessionId, e);
                return null;
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.w(TAG, "Could not convert data. Convertsession: " + this.mConvertSessionId, e2);
                return null;
            }
        }
        throw new java.lang.IllegalArgumentException("Parameter inBuffer is null");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int close(java.lang.String str) {
        java.lang.String str2;
        java.io.RandomAccessFile randomAccessFile;
        int i;
        int i2 = 491;
        if (this.mDrmClient == null || this.mConvertSessionId < 0) {
            return 491;
        }
        try {
            android.drm.DrmConvertedStatus closeConvertSession = this.mDrmClient.closeConvertSession(this.mConvertSessionId);
            if (closeConvertSession == null || closeConvertSession.statusCode != 1 || closeConvertSession.convertedData == null) {
                return 406;
            }
            java.io.RandomAccessFile randomAccessFile2 = null;
            java.io.RandomAccessFile randomAccessFile3 = null;
            java.io.RandomAccessFile randomAccessFile4 = null;
            java.io.RandomAccessFile randomAccessFile5 = null;
            java.io.RandomAccessFile randomAccessFile6 = null;
            randomAccessFile2 = null;
            try {
                try {
                    try {
                        try {
                            randomAccessFile = new java.io.RandomAccessFile(str, "rw");
                            try {
                                i = closeConvertSession.offset;
                                randomAccessFile.seek(i);
                                randomAccessFile.write(closeConvertSession.convertedData);
                            } catch (java.io.FileNotFoundException e) {
                                e = e;
                                randomAccessFile3 = randomAccessFile;
                                android.util.Log.w(TAG, "File: " + str + " could not be found.", e);
                                randomAccessFile2 = randomAccessFile3;
                                if (randomAccessFile3 != null) {
                                    try {
                                        randomAccessFile3.close();
                                        randomAccessFile2 = randomAccessFile3;
                                    } catch (java.io.IOException e2) {
                                        e = e2;
                                        str2 = "Failed to close File:" + str + android.media.MediaMetrics.SEPARATOR;
                                        android.util.Log.w(TAG, str2, e);
                                        return 492;
                                    }
                                }
                                return 492;
                            } catch (java.io.IOException e3) {
                                e = e3;
                                randomAccessFile4 = randomAccessFile;
                                android.util.Log.w(TAG, "Could not access File: " + str + " .", e);
                                randomAccessFile2 = randomAccessFile4;
                                if (randomAccessFile4 != null) {
                                    try {
                                        randomAccessFile4.close();
                                        randomAccessFile2 = randomAccessFile4;
                                    } catch (java.io.IOException e4) {
                                        e = e4;
                                        str2 = "Failed to close File:" + str + android.media.MediaMetrics.SEPARATOR;
                                        android.util.Log.w(TAG, str2, e);
                                        return 492;
                                    }
                                }
                                return 492;
                            } catch (java.lang.IllegalArgumentException e5) {
                                e = e5;
                                randomAccessFile5 = randomAccessFile;
                                android.util.Log.w(TAG, "Could not open file in mode: rw", e);
                                randomAccessFile2 = randomAccessFile5;
                                if (randomAccessFile5 != null) {
                                    try {
                                        randomAccessFile5.close();
                                        randomAccessFile2 = randomAccessFile5;
                                    } catch (java.io.IOException e6) {
                                        e = e6;
                                        str2 = "Failed to close File:" + str + android.media.MediaMetrics.SEPARATOR;
                                        android.util.Log.w(TAG, str2, e);
                                        return 492;
                                    }
                                }
                                return 492;
                            } catch (java.lang.SecurityException e7) {
                                e = e7;
                                randomAccessFile6 = randomAccessFile;
                                android.util.Log.w(TAG, "Access to File: " + str + " was denied denied by SecurityManager.", e);
                                randomAccessFile2 = randomAccessFile6;
                                if (randomAccessFile6 != null) {
                                    try {
                                        randomAccessFile6.close();
                                        randomAccessFile2 = randomAccessFile6;
                                    } catch (java.io.IOException e8) {
                                        e = e8;
                                        str2 = "Failed to close File:" + str + android.media.MediaMetrics.SEPARATOR;
                                        android.util.Log.w(TAG, str2, e);
                                        return 492;
                                    }
                                }
                                return i2;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                randomAccessFile2 = randomAccessFile;
                                if (randomAccessFile2 != null) {
                                    try {
                                        randomAccessFile2.close();
                                    } catch (java.io.IOException e9) {
                                        android.util.Log.w(TAG, "Failed to close File:" + str + android.media.MediaMetrics.SEPARATOR, e9);
                                        throw th;
                                    }
                                }
                                throw th;
                            }
                        } catch (java.io.FileNotFoundException e10) {
                            e = e10;
                        } catch (java.io.IOException e11) {
                            e = e11;
                        } catch (java.lang.IllegalArgumentException e12) {
                            e = e12;
                        } catch (java.lang.SecurityException e13) {
                            e = e13;
                        }
                        try {
                            randomAccessFile.close();
                            i2 = 200;
                            randomAccessFile2 = i;
                            return i2;
                        } catch (java.io.IOException e14) {
                            e = e14;
                            str2 = "Failed to close File:" + str + android.media.MediaMetrics.SEPARATOR;
                            android.util.Log.w(TAG, str2, e);
                            return 492;
                        } catch (java.lang.IllegalStateException e15) {
                            e = e15;
                            i2 = 200;
                            android.util.Log.w(TAG, "Could not close convertsession. Convertsession: " + this.mConvertSessionId, e);
                            return i2;
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        i2 = 492;
                    }
                } catch (java.lang.IllegalStateException e16) {
                    e = e16;
                    i2 = 492;
                }
            } catch (java.lang.Throwable th3) {
                th = th3;
            }
        } catch (java.lang.IllegalStateException e17) {
            e = e17;
        }
    }
}
