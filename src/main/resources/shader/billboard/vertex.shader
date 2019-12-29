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
        coord2d = vec2(-1.0, -1.0);
        break;
        case 1:
        coord2d = vec2(-1.0, 1.0);
        break;
        case 2:
        coord2d = vec2(1.0, -1.0);
        break;
        case 3:
        coord2d = vec2(1.0, 1.0);
        break;
    }
    // billboardCornerPosition = World position of a vertex on a quad with normal vector pointing in the z-direction
    vec4 billboardCornerPosition = vec4(pos, 1.0) + vec4(coord2d * sphereRadius, 0.0, 0.0);

    // billboardVertexPosition = World position of that vertex when that quad is tilted toward the camera with the center position fixed at 'pos'
    vec3 cameraRightDirection = vec3(viewMatrix[0][0], viewMatrix[1][0], viewMatrix[2][0]);
    vec3 cameraUpDirection =    vec3(viewMatrix[0][1], viewMatrix[1][1], viewMatrix[2][1]);
    billboardVertexPosition = pos + cameraRightDirection * billboardCornerPosition.x + cameraUpDirection * billboardCornerPosition.y;

    // gl_Position = Screen space position of that vertex
    gl_Position = prjMatrix * viewMatrix * vec4(billboardVertexPosition, 1.0);

    // Other output
    cameraPos = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz;
    billboardCenterPos = pos;
}