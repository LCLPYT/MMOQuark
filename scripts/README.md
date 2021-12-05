# Guide
## genLanguageFile
This script takes a source language file and a provider language file and generates a target language file according to it.
The script scans the source language file for existing keys and copies the key from the provider language file to the target language file (if it exists).

```bash
node genLanguageFile.mjs sourceLanguageFile providerLanguageFile targetLanguageFile
```

Tested with v16.6.1