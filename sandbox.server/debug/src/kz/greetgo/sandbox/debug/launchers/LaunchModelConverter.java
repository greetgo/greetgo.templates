///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.launchers;

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Modules;
import kz.greetgo.ts_java_convert.ConvertModelBuilder;

import java.io.File;

public class LaunchModelConverter {
  public static void main(String[] args) throws Exception {
    File sourceDir = Modules.INSTANCE.clientDir().toPath()
      .resolve("src").toFile();
    File destinationDir = Modules.INSTANCE.controllerDir().toPath()
      .resolve("src").toFile();
///MODIFY replace sandbox {PROJECT_NAME}
    String destinationPackage = "kz.greetgo.sandbox.controller.model";

    new ConvertModelBuilder()
      .sourceDir(sourceDir, "model")
      .destinationDir(destinationDir)
      .destinationPackage(destinationPackage)
      .create().execute();
  }
}
