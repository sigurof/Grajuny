package no.sigurof.tutorial.lwjgl.resource

interface ResourceGl {
    fun render()
    fun prepare()
    fun getVao() : Int
}