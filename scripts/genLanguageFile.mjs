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

if (process.argv.length < 5) {
    console.error(`Usage: ${normPath(process.argv0)} ${normPath(process.argv[1])} <sourceFile> <providerFile> <targetFile>`);
    process.exit(1);
}

(async () => {
    const [sourceFile, providerFile, targetFile] = process.argv.slice(2, 5).map(x => path.resolve(x));

    console.log(`Generating '${normPath(targetFile)}' from '${normPath(providerFile)}' according to '${normPath(sourceFile)}'...`)

    const refMap = JSON.parse(await fs.promises.readFile(providerFile));
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

    console.log('Done.')
})();