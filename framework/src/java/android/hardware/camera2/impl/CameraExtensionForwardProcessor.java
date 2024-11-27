package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraExtensionForwardProcessor {
    private static final int FORWARD_QUEUE_SIZE = 3;
    public static final java.lang.String TAG = "CameraExtensionForward";
    private final android.os.Handler mHandler;
    private final int mOutputSurfaceFormat;
    private final long mOutputSurfaceUsage;
    private final android.hardware.camera2.extension.IPreviewImageProcessorImpl mProcessor;
    private android.media.ImageReader mIntermediateReader = null;
    private android.view.Surface mIntermediateSurface = null;
    private android.util.Size mResolution = null;
    private android.view.Surface mOutputSurface = null;
    private android.media.ImageWriter mOutputWriter = null;
    private boolean mOutputAbandoned = false;

    public CameraExtensionForwardProcessor(android.hardware.camera2.extension.IPreviewImageProcessorImpl iPreviewImageProcessorImpl, int i, long j, android.os.Handler handler) {
        this.mProcessor = iPreviewImageProcessorImpl;
        this.mOutputSurfaceUsage = j;
        this.mOutputSurfaceFormat = i;
        this.mHandler = handler;
    }

    public void close() {
        if (this.mOutputWriter != null) {
            this.mOutputWriter.close();
            this.mOutputWriter = null;
        }
        if (this.mIntermediateReader != null) {
            this.mIntermediateReader.close();
            this.mIntermediateReader = null;
        }
    }

    public void onOutputSurface(android.view.Surface surface, int i) {
        this.mOutputSurface = surface;
        try {
            initializePipeline();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to initialize forward processor, extension service does not respond!");
        }
    }

    public void onResolutionUpdate(android.util.Size size) {
        this.mResolution = size;
    }

    public void onImageFormatUpdate(int i) {
        if (i != 35) {
            android.util.Log.e(TAG, "Unsupported input format: " + i);
        }
    }

    private void initializePipeline() throws android.os.RemoteException {
        if (this.mOutputWriter != null) {
            this.mOutputWriter.close();
            this.mOutputWriter = null;
        }
        if (this.mIntermediateReader == null) {
            this.mIntermediateReader = android.media.ImageReader.newInstance(this.mResolution.getWidth(), this.mResolution.getHeight(), 35, 3, this.mOutputSurfaceUsage);
            this.mIntermediateSurface = this.mIntermediateReader.getSurface();
            this.mIntermediateReader.setOnImageAvailableListener(new android.hardware.camera2.impl.CameraExtensionForwardProcessor.ForwardCallback(), this.mHandler);
            this.mProcessor.onOutputSurface(this.mIntermediateSurface, this.mOutputSurfaceFormat);
            this.mProcessor.onImageFormatUpdate(35);
            android.hardware.camera2.extension.Size size = new android.hardware.camera2.extension.Size();
            size.width = this.mResolution.getWidth();
            size.height = this.mResolution.getHeight();
            this.mProcessor.onResolutionUpdate(size);
        }
    }

    public void process(android.hardware.camera2.extension.ParcelImage parcelImage, android.hardware.camera2.TotalCaptureResult totalCaptureResult, android.hardware.camera2.extension.IProcessResultImpl iProcessResultImpl) throws android.os.RemoteException {
        if (this.mIntermediateSurface != null && this.mIntermediateSurface.isValid() && !this.mOutputAbandoned) {
            this.mProcessor.process(parcelImage, totalCaptureResult.getNativeMetadata(), totalCaptureResult.getSequenceId(), iProcessResultImpl);
        }
    }

    private class ForwardCallback implements android.media.ImageReader.OnImageAvailableListener {
        private ForwardCallback() {
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(android.media.ImageReader imageReader) {
            try {
                android.media.Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage == null) {
                    android.util.Log.e(android.hardware.camera2.impl.CameraExtensionForwardProcessor.TAG, "Invalid image");
                    return;
                }
                if (android.hardware.camera2.impl.CameraExtensionForwardProcessor.this.mOutputSurface != null && android.hardware.camera2.impl.CameraExtensionForwardProcessor.this.mOutputSurface.isValid() && !android.hardware.camera2.impl.CameraExtensionForwardProcessor.this.mOutputAbandoned) {
                    if (android.hardware.camera2.impl.CameraExtensionForwardProcessor.this.mOutputWriter == null) {
                        android.hardware.camera2.impl.CameraExtensionForwardProcessor.this.mOutputWriter = android.media.ImageWriter.newInstance(android.hardware.camera2.impl.CameraExtensionForwardProcessor.this.mOutputSurface, 3, acquireNextImage.getFormat());
                    }
                    try {
                        android.hardware.camera2.impl.CameraExtensionForwardProcessor.this.mOutputWriter.queueInputImage(acquireNextImage);
                        return;
                    } catch (java.lang.IllegalStateException e) {
                        android.util.Log.e(android.hardware.camera2.impl.CameraExtensionForwardProcessor.TAG, "Failed to queue processed buffer!");
                        acquireNextImage.close();
                        android.hardware.camera2.impl.CameraExtensionForwardProcessor.this.mOutputAbandoned = true;
                        return;
                    }
                }
                acquireNextImage.close();
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.e(android.hardware.camera2.impl.CameraExtensionForwardProcessor.TAG, "Failed to acquire processed image!");
            }
        }
    }
}
