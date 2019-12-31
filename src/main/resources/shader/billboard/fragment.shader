#version 420 core

// Which point on the billboard disk we're at "spherical space"
in vec2 coord2d;
// The actual world space position of that point
in vec3 billboardVertexPosition;
// The world space position of the billboard center
// Note that the billboard center IS NOT EQUAL TO the sphere center in general. They are only come close to equal
// when the camera 'approaches infinitely far away'.
in vec3 billboardCenterPos;
// The world space position of the camera
in vec3 cameraPos;
// The sphere radius
in float passSphereRadius;
// The displacement of the billboard
in float displacement;

out vec4 out_Color;


void main(void){

//    if (dot(coord2d, coord2d) > 1){
//        discard;
//    }
    float billboardCenterToBillboardPoint = length(billboardVertexPosition - billboardCenterPos);
    // domeHeight = the height above the billboard plane. From the formula for a sphere: r² = x² + y² + z² where r = 1 and z = h
    float domeHeight = sqrt(passSphereRadius*passSphereRadius - billboardCenterToBillboardPoint*billboardCenterToBillboardPoint) - displacement;
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


    out_Color = vec4(displacement, 0, 0, 1);
}