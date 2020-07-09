package com.eszdman.photoncamera;

import java.nio.ByteBuffer;

public class Wrapper {
    public Wrapper(){
        System.loadLibrary("HdrX");
        //System.loadLibrary("photon_accel");
    }
    /**
     * Function to create pointers for image buffers.
     *
     * @param rows Image rows.
     * @param cols Image cols.
     * @param frames Image count.
     */
public static native void init(int rows,int cols, int frames);
    /**
     * Function to load images.
     *
     * @param bufferptr Image buffer.
     */
public static native void loadFrame(ByteBuffer bufferptr);
public static native ByteBuffer processFrame();
//public static native void Test();
//public static native ByteBuffer ProcessOpenCL(ByteBuffer in);
}
