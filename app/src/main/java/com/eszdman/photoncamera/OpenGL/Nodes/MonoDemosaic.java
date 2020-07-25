package com.eszdman.photoncamera.OpenGL.Nodes;

import com.eszdman.photoncamera.OpenGL.GLFormat;
import com.eszdman.photoncamera.OpenGL.GLInterface;
import com.eszdman.photoncamera.OpenGL.GLProg;
import com.eszdman.photoncamera.OpenGL.GLTexture;
import com.eszdman.photoncamera.Render.Parameters;

public class MonoDemosaic extends Node{
    public MonoDemosaic(int rid, String name) {
        super(rid, name);
    }
    @Override
    public void Run() {
        GLInterface glint = basePipeline.glint;
        GLProg glProg = glint.glprogram;
        GLTexture glTexture;
        Parameters params = glint.parameters;
        glTexture = new GLTexture(params.rawSize, new GLFormat(GLFormat.DataType.UNSIGNED_16),glint.inputRaw);
        glProg.setTexture("RawBuffer",glTexture);
        glProg.servar("WhiteLevel",params.whitelevel);
        super.WorkingTexture = new GLTexture(params.rawSize,new GLFormat(GLFormat.DataType.FLOAT_16,4),null);
    }
}
