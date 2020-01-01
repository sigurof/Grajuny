#version 420 core

// Regular projection matrix for perspective
uniform mat4 prjMatrix;
// View matrix to look in a  certain direction
uniform mat4 viewMatrix;
// The radius of the desired sphere
uniform float sphereRadius;
// The center position of the desired sphere
uniform vec3 pos;

// See fragment shader for explanation
out vec2 coord2d;
out vec3 billboardVertexPosition;
out vec3 billboardCenterPos;
out vec3 cameraPos;
out float passSphereRadius;
out float displacement;
out mat4 prjMatrixPass;
out mat4 viewMatrixPass;
out vec3 sphereCenter;

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

    // 1. First step is to find the x (right) and z (up) directions of the local, right handed coordinate system on the
    // billboard surface given that the y (forward) direction is pointing from the billboard towards the camera

    // Find camera position
    cameraPos = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz;
    // The vector from camera to the billboard
    vec3 cameraToBillboard = pos - cameraPos;
    // The unit vector in the pos x-direction of the the right-handed coord system where the camera is at the origin and it's looking towards the pos y direction
    vec3 cameraRightDirection = vec3(viewMatrix[0][0], viewMatrix[1][0], viewMatrix[2][0]);
    // Arbitrarily (but intuitively) choosing an "up" direction on the billboard surface
    vec3 billboardUpDirection = normalize(cross(cameraRightDirection, cameraToBillboard));
    // Thus leads to a well-defined "right" direction on the billboard surface
    vec3 billboardRightDirection = normalize(cross(cameraToBillboard, billboardUpDirection));
    // The third orthonormal vector (pointing towards the camera), is:
    vec3 billboardOutwardsDirection = normalize(-cameraToBillboard);

    // 2. The billboard must be displaced outwards from the center position of the sphere it represents.
    // This displacement is always between 0 and the radius of the sphere
    float d = length(cameraToBillboard);// distance d from camera to center of sphere
    displacement = sphereRadius*sphereRadius / d;
    // At this distance from the sphere center in the direction of the camera, the radius of the circular intersection
    // between the sphere and the normal plane to said direction, is given by:
    float billboardRadius = sqrt(sphereRadius*sphereRadius - displacement*displacement);

    vec3 displacementVector = displacement * billboardOutwardsDirection;

    // The billboard space x component of the point on the billboard in world coordinates
    vec3 x = billboardRightDirection * coord2d.x * billboardRadius;
    // The billboard space y component of the point on the billboard in world coordinates
    vec3 y = billboardUpDirection * coord2d.y * billboardRadius;
    // Center position of the billboard
    billboardCenterPos = pos + displacementVector;
    // The full vertex position in world coordinates
    billboardVertexPosition = billboardCenterPos + x + y;

    // gl_Position = Screen space position of that vertex
    prjMatrixPass = prjMatrix;
    viewMatrixPass = viewMatrix;
    gl_Position = prjMatrix * viewMatrix * vec4(billboardVertexPosition, 1.0);

    passSphereRadius = sphereRadius;
    sphereCenter = pos;

}