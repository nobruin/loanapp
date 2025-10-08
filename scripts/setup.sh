#!/bin/bash
set -e

echo "🔧 Setting up pre-commit hooks..."

# ------------------------------------------------------------------------------
# 1. Check if Python 3 is installed
# ------------------------------------------------------------------------------
if ! command -v python3 &> /dev/null; then
  echo "❌ Python 3 not found."
  echo "Please install Python 3 and pip manually before running this script."
  echo "For example (Ubuntu): sudo apt install python3 python3-pip -y"
  exit 1
fi

# ------------------------------------------------------------------------------
# 2. Check if pre-commit is available, otherwise install it
# ------------------------------------------------------------------------------
if ! command -v pre-commit &> /dev/null; then
  echo "⚙️  Installing pre-commit using pip..."
  pip install pre-commit || pip3 install pre-commit
fi

# ------------------------------------------------------------------------------
# 3. Install Git hooks (pre-commit and commit-msg)
# ------------------------------------------------------------------------------
pre-commit install --hook-type pre-commit --hook-type commit-msg

echo "✅ pre-commit hooks successfully installed!"
echo "👉 Hooks will now run automatically on each commit."
echo "To test manually, run: pre-commit run --all-files"
