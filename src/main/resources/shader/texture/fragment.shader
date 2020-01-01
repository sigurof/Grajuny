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
uniform float ambient;
uniform vec3 color;

void main(void){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, ambient);
    vec3 diffuse = brightness * lightCol;

    // Specular
    vec3 unitVectorToCamera = normalize(toCameraVec);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor* reflectivity * lightCol;

    // Combine base color with texture color
    vec4 textureColor =texture(textureSampler, passTextureCoords);
    vec3 finalColor;
    finalColor.r = min(textureColor.r, color.r);
    finalColor.g = min(textureColor.g, color.g);
    finalColor.b = min(textureColor.b, color.b);

    out_Color = vec4(diffuse, 1.0)*vec4(finalColor, 1) + vec4(finalSpecular, 1.0);
}