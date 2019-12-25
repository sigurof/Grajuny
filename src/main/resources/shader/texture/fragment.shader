#version 420 core

in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVec;
in vec3 toCameraVec;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightCol;
uniform float shineDamper;
uniform float reflectivity;

void main(void){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, 0.0);
    vec3 diffuse = brightness * lightCol;

    // Specular
    vec3 unitVectorToCamera = normalize(toCameraVec);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor* reflectivity * lightCol;


    out_Color = vec4(diffuse, 1.0)*texture(textureSampler, passTextureCoords) + vec4(finalSpecular, 1.0);
}