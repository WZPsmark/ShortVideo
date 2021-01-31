//顶点着色器

attribute vec4 vPosition;//变量

attribute vec2 vCoord;//纹理坐标

varying vec2 aCoord;

void main(){
    gl_Position = vPosition;
    aCoord = vCoord;
}