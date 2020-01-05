#version 420 core

uniform vec3 lightPos;
uniform vec3 lightCol;
uniform float ambient;
uniform vec3 color;
uniform float shineDamper;
uniform float reflectivity;
uniform mat4 frPrjMatrix;// TODO Use uniform blocks to share with vertex shader
uniform mat4 frViewMatrix;
uniform float frSphereRadius;
uniform vec3 frCameraPos;
uniform vec3 frSphereCenter;
uniform vec2 frPolarAngles;
uniform sampler2D textureSampler;
uniform bool frUseTexture;

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

in vec3 cameraFwd;

out vec4 out_Color;

float pi = 3.14159265;
vec3 findThePointOnSphereSurface(){
    // Finding
    float billboardCenterToBillboardPoint = length(billboardVertexPosition - billboardCenterPos);
    // domeHeight = the height above the billboard plane.
    float domeHeight = sqrt(frSphereRadius*frSphereRadius- billboardCenterToBillboardPoint*billboardCenterToBillboardPoint) - sphereToBbdDist;
    // Calculate the height vector (vector of unit length pointing from billboard to camera)
    vec3 billboardToCamera = normalize(frCameraPos - billboardCenterPos);
    // TODO Her skjer det feil
    vec3 vec = billboardNormal;
//    vec3 vec = normalize(billboardVertexPosition - frSphereCenter);
//    vec3 vec = -normalize(cameraFwd);
    return billboardVertexPosition + domeHeight * vec;
}

float calculateFragmentDepth(vec3 point){
    // Transforming the point to clip space coordinates
    vec4 pointInClipSpace = frPrjMatrix * frViewMatrix  * vec4(point, 1.0);
    // Finding the normalized device coordinates depth (z)
    float ndcDepth = pointInClipSpace.z / pointInClipSpace.w;
    // Calculating the depth based on ndc
    return ((gl_DepthRange.diff * ndcDepth) + gl_DepthRange.near + gl_DepthRange.far) / 2.0;
}

vec3 calculateDiffuseLight(vec3 surfUnitNormal, vec3 unitToLight){
    float normalDotLight = dot(surfUnitNormal, unitToLight);
    float brightness = max(normalDotLight, ambient);
    return brightness * lightCol;
}

vec3 calculateSpecularLight(vec3 point, vec3 surfUnitNormal, vec3 unitToLight){
    vec3 unitVectorToCamera = normalize(frCameraPos - point);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, surfUnitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    return dampedFactor* reflectivity * lightCol;
}

void diffuseAndSpecularLighting(out vec3 diffuse, out vec3 specular, vec3 point, vec3 surfUnitNormal){
    vec3 toLightVec = normalize(lightPos - point);
    vec3 unitToLight = normalize(toLightVec);

    // Diffuse lighting
    diffuse = calculateDiffuseLight(surfUnitNormal, unitToLight);
    // Specular lighting
    specular = calculateSpecularLight(point, surfUnitNormal, unitToLight);
}

vec2 getPolarAnglesOfPoint(vec3 point, vec3 sphereCenter, float radius){
    vec3 onSphere = point - sphereCenter;
    // spherical coordinate system angles separating point and reference point
    float phi = acos(onSphere.y / radius);
    float theta = acos(onSphere.x/radius/sin(phi));
    if (onSphere.z > 0){
        theta = -theta;
    }
    // convert angles (-1 to 1) to range (0, 1):
    return vec2(theta, phi);
}

void main(void){
    // Discaring everything outside the sphere surface
    if (dot(coord2d, coord2d) > 1){
        //        out_Color = vec4(0, 0, 0, 1);
        //        gl_FragDepth = 1;
        discard;
    } else {
        vec3 point = findThePointOnSphereSurface();

        // Writing the appropriate fragment depth value to the depth buffer
        gl_FragDepth = calculateFragmentDepth(point);

        // Surface
        vec3 surfUnitNormal = normalize(point - frSphereCenter);// Valid because we're dealing with a sphere
        vec3 diffuse;
        vec3 specular;
        diffuseAndSpecularLighting(diffuse, specular, point, surfUnitNormal);

        // Texture
        vec3 finalColor = color;
        vec2 frPolarAngles2  = getPolarAnglesOfPoint(point, frSphereCenter, frSphereRadius);
        vec2 textureCoords = vec2((frPolarAngles2.x+pi)/2/pi , (frPolarAngles2.y+pi)/pi);

        vec4 textureColor =texture(textureSampler, textureCoords);
        if (frUseTexture){
            finalColor.r = min(textureColor.r, finalColor.r);
            finalColor.g = min(textureColor.g, finalColor.g);
            finalColor.b = min(textureColor.b, finalColor.b);
        }
            out_Color = vec4(diffuse, 1.0)*vec4(finalColor, 1) + vec4(specular, 1.0);
            //        out_Color = vec4(textureCoords.y, 0, 0, 1);

    }
}

