///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.launchers

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Modules
import kz.greetgo.ts_java_convert.ConvertModelBuilder

object LaunchModelConverter {

  @JvmStatic
  fun main(args: Array<String>) {
    val sourceDir = Modules.clientDir().toPath().resolve("src").toFile()
    val destinationDir = Modules.controllerDir().toPath().resolve("src").toFile()
///MODIFY replace sandbox {PROJECT_NAME}
    val destinationPackage = "kz.greetgo.sandbox.controller.model"

    ConvertModelBuilder()
      .sourceDir(sourceDir, "model")
      .destinationDir(destinationDir)
      .destinationPackage(destinationPackage)
      .create()
      .execute()
  }

}
