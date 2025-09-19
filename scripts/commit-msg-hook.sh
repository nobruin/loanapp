#!/bin/bash

# Pega a mensagem do commit
MSG_FILE=$1
COMMIT_MSG=$(cat "$MSG_FILE")

# Regex do Conventional Commit (exemplo simples)
PATTERN="^(feat|fix|docs|style|refactor|perf|test|chore|ci)(\([a-z]+\))?: .{1,50}"

if ! [[ "$COMMIT_MSG" =~ $PATTERN ]]; then
    echo "ERROR: Commit message does not follow Conventional Commits:"
    echo "Format: type(scope?): description (max 50 chars in description)"
    echo "Example: feat(auth): add login endpoint"
    exit 1
fi

