#version 420 core

uniform mat4 prjMatrix;
uniform mat4 viewMatrix;

uniform float sphereRadius;
uniform vec3 pos;

out vec3 color;
out vec2 mapping;


void main(void){

    switch (gl_VertexID){
        case 0:
        gl_Position = vec4(-0.5, -0.5, 0, 1.0);
        mapping = vec2(-1.0, -1.0);
        break;
        case 1:
        gl_Position = vec4(0.5, -0.5, 0, 1.0);
        mapping = vec2(-1.0, 1.0);
        break;
        case 2:
        gl_Position = vec4(-0.5, 0.5, 0, 1.0);
        mapping = vec2(1.0, -1.0);
        break;
        case 3:
        gl_Position = vec4(0.5, 0.5, 0, 1.0);
        mapping = vec2(1.0, 1.0);
        break;
    }
    color = vec3(0.5, 0.5, 0.2);
    vec4 cameraCornerPos = vec4(pos, 1.0) +vec4(mapping*sphereRadius, 0.0, 0.0);

    gl_Position = prjMatrix*viewMatrix*cameraCornerPos;
}