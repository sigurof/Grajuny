#version 420 core

// Regular projection matrix for perspective
uniform mat4 prjMatrix;
// View matrix to look in a  certain direction
uniform mat4 viewMatrix;
// The radius of the desired sphere
uniform float sphereRadius;
// The position of the desired sphere
uniform vec3 pos;

// See fragment shader for explanation
out vec2 coord2d;
out vec3 billboardVertexPosition;
out vec3 billboardCenterPos;
out vec3 cameraPos;


void main(void){

    // Which of the 4 elements is being rendered?
    switch (gl_VertexID){
        case 0:
        coord2d = vec2(-1.0, 1.0);
        break;
        case 1:
        coord2d = vec2(-1.0, -1.0);
        break;
        case 2:
        coord2d = vec2(1.0, 1.0);
        break;
        case 3:
        coord2d = vec2(1.0, -1.0);
        break;
    }


    // Find camera position
    cameraPos = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz;

    // billboardVertexPosition = World position of the vertex when the quad is tilted toward the camera with the center position fixed at 'pos'
    vec3 cb = pos - cameraPos;
    vec3 cameraRightDirection = vec3(viewMatrix[0][0], viewMatrix[1][0], viewMatrix[2][0]);
    vec3 billboardUpDirection = normalize(cross(cameraRightDirection, cb));
    vec3 billboardRightDirection = normalize(cross(cb, billboardUpDirection));
    vec3 x = billboardRightDirection * coord2d.x * sphereRadius;
    vec3 y = billboardUpDirection * coord2d.y * sphereRadius;
    billboardVertexPosition = pos +  x + y;

    // gl_Position = Screen space position of that vertex
    gl_Position = prjMatrix * viewMatrix * vec4(billboardVertexPosition, 1.0);

    // Other output
    billboardCenterPos = pos;
}