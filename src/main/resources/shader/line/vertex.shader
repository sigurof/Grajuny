#version 420 core

attribute vec3 position;

uniform   mat4 prjMatrix;
uniform   mat4 viewMatrix;
uniform mat4 trMatrix;

void main() {
    vec4 worldPos = trMatrix * vec4(position, 1.0);
    gl_Position = prjMatrix * viewMatrix * worldPos;
}