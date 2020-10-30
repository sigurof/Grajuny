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
uniform float ambientStrength;
uniform vec3 color;
uniform bool useTexture;
uniform bool useSpecular;
uniform bool useDiffuse;

vec3 calculateDiffuseLight(vec3 unitNormal, vec3 unitToLight){
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, 0.0);
    return brightness * lightCol;
}

vec3 calculateSpecularLight(vec3 unitToLight, vec3 unitNormal){
    vec3 unitVectorToCamera = normalize(toCameraVec);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper); // = spec
    return dampedFactor* reflectivity * lightCol; // = spec * specularStrength * lightColor
}


vec4 addPhongLighting(vec3 color){
    // Surface

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);

    vec3 ambient = ambientStrength * lightCol;
    vec3 diffuse = vec3(0, 0, 0);
    vec3 specular = vec3(0, 0, 0);

    if (useDiffuse){
        diffuse = calculateDiffuseLight(unitNormal, unitToLight);
    }
    if (useSpecular){
        specular = calculateSpecularLight(unitToLight, unitNormal);
    }
    return vec4((ambient + diffuse + specular) * color, 1);
}

vec3 blend(vec3 color1, vec3 color2){
    vec3 blended = color1;
    blended.r = min(color1.r, color2.r);
    blended.g = min(color1.g, color2.g);
    blended.b = min(color1.b, color2.b);
    return blended;
}


void main(void){

    // Combine base color with texture color
    vec3 fragColor;
    if (useTexture){
        fragColor = blend(color, texture(textureSampler, passTextureCoords).rgb);
    } else {
        fragColor = color;
    }
    out_Color = addPhongLighting(fragColor);
}