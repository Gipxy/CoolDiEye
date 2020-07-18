# CoolDiEye
Very cool and lightweight DI framework in Java 1.8+

## Design considerations
* For flexible, we are using xml to define the DI configuration, thanks for help of fasterxml framework, it help to simplify the parsing xml file. 
* Start with basic DI feture which is using everyday: nested bean, scope (singleton, prototype) and close (to destroy the container)
* Use Java 1.8 as still have alot of prjects are in java 1.8
* Use very popular testing framework Junit and Mockito for unit testing. 
* Use Java Reflector to create object and call method at runtime


## Limitations
* Only support field-based injection (no constructor-based injection)
* No anotation syntax support
* No cyclic dependency, lazy, primitive value.. supported yet!

## Getting Started / Usage Guide
