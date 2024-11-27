package android.print;

/* loaded from: classes3.dex */
public class PrintFileDocumentAdapter extends android.print.PrintDocumentAdapter {
    private static final java.lang.String LOG_TAG = "PrintedFileDocAdapter";
    private final android.content.Context mContext;
    private final android.print.PrintDocumentInfo mDocumentInfo;
    private final java.io.File mFile;
    private android.print.PrintFileDocumentAdapter.WriteFileAsyncTask mWriteFileAsyncTask;

    public PrintFileDocumentAdapter(android.content.Context context, java.io.File file, android.print.PrintDocumentInfo printDocumentInfo) {
        if (file == null) {
            throw new java.lang.IllegalArgumentException("File cannot be null!");
        }
        if (printDocumentInfo == null) {
            throw new java.lang.IllegalArgumentException("documentInfo cannot be null!");
        }
        this.mContext = context;
        this.mFile = file;
        this.mDocumentInfo = printDocumentInfo;
    }

    @Override // android.print.PrintDocumentAdapter
    public void onLayout(android.print.PrintAttributes printAttributes, android.print.PrintAttributes printAttributes2, android.os.CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, android.os.Bundle bundle) {
        layoutResultCallback.onLayoutFinished(this.mDocumentInfo, false);
    }

    @Override // android.print.PrintDocumentAdapter
    public void onWrite(android.print.PageRange[] pageRangeArr, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        this.mWriteFileAsyncTask = new android.print.PrintFileDocumentAdapter.WriteFileAsyncTask(parcelFileDescriptor, cancellationSignal, writeResultCallback);
        this.mWriteFileAsyncTask.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    private final class WriteFileAsyncTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
        private final android.os.CancellationSignal mCancellationSignal;
        private final android.os.ParcelFileDescriptor mDestination;
        private final android.print.PrintDocumentAdapter.WriteResultCallback mResultCallback;

        public WriteFileAsyncTask(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            this.mDestination = parcelFileDescriptor;
            this.mResultCallback = writeResultCallback;
            this.mCancellationSignal = cancellationSignal;
            this.mCancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: android.print.PrintFileDocumentAdapter.WriteFileAsyncTask.1
                @Override // android.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    android.print.PrintFileDocumentAdapter.WriteFileAsyncTask.this.cancel(true);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public java.lang.Void doInBackground(java.lang.Void... voidArr) {
            java.io.FileInputStream fileInputStream;
            try {
                fileInputStream = new java.io.FileInputStream(android.print.PrintFileDocumentAdapter.this.mFile);
            } catch (android.os.OperationCanceledException e) {
            } catch (java.io.IOException e2) {
                android.util.Log.e(android.print.PrintFileDocumentAdapter.LOG_TAG, "Error writing data!", e2);
                this.mResultCallback.onWriteFailed(android.print.PrintFileDocumentAdapter.this.mContext.getString(com.android.internal.R.string.write_fail_reason_cannot_write));
            }
            try {
                java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(this.mDestination.getFileDescriptor());
                try {
                    android.os.FileUtils.copy(fileInputStream, fileOutputStream, this.mCancellationSignal, (java.util.concurrent.Executor) null, (android.os.FileUtils.ProgressListener) null);
                    fileOutputStream.close();
                    fileInputStream.close();
                    return null;
                } finally {
                }
            } finally {
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(java.lang.Void r4) {
            this.mResultCallback.onWriteFinished(new android.print.PageRange[]{android.print.PageRange.ALL_PAGES});
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(java.lang.Void r3) {
            this.mResultCallback.onWriteFailed(android.print.PrintFileDocumentAdapter.this.mContext.getString(com.android.internal.R.string.write_fail_reason_cancelled));
        }
    }
}
