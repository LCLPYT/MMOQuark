#!/usr/bin/env bash

sourceFile=$1
providerDir=$2
destDir=$3

if [ -z "$sourceFile" ] || [ -z "$providerDir" ] || [ -z "$destDir" ]; then
  echo "Usage: $0 <sourceFile> <providerDir> <destDir>"
  exit 1
fi

if [ ! -f "$sourceFile" ]; then
  echo "Source file '$sourceFile' does not exist."
  exit 1
fi

if [ ! -d "$providerDir" ]; then
  echo "Provider directory '$providerDir' is not a directory."
  exit 1
fi

if [ ! -d "$destDir" ]; then
  echo "Destination directory '$destDir' is not a directory."
  exit 1
fi

script=./scripts/genLanguageFile.mjs
if [ ! -f $script ]; then
  printf "'%s' does not exist.\nMake sure to run the script from the project working directory.\n" $script
  exit 1
fi

declare -a arr=(
  "de_de"
  "en_gb"
  "es_ar"
  "es_es"
  "es_mx"
  "fi_fi"
  "fr_fr"
  "hu_hu"
  "it_it"
  "ja_jp"
  "ko_kr"
  "pl_pl"
  "pt_br"
  "ru_ru"
  "zh_cn"
  "zh_tw"
)

for lang in "${arr[@]}"; do
  providerFile="$(realpath --relative-to="$PWD" "$providerDir")/$lang.json"
  if [ ! -f "$providerFile" ]; then
    echo "Provider file '$providerFile' does not exist."
    continue
  fi

  cmd="node $script $sourceFile $providerFile $(realpath --relative-to="$PWD" "$destDir")/$lang.json"
  echo "Executing '$cmd'..."
  eval "$cmd"
done
