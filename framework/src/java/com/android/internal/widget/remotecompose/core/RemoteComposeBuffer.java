package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public class RemoteComposeBuffer {
    com.android.internal.widget.remotecompose.core.WireBuffer mBuffer = new com.android.internal.widget.remotecompose.core.WireBuffer();
    com.android.internal.widget.remotecompose.core.Platform mPlatform = null;
    com.android.internal.widget.remotecompose.core.RemoteComposeState mRemoteComposeState;

    public RemoteComposeBuffer(com.android.internal.widget.remotecompose.core.RemoteComposeState remoteComposeState) {
        this.mRemoteComposeState = remoteComposeState;
    }

    public void reset(int i) {
        this.mBuffer.reset(i);
        this.mRemoteComposeState.reset();
    }

    public com.android.internal.widget.remotecompose.core.Platform getPlatform() {
        return this.mPlatform;
    }

    public void setPlatform(com.android.internal.widget.remotecompose.core.Platform platform) {
        this.mPlatform = platform;
    }

    public com.android.internal.widget.remotecompose.core.WireBuffer getBuffer() {
        return this.mBuffer;
    }

    public void setBuffer(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer) {
        this.mBuffer = wireBuffer;
    }

    public void header(int i, int i2, java.lang.String str, long j) {
        com.android.internal.widget.remotecompose.core.operations.Header.COMPANION.apply(this.mBuffer, i, i2, j);
        if (str != null) {
            com.android.internal.widget.remotecompose.core.operations.RootContentDescription.COMPANION.apply(this.mBuffer, addText(str));
        }
    }

    public void header(int i, int i2, java.lang.String str) {
        header(i, i2, str, 0L);
    }

    public void drawBitmap(java.lang.Object obj, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, java.lang.String str) {
        int i11;
        int i12;
        int dataGetId = this.mRemoteComposeState.dataGetId(obj);
        if (dataGetId != -1) {
            i11 = dataGetId;
        } else {
            int cache = this.mRemoteComposeState.cache(obj);
            com.android.internal.widget.remotecompose.core.operations.BitmapData.COMPANION.apply(this.mBuffer, cache, i, i2, this.mPlatform.imageToByteArray(obj));
            i11 = cache;
        }
        if (str == null) {
            i12 = 0;
        } else {
            i12 = addText(str);
        }
        com.android.internal.widget.remotecompose.core.operations.DrawBitmapInt.COMPANION.apply(this.mBuffer, i11, i3, i4, i5, i6, i7, i8, i9, i10, i12);
    }

    int addText(java.lang.String str) {
        int dataGetId = this.mRemoteComposeState.dataGetId(str);
        if (dataGetId == -1) {
            int cache = this.mRemoteComposeState.cache(str);
            com.android.internal.widget.remotecompose.core.operations.TextData.COMPANION.apply(this.mBuffer, cache, str);
            return cache;
        }
        return dataGetId;
    }

    public void addClickArea(int i, java.lang.String str, float f, float f2, float f3, float f4, java.lang.String str2) {
        int i2;
        int i3;
        if (str == null) {
            i2 = 0;
        } else {
            i2 = addText(str);
        }
        if (str2 == null) {
            i3 = 0;
        } else {
            i3 = addText(str2);
        }
        com.android.internal.widget.remotecompose.core.operations.ClickArea.COMPANION.apply(this.mBuffer, i, i2, f, f2, f3, f4, i3);
    }

    public void setRootContentBehavior(int i, int i2, int i3, int i4) {
        com.android.internal.widget.remotecompose.core.operations.RootContentBehavior.COMPANION.apply(this.mBuffer, i, i2, i3, i4);
    }

    public void inflateFromBuffer(java.util.ArrayList<com.android.internal.widget.remotecompose.core.Operation> arrayList) {
        this.mBuffer.setIndex(0);
        while (this.mBuffer.available()) {
            com.android.internal.widget.remotecompose.core.CompanionOperation companionOperation = com.android.internal.widget.remotecompose.core.Operations.map.get(this.mBuffer.readByte());
            if (companionOperation == null) {
                throw new java.lang.RuntimeException("Unknown operation encountered");
            }
            companionOperation.read(this.mBuffer, arrayList);
        }
    }

    com.android.internal.widget.remotecompose.core.RemoteComposeBuffer copy() {
        java.util.ArrayList<com.android.internal.widget.remotecompose.core.Operation> arrayList = new java.util.ArrayList<>();
        inflateFromBuffer(arrayList);
        return copyFromOperations(arrayList, new com.android.internal.widget.remotecompose.core.RemoteComposeBuffer(this.mRemoteComposeState));
    }

    public void setTheme(int i) {
        com.android.internal.widget.remotecompose.core.operations.Theme.COMPANION.apply(this.mBuffer, i);
    }

    static java.lang.String version() {
        return "v1.0";
    }

    public static com.android.internal.widget.remotecompose.core.RemoteComposeBuffer fromFile(java.lang.String str, com.android.internal.widget.remotecompose.core.RemoteComposeState remoteComposeState) throws java.io.IOException {
        com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer = new com.android.internal.widget.remotecompose.core.RemoteComposeBuffer(remoteComposeState);
        read(new java.io.File(str), remoteComposeBuffer);
        return remoteComposeBuffer;
    }

    public com.android.internal.widget.remotecompose.core.RemoteComposeBuffer fromFile(java.io.File file, com.android.internal.widget.remotecompose.core.RemoteComposeState remoteComposeState) throws java.io.IOException {
        com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer = new com.android.internal.widget.remotecompose.core.RemoteComposeBuffer(remoteComposeState);
        read(file, remoteComposeBuffer);
        return remoteComposeBuffer;
    }

    public static com.android.internal.widget.remotecompose.core.RemoteComposeBuffer fromInputStream(java.io.InputStream inputStream, com.android.internal.widget.remotecompose.core.RemoteComposeState remoteComposeState) {
        com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer = new com.android.internal.widget.remotecompose.core.RemoteComposeBuffer(remoteComposeState);
        read(inputStream, remoteComposeBuffer);
        return remoteComposeBuffer;
    }

    com.android.internal.widget.remotecompose.core.RemoteComposeBuffer copyFromOperations(java.util.ArrayList<com.android.internal.widget.remotecompose.core.Operation> arrayList, com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer) {
        java.util.Iterator<com.android.internal.widget.remotecompose.core.Operation> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().write(remoteComposeBuffer.mBuffer);
        }
        return remoteComposeBuffer;
    }

    public void write(com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer, java.io.File file) {
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
            fileOutputStream.write(remoteComposeBuffer.mBuffer.getBuffer(), 0, remoteComposeBuffer.mBuffer.getSize());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    static void read(java.io.File file, com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer) throws java.io.IOException {
        read(new java.io.FileInputStream(file), remoteComposeBuffer);
    }

    public static void read(java.io.InputStream inputStream, com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer) {
        try {
            byte[] readAllBytes = readAllBytes(inputStream);
            remoteComposeBuffer.reset(readAllBytes.length);
            java.lang.System.arraycopy(readAllBytes, 0, remoteComposeBuffer.mBuffer.mBuffer, 0, readAllBytes.length);
            remoteComposeBuffer.mBuffer.mSize = readAllBytes.length;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] readAllBytes(java.io.InputStream inputStream) throws java.io.IOException {
        byte[] bArr = new byte[32768];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr, i, bArr.length - i);
            if (read == -1) {
                inputStream.close();
                return java.util.Arrays.copyOf(bArr, i);
            }
            i += read;
            if (i == bArr.length) {
                bArr = java.util.Arrays.copyOf(bArr, bArr.length * 2);
            }
        }
    }
}
