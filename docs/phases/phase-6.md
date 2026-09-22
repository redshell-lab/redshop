# Phase 6 — Intentional Vulnerabilities

## Status

**Not started.**

## Planned Sequence

1. Stored XSS
2. SQL Injection
3. IDOR / Broken Access Control
4. SSRF
5. File Upload
6. Race Condition
7. Business Logic

## Method

```text
Existing behavior
      ↓
Intentional vulnerable change
      ↓
Exploit
      ↓
Document
      ↓
Fix
      ↓
Retest
```

## Rules

- Keep changes minimal.
- Preserve unrelated functionality.
- Record endpoint, request and payload.
- Explain root cause and impact.
- Implement remediation.
- Retest after remediation.
