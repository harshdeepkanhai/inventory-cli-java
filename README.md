# Inventory CLI

A command-line inventory management system built in Java. Tracks items with name, price, and quantity, and calculates total inventory value.

> **Learning context:** This README compares Java concepts to Python equivalents to help bridge the gap as you learn Java.

---

## Table of Contents

- [Project Structure](#project-structure)
- [Java vs Python: Core Concepts Used Here](#java-vs-python-core-concepts-used-here)
  - [Classes and Objects](#classes-and-objects)
  - [Access Modifiers and Encapsulation](#access-modifiers-and-encapsulation)
  - [Constructors](#constructors)
  - [Types](#types)
  - [String Formatting](#string-formatting)
  - [Packages vs Modules](#packages-vs-modules)
- [Best Practices](#best-practices)
- [Tips and Tricks](#tips-and-tricks)
- [Useful Links](#useful-links)
- [License](#license)

---

## Project Structure

```
inventory-cli/
└── src/
    └── com/harshdeepkanhai/inventory/
        ├── Main.java        # Entry point (like if __name__ == "__main__")
        ├── Item.java        # Data model for a single inventory item
        └── Inventory.java   # Manages the collection of items
```

---

## Java vs Python: Core Concepts Used Here

### Classes and Objects

**Python**
```python
class Item:
    def __init__(self, name, price, quantity):
        self.name = name
        self.price = price
        self.quantity = quantity

    def total_value(self):
        return self.price * self.quantity

    def __str__(self):
        return f"{self.name} | ${self.price:.2f} | qty: {self.quantity}"
```

**Java**
```java
public class Item {
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double totalValue() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s | $%.2f | qty: %d", name, price, quantity);
    }
}
```

**Key differences:**

| Concept | Python | Java |
|---|---|---|
| Class definition | `class Item:` | `public class Item { }` |
| Constructor | `def __init__(self, ...)` | `public Item(...)` — same name as class |
| `self` | explicit `self` parameter | implicit `this` keyword |
| `__str__` | `def __str__(self)` | `@Override public String toString()` |
| Field declaration | just assign in `__init__` | must declare type at top of class |

---

### Access Modifiers and Encapsulation

Python uses convention (`_name` = private by convention). Java enforces it at compile time.

**Python (convention only)**
```python
class Item:
    def __init__(self):
        self._name = "apple"   # "private" by convention, not enforced
        self.price = 1.99      # public
```

**Java (compiler enforced)**
```java
public class Item {
    private String name;    // truly inaccessible outside this class
    public double price;    // accessible anywhere
}
```

> **Best practice:** Always make fields `private` and expose them through getter/setter methods. This is called *encapsulation* — it lets you change internal implementation without breaking callers.

---

### Constructors

Python uses `__init__` and always returns `None`. Java constructors have no return type at all (not even `void`).

**Python**
```python
item = Item("apple", 1.99, 10)   # __init__ called automatically
```

**Java**
```java
Item item = new Item("apple", 1.99, 10);  // constructor called with `new`
```

Note the `new` keyword — Python handles this under the hood via `__new__`.

---

### Types

Java is **statically typed** — every variable must declare its type at compile time. Python is **dynamically typed**.

**Python**
```python
name = "apple"       # type inferred at runtime, can change
price = 1.99
count = 10
```

**Java**
```java
String name = "apple";   // type fixed at compile time
double price = 1.99;
int count = 10;
```

**Primitive types you'll use often:**

| Java type | Python equivalent | Notes |
|---|---|---|
| `int` | `int` | 32-bit, no arbitrary precision |
| `long` | `int` (big) | 64-bit integer |
| `double` | `float` | 64-bit floating point |
| `boolean` | `bool` | `true`/`false` (lowercase in Java) |
| `String` | `str` | Object, not primitive — capital S |
| `char` | `str` of length 1 | Single character |

> **Tip:** Use `var` (Java 10+) to let the compiler infer types locally, just like Python:
> ```java
> var item = new Item("apple", 1.99, 10);  // compiler infers Item type
> ```

---

### String Formatting

**Python**
```python
f"{name} | ${price:.2f} | qty: {quantity}"
```

**Java**
```java
String.format("%s | $%.2f | qty: %d", name, price, quantity)
// Java 15+ (preferred):
"%s | $%.2f | qty: %d".formatted(name, price, quantity)
```

**Format specifiers:**

| Specifier | Meaning |
|---|---|
| `%s` | String |
| `%d` | Integer |
| `%.2f` | Float with 2 decimal places |
| `%n` | Platform newline (use in `printf`) |

---

### Packages vs Modules

**Python modules** are just `.py` files. Import with `import` or `from x import y`.

**Java packages** are namespace declarations that must match the directory structure.

```java
// This file MUST live at src/com/harshdeepkanhai/inventory/Item.java
package com.harshdeepkanhai.inventory;
```

The reverse-domain convention (`com.harshdeepkanhai`) is standard to avoid name collisions globally — similar to how PyPI package names need to be unique.

---

## Best Practices

### Always make fields `private`
```java
// Bad
public String name;

// Good
private String name;
public String getName() { return name; }
```

### Prefer `final` for fields that never change
```java
private final String name;   // set once in constructor, never reassigned
private int quantity;        // mutable — quantity can change
```
Python equivalent: there is no enforcement, but `@dataclass(frozen=True)` approximates it.

### Always override `toString()` on model classes
Debugging with `System.out.println(item)` prints a useless memory address without it.

### Use `@Override` annotation
It tells the compiler you intend to override a parent method. If you typo the method name, the compiler catches it — Python has no equivalent safety net.

### Avoid `System.out.println` in production code
Use a logging framework like [SLF4J](https://www.slf4j.org/) + [Logback](https://logback.qos.ch/) instead — same principle as Python's `logging` module over bare `print`.

### Naming conventions

| Thing | Java | Python |
|---|---|---|
| Classes | `PascalCase` | `PascalCase` |
| Methods / variables | `camelCase` | `snake_case` |
| Constants | `UPPER_SNAKE_CASE` | `UPPER_SNAKE_CASE` |
| Packages | `all.lowercase` | N/A |

---

## Tips and Tricks

**IntelliJ IDEA shortcuts:**

| Shortcut | Action |
|---|---|
| `Alt+Insert` | Generate constructor, getters, setters, toString automatically |
| `Ctrl+P` | Show parameter hints inside a method call |
| `Ctrl+Alt+L` | Auto-format code (like Black/Ruff for Python) |
| `Shift+F6` | Rename symbol everywhere (refactor) |
| `Ctrl+B` | Go to declaration |
| `Ctrl+Alt+V` | Extract expression into a variable |

**Quick Java wins:**

- Use `record` (Java 16+) for pure data classes — auto-generates constructor, getters, `equals`, `hashCode`, and `toString`:
  ```java
  public record Item(String name, double price, int quantity) {}
  ```
  That single line replaces the entire `Item.java`. Use it for immutable data holders.

- Use `List.of(...)` and `Map.of(...)` for quick immutable collections (like Python's `[...]` and `{...}` literals).

- `Optional<T>` is Java's answer to `None` — avoids `NullPointerException` the same way Python's `if x is not None` pattern does, but enforced by the type system.

- Java `for-each` loop looks like Python's `for`:
  ```java
  // Python: for item in items:
  for (Item item : items) {
      System.out.println(item);
  }
  ```

---

## Useful Links

### Java Fundamentals
- [Java 21 Documentation (Oracle)](https://docs.oracle.com/en/java/javase/21/) — official reference
- [Baeldung](https://www.baeldung.com/) — best practical Java tutorials, equivalent to Real Python
- [Java Almanac](https://javaalmanac.io/) — what was added in each Java version

### Java for Python Developers
- [Java for Python Programmers (Runestone)](https://runestone.academy/ns/books/published/java4python/index.html) — free, structured, exactly what you need
- [Java vs Python cheat sheet (Princeton)](https://introcs.cs.princeton.edu/java/11cheatsheet/) — side-by-side syntax reference

### Tools
- [IntelliJ IDEA Keymap PDF](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf) — full shortcut reference
- [sdkman](https://sdkman.io/) — manage Java versions (like pyenv for Python)
- [Maven](https://maven.apache.org/) / [Gradle](https://gradle.org/) — dependency management (like pip + requirements.txt, but more powerful)

### Style and Quality
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [Effective Java, 3rd Edition](https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/) — the definitive senior-engineer Java reference

---

## License

This project is licensed under the GNU General Public License v3.0. See [LICENSE](LICENSE) for details.
