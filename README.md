# Inventory CLI

A command-line inventory management system built in Java. Add, remove, search, and update items — each with a name, price, and quantity — and see the total inventory value at a glance.

> **Learning context:** This README compares Java concepts to Python equivalents to help bridge the gap as you learn Java.

---

## Table of Contents

- [How to Run](#how-to-run)
- [Available Commands](#available-commands)
- [Project Structure](#project-structure)
- [Java vs Python: Core Concepts Used Here](#java-vs-python-core-concepts-used-here)
  - [Classes and Objects](#classes-and-objects)
  - [Access Modifiers and Encapsulation](#access-modifiers-and-encapsulation)
  - [Constructors](#constructors)
  - [Types and Static Typing](#types-and-static-typing)
  - [String Formatting](#string-formatting)
  - [HashMap — Python dict equivalent](#hashmap--python-dict-equivalent)
  - [Optional — Python None equivalent](#optional--python-none-equivalent)
  - [Streams and Method References](#streams-and-method-references)
  - [Lambdas](#lambdas)
  - [Switch Expressions](#switch-expressions)
  - [Scanner — Python input() equivalent](#scanner--python-input-equivalent)
  - [Exception Handling](#exception-handling)
  - [Static Methods](#static-methods)
  - [Packages vs Modules](#packages-vs-modules)
- [Best Practices](#best-practices)
- [Tips and Tricks](#tips-and-tricks)
- [Useful Links](#useful-links)
- [License](#license)

---

## How to Run

**IntelliJ IDEA:** Open `Main.java` and click the green Run arrow, or press `Shift+F10`.

**Terminal (after compiling):**
```bash
javac -d out src/com/harshdeepkanhai/inventory/*.java
java -cp out com.harshdeepkanhai.inventory.Main
```

---

## Available Commands

Once running, type any of these at the `>` prompt:

| Command | Description |
|---|---|
| `add` | Add a new item (prompts for name, price, quantity) |
| `remove` | Remove an item by name |
| `update` | Update the quantity of an existing item |
| `search` | Find an item by name |
| `list` | List all items and total inventory value |
| `help` | Show the command menu |
| `quit` | Exit the program |

---

## Project Structure

```
inventory-cli/
└── src/
    └── com/harshdeepkanhai/inventory/
        ├── Main.java        # Entry point — REPL loop + CLI command handlers
        ├── Item.java        # Data model: name, price, quantity, totalValue()
        └── Inventory.java   # Business logic: HashMap store, add/remove/find/update/list
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

**Java** (`Item.java`)
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
self._name = "apple"   # "private" by convention — nothing stops outside access
self.price = 1.99      # public
```

**Java (compiler enforced)**
```java
private String name;    // truly inaccessible outside this class
public double price;    // accessible anywhere
```

> **Best practice:** Always make fields `private` and expose them through getters/setters. This lets you change the internal implementation later without breaking callers.

---

### Constructors

Python uses `__init__` and always returns `None`. Java constructors have no return type at all (not even `void`).

**Python**
```python
item = Item("apple", 1.99, 10)   # __init__ called automatically
```

**Java**
```java
Item item = new Item("apple", 1.99, 10);  // requires the `new` keyword
```

Python handles the `new` equivalent under the hood via `__new__`.

---

### Types and Static Typing

Java is **statically typed** — every variable must declare its type at compile time. Python is **dynamically typed**.

**Python**
```python
name = "apple"    # type inferred at runtime, can be reassigned to anything
price = 1.99
count = 10
```

**Java**
```java
String name = "apple";   // locked to String at compile time
double price = 1.99;
int count = 10;
```

**Primitive types you'll use often:**

| Java type | Python equivalent | Notes |
|---|---|---|
| `int` | `int` | 32-bit, no arbitrary precision |
| `long` | `int` (big) | 64-bit integer |
| `double` | `float` | 64-bit floating point |
| `boolean` | `bool` | `true`/`false` — lowercase in Java |
| `String` | `str` | Object, not primitive — capital S |
| `char` | `str` of length 1 | Single character |

> **Tip:** Use `var` (Java 10+) to let the compiler infer types locally, like Python:
> ```java
> var item = new Item("apple", 1.99, 10);  // compiler knows this is Item
> ```

---

### HashMap — Python dict equivalent

`Inventory.java` uses a `HashMap<String, Item>` as its data store — this is Java's direct equivalent of a Python `dict`.

**Python**
```python
items = {}                          # dict
items["apple"] = item_obj           # set
item = items.get("apple")           # get (returns None if missing)
"apple" in items                    # membership check
del items["apple"]                  # remove
items.values()                      # all values
```

**Java**
```java
Map<String, Item> items = new HashMap<>();
items.put("apple", item);           // set
items.get("apple");                 // get (returns null if missing)
items.containsKey("apple");         // membership check
items.remove("apple");              // remove — also returns the removed value
items.values();                     // all values
```

**Key differences:**

| Operation | Python dict | Java HashMap |
|---|---|---|
| Type declaration | none needed | `Map<String, Item>` — key and value types declared |
| Missing key get | returns `None` | returns `null` |
| Remove + get value | `items.pop("k")` | `items.remove("k")` — returns removed value |
| Iteration | `for k, v in items.items()` | `for (var entry : items.entrySet())` |

> **Why `Map<String, Item>` not `HashMap<String, Item>` for the variable type?**  
> Program to the interface, not the implementation. If you later switch from `HashMap` to `TreeMap`, only one line changes.

---

### Optional — Python None equivalent

`findItem()` returns `Optional<Item>` instead of `Item` (which could be `null`). This forces the caller to handle the "not found" case explicitly.

**Python**
```python
def find_item(self, name):
    return self.items.get(name)     # returns None if missing

item = inventory.find_item("apple")
if item is not None:
    print(item)
else:
    print("not found")
```

**Java**
```java
public Optional<Item> findItem(String name) {
    return Optional.ofNullable(items.get(name.toLowerCase()));
}

// Caller — can't accidentally use a null value
inventory.findItem("apple").ifPresentOrElse(
    item -> System.out.println("Found: " + item),
    () -> System.out.println("not found")
);
```

`Optional` makes "this might not exist" visible in the type signature — you cannot call methods on it without unwrapping first. Python's equivalent would be using `typing.Optional[Item]` as a type hint, but nothing enforces the check.

---

### Streams and Method References

`listAll()` uses the Stream API to sum total value. This is Java's equivalent of Python's `map`, `filter`, and `sum` — but chainable and lazy.

**Python**
```python
total = sum(item.total_value() for item in items.values())

# or with map:
total = sum(map(lambda item: item.total_value(), items.values()))

# print all:
for item in items.values():
    print(item)
```

**Java**
```java
double total = items.values().stream()
        .mapToDouble(Item::totalValue)   // method reference
        .sum();

// print all with method reference:
items.values().forEach(System.out::println);
```

**Method references** (`Item::totalValue`, `System.out::println`) are shorthand for a lambda where you just call one method:

| Method reference | Equivalent lambda |
|---|---|
| `Item::totalValue` | `item -> item.totalValue()` |
| `System.out::println` | `x -> System.out.println(x)` |
| `String::toLowerCase` | `s -> s.toLowerCase()` |

**Stream operations:**

| Java stream | Python equivalent |
|---|---|
| `.filter(x -> x > 0)` | `filter(lambda x: x > 0, items)` |
| `.map(Item::getName)` | `map(lambda i: i.name, items)` |
| `.mapToDouble(...)` | `map(float, ...)` |
| `.sum()` | `sum(...)` |
| `.collect(Collectors.toList())` | `list(...)` |
| `.forEach(...)` | `for x in ...: ...` |

---

### Lambdas

Java lambdas use `->` just like Python, but the syntax differs slightly.

**Python**
```python
fn = lambda x: x * 2
items.sort(key=lambda item: item.price)
```

**Java**
```java
// Single expression
Function<Integer, Integer> fn = x -> x * 2;

// Used inline (e.g., in ifPresentOrElse):
item -> System.out.println("Found: " + item)

// Multi-line lambda needs braces and return:
item -> {
    item.setQuantity(newQty);
    System.out.println("Updated: " + item);
}
```

Java lambdas are backed by functional interfaces (`Runnable`, `Consumer<T>`, `Function<T,R>`, etc.). Python lambdas are just anonymous functions with no such constraint.

---

### Switch Expressions

`Main.java` uses Java 14+ **switch expressions** with arrow `->` syntax — much cleaner than the old `switch` statement.

**Python**
```python
match command:
    case "add":    handle_add()
    case "remove": handle_remove()
    case _:        print("Unknown command")
```

**Java (arrow switch — Java 14+)**
```java
switch (command) {
    case "add"    -> handleAdd(scanner, inventory);
    case "remove" -> handleRemove(scanner, inventory);
    case "quit"   -> {
        running = false;        // block needed for multiple statements
        System.out.println("Bye!");
    }
    default -> System.out.println("Unknown command. Type 'help'.");
}
```

The arrow syntax eliminates the infamous `break` fall-through bug of the old `switch` statement. Each arm is independent.

---

### Scanner — Python input() equivalent

**Python**
```python
command = input("> ").strip().lower()
name    = input("Name: ").strip()
price   = float(input("Price: ").strip())
```

**Java**
```java
Scanner scanner = new Scanner(System.in);
String command = scanner.nextLine().trim().toLowerCase();
String name    = scanner.nextLine().trim();
double price   = Double.parseDouble(scanner.nextLine().trim());
```

> **Always close** `Scanner` when done — `scanner.close()` — to release the underlying stream. Python's `input()` is managed for you.

---

### Exception Handling

Java uses checked and unchecked exceptions. Parsing invalid input throws `NumberFormatException` (unchecked), equivalent to Python's `ValueError`.

**Python**
```python
try:
    price = float(input("Price: "))
except ValueError:
    print("Invalid number.")
```

**Java**
```java
try {
    double price = Double.parseDouble(scanner.nextLine().trim());
} catch (NumberFormatException e) {
    System.out.println("Invalid number, item not added.");
}
```

| Concept | Python | Java |
|---|---|---|
| Keyword | `try / except` | `try / catch` |
| Finally block | `finally:` | `finally { }` |
| Raise/throw | `raise ValueError("msg")` | `throw new IllegalArgumentException("msg")` |
| Custom exception | `class MyError(Exception):` | `class MyException extends RuntimeException { }` |

---

### Static Methods

The `handle*` methods in `Main.java` are `private static` — they belong to the class, not to an instance. Python has `@staticmethod` for the same idea.

**Python**
```python
class Main:
    @staticmethod
    def handle_add(scanner, inventory):
        ...
```

**Java**
```java
private static void handleAdd(Scanner sc, Inventory inv) {
    ...
}
```

`static` methods cannot access instance fields (`this.x`) — they only work with their parameters and other static members.

---

### Packages vs Modules

**Python modules** are just `.py` files. Import with `import` or `from x import y`.

**Java packages** are namespace declarations that must match the directory structure exactly.

```java
// This file MUST live at src/com/harshdeepkanhai/inventory/Item.java
package com.harshdeepkanhai.inventory;
```

The reverse-domain convention (`com.harshdeepkanhai`) avoids name collisions globally — similar to how PyPI package names need to be unique.

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
private int quantity;        // mutable — quantity legitimately changes
```
Python equivalent: `@dataclass(frozen=True)` approximates this, but Java enforces it at compile time.

### Program to the interface
```java
// Bad — locked to HashMap forever
HashMap<String, Item> items = new HashMap<>();

// Good — can swap implementation without changing callers
Map<String, Item> items = new HashMap<>();
```

### Always override `toString()` on model classes
Without it, `System.out.println(item)` prints a useless memory address like `Item@4e50df2e`.

### Use `@Override` annotation
It tells the compiler you intend to override a parent method. Typo the name and the compiler catches it — Python has no equivalent safety net.

### Avoid `System.out.println` in production code
Use [SLF4J](https://www.slf4j.org/) + [Logback](https://logback.qos.ch/) — same principle as Python's `logging` module over bare `print`.

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
| `Ctrl+Alt+V` | Extract expression into a local variable |
| `Ctrl+Alt+M` | Extract code block into a method |

**Quick Java wins:**

- Use `record` (Java 16+) for pure data classes — auto-generates constructor, getters, `equals`, `hashCode`, and `toString`:
  ```java
  public record Item(String name, double price, int quantity) {}
  // replaces the entire Item.java
  ```

- Java `for-each` looks like Python's `for`:
  ```java
  // Python: for item in items.values():
  for (var item : items.values()) {
      System.out.println(item);
  }
  ```

- `Map.getOrDefault` avoids null checks (like Python's `dict.get(key, default)`):
  ```java
  items.getOrDefault("apple", fallbackItem);
  ```

- Prefer `Map.of(...)` and `List.of(...)` for small immutable collections:
  ```java
  var commands = List.of("add", "remove", "list", "quit");
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

### Streams and Functional Java
- [Baeldung — Java 8 Streams](https://www.baeldung.com/java-8-streams)
- [Baeldung — Optional](https://www.baeldung.com/java-optional)

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
