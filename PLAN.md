## Name
Simple File Synchronizer
## Description
Simple desktop application for file synchronization
## Tools
- Java
- IntelliJ IDEA
- GitHub (version control)
- JavaFx (user interface)
- Log4j (info/error logging)
- JUnit (unit tests)
## Features
- simple user interface (table of folders to sync)
- add, remove, edit folder
- enable/disable specific folder sync
- set destination, source, sync frequency (every X days/hours/minutes, specific date/time)
- include/exclude specific folders/files
- "synchronize now" button + "shutdown after sync" option
---
- different sync sources/destinations (internal drive, external drive, pendrive, FTP server)
---
- configuration file (run on startup, auto-sync when device connects)
---
- don't brute-force copy-replace all files
- cache file hashes (MD5/SHA256/...)
- compare file hashes then compare date/time
