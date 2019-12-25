#version 420 core

in vec3 position;
in vec2 textureCoords;

uniform mat4 trMatrix;
uniform mat4 prjMatrix;
uniform mat4 viewMatrix;

out vec2 passTextureCoords;


void main(void){
    gl_Position = prjMatrix*viewMatrix*trMatrix*vec4(position.xyz, 1.0);
    passTextureCoords = textureCoords;

}