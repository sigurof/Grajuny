#version 420 core

// Which point on the billboard disk we're at "spherical space"
in vec2 coord2d;
// The actual world space position of that point
in vec3 billboardVertexPosition;
// The world space position of the billboard center
// Note that the billboard center IS NOT EQUAL TO the sphere center in general. They are only come close to equal
// when the camera 'approaches infinitely far away'.
in vec3 billboardCenterPos;
// Sphere center world position
in vec3 sphereCenter;
// The world space position of the camera
in vec3 cameraPos;
// The sphere radius
in float passSphereRadius;
// The displacement of the billboard
in float displacement;
// matrix
in mat4 prjMatrixPass;
in mat4 viewMatrixPass;

out vec4 out_Color;


void main(void){


    if (dot(coord2d, coord2d) > 1){
        discard;
    }
    float billboardCenterToBillboardPoint = length(billboardVertexPosition - billboardCenterPos);
    // domeHeight = the height above the billboard plane. From the formula for a sphere: r² = x² + y² + z² where r = 1 and z = h
    float domeHeight = sqrt(passSphereRadius*passSphereRadius - billboardCenterToBillboardPoint*billboardCenterToBillboardPoint) - displacement;
    // Calculate the height vector (vector of unit length pointing from billboard to camera)
    vec3 billboardToCamera = normalize(cameraPos - billboardCenterPos);
    // Then get the actual point on the spherical surface:
    vec3 point = billboardVertexPosition + domeHeight * billboardToCamera;
    vec3 radVector = point - sphereCenter;

    // For coloring
    vec3 x = vec3(1, 0, 0);
    vec3 y = vec3(0, 1, 0);
    vec3 z = vec3(0, 0, 1);
    float r = pow(dot(point, x), 10);
    float g = pow(dot(point, y), 10);
    float b = pow(dot(point, z), 10);

//    https://www.opengl.org/archives/resources/faq/technical/depthbuffer.htm
//    vec4 eyePos = viewMatrixPass  * vec4(point, 1.0);
//    float f = 1000;
//    float n = 0.1;
//    float we = eyePos.w;
//    float ze = eyePos.z;
//    float zc = -ze * (f + n)/(f - n) - we * 2* f*n/(f - n);
//    float wc = -ze;
//    float zndc = zc / wc;
//    float zw = s * ((we / ze) * f*n/(f-n) + 0.5*(f+n)/(f-n) + 0.5);

        float originalFragDepth = gl_FragCoord.z;
        vec4 clipPos = prjMatrixPass * viewMatrixPass  * vec4(point, 1.0);
        float ndcDepth = clipPos.z / clipPos.w;
//        float a = ((gl_DepthRange.diff * ndcDepth) + gl_DepthRange.near + gl_DepthRange.far) / 2.0;
//        float a = ndcDepth * (gl_DepthRange.far - gl_DepthRange.near)/2 + (gl_DepthRange.far + gl_DepthRange.near)/2;
        float a = ((gl_DepthRange.diff * ndcDepth) + gl_DepthRange.near + gl_DepthRange.far) / 2.0;
        gl_FragDepth = a;//-length(clipPos.xyz);


        vec3 col;
        float val = a;
        if (val < 1){
            col = vec3(1, 1, 1);
        }
        else{
            col = vec3(0, 0, 0);
        }
//
    out_Color = vec4(a/2, 0, 0, 1);
}