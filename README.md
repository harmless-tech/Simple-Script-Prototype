# Simple-Script-Prototype
This is a prototype for a scripting language that is compiled and uses a vm to run.
This will eventually be rewritten in a lower level language like rust, c, or c++.

### Language Spec
TODO Add url to language spec.

### Goals
- Only primitive data types and arrays.
- No structs or objects.
- Compile to a data structure that the runtime (vm) can run.
- Ability to include other files.
- Java-ish like syntax.
- Allow for easy integration of the vm into other programs.
- Minimal dependencies for the compiler and runtime.
- Safety through the use of a compiler, and the ban on null values.
- Security and safety of the vm/runtime. (All vars should be has private and final has possible)

### Partial TODO
- Allow for operations. (Arithmetic, Relational, Logic)
- Allow for control flow structures. (if, while, for)
- Array and Tuple type implementation.
- Start working on the compiler.
- Develop system lib.
- Allow for the runtime to be integrated.
- Add more comments.
- Remove the use of asserts for production code.
- Allow for low level libs.

### Running the code
##### Idea
1. Clone the repo and open the folder in idea.
2. Add two gradle configs for the tasks ```run``` and ```debug```.
3. Press the run button with either config selected.

##### Gradle 
1. Clone the repo.
2. Open a cmd prompt, terminal, or powershell window.
3. Type ```gradlew run``` to run without assertions. 
   Type ```gradlew debug``` to run with assertions.

##### Eclipse
TODO Add instructions.

### Building
###### Warning the build process is currently windows only!
- Run ```gradlew jlink``` to build an application image.
- Run ```gradlew jpack``` to build an installer.
TODO Add.