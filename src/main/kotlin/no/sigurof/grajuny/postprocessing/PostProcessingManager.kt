package no.sigurof.grajuny.postprocessing

object PostProcessingManager {

    var activeEffect: PostProcessingEffect? = null
        private set

    fun addEffect(effect: PostProcessingEffect) {
        activeEffect?.destroy()
        activeEffect = effect
        activeEffect?.initialize()
    }

}