# Simple-Script-Prototype
This is a prototype for a scripting language that is compiled and uses a vm to run.
This will eventually be rewritten in a lower level language like rust, c, or c++.

### Goals
- Only primitive data types and arrays.
- No structs or objects.
- Compile to a data structure that the runtime (vm) can run.
- Include other files.
- Java-ish like syntax.
- Allow for easy integration of the vm into other programs.
- Minimal dependencies for the compiler and runtime.
- Safety through the use of a compiler, and the ban on null values.
- Security and safety of the vm/runtime.

### Partial TODO
- Allow for control flow structures.
- Compile a file to a list of instructions.
- Develop system lib.
- Allow for low level libs.
- Add a cache. (To allow for holding of data for other instructions)
- Add more comments.
- Allow for equations.
- Remove the use of asserts for production code.

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
TODO Add.