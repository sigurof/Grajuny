#version 420 core

struct Material {
    sampler2D ambient;
    sampler2D diffuse;
    sampler2D specular;
    float shininess;
};

struct PointLight {
    vec3 position;

    float constant;
    float linear;
    float quadratic;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

uniform mat4 frPrjMatrix;// TODO Use uniform blocks to share with vertex shader
uniform mat4 frViewMatrix;
uniform float frSphereRadius;
uniform vec3 frCameraPos;
uniform vec3 frSphereCenter;

uniform Material material;
uniform PointLight light;

// Which point on the billboard disk we're at "spherical space"
in vec2 coord2d;
// The actual world space position of that point
in vec3 billboardVertexPosition;
// The world space position of the billboard center
// Note that the billboard center IS NOT EQUAL TO the sphere center in general. They are only come close to equal
// when the camera 'approaches infinitely far away'.
in vec3 billboardCenterPos;
// The displacement of the billboard
in float sphereToBbdDist;
in vec3 billboardNormal;
in float billboardRadius;

in vec3 cameraFwd;

out vec4 out_Color;

float pi = 3.14159265;

vec3 findThePointOnSphereSurface(){
    vec3 bbdPointToCameraUnit = normalize(frCameraPos - billboardVertexPosition); // Ray
    vec3 sphereToBbdPoint = billboardVertexPosition - frSphereCenter;
    float B = 2*dot(bbdPointToCameraUnit, sphereToBbdPoint);
    float C = dot(sphereToBbdPoint, sphereToBbdPoint) - frSphereRadius*frSphereRadius;
    float distance = (-B + sqrt(B*B - 4*C))/2;
    return billboardVertexPosition + distance * bbdPointToCameraUnit;
}

float calculateFragmentDepth(vec3 point){
    // Transforming the point to clip space coordinates
    vec4 pointInClipSpace = frPrjMatrix * frViewMatrix  * vec4(point, 1.0);
    // Finding the normalized device coordinates depth (z)
    float ndcDepth = pointInClipSpace.z / pointInClipSpace.w;
    // Calculating the depth based on ndc
    return ((gl_DepthRange.diff * ndcDepth) + gl_DepthRange.near + gl_DepthRange.far) / 2.0;
}


vec2 getPolarAnglesOfPoint(vec3 point, vec3 sphereCenter, float radius){
    vec3 onSphere = point - sphereCenter;
    float phi = acos(onSphere.y / radius);
    vec3 onSphereUnit = normalize(point - sphereCenter);
    float theta = atan(onSphereUnit.x, onSphereUnit.z);
    return vec2(theta, phi);
}

vec2 getTextureCoordinatesOfPoint(vec3 point, vec3 sphereCenter, float radius){
    vec2 frPolarAngles  = getPolarAnglesOfPoint(point, sphereCenter, radius);
    return vec2((frPolarAngles.x+pi)/2/pi, (frPolarAngles.y)/pi);
}

vec3 calculateAmbientLight(vec2 textureCoordinates){
    return light.ambient * texture(material.ambient, textureCoordinates).rgb;
}

vec3 calculateDiffuseLight(vec3 unitNormal, vec3 unitToLight, vec2 textureCoordinates){
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, 0.0);
    return light.diffuse * brightness * texture(material.diffuse, textureCoordinates).rgb;
}

vec3 calculateSpecularLight(vec3 unitNormal, vec3 unitToLight, vec3 fragPosition, vec2 textureCoordinates){
    vec3 toCameraVec =  frCameraPos - fragPosition.xyz;
    vec3 unitVectorToCamera = normalize(toCameraVec);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, material.shininess);// = spec
    return light.specular * dampedFactor * texture(material.specular, textureCoordinates).rgb;
}

vec4 calculatePointLight(vec3 fragPosition, vec2 textureCoordinates){
    vec3 unitNormal = normalize(fragPosition - frSphereCenter);// Valid because we're dealing with a sphere
    vec3 unitToLight = normalize(light.position - fragPosition);

    vec3 ambient = calculateAmbientLight(textureCoordinates);
    vec3 diffuse = calculateDiffuseLight(unitNormal, unitToLight, textureCoordinates);
    vec3 specular = calculateSpecularLight(unitNormal, unitToLight, fragPosition, textureCoordinates);

    float distance = length(light.position - fragPosition.xyz);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * distance * distance);

    return vec4((ambient + diffuse + specular)*attenuation, 1);
}

vec4 calculateSphere(){
    vec3 fragPosition = findThePointOnSphereSurface();
    // Writing the appropriate fragment depth value to the depth buffer
    gl_FragDepth = calculateFragmentDepth(fragPosition);
    // Texture
    vec2 textureCoordinates = getTextureCoordinatesOfPoint(fragPosition, frSphereCenter, frSphereRadius);

    return calculatePointLight(fragPosition, textureCoordinates);
}

void main(void){
    // Discaring everything outside the sphere surface
    if (dot(coord2d, coord2d) > 1){
        discard;
    } else {
        out_Color = calculateSphere();
    }
}

