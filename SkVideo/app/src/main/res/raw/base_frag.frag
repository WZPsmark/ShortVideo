//片元着色器
precision mediump float;//数据精度

varying vec2 aCoord;

uniform sampler2D vTexture;

void main(){
    vec4 rgba = texture2D(vTexture, aCoord);
    gl_FragColor = rgba;
}