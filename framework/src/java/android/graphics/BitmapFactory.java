package android.graphics;

/* loaded from: classes.dex */
public class BitmapFactory {
    private static final int DECODE_BUFFER_SIZE = 16384;

    private static native android.graphics.Bitmap nativeDecodeAsset(long j, android.graphics.Rect rect, android.graphics.BitmapFactory.Options options, long j2, long j3);

    private static native android.graphics.Bitmap nativeDecodeByteArray(byte[] bArr, int i, int i2, android.graphics.BitmapFactory.Options options, long j, long j2);

    private static native android.graphics.Bitmap nativeDecodeFileDescriptor(java.io.FileDescriptor fileDescriptor, android.graphics.Rect rect, android.graphics.BitmapFactory.Options options, long j, long j2);

    private static native android.graphics.Bitmap nativeDecodeStream(java.io.InputStream inputStream, byte[] bArr, android.graphics.Rect rect, android.graphics.BitmapFactory.Options options, long j, long j2);

    private static native boolean nativeIsSeekable(java.io.FileDescriptor fileDescriptor);

    public static class Options {
        public android.graphics.Bitmap inBitmap;
        public int inDensity;
        public boolean inDither;

        @java.lang.Deprecated
        public boolean inInputShareable;
        public boolean inJustDecodeBounds;
        public boolean inMutable;

        @java.lang.Deprecated
        public boolean inPreferQualityOverSpeed;

        @java.lang.Deprecated
        public boolean inPurgeable;
        public int inSampleSize;
        public int inScreenDensity;
        public int inTargetDensity;
        public byte[] inTempStorage;

        @java.lang.Deprecated
        public boolean mCancel;
        public android.graphics.ColorSpace outColorSpace;
        public android.graphics.Bitmap.Config outConfig;
        public int outHeight;
        public java.lang.String outMimeType;
        public int outWidth;
        public android.graphics.Bitmap.Config inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888;
        public android.graphics.ColorSpace inPreferredColorSpace = null;
        public boolean inScaled = true;
        public boolean inPremultiplied = true;

        @java.lang.Deprecated
        public void requestCancelDecode() {
            this.mCancel = true;
        }

        static void validate(android.graphics.BitmapFactory.Options options) {
            if (options == null) {
                return;
            }
            if (options.inBitmap != null) {
                if (options.inBitmap.getConfig() == android.graphics.Bitmap.Config.HARDWARE) {
                    throw new java.lang.IllegalArgumentException("Bitmaps with Config.HARDWARE are always immutable");
                }
                if (options.inBitmap.isRecycled()) {
                    throw new java.lang.IllegalArgumentException("Cannot reuse a recycled Bitmap");
                }
            }
            if (options.inMutable && options.inPreferredConfig == android.graphics.Bitmap.Config.HARDWARE) {
                throw new java.lang.IllegalArgumentException("Bitmaps with Config.HARDWARE cannot be decoded into - they are immutable");
            }
            if (options.inPreferredColorSpace != null) {
                if (!(options.inPreferredColorSpace instanceof android.graphics.ColorSpace.Rgb)) {
                    throw new java.lang.IllegalArgumentException("The destination color space must use the RGB color model");
                }
                if (!options.inPreferredColorSpace.equals(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.BT2020_HLG)) && !options.inPreferredColorSpace.equals(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.BT2020_PQ)) && ((android.graphics.ColorSpace.Rgb) options.inPreferredColorSpace).getTransferParameters() == null) {
                    throw new java.lang.IllegalArgumentException("The destination color space must use an ICC parametric transfer function");
                }
            }
        }

        static long nativeInBitmap(android.graphics.BitmapFactory.Options options) {
            if (options == null || options.inBitmap == null) {
                return 0L;
            }
            options.inBitmap.setGainmap(null);
            return options.inBitmap.getNativeInstance();
        }

        static long nativeColorSpace(android.graphics.BitmapFactory.Options options) {
            if (options == null || options.inPreferredColorSpace == null) {
                return 0L;
            }
            return options.inPreferredColorSpace.getNativeInstance();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.graphics.Bitmap decodeFile(java.lang.String str, android.graphics.BitmapFactory.Options options) {
        java.io.FileDescriptor fileDescriptor;
        android.graphics.BitmapFactory.Options.validate(options);
        android.graphics.Bitmap bitmap = null;
        bitmap = null;
        bitmap = null;
        java.io.FileDescriptor fileDescriptor2 = null;
        try {
            try {
                fileDescriptor = libcore.io.IoBridge.open(str, android.system.OsConstants.O_RDONLY);
            } catch (java.lang.Exception e) {
                e = e;
                fileDescriptor = null;
            } catch (java.lang.Throwable th) {
                th = th;
                if (fileDescriptor2 != null) {
                }
                throw th;
            }
            try {
                try {
                    bitmap = decodeFileDescriptor(fileDescriptor, null, options);
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    fileDescriptor2 = fileDescriptor;
                    if (fileDescriptor2 != null) {
                        try {
                            libcore.io.IoBridge.closeAndSignalBlockedThreads(fileDescriptor2);
                        } catch (java.io.IOException e2) {
                        }
                    }
                    throw th;
                }
            } catch (java.lang.Exception e3) {
                e = e3;
                android.util.Log.e("BitmapFactory", "Unable to decode file: " + e);
                if (fileDescriptor != null) {
                    libcore.io.IoBridge.closeAndSignalBlockedThreads(fileDescriptor);
                }
                return bitmap;
            }
            if (fileDescriptor != null) {
                libcore.io.IoBridge.closeAndSignalBlockedThreads(fileDescriptor);
            }
        } catch (java.io.IOException e4) {
        }
        return bitmap;
    }

    public static android.graphics.Bitmap decodeFile(java.lang.String str) {
        return decodeFile(str, null);
    }

    public static android.graphics.Bitmap decodeResourceStream(android.content.res.Resources resources, android.util.TypedValue typedValue, java.io.InputStream inputStream, android.graphics.Rect rect, android.graphics.BitmapFactory.Options options) {
        android.graphics.BitmapFactory.Options.validate(options);
        if (options == null) {
            options = new android.graphics.BitmapFactory.Options();
        }
        if (options.inDensity == 0 && typedValue != null) {
            int i = typedValue.density;
            if (i == 0) {
                options.inDensity = 160;
            } else if (i != 65535) {
                options.inDensity = i;
            }
        }
        if (options.inTargetDensity == 0 && resources != null) {
            options.inTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        return decodeStream(inputStream, rect, options);
    }

    public static android.graphics.Bitmap decodeResource(android.content.res.Resources resources, int i, android.graphics.BitmapFactory.Options options) {
        java.io.InputStream inputStream;
        android.util.TypedValue typedValue;
        android.graphics.BitmapFactory.Options.validate(options);
        android.graphics.Bitmap bitmap = null;
        bitmap = null;
        bitmap = null;
        java.io.InputStream inputStream2 = null;
        try {
            try {
                typedValue = new android.util.TypedValue();
                inputStream = resources.openRawResource(i, typedValue);
            } catch (java.lang.Exception e) {
                inputStream = null;
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                bitmap = decodeResourceStream(resources, typedValue, inputStream, null, options);
            } catch (java.lang.Exception e2) {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bitmap != null) {
                }
                return bitmap;
            } catch (java.lang.Throwable th2) {
                th = th2;
                inputStream2 = inputStream;
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (java.io.IOException e3) {
                    }
                }
                throw th;
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (java.io.IOException e4) {
        }
        if (bitmap != null && options != null && options.inBitmap != null) {
            throw new java.lang.IllegalArgumentException("Problem decoding into existing bitmap");
        }
        return bitmap;
    }

    public static android.graphics.Bitmap decodeResource(android.content.res.Resources resources, int i) {
        return decodeResource(resources, i, null);
    }

    public static android.graphics.Bitmap decodeByteArray(byte[] bArr, int i, int i2, android.graphics.BitmapFactory.Options options) {
        if ((i | i2) < 0 || bArr.length < i + i2) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        android.graphics.BitmapFactory.Options.validate(options);
        android.os.Trace.traceBegin(2L, "decodeBitmap");
        try {
            android.graphics.Bitmap nativeDecodeByteArray = nativeDecodeByteArray(bArr, i, i2, options, android.graphics.BitmapFactory.Options.nativeInBitmap(options), android.graphics.BitmapFactory.Options.nativeColorSpace(options));
            if (nativeDecodeByteArray == null && options != null && options.inBitmap != null) {
                throw new java.lang.IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(nativeDecodeByteArray, options);
            return nativeDecodeByteArray;
        } finally {
            android.os.Trace.traceEnd(2L);
        }
    }

    public static android.graphics.Bitmap decodeByteArray(byte[] bArr, int i, int i2) {
        return decodeByteArray(bArr, i, i2, null);
    }

    private static void setDensityFromOptions(android.graphics.Bitmap bitmap, android.graphics.BitmapFactory.Options options) {
        if (bitmap == null || options == null) {
            return;
        }
        int i = options.inDensity;
        if (i != 0) {
            bitmap.setDensity(i);
            int i2 = options.inTargetDensity;
            if (i2 == 0 || i == i2 || i == options.inScreenDensity) {
                return;
            }
            byte[] ninePatchChunk = bitmap.getNinePatchChunk();
            boolean z = ninePatchChunk != null && android.graphics.NinePatch.isNinePatchChunk(ninePatchChunk);
            if (options.inScaled || z) {
                bitmap.setDensity(i2);
                return;
            }
            return;
        }
        if (options.inBitmap != null) {
            bitmap.setDensity(android.graphics.Bitmap.getDefaultDensity());
        }
    }

    public static android.graphics.Bitmap decodeStream(java.io.InputStream inputStream, android.graphics.Rect rect, android.graphics.BitmapFactory.Options options) {
        android.graphics.Bitmap decodeStreamInternal;
        if (inputStream == null) {
            return null;
        }
        android.graphics.BitmapFactory.Options.validate(options);
        android.os.Trace.traceBegin(2L, "decodeBitmap");
        try {
            if (inputStream instanceof android.content.res.AssetManager.AssetInputStream) {
                decodeStreamInternal = nativeDecodeAsset(((android.content.res.AssetManager.AssetInputStream) inputStream).getNativeAsset(), rect, options, android.graphics.BitmapFactory.Options.nativeInBitmap(options), android.graphics.BitmapFactory.Options.nativeColorSpace(options));
            } else {
                decodeStreamInternal = decodeStreamInternal(inputStream, rect, options);
            }
            if (decodeStreamInternal == null && options != null && options.inBitmap != null) {
                throw new java.lang.IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(decodeStreamInternal, options);
            return decodeStreamInternal;
        } finally {
            android.os.Trace.traceEnd(2L);
        }
    }

    private static android.graphics.Bitmap decodeStreamInternal(java.io.InputStream inputStream, android.graphics.Rect rect, android.graphics.BitmapFactory.Options options) {
        byte[] bArr = options != null ? options.inTempStorage : null;
        if (bArr == null) {
            bArr = new byte[16384];
        }
        return nativeDecodeStream(inputStream, bArr, rect, options, android.graphics.BitmapFactory.Options.nativeInBitmap(options), android.graphics.BitmapFactory.Options.nativeColorSpace(options));
    }

    public static android.graphics.Bitmap decodeStream(java.io.InputStream inputStream) {
        return decodeStream(inputStream, null, null);
    }

    public static android.graphics.Bitmap decodeFileDescriptor(java.io.FileDescriptor fileDescriptor, android.graphics.Rect rect, android.graphics.BitmapFactory.Options options) {
        android.graphics.Bitmap decodeStreamInternal;
        android.graphics.BitmapFactory.Options.validate(options);
        android.os.Trace.traceBegin(2L, "decodeFileDescriptor");
        try {
            if (nativeIsSeekable(fileDescriptor)) {
                decodeStreamInternal = nativeDecodeFileDescriptor(fileDescriptor, rect, options, android.graphics.BitmapFactory.Options.nativeInBitmap(options), android.graphics.BitmapFactory.Options.nativeColorSpace(options));
            } else {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(fileDescriptor);
                try {
                    decodeStreamInternal = decodeStreamInternal(fileInputStream, rect, options);
                } finally {
                    try {
                        fileInputStream.close();
                    } catch (java.lang.Throwable th) {
                    }
                }
            }
            if (decodeStreamInternal == null && options != null && options.inBitmap != null) {
                throw new java.lang.IllegalArgumentException("Problem decoding into existing bitmap");
            }
            setDensityFromOptions(decodeStreamInternal, options);
            return decodeStreamInternal;
        } finally {
            android.os.Trace.traceEnd(2L);
        }
    }

    public static android.graphics.Bitmap decodeFileDescriptor(java.io.FileDescriptor fileDescriptor) {
        return decodeFileDescriptor(fileDescriptor, null, null);
    }
}
