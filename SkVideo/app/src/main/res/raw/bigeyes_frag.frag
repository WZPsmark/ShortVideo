//片元着色器
precision mediump float;//数据精度

varying vec2 aCoord;//当前要上颜色的像素点

uniform sampler2D vTexture;//图片

uniform vec2 left_eye;//float[2] -> x,y
uniform vec2 right_eye;

float fs(float r, float rmax){
    return (1.0-pow(r/rmax-1.0, 2.0)*0.3);
}

vec2 newCoord(vec2 coord, vec2 eye, float rmax){
    vec2 p = coord;
    float r = distance(coord, eye);
    if (r<rmax){
        float fsr = fs(r, rmax);
        p = fsr * (coord-eye) +eye;
    }
    return p;
}

void main(){
    float rmax = distance(left_eye, right_eye)/2.0;
    vec2 p = newCoord(aCoord, left_eye, rmax);
    p = newCoord(p, right_eye, rmax);

    gl_FragColor = texture2D(vTexture, p);
}