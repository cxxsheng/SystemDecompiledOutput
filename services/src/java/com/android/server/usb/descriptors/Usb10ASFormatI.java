package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class Usb10ASFormatI extends com.android.server.usb.descriptors.UsbASFormat {
    private static final java.lang.String TAG = "Usb10ASFormatI";
    private byte mBitResolution;
    private byte mNumChannels;
    private byte mSampleFreqType;
    private int[] mSampleRates;
    private byte mSubframeSize;

    public Usb10ASFormatI(int i, byte b, byte b2, byte b3, int i2) {
        super(i, b, b2, b3, i2);
    }

    public byte getNumChannels() {
        return this.mNumChannels;
    }

    public byte getSubframeSize() {
        return this.mSubframeSize;
    }

    public byte getBitResolution() {
        return this.mBitResolution;
    }

    public byte getSampleFreqType() {
        return this.mSampleFreqType;
    }

    @Override // com.android.server.usb.descriptors.UsbASFormat
    public int[] getSampleRates() {
        return this.mSampleRates;
    }

    @Override // com.android.server.usb.descriptors.UsbASFormat
    public int[] getBitDepths() {
        return new int[]{this.mBitResolution};
    }

    @Override // com.android.server.usb.descriptors.UsbASFormat
    public int[] getChannelCounts() {
        return new int[]{this.mNumChannels};
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mNumChannels = byteStream.getByte();
        this.mSubframeSize = byteStream.getByte();
        this.mBitResolution = byteStream.getByte();
        this.mSampleFreqType = byteStream.getByte();
        if (this.mSampleFreqType == 0) {
            this.mSampleRates = new int[2];
            this.mSampleRates[0] = byteStream.unpackUsbTriple();
            this.mSampleRates[1] = byteStream.unpackUsbTriple();
        } else {
            this.mSampleRates = new int[this.mSampleFreqType];
            for (int i = 0; i < this.mSampleFreqType; i++) {
                this.mSampleRates[i] = byteStream.unpackUsbTriple();
            }
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbASFormat, com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("" + ((int) getNumChannels()) + " Channels.");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Subframe Size: ");
        sb.append((int) getSubframeSize());
        reportCanvas.writeListItem(sb.toString());
        reportCanvas.writeListItem("Bit Resolution: " + ((int) getBitResolution()));
        byte sampleFreqType = getSampleFreqType();
        int[] sampleRates = getSampleRates();
        reportCanvas.writeListItem("Sample Freq Type: " + ((int) sampleFreqType));
        reportCanvas.openList();
        if (sampleFreqType == 0) {
            reportCanvas.writeListItem("min: " + sampleRates[0]);
            reportCanvas.writeListItem("max: " + sampleRates[1]);
        } else {
            for (int i = 0; i < sampleFreqType; i++) {
                reportCanvas.writeListItem("" + sampleRates[i]);
            }
        }
        reportCanvas.closeList();
        reportCanvas.closeList();
    }
}
