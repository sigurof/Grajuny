#version 420 core

in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVec;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightCol;

void main(void){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, 0.0);
    vec3 diffuse = brightness * lightCol;

    out_Color = vec4(diffuse, 1.0)*texture(textureSampler, passTextureCoords);
}