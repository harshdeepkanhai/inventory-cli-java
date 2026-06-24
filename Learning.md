# 2026-06-24

My first real training day on Java, coming from a Python background. I extended the CLI Inventory Manager through three "arcs." Here is everything I touched and what it actually means — in plain words, no code.

---

## Arc A — Adding Categories (the Classification Seal)

**What I built:** Every item now belongs to a fixed category (Electronics, Food, Clothing), and I can list items filtered by one category.

**What I learned:**

- **Enums are locked gates.** Instead of storing the category as free text (where someone could type "banananana" or misspell "electronics"), an enum only allows a fixed set of valid values. Garbage can't get in. This is the whole reason enums exist.

- **`valueOf` has a direction.** To turn the user's typed text into a real category, I call `valueOf` on the *thing I want to receive*. Calling it on the wrong class just gave me text back in a circle. Lesson: convert toward what you want out.

- **Casing matters.** The user types "food" but the enum is "FOOD". I had to force the text uppercase before converting, or it would blow up.

- **`==` vs `.equals()` in Java.** In Java, `==` checks if two things are the *same object in memory* (reference). `.equals()` checks if they hold the *same value* (content). This is the opposite of how I think in Python, where `==` compares content. BUT — for enums, `==` is safe and even preferred, because there's only ever ONE copy of each enum value in the whole program.

- **Streams have two kinds of steps.** A stream is like a river of data. "Intermediate" steps (like filter) keep the river flowing and can be chained. "Terminal" steps (like collect, count, forEach) END the river and hand back a final result. You can only end the river once, and nothing chains after it. I broke this by jamming a terminal step in the middle — the river died.

- **Separate the data from the decision.** Trying to filter, check-if-empty, and print all in one chain failed. The clean way: collect the filtered results into a List (the river ends here), then use normal everyday code to check if the list is empty and loop through it. Stream = a river you cross once. List = a lake you can visit many times.

---

## Arc B — Loud Failures (Custom Exceptions)

**What I built:** When someone tries to remove or update an item that doesn't exist, the program now throws a clear, named error instead of quietly printing a message.

**What I learned:**

- **Silent failure is dangerous.** Just printing "not found" is a mumble — the rest of the program can't react to it. Throwing an exception is loud — the caller is forced to deal with it.

- **Custom exceptions are just named beasts.** I made my own error type, `ItemNotFoundException`, by extending Java's built-in exception. Its job is simply to carry a message up to whoever catches it.

- **`super(message)` passes the message upward.** My exception hands its message to its parent class, which stores it. Later, `getMessage()` retrieves it — so the message I wrote deep in the logic shows up at the surface where it's caught.

- **Checked vs unchecked exceptions.** This is a Java thing Python doesn't have. "Checked" exceptions FORCE every caller to handle them (the compiler won't let you ignore them). "Unchecked" ones don't. I chose unchecked, because modern Java and Spring prefer it — forcing handling everywhere creates clutter and breaks inside lambdas.

- **A `throw` is an ejection, not a return.** When you throw, the method stops immediately and jumps out. The code after it doesn't run.

- **Exception ordering law.** If one exception type is a child of another, you must catch the child BEFORE the parent — otherwise the parent swallows everything and the child catch becomes dead code. If two exceptions are unrelated (siblings), order doesn't matter.

- **Error vs expected outcome.** A big judgment call: removing something that doesn't exist is the *caller's mistake* (throw an error). But searching and finding nothing is a *valid, expected answer* (don't throw — just say "not found"). Not every "missing" situation deserves an exception.

- **`orElseThrow` is the clean tool.** When I expect a value to exist, this either hands me the real value or throws if it's missing. It flattens the code into a straight top-to-bottom flow instead of nested branches.

---

## Arc C — Saving to Disk (the Immortal Ledger) — the Boss

**What I built:** The inventory now saves to a file when I quit, and reloads automatically when I start again. Close the app, reopen it, and my items are still there.

**What I learned:**

- **Single Responsibility.** Reading and writing files is a *different job* from managing items, so I made a separate class (`FileStore`) just for disk work. The inventory class shouldn't know about files; the file class shouldn't know about HashMaps. Keeping jobs separate makes the code easier to change later.

- **Encapsulation — don't hand out the keys.** To let the file class read my items, I exposed only a read-only view of the values, NOT the raw internal map. If I'd handed out the real map, any code could wipe or corrupt my inventory.

- **Two formats for two audiences.** I kept the existing pretty display (for humans, with dollar signs and bars) separate from a plain comma-separated format (for machines, easy to read back). Mixing them would make the file unreadable.

- **Design for the round-trip.** Whatever I write out, I have to be able to read back in. So I chose a format where saving and loading are perfect mirrors: join fields with commas when saving, split on commas when loading.

- **Checked exceptions for real (`IOException`).** File operations can fail (disk full, file missing), and Java FORCES me to handle that. No ignoring it.

- **Try-with-resources.** A way to open a file so it automatically closes when I'm done — even if something crashes mid-way. No leaks, no manual cleanup. (It's like Python's `with open(...)`.)

- **Reading line by line.** I read the file one line at a time until there are no more lines, parsing each line back into an item.

- **Resilience — survive the garbage.** A single corrupt line in the file should NOT destroy the whole load. So I wrapped each line's parsing in its own safety net: if one line is broken, skip it with a warning and keep going.

- **Handle the first run.** The very first time, the save file doesn't exist yet. That's not an error — it's expected. I handle it calmly by just starting empty.

- **Silent bulk loading.** When loading many saved items at startup, I do NOT want a "Added!" message printed for each one (that's spam). So I made a separate quiet method for bulk loading, owned by the domain class — different from a user adding one item, which SHOULD announce itself.

- **Naming conventions.** Constants that never change get ALL_CAPS names and `static final`. Regular variables get camelCase. The casing tells other developers what kind of thing it is. Also: I avoided giving a method parameter the same name as a field, so I never confuse the two.

---

## The Big Picture

The three-layer structure I built — data (Item) → logic (Inventory) → entry point (Main) — is the same shape used in real Spring Boot apps (Entity → Service → Controller). I'm learning the small version now so the big framework later is just scale.

The deeper habit I'm building: **read errors instead of panicking, think a few steps ahead before typing, and ask "is this an error or just an expected outcome?"** That mindset matters more than any single piece of syntax.
