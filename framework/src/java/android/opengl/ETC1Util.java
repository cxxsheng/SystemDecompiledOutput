package android.opengl;

/* loaded from: classes3.dex */
public class ETC1Util {
    public static void loadTexture(int i, int i2, int i3, int i4, int i5, java.io.InputStream inputStream) throws java.io.IOException {
        loadTexture(i, i2, i3, i4, i5, createTexture(inputStream));
    }

    public static void loadTexture(int i, int i2, int i3, int i4, int i5, android.opengl.ETC1Util.ETC1Texture eTC1Texture) {
        if (i4 != 6407) {
            throw new java.lang.IllegalArgumentException("fallbackFormat must be GL_RGB");
        }
        if (i5 != 33635 && i5 != 5121) {
            throw new java.lang.IllegalArgumentException("Unsupported fallbackType");
        }
        int width = eTC1Texture.getWidth();
        int height = eTC1Texture.getHeight();
        java.nio.ByteBuffer data = eTC1Texture.getData();
        if (isETC1Supported()) {
            android.opengl.GLES10.glCompressedTexImage2D(i, i2, 36196, width, height, i3, data.remaining(), data);
            return;
        }
        int i6 = i5 != 5121 ? 2 : 3;
        int i7 = i6 * width;
        java.nio.ByteBuffer order = java.nio.ByteBuffer.allocateDirect(i7 * height).order(java.nio.ByteOrder.nativeOrder());
        android.opengl.ETC1.decodeImage(data, order, width, height, i6, i7);
        android.opengl.GLES10.glTexImage2D(i, i2, i4, width, height, i3, i4, i5, order);
    }

    public static boolean isETC1Supported() {
        int[] iArr = new int[20];
        android.opengl.GLES10.glGetIntegerv(34466, iArr, 0);
        int i = iArr[0];
        if (i > 20) {
            iArr = new int[i];
        }
        android.opengl.GLES10.glGetIntegerv(34467, iArr, 0);
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] == 36196) {
                return true;
            }
        }
        return false;
    }

    public static class ETC1Texture {
        private java.nio.ByteBuffer mData;
        private int mHeight;
        private int mWidth;

        public ETC1Texture(int i, int i2, java.nio.ByteBuffer byteBuffer) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mData = byteBuffer;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public java.nio.ByteBuffer getData() {
            return this.mData;
        }
    }

    public static android.opengl.ETC1Util.ETC1Texture createTexture(java.io.InputStream inputStream) throws java.io.IOException {
        byte[] bArr = new byte[4096];
        if (inputStream.read(bArr, 0, 16) != 16) {
            throw new java.io.IOException("Unable to read PKM file header.");
        }
        java.nio.ByteBuffer order = java.nio.ByteBuffer.allocateDirect(16).order(java.nio.ByteOrder.nativeOrder());
        order.put(bArr, 0, 16).position(0);
        if (!android.opengl.ETC1.isValid(order)) {
            throw new java.io.IOException("Not a PKM file.");
        }
        int width = android.opengl.ETC1.getWidth(order);
        int height = android.opengl.ETC1.getHeight(order);
        int encodedDataSize = android.opengl.ETC1.getEncodedDataSize(width, height);
        java.nio.ByteBuffer order2 = java.nio.ByteBuffer.allocateDirect(encodedDataSize).order(java.nio.ByteOrder.nativeOrder());
        int i = 0;
        while (i < encodedDataSize) {
            int min = java.lang.Math.min(4096, encodedDataSize - i);
            if (inputStream.read(bArr, 0, min) != min) {
                throw new java.io.IOException("Unable to read PKM file data.");
            }
            order2.put(bArr, 0, min);
            i += min;
        }
        order2.position(0);
        return new android.opengl.ETC1Util.ETC1Texture(width, height, order2);
    }

    public static android.opengl.ETC1Util.ETC1Texture compressTexture(java.nio.Buffer buffer, int i, int i2, int i3, int i4) {
        java.nio.ByteBuffer order = java.nio.ByteBuffer.allocateDirect(android.opengl.ETC1.getEncodedDataSize(i, i2)).order(java.nio.ByteOrder.nativeOrder());
        android.opengl.ETC1.encodeImage(buffer, i, i2, i3, i4, order);
        return new android.opengl.ETC1Util.ETC1Texture(i, i2, order);
    }

    public static void writeTexture(android.opengl.ETC1Util.ETC1Texture eTC1Texture, java.io.OutputStream outputStream) throws java.io.IOException {
        java.nio.ByteBuffer data = eTC1Texture.getData();
        int position = data.position();
        try {
            int width = eTC1Texture.getWidth();
            int height = eTC1Texture.getHeight();
            java.nio.ByteBuffer order = java.nio.ByteBuffer.allocateDirect(16).order(java.nio.ByteOrder.nativeOrder());
            android.opengl.ETC1.formatHeader(order, width, height);
            byte[] bArr = new byte[4096];
            order.get(bArr, 0, 16);
            outputStream.write(bArr, 0, 16);
            int encodedDataSize = android.opengl.ETC1.getEncodedDataSize(width, height);
            int i = 0;
            while (i < encodedDataSize) {
                int min = java.lang.Math.min(4096, encodedDataSize - i);
                data.get(bArr, 0, min);
                outputStream.write(bArr, 0, min);
                i += min;
            }
        } finally {
            data.position(position);
        }
    }
}
