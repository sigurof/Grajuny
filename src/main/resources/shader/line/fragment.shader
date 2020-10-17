#version 420 core

uniform vec3 color;

out vec4 out_Color;

void main() {
    out_Color= vec4(color, 1.0);
}