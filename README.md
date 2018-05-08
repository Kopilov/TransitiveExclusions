# TransitiveExclusions

## Example set to reproduce Eclipse/Ceylon [#7377](https://github.com/eclipse/ceylon/issues/7377) issue

`ignite-examples_dependency` directory contains large set of Apache Ignite examples
downloaded together with Ignite itself (from https://ignite.apache.org/download.cgi#binaries webpage,
apache-ignite-fabric-2.4.0-bin.zip file). Here it is used as Java project that is built with Maven
and has many transitive dependencies. Also it definitly works and has trivial API (main method).  
**Run `mvn install` in this directory**

`java_dependent` is the simplest Java project that depends on ignite-examples.  
**Run `mvn compile && mvn exec:java -Dexec.mainClass="test.igniteprobejava.Main"` in this directory for test execution.**  
It works.

`ceylon_dependent` is Ceylon project equivalent to java_dependent.  
**Run `ceylon compile && ceylon run test.igniteprobeceylon` in this directory for test execution.**  
We get the error:
```
Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/commons/logging/LogFactory
        at org.springframework.context.support.AbstractApplicationContext.<init>(AbstractApplicationContext.java:161)
        at org.springframework.context.support.GenericApplicationContext.<init>(GenericApplicationContext.java:104)
        at org.apache.ignite.internal.util.spring.IgniteSpringHelperImpl.prepareSpringContext(IgniteSpringHelperImpl.java:439)
        at org.apache.ignite.internal.util.spring.IgniteSpringHelperImpl.applicationContext(IgniteSpringHelperImpl.java:377)
        at org.apache.ignite.internal.util.spring.IgniteSpringHelperImpl.loadConfigurations(IgniteSpringHelperImpl.java:104)
        at org.apache.ignite.internal.util.spring.IgniteSpringHelperImpl.loadConfigurations(IgniteSpringHelperImpl.java:98)
        at org.apache.ignite.internal.IgnitionEx.loadConfigurations(IgnitionEx.java:737)
        at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:938)
        at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:847)
        at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:717)
        at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:686)
        at org.apache.ignite.Ignition.start(Ignition.java:347)
        at org.apache.ignite.examples.sql.SqlDdlExample.main(SqlDdlExample.java:49)
        at test.igniteprobeceylon.run_.run(run.ceylon:5)
        at test.igniteprobeceylon.run_.main(run.ceylon)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.redhat.ceylon.compiler.java.runtime.tools.impl.JavaRunnerImpl.invokeMain(JavaRunnerImpl.java:93)
        at com.redhat.ceylon.compiler.java.runtime.tools.impl.JavaRunnerImpl.run(JavaRunnerImpl.java:61)
        at ceylon.modules.bootstrap.CeylonRunTool.startInFlatClasspath(CeylonRunTool.java:424)
        at ceylon.modules.bootstrap.CeylonRunTool.run(CeylonRunTool.java:289)
        at com.redhat.ceylon.common.tools.CeylonTool.run(CeylonTool.java:547)
        at com.redhat.ceylon.common.tools.CeylonTool.execute(CeylonTool.java:423)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.redhat.ceylon.launcher.Launcher.runInJava7Checked(Launcher.java:108)
        at com.redhat.ceylon.launcher.Launcher.run(Launcher.java:38)
        at com.redhat.ceylon.launcher.Launcher.run(Launcher.java:31)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.redhat.ceylon.launcher.Bootstrap.runVersion(Bootstrap.java:162)
        at com.redhat.ceylon.launcher.Bootstrap.runInternal(Bootstrap.java:117)
        at com.redhat.ceylon.launcher.Bootstrap.run(Bootstrap.java:93)
        at com.redhat.ceylon.launcher.Bootstrap.main(Bootstrap.java:85)
Caused by: java.lang.ClassNotFoundException: org.apache.commons.logging.LogFactory
        at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
        ... 40 more
```

Notice that `org.apache.commons.logging.LogFactory` class depends to `commons-logging:commons-logging` module.
This module is among other in `mvn dependency:list` output for ignite-examples or java_dependent project.

It can be found in the dependency branch `org.apache.ignite:ignite-examples -> org.apache.ignite:ignite-spring -> commons-logging:commons-logging-1.1.1`

But also we have another dependency branch, `org.apache.ignite:ignite-examples -> org.apache.ignite:ignite-spring-data -> org.springframework.data:spring-data-commons -> org.springframework:spring-core`,
where commons-logging:commons-logging is marked as exclusion.
