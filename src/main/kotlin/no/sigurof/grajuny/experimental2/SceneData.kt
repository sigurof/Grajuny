package no.sigurof.grajuny.experimental2

import org.joml.Vector4f

class SceneData(
    val backgroundColor: Vector4f,
    val materials: MutableList<MaterialData>,
    val meshes: MutableList<MeshData>,
    val cameras: MutableList<CameraData>,
    val transformations: MutableList<TransformData>,
    val instances: MutableList<InstanceData>,
    val lights: MutableList<LightData>,
    val mainCamera: CameraData
)
