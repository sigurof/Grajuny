package no.sigurof.tutorial.lwjgl.scenario

interface Scenario {

    fun prepare()

    fun run()

    fun cleanUp()
}