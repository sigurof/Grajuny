package no.sigurof.grajuny.experimental2

object CameraManager {
/*
    private var firstMouse: Boolean = true
    private var lastX: Double = 400.0
    private var lastY: Double = 300.0
    private val up: Vector3f = Vector3f(0f, 1f, 0f)
    private var fwAxis: Vector3f? = null// = lookingAt.sub(pos, Vector3f()).normalize()
    private var rtAxis: Vector3f? = null//fwAxis.cross(up, Vector3f()).normalize() // left hand rule?, not right hand rule?
    private var upAxis: Vector3f? = null//fwAxis.cross(rtAxis, Vector3f()).negate().normalize()

    private fun setCamera(cameraData: CameraData) {
         = lookingAt.sub(pos, Vector3f()).normalize()
        fwAxis.cross(up, Vector3f()).normalize() // left hand rule?, not right hand rule?
    }   fwAxis.cross(rtAxis, Vector3f()).negate().normalize()

    private fun setCursorPosCallback(window: Long) {
        GLFW.glfwSetCursorPosCallback(window, ::mouseCallback)
    }

    private fun mouseCallback(window: Long, xpos: Double, ypos: Double): Unit {
        if (firstMouse) {
            lastX = xpos
            lastY = ypos
            firstMouse = false
        }
        var xoffset = xpos - lastX
        var yoffset = lastY - ypos
        lastX = xpos
        lastY = ypos
        val sensitivity = 0.005f
        xoffset *= sensitivity
        yoffset *= sensitivity
        recalculateAxes(xoffset, yoffset)
    }

    private fun recalculateAxes(xoffset: Double, yoffset: Double) {
        val newFwAxis = Vector3f(fwAxis)
        newFwAxis.add(Vector3f(upAxis).mul(yoffset.toFloat()))
        newFwAxis.add(Vector3f(rtAxis).mul(xoffset.toFloat()))
        newFwAxis.normalize()
        fwAxis.set(newFwAxis)
        rtAxis = fwAxis.cross(up, Vector3f()).normalize()
        upAxis = fwAxis.cross(rtAxis, Vector3f()).negate().normalize()
    }*/
}