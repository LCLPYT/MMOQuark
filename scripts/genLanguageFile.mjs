import * as fs from 'fs';
import * as path from 'path';
import * as readline from 'readline';
import { fileURLToPath } from 'url';

function normPath(absPath) {
    const abs = path.resolve(absPath); // make sure it is an absolute path
    const pwd = path.dirname(fileURLToPath(import.meta.url));
    if (!abs.startsWith(pwd)) return absPath;
    
    const rel = abs.substr(pwd.length);
    return rel.split(path.sep).filter(x => x.length > 0).join(path.sep);
}

async function processFiles(sourceFile, providerFile, targetFile, search = null, repl = null) {
    let refContent = await fs.promises.readFile(providerFile, 'utf8');
    if (search && repl) {
        const regex = new RegExp(search, 'g');
        refContent = refContent.replace(regex, repl);
    }

    const refMap = JSON.parse(refContent);
    const output = fs.createWriteStream(targetFile, 'utf8');

    // to keep formatting in the files, iterate lines
    const input = fs.createReadStream(sourceFile, 'utf8');
    const rl = readline.createInterface({
        input: input,
        crlfDelay: Infinity
    });

    for await (const line of rl) {
        const match = line.match(/^(\s*)"(.+)":(\s*)".+"(,{0,1})$/);
        if (!match) {
            output.write(line);
            output.write('\n');
            continue;
        }

        const key = match[2];
        /** @type string */
        const rel = refMap[key];
        if (rel) {
            const preSpace = match[1]; // whitespace before the key
            const midSpace = match[3]; // whitespace between colon and value
            const suffix = match[4];

            output.write(`${preSpace}"${key}":${midSpace}"${rel.replace(/"/g, '\\"')}"${suffix}\n`);
        } else {
            console.warn(`No mapping found for "${key}"`);
        }
    }
}

// Start
if (process.argv.length < 5) {
    console.error(`Usage: ${normPath(process.argv0)} ${normPath(process.argv[1])} <sourceFile> <providerFile> <targetFile> [search regex] [replace]`);
    process.exit(1);
}

const argv = process.argv.slice(2);
const [sourceFile, providerFile, targetFile] = argv.slice(0, 3).map(x => path.resolve(x));
const find = argv.length >= 4 ? argv[3] : null;
const repl = argv.length >= 5 ? argv[4] : null;

console.log(`Generating '${normPath(targetFile)}' from '${normPath(providerFile)}' according to '${normPath(sourceFile)}'...`)
processFiles(sourceFile, providerFile, targetFile, find, repl)
    .then(() => console.log('Done.'))
    .catch(err => console.error('Error while generating language file:', err));