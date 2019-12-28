#version 420 core

in vec3 color;
in vec2 mapping;

out vec4 out_Color;

void main(void){
    out_Color = vec4(normalize(mapping), 0.2, 1.0);
}