#version 420 core

uniform vec3 pos;
uniform mat4 trMatrix;
uniform mat4 prjMatrix;
uniform mat4 viewMatrix;

out vec3 color;


void main(void){

    switch (gl_VertexID){
        case 0:
        gl_Position = vec4(-0.5, -0.5, 0, 1.0);
        break;
        case 1:
        gl_Position = vec4(0.5, -0.5, 0, 1.0);
        break;
        case 2:
        gl_Position = vec4(-0.5, 0.5, 0, 1.0);
        break;
        case 3:
        gl_Position = vec4(0.5, 0.5, 0, 1.0);
        break;
    }
    color = vec3(0.5, 0.5, 0.2);
}