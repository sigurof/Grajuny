#version 330 core
out vec4 FragColor;

in vec2 TexCoords;

uniform sampler2D screenTexture;

vec4 colorInversion(){
    return vec4(vec3(1.0 - texture(screenTexture, TexCoords)), 1.0);
}

vec4 grayScale(){
    vec4 origColor = texture(screenTexture, TexCoords);
    float average = 0.2126 * origColor.r + 0.7152 * origColor.g + 0.0722 * origColor.b;
    return vec4(average, average, average, 1.0);
}

float sharpenKernel[9] = float[](
-1, -1, -1,
-1, 9, -1,
-1, -1, -1
);
float blurKernel[9] = float[](
1.0 / 16, 2.0 / 16, 1.0 / 16,
2.0 / 16, 4.0 / 16, 2.0 / 16,
1.0 / 16, 2.0 / 16, 1.0 / 16
);
float edgeDetectKernel[9] = float[](
1, 1, 1,
1, -8, 1,
1, 1, 1
);

vec4 kernel(float offset, float[9] kernel){
    vec2 offsets[9] = vec2[](
    vec2(-offset, offset), // top-left
    vec2(0.0f, offset), // top-center
    vec2(offset, offset), // top-right
    vec2(-offset, 0.0f), // center-left
    vec2(0.0f, 0.0f), // center-center
    vec2(offset, 0.0f), // center-right
    vec2(-offset, -offset), // bottom-left
    vec2(0.0f, -offset), // bottom-center
    vec2(offset, -offset)// bottom-right
    );

    vec3 sampleTex[9];
    for (int i = 0; i < 9; i++)
    {
        sampleTex[i] = vec3(texture(screenTexture, TexCoords.st + offsets[i]));
    }
    vec3 col = vec3(0.0);
    for (int i = 0; i < 9; i++)
    col += sampleTex[i] * kernel[i];

    return vec4(col, 1.0);
}

void main()
{
        FragColor = texture(screenTexture, TexCoords);
//    FragColor = colorInversion();
//            FragColor = grayScale();
//        FragColor =kernel(1.0/300.0, edgeDetectKernel);

}
