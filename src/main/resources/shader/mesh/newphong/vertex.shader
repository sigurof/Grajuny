#version 420 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

uniform mat4 trMatrix;
uniform mat4 prjMatrix;
uniform mat4 viewMatrix;

out vec2 passTextureCoords;
out vec3 surfaceNormal;

out vec4 worldPos;


void main(void){
    worldPos = trMatrix * vec4(position, 1.0);
    gl_Position = prjMatrix*viewMatrix*worldPos;
    passTextureCoords = textureCoords;
    surfaceNormal = mat3(transpose(inverse(trMatrix))) * normal;

}