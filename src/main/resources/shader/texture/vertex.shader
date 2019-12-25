#version 420 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

uniform mat4 trMatrix;
uniform mat4 prjMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPos;

out vec2 passTextureCoords;
out vec3 surfaceNormal;
out vec3 toLightVec;


void main(void){
    vec4 worldPos = trMatrix * vec4(position, 1.0);
    gl_Position = prjMatrix*viewMatrix*worldPos;
    passTextureCoords = textureCoords;
    surfaceNormal = (trMatrix * vec4(normal, 0.0)).xyz;
    toLightVec = (lightPos - worldPos.xyz);

}