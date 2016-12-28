ResourceHelper
===================
Code that allows resources (e.g. .properties files, .png images, etc.) to
be both (1) included in your deployment jar/war/ear and (2) overridden by
files on your filesystem.

Allows the locations to be prioritized.

Allows locations to be files within .jar files.

Specifically, solves this problem:
 * At the start of your project, you want resources from the filesystem,
   specifically src/main/resources.
 * Later, when you deploy the project, you want resources from the deployed
   .jar file, but you also want overridden properties read from the
   /etc/app/myapp.properties


