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


in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec4 worldPos;

out vec4 out_Color;

uniform vec3 cameraPos;
uniform Material material;

#define MAX_NR_POINT_LIGHTS 10 // can be increased
uniform PointLight lights[MAX_NR_POINT_LIGHTS];
uniform int numberOfPointLights;

vec3 calculateAmbientLight(PointLight light){
    return light.ambient * texture(material.ambient, passTextureCoords).rgb;
}

vec3 calculateDiffuseLight(PointLight light, vec3 unitNormal, vec3 unitToLight){
    float nDotL = dot(unitNormal, unitToLight);
    float brightness = max(nDotL, 0.0);
    return light.diffuse * brightness * texture(material.diffuse, passTextureCoords).rgb;
}

vec3 calculateSpecularLight(PointLight light, vec3 unitNormal, vec3 unitToLight){
    vec3 toCameraVec =  cameraPos - worldPos.xyz;
    vec3 unitVectorToCamera = normalize(toCameraVec);
    vec3 lightDirection = -unitToLight;
    vec3 reflectedLightDir = reflect(lightDirection, unitNormal);
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, material.shininess);// = spec
    return light.specular * dampedFactor * texture(material.specular, passTextureCoords).rgb;
}


vec4 calculatePointLight(PointLight light){
    vec3 toLightVec = light.position - worldPos.xyz;
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitToLight = normalize(toLightVec);

    vec3 ambient = calculateAmbientLight(light);
    vec3 diffuse = calculateDiffuseLight(light, unitNormal, unitToLight);
    vec3 specular = calculateSpecularLight(light, unitNormal, unitToLight);

    float distance = length(light.position - worldPos.xyz);
    float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));

    return vec4((ambient + diffuse + specular)*attenuation, 1);
}

vec4 caluclatePointLights(){
    vec4 finalColor = vec4(0, 0 , 0, 0);
    for (int i = 0; i < numberOfPointLights; i++){
        finalColor += calculatePointLight(lights[i]);
    }
    return finalColor;
}

void main(void){


    out_Color = caluclatePointLights();
}