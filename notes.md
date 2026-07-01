# Java + Spring Boot — Learning Notes

> Running study log. Newest date at the bottom. Each session = one dated heading.
> Rule I'm training: **run the code, don't guess. Read the exception. Then I understand.**

---

## July 01, 2026 — Week 1: Java Foundations (Python → Java)

### What I built

- **Inventory-CLI** — a pure-Java command-line app (no frameworks).
- Three classes, one-way knowledge flow: `Main → Inventory → Item`.
  - `Item` = data (name, price, qty) + behavior (`totalValue`, `toString`).
  - `Inventory` = business logic, holds a `private Map<String, Item>`.
  - `Main` = the CLI loop (Scanner + switch).
- Completed **all 5 extensions**: Category enum, sorting (Comparator), low-stock filter, custom exception, file persistence (FileWriter/BufferedReader).

### Java vs Python — the mental rewire

- **Types are strict.** `int x = 5;` — a variable is a fixed-size box, not a re-stickable label. Can't reassign to a different type. (Python: variables are labels pointing to objects.)
- **Everything lives in a class.** `public static void main(String[] args)` = Python's `if __name__ == "__main__":`.
- **Collections:** `ArrayList` = list, `HashMap` = dict, `HashSet` = set. Angle brackets `<>` = generics = the type inside.
- **`String.format("%s %.2f %d", ...)`** = Python f-strings. `%s` string, `%.2f` float 2dp, `%d` int.
- **Every statement ends with `;`.** Every block uses `{ }`.
- **`Integer.parseInt` / `Double.parseDouble`** = Python's `int()` / `float()`. Throws `NumberFormatException` (Python: `ValueError`).
- **IntelliJ reflex:** red underline → press `Alt+Enter` → "Import class". Fixes ~80% of problems.

### ArrayList vs HashMap (self-test, corrected)

- `ArrayList` — ordered, _backed by_ an array but auto-grows. Search is **O(n)** (scans element by element).
- `HashMap` — key→value. Search is **O(1)** (hashes the key, jumps near-directly to it).
- Choose HashMap when I look things up by key; ArrayList when order matters and I iterate.

### Encapsulation — why `private` (proven by the compiler)

- `private` fields aren't just "hidden" — they give **future freedom to change** the internal representation without breaking other classes.
- **Proven live:** tried to reach `Inventory.items` from `Main` → compiler error `cannot find symbol: items`. The `private` boundary _actively blocked_ an outside class. That refusal IS encapsulation working.
- **Design rule learned:** the calculation belongs where the data lives. Added `getTotalValue()` _inside_ `Inventory` instead of streaming `items` from `Main`.

```java
// inside Inventory — compiles because it can see its own private field
public double getTotalValue() {
    return items.values().stream()
            .mapToDouble(i -> i.getPrice() * i.getQuantity())
            .sum();
}
```

### THREE experiments I ran myself (guess → certainty)

**1. Does `Optional` protect against NullPointerException? → NO.**

```java
Optional<Item> empty = inventory.findItem("ghost");
empty.get();   // Exception in thread "main" java.util.NoSuchElementException: No value present
```

- Optional is a **signal in the method signature** that a value might be missing — NOT a null-shield.
- Calling `.get()` on an empty Optional still crashes (with `NoSuchElementException`).
- Safety comes from **how I consume it**: `ifPresentOrElse`, `orElse`, `map` — not from the wrapper existing.
- Python parallel: returning `None` / the `Optional[Item]` type hint — also convention, not enforcement.

**2. What does `findItem(null)` actually do? → Crashes on INPUT, before the lookup.**

```java
inventory.findItem(null);
// java.lang.NullPointerException: Cannot invoke "String.toLowerCase()" because "name" is null
// at Inventory.findItem(Inventory.java:51)
```

- It dies on line 1 of the method: `name.toLowerCase()` where `name` is null. Never reaches `Optional.ofNullable`.
- **Key insight:** `Optional.ofNullable` guards the _output_ being null. It does nothing for the _input_ `name` being null. **Two different nulls.**
- Fix — validate input at the top:

```java
public Optional<Item> findItem(String name) {
    if (name == null) return Optional.empty();   // guard the input
    return Optional.ofNullable(items.get(name.toLowerCase()));
}
```

**3. Stream total → works, and revealed floating-point precision.**

```
Total: 2999.9700000000003
```

- 999.99 × 3 should be exactly 2999.97. The `...003` tail = **floating-point precision**: decimals can't be stored exactly in binary (same as 1/3 in decimal).
- Python does the identical thing: `0.1 + 0.2 == 0.30000000000000004`.
- **Rule:** never use `double`/`float` for money in production. Use `BigDecimal` (Java) / `Decimal` (Python). `double` is fine for a learning CLI.

### Design principles that stuck

- **One-way knowledge flow** (`Main → Inventory → Item`) = good design. `Item` knows nothing above it, so it's reusable anywhere.
- **`public` = access control, NOT reference/knowledge flow.** Different concepts — don't mix them.
- **Make failure loud, not silent.** A method that can fail should signal it (return boolean / throw / Optional), not silently return.
- **Good design is language-agnostic.** The Strategy pattern (enum + interface for discounts) works the same in Java and Python; only the syntax differs.

### `Optional.of` vs `Optional.ofNullable`

- `Optional.ofNullable(x)` → empty Optional if x is null (safe).
- `Optional.of(null)` → throws NPE immediately. Use `of` only when certain the value is non-null.

### Open gaps to revisit (not blocking Spring)

- **Raw arrays** (`int[]`) — jumped straight to collections, never wrote a raw array. Course Section 9.
- **Method overloading** — same name, different parameters. Course Section 5, lecture 51.
- Plan: learn these when Spring forces the need, not in a vacuum.

### Course mapping (Tim Buchalka Java Masterclass)

- Solidly covered via project: Sections 1–4, 6, 7 (setup, control flow, OOP part 1).
- Deletable (already done or duplicates): the "Old content for Java 11" sections (25–33), Archived Videos (33).
- Keep for future: Sections 5, 8–23 (interfaces, generics, collections deep, IO, concurrency, lambdas/streams, JUnit, databases).
- JavaFX (Section 15) = desktop GUI, least relevant to Spring Boot (which is web). Skippable for my goal.

---

## Week 2: Spring Boot Bootup — _(add today's date when I start)_

<!-- Coming next: dependency injection, @RestController, @Service, @Repository,
     Spring Initializr, application.properties, embedded Tomcat.
     Project: Book Library REST API (in-memory first).
     My Item→Inventory→Main becomes Entity→Service→Controller — same structure, bigger. -->
