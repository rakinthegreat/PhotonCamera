package com.eszdman.photoncamera.OpenGL;

import android.graphics.Point;

import java.nio.Buffer;

import static android.opengl.GLES20.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES20.GL_COLOR_ATTACHMENT0;
import static android.opengl.GLES20.GL_FRAMEBUFFER;
import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_TEXTURE16;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindFramebuffer;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glFramebufferTexture2D;
import static android.opengl.GLES20.glGenFramebuffers;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glTexImage2D;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLES20.glViewport;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_2D;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_MAG_FILTER;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_MIN_FILTER;

public class GLTexture implements AutoCloseable {
    public Point mSize;
    public int mGLFormat;
    public int mTextureID;
    public GLTexture(Point size,GLFormat glFormat, Buffer pixels){
        this.mSize = size;
        this.mGLFormat = glFormat.getGLFormatInternal();
        int[] TexID = new int[1];
        glGenTextures(TexID.length, TexID, 0);
        mTextureID = TexID[0];
        glActiveTexture(GL_TEXTURE16);
        glBindTexture(GL_TEXTURE_2D, mTextureID);
        glTexImage2D(GL_TEXTURE_2D, 0, glFormat.getGLFormatInternal(), size.x, size.y, 0, glFormat.getGLFormatExternal(), glFormat.getGLType(), pixels);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    }
    void BufferLoad(){
        int[] frameBuffer = new int[1];
        glGenFramebuffers(1, frameBuffer, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer[0]);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, mTextureID, 0);
        glViewport(0, 0, mSize.x,mSize.y);
    }
    void bind(int slot) {
        glActiveTexture(slot);
        glBindTexture(GL_TEXTURE_2D, mTextureID);
    }
    @Override
    public void close() {
        glDeleteTextures(1, new int[] {mTextureID}, 0);
    }
}
