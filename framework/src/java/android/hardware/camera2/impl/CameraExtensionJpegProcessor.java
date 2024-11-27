package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraExtensionJpegProcessor implements android.hardware.camera2.extension.ICaptureProcessorImpl {
    private static final int JPEG_APP_SEGMENT_SIZE = 65536;
    private static final int JPEG_QUEUE_SIZE = 1;
    public static final java.lang.String TAG = "CameraExtensionJpeg";
    private final android.os.Handler mHandler;
    private final android.hardware.camera2.extension.ICaptureProcessorImpl mProcessor;
    private android.media.ImageReader mYuvReader = null;
    private android.media.ImageReader mPostviewYuvReader = null;
    private android.hardware.camera2.extension.Size mResolution = null;
    private android.hardware.camera2.extension.Size mPostviewResolution = null;
    private int mFormat = -1;
    private android.view.Surface mOutputSurface = null;
    private android.media.ImageWriter mOutputWriter = null;
    private android.view.Surface mPostviewOutputSurface = null;
    private android.media.ImageWriter mPostviewOutputWriter = null;
    private java.util.concurrent.ConcurrentLinkedQueue<android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters> mJpegParameters = new java.util.concurrent.ConcurrentLinkedQueue<>();
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread(TAG);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int compressJpegFromYUV420pNative(int i, int i2, java.nio.ByteBuffer byteBuffer, int i3, int i4, java.nio.ByteBuffer byteBuffer2, int i5, int i6, java.nio.ByteBuffer byteBuffer3, int i7, int i8, java.nio.ByteBuffer byteBuffer4, int i9, int i10, int i11, int i12, int i13, int i14, int i15);

    private static final class JpegParameters {
        public int mQuality;
        public int mRotation;
        public java.util.HashSet<java.lang.Long> mTimeStamps;

        private JpegParameters() {
            this.mTimeStamps = new java.util.HashSet<>();
            this.mRotation = 0;
            this.mQuality = 100;
        }
    }

    public CameraExtensionJpegProcessor(android.hardware.camera2.extension.ICaptureProcessorImpl iCaptureProcessorImpl) {
        this.mProcessor = iCaptureProcessorImpl;
        this.mHandlerThread.start();
        this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
    }

    public void close() {
        this.mHandlerThread.quitSafely();
        if (this.mOutputWriter != null) {
            this.mOutputWriter.close();
            this.mOutputWriter = null;
        }
        if (this.mYuvReader != null) {
            this.mYuvReader.close();
            this.mYuvReader = null;
        }
    }

    private static android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters getJpegParameters(java.util.List<android.hardware.camera2.extension.CaptureBundle> list) {
        android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters jpegParameters = new android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters();
        if (!list.isEmpty()) {
            java.lang.Byte b = (java.lang.Byte) list.get(0).captureResult.get(android.hardware.camera2.CaptureResult.JPEG_QUALITY);
            if (b == null) {
                android.util.Log.w(TAG, "No jpeg quality set, using default: 100");
            } else {
                jpegParameters.mQuality = b.byteValue();
            }
            java.lang.Integer num = (java.lang.Integer) list.get(0).captureResult.get(android.hardware.camera2.CaptureResult.JPEG_ORIENTATION);
            if (num == null) {
                android.util.Log.w(TAG, "No jpeg rotation set, using default: 0");
            } else {
                jpegParameters.mRotation = (360 - (num.intValue() % 360)) / 90;
            }
            java.util.Iterator<android.hardware.camera2.extension.CaptureBundle> it = list.iterator();
            while (it.hasNext()) {
                java.lang.Long l = (java.lang.Long) it.next().captureResult.get(android.hardware.camera2.CaptureResult.SENSOR_TIMESTAMP);
                if (l == null) {
                    android.util.Log.e(TAG, "Capture bundle without valid sensor timestamp!");
                } else {
                    jpegParameters.mTimeStamps.add(l);
                }
            }
        }
        return jpegParameters;
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void process(java.util.List<android.hardware.camera2.extension.CaptureBundle> list, android.hardware.camera2.extension.IProcessResultImpl iProcessResultImpl, boolean z) throws android.os.RemoteException {
        android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters jpegParameters = getJpegParameters(list);
        try {
            this.mJpegParameters.add(jpegParameters);
            this.mProcessor.process(list, iProcessResultImpl, z);
        } catch (java.lang.Exception e) {
            this.mJpegParameters.remove(jpegParameters);
            throw e;
        }
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void onOutputSurface(android.view.Surface surface, int i) throws android.os.RemoteException {
        if (i != 256) {
            android.util.Log.e(TAG, "Unsupported output format: " + i);
        } else {
            this.mOutputSurface = surface;
            initializePipeline();
        }
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void onPostviewOutputSurface(android.view.Surface surface) throws android.os.RemoteException {
        android.hardware.camera2.impl.CameraExtensionUtils.SurfaceInfo querySurface = android.hardware.camera2.impl.CameraExtensionUtils.querySurface(surface);
        if (querySurface.mFormat != 256) {
            android.util.Log.e(TAG, "Unsupported output format: " + querySurface.mFormat);
        } else {
            this.mPostviewOutputSurface = surface;
            initializePostviewPipeline();
        }
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void onResolutionUpdate(android.hardware.camera2.extension.Size size, android.hardware.camera2.extension.Size size2) throws android.os.RemoteException {
        this.mResolution = size;
        this.mPostviewResolution = size2;
        initializePipeline();
    }

    @Override // android.hardware.camera2.extension.ICaptureProcessorImpl
    public void onImageFormatUpdate(int i) throws android.os.RemoteException {
        if (i != 35) {
            android.util.Log.e(TAG, "Unsupported input format: " + i);
        } else {
            this.mFormat = i;
            initializePipeline();
        }
    }

    private void initializePipeline() throws android.os.RemoteException {
        if (this.mFormat != -1 && this.mOutputSurface != null && this.mResolution != null && this.mYuvReader == null) {
            this.mOutputWriter = android.media.ImageWriter.newInstance(this.mOutputSurface, 1, 256, (((this.mResolution.width * this.mResolution.height) * 3) / 2) + 65536, 1);
            this.mYuvReader = android.media.ImageReader.newInstance(this.mResolution.width, this.mResolution.height, this.mFormat, 1);
            this.mYuvReader.setOnImageAvailableListener(new android.hardware.camera2.impl.CameraExtensionJpegProcessor.YuvCallback(this.mYuvReader, this.mOutputWriter), this.mHandler);
            this.mProcessor.onOutputSurface(this.mYuvReader.getSurface(), this.mFormat);
            this.mProcessor.onResolutionUpdate(this.mResolution, this.mPostviewResolution);
            this.mProcessor.onImageFormatUpdate(this.mFormat);
        }
    }

    private void initializePostviewPipeline() throws android.os.RemoteException {
        if (this.mFormat != -1 && this.mPostviewOutputSurface != null && this.mPostviewResolution != null && this.mPostviewYuvReader == null) {
            this.mPostviewOutputWriter = android.media.ImageWriter.newInstance(this.mPostviewOutputSurface, 1, 256, this.mPostviewResolution.width * this.mPostviewResolution.height, 1);
            this.mPostviewYuvReader = android.media.ImageReader.newInstance(this.mPostviewResolution.width, this.mPostviewResolution.height, this.mFormat, 1);
            this.mPostviewYuvReader.setOnImageAvailableListener(new android.hardware.camera2.impl.CameraExtensionJpegProcessor.YuvCallback(this.mPostviewYuvReader, this.mPostviewOutputWriter), this.mHandler);
            this.mProcessor.onPostviewOutputSurface(this.mPostviewYuvReader.getSurface());
            this.mProcessor.onResolutionUpdate(this.mResolution, this.mPostviewResolution);
            this.mProcessor.onImageFormatUpdate(this.mFormat);
        }
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        throw new java.lang.UnsupportedOperationException("Binder IPC not supported!");
    }

    private class YuvCallback implements android.media.ImageReader.OnImageAvailableListener {
        private android.media.ImageReader mImageReader;
        private android.media.ImageWriter mImageWriter;

        public YuvCallback(android.media.ImageReader imageReader, android.media.ImageWriter imageWriter) {
            this.mImageReader = imageReader;
            this.mImageWriter = imageWriter;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(android.media.ImageReader imageReader) {
            android.media.Image acquireNextImage;
            android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters jpegParameters;
            android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters jpegParameters2;
            android.media.Image image = null;
            byte b = 0;
            try {
                acquireNextImage = this.mImageReader.acquireNextImage();
            } catch (java.lang.IllegalStateException e) {
            }
            try {
                android.media.Image dequeueInputImage = this.mImageWriter.dequeueInputImage();
                java.nio.ByteBuffer buffer = dequeueInputImage.getPlanes()[0].getBuffer();
                buffer.clear();
                int width = dequeueInputImage.getWidth();
                android.media.Image.Plane plane = acquireNextImage.getPlanes()[0];
                android.media.Image.Plane plane2 = acquireNextImage.getPlanes()[1];
                android.media.Image.Plane plane3 = acquireNextImage.getPlanes()[2];
                java.util.concurrent.ConcurrentLinkedQueue concurrentLinkedQueue = new java.util.concurrent.ConcurrentLinkedQueue(android.hardware.camera2.impl.CameraExtensionJpegProcessor.this.mJpegParameters);
                java.util.Iterator it = concurrentLinkedQueue.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        jpegParameters = null;
                        break;
                    }
                    jpegParameters = (android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters) it.next();
                    if (jpegParameters.mTimeStamps.contains(java.lang.Long.valueOf(acquireNextImage.getTimestamp()))) {
                        it.remove();
                        break;
                    }
                }
                if (jpegParameters != null) {
                    jpegParameters2 = jpegParameters;
                } else if (concurrentLinkedQueue.isEmpty()) {
                    android.util.Log.w(android.hardware.camera2.impl.CameraExtensionJpegProcessor.TAG, "Empty jpeg settings queue! Using default jpeg orientation and quality!");
                    android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters jpegParameters3 = new android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters();
                    jpegParameters3.mRotation = 0;
                    jpegParameters3.mQuality = 100;
                    jpegParameters2 = jpegParameters3;
                } else {
                    android.util.Log.w(android.hardware.camera2.impl.CameraExtensionJpegProcessor.TAG, "No jpeg settings found with matching timestamp for current processed input!");
                    android.util.Log.w(android.hardware.camera2.impl.CameraExtensionJpegProcessor.TAG, "Using values from the top of the queue!");
                    jpegParameters2 = (android.hardware.camera2.impl.CameraExtensionJpegProcessor.JpegParameters) concurrentLinkedQueue.poll();
                }
                android.hardware.camera2.impl.CameraExtensionJpegProcessor.compressJpegFromYUV420pNative(acquireNextImage.getWidth(), acquireNextImage.getHeight(), plane.getBuffer(), plane.getPixelStride(), plane.getRowStride(), plane2.getBuffer(), plane2.getPixelStride(), plane2.getRowStride(), plane3.getBuffer(), plane3.getPixelStride(), plane3.getRowStride(), buffer, width, jpegParameters2.mQuality, 0, 0, acquireNextImage.getWidth(), acquireNextImage.getHeight(), jpegParameters2.mRotation);
                dequeueInputImage.setTimestamp(acquireNextImage.getTimestamp());
                acquireNextImage.close();
                try {
                    try {
                        this.mImageWriter.queueInputImage(dequeueInputImage);
                    } catch (java.lang.IllegalStateException e2) {
                        android.util.Log.e(android.hardware.camera2.impl.CameraExtensionJpegProcessor.TAG, "Failed to queue encoded result!");
                    }
                } finally {
                    dequeueInputImage.close();
                }
            } catch (java.lang.IllegalStateException e3) {
                image = acquireNextImage;
                if (image != null) {
                    image.close();
                }
                android.util.Log.e(android.hardware.camera2.impl.CameraExtensionJpegProcessor.TAG, "Failed to acquire processed yuv image or jpeg image!");
            }
        }
    }
}
