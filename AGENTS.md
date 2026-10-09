## VERY IMPORTANT RULES

### 1. Direct & Surgical Code Modifications
- **Strict Scope:** Modify ONLY the exact lines, components, or functions requested (e.g., if asked to update the header, touch ONLY the header code). Do NOT rewrite, refactor, or clean up surrounding code unless explicitly instructed.
- **No Unsolicited Changes:** Do not rename variables, reformat styles, or add "improvements" to code outside the target area.

### 2. Concise Output & Token Efficiency
- **No Explanations / No Fluff:** Deliver the exact code directly. Skip introductory text ("Here is the updated code:"), closing pleasantries, and unnecessary explanations.
- **Short & Direct Code:** Keep generated code concise, correct, and directly addressing the prompt.

### 3. Smart Navigation
- **Use References (@):** Respect provided file references (@) immediately instead of searching the workspace.

### 4. Subagent Delegation
- **Target Subagent:** Use `Nemotron 3.5 Lightning Free` (or `Nemotron 3 Ultra Free`) for routine edits.
- **Automatic Routing:** Delegate unit tests, DTOs, HTML/CSS changes, and simple functions to this subagent.
- **Primary Model Focus:** Use the primary model ONLY for complex logic and architecture.
- **Minimal Context:** Pass ONLY the file specified with `@` when calling the subagent.