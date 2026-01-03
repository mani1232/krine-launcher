package cc.worldmandia

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import java.io.OutputStream

class ClassListProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return ClassListProcessor(environment.codeGenerator)
  }
}

class ClassListProcessor(private val codeGenerator: CodeGenerator) : SymbolProcessor {

  @OptIn(KspExperimental::class)
  override fun process(resolver: Resolver): List<KSAnnotated> {
    val symbols =
        resolver.getSymbolsWithAnnotation("kotlinx.serialization.Serializable")
            .filterIsInstance<KSClassDeclaration>().filter { it.packageName.asString() == "cc.worldmandia.krine_launcher.proto" }

    if (!symbols.iterator().hasNext()) return emptyList()

    val file =
        codeGenerator.createNewFile(
            dependencies = Dependencies.ALL_FILES,
            packageName = "cc.worldmandia.generated",
            fileName = "GeneratedProtoHelper",
        )

    file.writeText(generateCode(symbols))

    return emptyList()
  }

  private fun generateCode(classes: Sequence<KSClassDeclaration>): String {
    val classList =
        classes.joinToString(",\n    ") { declaration ->
          "${declaration.qualifiedName!!.asString()}::class"
        }

    return """|package cc.worldmandia.generated
|
|import kotlin.reflect.KClass
|
|val exportedClasses: List<KClass<*>> = listOf(
|$classList
|)
        """
        .trimMargin()
  }
}

private fun OutputStream.writeText(text: String) {
  this.write(text.toByteArray())
  this.close()
}
