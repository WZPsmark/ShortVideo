#extension GL_OES_EGL_image_external : require
//摄像头数据比较特殊的一个地方
precision mediump float;// 数据精度

varying vec2 aCoord;

uniform samplerExternalOES  vTexture;// samplerExternalOES: 图片， 采样器

void main(){
    vec4 rgba = texture2D(vTexture, aCoord);//rgba
    gl_FragColor = rgba;
}