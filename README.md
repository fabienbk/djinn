djinn, the java dependency explorer
===================================

Description
-----------

A visual tool that analyze, and graph java dependencies between jars, classes, packages and projects (currently eclipse projects only).

This tool can be used to :
* Analyze legacy code-bases that do not rely on dependency management systems (such as maven or 
ivy) and then produce information that facilitate migration to these systems;
* Locate, and fix cyclic dependencies;
* Understand how a project is structured to gain comprehension quicker.

There is still a lot of work to do - the tool only provides basic functionality currently.

Building and running
--------------------

The project use maven. It has been tested under Windows and Linux, although it should run wherever swing applications are supported.

To build the runnable jar, simply run:

```bash
mvn compile assembly:single
```

The artifact generated is target/djinn-gui.jar subdirectory. You can run it using :

```bash
java -jar target/djinn-gui.jar
```

If you're a clicky person, flip the executable permission flag, and you should be able to run djinn by double-clicking on the jar, under linux or windows.