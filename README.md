# Simple-Script-Prototype
This is a prototype for a scripting language that is compiled and uses a vm to run.
This will eventually be rewritten in a lower level language like rust, c, or c++.

### Goals
- Only primitive data types and arrays.
- No structs or objects.
- Compile to a data structure that the runtime (vm) can run.
- Include other files.
- Java-ish like syntax.
- Allow for easy inegration of the vm into other programs.
- Minimal dependencies for the compiler and runtime.

### Partial TODO
- Allow for control flow structures.
- Compile a file to a list of instructions.
- Develop system lib.
- Allow for low level libs.
- Add a cache. (To allow for holding of data for other instructions)
- Add more comments.
- Allow for equations.