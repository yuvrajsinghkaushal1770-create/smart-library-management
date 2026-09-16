# Project Statement

## Problem Statement

Small and medium-scale libraries — school libraries, college reading rooms, hostel
libraries, coaching centers — still rely on manual registers or Excel sheets to
manage books, members, and loans. This causes:

- Books getting lost with no record of who borrowed them
- Manual fine calculation, leading to errors and disputes
- No quick way to check if a book is currently available
- No data on which books are borrowed most often
- No backup of records — a lost register means lost data
- No role separation — anyone can modify records

## Scope of the Project

The **Smart Library Management System** is a console-based Java application that
digitizes the core library operations for small-scale libraries. It covers:

- User management (Admin, Librarian, Member)
- Book catalog management
- Issue and return workflows
- Automatic fine calculation for late returns
- Reporting and analytics (most borrowed, overdue)
- Persistent storage using MySQL via JDBC
- Activity logging to file

**Out of scope:** GUI, web/mobile interface, online payment, email notifications.

## Target Users

| Role | Responsibilities |
|---|---|
| **Admin** | Manages members and librarians, views reports |
| **Librarian** | Manages books, issues and returns books, tracks fines |
| **Member** | Searches the catalog and views available books |

## High-Level Features

1. **Authentication** — role-based login with email and password
2. **User Management** — add, view, delete members and librarians
3. **Book Management** — add books, search by title or author
4. **Loan Management** — issue a book, return it, calculate fines
5. **Reports** — most borrowed books, overdue loans, CSV export
6. **Logging** — every action logged with a timestamp
7. **Validation** — input validation and custom exceptions