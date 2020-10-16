package no.sigurof.grajuny.node

import no.sigurof.grajuny.shader.Shader
import org.joml.Matrix4f
import org.joml.Vector3f

class GameObject private constructor(
    private var parent: GameObject? = null,
    val children: MutableList<GameObject> = mutableListOf(),
    val components: MutableList<GameComponent> = mutableListOf(),
    var transform: Matrix4f = Matrix4f().identity()
) {

    fun addChild(gameObject: GameObject) {
        gameObject.parent = this
        this.children.add(gameObject)
    }

    fun addGameComponent(gameComponent: GameComponent) {
        gameComponent.parent = this
        this.components.add(gameComponent)
    }

    fun update() {
        children.forEach { it.update() }
        components.forEach { it.update() }
    }

    fun input() {
        children.forEach { it.input() }
        components.forEach { it.input() }
    }

    fun render(shader: Shader, parentTransform: Matrix4f) {
        val compositeTransform = parentTransform.mul(transform, Matrix4f())
        children.forEach { it.render(shader, compositeTransform) }
        components.forEach { it.render(shader, compositeTransform) }
    }

    fun rotate(angle: Float, axis: Vector3f) {
        transform = transform.rotate(angle, axis)
    }

    fun getRoot(): GameObject {
        return parent?.getRoot() ?: this
    }

    fun getPosition(): Vector3f {
        return transform.getColumn(3, Vector3f())
    }

    data class Builder(
        val children: MutableList<GameObject> = mutableListOf(),
        val components: MutableList<GameComponent> = mutableListOf(),
        var transform: Matrix4f = Matrix4f().identity()
    ) {
        fun at(absolutePosition: Vector3f): Builder {
            transform.translate(absolutePosition)
            return this
        }

        fun build(): GameObject {
            return if (children.isEmpty() and components.isEmpty())
                error("")
            else GameObject(
                children = children,
                components = components,
                transform = transform
            )
        }
    }

    companion object {
        fun buildRoot(): GameObject {
            return GameObject()
        }

        fun withComponents(components: List<GameComponent>): Builder {
            return Builder(
                components = components.toMutableList()
            )
        }

        fun withComponent(component: GameComponent): Builder {
            return withComponents(mutableListOf(component))
        }

        fun withChildren(children: List<GameObject>): Builder {
            return Builder(
                children = children.toMutableList()
            )
        }

        fun withChild(child: GameObject): Builder {
            return withChildren(mutableListOf(child))
        }
    }

}