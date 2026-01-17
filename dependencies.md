# Project Dependencies Overview

This document explains the purpose and usage of each dependency used in the automation framework.

---

## Selenium Dependencies

### selenium-java

**Version:** 4.39.0
**Purpose:**
Core Selenium WebDriver library used for browser automation.
Includes Selenium Manager, which automatically handles browser driver binaries.

**Used for:**

* Local browser automation
* Remote execution via RemoteWebDriver
* Managing browsers like Chrome, Firefox, Edge, etc.

---

### selenium-grid

**Version:** 4.39.0
**Purpose:**
Provides Selenium Grid components required for distributed and remote test execution.

**Used for:**

* Running tests on Selenium Grid
* Remote browser execution in CI/CD
* Multi-browser and parallel execution across machines

**Note:**
Not required for pure local execution.

---

### selenium-server (optional)

**Version:** 4.39.0
**Purpose:**
Executable server that bundles Grid, Hub, Node, and Standalone modes.

**Used for:**

* Running Selenium Grid via CLI or embedding server execution
* Standalone Selenium execution

**Note:**
Not required for normal test automation projects.

---

## Test Framework

### TestNG

**Version:** 7.10.2
**Purpose:**
Testing framework used to define, execute, and manage test cases.
Note: Keep scope as compile -> This is a temporary workaround, not the correct Maven design. As compile scope means production code. We should move TestNG-based classes to src/test/java

**Used for:**

* Test annotations (`@Test`, `@BeforeSuite`, etc.)
* Parallel execution
* Assertions
* Test lifecycle management

---

## Reporting

### Extent Reports

**Version:** 5.1.2
**Purpose:**
Generates rich and interactive HTML reports.

**Used for:**

* Visual test reports
* Logging test steps
* Attaching screenshots and failures

---

## Logging

### log4j-api

**Version:** 2.25.3
**Purpose:**
Logging API that defines logging interfaces.

---

### log4j-core

**Version:** 2.25.3
**Purpose:**
Log4j implementation responsible for writing logs to console and files.

**Used for:**

* Application logs
* Debugging and tracing execution
* Error reporting

---

## Data Handling

### Apache POI (poi)

**Version:** 5.2.5
**Purpose:**
Provides APIs to read and write Excel `.xls` files.

---

### Apache POI OOXML (poi-ooxml)

**Version:** 5.2.5
**Purpose:**
Required for handling Excel `.xlsx` files.

**Used for:**

* Test data management
* Data-driven testing

---

### XMLBeans

**Version:** 5.3.0
**Purpose:**
Required by Apache POI for processing XML-based Office documents.

---

### Curves API

**Version:** 1.08
**Purpose:**
Supports Excel chart handling in Apache POI.

---

## JSON & Configuration

### Jackson Databind

**Version:** 2.17.1
**Purpose:**
Used for JSON parsing and serialization.

**Used for:**

* Reading configuration files
* Handling API responses
* Managing test data in JSON format

---

## Utility Libraries

### Commons IO

**Version:** 2.21.0
**Purpose:**
Utility library for file and stream operations.

**Used for:**

* File copy and deletion
* Saving screenshots
* Directory management

---

### Commons Logging

**Version:** 1.3.5
**Purpose:**
Logging bridge used internally by some Apache libraries.

---

### Commons Codec

**Version:** 1.20.0
**Purpose:**
Provides encoding and decoding utilities.

**Used for:**

* Base64 encoding/decoding
* Hashing and digests

---

## BDD (Optional)

### Cucumber (optional)

**Version:** 7.17.0
**Purpose:**
Behavior Driven Development (BDD) framework.

**Used for:**

* Writing tests in Gherkin syntax
* Collaboration between QA and business teams
* Integrating BDD with TestNG

---

## Summary

This dependency setup supports:

* Selenium WebDriver automation
* Grid-based distributed execution
* Rich reporting and logging
* Data-driven and configurable test execution
* Optional BDD support

---

**Tip:**
For most projects, `selenium-java` + `TestNG` is sufficient.
Add Grid, Reporting, or BDD dependencies only when required.
