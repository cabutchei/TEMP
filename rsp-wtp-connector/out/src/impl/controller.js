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
exports.EquinoxRspController = void 0;
const events_1 = require("events");
const rsp_wtp_server_connector_api_1 = require("rsp-wtp-server-connector-api");
const server_1 = require("./server");
class EquinoxRspController {
    constructor(opts) {
        this.opts = opts;
        this.launcher = new server_1.EquinoxRspLauncher(opts);
        this.host = '';
        this.port = 0;
        this.emitter = new events_1.EventEmitter();
    }
    getLauncher() {
        return this.launcher;
    }
    startRSP(stdoutCallback, stderrCallback) {
        return __awaiter(this, void 0, void 0, function* () {
            this.updateRSPStateChanged(rsp_wtp_server_connector_api_1.ServerState.STARTING);
            try {
                const startResult = yield this.launcher.start(stdoutCallback, stderrCallback, this);
                if (startResult) {
                    this.host = startResult.host;
                    this.port = startResult.port;
                    this.updateRSPStateChanged(rsp_wtp_server_connector_api_1.ServerState.STARTED);
                    return startResult;
                }
                else {
                    return this.handleStartError('No result from launcher.start');
                }
            }
            catch (error) {
                return this.handleStartError(error);
            }
        });
    }
    handleStartError(error) {
        return __awaiter(this, void 0, void 0, function* () {
            this.updateRSPStateChanged(rsp_wtp_server_connector_api_1.ServerState.STOPPED);
            const innerMsg = error ? (error.message ? error.message : JSON.stringify(error)) : '';
            return Promise.reject(`RSP Error - ${this.opts.providerName} failed to start - ${innerMsg}`);
        });
    }
    stopRSP() {
        return __awaiter(this, void 0, void 0, function* () {
            this.launcher.terminate().catch(error => {
                const innerMsg = error ? (error.message ? error.message : JSON.stringify(error)) : '';
                return Promise.reject(`RSP Error - ${this.opts.providerName} failed to stop - ${innerMsg}`);
            });
        });
    }
    getImage(serverType) {
        if (!serverType) {
            return null;
        }
        const toCall = this.opts.getImagePathForServerType;
        if (!toCall) {
            return null;
        }
        return toCall(serverType);
    }
    onRSPServerStateChanged(listener) {
        this.emitter.on('rspServerStateChanged', listener);
    }
    updateRSPStateChanged(state) {
        return __awaiter(this, void 0, void 0, function* () {
            this.emitter.emit('rspServerStateChanged', state);
        });
    }
    getHost() {
        return this.host;
    }
    getPort() {
        return this.port;
    }
}
exports.EquinoxRspController = EquinoxRspController;
//# sourceMappingURL=controller.js.map