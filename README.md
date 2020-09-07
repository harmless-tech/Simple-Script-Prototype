# Simple-Script-Prototype
This is a prototype for a scripting language that is compiled and uses a vm to run.
This will eventually be rewritten in a lower level language like rust, c, or c++.

[Main Repo](https://github.com/harmless-tech/Simple-Script)

### Goals
- Only primitive data types and arrays.
- Data structures like tuples, arrays, and matrixes.
- No structs or objects.
- Compile to a data structure that the runtime (vm) can run.
- Ability to import other script files.
- Java-ish like syntax.
- Allow for easy integration of the vm/compiler into other programs.
- Minimal dependencies for the compiler and runtime.
- Safety through the use of a compiler, and the ban on null values.
- Safety of the vm/runtime created by compiler checks.
- Vm/runtime basic security by use of private/final data.
- Allow a heap to hold custom data structures, that will have a long pointer to them. (Part of native extensions)

### Partial TODO
- Compiler Rewrite.
- Try to hit test/src3 milestone.

- Allow for operations. (Arithmetic, Relational, Logic)
- Allow for control flow structures. (if, while, for)
- Array and Tuple type implementation.
- Develop system lib.
- Make the compiler do safety checks.
- Allow for the runtime to be integrated into other apps.
- Add more comments.
- Remove the use of asserts for production code.
- Allow for low level libs.
- Compiler/Runtime arguments.

### Notes
- Maybe the compiler should format the code into a multiline String before processing.
- May need to reduce milestone 3, compiler is needing to be more complex than expected.
- Maybe remove scopes within methods for this milestone.
- Maybe turn the necessary spaces into a reserved char to make processing easier.
- Is removing the unncessary spaces going to help down the line?

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