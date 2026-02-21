#!/usr/bin/env python3
"""
Convert path-like values in Maven POM tags between Windows and POSIX styles.

Default tags:
- systemPath
- product.plugins.dir
- rsp-wtp-server.home

Examples:
  python scripts/convert_pom_paths.py to-windows --root /path/to/project
  python scripts/convert_pom_paths.py to-posix --root /path/to/project --dry-run
"""

from __future__ import annotations

import argparse
import re
from pathlib import Path
from typing import Iterable

DEFAULT_TAGS = ["systemPath", "product.plugins.dir", "rsp-wtp-server.home"]


def build_tag_pattern(tags: Iterable[str]) -> re.Pattern[str]:
    escaped_tags = [re.escape(t) for t in tags]
    tags_part = "|".join(escaped_tags)
    return re.compile(
        rf"(<(?P<tag>{tags_part})>\s*)(?P<value>[^<]*?)(\s*</(?P=tag)>)",
        re.MULTILINE,
    )


def is_url_like(value: str) -> bool:
    return "://" in value


def to_windows_path(value: str, drive: str) -> str:
    if not value or is_url_like(value):
        return value

    converted = value

    if converted.startswith("/Users/"):
        converted = f"{drive.upper()}:\\Users\\" + converted[len("/Users/") :]
    elif converted.startswith("/") and not converted.startswith("//"):
        converted = f"{drive.upper()}:\\" + converted[1:]

    converted = converted.replace("/", "\\")
    converted = re.sub(r"\\+", r"\\", converted)
    return converted


def to_posix_path(value: str) -> str:
    if not value or is_url_like(value):
        return value

    converted = value
    drive_match = re.match(r"^(?P<drive>[A-Za-z]):[\\/](?P<rest>.*)$", converted)
    if drive_match:
        rest = drive_match.group("rest").replace("\\", "/")
        if rest.startswith("Users/"):
            converted = "/" + rest
        else:
            converted = "/" + rest
    else:
        converted = converted.replace("\\", "/")

    converted = re.sub(r"/{2,}", "/", converted)
    return converted


def convert_value(value: str, direction: str, drive: str) -> str:
    if direction == "to-windows":
        return to_windows_path(value, drive)
    return to_posix_path(value)


def process_pom_file(path: Path, pattern: re.Pattern[str], direction: str, drive: str) -> tuple[bool, int]:
    text = path.read_text(encoding="utf-8")
    replacements = 0

    def replacer(match: re.Match[str]) -> str:
        nonlocal replacements
        original = match.group("value")
        updated = convert_value(original, direction, drive)
        if updated != original:
            replacements += 1
        return f"{match.group(1)}{updated}{match.group(4)}"

    new_text = pattern.sub(replacer, text)
    changed = new_text != text
    if changed:
        path.write_text(new_text, encoding="utf-8")
    return changed, replacements


def find_poms(root: Path) -> list[Path]:
    return sorted(root.rglob("pom.xml"))


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Convert path values in Maven pom.xml files.")
    parser.add_argument("direction", choices=["to-windows", "to-posix"], help="Conversion direction")
    parser.add_argument("--root", default=".", help="Root directory to scan recursively for pom.xml")
    parser.add_argument("--drive", default="C", help="Drive letter used for to-windows (default: C)")
    parser.add_argument(
        "--tags",
        default=",".join(DEFAULT_TAGS),
        help="Comma-separated XML tag names to rewrite",
    )
    parser.add_argument("--dry-run", action="store_true", help="Report changes without writing files")
    parser.add_argument("--verbose", action="store_true", help="Print each modified file")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    root = Path(args.root).expanduser().resolve()
    tags = [t.strip() for t in args.tags.split(",") if t.strip()]
    if not tags:
        print("No tags selected. Nothing to do.")
        return 1

    if not re.fullmatch(r"[A-Za-z]", args.drive):
        print(f"Invalid drive letter: {args.drive!r}")
        return 1

    pattern = build_tag_pattern(tags)
    pom_files = find_poms(root)

    changed_files = 0
    total_replacements = 0

    for pom in pom_files:
        original = pom.read_text(encoding="utf-8")

        replacements = 0

        def dry_replacer(match: re.Match[str]) -> str:
            nonlocal replacements
            original_value = match.group("value")
            updated_value = convert_value(original_value, args.direction, args.drive)
            if updated_value != original_value:
                replacements += 1
            return f"{match.group(1)}{updated_value}{match.group(4)}"

        candidate = pattern.sub(dry_replacer, original)
        if candidate == original:
            continue

        changed_files += 1
        total_replacements += replacements

        if not args.dry_run:
            pom.write_text(candidate, encoding="utf-8")

        if args.verbose:
            print(str(pom))

    mode = "DRY-RUN" if args.dry_run else "UPDATED"
    print(
        f"{mode}: files={changed_files}, replacements={total_replacements}, "
        f"direction={args.direction}, root={root}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
