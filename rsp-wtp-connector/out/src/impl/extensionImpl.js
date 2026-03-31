"use strict";
/*-----------------------------------------------------------------------------------------------
 *  Copyright (c) Red Hat, Inc. All rights reserved.
 *  Licensed under the EPL v2.0 License. See LICENSE file in the project root for license information.
 *-----------------------------------------------------------------------------------------------*/
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
exports.JAVA_EXTENSION = exports.JAVA_DEBUG_EXTENSION = void 0;
exports.activateImpl = activateImpl;
exports.deactivateImpl = deactivateImpl;
const controller_1 = require("./controller");
const vscode = require("vscode");
const rsp_wtp_server_connector_api_1 = require("rsp-wtp-server-connector-api");
const lib_1 = require("@redhat-developer/vscode-extension-proposals/lib");
const telemetry_1 = require("./telemetry");
exports.JAVA_DEBUG_EXTENSION = 'vscjava.vscode-java-debug';
exports.JAVA_EXTENSION = 'redhat.java';
let activeController;
function activateImpl(context, opts) {
    return __awaiter(this, void 0, void 0, function* () {
        yield (0, telemetry_1.initializeTelemetry)(context);
        const api = new controller_1.EquinoxRspController(opts);
        const rsp = {
            state: rsp_wtp_server_connector_api_1.ServerState.UNKNOWN,
            type: {
                id: opts.providerId,
                visibilename: opts.providerName
            }
        };
        const serverConnectorUI = yield (0, rsp_wtp_server_connector_api_1.retrieveUIExtension)();
        if (serverConnectorUI.available) {
            serverConnectorUI.api.registerRSPProvider(rsp);
        }
        registerRecommendations(context);
        context.subscriptions.push(vscode.commands.registerCommand('wtp.serverConnector.openWorkspaceStorage', () => __awaiter(this, void 0, void 0, function* () {
            const storageUri = context.storageUri;
            if (!storageUri) {
                vscode.window.showErrorMessage('No workspace-specific storage available (probably no folder opened).');
                return;
            }
            yield vscode.workspace.fs.createDirectory(storageUri);
            yield vscode.commands.executeCommand('revealFileInOS', storageUri);
        })));
        activeController = api;
        context.subscriptions.push(new vscode.Disposable(() => {
            void stopActiveController();
        }));
        const storageUri = context.storageUri;
        if (!storageUri) {
            vscode.window.showErrorMessage('No workspace-specific storage available (probably no folder opened).');
            return;
        }
        yield vscode.workspace.fs.createDirectory(storageUri);
        process.env['VSCODE_STORAGE_PATH'] = storageUri.path;
        return api;
    });
}
function deactivateImpl(opts) {
    return __awaiter(this, void 0, void 0, function* () {
        yield stopActiveController();
        const serverConnector = yield (0, rsp_wtp_server_connector_api_1.retrieveUIExtension)();
        if (serverConnector.available) {
            serverConnector.api.deregisterRSPProvider(opts.providerId);
        }
    });
}
function stopActiveController() {
    return __awaiter(this, void 0, void 0, function* () {
        const controller = activeController;
        activeController = undefined;
        if (!controller) {
            return;
        }
        try {
            yield controller.stopRSP();
        }
        catch (error) {
            console.error('Failed to stop RSP process during extension shutdown', error);
        }
    });
}
function registerRecommendations(context) {
    return __awaiter(this, void 0, void 0, function* () {
        const telem = yield (0, telemetry_1.getTelemetryServiceInstance)();
        const recommendService = lib_1.RecommendationCore.getService(context, telem);
        if (recommendService) {
            const r1 = recommendService.create(exports.JAVA_EXTENSION, 'Language Support for Java', '\'Language Support for Java\' is recommended for a better development environment experience when developing applications targeted to Java-based application servers .', true);
            const r2 = recommendService.create(exports.JAVA_DEBUG_EXTENSION, 'Debugger for Java', '\'Debugger for Java\' is required to launch a server in debug mode and connect to it with a debugger.', true);
            recommendService.register([r1, r2]);
        }
    });
}
//# sourceMappingURL=extensionImpl.js.map