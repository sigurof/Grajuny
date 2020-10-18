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
uniform bool useTexture;
uniform bool useSpecular;
uniform bool useDiffuse;

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


vec4 addDiffuseAndSpecular(vec3 finalColor){
    // Surface
    vec4 outColor = vec4(finalColor, 1);

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);



    if (useDiffuse){
        vec3 diffuse = calculateDiffuseLight(unitNormal, unitToLight);
        outColor = vec4(diffuse, 1.0) * outColor;
    }
    if (useSpecular){
        vec3 specular = calculateSpecularLight(unitToLight, unitNormal);
        outColor = outColor + vec4(specular, 1.0);
    }
    return outColor;
}


void main(void){

    // Combine base color with texture color
    vec3 finalColor = color;
    vec4 textureColor =texture(textureSampler, passTextureCoords);
    if (useTexture){
        finalColor.r = min(textureColor.r, color.r);
        finalColor.g = min(textureColor.g, color.g);
        finalColor.b = min(textureColor.b, color.b);
    }
    out_Color = addDiffuseAndSpecular(finalColor);
}