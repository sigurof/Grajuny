#version 420 core

// Which point on the billboard disk we're at "spherical space"
in vec2 coord2d;
// The actual world space position of that point
in vec3 billboardVertexPosition;
// The world space position of the billboard center (around which it is rotated to face the camera)
in vec3 billboardCenterPos;
// The world space position of the camera
in vec3 cameraPos;

out vec4 out_Color;


void main(void){

    if (dot(coord2d, coord2d) > 1){
        discard;
    }

    // domeHeight = the height above the billboard plane. From the formula for a sphere: r² = x² + y² + z² where r = 1 and z = h
    float domeHeight = sqrt(1 - dot(coord2d, coord2d));
    // Calculate the height vector (vector of unit length pointing from billboard to camera)
    vec3 billboardToCamera = normalize(cameraPos - billboardCenterPos);
    // Then get the actual point on the spherical surface:
    vec3 point = billboardVertexPosition + domeHeight * billboardToCamera;

    // For coloring
    vec3 x = vec3(1, 0, 0);
    vec3 y = vec3(0, 1, 0);
    vec3 z = vec3(0, 0, 1);
    float r = pow(dot(point, x), 10);
    float g = pow(dot(point, y), 10);
    float b = pow(dot(point, z), 10);


    out_Color = vec4(r, g, b, 1);
}