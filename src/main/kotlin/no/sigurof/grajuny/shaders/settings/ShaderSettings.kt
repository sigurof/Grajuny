package no.sigurof.grajuny.shaders.settings

interface ShaderSettings {
    val program: Int
    val attributes: List<Pair<Int, String>>
}