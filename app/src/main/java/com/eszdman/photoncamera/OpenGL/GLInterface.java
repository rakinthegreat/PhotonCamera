package com.eszdman.photoncamera.OpenGL;

import android.annotation.SuppressLint;

import com.eszdman.photoncamera.Render.Parameters;
import com.eszdman.photoncamera.api.Interface;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

public class GLInterface {
    public GLProg glprogram;
    public Parameters parameters;
    public ByteBuffer inputRaw;
    public GLCoreBlockProcessing glProc;
    public GLContext glContext;
    public GLInterface(GLCoreBlockProcessing processing){
        glProc = processing;
        glprogram = glProc.mProgram;
    }
    public GLInterface(GLContext context){
        glContext = context;
        glprogram = glContext.mProgram;
    }
    @SuppressLint("NewApi")
    static public String loadShader(int fragment) {
        StringBuilder source = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(Interface.i.mainActivity.getResources().openRawResource(fragment)));
        for (Object line : reader.lines().toArray()) {
            source.append(line +"\n");
        }
        return source.toString();
    }
}
