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

vec3 calculateDiffuseLight(vec3 unitNormal, vec3 unitToLight){
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, ambient);
    return brightness * lightCol;
}

vec3 calculateSpecularLight(vec3 unitToLight, vec3 unitNormal){
    vec3 unitVectorToCamera = normalize(toCameraVec);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    return dampedFactor* reflectivity * lightCol;
}


void main(void){

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);

    vec3 diffuse = calculateDiffuseLight(unitNormal, unitToLight);

    vec3 specular = calculateSpecularLight(unitToLight, unitNormal);

    // Combine base color with texture color
    vec4 textureColor =texture(textureSampler, passTextureCoords);
    vec3 finalColor;
    finalColor.r = min(textureColor.r, color.r);
    finalColor.g = min(textureColor.g, color.g);
    finalColor.b = min(textureColor.b, color.b);

    out_Color = vec4(diffuse, 1.0)*vec4(finalColor, 1) + vec4(specular, 1.0);
}