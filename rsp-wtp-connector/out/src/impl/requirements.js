/*-----------------------------------------------------------------------------------------------
 *  Copyright (c) Red Hat, Inc. All rights reserved.
 *  Licensed under the EPL v2.0 License. See LICENSE file in the project root for license information.
 *-----------------------------------------------------------------------------------------------*/
'use strict';
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.resolveRequirements = resolveRequirements;
exports.resolveRequirementsImpl = resolveRequirementsImpl;
exports.parseMajorVersion = parseMajorVersion;
const cp = require("child_process");
const expandHomeDir = require("expand-home-dir");
const findJavaHome = require("find-java-home");
const path = require("path");
const pathExists = require("path-exists");
const vscode_1 = require("vscode");
const isWindows = process.platform.indexOf('win') === 0;
const JAVAC_FILENAME = 'javac' + (isWindows ? '.exe' : '');
const JAVA_FILENAME = 'java' + (isWindows ? '.exe' : '');
/**
 * Resolves the requirements needed to run the extension.
 * Returns a promise that will resolve to a RequirementsData if
 * all requirements are resolved, it will reject with ErrorData if
 * if any of the requirements fails to resolve.
 */
function resolveRequirements(minJavaVersion) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const ret = yield resolveRequirementsImpl(minJavaVersion);
            return Promise.resolve(ret);
        }
        catch (err) {
            const ret = {
                unexpectedError: err
            };
            return Promise.resolve(ret);
        }
    });
}
function resolveRequirementsImpl(minJavaVersion) {
    return __awaiter(this, void 0, void 0, function* () {
        const javaHome = yield checkJavaRuntime();
        if (!javaHome) {
            return { rejection: getRejectionWithDownloadUrl('Unexpected Error: Java Home could not be located') };
        }
        if (javaHome.rspReqReject) {
            return { rejection: javaHome };
        }
        const javaHome2 = javaHome;
        const javaVersion = yield checkJavaVersion(javaHome2, minJavaVersion);
        if (!javaVersion) {
            return { rejection: getRejectionWithDownloadUrl('Unexpected Error: Java Version could not be discovered for java home ' + javaHome2) };
        }
        if (javaVersion.rspReqReject) {
            return { rejection: javaVersion };
        }
        const javaVersion2 = javaVersion;
        const data = { java_home: javaHome2, java_version: javaVersion2 };
        return { data: data };
    });
}
function checkJavaRuntime() {
    return __awaiter(this, void 0, void 0, function* () {
        let source;
        let javaHome = readJavaConfig();
        if (javaHome) {
            source = 'The wtp-rsp-ui.rsp.java.home variable defined in VS Code settings';
        }
        else {
            javaHome = process.env.JDK_HOME;
            if (javaHome) {
                source = 'The JDK_HOME environment variable';
            }
            else {
                javaHome = process.env.JAVA_HOME;
                source = 'The JAVA_HOME environment variable';
            }
        }
        if (javaHome) {
            javaHome = expandHomeDir(javaHome);
            if (!pathExists.sync(javaHome)) {
                return getRejectionWithDownloadUrl(`${source} points to a missing folder`);
            }
            if (!pathExists.sync(path.resolve(javaHome, 'bin', JAVA_FILENAME))) {
                return getRejectionWithDownloadUrl(`${source} does not point to a JDK. The '${JAVA_FILENAME}' command is missing.`);
            }
            if (!pathExists.sync(path.resolve(javaHome, 'bin', JAVAC_FILENAME))) {
                return getRejectionWithDownloadUrl(`${source} does not point to a JDK. The '${JAVAC_FILENAME}' command is missing.`);
            }
            return javaHome;
        }
        // No settings, let's try to detect as last resort.
        const ret1 = yield new Promise((resolve, reject) => {
            findJavaHome((err, home) => {
                if (err) {
                    resolve(getRejectionWithDownloadUrl('Java runtime could not be located'));
                }
                else {
                    resolve(home);
                }
            });
        });
        return ret1;
    });
}
function readJavaConfig() {
    const config = vscode_1.workspace.getConfiguration();
    const ret = config.get('wtp-rsp-ui.rsp.java.home', undefined);
    if (ret)
        return ret;
    // Backwards compatibility
    return config.get('java.home', undefined);
}
function checkJavaVersion(javaHome, minJavaVersion) {
    return __awaiter(this, void 0, void 0, function* () {
        const javaExecutable = path.resolve(javaHome, 'bin', JAVA_FILENAME);
        let ret;
        let cpDone = false;
        const process = cp.execFile(javaExecutable, ['-version'], {}, (error, stdout, stderr) => {
            const javaVersion = parseMajorVersion(stderr);
            if (!javaVersion) {
                ret = getRejectionWithDownloadUrl(`Java ${minJavaVersion} or newer is required. No Java was found on your system.
            Please get a recent JDK or configure it for "Servers View" if it already exists`);
            }
            else if (javaVersion < minJavaVersion) {
                ret = getRejectionWithDownloadUrl(`Java ${minJavaVersion} or newer is required. Java ${javaVersion} was found at ${javaHome}.
            Please get a recent JDK or configure it for "Servers View" if it already exists`);
            }
            else {
                ret = javaVersion;
            }
            cpDone = true;
        });
        let processDone = false;
        process.on('exit', function () {
            processDone = true;
        });
        const start = Date.now();
        const max = 10000;
        const end = start + max;
        let expired = false;
        while (!(cpDone && processDone) && !expired) {
            yield new Promise(resolve => setTimeout(resolve, 250));
            expired = Date.now() > end;
        }
        if (!ret && expired) {
            try {
                if (process)
                    process.kill();
                // eslint-disable-next-line no-empty
            }
            catch (e) { }
            const msg = 'Error getting java version for ' + javaExecutable + ': \'java -version\' did not return within ' + (max / 1000) + ' seconds.';
            return getRejectionWithDownloadUrl(msg);
        }
        if (!ret) {
            const msg = 'Error getting java version for ' + javaExecutable + ': \'java -version\' output was unable to be parsed.';
            return getRejectionWithDownloadUrl(msg);
        }
        return ret;
    });
}
function parseMajorVersion(content) {
    let regexp = /version "(.*)"/g;
    let match = regexp.exec(content);
    if (!match) {
        return undefined;
    }
    let version = match[1];
    // Ignore '1.' prefix for legacy Java versions
    if (version.startsWith('1.')) {
        version = version.substring(2);
    }
    // look into the interesting bits now
    regexp = /\d+/g;
    match = regexp.exec(version);
    let javaVersion = 0;
    if (match) {
        javaVersion = parseInt(match[0], 10);
    }
    return javaVersion;
}
const newLocal = 'https://developers.redhat.com/products/openjdk/download/?sc_cid=701f2000000RWTnAAO';
function getRejectionWithDownloadUrl(message) {
    let jdkUrl = newLocal;
    if (process.platform === 'darwin') {
        jdkUrl = 'http://www.oracle.com/technetwork/java/javase/downloads/index.html';
    }
    const rejectVal = {
        rspReqReject: true,
        message: message,
        label: 'Get the Java Development Kit',
        openUrl: vscode_1.Uri.parse(jdkUrl),
        replaceClose: false,
        btns: [
            {
                label: 'Get the Java Development Kit',
                openUrl: vscode_1.Uri.parse(jdkUrl),
            },
            {
                label: 'Configure Java'
            }
        ]
    };
    return rejectVal;
}
//# sourceMappingURL=requirements.js.map