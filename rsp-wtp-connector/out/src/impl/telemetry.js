"use strict";
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
exports.initializeTelemetry = initializeTelemetry;
exports.getTelemetryServiceInstance = getTelemetryServiceInstance;
/*******************************************************************************
 * Copyright (c) 2021 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
const vscode_redhat_telemetry_1 = require("@redhat-developer/vscode-redhat-telemetry");
let telemetryService;
function initializeTelemetry(context) {
    return __awaiter(this, void 0, void 0, function* () {
        telemetryService = (yield (0, vscode_redhat_telemetry_1.getRedHatService)(context)).getTelemetryService();
    });
}
function getTelemetryServiceInstance() {
    return __awaiter(this, void 0, void 0, function* () {
        return telemetryService;
    });
}
//# sourceMappingURL=telemetry.js.map