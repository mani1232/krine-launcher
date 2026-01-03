package cc.worldmandia.krine_launcher.proto

import cc.worldmandia.generated.exportedClasses
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.protobuf.schema.ProtoBufSchemaGenerator
import kotlinx.serialization.serializer

actual object ProtoGenerator {

    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    @JvmStatic
    fun main() {
        val descriptors = exportedClasses.map { it.serializer().descriptor }

        val schema = ProtoBufSchemaGenerator.generateSchemaText(descriptors)

        Path("../proto").let { path ->
            SystemFileSystem.createDirectories(path)
            SystemFileSystem.sink(Path(path, "/models.proto")).buffered().use {
                it.writeString(schema)
            }

            println("> Task :shared:generateProto -> Generated .proto file at: $path")
        }
    }
}