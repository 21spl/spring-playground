# Spring Boot Learning Labs 🚀

A collection of isolated hands-on labs exploring Java backend internals,
dependency management, and core software architecture patterns —
built from scratch, broken on purpose, understood for real.

## Lab Directory

| Lab                                                                                          | Concept                            | Key Takeaway                                                                                                                                             |
| :------------------------------------------------------------------------------------------- | :--------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`01-tight-coupling-pain`](./01-tight-coupling-pain)                                         | Tight Coupling                     | Why `new` inside a class makes unit testing impossible.                                                                                                  |
| [`02-decoupling-with-interface`](./02-decoupling-with-interface)                             | Interface + Constructor Injection  | How to decouple code manually without relying on any framework.                                                                                          |
| [`03-wiring-with-spring`](./03-wiring-with-spring)                                           | Java `@Configuration` & Beans      | Offloading the lifecycle and assembly of objects entirely to the Spring IoC Container.                                                                   |
| [`04-cglib-singleton-behavior`](./04-cglib-singleton-behavior)                               | CGLIB Runtime Proxies              | Why multiple calls to bean methods in a `@Configuration` class still yield singletons.                                                                   |
| [`05-unpack-SpringBootApplication-annotation`](./05-unpack-SpringBootApplication-annotation) | Convenience Meta-Annotations       | Unpacking `@SpringBootApplication` into its three distinct modular sub-annotations manually.                                                             |
| [`07-feel-the-fat-jar`](./07-feel-the-fat-jar)                                               | Thin vs. Executable Fat JAR        | Demystifying deployment mechanics and Spring's custom `JarLauncher` embedded structure.                                                                  |
| [`08-prove-test-starter-exclusion`](./08-prove-test-starter-exclusion)                       | Maven Dependency Scopes            | Verifying that development testing suites (`scope=test`) are completely stripped from production binaries.                                               |
| [`17-jpa-with-hibernate`](./17-jpa-with-hibernate)                                           | Plain JPA + Hibernate Fundamentals | Bootstrapping JPA from scratch, configuring `persistence.xml`, managing entities and transactions, and understanding dirty checking without Spring Boot. |


## Philosophy

Each lab intentionally starts with the wrong approach.
The pain of bad design is the lesson — not just the solution.

## Related

📝 Full writeups on every lab:
[brokenbydesign.hashnode.dev](https://brokenbydesign.hashnode.dev)
