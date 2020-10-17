#version 420 core

in vec3 position;

uniform mat4 trMatrix;
uniform mat4 prjMatrix;
uniform mat4 viewMatrix;

void main(void){

    vec4 worldPos = trMatrix * vec4(position, 1.0);
    gl_Position = prjMatrix*viewMatrix*worldPos;
}
