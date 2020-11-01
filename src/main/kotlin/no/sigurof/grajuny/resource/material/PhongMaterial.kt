package no.sigurof.grajuny.resource.material

import no.sigurof.grajuny.resource.texture.TextureManager
import no.sigurof.grajuny.resource.texture.TextureResource
import no.sigurof.grajuny.shader.Shader
import no.sigurof.grajuny.shader.shaders.PhongMeshShader2
import org.joml.Vector3f
import org.lwjgl.opengl.GL30

data class PhongMaterial private constructor(
    val shininess: Float,
    val ambient: Vector3f,
    val diffuse: TextureResource,
    val specular: Vector3f
) : Material {

    constructor(
        ambient: Vector3f,
        diffuse: TextureResource,
        specular: Vector3f,
        shine: Float
    ) : this(
        shininess = shine * 128f,
        ambient = ambient,
        diffuse = diffuse,
        specular = specular
    )

    constructor(
        ambient: Vector3f,
        diffuse: Vector3f,
        specular: Vector3f,
        shine: Float

    ) : this(
        ambient = ambient,
        diffuse = TextureManager.get1x1Texture(diffuse),
        specular = specular,
        shine = shine
    )

    override fun render(shader: Shader) {
        if (shader is PhongMeshShader2) {
            shader.loadMaterial(this)
        }
    }

    override fun activate() {
        GL30.glActiveTexture(GL30.GL_TEXTURE0)
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, this.diffuse.tex)
    }

    override fun deactivate() {
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0)
    }

    companion object {
        val emerald = PhongMaterial(
            ambient = Vector3f(0.0215f, 0.1745f, 0.0215f),
            diffuse = Vector3f(0.07568f, 0.61424f, 0.07568f),
            specular = Vector3f(0.633f, 0.727811f, 0.633f),
            shine = 0.6f
        )
        val jade = PhongMaterial(
            ambient = Vector3f(0.135f, 0.2225f, 0.1575f),
            diffuse = Vector3f(0.54f, 0.89f, 0.63f),
            specular = Vector3f(0.316228f, 0.316228f, 0.316228f),
            shine = 0.1f
        )
        val obsidian = PhongMaterial(
            ambient = Vector3f(0.05375f, 0.05f, 0.06625f),
            diffuse = Vector3f(0.18275f, 0.17f, 0.22525f),
            specular = Vector3f(0.332741f, 0.328634f, 0.346435f),
            shine = 0.3f
        )
        val pearl = PhongMaterial(
            ambient = Vector3f(0.25f, 0.20725f, 0.20725f),
            diffuse = Vector3f(1f, 0.829f, 0.829f),
            specular = Vector3f(0.296648f, 0.296648f, 0.296648f),
            shine = 0.088f
        )
        val ruby = PhongMaterial(
            ambient = Vector3f(0.1745f, 0.01175f, 0.01175f),
            diffuse = Vector3f(0.61424f, 0.04136f, 0.04136f),
            specular = Vector3f(0.727811f, 0.626959f, 0.626959f),
            shine = 0.6f
        )
        val turquoise = PhongMaterial(
            ambient = Vector3f(0.1f, 0.18725f, 0.1745f),
            diffuse = Vector3f(0.396f, 0.74151f, 0.69102f),
            specular = Vector3f(0.297254f, 0.30829f, 0.306678f),
            shine = 0.1f
        )
        val brass = PhongMaterial(
            ambient = Vector3f(0.329412f, 0.223529f, 0.027451f),
            diffuse = Vector3f(0.780392f, 0.568627f, 0.113725f),
            specular = Vector3f(0.992157f, 0.941176f, 0.807843f),
            shine = 0.21794872f
        )
        val bronze = PhongMaterial(
            ambient = Vector3f(0.2125f, 0.1275f, 0.054f),
            diffuse = Vector3f(0.714f, 0.4284f, 0.18144f),
            specular = Vector3f(0.393548f, 0.271906f, 0.166721f),
            shine = 0.2f
        )
        val chrome = PhongMaterial(
            ambient = Vector3f(0.25f, 0.25f, 0.25f),
            diffuse = Vector3f(0.4f, 0.4f, 0.4f),
            specular = Vector3f(0.774597f, 0.774597f, 0.774597f),
            shine = 0.6f
        )
        val copper = PhongMaterial(
            ambient = Vector3f(0.19125f, 0.0735f, 0.0225f),
            diffuse = Vector3f(0.7038f, 0.27048f, 0.0828f),
            specular = Vector3f(0.256777f, 0.137622f, 0.086014f),
            shine = 0.1f
        )
        val gold = PhongMaterial(
            ambient = Vector3f(0.24725f, 0.1995f, 0.0745f),
            diffuse = Vector3f(0.75164f, 0.60648f, 0.22648f),
            specular = Vector3f(0.628281f, 0.555802f, 0.366065f),
            shine = 0.4f
        )
        val silver = PhongMaterial(
            ambient = Vector3f(0.19225f, 0.19225f, 0.19225f),
            diffuse = Vector3f(0.50754f, 0.50754f, 0.50754f),
            specular = Vector3f(0.508273f, 0.508273f, 0.508273f),
            shine = 0.4f
        )
        val blackPlastic = PhongMaterial(
            ambient = Vector3f(0f, 0f, 0f),
            diffuse = Vector3f(0.01f, 0.01f, 0.01f),
            specular = Vector3f(0.5f, 0.5f, 0.5f),
            shine = 0.25f
        )
        val cyanPlastic = PhongMaterial(
            ambient = Vector3f(0f, 0.1f, 0.06f),
            diffuse = Vector3f(0f, 0.50980392f, 0.50980392f),
            specular = Vector3f(0.50196078f, 0.50196078f, 0.50196078f),
            shine = 0.25f
        )
        val greenPlastic = PhongMaterial(
            ambient = Vector3f(0f, 0f, 0f),
            diffuse = Vector3f(0.1f, 0.35f, 0.1f),
            specular = Vector3f(0.45f, 0.55f, 0.45f),
            shine = 0.25f
        )
        val redPlastic = PhongMaterial(
            ambient = Vector3f(0f, 0f, 0f),
            diffuse = Vector3f(0.5f, 0f, 0f),
            specular = Vector3f(0.7f, 0.6f, 0.6f),
            shine = 0.25f
        )
        val whitePlastic = PhongMaterial(
            ambient = Vector3f(0f, 0f, 0f),
            diffuse = Vector3f(0.55f, 0.55f, 0.55f),
            specular = Vector3f(0.7f, 0.7f, 0.7f),
            shine = 0.25f
        )
        val yellowPlastic = PhongMaterial(
            ambient = Vector3f(0f, 0f, 0f),
            diffuse = Vector3f(0.5f, 0.5f, 0f),
            specular = Vector3f(0.6f, 0.6f, 0.5f),
            shine = 0.25f
        )
        val blackRubber = PhongMaterial(
            ambient = Vector3f(0.02f, 0.02f, 0.02f),
            diffuse = Vector3f(0.01f, 0.01f, 0.01f),
            specular = Vector3f(0.4f, 0.4f, 0.4f),
            shine = 0.078125f
        )
        val cyanRubber = PhongMaterial(
            ambient = Vector3f(0f, 0.05f, 0.05f),
            diffuse = Vector3f(0.4f, 0.5f, 0.5f),
            specular = Vector3f(0.04f, 0.7f, 0.7f),
            shine = 0.078125f
        )
        val greenRubber = PhongMaterial(
            ambient = Vector3f(0f, 0.05f, 0f),
            diffuse = Vector3f(0.4f, 0.5f, 0.4f),
            specular = Vector3f(0.04f, 0.7f, 0.04f),
            shine = 0.078125f
        )
        val redRubber = PhongMaterial(
            ambient = Vector3f(0.05f, 0f, 0f),
            diffuse = Vector3f(0.5f, 0.4f, 0.4f),
            specular = Vector3f(0.7f, 0.04f, 0.04f),
            shine = 0.078125f
        )
        val whiteRubber = PhongMaterial(
            ambient = Vector3f(0.05f, 0.05f, 0.05f),
            diffuse = Vector3f(0.5f, 0.5f, 0.5f),
            specular = Vector3f(0.7f, 0.7f, 0.7f),
            shine = 0.078125f
        )
        val yellowRubber = PhongMaterial(
            ambient = Vector3f(0.05f, 0.05f, 0f),
            diffuse = Vector3f(0.5f, 0.5f, 0.4f),
            specular = Vector3f(0.7f, 0.7f, 0.04f),
            shine = 0.078125f
        )

    }
}