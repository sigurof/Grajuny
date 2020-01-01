#version 420 core

uniform vec3 lightPos;
uniform vec3 lightCol;
uniform float ambient;
uniform vec3 color;
uniform float shineDamper;
uniform float reflectivity;

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

    float originalFragDepth = gl_FragCoord.z;
    vec4 clipPos = prjMatrixPass * viewMatrixPass  * vec4(point, 1.0);
    float ndcDepth = clipPos.z / clipPos.w;
    float a = ((gl_DepthRange.diff * ndcDepth) + gl_DepthRange.near + gl_DepthRange.far) / 2.0;
    gl_FragDepth = a;


    vec3 surfaceNormal = normalize(point - sphereCenter);
    vec3 toLightVec = normalize(lightPos - point);
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, ambient);
    vec3 diffuse = brightness * lightCol;


    vec3 unitVectorToCamera = normalize(cameraPos - point);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor* reflectivity * lightCol;

//    out_Color = vec4(r, g, b, 1);
    out_Color = vec4(diffuse, 1.0)*vec4(color, 1) + vec4(finalSpecular, 1.0);
}