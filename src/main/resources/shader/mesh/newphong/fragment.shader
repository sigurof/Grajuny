#version 420 core

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
};

struct Light {
    vec3 position;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};


in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVec;
in vec3 toCameraVec;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 color;

uniform Material material;
uniform Light light;

vec3 calculateDiffuseLight(vec3 unitNormal, vec3 unitToLight){
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, 0.0);
    return light.diffuse * (brightness * material.diffuse);
}

vec3 calculateSpecularLight(vec3 unitNormal, vec3 unitToLight){
    vec3 unitVectorToCamera = normalize(toCameraVec);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, material.shininess);// = spec
    //    return dampedFactor * reflectivity * lightCol;// = spec * specularStrength * lightColor
    return light.specular * (dampedFactor * material.specular);
}


vec4 addPhongLighting(){
    // Surface

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);

    //    vec3 ambient = ambientStrength * lightCol;
    vec3 ambient = light.ambient * material.ambient;

    vec3 diffuse =  calculateDiffuseLight(unitNormal, unitToLight);
    vec3 specular = calculateSpecularLight(unitNormal, unitToLight);

    return vec4(ambient + diffuse + specular, 1);
}


void main(void){

    out_Color = addPhongLighting();
}