# Smart Library Management System

A console-based library management system built with Java, JDBC, and MySQL.
It replaces manual registers in small libraries with a digital system for
tracking books, members, loans, and fines.

## Overview

The project applies core Java concepts — Object-Oriented Programming,
Exception Handling, Multithreading, Collections, I/O Streams, and JDBC —
to solve a real-world library management problem.

Three roles are supported: **Admin**, **Librarian**, and **Member**, each
with their own menu and permissions.

## Features

- **User Management (Admin)** — add, view, delete members and librarians
- **Book Management (Librarian)** — add books, search by title/author, view catalog
- **Loan Management (Librarian)** — issue books, return books, auto fine calculation
- **Reports** — most borrowed books, overdue loans, CSV export
- **Authentication** — role-based login with email and password
- **Logging** — all actions logged to `logs/app.log`
- **Fine Calculation** — runs on a background thread using multithreading

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Build Tool | Maven |
| Database | MySQL 8 |
| DB Access | JDBC |
| Testing | JUnit 5 |
| IDE | VS Code |

## Project Structure
