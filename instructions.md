# Spring Boot Mastery — Python Developer Edition

> A 6-week crash course taking you from zero Java to production-grade Spring Boot.
> Built for people who already know Python. Every concept mapped to something you already understand.
> Designed to build **independent senior-level thinking** — not AI dependency.

---

## How to use this course

1. **Type all code yourself** — never copy-paste. Typing is how your hands learn syntax.
2. **Think before you reveal** — every drill has a question first. Answer it in your head before reading the solution.
3. **Read compiler errors, don't Google them first** — Java errors are specific. Form a hypothesis, then fix.
4. **The projects accumulate** — the Book Library API you start in Week 2 becomes a Dockerized microservice by Week 6.
5. **One rule in IntelliJ:** when something is red-underlined, press `Alt+Enter` before anything else. It fixes ~80% of problems.

---

## The 6-Week Roadmap

| Week | Focus                              | Phase          | Project                             |
| ---- | ---------------------------------- | -------------- | ----------------------------------- |
| 1    | Java foundations for Python devs   | Language       | CLI inventory manager (pure Java)   |
| 2    | Spring Boot bootup                 | Core Spring    | Book Library REST API (in-memory)   |
| 3    | Data layer — JPA & databases       | Persistence    | Add PostgreSQL + relationships      |
| 4    | REST APIs, validation & security   | Production API | JWT auth + validation + Swagger     |
| 5    | Testing, caching & async           | Senior craft   | Full test coverage + Redis + async  |
| 6    | Microservices, Docker & deployment | Architecture   | Split into Kafka-connected services |

---

# WEEK 1 — Java Foundations for Python Devs

**Phase:** Language · **Goal:** Understand the language before the framework.

## Topics covered

| Topic                                               | Python parallel                    |
| --------------------------------------------------- | ---------------------------------- |
| Types & variables — strict typing vs duck typing    | dynamic typing                     |
| Classes & OOP — Java forces everything into classes | optional classes / loose functions |
| Interfaces & abstract classes                       | ABCs / duck typing                 |
| Collections — List, Map, Set                        | list, dict, set                    |
| Running Java — JVM, JDK, classpath                  | venv (but stricter)                |
| Maven & Gradle                                      | pip + pyproject.toml               |
| Checked vs unchecked exceptions                     | only unchecked in Python           |
| Lambdas & Streams                                   | map / filter / list comprehensions |

## Week 1 Project: CLI Inventory Manager

Build a command-line inventory manager. Items have name, price, quantity. Add / remove / search / update. Use `ArrayList`, `HashMap`, and a proper class hierarchy. **No frameworks** — pure Java.

### Week 1 mindset

> The language isn't the enemy — verbosity is a trade-off for tooling power. Java's strictness is why IntelliJ can autocomplete with 99% accuracy. Embrace the compiler as your first debugger.

---

## Java vs Python: the mental rewire

### 1. Types — Java is strict, Python is trusting

```python
# Python
name = "Laptop"
price = 999.99
qty = 5
is_active = True
```

```java
// Java
String name = "Laptop";
double price = 999.99;
int qty = 5;
boolean isActive = true;
```

Every variable needs its type declared. Every statement ends with `;`. Java checks types at compile time — the IDE tells you about mistakes before you even run it.

### 2. Everything is a class — no loose functions

```python
# Python
def greet(name):
    print(f"Hello {name}")

greet("Alice")
```

```java
// Java
public class Greeter {
  public static void greet(String name) {
    System.out.println("Hello " + name);
  }
  public static void main(String[] args) {
    greet("Alice");
  }
}
```

`public static void main(String[] args)` is Java's `if __name__ == "__main__":`. It's the entry point. Always looks exactly like this.

### 3. Collections — almost identical, different syntax

```python
# Python
items = []
items.append("Laptop")

lookup = {}
lookup["laptop"] = 999

unique = set()
unique.add("Laptop")
```

```java
// Java
List<String> items = new ArrayList<>();
items.add("Laptop");

Map<String,Double> lookup = new HashMap<>();
lookup.put("laptop", 999.0);

Set<String> unique = new HashSet<>();
unique.add("Laptop");
```

### 4. Null vs None — same idea, different danger level

```python
# Python
item = None
if item is not None:
    print(item.name)
```

```java
// Java
Item item = null;
if (item != null) {
    System.out.println(item.getName());
}
```

Java's `null` causes `NullPointerException` — the #1 Java runtime crash. Always null-check before calling methods on an object.

### 5. For loops — Java has three styles

```python
# Python
for item in items:
    print(item)

for i, item in enumerate(items):
    print(i, item)
```

```java
// Java
for (Item item : items) {
    System.out.println(item);
}

for (int i = 0; i < items.size(); i++) {
    System.out.println(i + " " + items.get(i));
}
```

### 6. Exceptions — Java forces you to think about them

```python
# Python
try:
    price = float(input("Price: "))
except ValueError:
    print("Not a number!")
```

```java
// Java
try {
    double price = Double.parseDouble(input);
} catch (NumberFormatException e) {
    System.out.println("Not a number!");
}
```

---

## Step-by-step build

### Step 0 — Project structure

```
inventory-cli/
  src/
    main/
      java/
        com/yourname/inventory/
          Item.java          <-- data class (like a Python dataclass)
          Inventory.java     <-- business logic class
          Main.java          <-- entry point (like if __name__ == "__main__")
```

**In IntelliJ:** File → New → Project → Java → JDK 21 → name it `inventory-cli`. Then right-click `src` → New → Package → type `com.yourname.inventory`. Create 3 Java classes inside it.

### Step 1 — `Item.java` (the data class)

```python
# Python equivalent
from dataclasses import dataclass

@dataclass
class Item:
    name: str
    price: float
    quantity: int

    def total_value(self):
        return self.price * self.quantity

    def __str__(self):
        return f"{self.name} | ${self.price:.2f} | qty: {self.quantity}"
```

```java
package com.yourname.inventory;

public class Item {

    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName()    { return name; }
    public double getPrice()   { return price; }
    public int getQuantity()   { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double totalValue() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-20s $%8.2f  qty: %d",
                name, price, quantity);
    }
}
```

**Key ideas:**

- `private String name;` → declares a field. Java declares fields at the top with their type.
- `this.name = name;` → same as Python's `self.name = name`. "this" = "self".
- `public String getName()` → a getter. Java convention: always prefix with "get".
- `@Override` → tells the compiler "I'm intentionally replacing the parent method." Like overriding `__str__`. The compiler errors if you mistype the method name.
- `String.format("%-20s $%8.2f qty: %d", ...)` → like Python f-strings. `%s` = string, `%.2f` = float with 2 decimals, `%d` = integer.
- **Why private fields?** Encapsulation. The class controls how its data is read/written.

### Step 2 — `Inventory.java` (business logic)

```java
package com.yourname.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Inventory {

    private Map<String, Item> items = new HashMap<>();

    public void addItem(Item item) {
        String key = item.getName().toLowerCase();
        if (items.containsKey(key)) {
            System.out.println("Item already exists.");
            return;
        }
        items.put(key, item);
        System.out.println("Added: " + item);
    }

    public void removeItem(String name) {
        Item removed = items.remove(name.toLowerCase());
        if (removed == null) {
            System.out.println("Not found: " + name);
        } else {
            System.out.println("Removed: " + removed.getName());
        }
    }

    public Optional<Item> findItem(String name) {
        return Optional.ofNullable(items.get(name.toLowerCase()));
    }

    public void updateQuantity(String name, int newQty) {
        findItem(name).ifPresentOrElse(
            item -> {
                item.setQuantity(newQty);
                System.out.println("Updated: " + item);
            },
            () -> System.out.println("Not found: " + name)
        );
    }

    public void listAll() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n--- Inventory ---");
        items.values().forEach(System.out::println);
        double total = items.values().stream()
                .mapToDouble(Item::totalValue)
                .sum();
        System.out.printf("Total value: $%.2f%n", total);
    }
}
```

**New Java concepts:**

- `Optional<T>` → Java's way of saying "this might be null." Forces the caller to handle both cases. `Optional.ofNullable(x)` wraps x, which could be null.
- **Stream API** → `.stream().mapToDouble(...).sum()` is Java's equivalent of `sum(item.total_value() for item in items)`. Streams are lazy pipelines over collections.
- **Lambda** `item -> {...}` → Java's lambda. Equivalent to Python's `lambda item: ...`.
- **Method reference** `System.out::println` → shorthand for `item -> System.out.println(item)`. The `::` passes a method as a value.
- **Why HashMap over ArrayList?** Searching by name in a list is O(n). A HashMap gives O(1) lookup by key. This is your first architectural decision.

### Step 3 — `Main.java` (the CLI loop)

```java
package com.yourname.inventory;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Inventory Manager ===");
        printMenu();

        boolean running = true;
        while (running) {
            System.out.print("\n> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add"    -> handleAdd(scanner, inventory);
                case "remove" -> handleRemove(scanner, inventory);
                case "update" -> handleUpdate(scanner, inventory);
                case "search" -> handleSearch(scanner, inventory);
                case "list"   -> inventory.listAll();
                case "help"   -> printMenu();
                case "quit"   -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Unknown command. Type 'help'.");
            }
        }
        scanner.close();
    }

    private static void handleAdd(Scanner sc, Inventory inv) {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Price: ");
        try {
            double price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(sc.nextLine().trim());
            inv.addItem(new Item(name, price, qty));
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Try again.");
        }
    }

    private static void handleRemove(Scanner sc, Inventory inv) {
        System.out.print("Item name to remove: ");
        inv.removeItem(sc.nextLine().trim());
    }

    private static void handleUpdate(Scanner sc, Inventory inv) {
        System.out.print("Item name: ");
        String name = sc.nextLine().trim();
        System.out.print("New quantity: ");
        try {
            int qty = Integer.parseInt(sc.nextLine().trim());
            inv.updateQuantity(name, qty);
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
        }
    }

    private static void handleSearch(Scanner sc, Inventory inv) {
        System.out.print("Search: ");
        String name = sc.nextLine().trim();
        inv.findItem(name).ifPresentOrElse(
            item -> System.out.println("Found: " + item),
            ()   -> System.out.println("Not found: " + name)
        );
    }

    private static void printMenu() {
        System.out.println("Commands: add | remove | update | search | list | help | quit");
    }
}
```

- `Scanner scanner = new Scanner(System.in)` → opens a reader on your keyboard. `scanner.nextLine()` waits for Enter and returns what you typed — exactly like Python's `input()`.
- `switch (command) { case "add" -> ... }` → modern Java switch (14+). Like Python's `match/case` (3.10+).
- `Double.parseDouble` / `Integer.parseInt` → like Python's `float()` / `int()`. Throws `NumberFormatException` (Python: `ValueError`).

### Step 4 — Run it

Click the green ▶ Run button (or `Shift+F10` on Windows / `Ctrl+R` on Mac).

```
=== Inventory Manager ===
Commands: add | remove | update | search | list | help | quit

> add
Name: Laptop
Price: 999.99
Quantity: 3
Added: Laptop                $  999.99  qty: 3

> list
--- Inventory ---
Laptop                $  999.99  qty: 3
Total value: $2999.97

> quit
Goodbye!
```

---

## Common errors & exact fixes

| Error                                                   | Cause                             | Fix                                              |
| ------------------------------------------------------- | --------------------------------- | ------------------------------------------------ |
| `cannot find symbol`                                    | Missing import or typo            | `Alt+Enter` → "Import class"                     |
| `package ... does not exist`                            | File in wrong folder              | Check package declaration matches folder path    |
| `reached end of file while parsing`                     | Missing closing `}`               | Count braces — must be equal                     |
| `incompatible types: String cannot be converted to int` | Passing String where int expected | Use `Integer.parseInt(yourString)`               |
| `NullPointerException` (runtime)                        | Called method on null             | Check object was created with `new` first        |
| Program immediately exits                               | Wrong main signature              | Must be `public static void main(String[] args)` |

> **The most important Java skill:** learning to read compiler errors — not Google them immediately. Read the error, find the line, form a hypothesis, then fix.

---

## Stretch extensions (attempt before looking up)

1. **Category enum** — Add a `Category` enum (`ELECTRONICS, FOOD, CLOTHING`) to Item. Filter by category in a new `listByCategory()` method.
2. **Sorting** — Sort items alphabetically: `items.values().stream().sorted(Comparator.comparing(Item::getName))`. (Python: `sorted(items, key=lambda x: x.name)`.)
3. **Low stock alert** — Add `lowStockAlert(int threshold)` using Stream's `.filter()`.
4. **Custom exception** — Create `ItemNotFoundException extends RuntimeException`. Throw it from `removeItem()`. Catch it in Main.
5. **File persistence (hard)** — Save inventory to a `.txt` on exit, reload on startup. Use `FileWriter` and `BufferedReader`. Forces handling checked exceptions.

> Do not Google the solution first. Attempt it, hit an error, read the message carefully. The error message IS the lesson.

---

## Week 1 self-test (close everything, answer on paper)

1. What does `private` mean on a field? Why not make everything `public`?
2. What's the difference between `ArrayList` and `HashMap`? When would you choose each?
3. What does `Optional<Item>` protect against? What's the Python equivalent pattern?
4. Rewrite this in Java from memory: `total = sum(item.price * item.qty for item in items)`
5. What would happen if you called `inventory.findItem(null)`? Trace the execution.
6. Why does `Main.java` have `scanner.close()` at the end?
7. Draw the object relationships: which class knows about which? Could you redesign it differently?

---

## Week 1 lateral thinking challenges

### Challenge 1 — Types vs Python types (Beginner)

This Python runs fine: `x = 5; x = "hello"; x = [1,2,3]`. The Java equivalent won't compile. Why?

**Answer:** In Python, variables are **labels pointing to objects** — the object knows its type. `x = 5` points x to an integer object; `x = "hello"` repoints it to a string object. The label has no type.

In Java, `int x` is a **named memory slot of fixed size** (an int is exactly 32 bits). The slot can only hold a 32-bit integer — it physically cannot hold a String reference. The type is burned in at compile time.

> Python variables = post-it notes you stick on anything. Java variables = labeled boxes with a fixed shape.

**Bonus:** A Python list holds mixed types `[1, "hello", True]`. A Java `List<Integer>` can only hold Integers. Same property, different tradeoff: Python optimizes flexibility, Java optimizes compile-time correctness.

### Challenge 2 — HashMap design decision (Intermediate)

A teammate says "use ArrayList, HashMap silently overwrites duplicate names."

- **The cost they ignore:** ArrayList search is O(n) — scans every item. HashMap is O(1). For 500,000 SKUs this is the difference between 0.001ms and 500ms per search.
- **Better designs:** (A) UUID key `Map<UUID, Item>`; (B) `Map<String, List<Item>>` to group duplicates; (C) two structures — `Map<UUID, Item>` for lookup + `Map<String, List<UUID>>` for name index. **This is exactly what real databases do** (primary key + indexes).
- **On silent failure:** Returning silently is dangerous — the caller can't tell success from failure. Senior approaches: return a boolean, throw a custom exception, or return `Optional`/`Result`. **Make failure loud, not silent.**

### Challenge 3 — OOP design & the Python comparison (Hard)

A discount method grows into an 80-line `if/else` chain across 11 categories.

- **The problem:** Violates the **Open/Closed Principle** (open for extension, closed for modification) and **Single Responsibility**. Every new category forces editing working code.
- **Java fix — Strategy pattern via enum + interface:**

```java
public interface DiscountStrategy {
    double apply(double price);
}

public enum Category {
    ELECTRONICS(price -> price * 0.90),
    FOOD       (price -> price * 0.95),
    CLOTHING   (price -> price * 0.80);

    private final DiscountStrategy strategy;
    Category(DiscountStrategy s) { this.strategy = s; }
    public double applyDiscount(double price) {
        return strategy.apply(price);
    }
}
```

Adding a category = one line. Zero changes to existing code.

- **Python does the same in fewer lines** — the design principle is identical. **Good design is language-agnostic. Syntax is just the vehicle.**

### Challenge 4 — The broken inventory puzzle (Boss)

Three subtle bugs that compile and run but behave wrong:

**Bug 1 — String comparison with `==`:** `item.getName() == name` compares object _references_, not content. Two different String objects with identical text return `false`. Fix: `.equals(name)` or `.equalsIgnoreCase(name)`. (Python's `==` calls `__eq__` and compares content; Java's `==` is always reference equality for objects.)

**Bug 2 — Integer division & precedence:** `price / 100 * percent` — if both operands are ints, `9 / 100 = 0` in Java (truncation). Use `price * (1 - percent / 100.0)`. The `100.0` forces float division. (Python 3 always does float division with `/`.)

**Bug 3 — Removing from a list while iterating by index:** Calling `items.remove(i)` shifts later elements down, so the loop skips the next one. Fix: iterate backwards, or use `items.removeIf(item -> item.getPrice() > threshold)`. (Python has the identical bug with `pop(i)` in a `range` loop; the fix is a comprehension.)

---

## Deep dive: `Optional<Item>` — a corrected understanding

A common belief: _"Optional protects against NullPointerException by forcing the caller to handle the empty case."_ Half right.

**The nuance:** Optional does NOT protect against NPE by itself:

```java
Optional<Item> result = inventory.findItem("laptop");
Item item = result.get();  // throws NoSuchElementException if empty
```

That crashes just as hard as a raw null.

**The real value is at the _signature_ level.** When a method returns `Optional<Item>` instead of `Item`, it's a visible, type-level promise: _"I might not have an answer — you must decide what to do."_ You can't treat it as a guaranteed value without explicitly writing `.get()`, which is your moment to pause.

|                              | Protects against NPE? | Forces handling?                |
| ---------------------------- | --------------------- | ------------------------------- |
| Raw `null`                   | No                    | No                              |
| Python `Optional[Item]` hint | No                    | No (convention only)            |
| Java `Optional<Item>`        | Not by itself         | No, but its API _encourages_ it |

**Senior insight:** Optional is a _communication tool_ between method author and caller, not a safety net. Safety comes from how you consume it — use `ifPresentOrElse` instead of `.get()`.

**Gut-check:** What's the difference between `Optional.of(x)` and `Optional.ofNullable(x)`, and what happens if you call `Optional.of(null)`? _(Answer: `Optional.of(null)` throws NPE immediately; `ofNullable` returns an empty Optional. Use `of` only when you're certain the value is non-null.)_

---

# WEEK 2 — Spring Boot Bootup

**Phase:** Core Spring · **Goal:** Understand dependency injection and the Spring context.

## Topics

| Topic                                                  | Python parallel                    |
| ------------------------------------------------------ | ---------------------------------- |
| Auto-configuration                                     | FastAPI/Flask but opinionated      |
| Dependency Injection & IoC                             | the core rewire for Python devs    |
| `@Component`, `@Service`, `@Repository`, `@Controller` | —                                  |
| `application.properties` / `.yml`                      | `.env`                             |
| Spring context lifecycle                               | —                                  |
| Spring Initializr                                      | cookiecutter                       |
| `@RestController` + `@GetMapping`                      | Flask routing                      |
| Embedded Tomcat                                        | no separate server unlike Gunicorn |

**Project:** "Book Library" REST API — CRUD endpoints for books. No DB yet, use in-memory List. Understand what `@Autowired` actually does before using it.

**Spring Initializr settings:** Maven · Java · Spring Boot 3.5.x (not 4.x — too new) · Jar · Java 21. Dependencies: Spring Web, Spring Data JPA, H2 Database, Lombok.

**Drills:**

- If two `@Service` beans implement the same interface, what happens when you `@Autowire` it? _(NoUniqueBeanDefinitionException — fix with `@Qualifier` or `@Primary`.)_
- Difference between `@Component` and `@Bean`? _(Class-level auto-detection vs method-level manual declaration in a `@Configuration` class.)_
- Why can't you `@Autowire` in a POJO you `new`-ed yourself? _(Spring doesn't manage objects you create manually.)_

**Mindset:** DI feels like magic until you draw the object graph on paper. Draw every bean, every arrow. The moment the graph makes sense, Spring stops being magic.

---

# WEEK 3 — Data Layer (JPA & Databases)

**Phase:** Persistence

## Topics

| Topic                                     | Python parallel                |
| ----------------------------------------- | ------------------------------ |
| Spring Data JPA                           | SQLAlchemy (annotation-driven) |
| `@Entity`, `@Id`, `@Column`               | ORM models                     |
| `@OneToMany`, `@ManyToOne`, `@ManyToMany` | relationships                  |
| JPQL & native queries                     | SQL but object-oriented        |
| `CrudRepository`, `JpaRepository`         | repository pattern             |
| Flyway/Liquibase migrations               | Alembic                        |
| H2 in-memory DB for tests                 | no Docker needed in dev        |
| Query methods `findByNameAndAge()`        | SQLAlchemy `filter_by`         |

**Project:** Add PostgreSQL to the Book Library. Add Authors (many-to-many). Write custom JPQL. Run a Flyway migration. Trace SQL using Hibernate logging.

**Drills:**

- **N+1 query problem** — fetching 100 books + authors triggers 101 queries. Fix: `JOIN FETCH` or `@EntityGraph`.
- **EAGER vs LAZY** — EAGER loads immediately, LAZY loads on first access. Wrong choice → `LazyInitializationException`.
- Why `@Transactional` on the service layer, not repository? _(Business operations span multiple DB calls; the transaction wraps them atomically.)_

**Mindset:** JPA hides SQL but never forget SQL is happening underneath. Always enable SQL logging in dev. A senior dev predicts the SQL before running the code.

---

# WEEK 4 — REST APIs, Validation & Security

**Phase:** Production API

## Topics

| Topic                                           | Python parallel                  |
| ----------------------------------------------- | -------------------------------- |
| DTOs vs Entities                                | never expose ORM models directly |
| Bean Validation (`@NotNull`, `@Size`, `@Email`) | Pydantic                         |
| Spring Security basics                          | authentication vs authorization  |
| JWT tokens                                      | stateless auth (no sessions)     |
| `@ControllerAdvice` global exception handling   | Flask error handlers             |
| OpenAPI / Swagger                               | FastAPI auto-docs                |
| Pagination & sorting                            | `Pageable` interface             |
| CORS configuration                              | Flask-CORS                       |

**Project:** Secure the Book Library. JWT auth (register/login). Protect CRUD with roles (ADMIN deletes, USER reads). Full validation with custom error messages. Generate Swagger docs.

**Drills:**

- What's a DTO and why never return a JPA entity from a controller? _(Lazy collections, bidirectional refs → infinite JSON recursion, internal DB fields. DTOs are stable contracts.)_
- How does Spring Security's filter chain work? _(Every request passes through ordered filters before the controller. `JwtAuthFilter` runs before `UsernamePasswordAuthenticationFilter`.)_
- What happens when Bean Validation fails? _(`MethodArgumentNotValidException` thrown, caught by `@ControllerAdvice`, mapped to a clean error DTO.)_

**Mindset:** Security is not a feature you add later — it's baked in from day one. Draw the auth flow before writing a line.

---

# WEEK 5 — Testing, Caching & Async

**Phase:** Senior craft

## Topics

| Topic                                       | Python parallel            |
| ------------------------------------------- | -------------------------- |
| JUnit 5                                     | pytest                     |
| Mockito                                     | unittest.mock              |
| `@SpringBootTest` vs `@WebMvcTest`          | integration vs slice tests |
| Redis caching (`@Cacheable`, `@CacheEvict`) | —                          |
| `@Async`, `CompletableFuture`               | asyncio                    |
| Spring Events                               | Python signals             |
| Actuator (health, metrics)                  | —                          |
| Micrometer + Prometheus                     | observability              |

**Project:** Full test coverage — unit test services with Mockito, integration test APIs with MockMvc. Add Redis caching to book queries. Add async email notification on new book creation.

**Drills:**

- Mocking vs stubbing? _(Stubbing defines what a fake returns; mocking also verifies how it was called. `when()` = stub, `verify()` = mock assertion.)_
- Why not `@SpringBootTest` for every test? _(It loads the entire context — slow. Use the narrowest slice: `@WebMvcTest`, `@DataJpaTest`.)_
- What does `@Async` not solve that a message queue does? _(Async runs in-JVM — if the app crashes, the task is lost. A queue persists until acknowledged.)_

**Mindset:** A test is documentation that compiles. Test names are sentences: `should_returnEmptyList_whenNoBooksFound`.

---

# WEEK 6 — Microservices, Docker & Deployment

**Phase:** Architecture

## Topics

| Topic                                                              | Notes                                         |
| ------------------------------------------------------------------ | --------------------------------------------- |
| Monolith vs microservices                                          | when NOT to use microservices                 |
| Dockerizing Spring Boot                                            | Dockerfile, layers, multi-stage builds        |
| Spring Cloud (Eureka, Gateway, Config Server)                      | service discovery                             |
| Kafka intro                                                        | event-driven, producers & consumers           |
| Config management (Profiles, `@Value`, `@ConfigurationProperties`) | —                                             |
| CI/CD (GitHub Actions)                                             | pipeline for Spring Boot                      |
| Distributed tracing (Zipkin, correlation IDs)                      | —                                             |
| Production checklist                                               | connection pools, timeouts, graceful shutdown |

**Project:** Split the Book Library into `book-service` and `user-service` communicating via Kafka events. Dockerize both. Write a GitHub Actions pipeline that builds, tests, and pushes images.

**Drills:**

- The fallacy of "just use microservices for scale"? _(They add latency, distributed transactions, ops complexity. A tuned monolith beats a naive cluster. Split by team boundaries, not performance fantasies.)_
- What's a Kafka consumer group? _(Multiple instances share consumption — each message goes to exactly one consumer in the group. Scale consumers to match partitions.)_
- What happens to in-flight requests on deploy? _(Without graceful shutdown, requests are cut mid-flight. Configure `spring.lifecycle.timeout-per-shutdown-phase` / K8s preStop hooks.)_

**Mindset:** Architecture is a conversation about tradeoffs, not a blueprint of patterns. For every pattern you adopt, name what you're trading away. Senior engineers are fluent in tradeoffs — not just patterns.

---

## The throughline

The 3-layer separation you build in Week 1 (data class → logic class → entry point) is the foundation of every Spring Boot app:

```
Week 1:  Item        →  Inventory   →  Main
Spring:  Entity      →  Service     →  Controller
```

Same idea, bigger framework. Master the small version and the large one is just scale.

---

_Train your own neurons. Read errors before Googling. Build your own methods. The goal is needing the AI less every week._
